package com.fullcreative.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * Servlet implementation class Login
 */
@Controller
public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		// String usrpass = usr+""+password;

		
		ModelAndView modelView = new ModelAndView();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("Users", userName + " " + password);
		Entity authUser;
		try {

			if (userName.equals("admin") && password.equals("admin")) {

				HttpSession sess = req.getSession();
				sess.setAttribute("userName", userName);

				Query q = new Query("Users");
				Map<String, List<String>> finalUsers = new LinkedHashMap();

				PreparedQuery pq = datastore.prepare(q);

				for (Entity user : pq.asIterable()) {

					List<String> temp = new ArrayList();
					String userNamePassword = user.getKey().getName();
					String[] userSplit = userNamePassword.split(" ");
					// System.out.println(userSplit[0]);
					String name = (String) user.getProperty("mailID");
					// System.out.println(name);

					temp.add((String) user.getProperty("password"));
					temp.add((String) user.getProperty("mailID"));
					temp.add((String) user.getProperty("mobileNo"));
					temp.add((String) user.getProperty("dOB"));

					finalUsers.put(userSplit[0], temp);

				}

				modelView.setViewName("adminResult.jsp");
				modelView.addObject("allUsers", finalUsers);
				return modelView;

			}

			//MemcacheService mcs = MemcacheServiceFactory.getMemcacheService();
			//Object expected = mcs.get(userName);
	
			
			String userKey= userName+" "+password;
			req.setAttribute("taskacc", userKey);
			
			HttpSession sess1 = req.getSession();
			sess1.setAttribute("taskacc1", userKey);

			/**
			if (expected != null) {
				// mcs.get(userName);

				List<String> finalUsersCache = new ArrayList<String>();
				finalUsersCache.add(userName);
				finalUsersCache.addAll((List<String>) mcs.get(userName));
				// System.out.println(finalUsers);
				System.out.println("memcache");

				HttpSession sess = req.getSession();
				sess.setAttribute("userName", userName);
				
				modelView.setViewName("welcome.jsp");
				
				modelView.addObject("showUserDetail", finalUsersCache);
				return modelView;

			}

			System.out.println("not from memcache");
	*/
			authUser = datastore.get(key);
			// System.out.println();
			String userNameAndPassword = authUser.getKey().getName();
			String[] split = userNameAndPassword.split(" ");
			// System.out.println(split[0]);
			// System.out.println(split[1]);
			List<String> finalAuthUser = new ArrayList();
			// System.out.println(e.getProperties());
			finalAuthUser.add(split[0]);
			int temp = 0;
			for (Map.Entry e1 : authUser.getProperties().entrySet()) {

				do {
					HttpSession sess = req.getSession();
					sess.setAttribute("userName", userName);
					temp++;
				} while (temp < 0);

				finalAuthUser.add((String) e1.getValue());

				modelView.setViewName("welcome.jsp");
				modelView.addObject("showUserDetail", finalAuthUser);

			}
			return modelView;

		} catch (EntityNotFoundException e1) {
			modelView.setViewName("login.jsp");
			String errorMessage = "Incorrect Details";
			modelView.addObject("message", errorMessage);
		}
		return modelView;

	}

}
