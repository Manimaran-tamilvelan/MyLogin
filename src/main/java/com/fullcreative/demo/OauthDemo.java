package com.fullcreative.demo;

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
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.oauth.OAuthService;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.User;

@Controller
public class OauthDemo {

	@RequestMapping("/glogin")
	public ModelAndView testOauth(HttpServletRequest req, HttpServletResponse res) throws OAuthRequestException {

		String userName = req.getParameter("userName");
		String userId = req.getParameter("id");
		String userMail = req.getParameter("eMail");

		// System.out.println(a == null);

		// System.out.println(userName+"--"+userId+"=="+userMail);

		ModelAndView modelView = new ModelAndView();

		HttpSession sess1 = req.getSession();
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// entity name, keyname
		Key key = KeyFactory.createKey("gUsers", userId + " " + userMail);
		Entity authUser = null;
	
		/**
		 * try { authUser = datastore.get(key); System.out.println("Old"); }
		 * catch(EntityNotFoundException e) { //write code to create it!
		 * //authUser.setProperty("userName", userName);
		 * //authUser.setProperty("mailID", userMail); authUser.setProperty("tasks",
		 * ""); System.out.println("New"); datastore.put(authUser); }
		 */

		String userKey = userId + " " + userMail;
		// req.setAttribute("taskacc", userKey);

		req.setAttribute("taskacc", userKey);

		
		sess1.setAttribute("userName", userName);
		sess1.setAttribute("taskacc1", userKey);

		modelView.setViewName("welcome.jsp");
		// modelView.addObject("showUserDetail", finalAuthUser);

		return modelView;

	}

}
