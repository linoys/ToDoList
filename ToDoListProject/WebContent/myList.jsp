<%@ page language="java"
	import="il.ac.hit.model.*,il.ac.hit.controller.*,java.util.*"
	contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html>
<head>
<title>HELLO YOUR TO DO LIST</title>
<!-- Include CSS files -->
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/list.css">
<link rel="stylesheet" type="text/css" href="css/list.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" type="text/css" href="css/buttonListStyle.css">	
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/buttonListStyle.css">
<%
	String lAction = (String) request.getAttribute("message");
	if (lAction != null && (lAction).equals("emptyString")) {
%>
<script>
	alert("You must write something!");
</script>
<%
	}
%>


</head>
<div align="center" style="color:gray;">

<%

User user = (User)request.getSession().getAttribute("user");
  out.print("<h2>Hello "  + user.getUserName() + "</h2>");
%>
</div>
<form action="/ToDoListProject/controller/addItem" method="post">
	<div id="myDIV" class="header">
		<h2 style="margin: 100px"></h2>
		<input type="text" name="myInput" id="myInput"
			placeholder="What do you need to do...?"> 
			<input type='submit' name="Add" value="add" class="addBtn"></span>
	</div>
</form>
<ul id="myUL">
	


	<%
		List<Item> userItems = (List<Item>) session.getAttribute("userItems");

		request.setAttribute("itemList", userItems);
	%>

	<%
		for (Item item : userItems) {

			out.println("<li>" + item.getDescription()+ "<form action='/ToDoListProject/controller/delete' method='get'><input type ='hidden' value='"+ item.getItemId() + "' name='itemId'><button type='submit' class='button'><i class='fa fa-close'></i></li></form>");
		}
	%>
</ul>
<div style="margin: 10px 250px">
	<a href="/ToDoListProject/controller/logout" class="addBtn"> Log Out <i
		class="w3-margin-left fa fa-reply" class="fa fa-plus"></i>
	</a>

</div>
</body>
</html>
