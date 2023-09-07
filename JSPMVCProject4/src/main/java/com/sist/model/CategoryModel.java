package com.sist.model;

import javax.servlet.http.HttpServletRequest;

import com.sist.dao.*;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
// 			request   request
// Controller = Model = JSP
public class CategoryModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("CategoryModel:"+request);
		FoodDAO dao=FoodDAO.newInstance();
		List<CategoryVO> list=dao.foodCategoryData();
		// request : DispatcherServlet
		request.setAttribute("list", list);
		return "food/category.jsp";
	}

}
