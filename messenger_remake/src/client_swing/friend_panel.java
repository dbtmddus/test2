package client_swing;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class friend_panel extends Panel{

	private Socket connected_socket;
	private PrintWriter send ;		//추가
	private BufferedReader listen;
	private int id_n;

	private JTextField ID_textfield;
	private Panel[] panel = new Panel[100];

	static int f_list_size;
	static String[] f_list;

	static Vector<JPanel> arr_jpanel = new Vector<JPanel>(0); 

	public friend_panel(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _id_n ) throws IOException{		
		super();
		connected_socket = _connected_socket;		
		listen = _listen;
		send = _send;
		id_n = _id_n;

		//swing
		/*
		//setLayout(null);
		setLayout(new GridLayout(100, 1, 3, 2));
		setVisible(true);
		setSize(460, 226);
		 */
		JScrollPane sp = new JScrollPane();
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(5, 2));
		p.add(new JButton("첫번째"));
		p.add(new JButton("두번째"));      
		p.add(new JButton("세번째"));
		p.add(new JButton("네번째"));

		p.setBounds(f.getContentPane().getBounds());
		sp.setViewportView(p);

		sp.setBounds(f.getContentPane().getBounds());

		set_friend_db();

		for (int i=0; i<f_list_size; i++){
			make_one_f_room();
		}
	}//end creator

	public void make_one_f_room(){
		JPanel panel = new JPanel();
		add(panel);
		arr_jpanel.addElement(panel);	
		JButton b = new JButton("dd");
		panel.add(b);
	}


	public void set_friend_db() throws IOException{
		final String request_friend_list = "request_friend_list";
		send.println(request_friend_list);
		send.flush();

		String str_f_list = listen.readLine();
		str_f_list = str_f_list.substring(1, str_f_list.length()-2);
		String[] f_list_and_size = str_f_list.split(", ");
		f_list_size = Integer.parseInt(f_list_and_size[0]);

		f_list = new String[f_list_size];
		for (int i=0; i<f_list_size; i++){
			f_list[i] = f_list_and_size[i+1];
		}	
		System.out.println("size:"+f_list_size);
		System.out.println(f_list.toString());
	}


	ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();
			/*if (obj==login_button){

			}
			else if (obj==signin_button){	
			}*/
		}
	};

}//end

