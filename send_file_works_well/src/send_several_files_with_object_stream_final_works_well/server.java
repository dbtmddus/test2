package send_several_files_with_object_stream_final_works_well;
import java.io.BufferedOutputStream;
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
		
			sock.close();
		}
	}

	public static void send_file(File _file, Socket _sock) throws IOException{		
		_sock.getInputStream().read();	//이 부분 없으면, 서버가 대기하지 않고 클라이언트가 정보를 하나씩 받아오기 전에 파일 전부 보내버리는데, 그 후 클라이언트가 값을 가져와도 비어있게 되어 값 입력을 대기하는 현상 존재.
		//ObjectOutputStream toClient = new ObjectOutputStream(_sock.getOutputStream());
		ObjectOutputStream toClient = new ObjectOutputStream(new BufferedOutputStream(_sock.getOutputStream()));	
		
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
}
