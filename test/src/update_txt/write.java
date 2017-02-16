package update_txt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Vector;


public class write {

	public static void main(String[] args) throws UnknownHostException, IOException {
		for (int i=0; i<10; i++){
			String str = i +" / "+ Long.toString(System.currentTimeMillis())+"\n";
			record_txt("client.txt",str);
			record_txt("server.txt",str);
		}
		
		for (int i=10; i<20; i++){
			String str = i +" / "+ Long.toString(System.currentTimeMillis())+"\n";
			record_txt("server.txt",str);
		}
	}

	public static void record_txt(String file_name, String msg) throws IOException{
		File f = new File(".\\qwe\\"+file_name);		

		String ss = f.getParentFile().toString();
		new File(ss).mkdirs();

		FileOutputStream f_out = new FileOutputStream(f, true);
		f_out.write(msg.getBytes(Charset.forName("EUC-KR")));	//EUC-KR-윈도우, UTF-8 리눅스.
		f_out.close();
	}	

}
