package org.gregoire.demo;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static Logger log;
	
	static {
		log = Application.loggerContext.getLogger(MyServlet.class);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print(getParameter(request.getParameter("paramName")));
	}
	
	private String getParameter(String paramName) {
		//Get the servlet context
		ServletContext ctx = getServletContext();
		log.info("Got the servlet context: {}", ctx);
		
		//Grab a reference to the application context
		ApplicationContext appCtx = (ApplicationContext) ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		log.info("Got the application context: {}", appCtx);
		
		//Get the bean holding the parameter
		MyBean bean = (MyBean) appCtx.getBean("mybean");
		String param = bean.getParameter();
		log.info("Got the parameter - {} = {}", paramName, param);
		
		return param;
	}
}
