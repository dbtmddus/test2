package test;
import java.sql.*;

public class sql_main {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
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
				
				/*********************************************************************************/
				try{
					//String sql = "select * from login_test";
					//String sql="insert into login_test values('client15', 222);";
					//String sql="insert into login_test values('client200', 222)";
					
					String sql="insert into login_test2 values(?, ?)";
					
					System.out.println(sql);
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "client10000");
					pstmt.setInt(2, 500);
					
					pstmt.executeUpdate();
					//pstmt.setString(1, x);
					/*rs = pstmt.executeQuery();
					//System.out.println(rs.next());
					while (rs.next()){
						System.out.println("name : " + rs.getString("NAME"));
						System.out.println("name : " + rs.getInt("password"));
					}
					rs.close();
					*/
					pstmt.close();					
					
				}
				catch (SQLException e){
					e.printStackTrace();
				}
				conn.close();
				
				/*********************************************************************************/				
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
