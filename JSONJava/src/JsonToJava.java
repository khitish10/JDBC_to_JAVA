import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JsonToJava {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");//this class is responsible for all JDBC methods and help us to talk to db
		Connection conn=null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/Business","root","Asdf1234");//connecting to Mysql
		
		//Create  object statement class to execute queries
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from business.customerinfo where Location='Asia' limit 1;");//holds the query result
//		rs.next();//setting the pointer for to 1st row
//		rs.getString(1);//1st column is string type
//		rs.getString(2);//2nd column is String type
//		rs.getInt(3);//3rd column is Int type
//		rs.getString(4);//4th column 
		while(rs.next()) {//moves the pointer to end of 1st row and then prints all columns in that row
			CustomerDetails c = new CustomerDetails();//create object to passs value to setter methods
			c.setCoureseName(rs.getString(1));
			c.setPurchasedDate(rs.getString(2));
			c.setAmount(rs.getInt(3));
			c.setLocation(rs.getString(4));
			
			System.out.println(c.getCoureseName());
			System.out.println(c.getPurchasedDate());
			System.out.println(c.getAmount());
			System.out.println(c.getLocation());
		}
		conn.close();
	}

}
