<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

	<%
		response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
	%>
	
	
	
		



	<h3>Users Information</h3>
	<%
		Map<String, List<String>> details = (Map) request.getAttribute("allUsers");
	out.print("<div class=\"formBorderForAll\">");
	int temp = 0;
	out.print("<b>  UserName | Password | Mail ID | Mobile No | User DOB</b><br>");
	out.print("------------------------------------------------------------------------------------------------<br>");
	for (Map.Entry<String, List<String>> entry : details.entrySet()) {
		out.print(
		"<div style=\"border:1px solid #EBEBEB; display: inline; margin: 0px; padding: 0px; border-right: none; border-left: none;\">");
		out.print("&nbsp;&nbsp;" + entry.getKey() + "&nbsp;&nbsp;|&nbsp;");
		String myUserID = entry.getKey();
		//out.print("<div name=");

		for (int loop = 0; loop < entry.getValue().size(); loop++) {
			/**String s0 = entry.getValue().get(loop);
			int forAll = 20;
			int stringLength = s0.length();
			
			if (loop == 2) {
			    forAll = 30;
			}
			int spaceCount = forAll - stringLength;
			if (stringLength % 2 != 0) {
		out.print("&nbsp;");
			}
			for (int spacingIter = 1; spacingIter <= spaceCount / 2; spacingIter++) {
			
			    out.print("&nbsp;");
			}*/

			out.print(entry.getValue().get(loop));
			//for (int spacingIter = 1; spacingIter <= spaceCount / 2; spacingIter++) {
			//     out.print("&nbsp;");
			//}
			if (loop != 3) {
		out.print("&nbsp;|&nbsp;");
			}

		}
		do {
			out.print("<form action=\"delete\" method=\"post\" style=\" display: inline; margin: 0px; padding: 0px;\">");
			out.print("<input type=\"hidden\" name=\"userID\"value=" + myUserID + ">");
			out.print("<input type=\"hidden\" name=\"password\"value=" + entry.getValue().get(0) + ">");
			out.print("<input type=\"submit\" class=\"deleteButton\" value=\"\">");
			out.print("</form>");
			temp++;
		} while (temp < 0);

		out.print("</div>");

		out.println("<br>");

	}

	out.print("</div>");
	%>

<!-- 
<form action="authRegister.jsp" method="post">
	<div style="text-align: center; padding-top: 20px;">
	<input type="text" name="unameconfirm" id="field" placeholder="Enter UserName">
	<input type="password" name="pwdconfirm" id="field" placeholder="Enter password">
	
	<%
	//HttpSession sesssion = request.getSession();
	//String auth = "authDemo";
	//session.setAttribute("userName", auth);
	
	%>
	<input type="submit" id="button" value="Modify">
	</div>

</form>
 -->

	<div style="text-align: center;">

		<form action="logout">

			<input type="submit" id="button2" value="signout">

		</form>

	</div>

</body>
</html>