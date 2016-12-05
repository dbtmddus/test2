package client_obj;

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

public class login_frame_swing extends JFrame{

	private Socket connected_socket;
	private PrintWriter send ;		//�߰�
	private BufferedReader listen;

	private JLabel Password_label; 
	private JLabel Id_label;
	private JTextField ID_textfield;
	private JTextField password_textfield;
	private JButton login_button;
	private JButton signin_button;

	static String id;
	static String password;
	
	public login_frame_swing(Socket _connected_socket, BufferedReader _listen, PrintWriter _send ) throws IOException
	{
		super("�α���");
		connected_socket = _connected_socket;		
		listen = _listen;
		send = _send;

		//swing
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(460, 226);

		Id_label = new JLabel("ID :");
		getContentPane().add(Id_label);
		Id_label.setBounds(66, 37, 43, 20); // ����,����,ž,����Ʈ��ġ

		ID_textfield = new JTextField();
		ID_textfield.setBounds(123, 36, 202, 22);
		getContentPane().add(ID_textfield);

		Password_label = new JLabel("password :");
		Password_label.setBounds(45, 88, 79, 18);
		getContentPane().add(Password_label);

		password_textfield = new JTextField();
		password_textfield.setBounds(123, 85, 202, 24);
		getContentPane().add(password_textfield);
		password_textfield.setColumns(10);

		login_button = new JButton("login");
		login_button.setBounds(231, 135, 79, 32);
		getContentPane().add(login_button);
		login_button.addActionListener(action);

		signin_button = new JButton("sign in");
		signin_button.setBounds(324, 135, 87, 32);
		getContentPane().add(signin_button);
		signin_button.addActionListener(action);
		
		ID_textfield.setText("dbtmddus112");
		password_textfield.setText("1234");
	}//end creator

	ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();

			if (obj==login_button){
				send.println("login");
				id = ID_textfield.getText();
				password = password_textfield.getText();
				send.println(id);
				send.println(password);
				send.flush();

				String res = null;
				try {
					res = listen.readLine();
					System.out.println(res);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (res.equals("true")){
					System.out.println("??????????????????????");
					main_frame mf = new main_frame(connected_socket, listen, send );
					setVisible(false);
				}
			}
			else if (obj==signin_button){
				send.println("signin");
				id = ID_textfield.getText();
				password = password_textfield.getText();
				send.println(id);
				send.println(password);
				send.flush();
			}
		}
	};

}//end
