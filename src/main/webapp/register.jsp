<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Todo App - Please Register your Account!</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<h3>
		<a
			style="border: 2px solid white; background-color: white; box-shadow: 0px 0px 3px 1.5px white; padding: 2px; border-radius: 8px;">Register
			Page</a><span style="margin: 0px 20px;">|</span> <a href="login.jsp"
			style="color: black; text-decoration: none;">Login Page</a>
	</h3>
	
	<div style="text-align:center">${message} </div>

	<div class="formBorder">

		<form action="register" method="post">
			<input type="text" name="userName" pattern="^[A-Za-z](.)?[\S]{5,15}" title="Start with alpahbet" placeholder="Enter username should be 6 char"
				id="field" required><br> <br> <input type="password"
				name="password" placeholder="Enter Password" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$" title="Should have at =least 8 characters with 1 Uppercase, 1 Special Symbol, & 1 number" id="field" required><br>
			<br> <input type="text" name="mailID"  placeholder="Enter MailID" pattern="[A-Za-z0-9]+[.]?[A-Za-z0-9]*@[A-Za-z]+\.[A-Za-z]+(\.[A-Za-z]*)?[A-Za-z]" title="Please enter valid Mail"
				id="field" required><br> <br> <input type="text" name="dob"  pattern="\d\d-\d\d-\d{4}" title="DD-MM-YYYY"
				placeholder="Enter DOB [DD-MM-YYYY]" id="field" required><br> <br> <input
				type="text" name="mobileNo" pattern="(\+91)?\s?\d{10}" title="Enter valid Mobile No" placeholder="Enter Mobile No" id="field" required><br>
			<br> <input type="submit" value="Register" id="button">




		</form>
	</div>
</body>
</html>