package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Database;
import application.Employee;
import application.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ProductDAOImpl implements ProductDAO {
	private Connection connection = null;
	private PreparedStatement ps = null;
	private Statement statement;
	private ResultSet rs;
	@Override
	public Product get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAll() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		String sql = "SELECT * FROM product";
    	connection = Database.getConnection();
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			Product product = new Product(rs.getString("product_id")
					,rs.getString("brand")
					,rs.getString("product_name")
					,rs.getString("status")
					,rs.getDouble("price"));
			products.add(product);
		}
		return products;
	}

	@Override
	public int save(Product t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Product product) throws SQLException {
		String insertProduct = "INSERT INTO product"
    			+ "(product_id,brand,product_name,status,price)"
    			+ " VALUES(?,?,?,?,?)";
    	connection = Database.getConnection();
    	//IF THE product_id is same on the data exist, then we will block that
    	ps = connection.prepareStatement(insertProduct);
		ps.setString(1, product.getProductId());
		ps.setString(2,product.getBrand());
		ps.setString(3,product.getProductName());
		ps.setString(4,(String)product.getStatus());
		ps.setString(5,String.valueOf(product.getPrice()));
    	int result = ps.executeUpdate();
		return result;
	}

	@Override
	public int update(Product product) throws SQLException {
		int result = 0;
		String updateProduct = "UPDATE product SET brand = '"
    			+product.getBrand()+"', product_name = '"
    			+product.getProductName()+"', status = '"
    			+product.getStatus()+"', price = '"
    			+product.getPrice()+"' WHERE product_id = '"
    			+product.getProductId()+"'";
		connection = Database.getConnection();
		statement = connection.createStatement();
		result = statement.executeUpdate(updateProduct);
		return result;
	}

	@Override
	public int delete(Product product) throws SQLException {
		int result = 0;
		String deleteProduct = "DELETE FROM product WHERE product_id = '"
    			+product.getProductId()+"'";
    	connection = Database.getConnection();
    	Alert alert;
		//CHECK IF THE FIELDS ARE EMPTY
		if(product.getProductId().isEmpty()
				|| product.getBrand().isEmpty()
				|| product.getProductName().isEmpty()
				|| product.getStatus() == null
				|| product.getPrice() == null){
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();
		}else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to DELETE Product ID: " + product.getProductId() + "?");
			Optional<ButtonType> option = alert.showAndWait();
			//IF YOU CLICK OR
			if(option.get().equals(ButtonType.OK)) {
				ps = connection.prepareStatement(deleteProduct);
				result = ps.executeUpdate();
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Message");
				alert.setHeaderText(null);
				alert.setContentText("Successfully Deleted!");
				alert.showAndWait();
			}else {
				return 0;
			}
		}
		return result;
	}
	
	public boolean isExist(Product product) throws SQLException {
		boolean bool = false;
		String check = "SELECT product_id FROM product WHERE product_id = '"
				+product.getProductId()+"'";
		connection = Database.getConnection();
		Statement statement = connection.createStatement();
		rs = statement.executeQuery(check);
		if(rs.next()) {
			bool = true;
		}
		return bool;
	}

	@Override
	public Double getPrice(Product product) throws SQLException {
		Double result = -1.0;
		String gPrice = "SELECT price FROM product WHERE product_name = '"
    			+ product.getProductName()+"'";
		connection = Database.getConnection();
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(gPrice);
		if(rs.next()) {
			result = rs.getDouble("price");
		}
		return result;
	}

	@Override
	public List<String> getBrandName(Product product) throws SQLException {
		List<String> result = new ArrayList<String>();
		String searchB = "SELECT * FROM product WHERE brand = '"
    			+ product.getBrand()+"' and status = 'Available'";
		connection = Database.getConnection();
		ps = connection.prepareStatement(searchB);
		rs = ps.executeQuery();
		while(rs.next()) {
			result.add(rs.getString("product_name"));
		}
		return result;
	}

}
