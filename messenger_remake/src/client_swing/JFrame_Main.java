package client_swing;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class JFrame_Main extends Thread{

	private JFrame main_frame;
	private JPanel contentPane;
	private static JScrollPanel_friends_in_main fp;

	private JMenuBar menuBar;
	private JButton b_friend;
	private JButton b_chat_record_list;
	private JButton b_configuration;
	private JButton b_add_friend;

	private Panel p_main;

	private Socket connected_socket;
	private BufferedReader listen;
	private PrintWriter send;
	private int id_n;

	public static Vector<String> already_exist_chat_v = new Vector<String>(0);	//����ڰ� ����� ä��â ��� //��� thread�� �ƴ϶� static��� ����
	public static Vector<JFrame_Main> chat_v = new Vector<JFrame_Main>(0);
	
	public JFrame_Main(Socket _connected_socket, int _id_n ) throws IOException, ClassNotFoundException {
		super(Integer.toString(_id_n));
		
		/*********************************************************///
		connected_socket = _connected_socket;
		listen = new BufferedReader(new InputStreamReader(connected_socket.getInputStream()));
		send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream())));
		
		id_n = _id_n;
		/*********************************************************///
		set_chat_v();
	}
	public void run(){	//?? �̰� �� run���� ��������...
		/*********************************************************///
		main_frame = new JFrame(Integer.toString(id_n));
		//fp = new friend_panel(_connected_socket, _listen, _send, _id_n, this);
		menuBar = new JMenuBar();
		main_frame.setJMenuBar(menuBar);

		b_friend = new JButton("ģ�� ���");
		b_friend.addActionListener(action);
		menuBar.add(b_friend);

		b_chat_record_list = new JButton("ä�� ���");
		b_chat_record_list.addActionListener(action);
		menuBar.add(b_chat_record_list);

		b_configuration = new JButton("����");
		b_configuration.addActionListener(action);
		menuBar.add(b_configuration);

		b_add_friend = new JButton("ģ�� �߰�");
		b_add_friend.addActionListener(action);
		menuBar.add(b_add_friend);

		/*********************************************************///

		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setBounds(100, 100, 395, 650);

		//getContentPane().setLayout(null);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		main_frame.setContentPane(contentPane);

		p_main = new Panel();
		contentPane.add(p_main, BorderLayout.CENTER);

		JButton b10 = new JButton("ddddd");
		p_main.add(b10);

		main_frame.setVisible(true);
	}

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if(obj==b_friend){
				contentPane.removeAll();
				try {
					fp = new JScrollPanel_friends_in_main(connected_socket, id_n, main_frame);	// ģ�� ��� ������ �ð� �ɸ�, ���ΰ�ħ Ű �߰� �� �� �ٸ� ���ָ� ��
					contentPane.add(fp.get_spanel());
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
				catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				main_frame.revalidate();
				main_frame.repaint();		//2�� ���� ���� ����
			}
			else if(obj==b_chat_record_list){
				contentPane.removeAll();
				JScrollPanel_chat_list_in_main cr = null;
				try {
					cr = new JScrollPanel_chat_list_in_main(connected_socket, id_n, main_frame);	// ģ�� ��� ������ �ð� �ɸ�, ���ΰ�ħ Ű �߰� �� �� �ٸ� ���ָ� ��
					contentPane.add(cr.get_spanel());
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
				catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				main_frame.revalidate();
				main_frame.repaint();		//2�� ���� ���� ����
		
			}
			else if(obj==b_add_friend){
				try {
					JFrame_add_friend frame_add_friends = new JFrame_add_friend(connected_socket, id_n);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};
	public void set_chat_v(){
		
	}
}//end