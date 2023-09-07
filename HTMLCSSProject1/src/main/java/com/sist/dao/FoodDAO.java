package com.sist.dao;
import java.util.*;
import java.sql.*;
public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	private static FoodDAO dao;
	
	//1.드라이버 등록 = 한번 수행 => 시작과 동시에 한번 수행, 멤버변수 초기화
	public FoodDAO()
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
	
	public static FoodDAO newInstance()
	{
		
		if(dao==null)
		{
			dao=new FoodDAO();
		}
		return dao;
	}
	
	public List<FoodVO> foodAllData()
	{
		List<FoodVO> list=new ArrayList<FoodVO>();
		try 
		{
				getConnection();
				String sql="SELECT name,address,poster,phone,type FROM food_house ORDER BY fno ASC";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					FoodVO vo=new FoodVO();
					String name=rs.getString(1);
					vo.setName(name);
					String	addr=rs.getString(2);
					addr=addr.substring(0,addr.lastIndexOf("지번"));
					vo.setAddress(addr);
					String poster=rs.getString(3);
					poster=poster.substring(0,poster.indexOf("^"));
					poster=poster.replace("#","&");
					vo.setPoster(poster);
					vo.setTel(rs.getString(4));
					vo.setType(rs.getString(5));
					list.add(vo);
					
					
				}
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
	

}
