package dao;

import java.sql.SQLException;

import application.Customer;

public interface CustomerDAO extends DAO<Customer> {
	Integer getCustomerId() throws SQLException;
	double getTotal(Customer customer) throws SQLException;
	Customer getCustomer(int customerId) throws SQLException;
}
