package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Employee;

public interface EmployeeDAO extends DAO<Employee> {
	boolean authenticate(Employee employee) throws SQLException;
	boolean isExist(Employee employee) throws SQLException;
	int count() throws SQLException;
}
