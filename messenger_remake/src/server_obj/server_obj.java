/**
 *  12/02
 *  oracle 연동 직후
 * 
 */

package server_obj;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import common_use.command;

public class server_obj extends Thread {

	//static (thread)
	static ReentrantLock locker = new ReentrantLock();				// 동기화
	static DB_obj db = new DB_obj();
	static final int max_client_num = 100000;
	static String[][] ip_and_port = new String[max_client_num][2];
	static int next_available_id_n=1;	//다음 부여될 고유번호 관리
	static Socket[] socket_by_id_n = new Socket[max_client_num];
	///////////////////////
	int logged_in_id_n=0;
	String logged_in_id ="";

	ServerSocket server_socket = null;	//static 선언시 thread끼리 공유 (향후 분산처리 가능하므로 static x)
	Socket soc = null;
	int open_port_num;
	BufferedReader listen;
	PrintWriter send;

	//함수 호출 명령어
	

	public server_obj(ServerSocket ss) throws IOException{
		server_socket = ss;
		System.out.println(server_socket + "is made");
	}

	public void init_server(){
		connect_db();
		for (int i=0; i<max_client_num ; i++){
			ip_and_port[i][0] = null;
			socket_by_id_n[i] = null;
		}
	}

	public void run(){	//메인 기능 함수, 클라리언트 접속 대기하고, 이후 접송 종료까지 고객요청 접수 
		try {
			soc = server_socket.accept(); //새 고객 접속 대기
			System.out.println("client is admitted");
			listen = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
			show_connection_info();

			while (soc !=null){		// 이동 등으로 ip주소가 바뀌는 경우 (일시적으로 인터넷이 끊기는 경우 //위 listen, send 포함 함수화 하고 catch 부분에서 해당 함수 계속 다시 시도하도록 변경
				String client_request;
				client_request = listen.readLine();
				System.out.println("client command : "+client_request);
				call_func(client_request);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void call_func(String _client_request){
		try{
			switch(_client_request){
			case command.login:
				login();
				break;
			case command.sign_up:
				sign_up();
				break;
			case command.friends_detail_info:
				//locker.lock();
				send_friend_detail_info();
				//locker.unlock();
				break;
			case command.add_friend:
				add_friend();
				break;
			case command.friend_ip_and_port:
				send_friend_ip_and_port();
				break;
			case command.normal_message:
				listen_normal_message();
				break;
			case command.id_n_from_id:
				send_id_n_from_id();
				break;
			default:
				break;
			}
		} catch (IOException e) {
			System.out.println("client exited");
			if (logged_in_id_n!=0){
				ip_and_port[logged_in_id_n][0]=null;
				ip_and_port[logged_in_id_n][1]="0";
				socket_by_id_n[logged_in_id_n] = null;
			}
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void login() throws IOException{
		System.out.println("enter log-in func");
		String id = listen.readLine();
		int password = Integer.parseInt(listen.readLine());

		boolean b_approved = db.confirm_login(id, password);
		send.println(b_approved);	//make client open main_frame
		send.flush();
		if (b_approved){
			logged_in_id = id;
			logged_in_id_n = db.get_id_n_from_id(id);			
			ip_and_port[logged_in_id_n][0] = soc.getInetAddress().toString().substring(1);
			ip_and_port[logged_in_id_n][1] = Integer.toString( soc.getPort() );
			socket_by_id_n[logged_in_id_n] = soc;
			send.println(logged_in_id_n);
			send.flush();
		}
		System.out.println("finish log-in func");
	}

	public void sign_up() throws IOException{
		System.out.println("enter sign up func");
		String id = listen.readLine();
		int pw = Integer.parseInt(listen.readLine());
		while(db.inspect_id_n_exist_already(next_available_id_n)){		//고유번호 지정 (이미 채워진 고유번호는 넘어감)
			next_available_id_n++;						//만일 모든 고객 수가 다 찰 경우, 무한루트로 돌 수 있음	(오류 가능성)
		}
		db.insert_new_client(next_available_id_n, id, pw);
		System.out.println("new client signed up : ( "+id+ " / "+pw+" / "+next_available_id_n );
		System.out.println("finish log-in func");
	}//주석

	public void add_friend() throws IOException {
		System.out.println("enter add friend func");
		String f_id = listen.readLine();
		boolean b_approved = db.add_friend(logged_in_id_n, logged_in_id, f_id); 
		send.println(b_approved);
		send.flush();	
		System.out.println("finish add friend func");
	}

	public void send_friend_detail_info() throws IOException, ClassNotFoundException{
		System.out.println("enter friend list func");
		Vector[] temp_f_info = db.get_friend_info2(logged_in_id_n);

		//친구 명수, detail_info 값 전송
		int num_of_friend = temp_f_info[0].size();
		send.println(num_of_friend);
		send.flush();
		for(int i=0; i<num_of_friend; i++){
			send.println(temp_f_info[0].elementAt(i));
			send.println(temp_f_info[1].elementAt(i));
			send.flush();
			File file_temp = (File)temp_f_info[2].elementAt(i);
			//send_file_with_obj_stream(file_temp);	// send.println(temp_f_info[2].elementAt(i)) 역할
			send_file_fast(file_temp);
			send.println(temp_f_info[3].elementAt(i));
			send.flush();
		}
		for (int i=0; i<4; i++){
			System.out.println(logged_in_id + "의 친구 detail info : " + temp_f_info[i].toString());
		}
		System.out.println("finish friend list func");
	}

	public void send_file_with_obj_stream(File _file) throws IOException, ClassNotFoundException{//objectstream 버전, 간단하고 잘 동작하나 상당히 많이 느리다.
		soc.getInputStream().read();	//이 부분 없으면, 서버가 대기하지 않고 클라이언트가 정보를 하나씩 받아오기 전에 파일 전부 보내버리는데, 그 후 클라이언트가 값을 가져와도 비어있게 되어 값 입력을 대기하는 현상 존재.
		ObjectOutputStream toClient = new ObjectOutputStream(soc.getOutputStream());
		toClient.reset();
		if (_file!=null){
			toClient.writeObject(_file);
		}
		else{
			File null_file = null;
			toClient.writeObject(null_file);
		}
		toClient.flush();
	}

	public void send_file_fast(File _file) throws IOException{		
		soc.getInputStream().read();	//이 부분 없으면, 서버가 대기하지 않고 클라이언트가 정보를 하나씩 받아오기 전에 파일 전부 보내버리는데, 그 후 클라이언트가 값을 가져와도 비어있게 되어 값 입력을 대기하는 현상 존재.

		//send len
		int _file_len = (int)_file.length();
		send.println(_file_len);
		send.flush();
		
		//file data load
		if(_file_len!=0){
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(_file));
			byte[] mybytearray  = new byte[(int)_file.length()];
			bis.read(mybytearray,0,mybytearray.length);

			//data transmit
			OutputStream os = soc.getOutputStream();
			os.write(mybytearray,0,mybytearray.length);
			os.flush();

			//System.out.println("server send " + _file.getName() + "(" + _file.length() + " bytes)");

			//close
			bis.close();
		}
	}
	
	public void listen_normal_message() throws NumberFormatException, IOException{
		int _f_id_n= Integer.parseInt(listen.readLine());
		String m = listen.readLine();
		System.out.println("to "+ _f_id_n+" - " + m);
		
		send_normal_message(_f_id_n, m);
	}
	
	public void send_normal_message(int _f_id_n, String msg) throws NumberFormatException, IOException{
		Socket f_soc = socket_by_id_n[_f_id_n];
		if (f_soc != null){
			System.out.println("friend being connected");			
			PrintWriter _f_send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(f_soc.getOutputStream())));
			_f_send.println(msg);
		}else{
			System.out.println("friend being not connected");			
		}
	}


	public void send_friend_ip_and_port() throws IOException{
		String f_id = listen.readLine();
		int f_id_n = db.get_id_n_from_id(f_id);
		send.println(ip_and_port[f_id_n][0]);	//friend ip
		send.println(ip_and_port[f_id_n][1]); 	//firned port
		send.flush();
	}
	
	public void send_id_n_from_id() throws IOException{
		String _id = listen.readLine();
		int _id_n = db.get_id_n_from_id(_id); 
		send.println(_id_n);
		send.flush();
	}

	public void connect_db(){
		db.connect();
	}

	public void show_client_info(){
		System.out.println( soc.getRemoteSocketAddress());
	}

	public void show_connected_client_10(){
		for(int i=0; i<10; i++){
			System.out.print(ip_and_port[i][0] +"/"+ip_and_port[i][1]+", ");
		}
		System.out.println();
	}

	public void show_connection_info(){
		System.out.println("server 정보 : "+soc.getLocalSocketAddress());
		System.out.println("client 정보 : "+soc.getRemoteSocketAddress());
		System.out.println("server socket 정보 : " + server_socket.toString());
	}
}

