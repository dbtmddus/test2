/***
 * 12.07
 * friend_frame 대규모 변경 직전 저장
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
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}



//http://blog.naver.com/mktcrmer/220854105687 - oracle express 설치

//println과 print 혼용시 문자열이 합쳐지는 오류
//swing구현중 이벤트 헨들러 실수로 버튼 하나에 2번 이상 들어가면 해당 횟수만큼 반복 되는듯 (버튼 1번 눌렀는데 2번 이벤트 발생 등)
//String sql = "select f_id from login, friend_list where login.id_n = ?"; 로 수행 후,
// rs.getString(friend_list.f_id) 하면 오류남..... ㅂㄷㅂㄷ 그냥 f_id로 불러와야함
