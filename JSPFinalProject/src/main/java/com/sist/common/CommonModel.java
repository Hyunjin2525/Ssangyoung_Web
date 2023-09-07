package com.sist.common;

import javax.servlet.http.HttpServletRequest;
import com.sist.manager.*;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;
//=> Spring (공통모듈 => AOP)
// Spring => DI / AOP (사용법 : XML,Annotation)
public class CommonModel {
	public static void commonRequestData(HttpServletRequest request)
	{
		//footer
		FoodDAO dao=FoodDAO.newInstance();
		// => 공지사항 => 최신뉴스
		List<NewsVO> nlist=NewsManager.newsSearchData("맛집");
		for(NewsVO vo:nlist)
		{
			String title=vo.getTitle();
			if(title.length()>15)
			{
				title=title.substring(0,14)+"...";
				vo.setTitle(title);
			}
			vo.setTitle(title);
		}
		request.setAttribute("nlist", nlist);
		// => 방문 맛집
		List<FoodVO> flist=dao.foodTop7();
		request.setAttribute("flist", flist);
	}
}
