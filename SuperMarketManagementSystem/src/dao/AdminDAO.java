package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Admin;

public interface AdminDAO extends DAO<Admin> {
	boolean authenticate(Admin admin) throws SQLException;
}
