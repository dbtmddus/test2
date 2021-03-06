package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class swing extends JFrame{

	private JLabel Password_label; 
	private JLabel Id_label;

	private JTextField ID_textfield;
	private JTextField password_textfield;

	private JButton login_button;
	private JButton signin_button;

	//private InetAddress server_address_name;
	private Socket server_soc;
	private PrintWriter out ;		//추가
	
	public login_frame_swing(String _server_address, int _soc_n) throws IOException
	{
		super("로그인");
		getContentPane().setLayout(null);
		setVisible(true);
		setSize(460, 226);

		Id_label = new JLabel("ID :");
		getContentPane().add(Id_label);
		Id_label.setBounds(66, 37, 43, 20); // 가로,세로,탑,레프트위치

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

		//server_address_name = InetAddress.getByName(_server_address);
		//String ip = (String)server_address_name.getHostAddress().substring(0);
		//System.out.println("ip : "+ip);
		//server_soc = new Socket(ip, _soc_n);
		server_soc = new Socket(_server_address, _soc_n);
		System.out.println("client's send socket: " + server_soc);
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(server_soc.getOutputStream()))); //추가
		
	}//end creator

	ActionListener action = new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();

			if (obj==login_button){
				String login_id = ID_textfield.getText();
				String login_password = password_textfield.getText();

				out.println("id"+login_id+", password : "+login_password);
				out.flush();
				
				//login_request();	// 구성 필요
			}
			else if (obj==signin_button){
				System.out.println("회원가입 요청");
				
				// signin_request();
				//System.exit(0);

			}
		}
	};



}//end

