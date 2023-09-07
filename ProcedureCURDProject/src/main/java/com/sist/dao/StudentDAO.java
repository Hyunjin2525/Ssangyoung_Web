package com.sist.dao;
import java.util.*;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

import java.lang.invoke.CallSite;
import java.sql.*;
public class StudentDAO {
	private Connection conn;
	//함수(프로시저) 호충
	private CallableStatement cs;
	//URL
	private final String URL="jdbc:oracle:thin:@211.238.142.111:1521:xe";
	
	private static StudentDAO dao;
	
	public StudentDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
		}
		
	}
	
	public static StudentDAO newInstance()
	{
		
		if(dao==null)
		{
			dao=new StudentDAO();
		}
		return dao;
	}
	
	//데이터 추가
	/*
	 * CREATE OR REPLACE PROCEDURE studentInsert(
    pName student.name%TYPE,
    pKor student.kor%TYPE,
    pEng student.eng%TYPE,
    pMath student.math%TYPE
)
is
begin
    INSERT INTO student values(
      (SELECT NVL(MAX(hakbun)+1,1) FROM student),
      pName,pKor,pEng,pMath
      
    );
    commit;
end;
	 */
	public void studentInsert(StudentVO vo)
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			//함수 호출만 하면 된다
			String sql="{CALL studentInsert(?,?,?,?)}";
			cs=conn.prepareCall(sql);//ERP => 메뉴얼
			// ?에 값을 채운 다음 실행
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			//실행 요청
			cs.executeQuery();
; 		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null)conn.close();
				
			}catch (Exception e) {
			}
		}
	}
	
	/*
	 * CREATE OR REPLACE PROCEDURE studentUpdate(
 pHakbun student.hakbun%TYPE,
 pName student.name%TYPE,
 pKor student.kor%TYPE,
 pEng student.eng%TYPE,
 pMath student.math%TYPE
)
is
begin
    UPDATE student SET
    name=pName, kor=pKOR, eng=pEng
    where hakbun=pHakbun;
    commit;
end;
/
	 * 
	 */
	public void studentUpdate(StudentVO vo)
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL studentUpdate(?,?,?,?,?)}";
			cs=conn.prepareCall(sql);
			//?에 값을 채운다
			cs.setInt(1, vo.getHakbun());
			cs.setString(2, vo.getName());
			cs.setInt(3, vo.getKor());
			cs.setInt(4, vo.getEng());
			cs.setInt(5, vo.getMath());
			cs.executeQuery();
			
			//모든 테이블의 데이터를 페이징

			
		} catch (Exception e) {
				e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null)conn.close();
				
			}catch (Exception e) {
			}
		}
	}
	/*
	 * 
	 * CREATE OR REPLACE PROCEDURE studentDelete(
 pHakbun student.hakbun%TYPE
)
IS 
BEGIN
    DELETE FROM student
    WHERE hakbun=pHakbun;
    COMMIT;
END;
/
	 */
	
	public void studentDelete(int hakbun)
	{
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			//함수 호출만 하면 된다
			String sql="{CALL studentDelete(?)}";
			cs=conn.prepareCall(sql);//ERP => 메뉴얼
			// ?에 값을 채운 다음 실행
			cs.setInt(1, hakbun);
			//실행 요청
			cs.executeQuery();
; 		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null)conn.close();
				
			}catch (Exception e) {
			}
		}
	}
	
	/*
	 * CREATE OR REPLACE PROCEDURE studentDetailData(
    pHakbun student.hakbun%TYPE,
    pName  out student.name%TYPE,
    pKor out student.kor%TYPE,
    pEng out student.eng%TYPE,
    pMath out student.math%TYPE
)
is 
begin
    SELECT name,kor,eng,math INTO pName,pKor,pEng,pMath
    FROM student
    WHERE hakbun=pHakbun;
end;
/
	 * 
	 */
	public StudentVO studentDetail (int hakbun)
	{	
		StudentVO vo=new StudentVO();
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL studentDetailData(?,?,?,?,?)}";
			cs=conn.prepareCall(sql);
			//?에 값을 채운다
			cs.setInt(1, hakbun);
			// OUT 변수일 경우에 => 메모리에 저장한다
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.INTEGER);
			cs.registerOutParameter(4, OracleTypes.INTEGER);
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			//실행 요청
			cs.executeQuery();
			vo.setName(cs.getString(2));
			vo.setKor(cs.getInt(3));
			vo.setEng(cs.getInt(3));
			vo.setMath(cs.getInt(5));
			vo.setHakbun(hakbun);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null)conn.close();
				
			}catch (Exception e) {
			}
		}
		return vo;
	}
	/*
	 * CREATE OR REPLACE PROCEDURE stundentListData(
    pResult OUT SYS_REFCURSOR    
)
is 
begin
 OPEN pResult FOR
    SELECT * FROM student;
end;
	 * 
	 */
	
	public List<StudentVO> studentListData()
	{
		List<StudentVO> list=new ArrayList<StudentVO>();
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
			String sql="{CALL stundentListData(?)}";
			cs=conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			/*
			 * NUMBER => INTEGER/DOUBLE
			 * VARCHAR2,CHAR => VARCHAR
			 * CURSOR => CURSOR 
			 */
			cs.executeQuery();
			//결과값을 받는다
			ResultSet rs=(ResultSet)cs.getObject(1);
			// Cursoe => ResultSet변환
			while(rs.next())
			{
				StudentVO vo=new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				list.add(vo);
			}
					
		} catch (Exception e) {
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null)conn.close();
				
			}catch (Exception e) {
			}
		}
		return list;
	}

// 중복 코드가 있는 여부 => 공통 모듈(메소드화 처리, 클래스화)

}
