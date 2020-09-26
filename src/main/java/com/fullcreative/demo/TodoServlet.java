package com.fullcreative.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Controller
public class TodoServlet {

	@RequestMapping("/todoservlet")
	public void AddTodo(HttpServletRequest req, HttpServletResponse res) throws IOException, EntityNotFoundException {

		String newTask = req.getParameter("taskName");
		//String userName = req.getParameter("userName");
		//String password = req.getParameter("password");

		HttpSession sess1 = req.getSession();
		String credentials = (String) sess1.getAttribute("taskacc1");
		
		String[] split = credentials.split(" ");
		
		String userName = split[0];
		String password = split[1];
		
		
		 //System.out.println(userName+"--"+password+"--"+newTask);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("UsersTask", userName + " " + password);
		Entity authUser = datastore.get(key);

		String currentTasks = null;

		for (Map.Entry e : authUser.getProperties().entrySet()) {
			currentTasks = (String) e.getValue();
		}

		if (currentTasks.equals("")) {
			currentTasks = newTask;
			// System.out.println(currentTasks);

		} else {
			// System.out.println(currentTasks);
			currentTasks = currentTasks.concat("," + newTask);
			// System.out.println(currentTasks);

		}

		Entity etask = new Entity("UsersTask", userName + " " + password);
		etask.setProperty("tasks", currentTasks);
		datastore.put(etask);

		// Entity etask = new Entity("UsersTask", key);
		// etask.setProperty("tasks", currentTasks);
		// datastore.put(etask);

		res.getWriter().println(newTask);

	}
	
	

	@RequestMapping("/deleteTodo")
	public void deleteTodo(HttpServletRequest req, HttpServletResponse res)
			throws IOException, EntityNotFoundException {

		String deleteTask = req.getParameter("taskName");
		HttpSession sess1 = req.getSession();
		String credentials = (String) sess1.getAttribute("taskacc1");
		
		String[] split = credentials.split(" ");
		
		String userName = split[0];
		String password = split[1];
		
		
		// System.out.println(userName+"--"+password+"--"+deleteTask);

		// System.out.println(deleteTask+"--"+userName+"--"+password);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("UsersTask", userName + " " + password);
		Entity authUser = datastore.get(key);

		String currentTasks = null;

		for (Map.Entry e : authUser.getProperties().entrySet()) {
			currentTasks = (String) e.getValue();
		}
		

		String[] allTasks = currentTasks.split(",");
		//String[] anotherTasks = new String[allTasks.length-1];
		ArrayList<String> anotherTasks = new ArrayList<String>();
		
		//System.out.println(currentTasks);
		

		int temp = 0;
		for(int i =0;i<allTasks.length;i++) {
			if(allTasks[i].trim().equals(deleteTask)) {
				System.out.println("Matched");
			}
			else {
				//anotherTasks[temp]=allTasks[i];
				anotherTasks.add(allTasks[i]);
				temp++;
			}
		}
		
		String formattedString = anotherTasks.toString().replace("[", "").replace("]", "");
		/**
		ArrayList<String> finalList = new ArrayList<String>();

		for (int i = 0; i < allTasks.length; i++) {
			ll:
			if (allTasks[i].equals(deleteTask)) {
				break ll;
			}
			finalList.add(allTasks[i]);

		}

		String formattedString = finalList.toString().replace("[", "").replace("]", "");
*/
		// System.out.println(formattedString);

		
		if (currentTasks.trim().equals(deleteTask)) {
			Entity etask = new Entity("UsersTask", userName + " " + password);
			etask.setProperty("tasks", "");
			//etask.removeProperty("tasks");
			datastore.put(etask);
			res.getWriter().println("Empty");
		}
		
		else {
		Entity etask = new Entity("UsersTask", userName + " " + password);
		etask.setProperty("tasks", formattedString);
		datastore.put(etask);
		res.getWriter().println("Success");
		}
		

	}

}
