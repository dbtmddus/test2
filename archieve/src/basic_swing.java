import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class basic_swing extends JFrame{

	private JTextField ID_textfield;
	public basic_swing() {		
		super();

		setLayout(null);
		setVisible(true);
		setSize(260, 226);

		ID_textfield =new JTextField("aa");
		ID_textfield.setBounds(112, 128, 116, 24);	//안하면 안나옴 (아마 크기 0인듯)
		add(ID_textfield);						//안하면 안 나옴
		ID_textfield.addActionListener(action);	// 안 넣으면 이벤트 ㄴㄴ, 오류 주의
		
		/* 옵션
		 * ID_textfield.setText("aa");
		ID_textfield.setColumns(10);
		*/
	}//end creator

	ActionListener action = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			Object obj = e.getSource();
			/*if (obj==login_button){
			}
			else if (obj==signin_button){
				
			}*/
		}
	};
}//end

