package com.fullcreative.demo;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Worker extends HttpServlet {

	private static final Logger log = Logger.getLogger(Worker.class.getName());

	@RequestMapping("/worker")
	public void worker(HttpServletRequest req, HttpServletResponse res) {

		String userName = req.getParameter("key");

		log.info("Sending Mail to: " + userName);

	}

}
