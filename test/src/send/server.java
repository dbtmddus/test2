package send;
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
	public static void main (String [] args ) throws IOException, InterruptedException {

		ServerSocket servsock = new ServerSocket(13267);
		Socket sock = null;
		while (true) {
			System.out.println("Waiting...");
			sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);

			for (int i=1; i<5; i++){
				File myFile = new File ("C:\\messanger_image\\qkf"+i+".png");
				send_file(myFile, sock);
			}

			sock.close();
		}
	}

	public static void send_file(File _file, Socket _sock) throws IOException{		
		_sock.getInputStream().read();	//이 부분 없으면, 서버가 대기하지 않고 클라이언트가 정보를 하나씩 받아오기 전에 파일 전부 보내버리는데, 그 후 클라이언트가 값을 가져와도 비어있게 되어 값 입력을 대기하는 현상 존재.

		//send len
		int _file_len = (int)_file.length();
		PrintWriter send2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(_sock.getOutputStream())));	//
		//BufferedWriter send = new BufferedWriter(new OutputStreamWriter(_sock.getOutputStream()));	//bufferedwriter로 그대로 사용 가능. 수행시간은 큰 차이 없음, 이걸로 사용시 개행 고려할것
		send2.println(_file_len);
		send2.flush();
		//send.write(Integer.toString((int)_file.length())+"\n");
		//send.newLine();
		//send.flush();

		//file data load
		if(_file_len!=0){
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(_file));
			byte[] mybytearray  = new byte[(int)_file.length()];
			bis.read(mybytearray,0,mybytearray.length);

			//data transmit
			OutputStream os = _sock.getOutputStream();
			os.write(mybytearray,0,mybytearray.length);
			os.flush();

			System.out.println("server send " + _file.getName() + "(" + _file.length() + " bytes)");

			//close
			bis.close();
		}
	}
}
