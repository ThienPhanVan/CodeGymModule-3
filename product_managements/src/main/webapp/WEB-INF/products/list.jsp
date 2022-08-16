<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Basic Tables | Zircos - Responsive Bootstrap 4 Admin Dashboard</title>
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
        <div class="content" >

            <!-- Start Content-->
            <div class="container-fluid">

                <!-- start page title -->
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <a href="/product?action=create" class="btn btn-outline-primary">CREATE PRODUCT</a>
                            </div>
                            <h4 class="page-title">Basic Tables</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->

                <div class="row " >
                    <div class="col-sm-12 ">
                        <div class="row">
                            <div class="table-responsive">
                                <div class="row">
<%--                                <form action="/product">--%>
<%--                                    Search: <input type="text" width="100px" hint="search" value="${requestScope.q}" name="q" placeholder="Search">--%>
<%--                                    <button type="submit" style="line-height: 1;" class="btn btn-outline-primary">Submit</button>--%>
<%--                                </form>--%>
                                </div>
                                <table class="table table-striped m-0">

                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Image</th>
                                        <th>Quantity</th>
                                        <th>Category</th>
                                        <th>Price</th>
                                        <th>Action</th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${requestScope.productList}" var="product">
                                        <tr>
                                            <td>${product.getId()}</td>
                                            <td>
                                                <a href="/product?action=edit&id=${product.getId()}">${product.getName()}</a>
                                            </td>
                                            <td><img src="${product.getUrlImage()}" width="50px" height="50px" alt="" /></td>
                                            <td>${product.getQuantity()}</td>
                                            <c:forEach items="${applicationScope.listCategory}" var="category">
                                                <c:if test="${category.getId()==product.getIdcategory()}">
                                                    <td>${category.getName()}</td>
                                                </c:if>
                                            </c:forEach>
                                            <td>
<%--                                                    ${product.getPrice()}--%>
                                                <c:set var="price" value="${product.getPrice()}"/>
                                                <fmt:setLocale value="vi_VN"/>
                                                <fmt:formatNumber value="${price}" type="currency"/>
                                            </td>
                                            <td>
                                                <a href="/product?action=edit&id=${product.getId()}"
                                                   class="btn btn-outline-orange">Edit</a>
                                                <a href="/product?action=delete&id=${product.getId()}"
                                                   class="btn btn-outline-danger">Delete</a>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <%--For displaying Previous link except for the 1st page --%>

                            </div>

                        </div>
                        <!-- end card-box -->
                    </div>
                    <!-- end col -->
                </div>

                <!-- end row -->

            </div>

            <!-- end container-fluid -->

        </div>
        <!-- end content -->

        <div class="row w-100">
            <div class="col-12  d-flex justify-content-center border-0 text-white">
                <div class="container-fluid mt-2">
                    <div class="float-right">
                        <c:if test="${currentPage!=1}">
                            <a href="product?page=${currentPage-1}&q${requestScope.q}"
                               class="p-2 mr-1 border">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq 1}">
                                    <a href="product?page=${i}&q=${requestScope.q}" class="p-2 mr-1 border">${i}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="product?page=${i}&q=${requestScope.q}" class="p-2 mr-1 border">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage<noOfPages}">
                            <a href="product?page=${currentPage+1}&q=${requestScope.q}" class="p-2 mr-1 border">Next</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>



    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- Footer Start -->
<div>


    <jsp:include page="/WEB-INF/layout/footer.jsp"></jsp:include>
</div>
<!-- end Footer -->

<!-- END wrapper -->

<jsp:include page="/WEB-INF/layout/rightbar.jsp"></jsp:include>

<jsp:include page="/WEB-INF/layout/footer_js.jsp">
    <jsp:param name="page" value="list"/>
</jsp:include>


</body>

</html>