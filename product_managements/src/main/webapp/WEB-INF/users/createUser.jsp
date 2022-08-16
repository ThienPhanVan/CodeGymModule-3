<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Form Elements | Zircos - Responsive Bootstrap 4 Admin Dashboard</title>

    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>

    <link href="/assets\libs\toastr\toastr.min.css" rel="stylesheet" type="text/css">

</head>

<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">

    <!-- Navigation Bar-->
    <jsp:include page="/WEB-INF/layout/top_nav.jsp"></jsp:include>
    <!-- End Navigation Bar-->

    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">

                <!-- start page title -->
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <a href="/user" class="btn btn-outline-primary">LIST PRODUCT</a>
                            </div>
                            <h4 class="page-title">Form elements</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->

                <div class="row">
                    <div class="col-sm-12">
                        <c:if test="${requestScope.errors!=null}">
                            <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                                <c:forEach items="${requestScope.errors}" var="e">
                                    <span>${e}</span> </br>
                                </c:forEach>

                            </div>
                        </c:if>
                        <form class="form-horizontal" method="post">


                            <div class="form-group row">
                                <label class="col-md-2 control-label">Full Name</label>
                                <div class="col-md-10">
                                    <input type="text" id="name" name="fullName" class="form-control" value="${requestScope.user.getFullName()}" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Age</label>
                                <div class="col-md-10">
                                    <input type="text" id="age" name="age" class="form-control" value="${requestScope.user.getAge()}" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Phone Number</label>
                                <div class="col-md-10">
                                    <input type="text" id="phone" name="phone" class="form-control" value="${requestScope.user.getPhone()}" />
                                </div>
                            </div>


                            <div class="form-group row">
                                <label class="col-md-2 control-label">Email</label>
                                <div class="col-md-10">
                                    <input type="email" name="email" class="form-control" value="${requestScope.user.getEmail()}" />
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-md-2 control-label">Password</label>
                                <div class="col-md-10">
                                    <input type="password" id="passwordUser" name="passwordUser" value="${requestScope.user.getPasswordUser()}" class="form-control" />
                                </div>
                            </div>


                            <div class="form-group row">
                                <label class="col-md-2 control-label">Address</label>
                                <div class="col-md-10">

                                    <select class="form-control" name="address" id="address">
                                            <c:forEach var="city" items="${applicationScope.listCities}">
                                                <option value="${city.getIdCity()}">${city.getAddress()}</option>
                                            </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                    <button type="submit" class="btn btn-purple waves-effect waves-light">Submit
                                    </button>
                                </div>
                            </div>


                        </form>

                    </div>
                </div>
                <!-- end row -->

            </div>
            <!-- end container-fluid -->

        </div>
        <!-- end content -->


        <!-- Footer Start -->
        <footer class="footer">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        2018 - 2020 &copy; Zircos theme by <a href="">Coderthemes</a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- end Footer -->

    </div>


    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- Right Sidebar -->
<jsp:include page="/WEB-INF/layout/rightbar.jsp"></jsp:include>


<jsp:include page="/WEB-INF/layout/footer_js.jsp">
    <jsp:param name="page" value="create"/>
</jsp:include>
<c:if test="${requestScope.success!=null}">
    <script>
        $(document).ready(function () {
            <% String success= (String) request.getAttribute("success"); %>
            var js_Success = "<%= success %>";
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
            toastr["success"](js_Success)
        });
    </script>
</c:if>

</body>


</html>