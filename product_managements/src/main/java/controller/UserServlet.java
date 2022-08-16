package controller;

import dao.*;
import model.Product;
import model.User;

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
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IUserDAO iUserDAO;
    ICityDAO iCityDAO;

    public void init() {
        iUserDAO = new UserDAO();
        iCityDAO = new CityDAO();
        if (this.getServletContext().getAttribute("listCities") == null) {
            this.getServletContext().setAttribute("listCities", iCityDAO.selectAllCities());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                default:
                listUser(request, response);
                break;

            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
//        List<User> listUser = iUserDAO.selectAllUsers();
//        request.setAttribute("listUser", listUser);
//
//
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/users/listUser.jsp");
//        dispatcher.forward(request, response);

        int page = 1;
        int recordsPerPage = 5;
        String q = "";
        int address = -1;

        if (request.getParameter("q") != null) {
            System.out.println("aaaaaaaaaa");
            q = request.getParameter("q");
        }
        if (request.getParameter("address") != null) {
            address = Integer.parseInt(request.getParameter("address"));
        }

        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<User> listUser = iUserDAO.selectProductsPaging((page - 1) * recordsPerPage, recordsPerPage, q);
        int noOfRecords = iUserDAO.getNoOfRecords();

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("listUser", listUser);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        request.setAttribute("q", q);
//        request.setAttribute("idcategory", idcategory);
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/users/listUser.jsp");
        view.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/users/createUser.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = iUserDAO.selectUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/users/editUser.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);

    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<String> errors = new ArrayList<>();
        String name = "";
        String phone = "";
        String email = "";
        User newUser = null;
        try {
             name = request.getParameter("fullName");
             phone = request.getParameter("phone");
             email = request.getParameter("email");
            int age = Integer.parseInt(request.getParameter("age"));
            String passwordUser = request.getParameter("passwordUser");
            int address = Integer.parseInt(request.getParameter("address"));
            newUser = new User(name, age,phone,email, passwordUser,address);

            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<User>> constraintViolations = validator.validate(newUser);

            if (!constraintViolations.isEmpty()) {
                errors = getErrorFromValidator(constraintViolations);

                request.setAttribute("errors", errors);
                request.setAttribute("user", newUser);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/users/createUser.jsp");
                requestDispatcher.forward(request, response);

            }else{
                // Them thanh cong
                iUserDAO.insertUser(newUser);
                User user = new User();

                request.setAttribute("user", user);
                request.setAttribute("success", "More successful products!");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/users/createUser.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + newUser);

            newUser = new User();
            newUser.setFullName(name);
            newUser.setPasswordUser(phone);
            newUser.setEmail(email);

            List<String> listErrors = Arrays.asList("Number format not right!");


            request.setAttribute("user", newUser);
            request.setAttribute("errors", listErrors);
            request.getRequestDispatcher("/WEB-INF/users/createUser.jsp").forward(request, response);
        }
    }

    private List<String> getErrorFromValidator(Set<ConstraintViolation<User>> constraintViolations) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<User> c : constraintViolations) {
            errors.add(c.getMessage());
        }
        return errors;
    }


    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        List<String> errors = new ArrayList<>();
        String name = "";
        String phone = "";
        String email = "";
        User newUser = null;
        int id = 0;
        try {
            name = request.getParameter("fullName");
            phone = request.getParameter("phone");
            email = request.getParameter("email");
            id = Integer.parseInt(request.getParameter("id"));
            int age = Integer.parseInt(request.getParameter("age"));
            String passwordUser = request.getParameter("passwordUser");
            int address = Integer.parseInt(request.getParameter("address"));

            newUser = iUserDAO.selectUser(id);

            newUser.setId(id);
            newUser.setFullName(name);
            newUser.setAge(age);
            newUser.setPhone(phone);
            newUser.setAddress(address);
            newUser.setEmail(email);
            newUser.setPasswordUser(passwordUser);
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            Validator validator = validatorFactory.getValidator();

            Set<ConstraintViolation<User>> constraintViolations = validator.validate(newUser);

            if (!constraintViolations.isEmpty()) {
                errors = getErrorFromValidator(constraintViolations);

                request.setAttribute("errors", errors);
                request.setAttribute("user", newUser);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/users/editUser.jsp");
                requestDispatcher.forward(request, response);

            }else{
                iUserDAO.updateUser(newUser);
                response.sendRedirect("/user");
            }
        } catch (NumberFormatException ex) {
            System.out.println(this.getClass() + " NumberFormatException: User info from request: " + newUser);

            newUser = iUserDAO.selectUser(id);

            List<String> listErrors = Arrays.asList("Number format not right!");

            request.setAttribute("user", newUser);
            request.setAttribute("errors", listErrors);
            request.getRequestDispatcher("/WEB-INF/users/editUser.jsp").forward(request, response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        iUserDAO.deleteUser(id);

        List<User> listUser = iUserDAO.selectAllUsers();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/users/listUser.jsp");
        dispatcher.forward(request, response);
    }
}
