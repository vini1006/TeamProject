package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DevContact extends JPanel {
	JLabel[] labels;
	JLabel[] labels_num;

	public DevContact() {
		this.setPreferredSize(new Dimension(400, 500));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		
		String[] name = {"이동열", "윤빈", "서진호", "김재성", "김범진"};
		String[] phoneNum = {"010-2816-7518", "010-2371-7223", "010-8267-5819", "010-2940-8805", "010-2880-4085"};
				
		
		labels = new JLabel[name.length];
		labels_num = new JLabel[name.length];
		
		for(int i=0; i<name.length; i++) {
			labels[i] = new JLabel(name[i]);
			labels_num[i] = new JLabel(phoneNum[i]);
			labels[i].setBounds(100, 50+(i*90), 200, 30);
			labels_num[i].setBounds(100, 80+(i*90), 200, 30);
			labels[i].setFont(new Font("HY견고딕", Font.BOLD, 20));
			labels_num[i].setFont(new Font("HY견고딕", Font.BOLD, 20));
			labels[i].setForeground(Color.white);
			labels_num[i].setForeground(Color.white);
			this.add(labels[i]);
			this.add(labels_num[i]);
		}
		
		this.setVisible(true);
	}
	
	public JPanel getDevContact() {
		return this;
	}
		
}
