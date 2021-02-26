<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


 <%@ page import="java.util.*" %>
 
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <%@page import="java.sql.*"%>
  <title>TimeMate - New Time Report</title>
</head>

<body>
<%
	if(session.getAttribute("username") == null) {
		response.sendRedirect("login.jsp");
	}
%>

<!--Start navbar-->
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
<!--End navbar-->

<div class="d-flex" id="wrapper">

  <!-- Sidebar -->
  <div>
      <div class="bg-light border-right" id="sidebar-wrapper">
          <div class="sidebar-heading">Options</div>
          <div class="list-group list-group-flush">
          <a href="summaryreport.jsp" class="list-group-item list-group-item-action bg-light">Time Report Summary</a>
          <a href="newreport.jsp" class="list-group-item list-group-item-action bg-light"><b>Create New Time Report</b></a>
          <a href="updatereport.jsp" class="list-group-item list-group-item-action bg-light">Edit Time Report</a>
          </div>
      </div>
  </div>
  <!-- /#sidebar-wrapper -->

  <!-- Page Content -->
  <div id="page-content-wrapper">

    <div class="container-fluid">
      <div>
        <div class="card mx-auto rounded shadow shadow-sm" style="max-width: 50rem; margin-top:50px; margin-bottom:50px;">
          <div class="card-header">
          New Time Report
          </div>
          <div class="card-body">
        
         <form>
            <table class="table table-bordered">
              <tbody>
                <tr>
                <td colspan="4">
                <fieldset disabled>
                  <div class="form-group row">
                    <label for="text" class="col-4 col-form-label">Name:</label> 
                    <div class="col-8">
                      <input id="text" name="text" type="text" class="form-control" placeholder="${username}">
                    </div>
                  </div>
                </fieldset>
                </td>
                <td colspan="3">
                <fieldset disabled>
                  <div class="form-group row">
                    <label for="text1" class="col-4 col-form-label">Date</label> 
                    <div class="col-8">
                      
                      <fmt:formatDate var="fmtDate" value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy HH:mm:ss"/>  
              <input type="text" name="bean.dateProperty" class="form-control" value="${fmtDate}" placeholder="${fmtDate}"/>
                      
                    </div>
                  </div>
                </fieldset> 
                </td>
              </tr>
              <tr>
                <td colspan="4">
                  <fieldset disabled>
                  <div class="form-group row">
                    <label for="text" class="col-4 col-form-label">Project group:</label> 
                    <div class="col-8">
                      <input id="text" name="text" type="text" class="form-control">
                    </div>
                  </div>
                  </fieldset>
                </td>
                <td colspan="3">
                  <div class="form-group row">
                    <label for="text" class="col-4 col-form-label">Week:</label> 
                    <div class="col-8">
                      <input id="text" name="text" type="text" class="form-control">
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="7">
                  <fieldset disabled>
                    <div class="form-group row">
                      <label for="text" class="col-5 col-form-label"><b>Part A: Total time this week (minutes):</b></label> 
                      <div class="col-7">
                        <input id="text" name="text" type="text" class="form-control">
                      </div>
                    </div>
                    </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="7">
                  <b>Part B: Number of minutes per activity</b><br>
                  (The sum of all seperate activities are automatically summed up and entered above)
                </td>
              </tr>
              <tr>
                <td><b>Number</b></td>
                <td><b>Activity</b></td>
                <td><b>D</b></td>
                <td><b>I</b></td>
                <td><b>F</b></td>
                <td><b>R</b></td>
                <td><b>Total time</b></td>
              </tr>
              <tr>
                <td>11</td>
                <td>SDP</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>12</td>
                <td>SRS</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>13</td>
                <td>SVVS</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>14</td>
                <td>STLDD</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>15</td>
                <td>SVVI</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>16</td>
                <td>SDDD</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>17</td>
                <td>SVVR</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>18</td>
                <td>SSD</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td>19</td>
                <td>Final Report</td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <input id="text" name="text" type="text" class="form-control">
                </td>
                <td>
                  <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="2"><b>Sum</b></td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="2">21</td>
                <td colspan="4">Functional test</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">22</td>
                <td colspan="4">System test</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">23</td>
                <td colspan="4">Regression test</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">30</td>
                <td colspan="4">Meeting</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">41</td>
                <td colspan="4">Lecture</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">42</td>
                <td colspan="4">Exercise</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">43</td>
                <td colspan="4">Computer Exercise</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">44</td>
                <td colspan="4">Home reading</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">100</td>
                <td colspan="4">Other</td>
                <td><input id="text" name="text" type="text" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="7">
                  <b>Part C: Time spent at different types of sub activites</b><br>
                  (The values are summed up automatically)
                </td>
              </tr>
              </tbody>
              <tr>
                <td colspan="2"><b>Activity type</b></td>
                <td><b>Activity code</b></td>
                <td colspan="3"><b>Description</b></td>
                <td><b>Sum</b></td>
              </tr>
              <tr>
                <td colspan="2">Development and documentation</td>
                <td>D</td>
                <td colspan="3">Developing new code, test cases and documentation including documentation of the system</td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="2">Informal review</td>
                <td>I</td>
                <td colspan="3">Time spent preparing and at meeting for informal reviews</td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="2">Formal review</td>
                <td>F</td>
                <td colspan="3">Time spent preparing and at meeting for formal reviews</td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="2">Rework, improvement or correction</td>
                <td>R</td>
                <td colspan="3">Time spent improving, revising or correction documents and design objects</td>
                <td>
                  <fieldset disabled>
                    <input id="text" name="text" type="text" class="form-control">
                  </fieldset>  
                </td>
              </tr>
              <tr>
                <td colspan="7"><b>Part D: Signature</b></td>
              </tr>
              <tr>
                <td colspan="6"><b>Signed by manager</b></td>
                <td>
                <fieldset disabled>
                  <input id="text" name="text" type="text" class="form-control">
                </fieldset>
              </td>
              </tr>
            </table>
            <div class="form-group row">
              <div class="offset-0 col-8">
                <button name="submit" type="submit" class="btn btn-primary">Submit</button>
              </div>
            </div>
          </form>
        
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- /#page-content-wrapper -->

</div>


</body>