<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>TimeMate - Log in</title>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-12 min-vh-100 d-flex flex-column justify-content-center" style="margin-top:50px;">
            <div class="row">
                <div class="col-lg-6 col-md-8 mx-auto">

                    <!-- form card login -->
                    <div class="card rounded shadow shadow-sm text-center">
                        <div class="card-header"><h5>Enter username and password</h5></div>

                        <div class="card-body">
                            <form class="form" role="form" autocomplete="off" id="formLogin" action="LogIn">
                            	<div class="input-group">
                            		 <div class="input-group-append">
								      <div class="input-group-text"><i class="fa fa-user"></i></div>
								    </div>
								    <input class="form-control" type="text" placeholder="Username" name="username">
                            	</div>
								<div class="input-group" style="margin-top:10px">
                            		 <div class="input-group-append">
								      <div class="input-group-text"><i class="fa fa-lock"></i></div>
								    </div>
								    <input class="form-control" type="password" placeholder="Password" name="password">
                            	</div>
                                <div class="row justify-content-center" style="margin-top:10px">
                                	<button type="submit" class="btn btn-success justify-content-center" id="btnLogin">Log in</button>
                                </div>
                            </form>
                        </div>
                        
                        <div class="card-footer text-muted">
						    <a data-target="#exampleModal" data-toggle="modal" href="#">Forgot password?</a>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Reset password</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form class="form" role="form" autocomplete="off" id="formLogin" novalidate="" method="POST">
           <div class="input-group">
               <div class="input-group-append">
  					<div class="input-group-text"><i class="fa fa-user"></i></div>
				</div>
				<input class="form-control" type="text" placeholder="Enter your e-mail" name="mail">
            </div>
       </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Request new password</button>
      </div>
    </div>
  </div>
</div>
</body>