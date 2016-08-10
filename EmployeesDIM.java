


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import classicmodels.Employees;

@Entity
@Table(name="employees_dim" , schema = "`DWH`")
public class EmployeesDIM  implements Serializable{

	private static final long serialVersionUID = -3994732245081805668L;
	
	private int employeeNumber;
	private String lastName;
	private String firstName;
	
	public EmployeesDIM(){

	}
	
	public EmployeesDIM(Employees employee){
		this.employeeNumber = employee.getEmployeeNumber();
		this.lastName=employee.getLastName();
		this.firstName=employee.getFirstName();
	}
	
	@Id
	public int getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
