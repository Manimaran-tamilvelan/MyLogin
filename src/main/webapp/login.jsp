<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo App - Login</title>
<link rel="stylesheet" type="text/css" href="style.css">

<meta name="google-signin-client_id"
	content="628485492305-40ss5ddfpuaq2i3f5gp1g02etuasrdda.apps.googleusercontent.com">
<script src="http://code.jquery.com/jquery-3.5.1.min.js"
	integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
	crossorigin="anonymous"></script>
	  <script src="https://apis.google.com/js/api.js" async defer></script>

 <script>
      $(document).ready(function() {
        $("#goButton1").click(makeRequest1);
      });

      function makeRequest1() {
        // Define properties
        var AUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
        var RESPONSE_TYPE = "code";
        var CLIENT_ID = "628485492305-clc02nguilt3cn9aimatqolcgd8ivc7c.apps.googleusercontent.com";
        var REDIRECT_URI = "http://localhost:8080/oauth";
        var SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/calendar.events.readonly";

        
        // Build authorization request endpoint
        var requestEndpoint = AUTH_ENDPOINT + "?" +
        "response_type=" + encodeURIComponent(RESPONSE_TYPE) + "&" +
        "client_id=" + encodeURIComponent(CLIENT_ID) + "&" +
        "redirect_uri=" + encodeURIComponent(REDIRECT_URI) + "&" +
        "scope=" + encodeURIComponent(SCOPE)+"&access_type=offline";

        // Send to authorization request endpoint
        window.location.href = requestEndpoint;
      }
    </script>
 
    <script>
      $(document).ready(function() {
        $("#goButton").click(makeRequest);
      });

      function makeRequest() {
        // Define properties
        var AUTH_ENDPOINT = "https://www.facebook.com/dialog/oauth";
        var RESPONSE_TYPE = "code";
        var CLIENT_ID = "326846368586443";
        var REDIRECT_URI = "http://localhost:8080/oauth1";
        var SCOPE = "public_profile email user_posts";

        // Build authorization request endpoint
        var requestEndpoint = AUTH_ENDPOINT + "?" +
        "response_type=" + encodeURIComponent(RESPONSE_TYPE) + "&" +
        "client_id=" + encodeURIComponent(CLIENT_ID) + "&" +
        "redirect_uri=" + encodeURIComponent(REDIRECT_URI) + "&" +
        "scope=" + encodeURIComponent(SCOPE);

        // Send to authorization request endpoint
        window.location.href = requestEndpoint;
      }
    </script>

</head>

<body>

	<h3>
		<a href="register.jsp" style="color: black; text-decoration: none;">Register
			Page</a><span style="margin: 0px 20px;">|</span> <a
			style="border: 2px solid white; box-shadow: 0px 0px 3px 1.5px white; padding: 2px; background-color: white; border-radius: 8px;">Login
			Page</a>
	</h3>

	<div style="text-align: center">${message}</div>


	<div class="formBorder">

		<form action="login" method="post">
			<input type="text" name="username" placeholder="Enter UserName"
				id="field" required><br /> <br /> <input type="password"
				name="password" placeholder="Enter password" id="field" required><br />
			<br /> <input type="submit" id="button" value="Login">


		</form>
	</div>

	
 
 <button id="goButton" type="button">Fb Login</button>
 <button id="goButton1" type="button">Google Login</button>
    <div id="results"></div>


	
	<script src="https://apis.google.com/js/platform.js" async defer></script>


</body>
</html>