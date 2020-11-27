package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.ScrollPaneConstants;

public class ChatPanel_design {

	private JFrame frame;
	private JLabel myRankLabel;
	JPanel p_chat_container;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatPanel_design window = new ChatPanel_design();
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
	public ChatPanel_design() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1068, 749);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		p_chat_container = new JPanel();
		p_chat_container.setBackground(Color.DARK_GRAY);
		p_chat_container.setPreferredSize(new Dimension(900, 520));
		p_chat_container.setBounds(6, 60, 900, 520);
		frame.getContentPane().add(p_chat_container);
		p_chat_container.setLayout(new BorderLayout(0, 0));
		
		JPanel p_north = new JPanel();
		p_north.setBackground(Color.DARK_GRAY);
		p_north.setPreferredSize(new Dimension(10, 70));
		p_chat_container.add(p_north, BorderLayout.NORTH);
		p_north.setLayout(new BorderLayout(0, 0));
		
		JLabel p_north_chat_title = new JLabel("New label");
		p_north_chat_title.setForeground(Color.WHITE);
		p_north_chat_title.setHorizontalAlignment(SwingConstants.CENTER);
		p_north_chat_title.setFont(new Font("HY견고딕", Font.PLAIN, 25));
		p_north_chat_title.setPreferredSize(new Dimension(300, 15));
		p_north.add(p_north_chat_title, BorderLayout.WEST);
		
		JPanel p_north_chat_member_panel = new JPanel();
		p_north_chat_member_panel.setForeground(Color.WHITE);
		p_north_chat_member_panel.setBackground(Color.DARK_GRAY);
		p_north_chat_member_panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		p_north.add(p_north_chat_member_panel, BorderLayout.CENTER);
		
		JLabel chatMember_name_label = new JLabel("KIM");
		chatMember_name_label.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		chatMember_name_label.setForeground(Color.WHITE);
		chatMember_name_label.setPreferredSize(new Dimension(55, 60));
		p_north_chat_member_panel.add(chatMember_name_label);
		
		
		JPanel p_chat = new JPanel();
		p_chat.setPreferredSize(new Dimension(800, 10));
		p_chat.setBorder(UIManager.getBorder("ToolTip.border"));
		p_chat.setBackground(Color.DARK_GRAY);
		
		JScrollPane scrollPane = new JScrollPane(p_chat);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		p_chat.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel aChat_panel = new JPanel();
		aChat_panel.setPreferredSize(new Dimension(875, 100));
		aChat_panel.setBorder(null);
		aChat_panel.setBackground(Color.DARK_GRAY);
		p_chat.add(aChat_panel);
		aChat_panel.setLayout(null);
		
		JPanel myUserPanel = new JPanel();
		myUserPanel.setBounds(0, 0, 157, 78);
		aChat_panel.add(myUserPanel);
		myUserPanel.setBorder(null);
		myUserPanel.setBackground(Color.DARK_GRAY);
		myUserPanel.setLayout(null);
		
		JLabel myImagLabel = new JLabel("");
		myImagLabel.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\chat_human.png"));
		myImagLabel.setBounds(0, 0, 48, 78);
		myUserPanel.add(myImagLabel);
		
		JLabel myNameLabel = new JLabel("윤빈");
		myNameLabel.setForeground(Color.WHITE);
		myNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		myNameLabel.setFont(new Font("HY견고딕", Font.BOLD, 19));
		myNameLabel.setBounds(57, 0, 53, 40);
		myUserPanel.add(myNameLabel);
		
		myRankLabel = new JLabel("노예");
		myRankLabel.setForeground(Color.WHITE);
		myRankLabel.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		myRankLabel.setBounds(104, 8, 53, 29);
		myUserPanel.add(myRankLabel);
		
		JLabel myChatTimeLabel = new JLabel("12:12 pm");
		myChatTimeLabel.setForeground(Color.WHITE);
		myChatTimeLabel.setBounds(60, 26, 74, 29);
		myUserPanel.add(myChatTimeLabel);
		
		JTextArea chatTextArea = new JTextArea();
		chatTextArea.setBounds(185, 0, 483, 78);
		aChat_panel.add(chatTextArea);
		chatTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		chatTextArea.setForeground(Color.WHITE);
		chatTextArea.setAlignmentX(3.0f);
		chatTextArea.setAlignmentY(3.0f);
		chatTextArea.setBackground(SystemColor.activeCaption);
		chatTextArea.setEditable(false);
		chatTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		chatTextArea.setBorder(new LineBorder(SystemColor.activeCaption, 2, true));
		chatTextArea.setLineWrap(true);
		chatTextArea.setText("안녕하세요 좋은하루입니다 제가글을 길~~~~~~~~~~~~~~~~~~~~~~~~~~~게 써볼게요 그리고");
		
		JPanel otherUserPanel = new JPanel();
		otherUserPanel.setBounds(694, 0, 180, 78);
		aChat_panel.add(otherUserPanel);
		otherUserPanel.setLayout(null);
		otherUserPanel.setBorder(null);
		otherUserPanel.setBackground(Color.DARK_GRAY);
		
		JLabel otherImagLabel = new JLabel("");
		otherImagLabel.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\chat_human2.png"));
		otherImagLabel.setBounds(132, 0, 48, 79);
		otherUserPanel.add(otherImagLabel);
		
		JLabel otherNameLabel = new JLabel("윤빈");
		otherNameLabel.setForeground(Color.WHITE);
		otherNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		otherNameLabel.setFont(new Font("HY견고딕", Font.BOLD, 19));
		otherNameLabel.setBounds(12, 0, 53, 40);
		otherUserPanel.add(otherNameLabel);
		
		JLabel otherRankLabel = new JLabel("노예");
		otherRankLabel.setForeground(Color.WHITE);
		otherRankLabel.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		otherRankLabel.setBounds(57, 8, 53, 29);
		otherUserPanel.add(otherRankLabel);
		
		JLabel otherChatTimeLabel = new JLabel("12:12 pm");
		otherChatTimeLabel.setForeground(Color.WHITE);
		otherChatTimeLabel.setBounds(12, 26, 74, 29);
		otherUserPanel.add(otherChatTimeLabel);
		p_chat_container.add(scrollPane, BorderLayout.CENTER);
		
	}
}
