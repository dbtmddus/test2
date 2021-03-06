package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class server {

	public static void main(String[] args) throws IOException {

		ServerSocket server_socket = null;
		Socket soc = null;
		int open_socket_num = 1245;

		while (true){
			System.out.println("server is running");
			server_socket = new ServerSocket(open_socket_num);
			soc = server_socket.accept();//기다리다 연결되면 accept통해 만들어지는 것이 클라이언트 소켓 

			System.out.println("client is admitted");
			System.out.println("server 정보 : "+soc.getLocalSocketAddress());
			System.out.println("client 정보 : "+soc.getRemoteSocketAddress());

			if (soc !=null){
				System.out.println("server socket 정보 : " + server_socket.toString());
				
				while(true){
					BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
					String str_from_client= br.readLine();
					System.out.println("client message : "+str_from_client);
					switch(str_from_client){
						case "login":
							System.out.println("login handling");
							break;
						default:
							break;
					}
				}
			}
		}
	}//end main
}
