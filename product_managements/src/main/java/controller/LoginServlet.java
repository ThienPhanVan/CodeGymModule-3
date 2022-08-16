package controller;

import dao.IProductDAO;
import dao.ProductDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IProductDAO ProductDAO = new ProductDAO();
        String username = "";
        String password = "";
        boolean rememberme = false;
        if(req.getParameter("username")!=null){
            username = req.getParameter("username");
        }
        if(req.getParameter("password")!=null){
            password = req.getParameter("password");
        }
        if(req.getParameter("rememberme")!=null){
            if(req.getParameter("rememberme")!=null){
                rememberme = Boolean.parseBoolean(req.getParameter("rememberme"));
            }
            System.out.println("rememberme: " + rememberme);
        }

        if(ProductDAO.checkProductsExists(username, password)){
            if(rememberme){
                Cookie cookieProductName = new Cookie("username", username);
                cookieProductName.setMaxAge(24*60*60);
                Cookie cookiePassword = new Cookie("password", password);
                cookiePassword.setMaxAge(24*60*60);

                resp.addCookie(cookieProductName);
                resp.addCookie(cookiePassword);
            }
            resp.sendRedirect("/product");
        }else{
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/products/login.jsp");
            requestDispatcher.forward(req, resp);
        }

    }

    @Override
    public void init() throws ServletException {

    }
}
