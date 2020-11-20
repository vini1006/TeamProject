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

public class P_search_pop {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					P_search_pop window = new P_search_pop();
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
	public P_search_pop() {
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
		
		JPanel panel_pop_container = new JPanel();
		JPanel n_panel = new JPanel();
		panel_pop_container.setBounds(12, 10, 240, 400);
		panel_pop_container.setLayout(null);
		panel_pop_container.add(n_panel, BorderLayout.NORTH);
		
		
		n_panel.setBounds(0, 0, 240, 144);
		panel_pop_container.add(n_panel);
		n_panel.setBackground(new Color(64,64,64,200));
		n_panel.setLayout(null);
		
		JLabel la_closeX = new JLabel("X");
		la_closeX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		la_closeX.setFont(new Font("Arial Black", Font.BOLD, 18));
		la_closeX.setHorizontalAlignment(SwingConstants.CENTER);
		la_closeX.setBounds(197, 10, 31, 33);
		n_panel.add(la_closeX);
		
		JLabel la_title = new JLabel("검색");
		la_title.setForeground(new Color(169, 169, 169));
		la_title.setFont(new Font("HY견고딕", Font.PLAIN, 22));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		la_title.setBounds(66, 21, 108, 33);
		n_panel.add(la_title);
		
		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setFont(new Font("HY견고딕", Font.BOLD, 16));
		textField.setBackground(Color.BLACK);
		textField.setBounds(22, 59, 198, 27);
		n_panel.add(textField);
		textField.setColumns(10);
		
		Choice choose_category = new Choice();
		choose_category.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		choose_category.setBounds(22, 92, 91, 20);
		choose_category.add("Chat");
		choose_category.add("Board");
		choose_category.add("Files");
		n_panel.add(choose_category);
		
		TextField t_date = new TextField();
		t_date.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		t_date.setBounds(129, 92, 91, 20);
		n_panel.add(t_date);
		
		JLabel lblNewLabel = new JLabel("검색결과");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		lblNewLabel.setBounds(22, 120, 50, 15);
		n_panel.add(lblNewLabel);
		
		JPanel c_panel = new JPanel();
		c_panel.setBackground(new Color(64,64,64,200));
		c_panel.setBounds(0, 142, 240, 258);
		panel_pop_container.add(c_panel);
	}
}
