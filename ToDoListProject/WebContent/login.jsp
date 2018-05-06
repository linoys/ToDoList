<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
	<meta name="viewport" content="initial-scale=0.9,
height=device-width">

<title>login To Do List</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel='stylesheet prefetch'
	href='https://fonts.googleapis.com/css?family=Open+Sans:600'>

<link rel="stylesheet" type="text/css" href="css/styleLogins.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/styleLogins.css">

</head>

<body>
	<form action="/ToDoListProject/controller/login" method="post">

		<div class="login-wrap">
			<div class="login-html">
				<%
					String lAction = (String) request.getAttribute("LoginFailed");
					if (lAction != null && (lAction).equals("true")) {
				%>
				<script>
					alert("Please fill all the details");
				</script>
				<%
					}
				%>

				<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label
					for="tab-1" class="tab">Sign In</label> <input id="tab-2"
					type="radio" name="tab" class="sign-up"><label for="tab-2"
					class="tab"></label>
				<div class="login-form">
					<div class="sign-in-htm">
						<div class="group">
							<label for="user" class="label">Username</label> <input
								name="user" id="user" type="text" class="input">
						</div>
						<div class="group">
							<label for="pass" class="label">Password</label> <input id="pass"
								name="pass" type="password" class="input" data-type="password">
						</div>
							<div class="group">
							<label for="email" class="label">Email</label> <input
								name="email" id="email" type="email" class="input"
								 
								pattern="[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*" required>
						</div>
						<div class="group">
							<input id="check" type="checkbox" class="check" checked>
							<label for="check"><span class="icon"></span> Keep me
								Signed in</label>
						</div>
						<div class="group">
							<input type="submit" class="button" value="Sign In">
						</div>
						<div class="hr"></div><p>
						<a href="/ToDoListProject/register.jsp"> Register here</a></p>
						<p>
						<a	href="/ToDoListProject/welcome.jsp"> Return to home page</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</form>


</body>
</html>
