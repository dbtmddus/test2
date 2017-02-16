package update_txt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Vector;


public class update {

	public static void main(String[] args) throws UnknownHostException, IOException {
		read_one_line();
	}

	public static void read_one_line() throws IOException{

		//������.
		/***********************************************************************/
		String server_StrDir = ".\\qwe";
		File server_file = new File(server_StrDir+"\\server.txt");		
		Vector<String> server_ChattingRecord = new Vector<String>(0);
		BufferedReader server_br = new BufferedReader(new FileReader(server_file));
		String line_for_server;
		while ((line_for_server = server_br.readLine()) != null) {
			server_ChattingRecord.add(line_for_server);		//������ txt ���� ��ü ������� �� ����
		}
		System.out.println("server_ChattingRecord : "+server_ChattingRecord.toString());

		//for (int i=v.size()-1; i>=0; i--){		//�������� �ϳ�������, ���̸� Ŭ���̾�Ʈ���� �ߴ� ��û 
		///	send (v.elementAt(i))
		//}
		server_br.close();
		/***********************************************************************/

		String client_StrDir = ".\\qwe";
		File client_file = new File(server_StrDir+"\\client.txt");		

		Vector<String> client_ChattingRecord = new Vector<String>(0);
		BufferedReader client_br = new BufferedReader(new FileReader(client_file));
		String line_for_client;
		while ((line_for_client = client_br.readLine()) != null) {
			client_ChattingRecord.add(line_for_client);
		}
		
		System.out.println("client_ChattingRecord : "+client_ChattingRecord.toString());

		String receive_str;
		Vector<String> str_will_be_added = new Vector<String>(0);
		for (int i=server_ChattingRecord.size()-1; i>=0; i--){	//������ txt�ڿ������� �޴ٰ� ������ ������ �ߴ�,
			//System.out.println(server_ChattingRecord.size()-1);
			receive_str = server_ChattingRecord.elementAt(i); //listen
			if(!client_ChattingRecord.contains(receive_str)){	//string�� �̰ɷ� �ȵ� �� ����.
				str_will_be_added.add(receive_str);
			}else{
				break;	//�ϳ��� ������ ������ �ߴ�
			}
		}

		for(int i=str_will_be_added.size()-1; i>=0; i--){
			record_txt("client.txt",str_will_be_added.elementAt(i)+"\n");
		}
	}	

	public static void record_txt(String file_name, String msg) throws IOException{
		//File f = new File("C:\\Users\\dbtmddus\\Desktop\\qwerty\\dd.txt");		
		File f = new File(".\\qwe\\"+file_name);		

		String ss = f.getParentFile().toString();
		new File(ss).mkdirs();

		FileOutputStream f_out = new FileOutputStream(f, true);
		f_out.write(msg.getBytes(Charset.forName("EUC-KR")));	//EUC-KR-������, UTF-8 ������.
		f_out.close();
	}	

}