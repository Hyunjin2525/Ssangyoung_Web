package com.sist.model;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.AdminDAO;
import com.sist.dao.NoticeDAO;
import com.sist.dao.ReplyBoardDAO;
import com.sist.dao.ReserveDAO;
import com.sist.vo.NoticeVO;
import com.sist.vo.ReplyBoardVO;
import com.sist.vo.ReplyVO;
import com.sist.vo.ReserveVO;

public class AdminPageModel {
	@RequestMapping("adminpage/admin_main.do")
	public String admin_main(HttpServletRequest request,HttpServletResponse response)
	{
		request.setAttribute("adminpage_jsp", "../adminpage/admin_reserve.jsp");
		request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("adminpage/admin_reserve.do")
	public String mypage_reserve(HttpServletRequest request,HttpServletResponse response)
	{
		AdminDAO dao=AdminDAO.newInstance();
		List<ReserveVO> list=dao.reserveAdminData();
		request.setAttribute("list", list);
		request.setAttribute("adminpage_jsp","../adminpage/admin_reserve.jsp");
		request.setAttribute("main_jsp","../adminpage/admin_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/admin_reserve_ok.do")
	public String admin_ok(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		ReserveDAO dao=ReserveDAO.newInstance();
		dao.reserveOK(Integer.parseInt(no));
		
		return "redirect:../adminpage/admin_reserve.do";
	}
	
	//공지사항
	@RequestMapping("adminpage/admin_notice_list.do")
	// 이동 => request전송(return .jsp)
	public String admin_notice_list(HttpServletRequest request,HttpServletResponse response)
	{	
		String page=request.getParameter("page");
		  if(page==null)
			  page="1";
		  int curpage=Integer.parseInt(page);
		  NoticeDAO dao=NoticeDAO.newInstance();
		  List<NoticeVO> list=dao.noticeListDate(curpage);
		  int totalpage=dao.noticeTotalPage();
		  
		  String[] msg={"","일반공지","이벤트공지","맛집공지","여행공지","레시피공지"};
		  for(NoticeVO vo:list)
		  {
			  vo.setNotice_type(msg[vo.getType()]);
		  }
		  request.setAttribute("list", list);
		  request.setAttribute("curpage", curpage);
		  request.setAttribute("totalpage", totalpage);
		
		  request.setAttribute("adminpage_jsp","../adminpage/admin_notice_list.jsp");
		  request.setAttribute("main_jsp","../adminpage/admin_main.jsp");
		  CommonModel.commonRequestData(request);
		  return "../main/main.jsp";
	}
	@RequestMapping("adminpage/notice_insert.do")
	public String notice_insert(HttpServletRequest request,HttpServletResponse response)
	{
		 request.setAttribute("adminpage_jsp","../adminpage/notice_insert.jsp");
		  request.setAttribute("main_jsp","../adminpage/admin_main.jsp");
		  CommonModel.commonRequestData(request);
		  return "../main/main.jsp";
	}
	@RequestMapping("adminpage/notice_insert_ok.do")
	public String notice_insert_ok(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String type=request.getParameter("type");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		  
		NoticeVO vo=new NoticeVO();
		vo.setType(Integer.parseInt(type));
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setId(id);
		vo.setName(name);
		
		NoticeDAO dao=NoticeDAO.newInstance();
		dao.noticeInsert(vo);
		return "redirect:../adminpage/admin_notice_list.do";
	}
	@RequestMapping("adminpage/notice_delete.do")
	public String admin_notice_delete(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		
		NoticeDAO dao=NoticeDAO.newInstance();
		dao.noticeDelete(Integer.parseInt(no));
		return "redirect:../adminpage/admin_notice_list.do";
	}
	
	@RequestMapping("adminpage/notice_update.do")
	public String admin_notice_update(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		
		NoticeDAO dao=NoticeDAO.newInstance();
		NoticeVO vo=dao.noticeUpdateData(Integer.parseInt(no));
		request.setAttribute("vo", vo);
		request.setAttribute("adminpage_jsp","../adminpage/notice_update.jsp");
		request.setAttribute("main_jsp","../adminpage/admin_main.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/notice_update_ok.do")
	public String notice_update_ok(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String type=request.getParameter("type");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String no=request.getParameter("no");
	
		NoticeVO vo=new NoticeVO();
		vo.setType(Integer.parseInt(type));
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setNo(Integer.parseInt(no));
		
		NoticeDAO dao=NoticeDAO.newInstance();
		dao.noticeUpdate(vo);

		return "redirect:../adminpage/admin_notice_list.do";
	}
	
	@RequestMapping("adminpage/replyboard_list.do")
	public String adminpage_reply_list(HttpServletRequest request,HttpServletResponse response)
	{
		
		String page=request.getParameter("page");
		if(page==null)
			page="1";
		int curpage=Integer.parseInt(page);
		ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
		List<ReplyBoardVO> list=dao.replyBoardAdminListData(curpage);
		int total=dao.replyBoardAdminTotal();
		
		  request.setAttribute("list", list);
		  request.setAttribute("curpage", curpage);
		  request.setAttribute("totalpage", total);
		request.setAttribute("adminpage_jsp", "../adminpage/replyboard_list.jsp");
		request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/replyboard_insert.do")
	public String adminpage_reply_insert(HttpServletRequest request,HttpServletResponse response)
	{
		String no=request.getParameter("no");
		request.setAttribute("no", no);
		request.setAttribute("adminpage_jsp", "../adminpage/replyboard_insert.jsp");
		request.setAttribute("main_jsp", "../adminpage/admin_main.jsp");
		return "../main/main.jsp";
	}
	@RequestMapping("adminpage/replyboard_insert_ok.do")
	public String adminpage_replyboard_insert_ok(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		String pno=request.getParameter("pno");
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		String name=(String)session.getAttribute("name");
		
		ReplyBoardVO vo=new ReplyBoardVO();
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setId(id);
		vo.setName(name);
		
		ReplyBoardDAO dao=ReplyBoardDAO.newInstance();
		dao.replyBoardAdminInsert(Integer.parseInt(pno), vo);
		return "redirect:../adminpage/replyboard_list.do";
	}
}
