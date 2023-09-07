package com.sist.dao;

import java.sql.*;

import java.util.*;

import com.sist.dao.CreateDataBase;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FoodDAO dao;
	
	public static FoodDAO newInstance() {
		if(dao==null)
			dao = new FoodDAO();
		return dao;
	}
	
	public List<FoodVO> foodListData()
	{
		List<FoodVO>list=new ArrayList<FoodVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT fno,poster,name,rownum "
					+ "FROM food_location "
					+ "WHERE rownum<=20";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				vo.setFno(rs.getInt(1));
				String poster=rs.getString(2);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(3));
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
	public FoodVO foodatailData(int fno)
	{
		FoodVO vo=new FoodVO();
		try {
			//1.오라클 연결
			conn=db.getConnection();
			String sql="SELECT fno,name,score,poster,address,type,parking,menu,tel,price FROM food_location WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setScore(rs.getDouble(3));
			vo.setPoster(rs.getString(4));
			vo.setAddress(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setParking(rs.getString(7));
			vo.setMenu(rs.getString(8));
			vo.setPhone(rs.getString(9));
			vo.setPrice(rs.getString(10));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
}
