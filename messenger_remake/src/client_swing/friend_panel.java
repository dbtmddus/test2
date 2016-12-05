package client_swing;

import java.awt.Panel;
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

public class friend_panel extends Panel{

	private Socket connected_socket;
	private PrintWriter send ;		//�߰�
	private BufferedReader listen;

	private JTextField ID_textfield;
	private Panel[] panel = new Panel[100];
	
	public friend_panel(Socket _connected_socket, BufferedReader _listen, PrintWriter _send ){		
		super();
		connected_socket = _connected_socket;		
		listen = _listen;
		send = _send;

		//swing
		setLayout(null);
		setVisible(true);
		setSize(460, 226);

		ID_textfield = new JTextField();
		ID_textfield.setBounds(123, 36, 202, 22);
		ID_textfield.setText("textfield1");
		
		add(ID_textfield);
	}//end creator

	ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();
			/*if (obj==login_button){
				
			}
			else if (obj==signin_button){	
			}*/
		}
	};

	public void get_friend_db(){
		send.println("request_friend_list");
		send.flush();
	}
	
}//end
