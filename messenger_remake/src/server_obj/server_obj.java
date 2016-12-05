/**
 *  12/02
 *  oracle 연동 직후
 * 
 */

package server_obj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import javax.sound.sampled.Port;
import java.sql.*;
import java.util.Vector;

public class server_obj extends Thread {

	static DB_obj db = new DB_obj();
	static final int max_size_ip_and_port = 100000;
	static String[][] ip_and_port = new String[max_size_ip_and_port][2];
	static int available_id_n=1;
	int logged_in_id_n=0;
	String logged_in_id ="";
	
	ServerSocket server_socket = null;	//static 선언시 thread끼리 공유
	Socket soc = null;
	int open_port_num;
	BufferedReader listen;
	PrintWriter send;

	final String login = "login";	//!! switch용도, system 버전에 따라 오류날 수 있음
	final String signin = "signin";
	final String request_friend_list = "request_friend_list";
	final String add_friend = "add_friend";

	public server_obj(ServerSocket ss) throws IOException{
		server_socket = ss;
		System.out.println(server_socket + "is made");
	}

	public void init_server(){
		connect_db();
		for (int i=0; i<max_size_ip_and_port ; i++){
			ip_and_port[i][0] = "blank";
		}
	}

	public void run(){
		try {
			soc = server_socket.accept(); //새 고객 접속 대기
		} catch (IOException e) {
			System.out.println("e_num3 - 고객 접속 오류");
			e.printStackTrace();
		}

		System.out.println("client is admitted");
		System.out.println("server 정보 : "+soc.getLocalSocketAddress());
		System.out.println("client 정보 : "+soc.getRemoteSocketAddress());
		System.out.println("server socket 정보 : " + server_socket.toString());

		try {
			listen = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (soc !=null){		// 이동 등으로 ip주소가 바뀌는 경우 (일시적으로 인터넷이 끊기는 경우) 
			//위 listen, send 포함 함수화 하고 catch 부분에서 해당 함수 계속 다시 시도하도록 변경
			try {
				String str_from_client;
				str_from_client = listen.readLine();
				System.out.println("client command : "+str_from_client);

				switch(str_from_client){
				case login:
					login();
					break;
				case signin:
					signin();
					break;
				case request_friend_list:
					request_friend_list();
					break;
				case add_friend:
					add_friend();
					break;

				default:
					break;
				}
			} catch (IOException e) {
				System.out.println("client exited");
				if (logged_in_id_n!=0){
					ip_and_port[logged_in_id_n][0]="blank";
					ip_and_port[logged_in_id_n][1]="blank";
				}
				e.printStackTrace();
				break;
			}
		}
	}

	public void login() throws IOException{
		System.out.println("log-in handling");
		String id = listen.readLine();
		int password = Integer.parseInt(listen.readLine());

		System.out.println("id : "+id+ ", password : "+password+"\n");
		System.out.println("log-in is " +db.confirm_login(id, password));

		boolean confirm = db.confirm_login(id, password);
		send.println(confirm);	//make client open main_frame
		send.flush();
		if (confirm == true){
			logged_in_id_n = db.get_id_n_from_id(id);			
			ip_and_port[logged_in_id_n][0] = soc.getInetAddress().toString().substring(1);
			ip_and_port[logged_in_id_n][1] = Integer.toString( soc.getPort() );
			logged_in_id = id;
			
			send.println(db.get_id_n_from_id(id));
			send.flush();
			//open
		}
	}
	public void signin() throws IOException{
		System.out.println("sign-in handling");
		String id = listen.readLine();
		int pw = Integer.parseInt(listen.readLine());
		while(db.inspect_id_n_exist_already(available_id_n)){		//고유번호 지정 (이미 채워진 고유번호는 넘어감)
			available_id_n++;						//만일 모든 고객 수가 다 찰 경우, 무한루트로 돌 수 있음	(오류 가능성)
		}
		System.out.println("id : "+id+ ", password : "+pw+", id_n : "+available_id_n+"\n");
		System.out.println(soc.getInetAddress());
		System.out.println(soc.getPort());
		System.out.println(soc.getRemoteSocketAddress());
		db.insert_new_client(available_id_n, id, pw);
	}//주석

	public void request_friend_list() throws IOException{
		System.out.println("request friend list handling");
		String id = listen.readLine();
		Vector<String> v = db.get_friend_list(id);
		System.out.println(v.toString());
		send.println(v.toString());
		send.flush();
	}

	public void add_friend() throws IOException{
		String f_id = listen.readLine();
		
		db.add_friend(logged_in_id_n, logged_in_id, f_id);
		
	}


	public void connect_db(){
		db.connect();
	}
	public void get_client_imf(){
		System.out.println( soc.getRemoteSocketAddress());
	}

	public void show_connected_client(){
		for (int i=0; i<10; i++){
			System.out.print(ip_and_port[i][0] +"/"+ip_and_port[i][1]+", ");
		}
		System.out.println();
	}
}

