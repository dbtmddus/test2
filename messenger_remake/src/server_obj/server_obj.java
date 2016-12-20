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

public class server_obj extends Thread {

	static ReentrantLock locker = new ReentrantLock();				// 동기화

	static DB_obj db = new DB_obj();
	static final int max_size_ip_and_port = 100000;
	static String[][] ip_and_port = new String[max_size_ip_and_port][2];
	static int available_id_n=1;
	int logged_in_id_n=0;
	String logged_in_id ="";

	static ServerSocket server_socket = null;	//static 선언시 thread끼리 공유
	static Socket soc = null;
	static int open_port_num;
	static BufferedReader listen;
	static PrintWriter send;

	final String login = "login";	//!! switch용도, system 버전에 따라 오류날 수 있음
	final String signin = "signin";
	final String request_friend_list = "request_friend_list";
	final String add_friend = "add_friend";
	final String request_friend_ip = "request_friend_ip";
	final String normal_message = "normal_message";

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
			listen = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
		} catch (IOException e) {
			System.out.println("e_num3 - 고객 접속 오류");
			e.printStackTrace();
		}

		System.out.println("client is admitted");
		System.out.println("server 정보 : "+soc.getLocalSocketAddress());
		System.out.println("client 정보 : "+soc.getRemoteSocketAddress());
		System.out.println("server socket 정보 : " + server_socket.toString());

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
					//locker.lock();
					response_f_info();
					//locker.unlock();
					break;
				case add_friend:
					add_friend();
					break;
				case request_friend_ip:
					get_ip_from_id();
					break;
				case normal_message:
					normal_message();
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
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void login() throws IOException{
		System.out.println("enter log-in func");
		String id = listen.readLine();
		int password = Integer.parseInt(listen.readLine());

		System.out.println("id : "+id+ ", password : "+password+"\n");
		System.out.println("log-in is " +db.confirm_login(id, password));

		boolean b_approved = db.confirm_login(id, password);
		send.println(b_approved);	//make client open main_frame
		send.flush();
		if (b_approved == true){
			logged_in_id = id;
			logged_in_id_n = db.get_id_n_from_id(id);			
			ip_and_port[logged_in_id_n][0] = soc.getInetAddress().toString().substring(1);
			ip_and_port[logged_in_id_n][1] = Integer.toString( soc.getPort() );
			send.println(logged_in_id_n);
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

	public void normal_message() throws NumberFormatException, IOException{
		int temp_id_n= Integer.parseInt(listen.readLine());
		String m = listen.readLine();

		System.out.println("to "+ temp_id_n+" - " + m);
	}

	public void response_f_info() throws IOException, ClassNotFoundException{

		System.out.println("enter friend list func");
		Vector[] temp_f_info = db.get_friend_info2(logged_in_id_n);

		for (int i=0; i<4; i++){
			System.out.println( "친구 detail info : " + temp_f_info[i].toString());
		}

		int num_of_friend = temp_f_info[0].size();
		send.println(num_of_friend);
		send.flush();

		for(int i=0; i<num_of_friend; i++){
			send.println(temp_f_info[0].elementAt(i));
			send.println(temp_f_info[1].elementAt(i));
			send.flush();
		
			File file_temp = (File)temp_f_info[2].elementAt(i);
			send_file(file_temp);
		
			send.println(temp_f_info[3].elementAt(i));
			send.flush();
		}
	}

	public void send_file(File _file) throws IOException, ClassNotFoundException{
		soc.getInputStream().read();	//이 부분 없으면, 서버가 대기하지 않고 클라이언트가 정보를 하나씩 받아오기 전에 파일 전부 보내버리는데, 그 후 클라이언트가 값을 가져와도 비어있게 되어 값 입력을 대기하는 현상 존재.
		ObjectOutputStream toClient = new ObjectOutputStream(soc.getOutputStream());
		toClient.reset();
		if (_file.exists()){
			toClient.writeObject(_file);
		}
		else{
			File null_file = null;
			toClient.writeObject(null_file);
		}
		toClient.flush();
	}
	public void send_file_trashed(File _file, BufferedReader _listen, PrintWriter _send) throws IOException{	// 수정 예정. 이는 파일 1개 전용이며, 여러개 전용은 send_file project참고. 

		OutputStream os = soc.getOutputStream();	//send로 보내도 되지만, 아마 printwriter는 좀 느릴듯
		FileInputStream fis = new FileInputStream(_file);
		BufferedInputStream bis = new BufferedInputStream(fis);

		byte[] mybytearray  = new byte [(int)_file.length()];
		bis.read(mybytearray,0,mybytearray.length);
		System.out.println("Sending " + _file.getName() + "(" + mybytearray.length + " bytes)");
		os.write(mybytearray,0,mybytearray.length);
		os.flush();       

		fis.close();
		bis.close();
	}

	public void add_friend() throws IOException {
		String f_id = listen.readLine();
		boolean b = db.add_friend(logged_in_id_n, logged_in_id, f_id); 
		if (b){
			final String t_str = "confirm_add_friend";
			send.println(t_str);
			send.flush();	
		}else{
			final String t_str = "refuse_add_friend";
			send.println(t_str);
			send.flush();
		}
	}

	public void get_ip_from_id() throws IOException{
		String f_id = listen.readLine();
		System.out.println("f_id : ----------------------- "+f_id);
		int f_id_n = db.get_id_n_from_id(f_id);
		String f_ip = ip_and_port[f_id_n][0];
		String f_port = ip_and_port[f_id_n][1];
		System.out.println(f_ip + "////////////"+f_port);
		send.println(f_ip);
		send.println(f_port);
		send.flush();
	}

	public void connect_db(){
		db.connect();
	}
	public void get_client_info(){
		System.out.println( soc.getRemoteSocketAddress());
	}

	public void show_connected_client(){
		for (int i=0; i<10; i++){
			System.out.print(ip_and_port[i][0] +"/"+ip_and_port[i][1]+", ");
		}
		System.out.println();
	}
}

