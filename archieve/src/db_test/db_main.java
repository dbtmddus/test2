package db_test;

import java.sql.SQLException;

import server_obj.DB_obj;

public class db_main {

	public static void main(String[] args) throws SQLException {
		DB_obj db = new DB_obj();
		db.connect();
		
		//db.insert_new_client("client20000", 222);
		db.show_all();
		
		/*System.out.println("-----------------------------------------");
		db.delete_all();
		db.show_all();
*/		
		db.disconnect();
	}
}