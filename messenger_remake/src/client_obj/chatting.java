package client_obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;

import client_swing.JScrollPanel_friends_in_main;

public class chatting implements Runnable{

	private Socket soc_chat =null;
	private String id = null;
	
	public chatting(Socket _soc_chat, String _id){
		soc_chat = _soc_chat;
		id = _id;
	}

	public void run(){
		try {
			while(true){
				System.out.println("辰特 社掴 企奄掻..");
				BufferedReader listen_chat = new BufferedReader(new InputStreamReader(soc_chat.getInputStream()));
				String f_id = listen_chat.readLine();
				String str_msg = listen_chat.readLine();
				try{
					JScrollPanel_friends_in_main.chat_f.add_chat_record(str_msg, false);	//true = msg from me, false = from others
				}catch(Exception ee){
					ee.printStackTrace();
					System.out.println("ばばばばばばばばばばばばばばばば");
				}
				System.out.println("辰特 社掴 input : "+str_msg + "from "+f_id);
				record_txt(f_id,str_msg+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void record_txt(String f_id, String str) throws IOException{
		//String str_dir = "C:\\Users\\dbtmddus\\Desktop\\qwe";
		String str_dir = ".\\chat_record\\"+id;
		File f = new File(str_dir+"\\"+f_id+".txt");		
		new File(str_dir).mkdirs();
		FileOutputStream f_out = new FileOutputStream(f, true);	//戚嬢 床奄
		f_out.write(str.getBytes(Charset.forName("UTF-8")));
		f_out.close();
	}	
	
}