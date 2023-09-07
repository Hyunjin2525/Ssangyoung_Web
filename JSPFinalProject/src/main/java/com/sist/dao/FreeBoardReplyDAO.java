package com.sist.dao;
import java.util.*;

import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

/*
 *  try
 *  {
 *  	getConnection()
 *  	insert => commit() => 에러 발생
 *  	update => commit() => 에러 발생   == 방지 => 트랜잭션0 코딩
 *  	insert => commit()
 *  	**commit()
 *  }catch(Exception ex)
 *  {
 *  	**rollback()
 *   ex.printStackTrace();
 *  }
 *  finally
 *  {
 *  	disConnection()
 *  }
 *  
 */
public class FreeBoardReplyDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static FreeBoardReplyDAO dao;
	
	//싱글턴
	public static FreeBoardReplyDAO newInstance()
	{
		if(dao==null)
			dao=new FreeBoardReplyDAO();
		return dao;
	}
	//목록 출력 bno=게시물 번호
	public List<FreeBoardReplyVO> replyListData(int bno)
	{
		List<FreeBoardReplyVO> list=new ArrayList<FreeBoardReplyVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT no,bno,id,name,msg,TO_CHAR(regdate,'yyyy-MM-dd HH24:MI:SS'),group_tab "
					+ "FROM project_freeboard_reply WHERE bno=? ORDER BY group_id DESC,group_step ASC";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FreeBoardReplyVO vo=new FreeBoardReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setBno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setName(rs.getString(4));
				vo.setMsg(rs.getString(5));
				vo.setDbday(rs.getString(6));
				vo.setGroup_tab(rs.getInt(7));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	//수정
	public void replyUpdate(int no, String msg)
	{
		try {
			conn=db.getConnection();
			String sql="UPDATE project_freeboard_reply SET msg=? WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setInt(2, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	//삭제 => 트랜잭션 적용 (일괄처리) => commit(), catch => rollback()
	//댓글 입력
	public void replyInsert(FreeBoardReplyVO vo)
	{
		try {
			conn=db.getConnection();
			String sql="INSERT INTO project_freeboard_reply(no,bno,id,name,msg,group_id) VALUES(pfr_no_seq.nextval,?,?,?,?,"
					+ "(SELECT NVL(MAX(group_id)+1,1) FROM project_freeboard_reply))";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	//대댓글 입력 => 트랜잭션 적용 (일괄처리) => commit(), catch => rollback()
	//대댓글 입력
	// Spring // @Transactional
	public void replyReplyInsert(int pno,FreeBoardReplyVO vo)
	{
		try {
			conn=db.getConnection();
			//autocommit 해제
			conn.setAutoCommit(false);
			// 처리 =>SQL문장이 여러개 수행 => INSERT / UPDATE / DELETE
			String sql="SELECT group_id,group_step,group_tab "
					+ "FROM project_freeboard_reply "
					+ "WHERE no=?";   //댓글의 글 데이터 
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int gi=rs.getInt(1);
			int gs=rs.getInt(2);
			int gt=rs.getInt(3);
			rs.close();
			
			// group_step+1
			sql="UPDATE project_freeboard_reply SET group_step=group_step+1 WHERE group_id=? AND group_step>?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate(); //(commit()=X)
			
			//insert
			sql="INSERT INTO project_freeboard_reply VALUES(pfr_no_seq.nextval,?,?,?,?,SYSDATE,?,?,?,?,0)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, pno);
			ps.executeUpdate();
			
			//depth
			sql="UPDATE project_freeboard_reply SET depth=depth+1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ps.executeUpdate();
			
			
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {}
			e.printStackTrace();
		}finally {
			//conn의 원래 기능으로 설정
			try {
				conn.setAutoCommit(true);                     //<=트랜잭션 코드
			} catch (Exception e2) {}
			db.disConnection(conn, ps);
		}
	}
	//댓글 삭제
	public void replyDelete(int no)
	{
		try {
			conn=db.getConnection();
			conn.setAutoCommit(false);
			
			String sql="SELECT root,depth FROM project_freeboard_reply WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			int root=rs.getInt(1);
			int depth=rs.getInt(2);
			rs.close();
			
			if(depth==0)
			{
				sql="DELETE FROM project_freeboard_reply WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
			else
			{
				String msg="관리자가 삭제한 댓글입니다.";
				sql="UPDATE project_freeboard_reply SET msg=? WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, msg);
				ps.setInt(2, no);
				ps.executeUpdate();
				
			}
			
			//depth감소
			sql="UPDATE project_freeboard_reply SET depth=depth-1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {}
			e.printStackTrace();
		}
		finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {}
			db.disConnection(conn, ps);
		}
	}
}
