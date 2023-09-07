package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
import com.sist.common.*;
public class NoticeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static NoticeDAO dao;
	
	public static NoticeDAO newInstance()
	{
		if(dao==null)
			dao=new NoticeDAO();
		return dao;
	}
	
	public List<NoticeVO> noticeListDate(int page)
	{
		List<NoticeVO> list=new ArrayList<NoticeVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT no,subject,name,type,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit,num "
					+ "FROM (SELECT no,subject,name,type,regdate,hit,rownum as num "
					+ "FROM (SELECT /*+ INDEX_DESC(project_notice pn_no_pk*/no,subject,name,type,regdate,hit FROM project_notice)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowsize=10;
			int start=(rowsize*page)-(rowsize-1);
			int end=rowsize*page;
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				NoticeVO vo=new NoticeVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setType(rs.getInt(4));
				vo.setDbday(rs.getString(5));
				vo.setHit(rs.getInt(6));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	public int noticeTotalPage()
	{
		int total=0;
		try {
			conn=db.getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM project_notice";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			total=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	//상세보기
	public NoticeVO noticeDetailData(int no)
	{
		NoticeVO vo=new NoticeVO();
		try {
			conn=db.getConnection();
			String sql="UPDATE project_notice SET hit=hit+1 WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			sql="SELECT no,name,subject,content,type,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit "
					+ "FROM project_notice WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setType(rs.getInt(5));
			vo.setDbday(rs.getString(6));
			vo.setHit(rs.getInt(7));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	//목록
	//admin만
	//추가
	public void noticeInsert(NoticeVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="INSERT INTO project_notice VALUES(pn_no_seq.nextval,?,?,?,?,?,SYSDATE,0)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getName());
			ps.setInt(3, vo.getType());
			ps.setString(4, vo.getSubject());
			ps.setString(5, vo.getContent());
			// IN,OUT 입출력 오류
			ps.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	//삭제
	public void noticeDelete(int no)
	{
		try
		{
			conn=db.getConnection();
			String sql="DELETE FROM project_notice WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			// IN,OUT 입출력 오류
			ps.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	//수정
	public NoticeVO noticeUpdateData(int no)
	{
		NoticeVO vo=new NoticeVO();
		try
		{
			conn=db.getConnection();
			String sql="SELECT no,subject,content,type FROM project_notice WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			// IN,OUT 입출력 오류
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setSubject(rs.getString(2));
			vo.setContent(rs.getString(3));
			vo.setType(rs.getInt(4));
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	public void noticeUpdate(NoticeVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="UPDATE project_notice SET type=?,subject=?,content=? WHERE no=?";
			
			ps=conn.prepareStatement(sql);
			ps.setInt(1,vo.getType());
			ps.setString(2, vo.getSubject());
			ps.setString(2, vo.getContent());
			ps.setInt(3, vo.getNo());
			ps.executeUpdate();
			// IN,OUT 입출력 오류
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
}
