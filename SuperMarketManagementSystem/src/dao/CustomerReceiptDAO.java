package dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import application.CustomerReceipt;

public interface CustomerReceiptDAO extends DAO<CustomerReceipt> {
	Double incomeToday() throws SQLException;
	Double totalIncome() throws SQLException;
	Map<String,Integer> getData() throws SQLException;
	Integer getCustomerId() throws SQLException;
}
