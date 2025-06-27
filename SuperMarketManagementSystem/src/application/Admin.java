package application;

public class Admin {
	private int id;
	private String username;
	private String password;
	public Admin(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	public int getId(int id) {
		return id;
	}
	public void setId() {
		this .id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername() {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void set() {
		this.password = password;
	}
	public String toString() {
		return "Admin[id ="+id+", username="+username+", password="+password+"]";
	}
}
