package server_obj;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
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

	static String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";	
	static String id = "dbtmddus";
	static String pw = "134652";

	public DB_obj(){

	}

	public void connect(){	//http://onoctober.tistory.com/54
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("driver load success");
			try{
				conn = DriverManager.getConnection(url, id, pw);
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
			rs = pstmt.executeQuery();
			while (rs.next()){
				//System.out.print("id : " + rs.getString("id"));
				//System.out.println(" / password : " + rs.getInt("password"));
				//System.out.println();
			}
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void insert_new_client(int new_id_n, String new_id, int new_pw){	//회원가입
		try{
			String sql="insert into login values(?,?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, new_id_n);
			pstmt.setString(2, new_id);
			pstmt.setInt(3, new_pw);			
			pstmt.executeUpdate();
			//rs = pstmt.executeQuery();	//!!!!!!!!!!!!!!!!! 결과물이 없는 쿼리의 경우 쓰면 에러 유말 (쿼리 동작은 함)

			System.out.println("insert complete");
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void delete_all(){				// login 테이블 정보 모두 삭제
		try{
			String sql="delete from login";

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
			String sql = "SELECT * FROM login";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()){
				System.out.print("id_n : " + rs.getInt("id_n"));
				System.out.print("/ id : " + rs.getString("id"));
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
	public void disconnect() throws SQLException{
		conn.close();
	}

	public boolean confirm_login(String input_id, int input_pw){
		String sql = "select password from login where id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input_id);
			rs = pstmt.executeQuery();
			if (rs.next()){
				int pw_from_db = rs.getInt("password");
				if (  pw_from_db== input_pw){	//해당 id의 비밀번호
					//System.out.println("log-in is confirmed!");
					pstmt.close();
					rs.close();
					return true;
				}
				else{
					//System.out.println("log-in is denied_1");
				}
			}else{
				//System.out.println("log-in is denied_2");				
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("error - at confirm_login");
			e.printStackTrace();
		}
		return false;
	}

	public Vector<String> get_friend_list(int _id_n){
		Vector<String> friend_list = new Vector<String>(0);
		try{
			String sql = "select friend_relation.f_id from login,friend_relation where login.id_n = friend_relation.id_n and login.id_n = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id_n);
			rs = pstmt.executeQuery();

			friend_list.addElement("0");
			int rs_size = 0;				//rs 크기를 빠르게 가져오는 함수 없는듯??? resultSet.last() followed by resultSet.getRow() 느리ㄷ
			for (int i=0; rs.next(); i++){
				friend_list.addElement(rs.getString("f_id"));			//friend_list.f_id 로 불러오면 오류남
				rs_size++;
			}
			friend_list.set(0, Integer.toString(rs_size));
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		//System.out.println(friend_list.toString());
		return friend_list;
	}

	public Vector[] get_friend_info2(int _id_n) throws IOException{

		Vector[] f_info = new Vector[4];
		f_info[0] = new Vector<String>(0);	//f_id
		f_info[1] = new Vector<Integer>(0);	//f_id_n
		f_info[2] = new Vector<File>(0);	//f_image
		f_info[3] = new Vector<String>(0);	//f_stmt_message

		try{
			String sql = "with F(id_n) as(select f_id_n from friend_relation where id_n=?) select D.id, D.id_n, D.image, D.stmt_msg from detail_info D, F where D.id_n=F.id_n";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id_n);
			rs = pstmt.executeQuery();

			for (int i=0; rs.next(); i++){
				String id_temp = rs.getString("id");
				int id_n_temp = rs.getInt("id_n");
				Blob blob_temp = rs.getBlob("image");
				String stmt_msg_temp = rs.getString("stmt_msg");

				f_info[0].addElement(id_temp);			//friend_relation.f_id 로 불러오면 오류남
				f_info[1].addElement(id_n_temp);
				f_info[2].addElement(blob_to_file(blob_temp, id_temp+".jpg"));
				f_info[3].addElement(stmt_msg_temp);
			}
			rs.close();
			pstmt.close();					
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return f_info;
	}

	public boolean add_friend(int _id_n, String _id, String _f_id){
		int f_id_n= get_id_n_from_id(_f_id);

		if (f_id_n !=-1){
			try {
				String sql = "insert into friend_relation values(?,?,?,?)";	
				//System.out.println(sql);
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, _id_n);
				pstmt.setString(2, _id);
				pstmt.setInt(3, f_id_n);
				pstmt.setString(4, _f_id);
				pstmt.executeUpdate();
				pstmt.close();					
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public int get_id_n_from_id(String input_id){
		int return_v = 0;
		try{
			String sql = "select id_n from login where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, input_id);
			rs = pstmt.executeQuery();

			if (rs.next()){
				return_v = rs.getInt("id_n");
				rs.close();
				pstmt.close();					
				return return_v;
			}else{
				System.out.println("고유번호 id_n 오류");
				rs.close();
				pstmt.close();					
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return -1;	//에러 경우 리턴값
	}

	public String get_id_from_id_n(int _id_n){
		String return_v = null;
		try{
			String sql = "select id from login where id_n=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id_n);
			rs = pstmt.executeQuery();

			if (rs.next()){
				return_v = rs.getString("id");
				rs.close();
				pstmt.close();					
				return return_v;
			}else{
				System.out.println("id 오류 at db");
				rs.close();
				pstmt.close();					
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return null;	//에러 경우 리턴값
	}

	public boolean inspect_id_n_exist_already(int _id_n){
		try{
			String sql = "select id_n from login where id_n=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id_n);
			rs = pstmt.executeQuery();

			if (rs.next()){
				rs.close();
				pstmt.close();					
				return true;
			}else{
				rs.close();
				pstmt.close();					
				return false;
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	public boolean inspect_id_exist_already(String id){
		try{
			String sql = "select id_n from login where id_n=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()){
				rs.close();
				pstmt.close();					
				return false;
			}else{
				rs.close();
				pstmt.close();					
				return true;
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public void insert_image(int _id_n, String _file_path) throws IOException{
		try{
			String sql = "update detail_info set image = ? where id_n = ?";

			pstmt = conn.prepareStatement(sql);
			File f = new File(_file_path);
			FileInputStream file_in = new FileInputStream(f);
			pstmt.setBinaryStream(1, file_in, (int)f.length());
			pstmt.setInt(2, _id_n);
			pstmt.executeUpdate();

			pstmt.close();
			file_in.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	public Vector<File> download_image(int _id_n) throws IOException{
		Vector<File> v_f_images = new Vector<File>(0);

		try{
			String sql = "select image from detail_info where id_n = ?";
			pstmt = conn.prepareStatement(sql);			
			pstmt.setInt(1, _id_n);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Blob blob_image = rs.getBlob("image");
				InputStream file_in = blob_image.getBinaryStream();
				File file_from_db = new File(_id_n+"image_from_db.jpg");
				FileOutputStream file_out = new FileOutputStream(file_from_db);

				int b=0;
				while ( (b=file_in.read()) != -1 ) {
					file_out.write((byte)b);
				}
				file_in.close();
				file_out.close();
				v_f_images.addElement(file_from_db);
			}
			pstmt.close();
			rs.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return v_f_images;
	}

	public File blob_to_file(Blob _blob, String _name) throws SQLException, IOException{
		if (_blob !=null){
			InputStream file_in = _blob.getBinaryStream();
			File f = new File(_name);
			FileOutputStream file_out = new FileOutputStream(f);

			int b=0;
			while ( (b=file_in.read()) != -1 ) {
				file_out.write((byte)b);
			}
			file_in.close();
			file_out.close();		
			return f;
		}
		else{
			return null;
		}
	}
}// end 
