<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <%@page import="java.sql.*"%>
  <title>TimeMate - View Time Reports</title>
</head>

<body>
    <%
		if(session.getAttribute("username") == null) {
			response.sendRedirect("login.jsp");
		}
	%>
<nav class="navbar navbar-light navbar-expand-md bg-light">
    <a class="navbar-brand abs">TimeMate</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="navbar-collapse collapse" id="collapsingNavbar">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="index.jsp">Home</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Time reports
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <a class="dropdown-item active" href="viewreport.jsp">View time reports</a>
                <a class="dropdown-item" href="updatereport.jsp">Update time reports</a>
                <a class="dropdown-item" href="newreport.jsp">New time report</a>
                <a class="dropdown-item" href="#">View summaries</a>
              </div>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="usermanagement.jsp">User Management</a>
            </li>
            <c:if test = "${sessionScope.username eq 'admin'}">
            	<li class="nav-item">
              		<a class="nav-link" href="administration.jsp">Administration</a>
            	</li>
            </c:if>
        </ul>

        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <form class="form-inline my-2 my-lg-0" action="changepassword.jsp">
              	<input type="submit" value="Change Password" class="btn btn-primary" style="margin-right:7px">
              </form>
            </li>
            <li class="nav-item">
              <form class="form-inline my-2 my-lg-0" action="Logout">
                <input type="submit" value="Log out" class="btn btn-danger">
              </form>
            </li>
        </ul>
    </div>
</nav>
 <div>
  <table class="table table-striped">
  <thead>
    <tr>
      <th scope="col" data-field="state">Selection</th>
      <th scope="col">User</th>
      <th scope="col">Last update</th>
      <th scope="col">Week</th>
      <th scope="col">Development</th>
      <th scope="col">Informal review</th>
      <th scope="col">Formal review</th>
      <th scope="col">Rework</th>
      <th scope="col">Other</th>
      <th scope="col">Total time</th>
      <th scope="col">Signed</th>
      
    </tr>
  </thead>
  <tbody>

  </tbody>
</table>
</div>
<div class="d-flex justify-content-center">
<form class="form-inline my-2 my-lg-0" action="GetReport">
     <input type="submit" value="View selected report" class="btn btn-success">
   </form>
</div>
  	
</body>