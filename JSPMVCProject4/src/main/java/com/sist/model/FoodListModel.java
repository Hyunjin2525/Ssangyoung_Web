package com.sist.model;

import javax.servlet.http.HttpServletRequest;

import com.sist.dao.*;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
public class FoodListModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {

		String cno=request.getParameter("cno");
		FoodDAO dao=FoodDAO.newInstance();
	 	CategoryVO cvo=dao.foodCategoryInfoData(Integer.parseInt(cno));
	 	List<FoodVO> list=dao.foodCategoryListData(Integer.parseInt(cno));
	 	request.setAttribute("cvo", cvo);
	 	request.setAttribute("list", list);
		return "food/food_list.jsp";
	}


}
