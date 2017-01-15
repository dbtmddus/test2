package client_swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import common_use.command;

public class friend_panel {

	private Socket connected_socket;
	private PrintWriter send ;		//추가
	private BufferedReader listen;
	private int id_n;

	private int num_of_friend;
	private Vector<friend_info> f_info;

	private JScrollPane spanel;
	private JPanel big_panel; 	//each panels add되는 panel
	private static Vector<JPanel> each_panels = new Vector<JPanel>(0); //친구 1명값 표시되는 panel

	public friend_panel(Socket _connected_socket, int _id_n, JFrame m_frame ) throws IOException, ClassNotFoundException{		

		connected_socket = _connected_socket;		
		listen = new BufferedReader(new InputStreamReader(connected_socket.getInputStream()));
		send = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connected_socket.getOutputStream())));
		id_n = _id_n;

		// basic swing configuration
		spanel = new JScrollPane();
		spanel.setBounds(m_frame.getContentPane().getBounds());

		big_panel = new JPanel();
		big_panel.setLayout(new GridLayout(5, 2));
		big_panel.setBounds(m_frame.getContentPane().getBounds());

		// add each panel on big panel after setting friend infomation
		set_f_info();	//핵심 함수 (num_of_friend, f_info 값 설정 등)
		for (int i=0; i<num_of_friend; i++){
			JPanel each_panel = new JPanel();
			each_panel = one_friend_panel(i);
			each_panel.setName(f_info.elementAt(i).id);	//panel 이름은 각 친구들 id로 설정
			big_panel.add(each_panel);
			each_panel.addMouseListener(ml);
		}

		spanel.setViewportView(big_panel);	// 이것도 앞에 넣으면  반영전 panel값이 출력되는듯
		big_panel.setVisible(true);
		spanel.setVisible(true);
	}//end creator

	public void set_f_info() throws IOException, ClassNotFoundException{ 
		send.println(command.friends_detail_info);
		send.flush();

		num_of_friend = Integer.parseInt(listen.readLine());		//0일 때 에러???

		f_info = new Vector<friend_info>(0);
		for(int i=0; i<num_of_friend; i++){
			friend_info f_info_ele = new friend_info();

			f_info_ele.id = listen.readLine();
			System.out.println("id : "+f_info_ele.id);

			f_info_ele.id_n = Integer.parseInt(listen.readLine());
			System.out.println("id_n : "+f_info_ele.id_n);

			f_info_ele.image = receive_file_fast("c_"+f_info_ele.id+".jpg");	// !!!!!!!!!!!!!이후 파일 형식 처리 따로 필요
			System.out.println("file : "+f_info_ele.image.getName());

			f_info_ele.stmt_msg = listen.readLine();
			System.out.println("msg : "+ f_info_ele.stmt_msg);

			f_info.addElement(f_info_ele);
		}

		System.out.println("friend info : (size: "+num_of_friend);
		for (int i=0; i<num_of_friend ; i++){
			System.out.println(f_info.elementAt(i).id+"/"+f_info.elementAt(i).id_n+"/"+f_info.elementAt(i).image.getName()+"/"+f_info.elementAt(i).stmt_msg);
		}
	}

	public File receive_file_with_obj_stream(String file_name) throws IOException, ClassNotFoundException{ //objectstream 버전, 간단하고 잘 동작하나 상당히 많이 느리다.
		connected_socket.getOutputStream().write(1);	//전송 요청 신호 (objectStream과 send의 stream 공용 사용 문제 제어)
		connected_socket.getOutputStream().flush();			
		ObjectInputStream fromServer = new ObjectInputStream(connected_socket.getInputStream());
		File file = (File)fromServer.readObject();
		if (file != null){
			FileInputStream fis = new FileInputStream(file);
			File newFile = new File(file_name);
			FileOutputStream fos = new FileOutputStream(newFile);
			int i = 0;
			while ((i = fis.read()) != -1) {
				fos.write((char) i);
			}
			fos.close();
			fis.close();
			return newFile;
		}else{
			System.out.println("file is not exist");
			return null;
		}
	}
	public File receive_file_fast(String _file_name) throws IOException{
		connected_socket.getOutputStream().write(1);
		connected_socket.getOutputStream().flush();			

		//get file lengh
		int file_len = Integer.parseInt(listen.readLine());

		//measure byte size
		byte [] mybytearray  = new byte [file_len+100];
		InputStream is = connected_socket.getInputStream();
		int bytesRead =0;
		int current = 0;
		while( (bytesRead=is.read(mybytearray, current, file_len-current))  > 0){		//답변 달리면 수정 필요(bos.write(mybytearray, 0 , 165982);)
			current += bytesRead;	// read하면서 실제 파일 길이 측정, 값 저장
			//System.out.println("read: "+bytesRead +" / total: " + current);
			if (current == file_len){
				break;
			}
		}

		//byte to file
		File downloaded_file = new File(_file_name);
		FileOutputStream fos = new FileOutputStream(downloaded_file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(mybytearray, 0 , current);		//실제 길이만큼 write
		bos.flush();
		//System.out.println("File " + _file_name + " downloaded (" + current + " bytes read)");

		//close
		fos.close();
		bos.close();

		return downloaded_file;
	}

	public JScrollPane get_spanel(){
		return spanel;
	}

	public JPanel one_friend_panel(int _i) throws IOException{

		JPanel o_panel = new JPanel();
		o_panel.setLayout(new BorderLayout());
		o_panel.setBounds(0,0, 500,500);
		o_panel.addMouseListener(ml);

		String f_id_temp = f_info.elementAt(_i).id;
		int f_id_n_temp = f_info.elementAt(_i).id_n;
		File f_image_temp = f_info.elementAt(_i).image;
		String f_stmt_msg = f_info.elementAt(_i).stmt_msg;

		JButton b_image = new JButton();
		b_image.setSize(100, 100);
		b_image.setName(f_id_temp);
		if(f_image_temp!=null){
			Image image_temp = ImageIO.read(f_image_temp);
			ImageIcon icon_temp = new ImageIcon(image_temp);
			b_image.setIcon(icon_temp);
		}else{
			b_image.setText("profile image isn't set");
		}
		o_panel.add(b_image, BorderLayout.WEST);
		b_image.addMouseListener(ml);

		////////////////
		JPanel right_panel = new JPanel();
		right_panel.setName(f_id_temp);
		right_panel.setLayout(new GridLayout(2, 1));
		o_panel.add(right_panel, BorderLayout.CENTER);

		JButton b_id = new JButton(f_id_temp);
		b_id.setName(f_id_temp);
		b_id.addMouseListener(ml);
		right_panel.add(b_id);

		JButton b_stmt_msg = new JButton("default");
		if(f_stmt_msg!=null){
			b_stmt_msg.setText(f_stmt_msg);	
		}else{
			b_stmt_msg.setText("stmt_msg isn't set");	
		}
		b_stmt_msg.setName(f_id_temp);
		b_stmt_msg.addMouseListener(ml);
		right_panel.add(b_stmt_msg);

		o_panel.setVisible(true);
		return o_panel;
	}

	public void open_chat_frame(String _clicked_friend_id){
		String f_id = _clicked_friend_id;
		try {
			send.println(command.id_n_from_id);
			send.flush();
			send.println(f_id);
			send.flush();
			int _f_id_n = Integer.parseInt(listen.readLine());

			if (!main_frame.already_exist_chat_v.contains(f_id)){
				chat_frame chat_f = new chat_frame(connected_socket, id_n, _f_id_n);
				main_frame.already_exist_chat_v.addElement(f_id);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
		}
	};

	MouseListener ml = new MouseListener(){
		@Override
		public void mouseReleased(MouseEvent e) {
			String clicked_friend_id = e.getComponent().getName();
			open_chat_frame(clicked_friend_id);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			String clicked_friend_id = e.getComponent().getName();
			open_chat_frame(clicked_friend_id);	
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			String clicked_friend_id = e.getComponent().getParent().getName();
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
			id = null;
			id_n = -1;
			stmt_msg = null;
			image = null;

		}
		public friend_info(String _id, int _id_n, String _stmt_msg, File _image){
			id = _id;
			id_n = _id_n;
			stmt_msg = _stmt_msg;
			image = _image;
		}

	}
}//end

