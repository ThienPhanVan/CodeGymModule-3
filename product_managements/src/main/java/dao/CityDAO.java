package dao;

import model.Category;
import model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements ICityDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/product_managements?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Weak";

    private static final String INSERT_CITY_SQL = "INSERT INTO city (name) VALUES (?);";
    private static final String SELECT_CITY_BY_ID = "select name from city where idcity =?;";
    private static final String SELECT_ALL_CITIES = "select * from city";
    private static final String DELETE_CITIES_SQL = "delete from city where idcity = ?;";
    private static final String UPDATE_CITIES_SQL = "update city set name = ? where idcity = ?;";

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
    public void insertCity(City city) throws SQLException {
        System.out.println(INSERT_CITY_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CITY_SQL)) {

//			Statement // Không có tham so
//			PreparedStatement // Co tham số, bắt đầu từ 1, 2 , 3...
//			CallableStatement // Làm việc với store procedure
//
            preparedStatement.setString(1, city.getAddress());


            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            printSQLException(e);
        }


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

    @Override
    public Category selectCity(int id) {
        return null;
    }

    @Override
    public List<City> selectAllCities() {
        List<City> cities = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CITIES);) {
            System.out.println(this.getClass() + " selectAllUsers() + statement: " + preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("idcity");
                String name = rs.getString("address");
                cities.add(new City(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return cities;

    }

    @Override
    public boolean deleteCity(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean updateCity(City city) throws SQLException {
        return false;
    }
}
