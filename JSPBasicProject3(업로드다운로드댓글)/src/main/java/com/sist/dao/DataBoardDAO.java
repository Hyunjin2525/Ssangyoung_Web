package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.vo.*;
public class DataBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	public static DataBoardDAO dao;
	public final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	
	public DataBoardDAO()
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
		
		
	}
	
	public static DataBoardDAO newInstance() 
	{
		if(dao==null)
		{
			dao=new DataBoardDAO();
		}
		return dao;
		
	}

	public void getConnection()
	{
		try 
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		} 
		catch (Exception e) 
		{
		}
	}

	public void disConnection()
	{
		try 
		{
			if(ps!=null)
			{
				ps.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		} catch (Exception e) 
		{
		}
	}
	
	//기능
	// 1.목록 => 페이징(인라인뷰)
	// 2. => 블록별 => 1 2 3 4 5 => 
	public List<DataBoardVO> databoardListData(int page){
		List<DataBoardVO> list=new ArrayList<DataBoardVO>();
		try {
			getConnection();
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,num "
					+ "FROM(SELECT  no,subject,name,regdate,hit,rownum as num "
					+ "FROM(SELECT /*+INDEX_DESC(jspDataBoard jd_no_pk)*/ no,subject,name,regdate,hit "
					+ "FROM jspDataBoard)) "
					+ "WHERE num BETWEEN ? AND ?";
			// rownum = TOP-N (중간에 잘라올 수 없다)
			ps=conn.prepareStatement(sql);
			// ?에 값을 채운다
			int rowSize= 10;
			int startPage= (rowSize*page)-(rowSize-1);
			int endPage= rowSize*page;
			
			/*
			 * rownum => 1번
			 * 1 page => 1~10
			 * 2page => 11~20;
			 */
			ps.setInt(1, startPage);
			ps.setInt(2, endPage);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				DataBoardVO vo=new DataBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				
				list.add(vo);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	//총페이지
	public int databoardRowCount()
	{
		//게시물 번호 노출X
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM jspDataBoard";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			count=rs.getInt(1);
			rs.close();
		
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		return count;
	}
	//데이터 추가 => Insert
	public void databoardInsert(DataBoardVO vo)
	{
		try {
			getConnection();
			String sql="INSERT INTO jspDataBoard VALUES(jd_no_seq.nextval,?,?,?,?,SYSDATE,0,?,?)";
			ps=conn.prepareStatement(sql);
			//?에 값을 채운다
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setString(5, vo.getFileName());
			ps.setInt(6, vo.getFileSize());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	//내용보기
	public DataBoardVO databoardDetailData(int no,int type)
	{
		DataBoardVO vo=new DataBoardVO();
		try {
			getConnection();
			if(type==0)
			{
				String sql="UPDATE jspDataBoard SET hit=hit+1 WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
			
			String sql="SELECT no,name,subject,content,TO_CHAR(regdate,'YYYY-MM-DD'),hit,filename,filesize FROM jspDataBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			vo.setFileName(rs.getString(7));
			vo.setFileSize(rs.getInt(8));
			rs.close();
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}
	//인기순위 10개 출력
	public List<DataBoardVO> databoardTop10()
	{
		List<DataBoardVO> list=new ArrayList<DataBoardVO>();
		try {
			getConnection();
			String sql="SELECT no,name,subject,rownum FROM (SELECT no,name,subject FROM jspDataBoard ORDER BY hit DESC) "
					+ "WHERE rownum<=10";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				DataBoardVO vo=new DataBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				list.add(vo);
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		return list;
		
	}
	//게시물 삭제
	public DataBoardVO databoardFileInfo(int no) 
	{
		DataBoardVO vo=new DataBoardVO();
		try {
			getConnection();
			String sql="SELECT filename,filesize FROM jspDataBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFileName(rs.getString(1));
			vo.setFileSize(rs.getInt(2));
			rs.close();
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		
		return vo;
	}

	public boolean databoardDelete(int no,String pwd)
	{
		boolean bCheck=false;
		try {
			getConnection();
			String sql="SELECT pwd FROM jspDataBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String dp_pwd=rs.getString(1);
			rs.close();
			if(dp_pwd.equals(pwd))
			{
				bCheck=true;
				sql="DELETE FROM jspReply WHERE bno=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();  //참조하고 있는 댓글 먼저 지워야 한다
				
				sql="DELETE FROM jspDataBoard WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return bCheck;
	}
	//수정 하기
	public boolean databoardUpdate(DataBoardVO vo)
	{
		boolean bCheck=false;
		try {
			getConnection();
			String sql="SELECT pwd FROM jspDataBoard WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs=ps.executeQuery();
			rs.next();
			String db_pwd=rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd()))
			{
				bCheck=true;
				sql="UPDATE jspDataBoard SET name=?,subject=?,content=? WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return bCheck;
	}
	//댓글
	//1.댓글 추가
	public void repltInsert(ReplyVO vo)
	{
		try {
			getConnection();
			String sql="INSERT INTO jspReply VALUES(jr_no_seq.nextval,?,?,?,?,SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	//2.댓글 읽기
	public List<ReplyVO> replyListData(int bno)
	{
		List<ReplyVO> list=new ArrayList<ReplyVO>();
		try {
			getConnection();
			String sql="SELECT /*+INDEX_DESC(jspReply jr_no_pk) */no,bno,id,name,msg,TO_CHAR(regdate,'YYY-MM-DD HH24:MI:SS') FROM jspReply WHERE bno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ReplyVO vo=new ReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setBno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setName(rs.getString(4));
				vo.setMsg(rs.getString(5));
				vo.setDbday(rs.getString(6));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		return list;
	}
	//3.댓글 수정 => Jquery
	public void replyUpdate(int no,String msg)
	{
		try {
	
			getConnection();
			String sql="UPDATE jspReply SET msg=? WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, msg);
			ps.setInt(2, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
	}
	
	//4.댓글 삭제 =>
	public void replyDelete(int no)
	{
		try {
			getConnection();
			String sql="DELETE FROM jspReply WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
	}
	}
