import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class swing_test {

	static Vector<JPanel> arr_panel = new Vector<JPanel>(0);
	static Vector<JButton> arr_button = new Vector<JButton>(0);

	private JFrame frame;
	private	JScrollPane spanel;
	private JPanel big_panel;

	private int n_frame = 5;

	public swing_test(){
		
		frame = new JFrame("");

		String[] arr = {"a","b","c"};
		
		frame.getContentPane().setLayout(null);
		frame.setSize(264, 186);

		frame.setVisible(true);

		
		big_panel = new JPanel();
		big_panel.setLayout(new GridLayout(n_frame+1,1));
		//big_panel.setBounds(0,0,frame.getContentPane().getBounds().width, 60*n_frame);	//�ٽ� 
		big_panel.setBounds(frame.getContentPane().getBounds());	//55 
		
		for (int i=0; i<n_frame; i++){
			big_panel.add(new JButton("dd"));
		}
		
		spanel = new JScrollPane();
		//null, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spanel.setBounds(frame.getContentPane().getBounds());
		
		spanel.setViewportView(big_panel);
		frame.add(spanel);
		//frame.getContentPane().add(spanel);
		

		
		/*
		//for (int i=0; i<n_frame; i++){
			JPanel panel1 = new JPanel();
			big_panel.add(panel1);
		
			JButton b1 = new JButton("New button");
			b1.setBounds(panel1.getBounds());
			panel1.add(b1);
			
			//
			JPanel panel2 = new JPanel();
			big_panel.add(panel2);
				
			JButton b2 = new JButton("New button");
			b2.setBounds(panel2.getBounds());
			panel2.add(b2);
			
		//}*/
		
		
	}//end creator


	public void f_repaint(){
		big_panel.setBounds(0,0,frame.getContentPane().getBounds().width, 60*n_frame);	//�ٽ� 
		spanel.setBounds(big_panel.getBounds());
		//frame.getContentPane().getBounds());	//���ϸ� �ȳ��� (�Ƹ� ũ�� 0�ε�)

		for (int i=0; i< arr_panel.size(); i++){
			JPanel panel = arr_panel.get(i);
			panel.setBounds(0, 70*i ,(int)spanel.getBounds().getWidth(), 60);
			
			//panel.ali
			//panel.getComponents()[0].setBounds(panel.getBounds());
			//repaint();
		}
	}
	public static void main(String[] args) {
		swing_test s = new swing_test();
		//while(true){
		//	s.f_repaint();
		//}
	}//end creator
}//end

