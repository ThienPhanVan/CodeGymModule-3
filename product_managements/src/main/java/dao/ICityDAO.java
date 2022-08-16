package dao;

import model.Category;
import model.City;

import java.sql.SQLException;
import java.util.List;

public interface ICityDAO {
    public void insertCity(City city) throws SQLException;

    public Category selectCity(int id);

    public List<City> selectAllCities();

    public boolean deleteCity(int id) throws SQLException;

    public boolean updateCity(City city) throws SQLException;
}
