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
  <title>TimeMate - User Management</title>
</head>

<body>
  <%
  if(session.getAttribute("username") == null) {
    response.sendRedirect("login.jsp");
  } else if (!session.getAttribute("role").equals("ADMIN") || !session.getAttribute("role").equals("PG")) {
    response.sendRedirect("index.jsp");
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
            <li class="nav-item">
              <a class="nav-link" href="summaryreport.jsp">Time Report</a>
            </li>
            <c:if test = "${sessionScope.role eq 'ADMIN' || sessionScope.role eq 'PG'}">
            	<li class="nav-item active">
              		<a class="nav-link" href="usermanagement.jsp">User Management</a>
            	</li>
            </c:if>
            <c:if test = "${sessionScope.role eq 'ADMIN'}">
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
                <form class="form-inline my-2 my-lg-0">
                  <a class="btn btn-danger" href="#" data-toggle="modal" data-target="#logoutModal">Log out</a>
                </form>
              </li> 
          </ul>
      </div>
  </nav>
  
  <div class="modal" id="logoutModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"></span></button>
        <h4>Log Out <i class="fa fa-lock"></i></h4>
      </div>
      <div class="modal-body">
        <p><i class="fa fa-question-circle"></i> Are you sure you want to log-off? <br /></p>
        <div class="actionsBtns">
            <form action="login.jsp">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" class="btn btn-default btn-primary" value="Logout" />
	                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
            </form>
        </div>
      </div>
    </div>
  </div>
</div>
      
<div class="card mx-auto rounded shadow shadow-sm" style="max-width: 30rem; margin-top:50px; margin-bottom:50px;">
  <div class="card-header">
  User Management
  </div>
  <div class="card-body">
  <table class="table table-striped">
    <thead>
      <tr>
        <th scope="col">User</th>
        <th scope="col">PG</th>
        <th scope="col">UG</th>
        <th scope="col">TG</th>
        <th scope="col">SG</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>user1</td>
        <td><input type="radio" name="radioGroup1"></td>
        <td><input type="radio" name="radioGroup1"></td>
        <td><input type="radio" name="radioGroup1"></td>
        <td><input type="radio" name="radioGroup1"></td>
      </tr>
      <tr>
        <td>user2</td>
        <td><input type="radio" name="radioGroup2"></td>
        <td><input type="radio" name="radioGroup2"></td>
        <td><input type="radio" name="radioGroup2"></td>
        <td><input type="radio" name="radioGroup2"></td>
      </tr>
    </tbody>
  </table>
    <div class="form-group row">
      <div class="offset-0 col-8">
        <button name="submit" type="submit" class="btn btn-primary">Submit</button>
      </div>
    </div>

  </div>
</div>
</body>