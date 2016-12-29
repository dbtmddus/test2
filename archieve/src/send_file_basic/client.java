package send_file_basic;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class client {

	public final static int SOCKET_PORT = 13267;      // you may change this
	public final static String SERVER = "127.0.0.1";  // localhost
	public final static String FILE_TO_RECEIVED = "qkf_downloaded.png";  // you may change this, I give a

	public final static int FILE_SIZE = 6022386; // file size temporary hard coded
	// should bigger than the file to be downloaded

	public static void main (String [] args ) throws IOException {
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting...");

			// receive file
			byte [] mybytearray  = new byte [FILE_SIZE];
			InputStream is = sock.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED);
			bos = new BufferedOutputStream(fos);

			//bytesRead = is.read(mybytearray,0,mybytearray.length);	//?? �� ����?? ����ִ��� Ȯ���Ϸ���? ������ �Ʒ��� 0���� �ٽ� �б⶧���� ������ ������ �� �����ϴ���
			//current = bytesRead;		//1����. ������ �о����� ������ ����		//?? �̰͵� �ּ���??
			current = 0;				//2����. ������ ���� �� 2�� ��� ���� ���� �κ�. ��� �ʿ�� ������ �ʱ�ȭ ������.
			while( (bytesRead=is.read(mybytearray, current, (mybytearray.length-current)))  > 0) {	//�ݺ������� �ϴ� ���� - len���� ũ�� �����ϴ���, �����δ� �ѹ��� �� �о���� ����. ���� �ִ� 65536 ����
				current += bytesRead;	// read�ϸ鼭 ���� ���� ���� ����, �� ����
			}
			bos.write(mybytearray, 0 , current);		//���� ���̸�ŭ write
			
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED
					+ " downloaded (" + current + " bytes read)");
		}
		finally {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (sock != null) sock.close();
		}
	}

}