import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class jscroll_panel_basic{
	public static void main(String args[]){

		JFrame frame = new JFrame("dd");
		frame.setBounds(0,0,500,500);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBounds(0,0, 500,500);
		frame.add(panel);
		
		JButton b1 = new JButton("b1");
		panel.add(b1, BorderLayout.WEST);

		JPanel right_panel = new JPanel();
		right_panel.setLayout(new GridLayout(2,1));
		panel.add(right_panel, BorderLayout.CENTER);
		///////////////////////////////////////////////////
		
		JButton b2 = new JButton("b2");
		right_panel.add(b2);

		JButton b3 = new JButton("b3");
		right_panel.add(b3);

		frame.setVisible(true);
		
	}
}

