package com.sist.dao;

import java.sql.*;
import java.util.*;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	public static BoardDAO dao;
	public final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	
	//1.드라이버 등록
	public BoardDAO()
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
		
		
	}
	
	//2.싱글턴
	//메모리 누수, Connection 객체 생성갯수를 제한
	// 한개의 메모리만 사용이 가능하게 만든다
	// 서버프로그램, 데이터 베이스 프로그램에서 주로 사용
	//**Spring은 모든 객체가 싱글턴이다
	public static BoardDAO newInstance() 
	{
		if(dao==null)
		{
			dao=new BoardDAO();
		}
		return dao;
		
	}
	
	//3.오라클 연결
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
	//4.오라클 해제
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
	/////////// ==> 필수 =====> 클래스화 (라이브러리)
	//5. 기능
	//5-1. 목록 출력 => 페이지 나누기 (인라인 뷰); SELECT
	// => 1page => 10개
	// => BoardVO (게시물 1개)
	public List<BoardVo> boardListData(int page)
	{
		List<BoardVo> list=new ArrayList<BoardVo>();
		try 
		{
			//1.연결
			getConnection();
			//2.SQL문장 생성
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-dd'),hit,num "
					+ "FROM(SELECT no,subject,name,regdate,hit,rownum as num "
					+ "FROM(SELECT no,subject,name,regdate,hit "
					+ "FROM freeboard ORDER BY no DESC)) WHERE num BETWEEN ? AND ?";
			//rownumd은 중간에 데이터를 주출 할 수 없다
			//3.SQL문장 전송
			ps=conn.prepareStatement(sql);
			//4.사용자 요청한 데이터를 첨부
			//4-1. ?에 값을 채운다
			int rowSize=10;
			int start=(page-1)*rowSize+1;
			int end=page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			//5. 실행요청후 결과값을 받는다
			ResultSet rs=ps.executeQuery();
			//6. 받은 결과값을 리스트에 첨부
			while(rs.next())
			{
				BoardVo vo=new BoardVo();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3)); // SQL 데이터 받아서 저장
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			disConnection();
		}
		return list;
	}
	//5-1-1. 총페이지 구하기
	public int boardTotalPage()
	{
		int total=0;
		try 
		{
			//연결
			getConnection(); //반복 => 메소드
			
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM freeboard";
			// 43/10.0 => 4.3 => 5
			ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			rs.next(); //값이 출력되어 있는 위치에 커서를 이동
			total=rs.getInt(1);
			rs.close();
			
			
		} catch (Exception e) 
		{	
			e.printStackTrace();
		}
		finally 
		{
			disConnection();
		}
		return total;
	}
	//5-2. 상세보기 => 조회수 증가(UPDATE), 상세볼 게시물 읽기(SELECT) 
	public BoardVo boardDetailData(int no)
	{
		BoardVo vo=new BoardVo();
		try {
			getConnection();
		
			String sql="UPDATE freeboard SET hit=hit+1 WHERE no="+no;  //데이터가 많을 때는 ?
			
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql="SELECT no,name,subject,content,TO_CHAR(regdate,'yyyy-MM-dd'),hit "
					+ "FROM freeboard WHERE no="+no;
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}
	//5-3. 게시물 등록 => INSERT
	// 용도 (SQL문장 사용법, HTML 태그 => 웹사이트)
	public void boardInsert(BoardVo vo)
	{
		try {
			getConnection();
			String sql="INSERT INTO freeboard(no,name,subject,content,pwd) VALUES(fb_no_seq.nextval,?,?,?,?)";
			
			ps=conn.prepareStatement(sql);
		//실행 요청 전에 ?에 값을 채운다
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			//실행
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
	//5-4. 수정 (UPDATE) => 먼저 입력된 게시물 읽기, 실제 수정 (비밀번호 검색)
	public BoardVo boardDetailData2(int no)
	{
		BoardVo vo=new BoardVo();
		try {
			getConnection();
		
	
			String sql="SELECT no,name,TO_CHAR(regdate,'yyyy-MM-dd'),hit "
					+ "FROM freeboard WHERE no="+no;
			
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setDbday(rs.getString(3));
			vo.setHit(rs.getInt(4));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return vo;
	}
//	public BoardVo boardUpdate(BoardVo vo)
//	{
//		boolean bCheck=false;
//		try 
//		{
//			getConnection();
//			String sql="UPDATE freeboard SET subject=" WHERE no="+no;
//			ps=conn.prepareStatement(sql);
//			
//		} catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//		finally {
//			disConnection();
//		}
//		
//	}
	//5-5. 삭제(DELETE) => 비밀번호검색
	public boolean boardDelete(int no,String pwd)
	{
		boolean bCheck=false; // 비밀번호 => 본인여부 확인
		try { 
			getConnection();
			String sql="SELECT pwd FROM freeboard WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			String dp_pwd=rs.getString(1);
			rs.close();
			if(dp_pwd.equals(pwd))
			{
				bCheck=true;
				sql="DELETE FROM freeboard WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return bCheck;
	}
	//5-6. 찾기 (이름,제목,내용) => LIKE
}
