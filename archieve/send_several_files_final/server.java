package send_several_files_final;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.omg.CORBA.portable.OutputStream;

public class server {

	public final static int SOCKET_PORT = 13267;  // you may change this

	static BufferedReader listen;
	static PrintWriter send;

	public static void main (String [] args ) throws IOException, InterruptedException {

		ServerSocket servsock = new ServerSocket(SOCKET_PORT);
		Socket sock = null;

		while (true) {
			System.out.println("Waiting...");
			sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);

			listen = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));


			for (int i=1; i<5; i++){		
				String FILE_TO_SEND = "C:\\messanger_image\\qkf"+i+".png";  // you may change this
				File myFile = new File (FILE_TO_SEND);
				send_file(myFile, sock);
			}
			System.out.println("end all");
			while(true){
				//	Thread.sleep(10000000);
			}
			//			sock.close();
		}
	}

	public static void send_file(File _file, Socket _sock) throws IOException{		
		_sock.getInputStream().read();	//�� �κ� ������, ������ ������� �ʰ� Ŭ���̾�Ʈ�� ������ �ϳ��� �޾ƿ��� ���� ���� ���� ���������µ�, �� �� Ŭ���̾�Ʈ�� ���� �����͵� ����ְ� �Ǿ� �� �Է��� ����ϴ� ���� ����.
		ObjectOutputStream toClient = new ObjectOutputStream(_sock.getOutputStream());
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
}