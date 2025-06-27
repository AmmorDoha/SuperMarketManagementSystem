package application;

public class Product {
	private String productId;
	private String brand;
	private String productName;
	private String status;
	private Double price;
	public Product(String productId, String brand, String productName, String status, Double price) {
		this.productId = productId;
		this.brand = brand;
		this.productName = productName;
		this.status = status;
		this.price = price;
	}
	public String getProductId() {
		return productId;
	}
	public String getBrand() {
		return brand;
	}
	public String getProductName() {
		return productName;
	}
	public String getStatus() {
		return status;
	}
	public Double getPrice() {
		return price;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
