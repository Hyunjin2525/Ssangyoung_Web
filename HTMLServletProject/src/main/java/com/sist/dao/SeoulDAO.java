package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;

public class SeoulDAO {
	private String[] tables= {
		"seoul_location","seoul_nature","seoul_shop","seoul_hotel"	
	};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static SeoulDAO dao;
	
	// 1.기능
	public List<SeoulVO> seoulListData(int page, int type)
	{
		List<SeoulVO> list=new ArrayList<SeoulVO>();
		try {
			conn=db.getConnection();
			String sql="SElECT no,title,poster FROM "+tables[type]+" WHERE no=?"; // type을 ps.set으로 셋팅하면 ''가 붙어서 안된다
			ps=conn.prepareStatement(sql);
			ps.setInt(1, page);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				SeoulVO vo=new SeoulVO();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	//2. 총페이지 구하기
	public int seoulTotalpage(int type)
	{
		int total=0;
		try {
			conn=db.getConnection();
			String sql="SELECT CEIL(COUNT(*))/12.0) FROM "+tables[type];
			ResultSet rs=ps.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
			
	//3.상세보기
	public SeoulVO seoulDetailData(int no, int type)
	{
		SeoulVO vo=new SeoulVO();
		try {
			conn=db.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
		
	
}
