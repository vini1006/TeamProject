package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CenterChattingPanel extends JPanel{
	
	public CenterChattingPanel() {
		this.setPreferredSize(new Dimension(0, 0));
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
//		p_center.add(p_center_chat, BorderLayout.CENTER);
		this.setBackground(new Color(64, 64, 64));
		this.setLayout(new BorderLayout(0, 0));

		JPanel p_center_chat_west = new JPanel();
		p_center_chat_west.setBackground(Color.DARK_GRAY);
		p_center_chat_west.setPreferredSize(new Dimension(83, 0));
		this.add(p_center_chat_west, BorderLayout.WEST);

		JPanel p_center_chat_center = new JPanel();
		p_center_chat_center.setBackground(Color.DARK_GRAY);
		this.add(p_center_chat_center, BorderLayout.CENTER);

		JPanel p_center_chat_east = new JPanel();
		p_center_chat_east.setBackground(Color.DARK_GRAY);
		p_center_chat_east.setPreferredSize(new Dimension(83, 0));
		this.add(p_center_chat_east, BorderLayout.EAST);
		p_center_chat_east.setLayout(null);

		JPanel p_myChat_test_1 = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				ImageIcon icon = new ImageIcon("C:/workspace/Java_workspace/TeamProject/src/res/chat_human.png");
				Image img = icon.getImage();
				g2.drawImage(img, 5, 5, null);
			}
		};

		p_myChat_test_1.setLayout(null);
		p_myChat_test_1.setBackground(Color.DARK_GRAY);
		p_myChat_test_1.setBounds(4, 4, 75, 75);
		p_center_chat_east.add(p_myChat_test_1);
		p_center_chat_west.setLayout(null);

		JPanel p_myChat_test = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				ImageIcon icon = new ImageIcon("C:/workspace/Java_workspace/TeamProject/src/res/chat_human.png");
				Image img = icon.getImage();
				g2.drawImage(img, 5, 5, null);
			}
		};

		p_myChat_test.setBounds(4, 4, 75, 75);
		p_myChat_test.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_myChat_test.setBackground(Color.DARK_GRAY);
		p_myChat_test.setLayout(null);
		p_center_chat_west.add(p_myChat_test);

	}
	
	
	
	
}
