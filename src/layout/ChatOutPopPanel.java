package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;



public class ChatOutPopPanel extends JPanel {
	JLabel label_ment;
	public JButton close_button;
	Popup popup;
	
	public ChatOutPopPanel(String member_name, Popup popup) {
		label_ment = new JLabel(member_name+" 님이 나가셨습니다.");
		close_button = new JButton("확인");
		
		
		label_ment.setPreferredSize(new Dimension(240,80));
		label_ment.setFont(new Font("HY견고딕", Font.BOLD, 20));
		label_ment.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		close_button.setPreferredSize(new Dimension(60,40));
		
		this.add(label_ment);
		this.add(close_button);
		this.setPreferredSize(new Dimension(300,150));
		this.setBackground(Color.GRAY);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
	}

}
