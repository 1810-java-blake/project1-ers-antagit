package com.andy.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DispatcherServlet extends HttpServlet {
	private Logger log = Logger.getRootLogger();
	private ReimbursementController reimbControl = new ReimbursementController();
	private EmployeeController employcontrol = new EmployeeController();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
		
		String uri = req.getRequestURI();
		String context = "Project1";
		uri = uri.substring(context.length() + 2, uri.length());
		if (uri.startsWith("login")) {
			employcontrol.process(req, resp);
		}
		else if(!req.getMethod().equals("OPTIONS")) {
			try {
				if(!req.getSession().getAttribute("role").equals("null")) {
						reimbControl.process(req, resp);
				}

			}
			catch(NullPointerException e) {
				e.printStackTrace();
				resp.setStatus(404);
			}
		}
	}
}
