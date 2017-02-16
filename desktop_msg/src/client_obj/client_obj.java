package client_obj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class client_obj {

	static Socket connected_socket = null;
	static String server_ip;	//"127.0.0.1";
	//static String server_ip = "172.30.96.107";
	static int server_port;
	static PrintWriter send;
	static BufferedReader listen;

	static Socket socket_for_chat = null;

	public client_obj(String ip){//, int port_n) {
		server_ip = ip;
		server_port=1244;
		//server_port = port_n;
	}
	public void enter() throws IOException{
		while (true){
			try {
				connected_socket = new Socket(server_ip, server_port);
				send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream()))); //�߰�
				listen = new BufferedReader(new InputStreamReader(connected_socket.getInputStream()));

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				socket_for_chat = new Socket(server_ip, server_port);
				break;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.exit(1);
				break;
			} catch (IOException e) {
				System.out.println(server_port+" is used, connect with other port");
				server_port++;
			}
		}
		/************************************************************************************/

		System.out.println("����Ǽ̽��ϴ�");
		show_info();

		open_login_frame();
	}
	public void open_login_frame() throws IOException{
		/*Thread t1 = new Thread(new Runnable() {	//�Ƹ� class�ε�
			public void run(){
				try {
					while(true){
						System.out.println("ä�� ���� �����..");
						BufferedReader listen_chat = new BufferedReader(new InputStreamReader(socket_for_chat.getInputStream()));
						String str_msg = listen_chat.readLine();
						System.out.println("ä�� ���� input : "+str_msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
	*/
		//chatting chatting1 =new chatting(socket_for_chat, id);
		//Thread t1 = new Thread(chatting1);
		//t1.start();
		
		login_frame_swing lf = new login_frame_swing(connected_socket,socket_for_chat );
	}

	public void show_info(){
		System.out.println("client ���� : "+connected_socket.getLocalSocketAddress());
		System.out.println("server ���� : "+connected_socket.getRemoteSocketAddress());
	}

}//end