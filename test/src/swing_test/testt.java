package swing_test;


import java.awt.Point;
import java.awt.image.ImageObserver;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.html.ListView;

class testt{
	public static void main(String args[]){
		JFrame f = new JFrame("ScrollPaneTestttt");
		JScrollPane sp = new JScrollPane();
		JPanel p = new JPanel();

		ListView list = new ListView(null);
		f.setSize(350, 157);
		sp.setBounds(f.getContentPane().getBounds());
		p.setBounds(0,0,f.getContentPane().getWidth(),1000);
		//p.setSize(f.getContentPane().size());
		p.setLayout(null);
		//f.pack();
		sp.setViewportView(p);
		//p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		//p.setLayout();
		//
		
		int len_str=0;
		int left = 10;
		int right = f.getSize().width-15;	//p.WIDTH==0??? ��?
		int next_y=3;
		int font_size=10;
		int one_line_size=20;
		int len_w=0;
		int len_h =0;
		
		JTextPane tp1 = new JTextPane();
		String str1 = "111111111111111111";
		str1 = str1.
		tp1.setText("111111111111111111");
		len_str = tp1.getText().length();
		if (len_str<one_line_size){
		len_w = len_str;
		len_h = 1;
		}else{
			len_w = len_str%one_line_size;
			len_h = len_str/one_line_size;
		}
		Point size = new Point(len_w*font_size, (int)(len_h*font_size*3.8));
		Point loc = new Point(left, next_y);
		tp1.setBounds(loc.x, loc.y, size.x, size.y);
		next_y += (size.y+3);
		p.add(tp1);
		//
		
		JTextPane tp2 = new JTextPane();
		tp2.setText("22222222222222222222222\n2");
		len_str = tp2.getText().length();
		len_w = len_str;
		len_h = 1;
		size = new Point(len_w*10, len_h*50);
		loc = new Point(right-size.x, next_y);
		tp2.setBounds(loc.x, loc.y, size.x, size.y);
		next_y += (size.y+3);
		p.add(tp2);
		
		System.out.println(p.getPreferredSize().getWidth());
		System.out.println(f.getSize().width);
		System.out.println(f.size().width);
		System.out.println(right);
		System.out.println(size.x);
		System.out.println(loc.x);
		//
		
		f.getContentPane().add(sp);    
		f.setVisible(true);             
	}
}
