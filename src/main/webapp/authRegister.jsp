<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Please Register your Account!</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<%
		response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
	%>


	<h3>
		<a
			style="border: 2px solid white; background-color: white; box-shadow: 0px 0px 3px 1.5px white; padding: 2px; border-radius: 8px;">Register
			Page</a><span style="margin: 0px 20px;">|</span> <a href="login.jsp"
			style="color: black; text-decoration: none;">Login Page</a>
	</h3>

	<div class="formBorder">

		<form action="register" method="post">



			<%
				HttpSession sesssion = request.getSession();

			if (session.getAttribute("userName").equals("authDemo")) {
				String userPwdConfirm;
				String userNameModify;

				userNameModify = request.getParameter("unameconfirm").toString();
				userPwdConfirm = request.getParameter("pwdconfirm").toString();

				session.setAttribute("userNameModify", userNameModify);
				session.setAttribute("userPwdConfirm", userPwdConfirm);

				out.print("<div style=\"display:none;\"><input type=\"hidden\" name=\"userName\" value=" + userNameModify
				+ " pattern=\"^[A-Za-z](.)?[\\S]{5,15}\" title=\"Start with alpahbet\" placeholder=\"Enter username should be 6 char\" id=\"field\" required><br><br> </div>");

				out.print(
				"	<div style=\"display:none;\"><input type=\"hidden\" name=\"password\" placeholder=\"Enter Password\" value="
						+ userPwdConfirm
						+ " pattern=\"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\" title=\"Should have at =least 8 characters with 1 Uppercase, 1 Special Symbol, & 1 number\" id=\"field\" required></div>");

			}
			%>








			<br> <br> <input type="text" name="mailID"
				placeholder="Enter MailID"
				pattern="[A-Za-z0-9]+[.]?[A-Za-z0-9]*@[A-Za-z]+\.[A-Za-z]+(\.[A-Za-z]*)?[A-Za-z]"
				title="Please enter valid Mail" id="field" required><br>
			<br> <input type="text" name="dob" pattern="\d\d-\d\d-\d{4}"
				title="DD-MM-YYYY" placeholder="Enter DOB [DD-MM-YYYY]" id="field"
				required><br> <br> <input type="text"
				name="mobileNo" pattern="(\+91)?\s?\d{10}"
				title="Enter valid Mobile No" placeholder="Enter Mobile No"
				id="field" required><br> <br> <input type="submit"
				value="Register" id="button">




		</form>
	</div>
</body>
</html>