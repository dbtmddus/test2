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

public class basic_swing extends ScrollPane{

	private Socket connected_socket;
	private PrintWriter send ;		//�߰�
	private BufferedReader listen;

	private JTextField ID_textfield;

	public basic_swing() throws IOException{		
		super();

		setLayout(null);
		setVisible(true);
		setSize(260, 226);

		txtAa.setText("aa");
		txtAa.setColumns(10);

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
	/**
	 * @wbp.nonvisual location=110,84
	 */
	private final JTextField txtAa = new JTextField();

}//end

