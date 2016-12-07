package client_swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import java.awt.Panel;
import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class main_frame extends JFrame {

	private static friend_panel p_friend;

	private JPanel contentPane;

	private JMenuBar menuBar;
	private JButton b_friend;
	private JButton b_chat;
	private JButton b_configuration;
	private JButton b_add_friend;

	private Panel p_main;

	private Socket connected_socket;
	private BufferedReader listen;
	private PrintWriter send;
	private int id_n;

	public main_frame(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _id_n ) throws IOException {

		connected_socket = _connected_socket;
		listen = _listen;
		send = _send;
		id_n = _id_n;

		p_friend = new friend_panel(_connected_socket, _listen, _send, _id_n);
		//
		getContentPane().setLayout(null);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 650);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		b_friend = new JButton("친구 목록");
		b_friend.addActionListener(action);
		menuBar.add(b_friend);

		b_chat = new JButton("채팅 목록");
		b_chat.addActionListener(action);
		menuBar.add(b_chat);

		b_configuration = new JButton("설정");
		b_configuration.addActionListener(action);
		menuBar.add(b_configuration);

		b_add_friend = new JButton("친구 추가");
		b_add_friend.addActionListener(action);
		menuBar.add(b_add_friend);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		p_main = new Panel();
		contentPane.add(p_main, BorderLayout.CENTER);

		JButton b10 = new JButton("ddddd");
		p_main.add(b10);
	}

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj==b_friend){
				p_friend.setLayout(new BorderLayout(0, 0));
				contentPane.removeAll();
				contentPane.add(p_friend, BorderLayout.CENTER);
				try {
					p_friend.set_friend_db();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			else if (obj==b_add_friend){
				add_friend_frame frame_add_friends = new add_friend_frame(connected_socket, listen, send, id_n);
			}
		}
	};

}

