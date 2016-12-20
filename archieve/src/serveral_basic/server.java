package serveral_basic;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

	public final static int SOCKET_PORT = 13267;  // you may change this


	public static void main (String [] args ) throws IOException, InterruptedException {

		ServerSocket servsock = null;
		Socket sock = null;


		servsock = new ServerSocket(SOCKET_PORT);
		while (true) {
			System.out.println("Waiting...");
			sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);

			for (int i=1; i<5; i++){
				String FILE_TO_SEND = "C:\\messanger_image\\qkf"+i+".png";  // you may change this
				File myFile = new File (FILE_TO_SEND);
				send_file(myFile, sock);
			}
			while(true){
				//	Thread.sleep(10000000);
			}
			//			sock.close();
		}
	}

	public static void send_file(File _file, Socket _sock) throws IOException{		
		ObjectOutputStream toClient = new ObjectOutputStream(_sock.getOutputStream());
		//DataInputStream fromClient = new DataInputStream(_sock.getInputStream());
		// 리셋하지 않으면 첫번째 전송한 객체의 reference로 계속 전송된다함 (갱신된 data 반영 안됨)
		toClient.reset();
		toClient.writeObject(_file);
		//fromClient.close();
		//toClient.close();
	}
}
