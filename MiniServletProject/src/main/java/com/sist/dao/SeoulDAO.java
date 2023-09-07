package com.sist.dao;

import java.sql.*;
import java.util.*;



public class SeoulDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static SeoulDAO dao;
	
	public SeoulDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//java.io,java.net.java.sql
			
		} catch (Exception e) {}
	}
	//2.오라클 연결
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
	//3.오라클 해제
	public void disConnection()
	{
		try {
			if(ps!=null)
			{
				ps.close(); //통신이 열려있으면 닫는다
			}
			if(conn!=null)
			{
				conn.close();
			}
		} catch (Exception e) {}
		
	}
	//4.싱글턴 설정 = static은 메모리 공간이 1개만 가지고 있다
	// 메모리 누수 현상을 방지 ...
	// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다
	//싱글턴은 데이터베이스에서는 필수
	public static SeoulDAO newInstance()
	{
		
		if(dao==null)
		{
			dao=new SeoulDAO();
		}
		return dao;
	}
	
	//5.기능
	//목록 => SQL(인라인 뷰 => 페이징 기법)
	public List<SeoulVO> seoulListData(int page)
	{
		List<SeoulVO> list=new ArrayList<SeoulVO>();
		try {
			getConnection();
			String sql="SELECT no,title,poster,num FROM (SELECT no,title,poster,rownum as num FROM (SELECT no,title,poster "
					+ "FROM seoul_location ORDER BY no ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			//rownum => 가상컬럼 (오라클에 지원)
			// 단점 => 중간에 데이터를 추출 할 수 없다 => TOP-N
			// SQL문장 전송
			ps=conn.prepareStatement(sql);
			//?값을 채운다 => IN,OUT 입출력 에러
			int rowSize=12; // 여기만 바꾸면 됌
			int startPage=(rowSize*page)-(rowSize-1);
			int endPage=rowSize*page;
			ps.setInt(1, startPage);
			ps.setInt(2, endPage);
			//실행 요청
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				SeoulVO vo=new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return list;
	}
	
	public List<SeoulVO> seoulNatureData(int page)
	{
		List<SeoulVO> list=new ArrayList<SeoulVO>();
		try {
			getConnection();
			String sql="SELECT no,title,poster,num FROM (SELECT no,title,poster,rownum as num "
					+ "FROM (SELECT no,title,poster FROM seoul_nature ORDER BY no ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int startPage=(rowSize*page)-(rowSize-1);
			int endPage=rowSize*page;
			
			ps.setInt(1, startPage );
			ps.setInt(2, endPage );
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SeoulVO vo=new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				list.add(vo);
			}
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
		return list;
	}

}
