<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Login | Iphone Store </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="Responsive bootstrap 4 admin template" name="description">
    <meta content="Coderthemes" name="author">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- App favicon -->
    <link rel="shortcut icon" href="assets\images\favicon.ico">

    <!-- App css -->
    <link href="assets\css\bootstrap.min.css" rel="stylesheet" type="text/css" id="bootstrap-stylesheet">
    <link href="assets\css\icons.min.css" rel="stylesheet" type="text/css">
    <link href="assets\css\app.min.css" rel="stylesheet" type="text/css" id="app-stylesheet">

</head>

<body style="background-color: lightgrey;">

<div class="account-pages mt-5 mb-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div class="card">

                    <div class="text-center account-logo-box" style="background-color: white;">
                        <div class="mt-2 mb-2">
                            <a href="index.html" class="text-success">
                                <span><img src="https://logovina.com/wp-content/uploads/2020/07/logo-iphone.jpg" alt="" height="36"></span>
                            </a>
                        </div>
                    </div>

                    <div class="card-body">

                        <form action="#">

                            <div class="form-group">
                                <input class="form-control" type="text" id="username" required="" placeholder="Username">
                            </div>

                            <div class="form-group">
                                <input class="form-control" type="password" required="" id="password" placeholder="Password">
                            </div>

                            <div class="form-group">
                                <div class="custom-control custom-checkbox checkbox-success">
                                    <input type="checkbox" class="custom-control-input" id="checkbox-signin" checked="">
                                    <label class="custom-control-label" for="checkbox-signin">Remember me</label>
                                </div>
                            </div>

                            <div class="form-group text-center mt-4 pt-2">
                                <div class="col-sm-12">
                                    <a class="text-muted"><i class="fa fa-lock mr-1"></i> Forgot your password?</a>
                                </div>
                            </div>

                            <div class="form-group account-btn text-center mt-2">
                                <div class="col-12">
                                    <button class="btn width-md btn-bordered btn-danger waves-effect waves-light"  style="border-color:darkgray ; background-color: darkgrey;"  type="submit">Log In</button>
                                </div>
                            </div>
                        </form>

                    </div>
                    <!-- end card-body -->
                </div>
                <!-- end card -->

                <div class="row mt-5">
                    <div class="col-sm-12 text-center">
                        <p class="text-muted">Sign Up</p>
                    </div>
                </div>

            </div>
            <!-- end col -->
        </div>
        <!-- end row -->
    </div>
    <!-- end container -->
</div>
<!-- end page -->

<!-- Vendor js -->
<script src="assets\js\vendor.min.js"></script>

<!-- App js -->
<script src="assets\js\app.min.js"></script>

</body>

</html>