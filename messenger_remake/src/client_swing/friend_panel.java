package client_swing;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JTextField;

public class friend_panel extends Panel{

	private Socket connected_socket;
	private PrintWriter send ;		//추가
	private BufferedReader listen;

	private JTextField ID_textfield;
	private Panel[] panel = new Panel[100];
	
	public friend_panel(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _n_id ){		
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
		ID_textfield.setText(Integer.toString(_n_id));
		
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

	public void get_friend_db() throws IOException{
		final String request_friend_list = "request_friend_list";
		send.println(request_friend_list);
		send.flush();
		
		String str_f_list = listen.readLine();
		str_f_list = str_f_list.substring(1, str_f_list.length()-2);
		System.out.println("친구 리스트 : "+ str_f_list);
		String[] f_list_and_size = str_f_list.split(", ");
		int f_list_size = Integer.parseInt(f_list_and_size[0]);
		System.out.println("size : " + f_list_and_size);
		for (int i=1; i<f_list_size; i++){
			System.out.println(f_list_and_size[i]+", ");
		}
	}
	
}//end

