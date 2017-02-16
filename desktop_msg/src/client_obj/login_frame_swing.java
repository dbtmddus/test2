package client_obj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client_swing.JFrame_Main;
import common_use.command;

public class login_frame_swing extends JFrame{

	private Socket connected_socket;
	private PrintWriter send ;		//�߰�
	private BufferedReader listen;
	
	private String id;
	private String password;

	private Socket socket_for_chat;
	private PrintWriter send_for_chat ;		//�߰�
	private BufferedReader listen_for_chat;
	
	
	private JLabel Password_label; 
	private JLabel Id_label;
	private JTextField ID_textfield;
	private JTextField password_textfield;
	private JButton login_button;
	private JButton sign_up_button;

	public login_frame_swing(Socket _connected_socket, Socket _socket_for_chat ) throws IOException
	{
		super("�α���");
		connected_socket = _connected_socket;		
		listen = new BufferedReader(new InputStreamReader(connected_socket.getInputStream()));
		send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream())));
		
		socket_for_chat = _socket_for_chat;
		//PrintWriter out_chat = send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket_for_chat.getOutputStream()))); //�߰�
		
		//swing configuration
		getContentPane().setLayout(null);
		//setVisible(true);		//���� - �ٸ� ������Ʈ ���� ���� ���� ȣ�� �� ó���� �� �߰� repaint�� �ڴʰ� ����� ǥ�õ� �� ����
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

		sign_up_button = new JButton("sign in");
		sign_up_button.setBounds(324, 135, 87, 32);
		getContentPane().add(sign_up_button);
		sign_up_button.addActionListener(action);

		ID_textfield.setText("dbtmddus112");
		password_textfield.setText("1234");

		setVisible(true);
		//		getContentPane().repaint();
	}//end creator

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj==login_button){
				f_login_button();
			}
			else if (obj==sign_up_button){
				f_sign_up();
			}
		}
	};

	public void f_login_button(){
		send.println(command.login);
		send.flush();
		id = ID_textfield.getText();
		password = password_textfield.getText();
		send.println(id);
		send.println(password);
		send.flush();

		try {
			boolean b_approved = false;
			b_approved = Boolean.parseBoolean(listen.readLine());
			System.out.println(b_approved);
			if (b_approved){	//when login is approved
				int id_n;
				id_n = Integer.parseInt(listen.readLine());
				f_open_main_frame(connected_socket, listen, send, id_n );
				f_create_chat_socket();
				setVisible(false);
			}else{
				JOptionPane.showMessageDialog(null, "��ġ�ϴ� ������ �����ϴ�.", "�α���", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void f_sign_up(){
		send.println(command.sign_up);
		send.flush();
		id = ID_textfield.getText();
		password = password_textfield.getText();
		send.println(id);
		send.println(password);
		send.flush();
	}
	
	public void run(){
//		main_frame mf = new main_frame(_connected_socket, _listen, _send, _id_n );				
	}
	public void f_open_main_frame(Socket _connected_socket, BufferedReader _listen, PrintWriter _send, int _id_n ) throws ClassNotFoundException, IOException{
		JFrame_Main mf = new JFrame_Main(_connected_socket, _id_n);
		mf.start();
	}
	public void f_create_chat_socket(){
		chatting ch = new chatting(socket_for_chat, id);		
		Thread thread1=new Thread(ch);
		thread1.start();
	}
	
}//end