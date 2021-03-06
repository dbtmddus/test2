package send_several_files_with_object_stream_final_works_well;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class client {

	public final static int SOCKET_PORT = 13267;      // you may change this
	public final static String SERVER = "127.0.0.1";  // localhost

	public final static int FILE_SIZE = 602238600; // file size temporary hard coded
	// should bigger than the file to be downloaded

	public static void main (String [] args ) throws IOException, InterruptedException, ClassNotFoundException {
		Socket sock = null;

		sock = new Socket(SERVER, SOCKET_PORT);
		System.out.println("Connecting...");

		long a=System.currentTimeMillis();
		
		for (int i=1; i<5; i++){
			String file_name = "qkf_downloaded_obj_stream"+i+".png";  // you may change this, I give a
			receive_file(file_name, sock);
		}

		long b=System.currentTimeMillis();
		System.out.println(b-a);
		
		
		sock.close();
	}//end main

	public static void receive_file(String _file_name, Socket _sock) throws IOException, ClassNotFoundException{
		_sock.getOutputStream().write(1);
		_sock.getOutputStream().flush();			
		//ObjectInputStream fromServer = new ObjectInputStream(_sock.getInputStream());
		ObjectInputStream fromServer = new ObjectInputStream(new BufferedInputStream(_sock.getInputStream()));
		File file = (File)fromServer.readObject();
		if (file != null){
			FileInputStream fis = new FileInputStream(file);
			File newFile = new File(_file_name);
			FileOutputStream fos = new FileOutputStream(newFile);
			int i = 0;
			while ((i = fis.read()) != -1) {
				fos.write((char) i);
			}
			fos.close();
			fis.close();
		}else{
			System.out.println("file is not exist");
		}
		System.out.println(_file_name +" is downloaded.");
	}

}//end