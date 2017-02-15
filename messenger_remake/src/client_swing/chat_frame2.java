package client_swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import common_use.command;

public class chat_frame2 extends JFrame {

	private Socket connected_socket;
	private BufferedReader listen;
	private PrintWriter send;
	private int id_n;
	private int f_id_n;

	private JPanel contentPane;

	private JMenuBar menuBar;
	private JButton b_send_file;
	private JButton b_configuration;

	///	private JScrollPane chat_recode_sp;
	private Panel chat_recode_p;

	private JPanel writting_p;	//
	private JTextArea ta;
	private JButton b_send;

	private JScrollPane chat_set_sp;
	private JPanel chat_set_p;

	public chat_frame2(Socket _connected_socket,int _id_n, int _f_id_n ) throws IOException, ClassNotFoundException {

		connected_socket = _connected_socket;
		listen = new BufferedReader(new InputStreamReader(connected_socket.getInputStream()));
		send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream())));
		id_n = _id_n;
		f_id_n = _f_id_n;

		//swing configuration	
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		b_send_file = new JButton("파일 전송");
		b_send_file.addActionListener(action);
		menuBar.add(b_send_file);

		b_configuration = new JButton("설정");
		b_configuration.addActionListener(action);
		menuBar.add(b_configuration);
		/***********************************************************************/
		
		setTitle(Integer.toString(id_n)+" to "+Integer.toString(f_id_n));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 638);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		/****************************************************************/// 대화 기록 판넬
		chat_set_sp = new JScrollPane();
		contentPane.add(chat_set_sp);    

		chat_set_p = new JPanel();   
		chat_set_sp.setViewportView(chat_set_p);
		chat_set_p.setLayout(new BoxLayout(chat_set_p, BoxLayout.Y_AXIS));

		add_chat_record("111111111",false);
		add_chat_record("111111111222",true);
		add_chat_record("111111111222",true);
		add_chat_record("111111111222",true);
		//

		contentPane.add(chat_set_sp, BorderLayout.CENTER);
		/****************************************************************///

		/****************************************************************/// 메세지 전송 판넬

		writting_p = new JPanel();
		contentPane.add(writting_p, BorderLayout.SOUTH);
		writting_p.setLayout(new BorderLayout());

		ta = new JTextArea();
		ta.setText("dddddddddddd");
		writting_p.add(ta, BorderLayout.CENTER);

		b_send = new JButton("전송");
		writting_p.add(b_send, BorderLayout.EAST);
		b_send.addActionListener(action);

		/****************************************************************/
		setVisible(true);
	}

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj==b_send_file){
				/*try {
					fp.set_friend_db();
				} catch (IOException e1) {
					e1.printStackTrace();
				}*/
			}
			else if (obj==b_configuration){
				try {
					add_friend_frame frame_add_friends = new add_friend_frame(connected_socket,  id_n);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (obj==b_send){
				String text = ta.getText();
				send_message(text);
				ta.setText("");
			}
		}
	};
	public void send_message(String _text){
		send.println(command.normal_message);
		send.flush();

		send.println(f_id_n);
		send.println(ta.getText());
		send.flush();
	}
	public void add_chat_record(String str, boolean b_IsMine){
		int len_str;
		int len_w;
		int len_h;
		int font_size_w=10;
		int font_size_h=30;
		//   
		JPanel p1;
		
		if (b_IsMine){
			p1= new JPanel(new FlowLayout(FlowLayout.RIGHT));
		}else{
			p1= new JPanel(new FlowLayout(FlowLayout.LEFT));
		}
		JTextPane tp1 = new JTextPane();
		tp1.setText(str);
		len_str = tp1.getText().length();
		len_w =len_str;
		if (len_w>=20){
			len_w = 20;
		}
		len_h = (len_str/20)+1;
		p1.setMaximumSize(new Dimension(contentPane.getMaximumSize().width, len_h*font_size_h));
		tp1.setPreferredSize(new Dimension(len_w*font_size_w, len_h*font_size_h));
		//tp1.setSize(new Dimension(len_w*font_size_w, len_h*font_size_h));
		p1.add(tp1);

		JTextPane date1 = new JTextPane();
		date1.setText("5:20");
		p1.add(date1);
		chat_set_p.add(p1);

		chat_set_p.revalidate();
		chat_set_p.repaint();	
	}
}

