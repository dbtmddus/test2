package messanger_source;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int n=0; 
		ServerSocketChannel socket_chs = ServerSocketChannel.open();
		socket_chs.socket().bind(new InetSocketAddress(1245));
		socket_chs.configureBlocking(false);
		
		while (true){
			SocketChannel socket_ch = socket_chs.accept();
			
			if (socket_ch !=null){
				System.out.println("channel "+ socket_ch +"is connected");
				System.out.println("remote add: "+socket_ch.getRemoteAddress());
				//socket_ch.keyFor(null)
				System.out.println("server listen socket : "+socket_ch.socket() );
				BufferedReader br = new BufferedReader(new InputStreamReader(socket_ch.socket().getInputStream()));
				String str_from_client="";
				while(str_from_client != ".exit\n"){
					str_from_client = br.readLine();		//입력될때까지 여기서  대기
					System.out.println(str_from_client);
					//in.reset();
					br = new BufferedReader(new InputStreamReader(socket_ch.socket().getInputStream()));
				}
			}
		}
	}
}
