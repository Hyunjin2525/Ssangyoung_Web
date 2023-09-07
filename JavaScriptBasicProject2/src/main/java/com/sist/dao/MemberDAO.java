package com.sist.dao;

import java.sql.*;

import java.util.*;
public class MemberDAO {

	
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static MemberDAO dao;
	
	//1.드라이버 등록 = 한번 수행 => 시작과 동시에 한번 수행, 멤버변수 초기화
	public MemberDAO()
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
	public static MemberDAO newInstance()
	{
		
		if(dao==null)
		{
			dao=new MemberDAO();
		}
		return dao;
	}
	//5. 기능
	public List<ZipcodeVO> postfind(String dong)
	{
		List<ZipcodeVO> list=new ArrayList<ZipcodeVO>();
			try {
				getConnection();
				String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') FROM zipcode WHERE dong LIKE '%'||?||'%'";
				ps=conn.prepareStatement(sql);
				ps.setString(1, dong);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					ZipcodeVO vo=new ZipcodeVO();
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
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
	
	public int postfindCount(String dong)
	{
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM zipcode WHERE dong LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, dong);
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
}
