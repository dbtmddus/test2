package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.plaf.SliderUI;

public class client {

	public static void main(String[] args) throws IOException, InterruptedException {

		boolean server_on = false;
		Socket connected_socket = null;
		int port_num = 1245;
		String server_ip = "127.0.0.1";
		//String server_ip = "172.30.96.107";

		/************************************************************************************/ //서버 port에 접속
		while (true){
			try {
				connected_socket = new Socket(server_ip, port_num);
				server_on = true;
				break;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("unknown host error : "+ e);
				System.exit(1);
				break;
			} catch (IOException e) {
				if (port_num > 2000){
					System.exit(1);
					break;		
				}
				//port_num++;
				System.out.println("서버가 닫혀있거나 가능한 모든 포트가 사용중입니다");	// 추후 다른 포트로 접속 시도하도록 변경, 관련 함수도 있을것으로 생각
			}
		}
		/************************************************************************************/

		System.out.println("입장되셨습니다");
		System.out.println("client 정보 : "+connected_socket.getLocalSocketAddress());
		System.out.println("server 정보 : "+connected_socket.getRemoteSocketAddress());

		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream()))); //추가
		out.println("dddddddddddddddddddddddddddddd\n");
		out.flush();

		/*BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream())); //추가
		out2.write("dddddddddddddddddddddddddd\n");
		out2.flush();
		 */
		//login_frame_swing lf = new login_frame_swing(server_ip, port_num);
		login_frame_swing lf = new login_frame_swing(connected_socket);

		while(true){}
	}//end main
}
