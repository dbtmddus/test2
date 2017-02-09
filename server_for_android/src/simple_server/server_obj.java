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
import java.util.Arrays;
import java.util.Vector;

import my_sql_simple.DB_obj;

public class server_obj extends Thread {

	static final int max_client_num = 100000;
	ServerSocket server_socket = null;	//static ����� thread���� ���� (���� �л�ó�� �����ϹǷ� static x)
	int open_port_num;
	Socket soc = null;
	BufferedReader listen;
	PrintWriter send;

	static DB_obj db = new DB_obj();

	public server_obj(ServerSocket ss) throws IOException{
		server_socket = ss;
		System.out.println(server_socket + "is made");	
	}

	public void receive_new_client_connection(){
		try {
			System.out.println("watting new client..");
			soc = server_socket.accept(); //�� ���� ���� ���
			System.out.println("client is admitted");
			listen = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())));
			show_connection_info(soc);

			System.out.println("first str from client : "+listen.readLine());

			while (soc !=null){		// �̵� ������ ip�ּҰ� �ٲ�� ��� (�Ͻ������� ���ͳ��� ����� ��� //�� listen, send ���� �Լ�ȭ �ϰ� catch �κп��� �ش� �Լ� ��� �ٽ� �õ��ϵ��� ����
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


	public void run(){	//���� ��� �Լ�, Ŭ�󸮾�Ʈ ���� ����ϰ�, ���� ���� ������� ������û ���� 
		receive_new_client_connection();
	}

	public void call_func(String _client_request) throws NumberFormatException, IOException{
		if (_client_request.equals("send_one_info_from_id_n")){
			send_info_from_id_n();
		}else if (_client_request.equals("send_all_info")){
			send_all_info_with_size();
		}

		/*switch(_client_request){
		case command.send_one_info_from_id_n:
			send_info_from_id_n();
			break;
		case command.send_all_info:
			send_all_info_with_size();
			break;		
		default:
			break;
		}*/
	}

	public void show_connection_info(Socket _soc){
		System.out.println("server ���� : "+_soc.getLocalSocketAddress());
		System.out.println("client ���� : "+_soc.getRemoteSocketAddress());
		System.out.println("server socket ���� : " + server_socket.toString());
	}
	public void connect_db(){
		db.connect();
	}

	public void send_info_from_id_n() throws NumberFormatException, IOException{
		System.out.println("start send_info_from_id_n()");
		int _id_n=Integer.parseInt(listen.readLine());
		int[] arr = db.search_info_from_id_n(_id_n);
		send.println(arr[0]);
		send.println(arr[1]);
		send.println(arr[2]);
		send.println(arr[3]);
		send.flush();
		System.out.println("end send info from id_n, " + Arrays.toString(arr));
	}

	public void send_info_from_id_n(int _id_n){
		int[] arr = db.search_info_from_id_n(_id_n);
		send.println(arr[0]);
		send.println(arr[1]);
		send.println(arr[2]);
		send.println(arr[3]);
		send.flush();
	}

	public void send_all_info_with_size(){
		Vector<int[]> _vec = db.send_all_info();
		int _size = _vec.size();
		System.out.println("total info size : "+_size);

		send.println(_size);
		send.flush();
		for (int i=0; i<_size ;i++){
			send.println(_vec.elementAt(i)[0]);
			send.println(_vec.elementAt(i)[1]);
			send.println(_vec.elementAt(i)[2]);
			send.println(_vec.elementAt(i)[3]);
			send.flush();			
		}
	}


}//end class
