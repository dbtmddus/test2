package simple_server;

import java.io.IOException;

public class client_main {

	public static void main(String[] args) throws IOException {
		
		client_obj_thread c = new client_obj_thread("172.30.96.99", 1245);
		
		c.start();
		//client_obj c = new client_obj("192.168.123.138", 1245);
		//c.enter();		
	}
}
