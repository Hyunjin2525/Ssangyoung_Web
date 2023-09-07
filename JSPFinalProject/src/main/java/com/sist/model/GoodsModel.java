package com.sist.model;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;
public class GoodsModel {
	@RequestMapping("goods/goods_list.do")
	public String goods_list(HttpServletRequest request, HttpServletResponse response)
	{
		String page=request.getParameter("page");
		if(page==null)
		{
			page="1";
		}
		String type=request.getParameter("type");
		if(type==null)
		{
			type="1";
		}
		int curpage=Integer.parseInt(page);
		GoodsDAO dao=GoodsDAO.newInstance();
		
		List<GoodsVO> list=dao.goodsListData(curpage, Integer.parseInt(type));
		
		int total=dao.goodsTotalPage(Integer.parseInt(type));
		
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("type", type);
		request.setAttribute("totalpage", total);
		String[] msg= {"","전체 상품 목록","베스트 상품 목록","신상품 목록","특가 상품"};
		request.setAttribute("msg", msg[Integer.parseInt(type)]);
		request.setAttribute("main_jsp", "../goods/goods_list.jsp");
		request.setAttribute("type", type);
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}

	@RequestMapping("goods/goods_detail.before.do")
	public String goods_detail_before(HttpServletRequest request, HttpServletResponse response)
	{
		//no
		String no=request.getParameter("no");
		String type=request.getParameter("type");
		// 전송값 => 상세보기 : primary key
		// 검색 => 검색어
		// 로그인 (id,pwd) , ....
		//Cookie에 저장
		Cookie cookie=new Cookie("goods_"+no, no);
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		
		return "redirect:../goods/goods_detail.do?no="+no+"&type="+type; //redirect = 현 메소드 수행 후 그쪽 페이지를 다시 가서 수행 하라는 뜻
		// sendRedirect() => 기존에 제작된 메소드 (메소드를 호출)
		// => 반복 제거 = getConnection과 같은
		// forward => 새로운 데이터를 전송
		// sendRedirect => _ok.do , _before.do =>
		
	}
	
	@RequestMapping("goods/goods_detail.do")
	public String goods_detail(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		String type=request.getParameter("type");
		
		GoodsDAO dao=GoodsDAO.newInstance();
		GoodsVO vo=dao.goodsDetailData(Integer.parseInt(no), Integer.parseInt(type));
		String price=vo.getGoods_price();
		int p=Integer.parseInt(price.replaceAll("[^0-9]", ""));
		
		request.setAttribute("price", p);
		
		
		request.setAttribute("vo",vo);
		request.setAttribute("type",type);
		
		
		//데이터 => goods_detail
		request.setAttribute("main_jsp", "../goods/goods_detail.jsp");
		CommonModel.commonRequestData(request);
		return"../main/main.jsp";
	}
}
