package client_swing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class main {

	static Socket connected_socket = null;
	static PrintWriter send;
	static BufferedReader listen;
	
	public static void main(String[] args) {
		
		main_frame mf = new main_frame(connected_socket, listen, send);
	
	}//end main
}