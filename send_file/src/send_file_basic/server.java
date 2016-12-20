package send_file_basic;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
		//send len
		BufferedWriter send = new BufferedWriter(new OutputStreamWriter(_sock.getOutputStream()));
		PrintWriter send2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(_sock.getOutputStream())));	//
		String str = Integer.toString((int)_file.length());
		System.out.println("str : "+str);
		send.write(Integer.toString((int)_file.length()));
		send.newLine();
		send.flush();
		//send2.println(_file.length());
		System.out.println("server file len : "+ Integer.toString((int)_file.length()));

		//file data load
		FileInputStream fis = new FileInputStream(_file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte [] mybytearray  = new byte [(int)_file.length()];
		bis.read(mybytearray,0,mybytearray.length);
		
		//data transmit
		OutputStream os = _sock.getOutputStream();
		os.write(mybytearray,0,mybytearray.length);
		System.out.println("Sending " + _file.getName() + "(" + mybytearray.length + " bytes)");
		os.flush();
		System.out.println("file tranmit finish");
		
		//close
		fis.close();
		bis.close();
	}
}
