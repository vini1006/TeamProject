package featureTest;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class ChatPopadd {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatPopadd window = new ChatPopadd();
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
	public ChatPopadd() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 657);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel p_chat_set_pop_add_container = new JPanel();
		p_chat_set_pop_add_container.setBounds(0, 0, 180, 405);
		p_chat_set_pop_add_container.setBackground(new Color(50,50,50,180));
		
		frame.getContentPane().add(p_chat_set_pop_add_container);
		p_chat_set_pop_add_container.setLayout(new BorderLayout(0, 0));
		
		
		JPanel p_chat_set_pop_add_panel = new JPanel();
		p_chat_set_pop_add_panel.setBounds(0, 0, 180, 405);
		p_chat_set_pop_add_panel.setBackground(new Color(50,50,50,180));
		p_chat_set_pop_add_panel.setLayout(null);
		
		JScrollPane p_chat_pop_add_scrolls = new JScrollPane(p_chat_set_pop_add_panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("HY견고딕", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 140, 30);
		p_chat_set_pop_add_panel.add(lblNewLabel);
		p_chat_pop_add_scrolls.setBackground(new Color(50,50,50,180));
		p_chat_set_pop_add_container.add(p_chat_pop_add_scrolls, BorderLayout.CENTER);
		

		
	}
}
