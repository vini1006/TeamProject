package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class ChatPanel {

	public JLabel myRankLabel;
	public JPanel p_chat_container;
	public JPanel p_north;
	public JLabel p_north_chat_title;
	public JPanel p_north_chat_member_panel;
	public JLabel chatMember_name_label;
	public JPanel p_chat;
	public JPanel myUserPanel;
	public JLabel myNameLabel;
	public JLabel myChatTimeLabel;
	public JPanel otherUserPanel;
	public JLabel otherNameLabel;
	public JLabel otherRankLabel;
	public JLabel otherChatTimeLabel;
	public JTextArea chatTextArea;
	public JLabel myImagLabel;
	public JLabel otherImagLabel;
	public JScrollPane scrollPane;
	public JPanel aChat_panel;
	
	public ChatPanel() {
		p_chat_container = new JPanel();
		p_chat_container.setBackground(Color.DARK_GRAY);
		p_chat_container.setPreferredSize(new Dimension(900, 520));
		p_chat_container.setBounds(6, 60, 900, 520);
		p_chat_container.setLayout(new BorderLayout(0, 0));
		p_chat_container.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		
		p_north = new JPanel();
		p_north.setBackground(Color.DARK_GRAY);
		p_north.setPreferredSize(new Dimension(10, 70));
		p_north.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		p_north.setLayout(new BorderLayout(0, 0));
		
		p_north_chat_title = new JLabel("New label");
		p_north_chat_title.setForeground(Color.WHITE);
		p_north_chat_title.setHorizontalAlignment(SwingConstants.CENTER);
		p_north_chat_title.setFont(new Font("HY견고딕", Font.PLAIN, 25));
		p_north_chat_title.setPreferredSize(new Dimension(300, 15));
		
		p_north_chat_member_panel = new JPanel();
		p_north_chat_member_panel.setForeground(Color.WHITE);
		p_north_chat_member_panel.setBackground(Color.DARK_GRAY);
		p_north_chat_member_panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		/*
		chatMember_name_label = new JLabel("KIM");
		chatMember_name_label.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		chatMember_name_label.setForeground(Color.WHITE);
		chatMember_name_label.setPreferredSize(new Dimension(55, 60));
		*/
		
		
		p_chat = new JPanel();
		p_chat.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		p_chat.setMaximumSize(new Dimension(875, 5000000));
		p_chat.setBackground(Color.DARK_GRAY);
		p_chat.setLayout(new FlowLayout(FlowLayout.LEFT, 40,40));
		
		
		aChat_panel = new JPanel();
		aChat_panel.setPreferredSize(new Dimension(875, 150));
		aChat_panel.setBorder(null);
		aChat_panel.setBackground(Color.DARK_GRAY);
		aChat_panel.setLayout(null);
		
		myUserPanel = new JPanel();
		myUserPanel.setBounds(0, 0, 157, 78);
		//aChat_panel.add(myUserPanel);
		myUserPanel.setBorder(null);
		myUserPanel.setBackground(Color.DARK_GRAY);
		myUserPanel.setLayout(new BorderLayout());
		
		myImagLabel = new JLabel("");
		myImagLabel.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\chat_human.png"));
		myImagLabel.setBounds(0, 0, 48, 78);
		myUserPanel.add(myImagLabel);
		
		myNameLabel = new JLabel("윤빈");
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
		
		myChatTimeLabel = new JLabel("12:12 pm");
		myChatTimeLabel.setForeground(Color.WHITE);
		myChatTimeLabel.setBounds(60, 26, 90, 50);
		myUserPanel.add(myChatTimeLabel);
		
		chatTextArea = new JTextArea();
		chatTextArea.setBounds(185, 0, 483, 78);
		chatTextArea.setPreferredSize(new Dimension(483, 78));
		//aChat_panel.add(chatTextArea);
		chatTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		chatTextArea.setForeground(Color.WHITE);
		chatTextArea.setAlignmentX(3.0f);
		chatTextArea.setAlignmentY(3.0f);
		chatTextArea.setBackground(SystemColor.activeCaption);
		chatTextArea.setEditable(false);
		chatTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		chatTextArea.setBorder(new LineBorder(SystemColor.activeCaption, 6, true));
		chatTextArea.setLineWrap(true);
		chatTextArea.setText("채팅들어감");
		/*
		otherUserPanel = new JPanel();
		otherUserPanel.setBounds(694, 0, 180, 78);
		//aChat_panel.add(otherUserPanel);
		otherUserPanel.setLayout(null);
		otherUserPanel.setBorder(null);
		otherUserPanel.setBackground(Color.DARK_GRAY);
		
		otherImagLabel = new JLabel("");
		otherImagLabel.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\chat_human2.png"));
		otherImagLabel.setBounds(132, 0, 48, 79);
		otherUserPanel.add(otherImagLabel);
		
		otherNameLabel = new JLabel("윤빈");
		otherNameLabel.setForeground(Color.WHITE);
		otherNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		otherNameLabel.setFont(new Font("HY견고딕", Font.BOLD, 19));
		otherNameLabel.setBounds(12, 0, 53, 40);
		otherUserPanel.add(otherNameLabel);
		
		otherRankLabel = new JLabel("노예");
		otherRankLabel.setForeground(Color.WHITE);
		otherRankLabel.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		otherRankLabel.setBounds(57, 8, 53, 29);
		otherUserPanel.add(otherRankLabel);
		
		otherChatTimeLabel = new JLabel("12:12 pm");
		otherChatTimeLabel.setForeground(Color.WHITE);
		otherChatTimeLabel.setBounds(12, 26, 74, 29);
		otherUserPanel.add(otherChatTimeLabel);
		*/
	}
	public JPanel gatMychatPanel() {
		aChat_panel.add(chatTextArea);
		aChat_panel.add(myUserPanel);
		return aChat_panel;
	}
	
	public JPanel getOtherChatPanel() {
		aChat_panel.add(chatTextArea);
		aChat_panel.add(otherUserPanel);
		return aChat_panel;
	}
	
	public JPanel getChatPanel() {
		p_chat_container.add(p_north, BorderLayout.NORTH);
		p_north.add(p_north_chat_title, BorderLayout.WEST);
		p_north.add(p_north_chat_member_panel, BorderLayout.CENTER);
		scrollPane = new JScrollPane(p_chat);
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		p_chat_container.add(scrollPane, BorderLayout.CENTER);
		/*
		myUserPanel.add(myImagLabel);
		myUserPanel.add(myNameLabel);
		myUserPanel.add(myRankLabel);
		myUserPanel.add(myChatTimeLabel);
		p_chat.add(otherUserPanel);
		*/
		
		//p_north_chat_member_panel.add(chatMember_name_label);
		/*
		p_chat.add(myUserPanel);
		otherUserPanel.add(otherImagLabel);
		otherUserPanel.add(otherNameLabel);
		otherUserPanel.add(otherRankLabel);
		otherUserPanel.add(otherChatTimeLabel);
		p_chat.add(chatTextArea);
		p_chat.add(chatTextArea_1);
		*/
		
		return p_chat_container;
	}
}
