import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class swing_test {

	public swing_test(){

		JFrame frame =new JFrame("dd");
		frame.setBounds(0,0,500,500);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 500, 300);
		frame.add(panel);
		
		JButton b1= new JButton("b1");
		panel.add(b1);
		
		JButton b2= new JButton("b1");
		panel.add(b2);
		
		frame.setVisible(true);
		
		frame.removeAll();
	}
	
	public static void main(String[] args) {
		swing_test s = new swing_test();
		
	}//end creator
}//end

