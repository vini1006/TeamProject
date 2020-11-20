package layout;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Elements {
	JLabel Label_notice;
	
	public Elements() {
		
	}
	
	public void createFolder(JPanel panel, String folderName, int fontSize) {
		Font font = new Font("HY견고딕", Font.BOLD, fontSize);
		JLabel a = new JLabel(folderName, 20);
		panel.add(a);
	}

}
