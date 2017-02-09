/**
 *  12/02
 *  oracle ���� ����
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
	ServerSocket server_socket = null;	//static ����� thread���� ���� (���� �л�ó�� �����ϹǷ� static x)
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
			soc = server_socket.accept(); //�� ���� ���� ���
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

	public void run(){	//���� ��� �Լ�, Ŭ�󸮾�Ʈ ���� ����ϰ�, ���� ���� ������� ������û ���� 
		receive_new_client_connection();
	}

	public void show_connection_info(Socket _soc){
		System.out.println("server ���� : "+_soc.getLocalSocketAddress());
		System.out.println("client ���� : "+_soc.getRemoteSocketAddress());
		System.out.println("server socket ���� : " + server_socket.toString());
	}
}
