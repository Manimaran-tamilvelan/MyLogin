<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	${message}


	<form action="pullqueueadd">
		<input type="number" placeholder="enter no of task to add" name="add"> <input type="submit"
			value="Enter">
	</form>


	<form action="pullqueuelease">
		<input type="number" placeholder="enter no of task you are going to lease" name="lease"> <input type="submit"
			value="Enter">
	</form>


</body>
</html>