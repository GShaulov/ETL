

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import classicmodels.OrderDetails;

public class Main {

	private int records;
	
	public static void main(String[] args){
		Main main = new Main();
		main.createDWH();
	}
	
	public void createDWH()	{
		truncateDB();
		createData();
	}
	
	public static void truncateDB(){
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      Class.forName("com.mysql.jdbc.Driver");
	      System.out.println("Connecting to server...");
	      conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "1609");
	      System.out.println("Deleting DWH database...");
	      stmt = conn.createStatement();
	      String sql = "DROP DATABASE DWH";
	      stmt.executeUpdate(sql);
	   }
	   catch(SQLException se){System.out.println("Database DWH doesn't exist!"); }
	   catch(Exception e){}
	   try{
	      System.out.println("Creating DWH database...");
	      stmt = conn.createStatement();
	      String sql = "CREATE DATABASE DWH";
	      stmt.executeUpdate(sql);
	   }
	   catch(SQLException se){System.out.println("Database DWH doesn't created!"); }
	   catch(Exception e){}
	   finally{
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }
	      catch(SQLException se2){}
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){}
	   }
	}
	
	private void createData(){
		boolean bCommit = false;
		EntityManagerFactory emfOP = Persistence.createEntityManagerFactory("classicmodels");
		EntityManager emOP = emfOP.createEntityManager();
		EntityManagerFactory emfDWH = Persistence.createEntityManagerFactory("dwh");
		EntityManager emDWH = emfDWH.createEntityManager();
		EntityTransaction tx = null;
		
		///////////////////////////////////////////////////////////////////////////////////1
		tx = emDWH.getTransaction();
		tx.begin();
		try {
			@SuppressWarnings("unchecked")
			List<OrderDetails> ordersList = emOP.createQuery("from OrderDetails").getResultList();
			for(OrderDetails item:ordersList){		
				OrdersFACT ordersFACT = new OrdersFACT(item);
				emDWH.merge(ordersFACT);
				++records;
			}
			bCommit = true;
			System.out.println("Total records after moving data from classicmodels database: "+records);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();			
		}
		if (bCommit) 
			tx.commit();
		
		///////////////////////////////////////////////////////////////////////////////////2
		tx = null;
		tx = emDWH.getTransaction();
		tx.begin();
		try {
			for(int i=1;i<8; i++){
			@SuppressWarnings("unchecked")
			List<OrdersFACT> ordersList = emDWH.createQuery("from OrdersFACT where officeCode = "+i+"").getResultList();
			double amount=0;
			OrdersFACT ordersFACT = new OrdersFACT();
			for(OrdersFACT item:ordersList){
				if(amount==0.0)
					ordersFACT.setOfficeCode(item.getOfficeCode());
				amount+=item.getAmount();
			}
			ordersFACT.setAmount(amount);
			emDWH.merge(ordersFACT);
			amount=0;
			++records;
			}
			bCommit = true;
			System.out.println("Total records after sum amount grouped by office: "+records);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();			
		}
		if (bCommit) 
			tx.commit();
		
		///////////////////////////////////////////////////////////////////////////////////3
		tx = null;
		tx = emDWH.getTransaction();
		tx.begin();
		try {
			@SuppressWarnings("unchecked")
			List<EmployeesDIM> employeeList = emDWH.createQuery("from EmployeesDIM").getResultList();
			for(EmployeesDIM employee:employeeList){
				
				@SuppressWarnings("unchecked")
				List<OrdersFACT> ordersList = emDWH.createQuery("from OrdersFACT where salesRepEmployeeNumber = "+employee.getEmployeeNumber()+"").getResultList();
				double amount=0;
				OrdersFACT ordersFACT = new OrdersFACT();
				for(OrdersFACT item:ordersList){
					if(amount==0.0)
						ordersFACT.setSalesRepEmployeeNumber(item.getSalesRepEmployeeNumber());
					amount+=item.getAmount();
				}
				ordersFACT.setAmount(amount);
				emDWH.merge(ordersFACT);
				amount=0;
				++records;
			}
			bCommit = true;
			System.out.println("Total records after sum amount grouped by employee: "+records);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();			
		}
		if (bCommit) 
			tx.commit();

		///////////////////////////////////////////////////////////////////////////////////4
		tx = null;
		tx = emDWH.getTransaction();
		tx.begin();
		try {
			@SuppressWarnings("unchecked")
			List<CustomersDIM> customerList = emDWH.createQuery("from CustomersDIM").getResultList();
			for(CustomersDIM customer:customerList){
				@SuppressWarnings("unchecked")
				List<OrdersFACT> ordersList = emDWH.createQuery("from OrdersFACT where customerNumber = "+customer.getCustomerNumber()+"").getResultList();
				double amount=0;
				OrdersFACT ordersFACT = new OrdersFACT();
				for(OrdersFACT item:ordersList){
					if(amount==0.0)
						ordersFACT.setCustomer(item.getCustomer());
					amount+=item.getAmount();
				}
				ordersFACT.setAmount(amount);
				emDWH.merge(ordersFACT);
				amount=0;
				++records;
			}
			bCommit = true;
			System.out.println("Total records after sum amount grouped by customer: "+records);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();			
		}
		if (bCommit) 
			tx.commit();
		///////////////////////////////////////////////////////////////////////////////////5
		tx = null;
		tx = emDWH.getTransaction();
		tx.begin();
		try {
			@SuppressWarnings("unchecked")
			List<TimeDIM> yearList = emDWH.createQuery("from TimeDIM group by year").getResultList();
			for(TimeDIM year:yearList){
				@SuppressWarnings("unchecked")
				List<TimeDIM> monthList = emDWH.createQuery("from TimeDIM where year="+year.getYear()+" group by month").getResultList();
				double yearAmount=0;
				for(TimeDIM month:monthList){
					@SuppressWarnings("unchecked")
					List<TimeDIM> dayList = emDWH.createQuery("from TimeDIM where year="+year.getYear()+" and month="+month.getMonth()+" group by day").getResultList();
					double monthAmount=0;
					for(TimeDIM day:dayList){
						@SuppressWarnings("unchecked")
						List<Object[]> DayOrdersList = emDWH.createQuery("from OrdersFACT o inner join o.orderDate t where t.year ="+year.getYear()+" and t.month ="+month.getMonth()+" and t.day="+day.getDay()).getResultList();
						
						OrdersFACT dayOrdersFACT = new OrdersFACT();
						dayOrdersFACT.setOrderDate(day);
						double dayAmount=0;
						for(Object[] dayItem:DayOrdersList){
							OrdersFACT of = (OrdersFACT) dayItem[0];
							dayAmount+=of.getAmount();
						}
						dayOrdersFACT.setAmount(dayAmount);
						emDWH.merge(dayOrdersFACT);
						monthAmount+=dayAmount;
						dayAmount=0;
						++records;
					}
					OrdersFACT monthOrdersFACT = new OrdersFACT();
					TimeDIM monthTimeDIM = new TimeDIM();
					monthTimeDIM.setYear(year.getYear());
					monthTimeDIM.setMonth(month.getMonth());
					monthOrdersFACT.setOrderDate(monthTimeDIM);
					monthOrdersFACT.setAmount(monthAmount);
					emDWH.merge(monthOrdersFACT);
					yearAmount+=monthAmount;
					monthAmount=0;
					++records;		
				}
				
				OrdersFACT yearOrdersFACT = new OrdersFACT();
				TimeDIM yearTimeDIM = new TimeDIM();
				yearTimeDIM.setYear(year.getYear());
				yearOrdersFACT.setOrderDate(yearTimeDIM);
				yearOrdersFACT.setAmount(yearAmount);
				emDWH.merge(yearOrdersFACT);
				yearAmount=0;
				++records;
			}
			bCommit = true;
			System.out.println("Total records after sum amount grouped by time: "+records);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();			
		}
		if (bCommit) 
			tx.commit();
		
		emOP.close();
		emfOP.close();
		emDWH.close();
		emfDWH.close();
	}
}
	