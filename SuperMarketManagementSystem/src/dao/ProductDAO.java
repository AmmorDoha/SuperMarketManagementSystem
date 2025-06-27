package dao;

import java.sql.SQLException;
import java.util.List;

import application.Product;

public interface ProductDAO extends DAO<Product> {
	boolean isExist(Product product) throws SQLException;
	Double getPrice(Product product) throws SQLException;
	List<String> getBrandName(Product product) throws SQLException;
}
