package test;
import java.sql.*;

public class main {

	public static void main(String[] args) {
		
		Connection conn = null;
		
		String url = null;
		String id = "dbtmddus";
		String pw = "134652";
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver load success");
			
			try{
				url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";	
				//http://onoctober.tistory.com/54
				conn = DriverManager.getConnection(url, id, pw);
				System.out.println("db connect success");
			}
			catch (SQLException e){
				System.out.println(e);
			}
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
}
