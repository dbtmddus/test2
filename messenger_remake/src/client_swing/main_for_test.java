package client_swing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class main_for_test {

	static Socket connected_socket = null;
	static PrintWriter send;
	static BufferedReader listen;
	static JFrame frame;

	public static void main(String[] args) throws IOException {

		/*
		main_frame mf;
		try {
			mf = new main_frame(null, 1);
			mf.start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		*/
		try {
			JFrame_chat2 f = new JFrame_chat2(null, 0, 0);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//end main
}
