/***
 * 12.03 
 * 이 이후에는 고객의 ip, port정보를 db에 저장해서 매번 삽입,삭제해서 처리할지,
 * 서버 내에서 처리할지 고민해야하며, 회원마다 고유번호 (숫자, 오름차순 저장)을 통해 빠른 접근을 구현하는 게 좋을듯
 */

package server_obj;

import java.io.IOException;
import java.net.ServerSocket;

public class main {

	public static void main(String[] args) throws IOException {

		ServerSocket server_socket = new ServerSocket(1245);
		server_obj server0 = new server_obj(server_socket);
		server0.init_server();
		//server0.connect_db();

		server_obj server1 = new server_obj(server_socket);
		server1.start();

		server_obj server2 = new server_obj(server_socket);
		server2.start();

		while(true){
			server0.show_connected_client();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//server1.request_friend_list_test();
		
	}//end main
}



//http://blog.naver.com/mktcrmer/220854105687 - oracle express 설치



