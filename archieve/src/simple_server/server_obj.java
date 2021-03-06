/**
 *  12/02
 *  oracle 연동 직후
 * 
 */

package simple_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server_obj extends Thread {

	//static (thread)
	static final int max_client_num = 100000;
	ServerSocket server_socket = null;	//static 선언시 thread끼리 공유 (향후 분산처리 가능하므로 static x)
	int open_port_num;
	Socket soc = null;
	BufferedReader listen;
	PrintWriter send;
	//

	public server_obj(ServerSocket ss) throws IOException{
		server_socket = ss;
		System.out.println(server_socket + "is made");
	}

	public void receive_new_client_connection(){
		try {
			System.out.println("watting new client");
			soc = server_socket.accept(); //새 고객 접속 대기
			System.out.println("client is admitted");
			listen = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));

			show_connection_info(soc);

			
			//Thread t1 = new Thread( run send());
			//Thread t2 = new Thread(listen());
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void send(){
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		send.println(str);
	}
	
	public void listen() throws IOException{
		System.out.println( listen.readLine() );
	}

	public void run(){	//메인 기능 함수, 클라리언트 접속 대기하고, 이후 접송 종료까지 고객요청 접수 
		receive_new_client_connection();
	}

	public void show_connection_info(Socket _soc){
		System.out.println("server 정보 : "+_soc.getLocalSocketAddress());
		System.out.println("client 정보 : "+_soc.getRemoteSocketAddress());
		System.out.println("server socket 정보 : " + server_socket.toString());
	}
}

