


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import classicmodels.OrderDetails;

@Entity
@Table(name="orders_fact" , schema = "`DWH`")
public class OrdersFACT implements Serializable{
	private static final long serialVersionUID = 4102499081849065088L;
		private long id;
		private int orderNumber;
		private TimeDIM orderDate;
		private CustomersDIM customer;
		private EmployeesDIM salesRepEmployeeNumber;
		private OfficesDIM officeCode;
		private ProductsDIM productCode;
		private int quantityOrdered;
		private double priceEach;
		private double amount;

		public OrdersFACT() {

		}		

		public OrdersFACT(OrderDetails orderDetails) {
			this.quantityOrdered = orderDetails.getQuantityOrdered();
			this.priceEach = orderDetails.getPriceEach();
			this.amount = (quantityOrdered*priceEach);
			this.orderNumber = orderDetails.getOrderLineNumber();
			this.orderDate = new TimeDIM(orderDetails.getOrderNumber().getOrderDate());
			this.customer = new CustomersDIM(orderDetails.getOrderNumber().getCustomerNumber());
			this.salesRepEmployeeNumber = new EmployeesDIM(orderDetails.getOrderNumber().getCustomerNumber().getSalesRepEmployeeNumber());
			this.officeCode = new OfficesDIM(orderDetails.getOrderNumber().getCustomerNumber().getSalesRepEmployeeNumber().getOfficeCode());
			this.productCode = new ProductsDIM(orderDetails.getProductCode());
		}
	
		public OrdersFACT(OrdersFACT ordersFACT) {
			this.id = ordersFACT.getId();
			this.orderNumber = ordersFACT.getOrderNumber();
			this.orderDate = ordersFACT.getOrderDate();
			this.customer = ordersFACT.getCustomer();
			this.salesRepEmployeeNumber = ordersFACT.getSalesRepEmployeeNumber();
			this.officeCode = ordersFACT.getOfficeCode();
			this.productCode = ordersFACT.getProductCode();
			this.quantityOrdered = ordersFACT.getQuantityOrdered();
			this.priceEach = ordersFACT.getPriceEach();
			this.amount = ordersFACT.getAmount();
		}
	
		@GeneratedValue(strategy=GenerationType.IDENTITY)	
		@Id
		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public int getOrderNumber() {
			return orderNumber;
		}

		public void setOrderNumber(int orderNumber) {
			this.orderNumber = orderNumber;
		}

		@ManyToOne(cascade = CascadeType.MERGE)
		public TimeDIM getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(TimeDIM orderDate) {
			this.orderDate = orderDate;
		}

		@ManyToOne(cascade = CascadeType.MERGE)
		@JoinColumn (name = "customerNumber")
		public CustomersDIM getCustomer() {
			return customer;
		}

		public void setCustomer(CustomersDIM customer) {
			this.customer = customer;
		}
		
		@ManyToOne(cascade = CascadeType.MERGE)
		@JoinColumn (name = "salesRepEmployeeNumber", referencedColumnName = "employeeNumber")
		public EmployeesDIM getSalesRepEmployeeNumber() {
			return salesRepEmployeeNumber;
		}
		
		public void setSalesRepEmployeeNumber(EmployeesDIM salesRepEmployeeNumber) {
			this.salesRepEmployeeNumber = salesRepEmployeeNumber;
		}

		@ManyToOne(cascade = CascadeType.MERGE)
		@JoinColumn(name = "officeCode", referencedColumnName = "officeCode")
		public OfficesDIM getOfficeCode() {
			return officeCode;
		}

		public void setOfficeCode(OfficesDIM officeCode) {
			this.officeCode = officeCode;
		}

		@ManyToOne(cascade = CascadeType.MERGE)
		@JoinColumn (name = "productCode", referencedColumnName = "productCode")
		public ProductsDIM getProductCode() {
			return productCode;
		}

		public void setProductCode(ProductsDIM productCode) {
			this.productCode = productCode;
		}

		@Column(name="quantityOrdered")
		public int getQuantityOrdered() {
			return quantityOrdered;
		}

		public void setQuantityOrdered(int quantityOrdered) {
			this.quantityOrdered = quantityOrdered;
		}

		@Column(name="priceEach")
		public double getPriceEach() {
			return priceEach;
		}

		public void setPriceEach(double priceEach) {
			this.priceEach = priceEach;
		}
		
		@Column(name="amount")
		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}
		
}

