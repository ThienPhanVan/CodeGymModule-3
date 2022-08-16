package dao;

import model.Product;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/product_managements?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Weak";

    //INSERT INTO `c3_usermanager`.`users` (`name`, `email`, `country`) VALUES (?, ?, ?);
    private static final String INSERT_USERS_SQL = "INSERT INTO users (full_name,age,phone ,email, password,id_address) VALUES (?, ?, ?, ?, ?, ?);";
    //SELECT * FROM c3_usermanager.users where id = ?;
    private static final String SELECT_USER_BY_ID = "select id_user,full_name,age,phone ,email, password,id_address from users where id_user =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id_user = ?;";
    private static final String UPDATE_USERS_SQL = "update users set full_name=?,age=?,phone=? ,email=?, password=?,id_address=? where id_user = ?;";

    public UserDAO() {
    }
    private int noOfRecords;
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

    public void insertUser(User user) throws SQLException {
        System.out.println(INSERT_USERS_SQL);
        // try-with-resource statement will auto close the connection.
        try (
                Connection connection = getConnection();
                //INSERT INTO users (name, email, country) VALUES (?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)
        ) {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPasswordUser());
            preparedStatement.setInt(6, user.getAddress());



            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public User selectUser(int id) {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             //SELECT * FROM c3_usermanager.users where id = ?
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("full_name");
                int age = Integer.parseInt(rs.getString("age"));
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int address = Integer.parseInt(rs.getString("id_address"));
                user = new User( name,age,phone, email,password, address);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> selectAllUsers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id_user");
                String name = rs.getString("full_name");
                int age = Integer.parseInt(rs.getString("age"));
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int address = Integer.parseInt(rs.getString("id_address"));
                users.add(new User(id, name, age,phone,email,password,address ));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getFullName());
            statement.setInt(2, user.getAge());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPasswordUser());
            statement.setInt(6, user.getAddress());

            statement.setInt(7, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    @Override
    public List<User> selectProductsPaging(int offset, int recordsPerPage, String q) {
        String query = "select SQL_CALC_FOUND_ROWS * from users where full_name like ? limit "
                + offset + ", " + recordsPerPage;
        List<User> userList= new ArrayList<User>();
        User user=null;

        Connection connection=null;
        PreparedStatement stmt=null;
        try{
            connection =getConnection();

            stmt = connection.prepareStatement(query);
            stmt.setString(1,'%' + q + '%');
            System.out.println(stmt);
            ResultSet resultSet=stmt.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id_user"));
                user.setFullName(resultSet.getString("full_name"));
                user.setAge(resultSet.getInt("age"));
                user.setPhone(resultSet.getString("phone"));
                user.setEmail(resultSet.getString("email"));
                user.setPasswordUser(resultSet.getString("password"));
                user.setAddress(resultSet.getInt("id_address"));


                userList.add(user);
            }
            resultSet.close();
            resultSet=stmt.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords=resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection!= null)
                    connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return userList;
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
