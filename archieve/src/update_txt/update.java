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

		//서버측.
		/***********************************************************************/
		String server_StrDir = ".\\qwe";
		File server_file = new File(server_StrDir+"\\server.txt");		
		Vector<String> server_ChattingRecord = new Vector<String>(0);
		BufferedReader server_br = new BufferedReader(new FileReader(server_file));
		String line_for_server;
		while ((line_for_server = server_br.readLine()) != null) {
			server_ChattingRecord.add(line_for_server);		//서버측 txt 내용 전체 순서대로 다 넣음
		}
		System.out.println("server_ChattingRecord : "+server_ChattingRecord.toString());

		//for (int i=v.size()-1; i>=0; i--){		//역순으로 하나씩보냄, 끝이면 클라이언트에서 중단 요청 
		///	send (v.elementAt(i))
		//}
		server_br.close();
		/***********************************************************************/

		String client_StrDir = ".\\qwe";
		File client_file = new File(client_StrDir+"\\client.txt");		

		Vector<String> client_ChattingRecord = new Vector<String>(0);
		BufferedReader client_br = new BufferedReader(new FileReader(client_file));
		String line_for_client;
		while ((line_for_client = client_br.readLine()) != null) {
			client_ChattingRecord.add(line_for_client);
		}
		
		System.out.println("client_ChattingRecord : "+client_ChattingRecord.toString());

		String receive_str;
		Vector<String> str_will_be_added = new Vector<String>(0);
		for (int i=server_ChattingRecord.size()-1; i>=0; i--){	//서버측 txt뒤에서부터 받다가 읽은거 나오면 중단,
			//System.out.println(server_ChattingRecord.size()-1);
			receive_str = server_ChattingRecord.elementAt(i); //listen
			if(!client_ChattingRecord.contains(receive_str)){	//string은 이걸로 안될 수 있음.
				str_will_be_added.add(receive_str);
			}else{
				break;	//하나라도 읽은거 나오면 중단
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
		f_out.write(msg.getBytes(Charset.forName("EUC-KR")));	//EUC-KR-윈도우, UTF-8 리눅스.
		f_out.close();
	}	

}
