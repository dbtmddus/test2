package send.copy;
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
		_sock.getInputStream().read();	//�� �κ� ������, ������ ������� �ʰ� Ŭ���̾�Ʈ�� ������ �ϳ��� �޾ƿ��� ���� ���� ���� ���������µ�, �� �� Ŭ���̾�Ʈ�� ���� �����͵� ����ְ� �Ǿ� �� �Է��� ����ϴ� ���� ����.

		//send len
		int _file_len = (int)_file.length();
		PrintWriter send2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(_sock.getOutputStream())));	//
		//BufferedWriter send = new BufferedWriter(new OutputStreamWriter(_sock.getOutputStream()));	//bufferedwriter�� �״�� ��� ����. ����ð��� ū ���� ����, �̰ɷ� ���� ���� �����Ұ�
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