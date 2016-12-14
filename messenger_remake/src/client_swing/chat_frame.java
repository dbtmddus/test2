package client_swing;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class chat_frame extends JFrame {

	private static friend_panel fp;

	private JPanel contentPane;

	private JMenuBar menuBar;
	private JButton b_send_file;
	private JButton b_configuration;

	private Panel chat_recode_p;

	private Socket connected_socket;
	private BufferedReader listen;
	private PrintWriter send;
	private int id_n;

	public chat_frame(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _id_n ) throws IOException {

		connected_socket = _connected_socket;
		listen = _listen;
		send = _send;
		id_n = _id_n;

		fp = new friend_panel(_connected_socket, _listen, _send, _id_n, this);
		//
			
		/***********************************************************************/
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		b_send_file = new JButton("파일 전송");
		b_send_file.addActionListener(action);
		menuBar.add(b_send_file);

		b_configuration = new JButton("설정");
		b_configuration.addActionListener(action);
		menuBar.add(b_configuration);
		/***********************************************************************/

		//getContentPane().setLayout(null);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 638);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		/****************************************************************/// 대화 기록 판넬
		chat_recode_p = new Panel();
		chat_recode_p.setLayout(null);
		
		JTextPane first_tp = new JTextPane();
		first_tp.setBounds(chat_recode_p.getBounds().x-90, 10, 180, 25);
		first_tp.setText("대화가 시작되었습니다.");
		chat_recode_p.add(first_tp);
		
		JScrollPane chat_recode_sp = new JScrollPane();
		chat_recode_sp.setViewportView(chat_recode_p);
		chat_recode_sp.setBounds(getContentPane().getBounds());
		contentPane.add(chat_recode_sp, BorderLayout.CENTER);
		/****************************************************************///
		
		/****************************************************************/// 메세지 전송 판넬
		JPanel writting_p = new JPanel();
		contentPane.add(writting_p, BorderLayout.SOUTH);
		writting_p.setLayout(new BorderLayout());
		
		JTextArea ta = new JTextArea();
		ta.setText("dddddddddddd");
		writting_p.add(ta, BorderLayout.CENTER);
		
		JButton b_send = new JButton("전송");
		writting_p.add(b_send, BorderLayout.EAST);
		/****************************************************************/
		
		setVisible(true);
	}

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj==b_send_file){
				//p_friend.setLayout(new BorderLayout(0, 0));
				contentPane.removeAll();
				contentPane.add(fp.get_spanel());
				/*try {
					fp.set_friend_db();
				} catch (IOException e1) {
					e1.printStackTrace();
				}*/
			}
			else if (obj==b_configuration){
				add_friend_frame frame_add_friends = new add_friend_frame(connected_socket, listen, send, id_n);
			}
		}
	};

}

