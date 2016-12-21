package send_several_files_with_byte_array_final_works_well;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class client {

	public final static int SOCKET_PORT = 13267;      // you may change this
	public final static String SERVER = "127.0.0.1";  // localhost
	public final static int FILE_SIZE = 602238600; // should bigger than the file to be downloaded
	
	public static void main (String [] args ) throws IOException, InterruptedException {
		Socket sock = new Socket(SERVER, SOCKET_PORT);
		System.out.println("Connecting...");

		long a=System.currentTimeMillis();
		
		for (int i=1; i<5; i++){
			String r_file_name = "qkf_downloaded"+i+".png";  // you may change this, I give a
			File r_file = new File(r_file_name);
			receive_file(r_file, sock);
		}

		long b=System.currentTimeMillis();
		System.out.println(b-a);
		
		sock.close();
	}//end main

	public static void receive_file(File _file, Socket _sock) throws IOException{
		_sock.getOutputStream().write(1);
		_sock.getOutputStream().flush();			
		
		//get file lengh
		BufferedReader listen = new BufferedReader(new InputStreamReader(_sock.getInputStream()));
		int file_len = Integer.parseInt(listen.readLine());
		
		//measure byte size
		byte [] mybytearray  = new byte [FILE_SIZE];
		InputStream is = _sock.getInputStream();
		int bytesRead =0;
		int current = 0;
		while( (bytesRead=is.read(mybytearray, current, file_len-current))  > 0){		//답변 달리면 수정 필요(bos.write(mybytearray, 0 , 165982);)
			current += bytesRead;	// read하면서 실제 파일 길이 측정, 값 저장
			System.out.println("read: "+bytesRead +" / total: " + current);
			if (current == file_len){
				break;
			}
		}
		
		//byte to file
		FileOutputStream fos = new FileOutputStream(_file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(mybytearray, 0 , current);		//실제 길이만큼 write
		//bos.write(mybytearray, 0 , 165982);
		bos.flush();
		System.out.println("File " + _file.getName() + " downloaded (" + current + " bytes read)");

		//close
		fos.close();
		bos.close();
	}

}//end