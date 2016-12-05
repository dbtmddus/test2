/***
 * 12.05 
 * 친구 추가 기능 구현중, db에 삽입되지 않고 있는 오류.  sql 오류 의심
 *
 */

package server_obj;

import java.io.IOException;
import java.net.ServerSocket;

public class main {

	static ServerSocket server_socket;
	static server_obj server0;
	
	public static void main(String[] args) throws IOException {

		server_socket = new ServerSocket(1245);
		server0 = new server_obj(server_socket);
		server0.init_server();
		//server0.connect_db();

		server_obj server1 = new server_obj(server_socket);
		server1.start();

		server_obj server2 = new server_obj(server_socket);
		server2.start();
		
		server_obj server3 = new server_obj(server_socket);
		server3.start();
		
		show_connected_client();
		//server1.request_friend_list_test();
		
	}//end main
	
	static public void show_connected_client(){
		while(true){
			try {
				server0.show_connected_client();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}



//http://blog.naver.com/mktcrmer/220854105687 - oracle express 설치



