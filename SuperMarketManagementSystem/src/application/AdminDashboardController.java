package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import dao.EmployeeDAO;
import dao.EmployeeDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import dao.CustomerReceiptDAO;
import dao.CustomerReceiptDAOImpl;

public class AdminDashboardController implements Initializable {
	@FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brandName;

    @FXML
    private Button addProducts_btn;

    @FXML
    private Button addProducts_clearBtn;

    @FXML
    private TableColumn<Product, String> addProducts_col_brandName;
    
    @FXML
    private TableColumn<Product, String> addProducts_col_price;

    @FXML
    private TableColumn<Product, String> addProducts_col_productID;

    @FXML
    private TableColumn<Product, String> addProducts_col_productName;

    @FXML
    private TableColumn<Product, String> addProducts_col_status;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productID;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private TextField addProducts_search;

    @FXML
    private ComboBox<?> addProducts_status;

    @FXML
    private TableView<Product> addProducts_tableView;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private Button close;

    @FXML
    private Label dashboard_activeEmployees;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_IncomeToday;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Button employees_btn;

    @FXML
    private Button employees_clearBtn;

    @FXML
    private TableColumn<Employee, String> employees_col_date;

    @FXML
    private TableColumn<Employee, String> employees_col_employeeID;

    @FXML
    private TableColumn<Employee, String> employees_col_firstName;

    @FXML
    private TableColumn<Employee, String> employees_col_gender;

    @FXML
    private TableColumn<Employee, String> employees_col_lastName;

    @FXML
    private TableColumn<Employee, String> employees_col_password;

    @FXML
    private Button employees_deleteBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private TextField employees_firstName;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TextField employees_password;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private TableView<Employee> employees_tableView;

    @FXML
    private Button employees_updateBtn;

    @FXML
    private Button logout;
    
    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minize;

    @FXML
    private Label username;
    
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    
    private ProductDAO productDAO = new ProductDAOImpl();
    
    private CustomerReceiptDAO customerReceiptDAO = new CustomerReceiptDAOImpl(); 
    
    private double x;
    private double y;
    
    
    public void dashboardDisplayActiveEmployees() {
    	int count = -1;
    	try {
    		count = employeeDAO.count();
    		dashboard_activeEmployees.setText(String.valueOf(count));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
   //2 
    public void dashboardDisplayIncomeToday() {
    	double sumT = 0;
    	try {
    		sumT = customerReceiptDAO.incomeToday();
    		dashboard_IncomeToday.setText("$"+String.valueOf(sumT));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void dashboardTotalIncome() {
    	double sumTI = 0;
    	try {
    		sumTI = customerReceiptDAO.totalIncome();
    		dashboard_totalIncome.setText("$"+String.valueOf(sumTI));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void dashboardChart() {
    	dashboard_chart.getData().clear();
    	try {
    		XYChart.Series chart = new XYChart.Series();
    		Map<String,Integer> resultList = customerReceiptDAO.getData();
    		for(Map.Entry<String,Integer> entry : resultList.entrySet()){
    			chart.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
    		}
    		dashboard_chart.getData().add(chart);
    	}catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void addProductsAdd() {
    	try {
    		Alert alert;
    		//CHECK IF THE FIELDS ARE EMPTY
    		if(addProducts_productID.getText().isEmpty()
    				|| addProducts_brandName.getText().isEmpty()
    				|| addProducts_productName.getText().isEmpty()
    				|| addProducts_status.getSelectionModel().getSelectedItem() == null
    				|| addProducts_price.getText().isEmpty()){
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all blank fields");
				alert.showAndWait();
			}else {
				Product product = new Product(addProducts_productID.getText()
		    			,addProducts_brandName.getText()
		    			,addProducts_productName.getText()
		    			,(String)addProducts_status.getSelectionModel().getSelectedItem()
		    			,Double.valueOf(addProducts_price.getText()));
				boolean bool = productDAO.isExist(product);
				//IF THE product_id is same on the data exist, then we will block that
				if(bool) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Product ID: " + addProducts_productID.getText() + " was already exist!");
					alert.showAndWait();
				}else {
					productDAO.insert(product);
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successful Added!");
					alert.showAndWait();
					//TO UPDATE THE TABLEVIEW ONCE DATA WAS INSERTED
					addProductsShowData();
					//TO CLEAR THE FIELDS ONCE THE DATA WAS SUCCESSFULLY INSERTED
					addProductsClear();
				}
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void addProductsUpdate() {
    	Product product = null;
    	try {
    		Alert alert;
    		//CHECK IF THE FIELDS ARE EMPTY
    		if(addProducts_productID.getText().isEmpty()
    				|| addProducts_brandName.getText().isEmpty()
    				|| addProducts_productName.getText().isEmpty()
    				|| addProducts_status.getSelectionModel().getSelectedItem() == null
    				|| addProducts_price.getText().isEmpty()){
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Message");
				alert.setHeaderText(null);
				alert.setContentText("Please fill all blank fields");
				alert.showAndWait();
			}else {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Message");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to UPDATE Product ID: " + addProducts_productID.getText() + "?");
				Optional<ButtonType> option = alert.showAndWait();
				//IF YOU CLICK OR
				if(option.get().equals(ButtonType.OK)) {
					product = new Product(addProducts_productID.getText()
			    			,addProducts_brandName.getText()
			    			,addProducts_productName.getText()
			    			,(String)addProducts_status.getSelectionModel().getSelectedItem()
			    			,Double.valueOf(addProducts_price.getText()));
					productDAO.update(product);
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Updated!");
					alert.showAndWait();
					//TO UPDATE THE TABLEVIEW ONCE DATA WAS INSERTED
					addProductsShowData();
					//TO CLEAR THE FIELDS ONCE THE DATA WAS SUCCESSFULLY INSERTED
					addProductsClear();
				}
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void addProductsDelete() {
    	Product product = new Product(addProducts_productID.getText()
    			,addProducts_brandName.getText()
    			,addProducts_productName.getText()
    			,(String)addProducts_status.getSelectionModel().getSelectedItem()
    			,Double.valueOf(addProducts_price.getText()));
    	try {
    		int result = productDAO.delete(product);
			if(result == 1) {
				//TO UPDATE THE TABLEVIEW ONCE DATA WAS INSERTED
				addProductsShowData();
				//TO CLEAR THE FIELDS ONCE THE DATA WAS SUCCESSFULLY INSERTED
				addProductsClear();
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void addProductsClear() {
    	addProducts_productID.setText("");
    	addProducts_brandName.setText("");
    	addProducts_productName.setText("");
    	addProducts_status.getSelectionModel().clearSelection();
    	addProducts_price.setText("");
    }
    private String[] statusList = {"Available","Not Available"};
    public void addProductsStatusList() {
    	List<String> listS = new ArrayList<>();
    	for(String data : statusList) {
    		listS.add(data);
    	}
    	ObservableList statusData = FXCollections.observableArrayList(listS);
    	addProducts_status.setItems(statusData);
    }
    
    public void addProductsSearch() {
    	FilteredList<Product> filter = new FilteredList<>(addProductsList, e->true);
    		addProducts_search.textProperty().addListener((Observable, oldValue, newValue) ->{
    		filter.setPredicate(predicateProductData ->{
    			if(newValue == null || newValue.isEmpty()) {
    				return true;
    			}
    			String searchKey = newValue.toLowerCase();
    			if(predicateProductData.getProductId().toLowerCase().contains(searchKey)) {
    				return true;
    			}else if(predicateProductData.getBrand().toLowerCase().contains(searchKey)) {
    				return true;
    			}else if(predicateProductData.getStatus().toLowerCase().contains(searchKey)) {
    				return true;
    			}else if(predicateProductData.getProductName().toLowerCase().contains(searchKey)) {
    				return true;
    			}else if(predicateProductData.getPrice().toString().contains(searchKey)) {
    				return true;
    			}return false;
    		});
    	});
    	SortedList<Product> sortList = new SortedList<>(filter);
    	sortList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
    	addProducts_tableView.setItems(sortList);
    }
    
    public ObservableList<Product> addProductsListData(){
    	ObservableList<Product> productsList = FXCollections.observableArrayList();
    	try {
    		List<Product> products = new ArrayList<Product>();
    		products = productDAO.getAll();
    		for(Product product : products) {
    			productsList.add(product);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return productsList;
    }
    
    private ObservableList<Product> addProductsList;
    public void addProductsShowData() {
    	addProductsList = addProductsListData();
    	addProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	addProducts_col_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
    	addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
    	addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    	addProducts_tableView.setItems(addProductsList);
    }
    
    public void addProductsSelect() {
    	Product prod = addProducts_tableView.getSelectionModel().getSelectedItem();
    	int num = addProducts_tableView.getSelectionModel().getSelectedIndex();
    	if((num - 1) < -1) {
    		return;
    	}
    	addProducts_productID.setText(prod.getProductId());
    	addProducts_brandName.setText(prod.getBrand());
    	addProducts_productName.setText(prod.getProductName());
    	addProducts_price.setText(String.valueOf(prod.getPrice()));
    }
    
    public void employeesSave() {
		Alert alert;
		//CHECK IF THE FIELDS ARE EMPTY
		if(employees_employeeID.getText().isEmpty()
				|| employees_password.getText().isEmpty()
				|| employees_firstName.getText().isEmpty()
				|| employees_lastName.getText().isEmpty()
				|| employees_gender.getSelectionModel().getSelectedItem() == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();
		}else {
			try {
				Date date = new Date();
				Employee employee = new Employee(employees_employeeID.getText()
		    			,employees_password.getText()
		    			,employees_firstName.getText()
		    			,employees_lastName.getText()
		    			,(String)employees_gender.getSelectionModel().getSelectedItem()
		    			,date);
				boolean bool = employeeDAO.isExist(employee);
				if(bool) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Employee ID: " + employee.getEmployeeId() + " was already exist!");
					alert.showAndWait();
					//IF THE product_id is same on the data exist, then we will block that
				}else {
					employeeDAO.insert(employee);
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Message");
					alert.setHeaderText(null);
					alert.setContentText("Successful Added!");
					alert.showAndWait();
					//TO UPDATE THE TABLEVIEW ONCE DATA WAS INSERTED
					employeesShowListData();
					//TO CLEAR THE FIELDS
					employeesReset();
				}
			}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
    }
    
    private String[] genderList = {"Male", "Female", "Others"};
    
    public void employeesGender() {
    	List<String> genderL = new ArrayList<>();
    	for(String data : genderList) {
    		genderL.add(data);
    	}
    	ObservableList listG = FXCollections.observableArrayList(genderL);
    	employees_gender.setItems(listG);
    }
    
    public void employeesUpdate() {
    	Date date = new Date();
    	Employee employee = new Employee(employees_employeeID.getText()
    			,employees_password.getText()
    			,employees_firstName.getText()
    			,employees_lastName.getText()
    			,(String)employees_gender.getSelectionModel().getSelectedItem()
    			,date);
		try {
			int result = employeeDAO.update(employee);
			if(result == 1) {
				employeesShowListData();
				employeesReset();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    public void employeesDelete() {
    	Date date = new Date();
    	Employee employee = new Employee(employees_employeeID.getText()
    			,employees_password.getText()
    			,employees_firstName.getText()
    			,employees_lastName.getText()
    			,(String)employees_gender.getSelectionModel().getSelectedItem()
    			,date);
    	try {
    		int result = employeeDAO.delete(employee);
				if(result == 1) {
					//TO UPDATE THE TABLEVIEW ONCE DATA WAS INSERTED
					employeesShowListData();
					//TO CLEAR THE FIELDS ONCE THE DATA WAS SUCCESSFULLY INSERTED
					employeesReset();
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void employeesReset() {
    	employees_employeeID.setText("");
    	employees_password.setText("");
    	employees_firstName.setText("");
    	employees_lastName.setText("");
    	employees_gender.getSelectionModel().clearSelection();
    }
    
    public ObservableList<Employee> employeesListData(){
    	ObservableList<Employee> emData = FXCollections.observableArrayList();
    	try {
    		List<Employee> employees = employeeDAO.getAll();
    		for(Employee employee : employees) {
    			emData.add(employee);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return emData;
    }
    
    private ObservableList<Employee> employeesList;
    public void employeesShowListData() {
    	employeesList = employeesListData();
    	employees_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
    	employees_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
    	employees_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    	employees_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    	employees_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    	employees_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	employees_tableView.setItems(employeesList);
    }
    
    public void employeesSelect() {
    	Employee employeeD = employees_tableView.getSelectionModel().getSelectedItem();
    	int num = employees_tableView.getSelectionModel().getSelectedIndex();
    	if((num - 1) < -1) {
    		return;
    	}
    	employees_employeeID.setText(employeeD.getEmployeeId());
    	employees_password.setText(employeeD.getPassword());
    	employees_firstName.setText(employeeD.getFirstName());
    	employees_lastName.setText(employeeD.getLastName());
    }
    
    public void logout() {
    	try {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Are you sure you want to logout?");
    		Optional<ButtonType> option = alert.showAndWait();
    		if(option.get().equals(ButtonType.OK)) {
    			logout.getScene().getWindow().hide();
    			Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
    			Stage stage = new Stage();
    			Scene scene = new Scene(root);
    			root.setOnMousePressed((MouseEvent event) ->{
    				x = event.getSceneX();
    				y = event.getSceneY();
    			});
    			
    			root.setOnMouseDragged((MouseEvent event) ->{
    				stage.setX(event.getScreenX() - x);
    				stage.setY(event.getScreenY() - y);
    				
    				stage.setOpacity(.8);
    			});
    			
    			root.setOnMouseReleased((MouseEvent event) ->{
    				stage.setOpacity(1);
    			});
    			
    			stage.initStyle(StageStyle.TRANSPARENT);
    			
    			stage.setScene(scene);
    			stage.show();
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void displayUsername() {
    	username.setText(getData.username);
    }
    
    public void defaultNavBtn() {
    	dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
    }
    
    public void switchForm(ActionEvent event) {
    	if(event.getSource()==dashboard_btn) {
    		dashboard_form.setVisible(true);
    		addProducts_form.setVisible(false);
    		employees_form.setVisible(false);
    		dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
    		addProducts_btn.setStyle("-fx-background-color: transparent");
			employees_btn.setStyle("-fx-background-color: transparent");
			dashboardDisplayActiveEmployees();
			dashboardDisplayIncomeToday();
			dashboardTotalIncome();
			dashboardChart();
    	}else if(event.getSource()==addProducts_btn) {
    		dashboard_form.setVisible(false);
    		addProducts_form.setVisible(true);
    		employees_form.setVisible(false);
    		addProducts_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
    		dashboard_btn.setStyle("-fx-background-color: transparent");
			employees_btn.setStyle("-fx-background-color: transparent");
			addProductsShowData();
			addProductsStatusList();
			addProductsSearch();
    	}else if(event.getSource()==employees_btn) {
    		dashboard_form.setVisible(false);
    		addProducts_form.setVisible(false);
    		employees_form.setVisible(true);
    		employees_btn.setStyle("-fx-background-color: linear-gradient(to top right, #896b34, #b8a536)");
    		addProducts_btn.setStyle("-fx-background-color: transparent");
    		dashboard_btn.setStyle("-fx-background-color: transparent");
    		employeesShowListData();
    	}
    }
    
    public void close() {
    	System.exit(0);
    }
    
    public void minimize() {
    	Stage stage = (Stage)main_form.getScene().getWindow();
    	stage.setIconified(true);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		displayUsername();
		defaultNavBtn();
		dashboardDisplayActiveEmployees();
		dashboardDisplayIncomeToday();
		dashboardTotalIncome();
		dashboardChart();
		addProductsShowData();
		addProductsStatusList();
		addProductsSearch();
		employeesShowListData();
		employeesGender();
	}

}
