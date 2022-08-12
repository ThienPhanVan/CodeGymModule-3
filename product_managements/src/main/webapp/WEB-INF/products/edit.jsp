<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

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
                <a href="/product" class="btn btn-outline-primary">LIST PRODUCT</a>
              </div>
              <h4 class="page-title">Form elements</h4>
            </div>
          </div>
        </div>
        <!-- end page title -->

        <div class="row">
          <div class="col-sm-12">
            <form method="post" class="form-horizontal">
              <c:if test="${product != null}">
                <input type="hidden" name="id" value='${product.getId()}' />
              </c:if>
              <div class="form-group row">
                <label class="col-md-2 control-label">NAME</label>
                <div class="col-md-10">
                  <input type="text" class="form-control" name="name"
                         value="${product.getName()}'/>">
                </div>
              </div>
              <div class="form-group row">
                <label class="col-md-2 control-label">QUANTITY</label>
                <div class="col-md-10">
                  <input type="text" class="form-control" name="quantity"
                         value="<c:out value='${product.getQuantity()}'/>">
                </div>
              </div>
              <div class="form-group row">
                <label class="col-md-2 control-label">PRICE</label>
                <div class="col-md-10">
                  <input type="text" class="form-control" name="price"
                         value="<c:out value='${product.getPrice()}'/>">
                </div>
              </div>
              <div class="form-group row">
                <label class="col-md-2 control-label">CATEGORY</label>
                <div class="col-md-1">
                  <select style=" position: relative;padding: 7px;left: -5px;" name="idcategory">
                    <c:forEach items="${applicationScope.listCategory}" var="category">
                      <%--                                            <option value="${category.getId()}" selected>${category.getName()}</option>--%>
                      <c:choose>
                        <c:when test="${category.getId() == product.getIdcategory()}">
                          <option value="${category.getId()}" selected >${category.getName()}</option>
                        </c:when>
                        <c:otherwise>
                          <option value="${category.getId()}" >${category.getName()}</option>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                  </select>
                </div>
              </div>
<%--              <div class="form-group row">--%>
<%--                <label class="col-md-2 control-label">IMAGE</label>--%>
<%--                <div class="col-md-10">--%>
<%--                  <input type="text" class="form-control" name="image"--%>
<%--                         value="<c:out value='${product.getImage()}'/>">--%>
<%--                </div>--%>
<%--              </div>--%>


<%--              <div class="btn btn-outline-primary bg-primary" >--%>
<%--                <input type="submit" value="SAVE" class="bg-primary border-0">--%>
<%--              </div>--%>
              <div class="page-title-right">
                <input type="submit" value="SAVE" class="btn btn-outline-primary">

              </div>
            </form>
            <c:if test="${requestScope.errors!=null}">
              <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                  <span aria-hidden="true">×</span>
                </button>
                <c:forEach items="${requestScope.errors}" var="e">
                  <%--                                                <strong>${e.key}</strong> </br>--%>

                  <strong>*Field: ${fn:toUpperCase(e.key)}</strong> </br>
                  <c:forEach items="${e.value}" var="item">
                    <span>${item}</span> </br>
                  </c:forEach>
                </c:forEach>

              </div>
            </c:if>
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
    $( document ).ready(function() {
      <% String success= (String) request.getAttribute("success"); %>
      var js_Success="<%= success %>";
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