package socket;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import layout.MainApp;
import models.MessageVO;



public class MainAppChatThread extends Thread{
	Socket socket;
	MainAppChatSocket mainAppchatSocket;
	MainApp mainApp;
	BufferedReader buffr;
	BufferedWriter buffw;
	boolean flag = true;
	String sender;
	
	int chat_id;
	String current_time;
	int member_no;
	String content;
	int message_id;
	
	
	

	public MainAppChatThread(MainApp mainApp, Socket socket, MainAppChatSocket mainAppchatSocket) {
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
				text = line.split(",", 5);
				chat_id = Integer.parseInt(text[0]);
				current_time = text[1];
				member_no = Integer.parseInt(text[2]);
				
				message_id = Integer.parseInt(text[3]);
				content = text[4];
				
				mainApp.messageVOList.add(setAmessageVO());
				insertMyChat(content);
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
		MessageVO messageVO = new MessageVO();
		messageVO.setChat_id(chat_id);
		messageVO.setChat_time(current_time);
		messageVO.setMember_no(member_no);
		messageVO.setContent(content);
		messageVO.setMessage_id(message_id);
		
		return messageVO;
	}
	
	public void insertMyChat(String msg){
		JTextArea chatTextArea = new JTextArea();
		if(mainApp.messageVOList.size() <= 0) {
			chatTextArea = new JTextArea();
			chatTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 16));
			chatTextArea.setForeground(Color.WHITE);
			chatTextArea.setBackground(SystemColor.activeCaption);
			chatTextArea.setEditable(false);
			chatTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			chatTextArea.setBorder(new LineBorder(SystemColor.activeCaption, 2, true));
			chatTextArea.setLineWrap(true);
			chatTextArea.setText(msg);
			chatTextArea.setBounds(200, 10, 480, 80);
			mainApp.p_chat.add(chatTextArea);
			mainApp.p_center.updateUI();
			
		}else {
			chatTextArea = new JTextArea();
			chatTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 16));
			chatTextArea.setForeground(Color.WHITE);
			chatTextArea.setBackground(SystemColor.activeCaption);
			chatTextArea.setEditable(false);
			chatTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			chatTextArea.setBorder(new LineBorder(SystemColor.activeCaption, 2, true));
			chatTextArea.setLineWrap(true);
			chatTextArea.setText(msg);
			chatTextArea.setBounds(200, mainApp.messageVOList.size()*80+40, 480, 80);
			mainApp.p_chat.add(chatTextArea);
			mainApp.p_center.updateUI();
		}
	}
	
}
