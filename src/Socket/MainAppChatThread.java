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

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import layout.ChatPanel;
import layout.MainApp;
import models.MessageVO;



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
	String current_MemberName;
	
	
	

	public MainAppChatThread(MainApp mainApp, Socket socket, MainAppChatSocket mainAppchatSocket) {
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
	
	//서버가 보낸 메시지 듣기.
	public void listen() {
		String line = null;
		String[] text = new String[4];
		
		
	
		while(flag) {
			try {
				line = buffr.readLine();
				text = line.split(",", 6);
				chat_id = Integer.parseInt(text[0]);
				current_time = text[1];
				member_no = Integer.parseInt(text[2]);
				message_id = Integer.parseInt(text[3]);
				current_MemberName = text[4];
				mainApp.gotChatMemberName = text[4];
				content = text[5];
				mainApp.messageVOList.add(setAmessageVO());
				
				String[] decryptN = content.split("#n:931006");
				String decryptNcontent = "";
				StringBuffer sb = new StringBuffer();
				for(int i =0; i<decryptN.length; i++) {
					sb.append(decryptN[i]+"\n");
				}
				decryptNcontent = sb.toString(); 
				if(decryptNcontent != null) {
					insertMyChat(decryptNcontent);
				}
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
		panel.myNameLabel.setText(current_MemberName);
		userPanel.add(panel.myImagLabel);
		userPanel.add(panel.myChatTimeLabel);
		userPanel.add(panel.myRankLabel);
		userPanel.add(panel.myNameLabel);
		
		aChatPanel.setPreferredSize(new Dimension(875, chatTextAreaHeight+30));
		aChatPanel.setBorder(null);
		aChatPanel.setBackground(Color.DARK_GRAY);
		aChatPanel.add(chatTextArea);
		aChatPanel.add(userPanel);
		
		mainApp.p_chat.setPreferredSize(new Dimension(300, mainApp.messageVOList.size()*100));
		mainApp.p_chat.add(aChatPanel);
		System.out.println(mainApp.p_chat.getComponentCount());
		mainApp.p_chat.updateUI();
		mainApp.p_center.updateUI();
		System.out.println(mainApp.p_chat);
		}
	
}
