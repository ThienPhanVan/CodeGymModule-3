package controller;

import dao.CategoryDAO;
import dao.ICategoryDAO;
import dao.IProductDAO;
import dao.ProductDAO;
import model.Category;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@WebServlet(name ="CategoryServlet" , urlPatterns = "/category")

public class CategoryServlet extends HttpServlet {
    ICategoryDAO iCategoryDAO;
    IProductDAO iProductDAO;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if(action==null){
            action ="";
        }
        switch (action){
            case "create":
                showFormCreate(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "sales":
                showSalesPage(req, resp);
                break;
            default:
                showListCategory(req, resp);

        }
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category existingCategory = iCategoryDAO.selectCategory(id);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/products/edit.jsp");
        request.setAttribute("category", existingCategory);

        dispatcher.forward(request, response);

    }
    private void showListCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showSalesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/sales.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null){
            action ="";
        }
        switch (action){
            case "create":
                insertCategory(req, resp);
                break;
            case "edit":

                break;
            case "sales":
                showSalesPage(req, resp);
                break;
            default:
                showListCategories(req, resp);

        }
    }

    private void showListCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = iCategoryDAO.selectAllCategories();
        req.setAttribute("categories", categories);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/products/edit.jsp");
        dispatcher.forward(req, resp);
    }

    private void insertCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        Category category = new Category(name);



        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);

        if(!constraintViolations.isEmpty()){


            //req.setAttribute("errors", getErrorFromContraint(constraintViolations));
            req.setAttribute("errors", getErrorFromContraint(constraintViolations));
            req.setAttribute("category", category);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/create.jsp");
            requestDispatcher.forward(req, resp);
        }else{

            req.setAttribute("success", "Insert success xxx");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/user/create.jsp");
            requestDispatcher.forward(req, resp);
        }

    }

    private HashMap<String, List<String>> getErrorFromContraint(Set<ConstraintViolation<Category>> constraintViolations) {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for(ConstraintViolation<Category> c : constraintViolations){
            if(hashMap.keySet().contains(c.getPropertyPath().toString())){
                hashMap.get(c.getPropertyPath().toString()).add(c.getMessage());
            }else{
                List<String> listMessages = new ArrayList<>();
                listMessages.add(c.getMessage());
                hashMap.put(c.getPropertyPath().toString(), listMessages);
            }
        }
        return hashMap;
    }

}
