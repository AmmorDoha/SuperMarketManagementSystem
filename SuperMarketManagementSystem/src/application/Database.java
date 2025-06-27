package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/market", "mohamed", "motdepasse");
			return connect;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void closePreparedStatement(PreparedStatement ps) throws SQLException {
		ps.close();
	}
	public static void closeConnection(Connection con) throws SQLException {
		con.close();
	}
}
