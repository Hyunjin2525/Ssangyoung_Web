package com.sist.vo;
// Beans => 데이터를 모아서 관리 (전송)
// private 변수, getter/setter => 캡슐화 방식(데이터 보호 목적)
/*
 *  빈즈 연결
 *  ------- jsp 액션 태그
 *  	<jsp:useBean>
 *  	<jsp:setProperty>
 *  	<jsp:getProperty>
 *  	<jsp:param>
 *  	***<jsp:include>
 *  	<jsp:forward>
 */
import java.util.*;

/*
 *  NO                                        NOT NULL NUMBER
 NAME                                      NOT NULL VARCHAR2(51)
 SUBJECT                                   NOT NULL VARCHAR2(1000)
 CONTENT                                   NOT NULL CLOB
 PWD                                       NOT NULL VARCHAR2(10)
 REGDATE                                            DATE
 HIT                                                NUMBER
 FILENAME                                           VARCHAR2(260)
 FILESIZE                                           NUMBER
 
  ~Bean => model1 (jsp)
  ~VO => Spring
  ~DTO => Mybatis
 * 
 */
public class DataBoardVO {
	private int no,hit,fileSize;
	private String name,subject,content,pwd,fileName,dbday;
	private Date regdate;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getDbday() {
		return dbday;
	}
	public void setDbday(String dbday) {
		this.dbday = dbday;
	}
	
	
	
}
