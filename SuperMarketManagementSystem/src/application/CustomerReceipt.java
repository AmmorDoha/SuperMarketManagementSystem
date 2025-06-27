package application;

import java.util.Date;

public class CustomerReceipt {
	private int customerId;
	private Double total;
	private Date date;
	public CustomerReceipt(int customerId, Double total, Date date) {
		this.customerId = customerId;
		this.total = total;
		this.date = date;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "CustomerReceipt [customerId=" + customerId + ", total=" + total + "]";
	}
	
}
