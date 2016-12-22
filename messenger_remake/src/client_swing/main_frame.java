package client_swing;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class main_frame extends JFrame {

	private static friend_panel fp;

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
	private JFrame main_frame;

	public static Vector<String> already_exist_chat_v = new Vector<String>(0);	//사용자가 열어둔 채팅창 목록 //얘는 thread가 아니라 static사용 가능

	public main_frame(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _id_n ) throws IOException, ClassNotFoundException {

		/*********************************************************///
		connected_socket = _connected_socket;
		listen = _listen;
		send = _send;
		id_n = _id_n;
		main_frame = this;

		//fp = new friend_panel(_connected_socket, _listen, _send, _id_n, this);
		/*********************************************************///

		/*********************************************************///
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

		/*********************************************************///

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 650);

		//getContentPane().setLayout(null);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		p_main = new Panel();
		contentPane.add(p_main, BorderLayout.CENTER);

		JButton b10 = new JButton("ddddd");
		p_main.add(b10);

		setVisible(true);
	}

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if(obj==b_friend){
				contentPane.removeAll();
				try {
					fp = new friend_panel(connected_socket, listen, send, id_n, main_frame);	// 친구 목록 많을시 시간 걸림, 새로고침 키 추가 시 이 줄만 없애면 됨
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				contentPane.add(fp.get_spanel());
				revalidate();
				repaint();		//2개 같이 쓰기 권장
			}
			else if(obj==b_add_friend){
				add_friend_frame frame_add_friends = new add_friend_frame(connected_socket, listen, send, id_n);
			}
		}
	};
	
}//end
