package dao;

import model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/product_managements?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Weak";

    private static final String INSERT_PRODUCTS_SQL = "INSERT INTO product" + "  (name, idcategory, quantity,price,urlImage) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String SELECT_PRODUCTS_BY_ID = "select id,name,idcategory,quantity,price,urlImage from product where id =?";
    private static final String SELECT_ALL_PRODUCTS = "select * from product";
    private static final String DELETE_PRODUCTS_SQL = "delete from product where id = ?;";
    private static final String UPDATE_PRODUCTS_SQL = "update product set name = ?,idcategory= ?, quantity =?,price=?,urlImage=?  where id = ?;";

    private int noOfRecords;

    public ProductDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertProduct(Product product) throws SQLException {
        System.out.println(INSERT_PRODUCTS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL)) {

//			Statement // Không có tham so
//			PreparedStatement // Co tham số, bắt đầu từ 1, 2 , 3...
//			CallableStatement // Làm việc với store procedure
//
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getIdcategory());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setBigDecimal(4, product.getPrice());
            preparedStatement.setString(5, product.getUrlImage());


            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    @Override
    public Product selectProduct(int id) {
        Product product = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCTS_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                int idcategory = Integer.parseInt(rs.getString("idcategory"));
                int quantity = Integer.parseInt(rs.getString("quantity"));
                BigDecimal price = rs.getBigDecimal("price");
                String urlImage = rs.getString("urlImage");
                product = new Product(id, name, idcategory, quantity, price, urlImage);


            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;

    }

    @Override
    public List<Product> selectAllProducts() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            System.out.println(this.getClass() + " selectAllUsers() + statement: " + preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int idCategory = rs.getInt("idcategory");
                int quantity = rs.getInt("quantity");
                BigDecimal price = rs.getBigDecimal("price");
                String urlImage = rs.getString("urlImage");
                products.add(new Product(id, name, idCategory, quantity, price, urlImage));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;

    }


    public int getNoOfRecords() {
        return noOfRecords;
    }

    public boolean checkProductsExists(String username, String password) {
        if (username.equals("username") && password.equals("password")) {
            return false;
        } else {

            return true;
        }
    }

    @Override
    public List<Product> selectProductsPaging(int offset, int noOfRecords, String q) {

        String query = "select SQL_CALC_FOUND_ROWS * from product where name like ? limit "
                + offset + ", " + noOfRecords;
        List<Product> list = new ArrayList<Product>();
        Product product = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = getConnection();

            stmt = connection.prepareStatement(query);
            stmt.setString(1, '%' + q + '%');
            System.out.println(stmt);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setIdcategory(resultSet.getInt("idcategory"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setUrlImage(resultSet.getString("urlImage"));

                list.add(product);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Product> selectProductsPading(int offset, int noOfRecords) {
        String query = "select SQL_CALC_FOUND_ROWS * from product limit "
                + offset + ", " + noOfRecords;
        List<Product> list = new ArrayList<Product>();
        Product product = null;

        Connection connection = null;
        Statement stmt = null;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setIdcategory(rs.getInt("idcategory"));
                product.setQuantity(rs.getInt("quantity"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setUrlImage(rs.getString("urlImage"));
                list.add(product);
            }
            rs.close();

            rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if (rs.next())
                this.noOfRecords = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getIdcategory());
            statement.setInt(3, product.getQuantity());
            statement.setBigDecimal(4, product.getPrice());
            statement.setString(5, product.getUrlImage());

            statement.setInt(6, product.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
