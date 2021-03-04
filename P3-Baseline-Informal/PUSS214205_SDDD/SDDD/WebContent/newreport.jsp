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
          <c:if test = "${sessionScope.role eq 'ADMIN' || sessionScope.role eq 'PG'}">
            <form action="UserManagementServlet">
              <input type="submit" value="User Management" class="nav-link astext">
            </form>
          </c:if>
          <c:if test = "${sessionScope.role eq 'ADMIN'}">
            <form action="AdministrationServlet">
              <input type="submit" value="Administration" class="nav-link astext">
            </form>
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
        <p><i class="fa fa-question-circle"></i> Are you sure you want to log out? <br /></p>
        <div class="actionsBtns">
            <form action="LogOut">
            
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" class="btn btn-default btn-primary" value="Logout" />
	                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
            </form>
        </div>
      </div>
    </div>
  </div>
</div>
<!--End navbar-->

<div class="d-flex" id="wrapper">

 <!-- Sidebar -->
    <div>
        <div class="bg-light border-right" id="sidebar-wrapper">
            <div class="sidebar-heading">Options</div>
            <div class="list-group list-group-flush">
            <form action="TimeReportServlet">
       	        <input type="submit" name="summary" value="Time Report Summary" class="list-group-item list-group-item-action bg-light astext">
            </form>
            <form action="TimeReportServlet" method="POST">
        	    <input type="submit" name="new" value="Create New Time Report" class="list-group-item list-group-item-action bg-light astext">
            </form>
            <c:if test = "${sessionScope.role eq 'ADMIN' || sessionScope.role eq 'PG'}">
    	       	<form action="TimeReportManagementServlet">
        	        <input type="submit" value="Sign Time Reports" class="list-group-item list-group-item-action bg-light astext">
              	</form>
            </c:if>
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
        
         <form action="TimeReportServlet" method="POST">
            <table class="table table-bordered">
              <tbody>
                <tr>
                <td colspan="7">
                <fieldset disabled>
                  <div class="form-group row">
                    <label for="text" class="col-2 col-form-label">Name:</label> 
                    <div class="col-10">
                      <input id="username" name="username" type="text" class="form-control" placeholder="${username}">
                    </div>
                  </div>
                </fieldset>
                </td>
              </tr>
              <tr>
                <td colspan="4">
                  <fieldset disabled>
                  <div class="form-group row">
                    <label for="text" class="col-4 col-form-label">Role:</label> 
                    <div class="col-8">
                      <input id="projectgroup" name="projectgroup" type="text" class="form-control" placeholder="${role}">
                    </div>
                  </div>
                  </fieldset>
                </td>
                <td colspan="3">
                  <div class="form-group row">
                    <label for="text" class="col-4 col-form-label">Week:</label> 
                    <div class="col-8">
                      <input type="number" id="week" name="week" class="form-control">
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="7">
                    <div class="form-group row">
                      <label for="text" class="col-5 col-form-label"><b>Part A: Total time this week (minutes):</b></label>
                      <div class="col-7">
                        <input type="number" id="totalMinutes" name="totalMinutes" class="form-control" readonly>
                      </div>
                    </div>
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
                  <input type="number" id="sdp_d" name="sdp_d" class="sdp_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sdp_i" name="sdp_i" class="sdp_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sdp_f" name="sdp_f" class="sdp_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sdp_r" name="sdp_r" class="sdp_report_value form-control">
                </td>
                <td>
                    <input type="number" id="sdp_total" name="sdp_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>12</td>
                <td>SRS</td>
                <td>
                  <input type="number" id="srs_d" name="srs_d" class="srs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="srs_i" name="srs_i" class="srs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="srs_f" name="srs_f" class="srs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="srs_r" name="srs_r" class="srs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="srs_total"  name="srs_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>13</td>
                <td>SVVS</td>
                <td>
                  <input type="number" id="svvs_d" name="svvs_d" class="svvs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvs_i" name="svvs_i" class="svvs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvs_f" name="svvs_f" class="svvs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvs_r" name="svvs_r" class="svvs_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvs_total" name="svvs_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>14</td>
                <td>STLDD</td>
                <td>
                  <input type="number" id="stldd_d" name="stldd_d" class="stldd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="stldd_i" name="stldd_i" class="stldd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="stldd_f" name="stldd_f" class="stldd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="stldd_r" name="stldd_r" class="stldd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="stldd_total"  name="stldd_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>15</td>
                <td>SVVI</td>
                <td>
                  <input type="number" id="svvi_d" name="svvi_d" class="svvi_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvi_i" name="svvi_i" class="svvi_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvi_f" name="svvi_f" class="svvi_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvi_r" name="svvi_r" class="svvi_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvi_total" name="svvi_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>16</td>
                <td>SDDD</td>
                <td>
                  <input type="number" id="sddd_d" name="sddd_d" class="sddd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sddd_i" name="sddd_i" class="sddd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sddd_f" name="sddd_f" class="sddd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sddd_r" name="sddd_r" class="sddd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="sddd_total" name="sddd_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>17</td>
                <td>SVVR</td>
                <td>
                  <input type="number" id="svvr_d" name="svvr_d" class="svvr_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvr_i" name="svvr_i" class="svvr_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvr_f" name="svvr_f" class="svvr_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvr_r" name="svvr_r" class="svvr_report_value form-control">
                </td>
                <td>
                  <input type="number" id="svvr_total" name="svvr_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>18</td>
                <td>SSD</td>
                <td>
                  <input type="number" id="ssd_d" name="ssd_d" class="ssd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="ssd_i" name="ssd_i" class="ssd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="ssd_f" name="ssd_f" class="ssd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="ssd_r" name="ssd_r" class="ssd_report_value form-control">
                </td>
                <td>
                  <input type="number" id="ssd_total" name="ssd_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td>19</td>
                <td>Final Report</td>
                <td>
                  <input type="number" id="final_d" name="final_d" class="final_report_value form-control">
                </td>
                <td>
                  <input type="number" id="final_i" name="final_i" class="final_report_value form-control">
                </td>
                <td>
                  <input type="number" id="final_f" name="final_f" class="final_report_value form-control">
                </td>
                <td>
                  <input type="number" id="final_r" name="final_r" class="final_report_value form-control">
                </td>
                <td>
                  <input type="number" id="final_total" name="final_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td colspan="2"><b>Sum</b></td>
                <td>
                    <input type="number" id="total_d" name="total_d" class="form-control" disabled>
                </td>
                <td>
                    <input type="number" id="total_i" name="total_i" class="form-control" disabled>
                </td>
                <td>
                    <input type="number" id="total_f" name="total_f" class="form-control" disabled>
                </td>
                <td>
                    <input type="number" id="total_r" name="total_r" class="form-control" disabled>
                </td>
                <td>
                    <input type="number" id="total_total" name="total_total" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td colspan="2">21</td>
                <td colspan="4">Functional test</td>
                <td><input type="number" id="functionalTest" class="other-values" name="functionalTest" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">22</td>
                <td colspan="4">System test</td>
                <td><input type="number" id="systemTest" class="other-values" name="systemTest" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">23</td>
                <td colspan="4">Regression test</td>
                <td><input type="number" id="regressionTest" class="other-values" name="regressionTest" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">30</td>
                <td colspan="4">Meeting</td>
                <td><input type="number" id="meeting" class="other-values" name="meeting" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">41</td>
                <td colspan="4">Lecture</td>
                <td><input type="number" id="lecture" class="other-values" name="lecture" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">42</td>
                <td colspan="4">Exercise</td>
                <td><input type="number" id="exercise" class="other-values" name="exercise" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">43</td>
                <td colspan="4">Computer Exercise</td>
                <td><input type="number" id="computerExercise" class="other-values" name="computerExercise" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">44</td>
                <td colspan="4">Home reading</td>
                <td><input type="number" id="homeReading" class="other-values" name="homeReading" class="form-control"></td>
              </tr>
              <tr>
                <td colspan="2">100</td>
                <td colspan="4">Other</td>
                <td><input type="number" id="other" class="other-values" name="other" class="form-control"></td>
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
                    <input type="number" id="total_d" name="total_d" type="text" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td colspan="2">Informal review</td>
                <td>I</td>
                <td colspan="3">Time spent preparing and at meeting for informal reviews</td>
                <td>
                    <input type="number" id="total_i" name="total_i" type="text" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td colspan="2">Formal review</td>
                <td>F</td>
                <td colspan="3">Time spent preparing and at meeting for formal reviews</td>
                <td>
                    <input type="number" id="total_f" name="total_f" type="text" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td colspan="2">Rework, improvement or correction</td>
                <td>R</td>
                <td colspan="3">Time spent improving, revising or correction documents and design objects</td>
                <td>
                    <input type="number" id="total_r" name="total_r" type="text" class="form-control" disabled>
                </td>
              </tr>
              <tr>
                <td colspan="7"><b>Part D: Signature</b></td>
              </tr>
              <tr>
                <td colspan="6"><b>Signed by manager</b></td>
                <td>
                  <input type="number" id="signature" name="signature" type="text" class="form-control" disabled>
              </td>
              </tr>
            </table>
            <div class="form-group row">
              <div class="offset-0 col-8">
                <button name="submitNew" type="submit" class="btn btn-primary">Submit</button>
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

<script>

  // Format is "row-boxes", "row-total"
  let documents = [
    ["sdp_report_value", "sdp_total"],
    ["srs_report_value", "srs_total"],
    ["svvs_report_value", "svvs_total"],
    ["stldd_report_value", "stldd_total"],
    ["svvi_report_value", "svvi_total"],
    ["sddd_report_value", "sddd_total"],
    ["svvr_report_value", "svvr_total"],
    ["ssd_report_value", "ssd_total"],
    ["final_report_value", "final_total"]
  ];

  let total_columns = ['total_d', 'total_i', 'total_f', 'total_r'];

  // Bind event listeners on all inputs
  for (let doc of documents) {
    for (let box of document.getElementsByClassName(doc[0])) {
      box.addEventListener('input', updateTotals);
    }
  }

  // Bind event listeners for others as well.
  for (let box of document.getElementsByClassName("other-values")) {
    box.addEventListener('input', updateTotals);
  }

  // Week must be between 0-53!
  document.getElementById('week').addEventListener('input', change => {
    const week = document.getElementById('week');
    const value = getValue(week);
    if (value > 53) {
      week.value = 53;
    } else if (value < 0) {
      week.value = 0;
    }
  });

  // Call it once to set to 0.
  updateTotals();

  /* Updates all the total boxes with the total report for each document. */
  function updateTotals() {
    let col_sums = [0, 0, 0, 0];

    for (let doc of documents) {
      let totalMinutes = getTotal(doc[0]);
      document.getElementById(doc[1]).value = totalMinutes;

      // Calculate the column totals.
      let boxes = document.getElementsByClassName(doc[0]);
      for (let i = 0; i < 4; i++) {
        col_sums[i] += getValue(boxes[i]);
      }
    }

    // Update the total boxes.
    for (let i = 0; i < 4; i++) {
        console.log(total_columns[i], col_sums[i])
        for (let box of document.getElementsByName(total_columns[i])) {
          box.value = col_sums[i];
        }
    }

    let others = getOtherValues();

    // Update the total time (not including "others").
    let totalTotal = 0;
    col_sums.forEach(time => totalTotal += time);
    document.getElementById("total_total").value = totalTotal;


    console.log(others, totalTotal)
    // Update the absolute TOTAL time.
    document.getElementById('totalMinutes').value = others + totalTotal;
  }

  /* Get the total time for the "other" values. */
  function getOtherValues() {
    let sum = 0;
    for (let box of document.getElementsByClassName("other-values")) {
      sum += getValue(box);
    }
    return sum;
  }

  /* Gets the total time for a given document. */
  function getTotal(box_class) {
    let boxes = document.getElementsByClassName(box_class);
    let sum = 0;
    for (let i = 0; i < 4; i++) {
      let value = getValue(boxes[i]);
      sum += value;
    }
    return sum;
  }

  /* Helper function to parse the integer value. */
  function getValue(box) {
    return Number.isNaN(parseInt(box.value)) ? 0 : parseInt(box.value);
  }

</script>
</body>