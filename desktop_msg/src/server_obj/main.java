/***
 * 12.14
 * ������ ��� ��� ������ Ȯ��
 * �����丵 ������ ����
 * ��� �κ� - chat_frame �κ� �ۼ� ��
 * �Ϲ� �޼��� �ۼ����ϴ� ���� ������ Ŭ�󸮾�Ʈ�� ������ �ٸ� ��� �Ұ���.!!!!!!!!!!!!!!!!!!!!
 * chat_frame�� thread�� �����ϰ�, ���ϵ� ä�ù� �ϳ� �� or ä�ÿ� ������ ���� ������ �־���ҵ�.
 * (chat frame�� ������ �Ѵ� ��� thread�� ��� listen�ϰ� �ִ� ����)
 * 
 */

package server_obj;

import java.io.IOException;
import java.net.ServerSocket;

public class main {

	static ServerSocket server_socket;
	static server_obj server0;
	
	public static void main(String[] args) throws IOException {

		server_socket = new ServerSocket(1245);
		server0 = new server_obj(server_socket);
		server0.init_server();
		server0.db.insert_image(1,"C:\\messanger_image\\qkf2_resize.png");
		server0.db.insert_image(2,"C:\\messanger_image\\qkf_resize.png");
		server0.db.insert_image(3,"C:\\messanger_image\\qkf2_resize.png");
		//server0.connect_db();

		server_obj server1 = new server_obj(server_socket);
		server1.start();

		server_obj server2 = new server_obj(server_socket);
		server2.start();
		
		server_obj server3 = new server_obj(server_socket);
		server3.start();
		
		//show_connected_client();
		//server1.request_friend_list_test();		
	}//end main
	
	static public void show_connected_client(){
		while(true){
			try {
				server0.show_connected_client_10();
				Thread.sleep(300000);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			} 
		}
	}
}



//http://blog.naver.com/mktcrmer/220854105687 - oracle express ��ġ(���� �� sys ���� �� ���)

//println�� print ȥ��� ���ڿ��� �������� ����
//swing������ �̺�Ʈ ��鷯 �Ǽ��� ��ư �ϳ��� 2�� �̻� ���� �ش� Ƚ����ŭ �ݺ� �Ǵµ� (��ư 1�� �����µ� 2�� �̺�Ʈ �߻� ��)
//String sql = "select f_id from login, friend_list where login.id_n = ?"; �� ���� ��,
// rs.getString(friend_list.f_id) �ϸ� ������..... �������� �׳� f_id�� �ҷ��;���
//DISPOSE_ON_CLOSE exit_ON_CLOSE	 -> JFrame ����� �ش� �����Ӹ� ��������, ���α׷� ��ü �������� ����
// setVisible(true); -> �ʹ� �տ��� ȣ�� �� frame���� ������Ʈ ���� frame�� resize������ �ڴʰ� ǥ�õ� �� ����. ���� �������� ȣ�� ����
// revalidate(), repaint();		//2�� ���� ���� ����
// ���� �ۼ������� ���� �뷮 ū ������ ����ƴµ�, ���� ���̸� 100mb �ʰ��� git�� �Ϲ��� ������δ� Ǫ�ð� �ȵ�(��뷮 ���� ���� ���� �ִµ�), 
// filter�����Ͽ� �����ϴ� ������� ó������. �������� ' �� �����쿡�� "���� ó���ϴ� ��찡 ��ټ��ΰ� ����. ���۸��� ��κ� ���ɾ�� ���� ǥ�� ���� ������ ������� ����