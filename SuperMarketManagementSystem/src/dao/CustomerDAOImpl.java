package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import application.Customer;
import application.Database;

public class CustomerDAOImpl implements CustomerDAO {
	private Connection connection;
	private PreparedStatement ps;
	private Statement statement;
	private ResultSet rs;
	@Override
	public Customer get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Customer t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Customer customer) throws SQLException {
		int result = 0;
		String insertProd = "INSERT INTO customer"
    			+ "(customer_id,brand,productName,quantity,price,date)"
    			+ " VALUES(?,?,?,?,?,?)";
		connection = Database.getConnection();
		ps = connection.prepareStatement(insertProd);
		ps.setString(1,String.valueOf(customer.getCustomerId()));
		ps.setString(2,customer.getBrand());
		ps.setString(3,customer.getProductName());
		ps.setString(4,String.valueOf(customer.getQuantity()));
		ps.setString(5,String.valueOf(customer.getPrice()));
		ps.setString(6,String.valueOf(new Date(customer.getDate().getTime())));
		result = ps.executeUpdate();
		return result;
	}

	@Override
	public int update(Customer t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Customer customer) throws SQLException {
		int result = -1;
		String resetData = "DELETE FROM customer WHERE customer_id = '"
    			+ customer.getCustomerId() +"'";
		connection = Database.getConnection();
		statement = connection.createStatement();
		result = statement.executeUpdate(resetData);
		return result;
	}

	@Override
	public Integer getCustomerId() throws SQLException {
		Integer customerId = -1;
		String cID = "SELECT customer_id FROM customer";
    	connection = Database.getConnection();
    	ps = connection.prepareStatement(cID);
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			//cID.FINAL ROW OF CUSTOMER IF
			customerId = result.getInt("customer_id");
		}
		return customerId;
	}

	@Override
	public double getTotal(Customer customer) throws SQLException {
		double result = -1;
		String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '"
    			+ customer.getCustomerId() + "'";
		connection = Database.getConnection();
		statement = connection.createStatement();
		rs = statement.executeQuery(sql);
		if(rs.next()) {
			result = rs.getDouble("SUM(price)");
		}
		return result;
	}

	@Override
	public Customer getCustomer(int customerId) throws SQLException {
		Customer customer = null;
    	String sql = "SELECT * FROM customer WHERE customer_id = '"+customerId+"'";
    	connection = Database.getConnection();
    	ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			customer = new Customer(rs.getInt("customer_id")
					, rs.getString("brand")
					, rs.getString("productName")
					, rs.getInt("quantity")
					, rs.getDouble("price")
					, rs.getDate("date"));
		}
		return customer;
	}

}
