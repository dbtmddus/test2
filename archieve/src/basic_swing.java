

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import client_swing.main_frame;

public class basic_swing extends ScrollPane{

	private Socket connected_socket;
	private PrintWriter send ;		//추가
	private BufferedReader listen;

	private JTextField ID_textfield;

	public basic_swing(Socket _connected_socket, BufferedReader _in, PrintWriter _out ) throws IOException{		super("로그인");
		connected_socket = _connected_socket;		
		listen = _in;
		send = _out;

		//swing
		setLayout(null);
		setVisible(true);
		setSize(460, 226);

		ID_textfield = new JTextField();
		ID_textfield.setBounds(123, 36, 202, 22);
		
		add(ID_textfield);
	}//end creator

	ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();

			if (obj==login_button){
				
			}
			else if (obj==signin_button){
				
			}
		}
	};

}//end

