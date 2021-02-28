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
            <li class="nav-item active">
                <a class="nav-link" href="summaryreport.jsp">Time Report</a>
            </li>
            <c:if test = "${sessionScope.role eq 'ADMIN' || sessionScope.role eq 'PG'}">
            	<li class="nav-item">
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

<div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div>
        <div class="bg-light border-right" id="sidebar-wrapper">
            <div class="sidebar-heading">Options</div>
            <div class="list-group list-group-flush">
            <a href="summaryreport.jsp" class="list-group-item list-group-item-action bg-light"><b>Time Report Summary</b></a>
            <a href="newreport.jsp" class="list-group-item list-group-item-action bg-light">Create New Time Report</a>
            <c:if test = "${sessionScope.role eq 'ADMIN' || sessionScope.role eq 'PG'}">
                <a href="signreport.jsp" class="list-group-item list-group-item-action bg-light">Sign Time Reports</a>
            </c:if>
            </div>
        </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
  <div id="page-content-wrapper">
    
  <div class="collapse multi-collapse show"  id="collapseExample">

   <div style="background-color:white">
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
            <tr>
              <td><input type="radio" name="radioGroup"></td>
              <td>${username}</td>
              <td>date</td>
              <td>week</td>
              <td>dev</td>
              <td>inf</td>
              <td>for</td>
              <td>rew</td>
              <td>other</td>
              <td>total</td>
              <td>signed</td>
            </tr>
            <tr>
              <td><input type="radio" name="radioGroup"></td>
              <td>${username}</td>
              <td>date</td>
              <td>week</td>
              <td>dev</td>
              <td>inf</td>
              <td>for</td>
              <td>rew</td>
              <td>other</td>
              <td>total</td>
              <td>signed</td>
            </tr>
          </tbody>
        </table>
        
      </div>
 </div> 
 
 <div class="collapse multi-collapse"  id="collapseExample2">

  <div style="background-color:white">
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
           <tr>
             <td><input type="radio" name="radioGroup"></td>
             <td>${username}</td>
             <td>date</td>
             <td>week</td>
             <td>dev</td>
             <td>inf</td>
             <td>for</td>
             <td>rew</td>
             <td>other</td>
             <td>total</td>
             <td>signed</td>
           </tr>
           <tr>
             <td><input type="radio" name="radioGroup"></td>
             <td>${username}</td>
             <td>date</td>
             <td>week</td>
             <td>dev</td>
             <td>inf</td>
             <td>for</td>
             <td>rew</td>
             <td>other</td>
             <td>total</td>
             <td>signed</td>
           </tr>
           <tr>
            <td><input type="radio" name="radioGroup"></td>
            <td>${username}</td>
            <td>date</td>
            <td>week</td>
            <td>dev</td>
            <td>inf</td>
            <td>for</td>
            <td>rew</td>
            <td>other</td>
            <td>total</td>
            <td>signed</td>
          </tr>
          <tr>
            <td><input type="radio" name="radioGroup"></td>
            <td>${username}</td>
            <td>date</td>
            <td>week</td>
            <td>dev</td>
            <td>inf</td>
            <td>for</td>
            <td>rew</td>
            <td>other</td>
            <td>total</td>
            <td>signed</td>
          </tr>
          <tr>
            <td><input type="radio" name="radioGroup"></td>
            <td>${username}</td>
            <td>date</td>
            <td>week</td>
            <td>dev</td>
            <td>inf</td>
            <td>for</td>
            <td>rew</td>
            <td>other</td>
            <td>total</td>
            <td>signed</td>
          </tr>
         </tbody>
       </table>
       
     </div>
</div>
    
    
    
       
        <div class="d-flex justify-content-center" style="margin-top:5px">
            <form class="form-inline my-2 my-lg-0" style="margin-right:2.5px">
                <input type="submit" value="Edit selected report" class="btn btn-success" style="margin-right:3px">
                <input type="submit" value="View selected report" class="btn btn-success" style="margin-left:3px; margin-right:3px">
                <c:if test = "${sessionScope.role eq 'ADMIN' || sessionScope.role eq 'PG'}">
                <button class="btn btn-primary" type="button" data-toggle="collapse" data-target=".multi-collapse" aria-expanded="false" aria-controls="collapseExample collapseExample2" style="margin-left:3px">
                    Show signed reports
                </button>                
                </c:if>
            </form>
        </div>
    </div>
    
   
    
    <!-- /#page-content-wrapper -->

</div>

</body>