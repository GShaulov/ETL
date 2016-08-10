


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import classicmodels.Products;

@Entity
@Table(name="products_dim" , schema = "`DWH`")
public class ProductsDIM  implements Serializable{
	
	private static final long serialVersionUID = -3994732245081805668L;
	
	private String productCode;
	private String productName;
	private double buyPrice;
	
	public ProductsDIM(){
	}
	
	public ProductsDIM(Products products){
		this.productCode=products.getProductCode();
		this.productName=products.getProductName();
		this.buyPrice=products.getBuyPrice();
	}
	
	@Id
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

}
