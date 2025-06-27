package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import application.Admin;
import application.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminDAOImpl implements AdminDAO{

	@Override
	public Admin get(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Admin> getAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(Admin t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Admin t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Admin t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Admin t) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean authenticate(Admin admin) throws SQLException {
		boolean bool = false;
		String adminData = "SELECT * FROM admin WHERE username = ? and password = ?";
		Connection connection = Database.getConnection();
		PreparedStatement prepare = connection.prepareStatement(adminData);
		prepare.setString(1, admin.getUsername());
		prepare.setString(2, admin.getPassword());
		ResultSet result = prepare.executeQuery();
		if(result.next()) {
			bool = true;
		}
    	return bool;
}
}