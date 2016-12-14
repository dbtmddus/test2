package client_swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class friend_panel {

	private Socket connected_socket;
	private PrintWriter send ;		//추가
	private BufferedReader listen;
	private int id_n;

	private JTextField ID_textfield;
	private Panel[] panel = new Panel[100];

	static int f_list_size;
	static String[] fid_list;
	static String[] fimg_list;
	static String[] fm_list;
	
	private JScrollPane spanel;
	private JPanel big_panel;

	static Vector<JPanel> arr_jpanel = new Vector<JPanel>(0); 

	public friend_panel(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, 
			int _id_n, JFrame frame ) throws IOException{		
		connected_socket = _connected_socket;		
		listen = _listen;
		send = _send;
		id_n = _id_n;

		//swing
		spanel = new JScrollPane();

		big_panel = new JPanel();
		big_panel.setLayout(new GridLayout(5, 2));

		set_friend_db();

		for (int i=0; i<f_list_size; i++){
			JPanel each_panel = new JPanel();
			each_panel = one_friend_panel(fid_list[i]);
			each_panel.setName(fid_list[i]);
			big_panel.add(each_panel);
			//	each_panel.addMouseListener(ml);
		}

		//p.add(new JButton("첫번째"));

		big_panel.setBounds(frame.getContentPane().getBounds());
		spanel.setViewportView(big_panel);

		spanel.setBounds(frame.getContentPane().getBounds());

		big_panel.setVisible(true);
		spanel.setVisible(true);
	}//end creator

	public void set_friend_db() throws IOException{
		final String request_friend_list = "request_friend_list";
		send.println(request_friend_list);
		send.flush();

		String str_f_list = listen.readLine();
		str_f_list = str_f_list.substring(1, str_f_list.length()-1);
		String[] f_list_and_size = str_f_list.split(", ");
		try{
			f_list_size = Integer.parseInt(f_list_and_size[0]);
		}catch (NumberFormatException e){
			System.out.println("친구 0명");
			f_list_size=0;
		}
		fid_list = new String[f_list_size];
		for (int i=0; i<f_list_size; i++){
			fid_list[i] = f_list_and_size[i+1];
		}	
		/*System.out.println("size:"+f_list_size);
		for (int i=0; i<f_list_size ; i++){
			System.out.print(fid_list[i]+ ", ");
		}System.out.println();*/
	}

	public JScrollPane get_spanel(){
		return spanel;
	}

	public JPanel one_friend_panel(String f_id){

		JPanel o_panel = new JPanel();
		o_panel.setLayout(new BorderLayout());
		o_panel.setBounds(0,0, 500,500);
		o_panel.addMouseListener(ml);

		JButton b1 = new JButton("profile image isn't set");
		o_panel.add(b1, BorderLayout.WEST);
		b1.addMouseListener(ml);

		JPanel right_panel = new JPanel();
		right_panel.setName(f_id);
		right_panel.setLayout(new GridLayout(2, 1));
		o_panel.add(right_panel, BorderLayout.CENTER);

		////////////////
		JButton b2 = new JButton(f_id);
		right_panel.add(b2);
		b2.addMouseListener(ml);

		JButton b3 = new JButton("statement message isn't set");
		right_panel.add(b3);
		b3.addMouseListener(ml);

		o_panel.setVisible(true);
		return o_panel;
	}

	ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			/*if (obj==p){
				//.getComponents() login_button){
			}

			else if (obj==signin_button){	
			}*/
		}
	};

	MouseListener ml = new MouseListener(){
		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			send.println("request_friend_ip");
			send.flush();
			send.println(e.getComponent().getParent().getName());
			send.flush();

			try {
				System.out.println("friend ip : "+ listen.readLine());
				System.out.println("friend port : "+ listen.readLine());
				chat_frame chat_f = new chat_frame(connected_socket, listen, send, id_n);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			send.println("request_friend_ip");
			send.flush();
			send.println(e.getComponent().getParent().getName());
			send.flush();

			try {
				System.out.println("friend ip : "+ listen.readLine());
				System.out.println("friend port : "+ listen.readLine());
				chat_frame chat_f = new chat_frame(connected_socket, listen, send, id_n);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	};
}//end

