package com.sist.dao;
import java.sql.*;
import java.util.*;
//DBCP
import javax.sql.*;
import javax.naming.*;
public class FoodDAO {
	//연결 객체 얻기
	private Connection conn;
	// SQL송수신 
	private PreparedStatement ps;
	// 싱글턴
	private static FoodDAO dao;
	// 출력 갯수
	private final int ROW_SIZE=20;
	// Pool영역에서 Connection객체를 얻어 온다
	public void getConnection(){
		// Connection 객체 주소를 => 메모리에 저장
		// 저장 공간 => POOL 영역 (디렉토리형식으로 저장) => JNDI
		// 루트 => 저장 공간
		// java//env/comp => C드라이버 => jdbc/oracle
		
		try {
			//1.탐색기를 연다
			Context init=new InitialContext();
			//2.C드라이버 연결
			Context cdriver=(Context)init.lookup("java://comp/env");
			//lookup => 문자열(key) => 객체 찾기 (RMI) => Socket
			//3. Connection 객체 찾기
			DataSource ds=(DataSource)cdriver.lookup("jdbc/oracle");
			//4. Connection주소를 연결
			conn=ds.getConnection();
			//282page
			// => 오라클 연결 시간을 줄이낟
			// => Connection 객체가 일정하게 생성 => 관리 
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	//connection객체 사용 후에 반환
	public void disConnection(){
		try {
				if(ps!=null) ps.close();
				if(conn!=null) conn.close();
		} catch (Exception e) {}
	}
	// 싱글턴 객체 만들기
	public static FoodDAO newInstance(){
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	// 동일 => 오라클 연결후 SQL문장 실행
	public List<FoodBean> foodListData(int page){
		List<FoodBean> list=new ArrayList<FoodBean>();
		try {
			 getConnection();
			 
			 String sql="SELECT fno,poster,name,num FROM(SELECT fno,poster,name,rownum as num "
			 		+ "FROM(SELECT /*INDEX_ASC(food_location fl_fno_pk)*/fno,poster,name "
			 		+ "FROM food_location)) WHERE num BETWEEN ? AND ?";
			 ps=conn.prepareStatement(sql);
			 int rowsize=ROW_SIZE;
			 int startPage=(rowsize*page)-(rowsize-1);
			 int endPage=rowsize*page;
			 
			 ps.setInt(1, startPage);
			 ps.setInt(2, endPage);
			 
			 ResultSet rs=ps.executeQuery();
			 while(rs.next()) {
				 FoodBean vo=new FoodBean();
				 vo.setFno(rs.getInt(1));
				 vo.setName(rs.getString(3));
				 String poster=rs.getString(2);
				 poster=poster.substring(0,poster.indexOf("^"));
				 poster=poster.replace("#", "&");
				 vo.setPoster(poster);
				 list.add(vo);
			 }
		}catch (Exception e) {
				e.printStackTrace();
		}finally {
				disConnection();
		}
		return list;
	}
	public int foodtotalPage()
	{
		int totalpage=0;
		try {
			getConnection();
			
			String sql="SELECT CEIL(COUNT(*)/"+ROW_SIZE+")"+" FROM food_location";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			rs.next();
			totalpage=rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return totalpage;
	}
	//상세보기
	public FoodBean foodDetailData(int fno)
	{
		FoodBean vo=new FoodBean();
		try {
			getConnection();
			
			String sql="SELECT fno,poster,name,score,tel,type,price,time,menu,parking,address "
					+ "FROM food_location WHERE fno=?";
			ps=conn.prepareStatement(sql);
			//실행전에?에 값을 채운다
			ps.setInt(1, fno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setPoster(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setTel(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setPrice(rs.getString(7));
			vo.setTime(rs.getString(8));
			vo.setMenu(rs.getString(9));
			vo.setParking(rs.getString(10));
			vo.setAddress(rs.getString(11));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return vo;
	}

}
