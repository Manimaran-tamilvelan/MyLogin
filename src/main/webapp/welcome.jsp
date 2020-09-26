<%@page
	import="com.google.appengine.api.datastore.EntityNotFoundException"%>
<%@page import="com.google.api.client.util.store.DataStore"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.google.appengine.api.datastore.Entity"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="com.google.appengine.api.datastore.Key"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo App - Welcome !</title>
<link rel="stylesheet" type="text/css" href="style.css">
<link
	href="//fonts.googleapis.com/css?family=Work+Sans:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Kumbh+Sans:wght@300;700&display=swap"
	rel="stylesheet">
<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>
<script src="https://apis.google.com/js/api.js" async defer></script>
<meta name="google-signin-client_id"
	content="628485492305-40ss5ddfpuaq2i3f5gp1g02etuasrdda.apps.googleusercontent.com">

<script src="todoscript.js">
	
</script>
<script>
	function checkCalendar() {
		//console.log("Okay")

		$(".welcomeformBorder").css("filter", "blur(2px)");

		$
				.ajax({
					url : "calendarTasks",
					type : "GET",
					success : function(result) {

						if (result.trim() == "") {
							alert("No task Available")
							return "";
						}

						let count = $("p").length;

						let checkArray = [];

						for (var i = 1; i <= count; i++) {
							checkArray.push($(document.getElementById(i))
									.text().trim());
						}

						//	console.log(checkArray);

						let tasks = [];
						var n = result.includes(",");

						if (n == true) {
							tasks = result.split(",");

							let a = tasks.pop()

							tasks.push(a.trim());
							//console.log(tasks);
						} else {
							tasks.push(result);

							let a = tasks.pop()

							tasks.push(a.trim());
							//console.log(tasks);
						}

						$("#noContent").hide();

						let finalTasks = [];

						for (var i = 0; i < tasks.length; i++) {
							console.log(checkArray)
							let check = checkArray.includes(tasks[i]);
							if (check == true) {
								console.log("already there")

							} else {
								finalTasks.push(tasks[i]);
							}
						}

						//let finalTasks = ["Wait"]

						console.log(finalTasks);

						for (var i = 0; i < finalTasks.length; i++) {
							$("ul")
									.append(
											"<p><input value=\"&#10004;\" style=\"height: 20px; text-align:center; border-radius: 5px;\" type=\"button\" onclick=clicked("
													+ ($(".list").length + 1)
													+ ") id="
													+ ($(".list").length + 100)
													+ "> <label  for="
													+ ($(".list").length + 100)
													+ " class=\"list\" onclick=clicked("
													+ ($(".list").length + 1)
													+ ") id="
													+ ($(".list").length + 1)
													+ ">"
													+ finalTasks[i]
													+ "</label> <input type=\"submit\" onclick=\"remove("
													+ ($(".list").length + 1)
													+ ")\" id="
													+ ($(".list").length + 1 + "button")
													+ " value= \"\" class=\"deleteButton\" > </p>");
							myArray.push(finalTasks[i]);
							consistArray.push(finalTasks[i]);
							

						}$(".welcomeformBorder").css("filter", "none");

					},

					error : function(xhr) {
						console.log(xhr.status + "--" + xhr.statusText);
					}

				});
		
		

	}
</script>


</head>
<body>

	<%
		response.setHeader("cache-control", "no-cache, no-store, must-revalidate");

	if (session.getAttribute("userName") == null) {
		response.sendRedirect("login.jsp");
	}
	%>




	<h3>Your Tasks</h3>



	<div id="greeting">

		<form action="logout">

			<input type="submit" class="button2" id="button1" value="signout">

		</form>
	</div>



	<%
		String entityKey = (String) request.getAttribute("taskacc");

	String[] getUserName = entityKey.split(" ");

	//out.print("<div id=\"greet\">" + "Welcome <span id=\"userName\">" + getUserName[0]
	//		+ "</span> !</div>");

	out.print("<div id=\"greet\">" + "Welcome !</div>");

	if (getUserName[0].matches("[0-9]+") && getUserName[0].length() > 10) {

		out.println(
		"<script>let a= true; if(a ==true) {$(\"#button1\").removeAttr(\"id\"); $('.button2').attr('onclick', 'signOut()');}  </script>");

		//out.println(
		//"<script>let a= true; if(a ==true) {$(\"#button1\").remove();  $(\"#greet\").css(\"text-align\",\"center\");  } </script>");

	}

	out.print("<div class=\"welcomeformBorder\">");

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	//System.out.println(entityKey);
	Entity authUser;

	try {
		Key key = KeyFactory.createKey("UsersTask", entityKey);
		authUser = datastore.get(key);
		//write prog to display old tasks

		int size1 = 0;
		String check1 = null;
		int splitSize = 0;

		int tempcount = 100;

		out.print("<ul>");
		breakit: for (Map.Entry e : authUser.getProperties().entrySet()) {

			if (!(e.toString().contains(",")) && e.getValue().toString().length() > 1) {

		out.print(
				"<p><input value=\"&#10004;\" style=\"height: 20px; text-align:center; border-radius: 5px;\" type=\"button\" onclick=clicked(\"1\") id="
						+ tempcount + "><label class=\"list\" onclick=\"clicked(1)\" for=" + tempcount
						+ " id=\"1\">" + e.getValue().toString().trim()
						+ "</label> <input type=\"submit\" onclick=\"remove(1)\" id=\"1button\" value= \"\" class=\"deleteButton\" > </p>");
		tempcount++;
		out.println("</ul>");

			}

			String[] split = e.getValue().toString().split(",");
			splitSize = split.length;
			if (size1 == 0) {
		check1 = split[0];
			}
			size1++;

		}
		//System.out.println("check2" + "--" + size1 + " -" + splitSize);
		boolean bool = check1.equals("");

		if (splitSize > 1 && !check1.equals("")) {
			for (Map.Entry e : authUser.getProperties().entrySet()) {

		String[] split = e.getValue().toString().split(",");

		String a[] = new String[split.length];

		for (int index = 0; index < split.length; index++) {
			out.print(
					"<p><input value=\"&#10004;\" style=\"height: 20px; text-align:center; border-radius: 5px;\" type=\"button\" onclick=clicked("
							+ (index + 1) + ") id=" + tempcount + "><label class=\"list\" onclick=\"clicked("
							+ (index + 1) + ")\" for=" + tempcount + " id=" + (index + 1) + ">"
							+ split[index].trim() + "</label> <input type=\"submit\" onclick=\"remove("
							+ (index + 1) + ")\" id=" + (index + 1 + "button")
							+ " value= \"\" class=\"deleteButton\" > </p>");
			tempcount++;
			//" 
			//"<input type=\"submit\" class=\"deleteButton\" value=\"\">"

			a[index] = split[index].trim();

		}

		//request.setAttribute("oldTasks", a[]);

			}
		}

		out.print("</ul>");

	} catch (EntityNotFoundException e) {
		Entity etask = new Entity("UsersTask", entityKey);
		etask.setProperty("tasks", "");
		datastore.put(etask);

		Key key = KeyFactory.createKey("UsersTask", entityKey);
		authUser = datastore.get(key);
		out.print("<ul></ul>");

	}

	//check no content
	int size = 0;
	String check = null;

	for (Map.Entry e : authUser.getProperties().entrySet()) {
		check = e.getValue().toString();
		size++;

	}

	if (size == 1 && check.equals("")) {
		//display no content
		out.print(
		"<p style=\"text-align:center; padding-left:50px; color:gray;  \" id=\"noContent\">No Task is There .. !</p>");

	}

	out.print("</div>");
	/**
	List<String> details = (ArrayList<String>) request.getAttribute("showUserDetail");

	int size=1;
	for (String detailsIterate : details) {

		if(size==1)
			out.print("<b>UserName:</b>"+detailsIterate);
		if(size==2)
			out.print("<b>Password:</b>"+detailsIterate);
		if(size==3)
			out.print("<b>Mail ID:</b>"+detailsIterate);
		if(size==4)
			out.print("<b>Mobile No:</b>"+detailsIterate);
		if(size==5)
			out.print("<b>DOB:</b>"+detailsIterate);
		
		out.print("<br>");
		size++;
	}
	out.print("</div>");
	*/
	%>


	<div style="text-align: center; padding-top: 50px;">



		<input type="text" placeholder="Enter your Task Now !" id="taskfield">
		<input type="submit" id="button" onclick="addTask()" value="Add Task">


	</div>

	<%
		Object source = session.getAttribute("from");
	if (source != null) {
		out.println("<form><input type=\"button\" id=\"fetchTask\" onclick=\"checkCalendar()\" value=\"&#x21bb;\"></form>");
	}
	%>

	<!-- a href="/login.jsp" id="signout" style="padding-left: 630px; padding-top:10px;" onclick="signOut();">Sign out</a -->

	<script src="https://apis.google.com/js/platform.js?onload=onLoad"
		async defer></script>



</body>
</html>