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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class EmployeeDAOImpl implements EmployeeDAO {
	private Connection connection;
	private Statement statement;

	@Override
	public Employee get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAll() throws SQLException {
		List<Employee> employees = new ArrayList<Employee>();
		String sql = "SELECT * FROM employee";
    	connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(sql);
		ResultSet result = ps.executeQuery();
		while(result.next()) {
			Employee employee = new Employee(result.getString("employee_id")
					,result.getString("password")
					,result.getString("firstName")
					,result.getString("lastName")
					,result.getString("gender")
					,result.getDate("date"));
			employees.add(employee);
		}
		return employees;
	}

	@Override
	public int save(Employee t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Employee employee) throws SQLException {
		int result = 0;
		java.sql.Date date = new java.sql.Date(employee.getDate().getTime());
		String insertEmployee = "INSERT INTO employee"
    			+ "(employee_id,password,firstName,lastName,gender,date)"
    			+ " VALUES(?,?,?,?,?,?)";
		connection = Database.getConnection();
		PreparedStatement prepare = connection.prepareStatement(insertEmployee);
		prepare.setString(1, employee.getEmployeeId());
		prepare.setString(2,employee.getPassword());
		prepare.setString(3,employee.getFirstName());
		prepare.setString(4,employee.getLastName());
		prepare.setString(5,employee.getGender());
		prepare.setString(6, String.valueOf(date));
		result = prepare.executeUpdate();
		return result;
	}

	@Override
	public int update(Employee employee) throws SQLException {
		int result = 0;
		String UpdateEmployee = "UPDATE employee SET password = '"
    			+ employee.getPassword()+"', firstName = '"
    			+ employee.getFirstName()+"', lastName = '"
    			+ employee.getLastName()+"', gender = '"
    			+ employee.getGender()
    			+"' WHERE employee_id = '" + employee.getEmployeeId() + "'";
    	connection = Database.getConnection();
    	Alert alert;
		//CHECK IF THE FIELDS ARE EMPTY
		if(employee.getEmployeeId().isEmpty()
				|| employee.getPassword().isEmpty()
				|| employee.getFirstName().isEmpty()
				|| employee.getLastName().isEmpty()
				|| employee.getGender() == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();
		}else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to UPDATE Employee ID: " + employee.getEmployeeId() + "?");
			Optional<ButtonType> option = alert.showAndWait();
			if(option.get().equals(ButtonType.OK)) {
				statement = connection.createStatement();
				result = statement.executeUpdate(UpdateEmployee);
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Message");
				alert.setHeaderText(null);
				alert.setContentText("Successful Updated!");
				alert.showAndWait();
			}else {
				return 0;
			}
		}
		return result;
	}

	@Override
	public int delete(Employee employee) throws SQLException {
		int result = 0;
		String deleteEmployee = "DELETE FROM employee WHERE employee_id = '"
    			+employee.getEmployeeId()+"'";
    	connection = Database.getConnection();
    		Alert alert;
    		//CHECK IF THE FIELDS ARE EMPTY
    		if(employee.getEmployeeId().isEmpty()
    				|| employee.getPassword().isEmpty()
    				|| employee.getFirstName().isEmpty()
    				|| employee.getLastName().isEmpty()
    				|| employee.getGender() == null){
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all blank fields");
				alert.showAndWait();
			}else {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Message");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to DELETE employee ID: " + employee.getEmployeeId() + "?");
				Optional<ButtonType> option = alert.showAndWait();
				//IF YOU CLICK OR
				if(option.get().equals(ButtonType.OK)) {
					statement = connection.createStatement();
					result = statement.executeUpdate(deleteEmployee);
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
	
	@Override
	public boolean authenticate(Employee employee) throws SQLException {
		boolean bool = false;
		String adminData = "SELECT * FROM employee WHERE employee_id = ? and password = ?";
		connection = Database.getConnection();
		PreparedStatement prepare = connection.prepareStatement(adminData);
		prepare.setString(1, employee.getEmployeeId());
		prepare.setString(2, employee.getPassword());
		ResultSet result = prepare.executeQuery();
		if(result.next()) {
			bool = true;
		}
    	return bool;
	}

	@Override
	public boolean isExist(Employee employee) throws SQLException {
		boolean bool = false;
		String adminData = "SELECT * FROM employee WHERE employee_id = ? AND password = ?";
		connection = Database.getConnection();
		PreparedStatement prepare = connection.prepareStatement(adminData);
		prepare.setString(1, employee.getEmployeeId());
		ResultSet result = prepare.executeQuery();
		if(result.next()) {
			bool = true;
		}
    	return bool;
	}

	@Override
	public int count() throws SQLException {
		int result = -1;
		String sql = "SELECT COUNT(id) FROM employee";
    	connection = Database.getConnection();
    	statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if(rs.next()) {
			result = rs.getInt("COUNT(id)");
		}
		return result;
	}

}
