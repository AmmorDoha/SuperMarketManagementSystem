package application;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;

public class SampleController {
	@FXML
    private AnchorPane admin_form;

    @FXML
    private Hyperlink admin_hyperLink;

    @FXML
    private Button admin_loginBtn;

    @FXML
    private PasswordField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private AnchorPane employee_form;

    @FXML
    private Hyperlink employee_hyperLink;
    
    private double x = 0;
	private double y = 0;
    
    public void employeeLogin() {
    	EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    	Employee employee = new Employee (employee_id.getText(),employee_password.getText(),null,null,null,null);
    	
    		Alert alert;
    		//CHECK IF THE TEXTFIELDS ARE EMPTY O
    		if(employee.getEmployeeId().isEmpty()
    				|| employee.getPassword().isEmpty()) {
    			alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message");
    			alert.setHeaderText(null);
    			alert.setContentText("Please fill all blank fields");
    			alert.showAndWait();
    		}else {
    			try {
	    			boolean result = employeeDAO.authenticate(employee);
	    			if(result) {
	    				getData.employeeId = employee_id.getText();
	    				alert = new Alert(AlertType.INFORMATION);
	    				alert.setTitle("Information Message");
	    				alert.setHeaderText(null);
	    				alert.setContentText("Successfully Login!");
	    				alert.showAndWait();
	    				
	    				employee_loginBtn.getScene().getWindow().hide();
	    				Parent root = FXMLLoader.load(getClass().getResource("EmployeeDashBoard.fxml"));
	    				Stage stage = new Stage();
	    				Scene scene = new Scene(root);
	    				root.setOnMousePressed((MouseEvent event)->{
	    					x = event.getSceneX();
	    					y = event.getSceneY();
	    				});
	    				root.setOnMouseDragged((MouseEvent event)->{
	    					stage.setX(event.getScreenX() - x);
	    					stage.setY(event.getScreenY() - y);
	    				});
	    				stage.initStyle(StageStyle.TRANSPARENT);
	    				stage.setScene(scene);
	    				stage.show();
	    			}else {
	    				alert = new Alert(AlertType.ERROR);
	        			alert.setTitle("Error Message");
	        			alert.setHeaderText(null);
	        			alert.setContentText("Wrong Username/Password");
	        			alert.showAndWait();
	    			}
	    		}
    			catch(Exception e) {
    				e.printStackTrace();
    			}
    		}
    }
    
    public void adminLogin() {
    	AdminDAO adminDAO = new AdminDAOImpl();
    	Admin admin = new Admin(0, admin_username.getText(), admin_password.getText());
		Alert alert;
    	//CHECK IF THE TEXTFIELDS ARE EMPTY O
		if(admin.getUsername().isEmpty()
				|| admin.getPassword().isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();
		}else {
			try{
				boolean result = adminDAO.authenticate(admin);
				if(result) {
					getData.username = admin_username.getText();
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Login!");
					alert.showAndWait();
					admin_loginBtn.getScene().getWindow().hide();
					Parent root = FXMLLoader.load(getClass().getResource("AdminDashBoard.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					root.setOnMousePressed((MouseEvent event)->{
						x = event.getSceneX();
						y = event.getSceneY();
					});
					root.setOnMouseDragged((MouseEvent event)->{
						stage.setX(event.getScreenX() - x);
						stage.setY(event.getScreenY() - y);
					});
					stage.initStyle(StageStyle.TRANSPARENT);
					stage.setScene(scene);
					stage.show();
				}else {
					alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("Error Message");
	    			alert.setHeaderText(null);
	    			alert.setContentText("Wrong Username/Password");
	    			alert.showAndWait();
				}
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
    }

    @FXML
    private TextField employee_id;

    @FXML
    private Button employee_loginBtn;

    @FXML
    private PasswordField employee_password;
    
    public void swichForm(ActionEvent event) {
    	if(event.getSource() == admin_hyperLink) {
    		admin_form.setVisible(false);;
    		employee_form.setVisible(true);
    	}else if(event.getSource() == employee_hyperLink) {
    		admin_form.setVisible(true);;
    		employee_form.setVisible(false);
    	}
    }
    
    public void close() {
    	System.exit(0);
    }
}
