package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.common.CreateDataBase;
import com.sist.vo.ReserveVO;

public class AdminDAO {
	 private Connection conn;
	   private PreparedStatement ps;
	   private CreateDataBase db=new CreateDataBase();
	   private static AdminDAO dao; 
	   
	   // 싱글턴 
	   public static AdminDAO newInstance()
	   {
		   if(dao==null)
			   dao=new AdminDAO();
		   return dao;
	   }
	   
	public List<ReserveVO> reserveAdminData()
	   {
		   List<ReserveVO> list=new ArrayList<ReserveVO>();
			try 
			{
				conn=db.getConnection();
				String sql="SELECT no,fno,rday,rtime,inwon,TO_CHAR(regdate,'yyyy-MM-dd hh24:mi:ss'),foodGetPoster(fno),foodGetName(fno),"
						+ "foodGetPhone(fno),rok,id FROM reserve_info ORDER BY no DESC";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					ReserveVO vo=new ReserveVO();
					vo.setNo(rs.getInt(1));
					vo.setFno(rs.getInt(2));
					vo.setRday(rs.getString(3));
					vo.setRtime(rs.getString(4));
					vo.setInwon(rs.getString(5));
					vo.setDbday(rs.getString(6));
					String poster=rs.getString(7);
					poster=poster.substring(0,poster.indexOf("^"));
					poster=poster.replace("#", "&");
					vo.setPoster(poster);
					vo.setName(rs.getString(8));
					vo.setTel(rs.getString(9));
					vo.setRok(rs.getString(10));
					vo.setId(rs.getString(11));
					list.add(vo);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				db.disConnection(conn, ps);
			}
			return list;
	   }
}
