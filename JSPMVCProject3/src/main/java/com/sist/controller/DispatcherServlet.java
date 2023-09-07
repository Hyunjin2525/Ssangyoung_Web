package com.sist.controller;

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

import com.sist.dao.Model;
import com.sist.model.CategoryModel;
import com.sist.model.FoodDetailModel;
import com.sist.model.FoodListModel;


@WebServlet("*.do")
//main.do , category.do food_list.do //알아보기 위해서 //*=어떠한 단어가 들어와도 된다
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String,Model> clsMap=new HashMap<String,Model>();
	public void init(ServletConfig config) throws ServletException {
		clsMap.put("category.do", new CategoryModel());
		clsMap.put("food_list.do", new FoodListModel());
		clsMap.put("food_detail.do", new FoodDetailModel());
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
