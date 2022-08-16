package controller;

import dao.CategoryDAO;
import dao.ICategoryDAO;
import dao.IProductDAO;
import dao.ProductDAO;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.validation.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.*;
import java.util.*;


@WebServlet(name = "ProducServlet", urlPatterns = "/product")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProducServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IProductDAO iProductDAO;
    ICategoryDAO iCategoryDAO;

    @Override
    public void init() throws ServletException {
        iProductDAO = new ProductDAO();
        iCategoryDAO = new CategoryDAO();
        if (this.getServletContext().getAttribute("listCategory") == null) {
            this.getServletContext().setAttribute("listCategory", iCategoryDAO.selectAllCategories());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showFormCreate(req, resp);
                    break;
                case "edit":
                    showFormUpdate(req, resp);
                    break;
                case "delete":
                    deleteProduct(req, resp);
                    break;
                case "login":
                    showLogin(req, resp);
                    break;
                default:
                    listProductPaging(req, resp);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void listProductPaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Servlet location: " + getServletContext().getRealPath("/"));
        int page = 1;
        int recordsPerPage = 5;
        String q = "";
        int idcategory = -1;

        if (request.getParameter("q") != null) {
            System.out.println("aaaaaaaaaa");
            q = request.getParameter("q");
        }
        if (request.getParameter("idcategory") != null) {
            idcategory = Integer.parseInt(request.getParameter("idcategory"));
        }

        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<Product> productList = iProductDAO.selectProductsPaging((page - 1) * recordsPerPage, recordsPerPage, q);
        int noOfRecords = iProductDAO.getNoOfRecords();

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("productList", productList);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        request.setAttribute("q", q);
        request.setAttribute("idcategory", idcategory);
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/products/list.jsp");
        view.forward(request, response);
    }


    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product existingProduct = iProductDAO.selectProduct(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/edit.jsp");
        req.setAttribute("product", existingProduct);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(req, resp);
                    break;
                case "edit":
                    updateProduct(req, resp);
                    break;
                case "delete":
//                showSalesPage(req, resp);
                    break;
                case "login":
                    showLogin(req, resp);
                    break;

                default:
                    listProductPaging(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/login.jsp");
        requestDispatcher.forward(req, resp);
//        resp.sendRedirect("/WEB-INF/products/login.jsp");
    }

    public void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        iProductDAO.deleteProduct(id);

        List<Product> productList = iProductDAO.selectAllProducts();
        req.setAttribute("productList", productList);
        resp.sendRedirect("/product");
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        Product product = null;
        HashMap<String, List<String>> errors = new HashMap<>();
        String name = "";
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            name = req.getParameter("name");
            int idcategory = Integer.parseInt(req.getParameter("idcategory"));
            BigDecimal price = BigDecimal.valueOf(0);
            int quantity = 0;
            if (!req.getParameter("quantity").equals("")) {
                quantity = Integer.parseInt(req.getParameter("quantity"));
            }
            if (!req.getParameter("price").equals("")) {
                price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
            }
            product = new Product(id, name, idcategory, quantity, price);
            System.out.println("Product edit info: " + product);


            for (Part part : req.getParts()) {
                System.out.println("Content type of Part" + part.getContentType());
                System.out.println("Name of Part" + part.getName());
                if (part.getName().equals("file")) {
                    String fileName = extractFileName(part);
                    // refines the fileName in case it is an absolute path
                    fileName = new File(fileName).getName();

                    if(!fileName.equals("")){
                        part.write("D:\\codegym\\module_3\\casestudymodule3\\product_managements\\src\\main\\webapp\\images\\" + fileName);
                        String servletRealPath = this.getServletContext().getRealPath("/") + "images\\" + fileName;
                        System.out.println("servletRealPath: " + servletRealPath);
                        part.write(servletRealPath);
                        product.setUrlImage("images\\" + fileName);
                    }else {
                        Product oldProduct = iProductDAO.selectProduct(product.getId());
                        product.setUrlImage(oldProduct.getUrlImage());
                    }
                }

            }


            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);


            if (!constraintViolations.isEmpty()) {

                req.setAttribute("errors", "MUST NOT BE BRANDED!");
                errors = getErrorFromContraint(constraintViolations);
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/edit.jsp");
                requestDispatcher.forward(req, resp);
            } else {

                iProductDAO.updateProduct(product);
                resp.sendRedirect(req.getContextPath() + "/product");
            }
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + product);

            product = new Product();
            product.setName(name);

            List<String> listErrors = Arrays.asList("Number format not right!");
            errors.put("Exception", listErrors);

            req.setAttribute("product", product);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/products/create.jsp").forward(req, resp);
        }
        ;

    }

    private void showListProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList = iProductDAO.selectAllProducts();
        req.setAttribute("productList", productList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/list.jsp");
        dispatcher.forward(req, resp);
    }


    private void insertProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Product product = null;
        HashMap<String, List<String>> errors = new HashMap<>();
        String name = "";

        try {

            name = req.getParameter("name");
            int idcategory = Integer.parseInt(req.getParameter("idcategory"));
            BigDecimal price = BigDecimal.valueOf(0);
            int quantity = 0;
            if (!req.getParameter("quantity").equals("")) {
                quantity = Integer.parseInt(req.getParameter("quantity"));
            }
            if (!req.getParameter("price").equals("")) {
                price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
            }
            product = new Product(name, idcategory, quantity, price);
            System.out.println("Product insert info: " + product);


            for (Part part : req.getParts()) {
                System.out.println("Content type of Part" + part.getContentType());
                System.out.println("Name of Part" + part.getName());
                if (part.getName().equals("file")) {
                    String fileName = extractFileName(part);
                    fileName = new File(fileName).getName();
                    if(!fileName.equals("")){
                        part.write("D:\\codegym\\module_3\\casestudymodule3\\product_managements\\src\\main\\webapp\\images\\" + fileName);
                        String servletRealPath = this.getServletContext().getRealPath("/") + "images\\" + fileName;
                        System.out.println("servletRealPath: " + servletRealPath);
                        part.write(servletRealPath);
                        product.setUrlImage("images\\" + fileName);
                    }else{
                        product.setUrlImage("images\\default.jpg");
                    }
                }
            }
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);


            if (!constraintViolations.isEmpty()) {

                errors = getErrorFromContraint(constraintViolations);
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/create.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                iProductDAO.insertProduct(product);
                Product pro = new Product();
                req.setAttribute("product", pro);
                int noOfRecords = iProductDAO.getNoOfRecords();

                int noOfPages = (int) Math.ceil(noOfRecords * 1.08/5);
                String path = "/product?page="+noOfPages;

                req.setAttribute("success", "More successful products!");
//                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/list.jsp");
//                requestDispatcher.forward(req, resp);
                resp.sendRedirect(path);
            }

        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + product);

            product = new Product();
            product.setName(name);

            List<String> listErrors = Arrays.asList("Number format not right!");
            errors.put("Exception", listErrors);

            req.setAttribute("product", product);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/products/create.jsp").forward(req, resp);
        }
    }


    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<Product>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (ConstraintViolation<Product> c : constraintViolations) {
            if (hashMap.keySet().contains(c.getPropertyPath().toString())) {
                hashMap.get(c.getPropertyPath().toString()).add(c.getMessage());
            } else {
                List<String> listMessages = new ArrayList<>();
                listMessages.add(c.getMessage());
                hashMap.put(c.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        System.out.println(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }

}
