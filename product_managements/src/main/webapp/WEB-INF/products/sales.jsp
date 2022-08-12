<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Dashboard 1 | Zircos - Responsive Bootstrap 4 Admin Dashboard</title>
    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>
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
                                <ol class="breadcrumb m-0">
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Zircos</a></li>
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Dashboard </a></li>
                                    <li class="breadcrumb-item active">Dashboard</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Dashboard</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->


                <!-- end row -->

                <!-- end row -->
                <%--// giữ lại--%>
                <div class="row">
                    <div class="col-xl-12">
                        <div class="card-box">
                            <h4 class="header-title mb-4">Recent Users</h4>

                            <div class="table-responsive">
                                <table class="table table-hover table-centered m-0">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Product Name</th>
                                        <th>Category</th>
                                        <th>Quantity</th>

                                        <%--                                        <th>Date</th>--%>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th>
                                            <img src="assets\images\users\avatar-1.jpg" alt="user"
                                                 class="avatar-sm rounded-circle">
                                        </th>
                                        <td>
                                            <h5 class="m-0 font-15">Louis Hansen</h5>
                                            <p class="m-0 text-muted"><small>Web designer</small></p>
                                        </td>
                                        <td>+12 3456 789</td>
                                        <td>USA</td>
                                        <td>07/08/2016</td>
                                    </tr>

                                    <tr>
                                        <th>
                                            <img src="assets\images\users\avatar-2.jpg" alt="user"
                                                 class="avatar-sm rounded-circle">
                                        </th>
                                        <td>
                                            <h5 class="m-0 font-15">Craig Hause</h5>
                                            <p class="m-0 text-muted"><small>Programmer</small></p>
                                        </td>
                                        <td>+89 345 6789</td>
                                        <td>Canada</td>
                                        <td>29/07/2016</td>
                                    </tr>

                                    <tr>
                                        <th>
                                            <img src="assets\images\users\avatar-3.jpg" alt="user"
                                                 class="avatar-sm rounded-circle">
                                        </th>
                                        <td>
                                            <h5 class="m-0 font-15">Edward Grimes</h5>
                                            <p class="m-0 text-muted"><small>Founder</small></p>
                                        </td>
                                        <td>+12 29856 256</td>
                                        <td>Brazil</td>
                                        <td>22/07/2016</td>
                                    </tr>

                                    <tr>
                                        <th>
                                            <img src="assets\images\users\avatar-4.jpg" alt="user"
                                                 class="avatar-sm rounded-circle">
                                        </th>
                                        <td>
                                            <h5 class="m-0 font-15">Bret Weaver</h5>
                                            <p class="m-0 text-muted"><small>Web designer</small></p>
                                        </td>
                                        <td>+00 567 890</td>
                                        <td>USA</td>
                                        <td>20/07/2016</td>
                                    </tr>

                                    <tr>
                                        <th>
                                            <img src="assets\images\users\avatar-5.jpg" alt="user"
                                                 class="avatar-sm rounded-circle">
                                        </th>
                                        <td>
                                            <h5 class="m-0 font-15">Mark</h5>
                                            <p class="m-0 text-muted"><small>Web design</small></p>
                                        </td>
                                        <td>+91 123 456</td>
                                        <td>India</td>
                                        <td>07/07/2016</td>
                                    </tr>

                                    </tbody>
                                </table>

                            </div>
                            <!-- table-responsive -->
                        </div>
                        <!-- end card -->
                    </div>
                    <!-- end col -->


                    <!-- end col -->

                </div>
                <!-- end row -->

            </div>
            <!-- end container-fluid -->

        </div>
        <!-- end content -->


        <!-- Footer Start -->
        <jsp:include page="/WEB-INF/layout/footer.jsp"></jsp:include>
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
    <jsp:param name="page" value="sales"/>
</jsp:include>

</body>

</html>