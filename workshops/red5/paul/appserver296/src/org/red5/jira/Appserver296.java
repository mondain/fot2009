package org.red5.jira;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.server.net.remoting.RemotingClient;
import org.slf4j.Logger;

/**
 * Servlet implementation class 
 */
public class Appserver296 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static Logger log;
	
	static {
		log = Application.loggerContext.getLogger(Appserver296.class);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Appserver296() {
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
		response.getWriter().print(test(request.getParameter("param")));
	}
	
	private boolean test(String param) {
		boolean result = false;
		
		Object[] params = null;
		
		if (param != null) {
			params = new Object[]{param};
		}
		
        try { 
        	RemotingClient client = new RemotingClient(Application.getUrl()); 
        	log.info("Client: {}", client);
        	log.info("Remoting handler response: {}", client.invokeMethod("remotinghandler.test", params).getClass()); 

        	result = true;
        } catch (Exception e) { 
        	log.error("Client call failed", e);
        } 
		
        return result;
	}
}
