package layout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Choice;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class ChatsetPop {

	private JFrame frame;
	private JTextField t_chat_pop_name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatsetPop window = new ChatsetPop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatsetPop() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 835, 786);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel p_chat_set_pop = new JPanel();
		p_chat_set_pop.setBackground(new Color(64,64,64,200));
		p_chat_set_pop.setBounds(0, 0, 231, 405);
		p_chat_set_pop.setLayout(null);
		
		JLabel la_chat_pop_title = new JLabel("채팅 시작");
		la_chat_pop_title.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_title.setFont(new Font("HY견고딕", Font.PLAIN, 28));
		la_chat_pop_title.setBounds(57, 10, 150, 69);
		p_chat_set_pop.add(la_chat_pop_title);
		
		JLabel la_chat_pop_name = new JLabel("이름");
		la_chat_pop_name.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_name.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_chat_pop_name.setBounds(12, 68, 91, 40);
		p_chat_set_pop.add(la_chat_pop_name);
		
		t_chat_pop_name = new JTextField();
		t_chat_pop_name.setForeground(Color.WHITE);
		t_chat_pop_name.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		t_chat_pop_name.setBackground(Color.DARK_GRAY);
		t_chat_pop_name.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEFT, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		t_chat_pop_name.setBounds(12, 101, 207, 30);
		p_chat_set_pop.add(t_chat_pop_name);
		t_chat_pop_name.setColumns(10);
		
		JLabel la_chat_pop_auth = new JLabel("참여가능권한");
		la_chat_pop_auth.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_auth.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_chat_pop_auth.setBounds(12, 150, 101, 30);
		p_chat_set_pop.add(la_chat_pop_auth);
		
		Choice ch_chat_pop_auth = new Choice();
		ch_chat_pop_auth.setForeground(Color.WHITE);
		ch_chat_pop_auth.setBackground(Color.DARK_GRAY);
		ch_chat_pop_auth.setPreferredSize(new Dimension(7, 21));
		ch_chat_pop_auth.setBounds(12, 186, 207, 30);
		p_chat_set_pop.add(ch_chat_pop_auth);
		
		JLabel la_chat_pop_invite = new JLabel("참여 인원 선택");
		la_chat_pop_invite.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_invite.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_chat_pop_invite.setBounds(12, 226, 126, 30);
		p_chat_set_pop.add(la_chat_pop_invite);
		
		JButton bt_chat_pop_cancel = new JButton("취소");
		bt_chat_pop_cancel.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		bt_chat_pop_cancel.setBounds(29, 359, 91, 23);
		p_chat_set_pop.add(bt_chat_pop_cancel);
		bt_chat_pop_cancel.addActionListener((e)->{
			/*
			popup.hide();
			s_pop = false;
			t_chat_pop_name.setText("");
			*/
		});
		
		JButton bt_chat_pop_ok = new JButton("시작");
		bt_chat_pop_ok.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		bt_chat_pop_ok.setBounds(132, 359, 91, 23);
		p_chat_set_pop.add(bt_chat_pop_ok);
		
		JScrollPane p_chat_set_pop_scroll = new JScrollPane();
		p_chat_set_pop_scroll.setBounds(12, 266, 207, 81);
		p_chat_set_pop.add(p_chat_set_pop_scroll);
		
		JPanel p_chat_set_pop_checkPanel = new JPanel();
		p_chat_set_pop_checkPanel.setPreferredSize(new Dimension(10, 21));
		p_chat_set_pop_checkPanel.setBackground(Color.GRAY);
		p_chat_set_pop_scroll.setViewportView(p_chat_set_pop_checkPanel);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("사용자이름");
		chckbxNewCheckBox_1.setPreferredSize(new Dimension(160, 25));
		chckbxNewCheckBox_1.setFont(new Font("HY견고딕", Font.PLAIN, 13));
		chckbxNewCheckBox_1.setBackground(Color.GRAY);
		p_chat_set_pop_checkPanel.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("사용자이름");
		chckbxNewCheckBox_2.setPreferredSize(new Dimension(160, 25));
		chckbxNewCheckBox_2.setFont(new Font("HY견고딕", Font.PLAIN, 13));
		chckbxNewCheckBox_2.setBackground(Color.GRAY);
		p_chat_set_pop_checkPanel.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("사용자이름");
		chckbxNewCheckBox.setBackground(Color.GRAY);
		chckbxNewCheckBox.setFont(new Font("HY견고딕", Font.PLAIN, 13));
		chckbxNewCheckBox.setPreferredSize(new Dimension(160, 25));
		p_chat_set_pop_checkPanel.add(chckbxNewCheckBox);
		
		bt_chat_pop_ok.addActionListener((e)->{
			Object obj = e.getSource();
			if(obj == bt_chat_pop_ok) {
				/*
				createFolder(p_chat_south_center, t_chat_pop_name.getText(), 15);
	  			t_chat_pop_name.setText("");
	  			popup.hide();
				s_pop = false;
				*/
			}
		});
	}
}
