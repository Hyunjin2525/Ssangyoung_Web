package com.sist.model;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.html.HTMLHtmlElement;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.CartDAO;
import com.sist.dao.FoodJjimLikeDAO;
import com.sist.dao.ReserveDAO;
import com.sist.vo.CartVO;
import com.sist.vo.FoodJJimVO;
import com.sist.vo.ReserveVO;

public class MyPageModel {
	@RequestMapping("mypage/mypage_main.do")
	public String mypage_main(HttpServletRequest request,HttpServletResponse response)
	{
		request.setAttribute("mypage_jsp", "../mypage/mypage_reserve.jsp");
		request.setAttribute("main_jsp", "../mypage/mypage_main.jsp");
		return "../main/main.jsp";
	}
	@RequestMapping("mypage/mypage_reserve.do")
	public String mypage_reserve(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		ReserveDAO dao=ReserveDAO.newInstance();
		List<ReserveVO> list=dao.reserveInfoData(id);
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp","../mypage/mypage_reserve.jsp");
		request.setAttribute("main_jsp","../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}

	@RequestMapping("mypage/mypage_jjim_list.do")
	public String jjim_list(HttpServletRequest request,HttpServletResponse response)
	{
		
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		FoodJjimLikeDAO dao=FoodJjimLikeDAO.newInstance();
		List<FoodJJimVO> list=dao.foodJjimListData(id);
		
		request.setAttribute("list", list);
		
		request.setAttribute("mypage_jsp","../mypage/mypage_jjim_list.jsp");
		request.setAttribute("main_jsp","../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("mypage/mypage_cart.do")
	public String mypage_cart(HttpServletRequest request,HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		CartDAO dao=CartDAO.newInstance();
		List<CartVO> list=dao.mypageCartListData(id);
	
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp","../mypage/mypage_cart.jsp");
		request.setAttribute("main_jsp","../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("mypage/mypage_buy.do")
	public String mypage_buy(HttpServletRequest request,HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		CartDAO dao=CartDAO.newInstance();
		List<CartVO> list=dao.mypageBuyListData(id);
		request.setAttribute("list", list);
		request.setAttribute("mypage_jsp","../mypage/mypage_buy.jsp");
		request.setAttribute("main_jsp","../mypage/mypage_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("mypage/cart_cancel.do")
	public String mypage_cart_cancel(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		CartDAO dao=CartDAO.newInstance();
		dao.cartCancel(Integer.parseInt(no));
		return"redirect:../mypage/mypage_cart.do";
	}
}