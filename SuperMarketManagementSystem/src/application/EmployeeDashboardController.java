package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import dao.CustomerReceiptDAO;
import dao.CustomerReceiptDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;

public class EmployeeDashboardController implements Initializable{
	@FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_brand;

    @FXML
    private TableColumn<Customer, String> purchase_col_brand;

    @FXML
    private TableColumn<Customer, String> purchase_col_price;

    @FXML
    private TableColumn<Customer, String> purchase_col_productName;

    @FXML
    private TableColumn<Customer, String> purchase_col_quantity;

    @FXML
    private Label purchase_employeeId;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private ComboBox<String> purchase_productName;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Button purchase_receiptBtn;

    @FXML
    private TableView<Customer> purchase_tableView;

    @FXML
    private Label purchase_total;
    
    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private CustomerReceiptDAO customerReceiptDAO = new CustomerReceiptDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    
    public void purchaseAdd() {
    	purchaseCustomerId();
    	purchaseSpinnerValue();
    	purchaseGetPrice();
		Alert alert;
		Date date = new Date();
		//CHECK IF THE FIELDS ARE EMPTY
		if(purchase_brand.getText().isEmpty()
				|| purchase_productName.getSelectionModel().getSelectedItem() == null
				|| qty == 0){
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please choose product/s first");
			alert.showAndWait();
		}else {
			double totalPrice = (qty * price);
			Customer customer = new Customer(customerId
					,purchase_brand.getText()
					,(String)purchase_productName.getSelectionModel().getSelectedItem()
					,qty
					,totalPrice
					,date);
			try {
				customerDAO.insert(customer);
				//TO UPDATE THE TABLEVIEW ONCE DATA WAS INSERTED
				purchaseShowListData();
				// DISPLAY SUM
				purchaseDisplayTotal();
			}catch(Exception e) {
	    		e.printStackTrace();
	    	}
		}
    }
    
    public void purchaseReset() {
    	Customer customer = new Customer(customerId
    			,null
    			,null
    			,null
    			,null
    			,null);
    	Alert alert;
    	if(purchase_tableView.getItems().getFirst() == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please choose the product first");
			alert.showAndWait();
		}else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to reset? The Product List including to reset");
			Optional<ButtonType> option = alert.showAndWait();
			if(option.get().equals(ButtonType.OK)) {
			try {
				customerDAO.delete(customer);
				purchase_brand.setText("");
	    		purchase_productName.getSelectionModel().clearSelection();
	    		purchaseSpinner();
	    		purchase_total.setText("$0.0");
	    		purchaseShowListData();
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}	
			}else return;
		}
    }
    
    public void purchasePay() {
    	purchaseCustomerId();
    	purchaseDisplayTotal();
    	Date date = new Date();
    	CustomerReceipt customerReceipt = new CustomerReceipt(customerId
    			,totalP
    			,date);
		Alert alert;
		if(purchase_tableView.getItems().getFirst() == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please choose the product first");
			alert.showAndWait();
		}else {
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Message");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure?");
			Optional<ButtonType> option = alert.showAndWait();
			if(option.get().equals(ButtonType.OK)) {
				try {
					customerReceiptDAO.insert(customerReceipt);
				}catch(Exception e) {
					e.printStackTrace();
				}
        		alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Information Message");
    			alert.setHeaderText(null);
    			alert.setContentText("Successful!");
    			alert.showAndWait();
			}else return;
		}
    }
    
    public void purchaseReceipt() {
    	purchaseDisplayTotal();
    	purchaseCustomerId();
    	HashMap hash = new HashMap();
    	hash.put("marketP", customerId);
    	try {
    		Alert alert;
    		if(totalP == 0) {
    			alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error Message");
    			alert.setHeaderText(null);
    			alert.setContentText("Invalid!");
    			alert.showAndWait();
    		}else {
    			JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\PC\\eclipse-workspace\\SuperMarketManagementSystem\\src\\report.jrxml");
	    	    JasperReport jReport = JasperCompileManager.compileReport(jDesign);
	    	    JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, Database.getConnection());
	    	    JasperViewer.viewReport(jPrint, false);
    		}
    	}catch(Exception e) {e.printStackTrace();}   	
    }
    
    private double totalP = 0;
    
    public void purchaseDisplayTotal() {
    	purchaseCustomerId();
    	Customer customer = new Customer(customerId
    			,null
    			,null
    			,null
    			,null
    			,null);
    	try {
    			totalP = customerDAO.getTotal(customer);
    		}catch(Exception e) {
    		e.printStackTrace();
    	}
		purchase_total.setText("$"+String.valueOf(totalP));
    }
    
    private double price = 0;
    
    private double totalPrice = 0;
    
    public void purchaseGetPrice() {
    	Product product = new Product(null
    			,null
    			,purchase_productName.getSelectionModel().getSelectedItem()
    			,null
    			,null);
    	try {
    			price = productDAO.getPrice(product);
    		}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void purchaseSearchBrand() {
    	Product product = new Product(null
    			,purchase_brand.getText()
    			,null
    			,"Available"
    			,null);
    	List<String> brandNames = new ArrayList<String>();
    	try {
    		brandNames = productDAO.getBrandName(product);
		}catch(Exception e) {
			e.printStackTrace();
		}
		ObservableList listProduct = FXCollections.observableArrayList();
		brandNames.forEach(brandName -> listProduct.add(brandName));
		purchase_productName.setItems(listProduct);
    }
    
    private SpinnerValueFactory spinner;
    
    public void purchaseSpinner() {
    	spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,0);
    	purchase_quantity.setValueFactory(spinner);
    }
    
    private int qty;
    
    public void purchaseSpinnerValue() {
    	qty = purchase_quantity.getValue();
    	//System.out.println(qty);
    }
    
    public ObservableList<Customer> purchaseListData(){
    	purchaseCustomerId();
    	ObservableList<Customer> customerList = FXCollections.observableArrayList();
    	Customer customer = null;
    	try {
    		customer = customerDAO.getCustomer(customerId);
		}catch(Exception e) {
			e.printStackTrace();
		}
    	customerList.add(customer);
    	return customerList;
    }
    
    private ObservableList<Customer> purchaseList;
    public void purchaseShowListData() {
    	purchaseList = purchaseListData();
    	purchase_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
    	purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
    	purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    	purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    	purchase_tableView.setItems(purchaseList);
    }
    
    private int customerId;
    public void purchaseCustomerId() {
    	try {
    		customerId = customerDAO.getCustomerId();
    		int customerId2 = customerReceiptDAO.getCustomerId();
    		if(customerId == 0) {
    			customerId+=1;
    		}else if(customerId2==customerId) {
    			customerId+=1;
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void displayEmployeeId() {
    	purchase_employeeId.setText(getData.employeeId);
    }
    
    private double x = 0;
    private double y = 0;
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
		displayEmployeeId();
		purchaseShowListData();
		purchaseSpinner();
		//purchaseSpinnerValue();
		purchaseDisplayTotal();
	}
}
