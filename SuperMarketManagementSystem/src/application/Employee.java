package application;

import java.util.Date;

public class Employee {
	private String employeeId;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private Date date;
	public Employee(String employeeId, String password, String firstName, String lastName, String gender, Date date) {
		this.employeeId = employeeId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.date = date;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getGender() {
		return gender;
	}
	public Date getDate() {
		return date;
	}
}
