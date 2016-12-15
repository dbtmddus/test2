package client_swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

	static int num_of_friend;
	static Vector<friend_info> f_info;
	/*
	static String[] fid_list;
	static String[] fimg_list;
	static String[] fm_list;
	 */

	private JTextField ID_textfield;
	private Panel[] panel = new Panel[100];

	private JScrollPane spanel;
	private JPanel big_panel;

	static Vector<JPanel> arr_jpanel = new Vector<JPanel>(0); 

	public friend_panel(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _id_n, JFrame m_frame ) throws IOException{		

		/***************************************************************///
		connected_socket = _connected_socket;		
		listen = _listen;
		send = _send;
		id_n = _id_n;

		num_of_friend=0;
		f_info = new Vector<friend_info>(0);

		/***************************************************************///

		/***************************************************************///swing
		spanel = new JScrollPane();
		//spanel.setViewportView(big_panel);
		spanel.setBounds(m_frame.getContentPane().getBounds());

		big_panel = new JPanel();
		big_panel.setLayout(new GridLayout(5, 2));
		big_panel.setBounds(m_frame.getContentPane().getBounds());

		set_friend_db();

		for (int i=0; i<num_of_friend; i++){
			String f_id_temp= f_info.elementAt(i).id;
			JPanel each_panel = new JPanel();
			each_panel = one_friend_panel(f_id_temp);
			each_panel.setName(f_id_temp);
			big_panel.add(each_panel);
			//	each_panel.addMouseListener(ml);
		}

		spanel.setViewportView(big_panel);	// 이것도 앞에 넣으면  반영전 panel값이 출력되는듯
		big_panel.setVisible(true);
		spanel.setVisible(true);
	}//end creator

	public void set_friend_db() throws IOException{ 
		final String request_friend_list = "request_friend_list";
		send.println(request_friend_list);
		send.flush();

		int num_of_friend = Integer.parseInt(listen.readLine());

		friend_info[] f_info = new friend_info[num_of_friend];
		for (int i=0; i<num_of_friend; i++){
			f_info[i] = new friend_info();
		}

		for(int i=0; i<num_of_friend; i++){
			f_info[i].id = listen.readLine();
			f_info[i].id_n = Integer.parseInt(listen.readLine());
			/*FileOutputStream out = new FileOutputStream(f_info[i].image);
			String input_str="";
			while ( !(input_str=listen.readLine()).equals("end") ){
				Byte byte_data = Byte.parseByte(input_str);
				out.write(byte_data);	
			}out.close();*/
			f_info[i].stmt_msg = listen.readLine();		
		}
		
		System.out.println("size:"+num_of_friend);
		for (int i=0; i<num_of_friend ; i++){
			System.out.println(f_info[i].id+"/"+f_info[i].id_n+"/"+f_info[i].image+"/"+f_info[i].stmt_msg);
		}
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
			String clicked_friend_id = e.getComponent().getParent().getName();
			send.println("request_friend_ip");
			send.flush();
			send.println(clicked_friend_id);
			send.flush();

			try {
				System.out.println("friend ip : "+ listen.readLine());
				System.out.println("friend port : "+ listen.readLine());
				if (!main_frame.already_exist_chat_v.contains(clicked_friend_id)){
					chat_frame chat_f = new chat_frame(connected_socket, listen, send, id_n, 0);
					main_frame.already_exist_chat_v.addElement(clicked_friend_id);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			String clicked_friend_id = e.getComponent().getParent().getName();
			send.println("request_friend_ip");
			send.flush();
			send.println(clicked_friend_id);
			send.flush();

			try {
				System.out.println("friend ip : "+ listen.readLine());
				System.out.println("friend port : "+ listen.readLine());
				if (!main_frame.already_exist_chat_v.contains(clicked_friend_id)){
					chat_frame chat_f = new chat_frame(connected_socket, listen, send, id_n, 2);
					main_frame.already_exist_chat_v.addElement(clicked_friend_id);
				}
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

	class friend_info{

		public String id;
		public int id_n;
		public String stmt_msg;
		public File image;

		public friend_info(){
		}
		public friend_info(String _id, int _id_n, String _stmt_msg, File _image){
			id = _id;
			id_n = _id_n;
			stmt_msg = _stmt_msg;
			image = _image;
		}

	}
}//end

