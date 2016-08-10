


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import classicmodels.Customers;

@Entity
@Table(name="customer_dim" , schema = "`DWH`")
public class CustomersDIM  implements Serializable{
	
	private static final long serialVersionUID = -3994732245081805668L;
	
	private int customerNumber;
	private String customerName;
	private String phone;

	public CustomersDIM(){
	}
	
	public CustomersDIM(Customers customer){
		this.customerNumber=customer.getCustomerNumber();
		this.customerName=customer.getCustomerName();
		this.phone=customer.getPhone();
	}

	@Id
	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
