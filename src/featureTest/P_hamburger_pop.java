package featureTest;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JEditorPane;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class P_hamburger_pop {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					P_hamburger_pop window = new P_hamburger_pop();
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
	public P_hamburger_pop() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel p_hamburger_pop_container = new JPanel();
		JPanel p_hamburger_pop_n_panel = new JPanel();
		p_hamburger_pop_container.setBounds(12, 10, 240, 202);
		p_hamburger_pop_container.setLayout(null);
		p_hamburger_pop_container.add(p_hamburger_pop_n_panel, BorderLayout.NORTH);
		
		
		p_hamburger_pop_n_panel.setBounds(0, 0, 240, 35);
		p_hamburger_pop_container.add(p_hamburger_pop_n_panel);
		p_hamburger_pop_n_panel.setBackground(new Color(64,64,64,200));
		p_hamburger_pop_n_panel.setLayout(null);
		
		JLabel p_hamburger_la_closeX = new JLabel("X");
		p_hamburger_la_closeX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_la_closeX.setFont(new Font("Arial Black", Font.BOLD, 18));
		p_hamburger_la_closeX.setHorizontalAlignment(SwingConstants.CENTER);
		p_hamburger_la_closeX.setBounds(197, 0, 31, 33);
		p_hamburger_pop_n_panel.add(p_hamburger_la_closeX);
		p_hamburger_la_closeX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				popup.hide();
				s_pop = false;
			}
		});
		
		
		JPanel p_hamburger_team_panel = new JPanel();
		p_hamburger_team_panel.setBounds(0, 32, 240, 67);
		p_hamburger_team_panel.setBackground(new Color(64,64,64,200));
		p_hamburger_pop_container.add(p_hamburger_team_panel);
		p_hamburger_team_panel.setLayout(null);
		
		JLabel p_hamburger_pop_la_dev = new JLabel("dev");
		p_hamburger_pop_la_dev.setBounds(12, 10, 59, 26);
		p_hamburger_pop_la_dev.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_pop_la_dev.setForeground(new Color(169, 169, 169));
		p_hamburger_pop_la_dev.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		p_hamburger_team_panel.add(p_hamburger_pop_la_dev);
		
		JLabel p_hamburger_la_devContact = new JLabel("개발 팀 및 연락처");
		p_hamburger_la_devContact.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_la_devContact.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_la_devContact.setForeground(new Color(169, 169, 169));
		p_hamburger_la_devContact.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p_hamburger_la_devContact.setBounds(12, 36, 171, 26);
		p_hamburger_team_panel.add(p_hamburger_la_devContact);
		
		JPanel p_hamburger_logout_panel = new JPanel();
		p_hamburger_logout_panel.setBounds(0, 97, 240, 106);
		p_hamburger_logout_panel.setBackground(new Color(64,64,64,200));
		p_hamburger_pop_container.add(p_hamburger_logout_panel);
		p_hamburger_logout_panel.setLayout(null);
		
		JLabel p_hamburger_pop_la_accSetting = new JLabel("개인설정");
		p_hamburger_pop_la_accSetting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_pop_la_accSetting.setBounds(12, 20, 92, 24);
		p_hamburger_pop_la_accSetting.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_pop_la_accSetting.setForeground(new Color(169, 169, 169));
		p_hamburger_pop_la_accSetting.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p_hamburger_logout_panel.add(p_hamburger_pop_la_accSetting);
		
		JLabel p_hamburger_la_logout = new JLabel("로그아웃");
		p_hamburger_la_logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_la_logout.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_la_logout.setForeground(new Color(169, 169, 169));
		p_hamburger_la_logout.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p_hamburger_la_logout.setBounds(12, 54, 99, 24);
		p_hamburger_logout_panel.add(p_hamburger_la_logout);
	}
}
