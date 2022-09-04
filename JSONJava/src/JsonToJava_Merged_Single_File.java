import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonToJava_Merged_Single_File {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, StreamWriteException, DatabindException, IOException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");//this class is responsible for all JDBC methods and help us to talk to db
		Connection conn=null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/Business","root","Asdf1234");//connecting to Mysql
		
		Statement st = conn.createStatement();//Create  object statement class to execute queries
		ResultSet rs = st.executeQuery("select * from business.customerinfo where Location='Asia';");//holds the query result
		
		ArrayList<CustomerDetails> a = new ArrayList<>();//Create array list to store multiple customer details inside each objects
		JSONArray js = new JSONArray();//creates an array object
		
		while(rs.next()) {//moves the pointer to end of 1st row and then prints all columns in that row
			CustomerDetails c = new CustomerDetails();//create object to pass value to setter methods
			c.setCoureseName(rs.getString(1));
			c.setPurchasedDate(rs.getString(2));
			c.setAmount(rs.getInt(3));
			c.setLocation(rs.getString(4));
			a.add(c);//Store objects of customer details into arraylist
		}
		
		//create a loop to access each arraylist object and convert them to json
		for(int i=0;i<a.size();i++) {
			ObjectMapper o = new ObjectMapper();
			o.writeValue(new File("C:\\Users\\khitish\\git\\repository3\\JSONJava\\customerInfo"+i+".json"), a.get(i));//create a new file and put the content
			
			//Create JSON String from java object. Create JSON String from java object using Gson dependency
			Gson g = new Gson();//convert java object to json string
			String jsonString = g.toJson(a.get(i));//takes argument as java object
			js.add(jsonString);
		}
		
		
		
		JSONObject jo = new JSONObject();
		jo.put("data", js);//Entire JSON file is present inside js
		System.out.println(jo.toJSONString());
		
		String unescapeString = StringEscapeUtils.unescapeJava(jo.toJSONString());//use Apache common text to to remove backslash(escape)
		System.out.println(unescapeString);
		String str1 = unescapeString.replace("\"{", "{");//removes the double quotes from beginning
		String finalString = str1.replace("}\"", "}");//removes the double quotes from beginning
		System.out.println(finalString);
		
		//Convert text we are getting in console to a file
		try (FileWriter file = new FileWriter("C:\\Users\\khitish\\git\\repository3\\JSONJava\\SingleJSON.json")){
			file.write(finalString);//file writer accepts only String args
		}
		
		
		conn.close();
	}

}
