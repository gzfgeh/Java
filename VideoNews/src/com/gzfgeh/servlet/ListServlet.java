package com.gzfgeh.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gzfgeh.domain.News;
import com.gzfgeh.service.VideoNewsService;
import com.gzfgeh.service.impl.VideoNewsServiceImpl;

/**
 * Servlet implementation class ListServlet
 */
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private VideoNewsService service = new VideoNewsServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<News> videos = service.getLastNews();
		
		String format = request.getParameter("format");
//		if (format.equal("json")){
			StringBuilder builder = new StringBuilder();
			builder.append('[');
			for (News news : videos) {
				builder.append('{');
				builder.append("id:").append(news.getId()).append(',');
				builder.append("title:").append('\"').append(news.getTitle()).append("\",");
				builder.append("timelength:").append(news.getTimelength());
				builder.append("},");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(']');
			request.setAttribute("json", builder.toString());
			request.getRequestDispatcher("/WEB-INF/page/jsonvideosnews.jsp").forward(request, response);
			
//		}else{
//			request.setAttribute("videos", videos);
//			request.getRequestDispatcher("/WEB-INF/page/videosnews.jsp").forward(request, response);
//		
//		}
	}

}
