package com.sist.controller;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.model.*;
import com.sist.model.CategoryModel;
import com.sist.model.FoodDetailModel;
import com.sist.model.FoodListModel;
import javax.xml.parsers.*;
import org.w3c.dom.*;

@WebServlet("*.do")
//main.do , category.do food_list.do //알아보기 위해서 //*=어떠한 단어가 들어와도 된다
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,Model> clsMap=new HashMap<String,Model>();
	public void init(ServletConfig config) throws ServletException {
		try {
			File file=new File("C:\\webDev\\webStudy\\JSPMVCProject4\\src\\main\\webapp\\WEB-INF\\cmd.xml");
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=dbf.newDocumentBuilder();
			//XML 파서기
			Document doc=db.parse(file);
			Element beans=doc.getDocumentElement();//루트
			NodeList list=beans.getElementsByTagName("bean");
			for(int i=0;i<list.getLength();i++)
			{
				Element bean=(Element)list.item(i);
				String id=bean.getAttribute("id");
				String cls=bean.getAttribute("class");
				
				Class clsName=Class.forName(cls);
				Object obj=clsName.getDeclaredConstructor().newInstance();
				
				clsMap.put(id, (Model)obj);
			}
		} catch (Exception e) {
			
		}
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd=request.getRequestURI();
		cmd=cmd.substring(cmd.lastIndexOf("/")+1);
		Model model=clsMap.get(cmd);
		System.out.println("Controller:"+request); //주소가 같기 때문에 controller에 값을 채운다
		String jsp=model.handlerRequest(request, response);
		RequestDispatcher rd=request.getRequestDispatcher(jsp);
		rd.forward(request, response);
	}

}
