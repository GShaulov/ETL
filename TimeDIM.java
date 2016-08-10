


import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="time_dim" , schema = "`DWH`")
public class TimeDIM implements Serializable {

	@Override
	public String toString() {
		return "TimeDIM [year=" + year + ", month=" + month + ", day=" + day + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3046604941235204622L;
	private long id;
	private Date orderDate;
	private int year;
	private int month;
	private int day;

	public TimeDIM() {

	}
	
	public TimeDIM(Date orderDate) {
		this.orderDate = orderDate;
	    LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    this.year = localDate.getYear();
	    this.month = localDate.getMonthValue();
	    this.day = localDate.getDayOfMonth();

	}
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}


}
