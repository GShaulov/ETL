


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import classicmodels.Offices;

@Entity
@Table(name="offices_dim" , schema = "`dwh`")
public class OfficesDIM  implements Serializable{
	
	private static final long serialVersionUID = -3994732245081805668L;
	
	private String officeCode;
	private String city;
	private String country;
	
	public OfficesDIM(){
	}
	
	public OfficesDIM(Offices office){
		this.officeCode = office.getOfficeCode();
		this.city=office.getCity();
		this.country=office.getCountry();
	}

	@Id
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
