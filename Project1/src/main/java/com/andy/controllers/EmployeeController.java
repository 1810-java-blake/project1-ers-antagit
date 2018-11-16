package com.andy.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.andy.dto.Credential;
import com.andy.model.Employee;
import com.andy.services.IEmployeeService;
import com.andy.util.ResponseMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EmployeeController {
	private Logger log = Logger.getRootLogger();
	private IEmployeeService employeeService = IEmployeeService.currentImplamentation;
	private ObjectMapper om = new ObjectMapper();

	void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String method = req.getMethod();
		switch (method) {
		case "GET":
			processGet(req, resp);
			break;
		case "POST":
			processPost(req, resp);
			break;
		case "OPTIONS":
			return;
		default:
			resp.setStatus(404);
			break;
		}
	}

	private void processGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {		
	}
	
	// getting post request with credentials
	private void processPost(HttpServletRequest req, HttpServletResponse resp) throws JsonParseException, JsonMappingException, IOException {
		String uri = req.getRequestURI();
		String context = "project1";
		uri = uri.substring(context.length() + 2, uri.length());
		 if ("login".equals(uri)) {
			 //reading in the values of the request that is of credentail objects and creating credential class with read in value IE username and password
			Credential cred = om.readValue(req.getReader(), Credential.class);
			// login will return false if user input wrong credentials or employee was not found in database
			if(!employeeService.login(cred, req.getSession())) {
				//return forbidden status code
				resp.setStatus(403);
				return;
			}
			else if(employeeService.login(cred, req.getSession())){
				Employee emp = employeeService.findByUserName((String)req.getSession().getAttribute("username"));
				Map<String, String> tMap = new HashMap<>();
				tMap.put("role", emp.getRole());
				ResponseMapper.convertAndAttach(tMap, resp);
				
				return;
			}
		} else {
			//return if page was not found
			resp.setStatus(404);
			return;
		}
	}
}
