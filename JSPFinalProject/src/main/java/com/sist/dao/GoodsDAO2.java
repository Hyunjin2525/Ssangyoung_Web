package com.sist.dao;
import java.util.*;
import java.sql.*;
// 일반 값으 가져올 경우 JDBC를 써야 된다 
public class GoodsDAO2 {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public GoodsDAO2()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	public void getConnection()
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {}
	}
	
	public void disConnection()
	{
		try {
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (Exception e) {
		}
	}
	public List<Integer> goodsGetNoData(String tab)
	{
		List<Integer> list=new ArrayList<Integer>();
		try {
			getConnection();
			String sql="SELECT no FROM "+tab+" ORDER BY no ASC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			while(rs.next())
			{
				int no=rs.getInt(1);
				list.add(no);
			}
			rs.close();
		}catch (Exception e) 
		{
			
		}
		finally {
			disConnection();
		}
		return list;
	}
	
	public void goodsAccountInsert(int account,int no,String tab)
	{
		try {
			getConnection();
			String sql="UPDATE "+tab+" SET account=? WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, account);
			ps.setInt(2, no);
			ps.executeUpdate();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			disConnection();
		}
	}
	public static void main(String[] args) {
		GoodsDAO2 dao=new GoodsDAO2();
		List<Integer> list=dao.goodsGetNoData("goods_all");
		
		for(int no:list)
		{	
			int account=(int)(Math.random()*50)+11;
			dao.goodsAccountInsert(account, no, "goods_all");
		}
		System.out.println("완료");
	}
}
