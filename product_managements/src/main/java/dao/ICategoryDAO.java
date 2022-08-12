package dao;

import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDAO {
    public void insertCategory(Category category) throws SQLException;

    public Category selectCategory(int id);

    public List<Category> selectAllCategories();

    public boolean deleteCategory(int id) throws SQLException;

    public boolean updateCategory(Category category) throws SQLException;
}
