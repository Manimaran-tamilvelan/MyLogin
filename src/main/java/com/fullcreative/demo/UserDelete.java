package com.fullcreative.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * Servlet implementation class delete
 */
@Controller
public class UserDelete extends HttpServlet {

	@RequestMapping("/delete")
	public ModelAndView deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");

		// int key1 = Integer.valueOf(userID);

		deleteUserDetail(userID, password);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		
		Query q = new Query("Users");
		Map<String, List<String>> finalUsers = new LinkedHashMap();

		PreparedQuery pq = datastore.prepare(q);

		for (Entity user : pq.asIterable()) {

			List<String> temp = new ArrayList();
			String userNamePassword = user.getKey().getName();
			String[] userSplit = userNamePassword.split(" ");
			//System.out.println(userSplit[0]);
			String name = (String) user.getProperty("mailID");
			//System.out.println(name);

			temp.add((String) user.getProperty("password"));
			temp.add((String) user.getProperty("mailID"));
			temp.add((String) user.getProperty("mobileNo"));
			temp.add((String) user.getProperty("dOB"));

			finalUsers.put(userSplit[0], temp);

			
		}

		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("adminResult.jsp");
		modelView.addObject("allUsers", finalUsers);
		return modelView;
		// request.setAttribute("allUsers", );
		// request.getRequestDispatcher("adminResult.jsp").forward(request, response);
	}
	
	public boolean deleteUserDetail(String userID, String password) {
		
		Key key = KeyFactory.createKey("Users", userID + " " + password);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		datastore.delete(key);
		
		return true;
		
	}

}
