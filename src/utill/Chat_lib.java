package utill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import socket.MainAppChatSocket;
import layout.ChatPanel;
import layout.MainApp;
import models.ChatMemberVO;
import models.ChatVO;
import models.MessageVO;

/*
 * 채팅 관련 메소드들 모아놓는 lib 클래스
 * 현재 메소드목록
 * 
 * 1. createChatTable();
 * 2. createChatList();
 * 
 */

public class Chat_lib {
	DBManager dbManager;
	MainApp mainApp;
	Connection con;
	ChatVO chatVO;
	public Chat_lib() {
		
	}
	public Chat_lib(MainApp mainApp) {
		this.dbManager = new DBManager();
		this.mainApp = mainApp;
		this.con = mainApp.con;
	}

	// 채팅 목록에 리스트생성시 확인버튼누르면 chat, chatmember(채팅참여인원)에 db입력메소드
	public void insertChatTable() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Integer> member_idList = new ArrayList<Integer>();
		int chat_id = 0;

		// chat 테이블에 작성
		String sql_insert_chat = "insert into chat(chat_id, chat_title, chat_status)";
		sql_insert_chat += " values(seq_chat.nextval, ?, '1')";

		// chat 티이블의 pk 얻어오기
		String sql_select_pk_chat = "select chat_id from chat where chat_title= ?";
		// t_chat_pop_name.getText()

		// RegistMember 테이블로부터 멤버아이디얻어오기
		String sql_select_RegistMember_no = "select member_no from registmember where member_name =?";

		// chatmember에 chat 테이블의 pk 넣기
		String sql_insert_chatmember = "insert into chatmember(chat_id, chatmember_id, member_no)"
				+ " values(?, seq_chatmember.nextval, ?)";

		try {
			pstmt = con.prepareStatement(sql_insert_chat);
			pstmt.setString(1, mainApp.t_chat_pop_name.getText());
			int isExecute = pstmt.executeUpdate();
			if (isExecute == 0) {
				JOptionPane.showMessageDialog(mainApp.frame, "chat insert 쿼리실패");
			} else {
				JOptionPane.showMessageDialog(mainApp.frame, "chat insert 쿼리성공");
				pstmt.close();
				pstmt = con.prepareStatement(sql_select_pk_chat);
				pstmt.setString(1, mainApp.t_chat_pop_name.getText());
				rs = pstmt.executeQuery();
				if (rs.next() == false) {
					JOptionPane.showMessageDialog(mainApp.frame, "chat_id 불러오기실패");
				} else {
					chat_id = rs.getInt("chat_id");
				}
				// 생성된 chat table의 pk 불러오기 성공(String chat_id)
				if (chat_id == 0) {
					JOptionPane.showMessageDialog(mainApp.frame, "chat_id 불러오기실패");
				} else {
					JOptionPane.showMessageDialog(mainApp.frame, "chat_id 불러옴");
					pstmt = con.prepareStatement(sql_select_RegistMember_no);
					for (int i = 0; i < mainApp.chat_settedMember.size(); i++) {
						pstmt.setString(1, mainApp.chat_settedMember.get(i));
						rs = pstmt.executeQuery();
						if (rs.next() == false) {
							JOptionPane.showMessageDialog(mainApp.frame, "member_no 불러오기실패");
						} else {
							member_idList.add(rs.getInt("member_no"));
						}
					}

//					String sql_insert_chatmember = "insert into chatmember(chat_id, chatmember_id, member_id)"
//							+ " values(?, seq_chatmember.nextval, ?)";
					pstmt = con.prepareStatement(sql_insert_chatmember);
					pstmt.setInt(1, chat_id);
					for (int i = 0; i < member_idList.size(); i++) {
						pstmt.setInt(2, member_idList.get(i));
						int isDone = pstmt.executeUpdate();
						if (isDone == 0) {
							JOptionPane.showMessageDialog(mainApp.frame, "chatmember insert 실패");
							dbManager.close(pstmt, rs);
							break;
						}
					}
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			dbManager.close(pstmt, rs);
		}
	}

	// 좌측하단의 채팅 목록에 리스트 생성하는 메소드
	public void createChatList(JPanel panel, String chatName, int fontSize) {
		Font font = new Font("HY견고딕", Font.BOLD, fontSize);
		JLabel label = new JLabel(chatName, 10);
		JButton Xbutton = new JButton("X");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(font);
		Xbutton.setHorizontalAlignment(SwingConstants.RIGHT);
		Xbutton.setFont(new Font("HY견고딕", Font.BOLD, 10));
		Xbutton.setPreferredSize(new Dimension(30,15));
		
		MouseAdapter chat_m_adapt = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//채팅리스트의 패널 선택시 소켓과 연결.
				if(e.getSource() == e.getComponent()) {
//					System.out.println(label.getText());
					if(mainApp.isSocketConnected == false) {
						mainApp.mainAppChatSocket = new MainAppChatSocket(mainApp);
						mainApp.isSocketConnected = true;
					}
					setCurrentChatVO(label.getText());
					loadChatPanel();
				}
			}
		};
		
		mainApp.chatSmallLabels.add(label);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.add(mainApp.chatSmallLabels.get(mainApp.chatSmallLabels.size() - 1),BorderLayout.CENTER);
		panel1.add(Xbutton,BorderLayout.EAST);
		panel1.setBackground(new Color(64, 64, 64, 60));
		panel1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel1.setPreferredSize(new Dimension(220, 40));
		panel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel1.addMouseListener(chat_m_adapt);
		Xbutton.addActionListener((e)->{
			chatListButtonX(panel1, label, Xbutton, chatName);
		});
		if (mainApp.chatSmallPanels.size() <= 0) {
			panel1.setBounds(0, 0, 240, 40);
		} else {
			panel1.setBounds(mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1).getX(),
					mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1).getY() + 40, 240, 40);
		}
		mainApp.chatSmallPanels.add(panel1);
		mainApp.p_chat_south_center.add(panel1);
		mainApp.p_chat_south_center.setPreferredSize(new Dimension(240, mainApp.chatSmallPanels.size()*45));
		mainApp.p_chat_south_center.updateUI();
		mainApp.p_west_south_chat.updateUI();
	}
	
	//DB조회하여 내가 참여한 방을 확인후 chatList 새로고침
	public void refreshChatList(Connection con) {
		mainApp.p_chat_south_center.removeAll();
		mainApp.chatSmallLabels.clear();
		mainApp.chatSmallPanels.clear();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql_chat_id = "select chat_id from chatmember where member_no = ?";
    	String sql_chat_title = "select chat_title from chat where chat_id = ? and chat_status = ?";
    	ArrayList<Integer> chat_id_list = new ArrayList<Integer>();
    	String title = "";
    	try {
    		pstmt = con.prepareStatement(sql_chat_id);
    		pstmt.setInt(1, mainApp.getRegistMemberVO().getMember_no());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				chat_id_list.add(rs.getInt("chat_id"));
			}
			
			pstmt = con.prepareStatement(sql_chat_title);
			for(int i=0; i<chat_id_list.size();i++) {
				pstmt.setInt(1, chat_id_list.get(i));
				pstmt.setString(2, "1");
				rs = pstmt.executeQuery();
				if(rs.next() != false) {
					title = rs.getString("chat_title");
					createChatList(mainApp.p_chat_south_center, title, 15);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//deprecate 될예정
	
	
	//채팅리스트 패널의 x버튼 메소드, 채팅테이블의 참여 멤버에서 본인을 지움. 그리고 서버에 변경사항을 명령문으로 전달.
	public void chatListButtonX(JPanel panel1, JLabel chat_title_label, JButton Xbutton, String chatName) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		mainApp.p_chat_south_center.remove(Xbutton.getParent());
		mainApp.chatSmallLabels.remove(chat_title_label);
		mainApp.chatSmallPanels.remove(panel1);
		mainApp.p_center_south.setVisible(false);
		mainApp.p_chat_south_center.setPreferredSize(new Dimension(240, mainApp.chatSmallPanels.size()*45));
		mainApp.p_center_center.removeAll();
		mainApp.p_center_center.updateUI();
		mainApp.p_chat_south_center.updateUI();
		
		String sql_getChat_id = "select * from chat where chat_title = '"+chatName+"'";
		String sql = "delete from chatmember where member_no= ? and chat_id = ?";
		int chat_id = 0;
		
		try {
			pstmt = con.prepareStatement(sql_getChat_id);
			rs = pstmt.executeQuery();
			boolean hasNext = rs.next();
			if(hasNext) {
				chat_id = rs.getInt("chat_id");
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mainApp.getRegistMemberVO().getMember_no());
			pstmt.setInt(2, chat_id);
			pstmt.executeUpdate();
			if(isChatMemberNull(chat_id) == 0) {
				String sql_setChatStatus = "update chat set chat_status = '0' where chat_id = "+chat_id;
				pstmt = con.prepareStatement(sql_setChatStatus);
				pstmt.executeUpdate();
			}else if(isChatMemberNull(chat_id) == 1) {
				if(mainApp.isSocketConnected == false) {
					mainApp.mainAppChatSocket = new MainAppChatSocket(mainApp);
					mainApp.isSocketConnected = true;
				}
				if(mainApp.isCnt == false) {
				sendClientInfo(chat_id, mainApp.getRegistMemberVO().getMember_no(), mainApp.getRegistMemberVO().getMember_name());
				mainApp.isCnt = true;
				mainApp.mainAppChatSocket.mainAppchatThread.send("#oneLeft:931006");
				}else if(mainApp.isCnt) {
					mainApp.mainAppChatSocket.mainAppchatThread.send("chatChanged:931006");
					mainApp.isCnt = false;
					sendClientInfo(chat_id, mainApp.getRegistMemberVO().getMember_no(), mainApp.getRegistMemberVO().getMember_name());
					mainApp.isCnt = true;
					mainApp.mainAppChatSocket.mainAppchatThread.send("#oneLeft:931006");
				}
			}else if(isChatMemberNull(chat_id) == 2) {
				if(mainApp.isSocketConnected == false) {
					mainApp.mainAppChatSocket = new MainAppChatSocket(mainApp);
					mainApp.isSocketConnected = true;
				}
				if(mainApp.isCnt == false) {
					sendClientInfo(chat_id, mainApp.getRegistMemberVO().getMember_no(), mainApp.getRegistMemberVO().getMember_name());
					mainApp.isCnt = true;
					mainApp.mainAppChatSocket.mainAppchatThread.send("#outFromChat:931006");
				}else if(mainApp.isCnt) {
					mainApp.mainAppChatSocket.mainAppchatThread.send("chatChanged:931006");
					mainApp.isCnt = false;
					sendClientInfo(chat_id, mainApp.getRegistMemberVO().getMember_no(), mainApp.getRegistMemberVO().getMember_name());
					mainApp.isCnt = true;
					mainApp.mainAppChatSocket.mainAppchatThread.send("#outFromChat:931006");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			dbManager.close(pstmt, rs);
		}
	}
	 
	//해당채팅의 멤버가 존재하는지 확인해서 int 값 0,1,2를 반환 0은 없음, 1은 1명만존재, 2는 2명이상.
	public int isChatMemberNull(int chat_id) {
		String sql = "select * from chatmember where chat_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int isNull = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, chat_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String sql_checkMemberCount = "select count(member_no) as member_count from chatmember where chat_id = "+chat_id;
				pstmt = con.prepareStatement(sql_checkMemberCount);
				rs = pstmt.executeQuery();
				rs.next();
				int member_count = rs.getInt(1);
				if(member_count>1) {
					isNull = 2;
				} else if(member_count == 1) {
					isNull = 1;
				}
			}else {
				isNull = 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.close(pstmt,rs);
		}
		
		return isNull;
		
	}
	
	//채팅패널 선택시 채팅VO와 채팅멤버VO채우는 메소드
	public void setCurrentChatVO(String title) {
		mainApp.chatMemberVOList.clear();
		mainApp.messageVOList.clear();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String chat_title = title;
		int chat_id = 0;
		String sql_select_chat_id = "select * from chat where chat_title = ?";
		String sql_select_chat_member = "select * from chatmember where chat_id = ?";
		String sql_select_message = "select * from message where chat_id = ?";
		try {
			pstmt = con.prepareStatement(sql_select_chat_id);
			pstmt.setString(1, chat_title);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mainApp.chatVO.setChat_id(rs.getInt("chat_id"));
				mainApp.chatVO.setChat_status(rs.getString("chat_status"));
				mainApp.chatVO.setChat_title(rs.getString("chat_title"));
			}
			chat_id = mainApp.chatVO.getChat_id();
			mainApp.frame.setTitle(title);
			
//			System.out.println("현재 채팅의 id는: "+mainApp.chatVO.getChat_id());
			
			pstmt = con.prepareStatement(sql_select_chat_member);
			pstmt.setInt(1, chat_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ChatMemberVO chatMemberVO = new ChatMemberVO();
				chatMemberVO.setChat_id(chat_id);
				chatMemberVO.setChatMember_id(rs.getInt("chatmember_id"));
				chatMemberVO.setMember_no(rs.getInt("member_no"));
				mainApp.chatMemberVOList.add(chatMemberVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.close(pstmt, rs);
		}
	}
	
	
	//VO정보들을 가지고 채팅타이틀과, 채팅참여인원 띄우기. 
	//그리고 현재 채팅의 정보와, 채팅하는사람의 정보 서버소켓에 전달 ","로 구분하도록
	
	//mainApp채팅창 띄우는 메소드
	public void loadChatPanel() {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String sql_select_message_order = "select content from message where chat_id = ? order by message_id desc ";
		String sql_select_message = "select * from message where chat_id = ?";
		Boolean isAlone = false;
		mainApp.chattextArea.setEnabled(true);
		mainApp.p_center_south.setVisible(true);
		mainApp.p_center_south.add(mainApp.chattextArea);
		mainApp.p_center.add(mainApp.p_center_south, BorderLayout.SOUTH);
		if(mainApp.isCnt) {
			mainApp.mainAppChatSocket.mainAppchatThread.send("chatChanged:931006");
			mainApp.isCnt = false;
		}
		int current_chat_id = mainApp.chatVO.getChat_id();
		if(mainApp.loadFlag) {
			mainApp.chatPanel = new ChatPanel();
			mainApp.chatPanel.p_north_chat_title.setText("");
			mainApp.chatPanel.p_north_chat_member_panel.removeAll();
			mainApp.loadFlag = false;
		}else {
			mainApp.p_center_center.removeAll();
			mainApp.chatPanel = new ChatPanel();
			mainApp.chatPanel.p_north_chat_title.setText("");
			mainApp.chatPanel.p_north_chat_member_panel.removeAll();
		}
		ArrayList<String> chatMemberName = new ArrayList<String>();
		ArrayList<Integer> chatMemberNo = new ArrayList<Integer>();
		for (int i = 0; i < mainApp.chatMemberVOList.size(); i++) {
			int memberno = mainApp.chatMemberVOList.get(i).getMember_no();
			chatMemberNo.add(memberno);
		}
		chatMemberName = changeMemberNotoNameList(chatMemberNo);
		for (int i = 0; i < chatMemberName.size(); i++) {
			JLabel membernameLabel = new JLabel(chatMemberName.get(i));
			membernameLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
			membernameLabel.setForeground(Color.WHITE);
			membernameLabel.setPreferredSize(new Dimension(55, 60));
			mainApp.chatPanel.p_north_chat_member_panel.add(membernameLabel);
		}
		mainApp.chatPanel.p_north_chat_title.setText(mainApp.chatVO.getChat_title());
		mainApp.p_chat = mainApp.chatPanel.p_chat;
		mainApp.chat_panel = mainApp.chatPanel.getChatPanel();
		mainApp.chat_scroll = mainApp.chatPanel.scrollPane; 
		mainApp.p_center_center.add(mainApp.chat_panel, BorderLayout.CENTER);
		
		String chat_id = Integer.toString(mainApp.chatVO.getChat_id());
		String member_no = Integer.toString(mainApp.getRegistMemberVO().getMember_no());
		String current_member_name = mainApp.getRegistMemberVO().getMember_name();
		String client_info = chat_id+","+member_no+","+current_member_name;
		
		if(isChatMemberNull(current_chat_id) == 1) {
			isAlone = true;
		}
		//서버로 정보보내기
		if(mainApp.isCnt == false) {
			mainApp.mainAppChatSocket.mainAppchatThread.send(client_info);
			mainApp.isCnt = true;
		}
		try {
			pstmt = con.prepareStatement(sql_select_message);
			pstmt.setInt(1, mainApp.chatVO.getChat_id());
			rs = pstmt.executeQuery();
				while(rs.next()) {
					MessageVO messageVO = new MessageVO();
					messageVO.setChat_id(mainApp.chatVO.getChat_id());
					messageVO.setMember_no(rs.getInt("member_no"));
					mainApp.gotChatMemberName = changeMemberNotoName(rs.getInt("member_no"));
					messageVO.setMessage_id(rs.getInt("message_id"));
					messageVO.setChat_time(rs.getString("chat_time"));
					messageVO.setContent(rs.getString("content"));
					System.out.println(rs.getString("content"));
					
					String[] decryptN = messageVO.getContent().split("#n:931006");
					String decryptNcontent = "";
					StringBuffer sb = new StringBuffer();
					for(int i =0; i<decryptN.length; i++) {
						if(decryptN[i] != null) {
							sb.append(decryptN[i]+"\n");
						}
					}
					decryptNcontent = sb.toString();
					mainApp.messageVOList.add(messageVO);
					insertMyChat(decryptNcontent, messageVO.getChat_time(), messageVO.getMember_no());
			}
			if(isAlone) {
				mainApp.chatPanel.p_north_chat_member_panel.removeAll();
				JLabel membernameLabel = new JLabel("참여자가 없는 방입니다.");
				membernameLabel.setFont(new Font("HY견고딕", Font.PLAIN, 20));
				membernameLabel.setForeground(Color.WHITE);
				membernameLabel.setPreferredSize(new Dimension(400, 60));
				mainApp.chatPanel.p_north_chat_member_panel.add(membernameLabel);
				mainApp.chattextArea.setEnabled(false);
			}
			mainApp.p_chat.updateUI();
			mainApp.p_center_center.updateUI();
			mainApp.p_center_south.updateUI();
			mainApp.p_center.updateUI();
			mainApp.chat_panel.updateUI();
			mainApp.p_center_center.updateUI();
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.close(pstmt,rs);
		}
	}
	
	//deprecate 될 예정
	public void insertMessageDB(String content) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into message(chat_id, message_id, content, chat_time, member_no)";
		sql += " values(?, seq_message.nextval, ?, ?, ?)";
		
		SimpleDateFormat date_format = new SimpleDateFormat ( "MM월dd일 HH시mm분ss초");
		String current_time = date_format.format (System.currentTimeMillis());
		
		try {
			pstmt = mainApp.con.prepareStatement(sql);
			pstmt.setInt(1, mainApp.chatVO.getChat_id());
			pstmt.setString(2, content);
			pstmt.setString(3, current_time);
			pstmt.setInt(4, mainApp.getRegistMemberVO().getMember_no());
			int isDone = pstmt.executeUpdate();
			if(isDone==0) {
				JOptionPane.showMessageDialog(mainApp.frame, "입력실패!");
			}else {
				JOptionPane.showMessageDialog(mainApp.frame, "입력성공!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//멤버no를 멤버name으로 바꿔줌
	public String changeMemberNotoName(int member_no) {
		String member_name = "";
		String sql = "select member_name from registmember where member_no = "+member_no;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member_name = rs.getString("member_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member_name;
	}
	
	//멤버no가 담긴 arrayList를 넘기면 멤버Name리스트로 바꿔줌
	public ArrayList<String> changeMemberNotoNameList(ArrayList<Integer> chatmemberno) {
		String sql = "select member_name from registmember where member_no = ?";
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<String> member_nameList = new ArrayList<String>();
		try {
			pstmt = con.prepareStatement(sql);
			for(int i=0; i<chatmemberno.size();i++) {
				pstmt.setInt(1, chatmemberno.get(i));
				rs = pstmt.executeQuery();
				if(rs.next()) {
					member_nameList.add(rs.getString("member_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member_nameList;
	}

	// 로그인 하고, mainApp의 현재 RegistMemberVO의member_no와모든 chatmember의 member_no 와 비교하여
	// 중복되는게 있다면 true 없으면 false
	public boolean chatComparingUser() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean hasChat = false;
		String sql = "select chat_id from chatmember where member_no = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mainApp.getRegistMemberVO().getMember_no());
			rs = pstmt.executeQuery();
			hasChat = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hasChat;
	}
	
	
	//새로운 채팅이 생겼다는 메시지를 서버가 수신했을때,
	//현재 로그인한 멤버의pk를 DB내에서 해당메시지가 가지고있는 chatPK로 chatMember에서검색하여
	//내가 초대되 있는방인지 아닌지를 확인하는 메소드
	public boolean memberComparing(int chat_id, int member_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean hasMe = false;
		String sql = "select * from chatmember where chat_id = "+chat_id+" and member_no ="+member_no;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				hasMe = true;
			}else {
				hasMe = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.close(pstmt,rs);
		}
		return hasMe;
	}
	
	//소켓으로 채팅방에관한 명령문 전달시, 해당 채팅방의 정보를 전달하는 메소드
	public void sendClientInfo(int chat_id, int member_no, String member_name ) {
		String clientInfo = chat_id+","+member_no+","+member_name;
		mainApp.mainAppChatSocket.mainAppchatThread.send(clientInfo);
		System.out.println("sendClientInfo 발동 인포는 : "+clientInfo);
	}

	// 채팅생성시팝업패널에 현재 회원목록을 보여주는 체크박스 생성 메소드
	public void createPopPanelCheckBox() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> nameList = new ArrayList<String>();

		String sql = "select member_name from registmember";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if(mainApp.getRegistMemberVO().getMember_name().equals(rs.getString("member_name"))==false) {
					nameList.add(rs.getString("member_name"));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			dbManager.close(pstmt, rs);
		}

		for (int i = 0; i < nameList.size(); i++) {
			JCheckBox checkBoxUser = new JCheckBox(nameList.get(i), false);
			checkBoxUser.setPreferredSize(new Dimension(160, 25));
			checkBoxUser.setFont(new Font("HY견고딕", Font.PLAIN, 13));
			checkBoxUser.setBackground(Color.GRAY);
			mainApp.chatCheckBoxList.add(checkBoxUser);
			mainApp.p_chat_set_pop_checkPanel.add(checkBoxUser);
			checkBoxUser.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						int x = mainApp.frame.getLocationOnScreen().x;
						int y = mainApp.frame.getLocationOnScreen().y;
						JCheckBox box = (JCheckBox) e.getItem();
						chatPopAddappendLabel(box.getText());
						mainApp.popup_ch_add.hide();
						mainApp.popup_ch_add = mainApp.popupFactory.getPopup(mainApp.frame, mainApp.p_chat_set_pop,
								x + 415, y + 50);
						mainApp.popup_ch_add.show();
						mainApp.p_chat_set_pop_add_panel.updateUI();
						mainApp.p_chat_set_pop_add_container.updateUI();
					} else if (e.getStateChange() == ItemEvent.DESELECTED) {
						JCheckBox box = (JCheckBox) e.getItem();
						int x = mainApp.frame.getLocationOnScreen().x;
						int y = mainApp.frame.getLocationOnScreen().y;
						mainApp.popup_ch_add.hide();
						for (int i = 0; i < mainApp.p_chat_set_pop_add_panel.getComponentCount(); i++) {
							JLabel label = (JLabel) mainApp.p_chat_set_pop_add_panel.getComponent(i);
							if (box.getText().equals(label.getText())) {
								mainApp.p_chat_set_pop_add_panel.remove(i);
								mainApp.chatPopAddLabels.remove(i);
							} 
						}
						mainApp.popup_ch_add = mainApp.popupFactory.getPopup(mainApp.frame, mainApp.p_chat_set_pop,
								x + 415, y + 50);
						mainApp.popup_ch_add.show();
						mainApp.p_chat_set_pop_add_panel.updateUI();
						mainApp.p_chat_set_pop_add_container.updateUI();
					}
				}
			});
		}
	}
	
	//채팅참여인원 체크박스 체크시, 선택한 인원이 오른쪽 패널에 붙게하는 메소드 
	public void chatPopAddappendLabel(String name) {
			mainApp.chat_settedMember.add(name);
			JLabel selectedName = new JLabel(name);
			selectedName.setFont(new Font("HY견고딕", Font.PLAIN, 25));
			selectedName.setPreferredSize(new Dimension(180, 30));
			mainApp.chatPopAddLabels.add(selectedName);
			mainApp.p_chat_set_pop_add_panel.add(selectedName);
	}
	
	//메시지의 #n:931006을 기점으로 \n으로 치환하는 메소드
	public String seperateTextLine() {
		StringReader result = new StringReader(mainApp.chattextArea.getText());
		BufferedReader br = new BufferedReader(result);
		StringBuffer sb = new StringBuffer();

		if (mainApp.chattextArea.getLineCount() != 0) {
			for (int i = 0; i < mainApp.chattextArea.getLineCount(); i++) {
				try {
					sb.append(br.readLine()+"#n:931006");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	//채팅창 업로드시 채팅창에 채팅말풍선 패널 생성 메소드
	public void insertMyChat(String msg, String chat_time, int member_no) {
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
		int chatTextAreaHeight = 15 * lineCount;
		chatTextArea.setPreferredSize(new Dimension(480, chatTextAreaHeight));

		JPanel userPanel = new JPanel();
		userPanel.setBorder(null);
		userPanel.setBackground(Color.DARK_GRAY);
		userPanel.setLayout(null);

		if (mainApp.getRegistMemberVO().getMember_no() == member_no) {
			userPanel.setBounds(0, 0, 157, 78);
			userPanel.setPreferredSize(new Dimension(157, 78));
			chatTextArea.setBounds(185, 0, 480, chatTextAreaHeight);
			aChatPanel.add(userPanel, BorderLayout.WEST);
			panel.myNameLabel.setText(mainApp.getRegistMemberVO().getMember_name());
			chatTextArea.setBackground(new Color(62, 179, 181));
		} else if(mainApp.getRegistMemberVO().getMember_no() != member_no) {
			panel.myChatTimeLabel.setBounds(12, 26, 74, 29);
			panel.myRankLabel.setBounds(57, 8, 53, 29);
			panel.myNameLabel.setBounds(12, 0, 53, 40);
			panel.myImagLabel.setBounds(100, 0, 48, 79);
			panel.myNameLabel.setText(mainApp.gotChatMemberName);
//			userPanel.setBounds(694, 0, 157, 78);
			userPanel.setPreferredSize(new Dimension(157, 78));
			chatTextArea.setBounds(230, 0, 480, chatTextAreaHeight);
			aChatPanel.add(userPanel, BorderLayout.EAST);
		}
		panel.myChatTimeLabel.setText(chat_time);
		userPanel.add(panel.myImagLabel);
		userPanel.add(panel.myChatTimeLabel);
		userPanel.add(panel.myRankLabel);
		userPanel.add(panel.myNameLabel);

		aChatPanel.setPreferredSize(new Dimension(875, chatTextAreaHeight + 30));
		aChatPanel.setBorder(null);
		aChatPanel.setBackground(Color.DARK_GRAY);
		aChatPanel.add(chatTextArea);
		aChatPanel.add(userPanel);

		mainApp.p_chat.setPreferredSize(new Dimension(300, mainApp.messageVOList.size() * 105));
		mainApp.p_chat.add(aChatPanel);
		
		
		Runnable doScroll = new Runnable() {
			   public void run() {
				   	mainApp.chat_scroll.getVerticalScrollBar().setValue(0);
			   }
			  };
			  SwingUtilities.invokeLater(doScroll);

		mainApp.p_chat.updateUI();
		mainApp.p_center.updateUI();
	}

}