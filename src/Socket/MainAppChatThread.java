package socket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import layout.ChatOutPopPanel;
import layout.ChatPanel;
import layout.MainApp;
import models.MessageVO;
import utill.Chat_lib;



public class MainAppChatThread extends Thread{
	Socket socket;
	MainAppChatSocket mainAppchatSocket;
	MainApp mainApp;
	BufferedReader buffr;
	BufferedWriter buffw;
	public boolean flag = true;
	String sender;
	MessageVO messageVO;
	
	int chat_id;
	String current_time;
	int member_no;
	String content;
	int message_id;
	String memberName;
	
	Chat_lib chat_lib;
	

	public MainAppChatThread(MainApp mainApp, Socket socket, MainAppChatSocket mainAppchatSocket) {
		
		chat_lib = new Chat_lib(mainApp);
		messageVO = new MessageVO();
		this.socket = socket;
		this.mainAppchatSocket = mainAppchatSocket;
		this.mainApp = mainApp;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		listen();
	}
	
	// 서버가 보낸 메시지 듣기.
	public void listen() {
		String line = null;
		String[] text = new String[4];

		while (flag) {
				try {
					line = buffr.readLine();
					System.out.println("현재 내가 참여한 채팅방 : "+mainApp.chatVO.getChat_id());
					text = line.split(",", 6);
					chat_id = Integer.parseInt(text[0]);
					current_time = text[1];
					member_no = Integer.parseInt(text[2]);
					message_id = Integer.parseInt(text[3]);
					memberName = text[4];
					mainApp.gotChatMemberName = text[4];
					mainApp.messageVO = setAmessageVO();
					mainApp.messageVOList.add(setAmessageVO());
					content = text[5];
					String[] decryptN = content.split("#n:931006");
					
					if(decryptN[0].equals("#newChat:931006")) {
						if (chat_lib.memberComparing(mainApp.messageVO.getChat_id(),
								mainApp.getRegistMemberVO().getMember_no())) {
							chat_lib.refreshChatList(mainApp.con);
						}
					}else if(decryptN[0].equals("#oneLeft:931006")) {
						if(mainApp.chatVO.getChat_id() == mainApp.messageVO.getChat_id()) {
							mainApp.chatPanel.p_north_chat_member_panel.removeAll();
							JLabel membernameLabel = new JLabel("참여자가 없는 채팅방입니다.");
							membernameLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
							membernameLabel.setForeground(Color.WHITE);
							membernameLabel.setPreferredSize(new Dimension(300, 60));
							mainApp.chatPanel.p_north_chat_member_panel.add(membernameLabel);
							mainApp.chattextArea.setEnabled(false);
							System.out.println("#oneLeft 들어옴");
							mainApp.chattextArea.updateUI();
							mainApp.chatPanel.p_north_chat_member_panel.updateUI();
						}
					}else if(decryptN[0].equals("#outFromChat:931006")) {
						if(mainApp.chatVO.getChat_id() == mainApp.messageVO.getChat_id()) {
							chat_lib.setCurrentChatVO(mainApp.chatVO.getChat_title());
							chat_lib.loadChatPanel();
							
							String m_name = chat_lib.changeMemberNotoName(mainApp.messageVO.getMember_no());
							ChatOutPopPanel chatOutPopPanel = new ChatOutPopPanel(m_name, mainApp.chatOut_pop);
							mainApp.chatOut_pop = mainApp.popupFactory.getPopup(mainApp.frame, chatOutPopPanel, mainApp.frame.getLocationOnScreen().x+450, mainApp.frame.getLocationOnScreen().y+150 );
							chatOutPopPanel.close_button.addActionListener((e)->{
								mainApp.chatOut_pop.hide();
								if(mainApp.messageVO.getChat_id() == mainApp.chatVO.getChat_id() && mainApp.messageVO.getMember_no() == mainApp.getRegistMemberVO().getMember_no()) {
									mainApp.p_center_south.setVisible(false);
									mainApp.p_chat_south_center.setPreferredSize(new Dimension(240, mainApp.chatSmallPanels.size()*45));
									mainApp.p_center_center.removeAll();
									mainApp.p_center_center.updateUI();
									mainApp.p_chat_south_center.updateUI();
								}
							});
							mainApp.chatOut_pop.show();
							
						}
					}else {
						
						if (mainApp.chatVO.getChat_id() == mainApp.messageVO.getChat_id()) {
							String decryptNcontent = "";
							StringBuffer sb = new StringBuffer();
							for (int i = 0; i < decryptN.length; i++) {
								if (!decryptN[i].equals("null")) {
									sb.append(decryptN[i] + "\n");
								}
							}
							decryptNcontent = sb.toString();
							if (decryptNcontent != null) {
								insertMyChat(decryptNcontent);
							}
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	//MainApp에서 채팅 칠시 send메소드 호출
	//클라이언트의 소켓으로 전송
	public void send(String msg) {
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MessageVO setAmessageVO() {
		messageVO = new MessageVO();
		messageVO.setChat_id(chat_id);
		messageVO.setChat_time(current_time);
		messageVO.setMember_no(member_no);
		messageVO.setContent(content);
		messageVO.setMessage_id(message_id);
		
		return messageVO;
	}
	
	public void insertMyChat(String msg){
		JTextArea chatTextArea = new JTextArea();
		ChatPanel panel = new ChatPanel();
		JPanel aChatPanel = new JPanel();
		aChatPanel.setLayout(new BorderLayout());
		
		chatTextArea = new JTextArea();
		chatTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		chatTextArea.setForeground(Color.WHITE);
		chatTextArea.setBackground(SystemColor.activeCaption);
		chatTextArea.setEditable(false);
		chatTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		chatTextArea.setBorder(new LineBorder(SystemColor.activeCaption, 2, true));
		chatTextArea.setLineWrap(true);
		chatTextArea.setText(msg);
		
		int lineCount = chatTextArea.getLineCount();
		int chatTextAreaHeight = 15*lineCount;
		chatTextArea.setPreferredSize(new Dimension(480, chatTextAreaHeight));
		
		JPanel userPanel = new JPanel();
		userPanel.setBorder(null);
		userPanel.setBackground(Color.DARK_GRAY);
		userPanel.setLayout(null);
		
		if(mainApp.getRegistMemberVO().getMember_no() == messageVO.getMember_no() ) {
			userPanel.setBounds(0, 0, 157, 78);
			userPanel.setPreferredSize(new Dimension(157,78));
			chatTextArea.setBounds(185, 0, 480, chatTextAreaHeight);
			aChatPanel.add(userPanel, BorderLayout.WEST);
			chatTextArea.setBackground(new Color(62, 179, 181));
		}else {
			panel.myChatTimeLabel.setBounds(12, 26, 74, 29);
			panel.myRankLabel.setBounds(57, 8, 53, 29);
			panel.myNameLabel.setBounds(12, 0, 53, 40);
			panel.myImagLabel.setBounds(100, 0, 48, 79);
			userPanel.setBounds(694, 0, 157, 78);
			userPanel.setPreferredSize(new Dimension(157,78));
			chatTextArea.setBounds(230, 0, 480, chatTextAreaHeight);
			aChatPanel.add(userPanel, BorderLayout.EAST);
		}
		panel.myChatTimeLabel.setText(messageVO.getChat_time());
		panel.myNameLabel.setText(memberName);
		userPanel.add(panel.myImagLabel);
		userPanel.add(panel.myChatTimeLabel);
		userPanel.add(panel.myRankLabel);
		userPanel.add(panel.myNameLabel);
		
		aChatPanel.setPreferredSize(new Dimension(875, chatTextAreaHeight+30));
		aChatPanel.setBorder(null);
		aChatPanel.setBackground(Color.DARK_GRAY);
		aChatPanel.add(chatTextArea);
		aChatPanel.add(userPanel);
		
		mainApp.p_chat.setPreferredSize(new Dimension(300, mainApp.messageVOList.size()*105));
		mainApp.p_chat.add(aChatPanel);
		mainApp.chat_scroll.revalidate();
		int max = mainApp.chat_scroll.getVerticalScrollBar().getMaximum();
		
		Runnable doScroll = new Runnable() {
		
			public void run() {
				mainApp.chat_scroll.getVerticalScrollBar().setValue(max);
			}
		};
		SwingUtilities.invokeLater(doScroll);
		
		mainApp.p_chat.updateUI();
		mainApp.p_center.updateUI();
		}
	
	
	
}
