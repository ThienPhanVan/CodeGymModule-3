package controller;

import dao.CategoryDAO;
import dao.ICategoryDAO;
import dao.IProductDAO;
import dao.ProductDAO;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;


@WebServlet(name = "ProducServlet", urlPatterns = "/product")
public class ProducServlet extends HttpServlet {

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
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void listProductPaging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 5;
        String q = "";
        int idcategory = -1;

        if (request.getParameter("page") != null) {
            System.out.println("aaaaaaaaaa");
            q = request.getParameter("q");
        }
        if (request.getParameter("idcategory") != null) {
            idcategory = Integer.parseInt(request.getParameter("idcategory"));
        }


        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<Product> productList = iProductDAO.selectProductsPading((page - 1) * recordsPerPage, recordsPerPage);
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
                    showListProduct(req, resp);
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/list.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
        int idcategory = Integer.parseInt(req.getParameter("idcategory"));
        Product product = new Product(id, name, quantity, price,idcategory);

        iProductDAO.updateProduct(product);
        resp.sendRedirect(req.getContextPath() + "/product");
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
        try {
            String name = req.getParameter("name");
            int idcategory = Integer.parseInt(req.getParameter("idcategory"));
            int quantity = 0;
            if (req.getParameter("quantity") != "") {
                quantity = Integer.parseInt(req.getParameter("quantity"));
            }
            BigDecimal price = null;
            if (req.getParameter("price") != "") {
                price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));
            }

            product = new Product(name, idcategory, quantity, price);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);

            if (!constraintViolations.isEmpty()) {

                req.setAttribute("errors", "MUST NOT BE BRANDED!");
                errors = getErrorFromContraint(constraintViolations);
                req.setAttribute("errors", errors);
                req.setAttribute("product", product);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/create.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                iProductDAO.insertProduct(product);
                Product pro = new Product();
                req.setAttribute("product", pro);

                req.setAttribute("success", "More successful products!");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/create.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + product);
            List<String> listErrors = Arrays.asList("Number format not right!");
            errors.put("Exception", listErrors);

            req.setAttribute("product", product);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/products/create.jsp").forward(req, resp);
        } catch (Exception ex) {

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

}
