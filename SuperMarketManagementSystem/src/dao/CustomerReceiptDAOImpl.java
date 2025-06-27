package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.CustomerReceipt;
import application.Database;

public class CustomerReceiptDAOImpl implements CustomerReceiptDAO {
	private Connection connection;
	private Statement statement;
	private PreparedStatement ps;
	@Override
	public CustomerReceipt get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerReceipt> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(CustomerReceipt t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(CustomerReceipt customerReceipt) throws SQLException {
		int result = -1;
		String sql = "INSERT INTO customer_receipt(customer_id,total,date) "
    			+ "VALUES(?,?,?)";
		connection = Database.getConnection();
		ps = connection.prepareStatement(sql);
		ps.setString(1, String.valueOf(customerReceipt.getCustomerId()));
		ps.setString(2, String.valueOf(customerReceipt.getTotal()));
		ps.setString(3, String.valueOf(new java.sql.Date(customerReceipt.getDate().getTime())));
		ps.executeUpdate();
		return result;
	}

	@Override
	public int update(CustomerReceipt t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(CustomerReceipt t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double incomeToday() throws SQLException {
		Double result = -1.0;
		Date date = new Date();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		String sql = "SELECT SUM(total) FROM customer_receipt WHERE date = '"
    			+ sqlDate + "'";
		connection = Database.getConnection();
		statement = connection.createStatement();
    	ResultSet rs = statement.executeQuery(sql);
    		if(rs.next()) {
    			result = rs.getDouble("SUM(total)");
    		}
		return result;
	}

	@Override
	public Double totalIncome() throws SQLException {
		Double total = -1.0;
    	String sql = "SELECT SUM(total) FROM customer_receipt";
    	connection = Database.getConnection();
		statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		if(result.next()) {
			total = result.getDouble("SUM(total)");
		}
		return total;
	}

	@Override
	public Map<String,Integer> getData() throws SQLException {
		Map<String,Integer> list = new HashMap<String,Integer>();
    	String sql = "SELECT date, SUM(total) FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 9";
    	connection = Database.getConnection();
    	ps = connection.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			list.put(result.getString(1), result.getInt(2));
		}
		return list;
	}

	@Override
	public Integer getCustomerId() throws SQLException {
		Integer customerId = -1;
		String query = "SELECT customer_id FROM customer_receipt";
		connection = Database.getConnection();
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while(rs.next()) {
			customerId = rs.getInt("customer_id");
		}
		return customerId;
	}

}
