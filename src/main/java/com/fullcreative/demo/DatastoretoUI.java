package com.fullcreative.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Controller
public class DatastoretoUI {

	@RequestMapping("/oauthdatastore")
	public ModelAndView datastoreToUI(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession sess = req.getSession();
		String id = (String) sess.getAttribute("currentid");
		String eMail = (String) sess.getAttribute("currentemail");

		ModelAndView modelView = new ModelAndView();

		if (id == null && eMail == null) {
			modelView.setViewName("login.jsp");

			return modelView;

		}

		HttpSession sess1 = req.getSession();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		// entity name, keyname
		Key key = KeyFactory.createKey("gUsers", id + " " + eMail);
		Entity authUser = null;
		/**
		 * try { authUser = datastore.get(key); System.out.println("Old"); }
		 * catch(EntityNotFoundException e) { //write code to create it!
		 * //authUser.setProperty("userName", userName);
		 * //authUser.setProperty("mailID", userMail); authUser.setProperty("tasks",
		 * ""); System.out.println("New"); datastore.put(authUser); }
		 */

		String userKey = id + " " + eMail;
		// req.setAttribute("taskacc", userKey);

		req.setAttribute("taskacc", userKey);

		sess1.setAttribute("userName", eMail);
		sess1.setAttribute("taskacc1", userKey);

		modelView.setViewName("welcome.jsp");
		// modelView.addObject("showUserDetail", finalAuthUser);

		return modelView;

	}

	@RequestMapping("/calendarTasks")
	public void fetchFromCalendar(HttpServletRequest req, HttpServletResponse res)
			throws IOException, EntityNotFoundException {

		HttpSession session = req.getSession();
		String mailId = (String) session.getAttribute("userName");

		// System.out.println(mailId);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey("RefToken", mailId);

		Entity getEntity = datastore.get(key);

		String refreshToken = null;

		for (Map.Entry entry : getEntity.getProperties().entrySet()) {

			refreshToken = entry.getValue().toString();
		}
		// System.out.println(refreshToken);

		final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com";
		final String GRANT_TYPE = "refresh_token";
		final String REFRESH_TOKEN = refreshToken;
		final String CLIENT_ID = "628485492305-clc02nguilt3cn9aimatqolcgd8ivc7c.apps.googleusercontent.com";
		final String CLIENT_SECRET = "****";

		// System.out.println(authorizationCode);

		HttpPost httpPost = new HttpPost(
				TOKEN_ENDPOINT + "/token?client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.name())
						+ "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8.name())
						+ "&refresh_token=" + URLEncoder.encode(REFRESH_TOKEN, StandardCharsets.UTF_8.name())
						+ "&grant_type=" + URLEncoder.encode(GRANT_TYPE, StandardCharsets.UTF_8.name()));

		httpPost.setHeader("Host", "oauth2.googleapis.com");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		// System.out.println(httpPost.toString());

		HttpClient httpClient = HttpClients.createDefault();
		HttpResponse httpResponse = httpClient.execute(httpPost);

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(httpResponse.getEntity().getContent()));

		StringBuilder total = new StringBuilder();

		String line1 = null;

		while ((line1 = bufferedReader.readLine()) != null) {
			total.append(line1);
		}

		// bufferedReader.close();
		// System.out.println(total.toString());
		String jsonString = total.toString();

		String newAccessToken = null;
		String[] responseProperties = jsonString.split(",");
		for (String responseProperty : responseProperties) {
			if (responseProperty.contains("access_token")) {
				String[] tempResponseToken = responseProperty.split(":");
				newAccessToken = tempResponseToken[1].substring(2, tempResponseToken[1].length() - 1);

			}
		}

		// System.out.println(newAccessToken);

		HttpGet httpGet = new HttpGet("https://www.googleapis.com/calendar/v3/calendars/primary/events");

		httpGet.setHeader("Host", "www.googleapis.com");
		httpGet.setHeader("Authorization", "Bearer " + newAccessToken);
		httpGet.setHeader("Content-length", "0");

		// System.out.println(httpGet.toString());

		// HttpClient httpClient1 = HttpClients.createDefault();
		HttpResponse httpResponse1 = httpClient.execute(httpGet);

		bufferedReader = new BufferedReader(new InputStreamReader(httpResponse1.getEntity().getContent()));

		StringBuilder total1 = new StringBuilder();

		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			total1.append(line);
		}

		System.out.println(total1);

		String[] tempSplit = total1.toString().split("]");

		String calendarTasks = "";
		int actualCount = 0;
		int tempCount = 0;
		for (String temp : tempSplit) {

			if (temp.contains("items")) {

				String[] splitTask = temp.split(",");

				for (String task : splitTask) {
					if (task.contains("summary")) {
						tempCount++;

					}

				}

				for (String task : splitTask) {

					if (task.contains("summary")) {
						String tempTask = task.trim().substring(12, task.trim().length() - 1);
						actualCount++;

						if (tempCount > actualCount) {
							calendarTasks = calendarTasks + tempTask + ",";
						} else {
							calendarTasks = calendarTasks + tempTask.trim();
						}
					}
				}

			}

		}
		// System.out.println(calenderTasks);

		bufferedReader.close();

		HttpSession sess1 = req.getSession();
		String credentials = (String) sess1.getAttribute("taskacc1");

		String[] split = credentials.split(" ");

		String userName = split[0];
		String password = split[1];

		// System.out.println(userName+"--"+password+"--"+newTask);

		Key key1 = KeyFactory.createKey("UsersTask", userName + " " + password);
		Entity authUser = datastore.get(key1);

		String currentTasks = null;
		
	//	String filteredTasks = null;

		for (Map.Entry e : authUser.getProperties().entrySet()) {
			currentTasks = (String) e.getValue();
		}

		//System.out.println(currentTasks);
		if (currentTasks.equals("") || currentTasks == null) {
			currentTasks = calendarTasks;
			//filteredTasks = calendarTasks;
			//System.out.println("Helo");

		} else if(!currentTasks.equals("") && !calendarTasks.contains(",")){
		
			if(currentTasks.contains(calendarTasks.trim())) {
				System.out.println("already there");
			}else {
				currentTasks = currentTasks.concat("," + calendarTasks.trim());
			}
			
			//System.out.println("Check");
		}
		
		
		
		else {
		
			
			// System.out.println(currentTasks);
			//String tempTasks;
			if (calendarTasks.contains(",")) {
				String[] splitCalendarTasks = calendarTasks.split(",");
				
				for (String task : splitCalendarTasks) {
					
					
					if (currentTasks.contains(task+",") || currentTasks.endsWith(task)) {
						System.out.println("Duplicate is there");
					}
					
					else {
						currentTasks = currentTasks.concat("," + task.trim());
						//filteredTasks = filteredTasks.concat(","+task);
						System.out.println("no duplicate");
					}
						
				}
			}

		}

		Entity etask = new Entity("UsersTask", userName + " " + password);
		etask.setProperty("tasks", currentTasks);
		datastore.put(etask);
		System.out.println(calendarTasks);
		
		res.getWriter().println(calendarTasks);

	}

}
