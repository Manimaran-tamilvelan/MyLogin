package com.fullcreative.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.cloud.datastore.Transaction;

/**
 * Servlet implementation class Register
 */
@Controller
public class Register extends HttpServlet {

	@RequestMapping("/register")
	public ModelAndView register(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String mailID = req.getParameter("mailID");
		String dOB = req.getParameter("dob");
		String mobileNo = req.getParameter("mobileNo");

		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		ModelAndView modelView = new ModelAndView();

		HttpSession session = req.getSession();

		MemcacheService mcs = MemcacheServiceFactory.getMemcacheService();
		Object check = mcs.get(userName);

		//checking memcache
		if (check != null) {

			modelView.setViewName("register.jsp");
			String errorMessage = "Username already Details";
			modelView.addObject("message", errorMessage);

			return modelView;

		}

		Entity e = new Entity("Users", userName + " " + password);
		
		
		
		try {

			if (session.getAttribute("userNameModify") != null) {
				String userNameCheck = (String) session.getAttribute("userNameModify");
				String passwordCheck = (String) session.getAttribute("userPwdConfirm");

				e = new Entity("Users", userNameCheck + " " + passwordCheck);
				session.removeAttribute("userNameModify");

				session.invalidate();

			}
		} finally {

		}

		// e.setProperty("userName", userName);
		e.setProperty("password", password);
		e.setProperty("mailID", mailID);
		e.setProperty("dOB", dOB);
		e.setProperty("mobileNo", mobileNo);

		datastore.put(e);

		List<String> userDetails = new ArrayList();
		userDetails.add(password);
		userDetails.add(mailID);
		userDetails.add(dOB);
		userDetails.add(mobileNo);

		mcs.put(userName, userDetails);
		// System.out.println(mcs.get(userName));
		
		
		
		Queue queue = QueueFactory.getDefaultQueue();

		queue.add(TaskOptions.Builder.withUrl("/worker").param("key", userName));
		
		//res.sendRedirect("/");
		
		
		
		String display = "Registered Successfully! You can Login now";

		req.setAttribute("message", display);

		modelView.setViewName("login.jsp");

		modelView.addObject("message", display);

		return modelView;

	}

}
