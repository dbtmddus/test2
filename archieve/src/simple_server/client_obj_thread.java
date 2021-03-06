package simple_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class client_obj_thread extends Thread{

	static Socket connected_socket = null;
	static String server_ip;	//"127.0.0.1";
	static int server_port;
	
	static PrintWriter send;
	static BufferedReader listen;
	
	
	public client_obj_thread(String ip, int port){//, int port_n) {
		server_ip = ip;
		server_port= port;
	}
	public void run(){
		try {
			enter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void enter() throws IOException{
		while (true){
			try {
				connected_socket = new Socket(server_ip, server_port);
				send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream()))); //추가
				listen = new BufferedReader(new InputStreamReader(connected_socket.getInputStream()));			
				break;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.exit(1);
				break;
			} catch (IOException e) {
				System.out.println(server_port+" is used, connect with other port");
				server_port++;
				e.printStackTrace();
			}
		}
		/************************************************************************************/
		System.out.println("입장되셨습니다");
		show_info();		
	}
	
	public void show_info(){
		System.out.println("client 정보 : "+connected_socket.getLocalSocketAddress());
		System.out.println("server 정보 : "+connected_socket.getRemoteSocketAddress());
	}
		
}//end
