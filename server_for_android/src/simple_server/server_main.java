package simple_server;

import java.io.IOException;
import java.net.ServerSocket;

import my_sql_simple.DB_obj;

public class server_main {
	static DB_obj db;
	public static void main(String[] args) throws IOException {
		ServerSocket server_socket = new ServerSocket(1245);
		
		server_obj s0 = new server_obj(server_socket);
		s0.connect_db();
				
		server_obj s = new server_obj(server_socket);
		//System.out.println(s.listen.readLine());
		s.run();
		//s.send_info_from_id_n(475294);	//4�� ����
		
		
		
		
		//s.send.println("str_from_pc");
		//s.send.flush();
	}
}
