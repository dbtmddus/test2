package my_sql_simple;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DB_obj {

	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;

	//static String url = "jdbc:mysql://localhost:3306/";	
	static String url = "jdbc:mysql://localhost/test?useSSL=false";
	static String id = "root";
	static String pw = "134652";

	public DB_obj(){

	}

	public void connect(){	//http://onoctober.tistory.com/54
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver load success");
			try{
				conn = DriverManager.getConnection(url, id, pw);
				//conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=134652");
				System.out.println("db connect success");
			}
			catch (SQLException e){
				System.out.println("db connect fail");
				System.out.println(e);
			}
		}
		catch(ClassNotFoundException e){
			System.out.println("driver load fail");
			System.out.println(e);
		}
	}


	public void exec_quety(String str_query){
		try{
			String sql = str_query;

			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, x);
			rs = pstmt.executeQuery();
			//System.out.println(rs.next());
			while (rs.next()){
				System.out.print("name : " + rs.getString("NAME"));
				System.out.println(" / password : " + rs.getInt("password"));
				System.out.println();
			}
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void insert_new_client(int _id_n, int _den_1, int _den_2,int _den_3){
		try{
			String sql="insert into t_test values(?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id_n);
			pstmt.setInt(2, _den_1);
			pstmt.setInt(3, _den_2);
			pstmt.setInt(4, _den_3);
			pstmt.executeUpdate();
			//rs = pstmt.executeQuery();	//!!!!!!!!!!!!!!!!! 결과물이 없는 쿼리의 경우 쓰면 에러 유말 (쿼리 동작은 함)
			
			System.out.println("insert complete");
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void delete_all(){
		try{
			String sql="delete from login_test";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		
			System.out.println("delete complete");
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	public void show_all(){
		try{
			String sql = "select * from t_test";

			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, x);
			rs = pstmt.executeQuery();
			//System.out.println(rs.next());
			while (rs.next()){
				System.out.print("id_n : " + rs.getInt("id_n"));
				System.out.println(" / den_1 : " + rs.getInt("den_1"));
				System.out.println();
			}
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	public void disconnect() throws SQLException{
		conn.close();
	}

	public int[] search_info_from_id_n(int _id_n){
		int[] return_v = new int[4];
		try{
			String sql="select * from t_test where id_n=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id_n);
			rs = pstmt.executeQuery();

			if (rs.next()){
				return_v[0] = rs.getInt("id_n");
				return_v[1] = rs.getInt("den_1");
				return_v[2] = rs.getInt("den_2");
				return_v[3] = rs.getInt("den_3");
				rs.close();
				pstmt.close();					
				return return_v;
			}else{
				System.out.println("해당하는 값이 없습니다.");
				rs.close();
				pstmt.close();
			}			
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		int[] error_return = {-1,-1,-1,-1};
		return error_return;
	}
	
	public int info_size(){
		int size_info = 0;
		try{
			String sql="select * from t_test";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()){
				size_info++;
			}
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return size_info;
	}
	
	public Vector send_all_info(){
		Vector<int[]> info= new Vector(0);
		//int info_size=0;
		try{
			String sql="select * from t_test";
			info.set(0, new int[]{0,0,0,0});
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()){
				//info_size++;
				int[] t_arr = new int[4];
				t_arr[0] = rs.getInt("id_n");
				t_arr[1] = rs.getInt("den_1");
				t_arr[2] = rs.getInt("den_2");
				t_arr[3] = rs.getInt("den_3");
				info.add(t_arr);
			}
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		//info.elementAt(0)[0] = info_size;
		return info;
	}
	
	
}//end class
