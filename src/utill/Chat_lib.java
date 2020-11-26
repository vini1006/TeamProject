package utill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.border.LineBorder;

import layout.ChatPanel;
import layout.MainApp;
import models.ChatMemberVO;
import models.ChatVO;
import models.MessageVO;
import socket.MainAppChatSocket;

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

	// 채팅 목록에 리스트생성시 확인버튼누르면 chat, chatmember에 db입력메소드
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
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			panel.remove(Xbutton.getParent());
			mainApp.chatSmallLabels.remove(label);
			mainApp.chatSmallPanels.remove(panel1);
			panel.updateUI();
//			String sql = "update chat set chat_status='0' where chat_title = ?";
			String sql_getChat_id = "select * from chat where chat_title = '"+chatName+"'";
			String sql = "delete from chatmember where member_no= ? and chat_id = ?";
			try {
				pstmt = con.prepareStatement(sql_getChat_id);
				rs = pstmt.executeQuery();
				boolean hasNext = rs.next();
				int chat_id = 0;
				if(hasNext) {
					chat_id = rs.getInt("chat_id");
				}
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, mainApp.getRegistMemberVO().getMember_no());
				pstmt.setInt(2, chat_id);
				int isDone = pstmt.executeUpdate();
				if(isDone == 0 ) {
					System.out.println("chatMember에서 본인삭제실패");
				}else {
					System.out.println("chatMember에서 본인삭제완료");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				dbManager.close(pstmt, rs);
			}
		});

		if (mainApp.chatSmallPanels.size() <= 0) {
			panel1.setBounds(0, 0, 240, 40);
		} else {
			panel1.setBounds(mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1).getX(),
					mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1).getY() + 40, 240, 40);
		}

		mainApp.chatSmallPanels.add(panel1);
		panel.add(mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1));
		panel.setPreferredSize(new Dimension(240, panel.getHeight() + 40));
		panel.updateUI();
		mainApp.p_west_south_chat.updateUI();
	}
	
	
	//채팅패널 선택시 채팅창 센터에 띄우기.
	public void setCurrentChatVO(String title) {
		mainApp.messageVOList.clear();
		mainApp.chatMemberVOList.clear();
		mainApp.messageVOList.clear();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String chat_title = title;
		int chat_id = 0;
		String sql_select_chat_id = "select * from chat where chat_title = ?";
		String sql_select_chat_member = "select * from chatmember where chat_id = ?";
		String sql_select_message = "select * from message where chat_id = ?";
		String sql_select_message_order = "select content from message order by message_id desc";
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
			
			pstmt = con.prepareStatement(sql_select_message);
			pstmt.setInt(1, chat_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				while(rs.next()) {
					MessageVO messageVO = new MessageVO();
					messageVO.setChat_id(chat_id);
					messageVO.setMember_no(rs.getInt("member_no"));
					messageVO.setMessage_id(rs.getInt("message_id"));
					messageVO.setChat_time(rs.getString("chat_time"));
					messageVO.setContent(rs.getString("content"));
					mainApp.messageVOList.add(messageVO);
				}
				
			}else {
				JOptionPane.showMessageDialog(mainApp.frame, "채팅 로그 없음.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.close(pstmt, rs);
		}
	}
	
	
	//VO정보들을 가지고 채팅타이틀과, 채팅참여인원 띄우기. 
	//그리고 현재 채팅의 정보와, 채팅하는사람의 정보 서버소켓에 전달 ","로 구분하도록
	
	public void loadChatPanel() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql_select_message_order = "select content from message order by message_id desc";
		if(mainApp.loadFlag) {
			mainApp.chatPanel = new ChatPanel();
			mainApp.chatPanel.p_north_chat_title.setText("");
			mainApp.chatPanel.p_north_chat_member_panel.removeAll();
			
			ArrayList<String> chatMemberName = new ArrayList<String>();
			ArrayList<Integer> chatMemberNo = new ArrayList<Integer>();
			for (int i = 0; i < mainApp.chatMemberVOList.size(); i++) {
				int memberno = mainApp.chatMemberVOList.get(i).getMember_no();
				chatMemberNo.add(memberno);
			}
			chatMemberName = changeMemberNotoName(chatMemberNo);
			for (int i = 0; i < chatMemberName.size(); i++) {
				JLabel membernameLabel = new JLabel(chatMemberName.get(i));
				membernameLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));
				membernameLabel.setForeground(Color.WHITE);
				membernameLabel.setPreferredSize(new Dimension(55, 60));
				mainApp.chatPanel.p_north_chat_member_panel.add(membernameLabel);
			}
			mainApp.chatPanel.p_north_chat_title.setText(mainApp.chatVO.getChat_title());
			mainApp.p_chat = mainApp.chatPanel.p_chat;
			mainApp.chat_panel = mainApp.chatPanel.getChatPanel();
			mainApp.p_center_center.add(mainApp.chat_panel, BorderLayout.CENTER);
			mainApp.chat_panel.updateUI();
			mainApp.p_center_center.updateUI();
			mainApp.loadFlag = false;
			String chat_id = Integer.toString(mainApp.chatVO.getChat_id());
			String member_no = Integer.toString(mainApp.getRegistMemberVO().getMember_no());
			String client_info = chat_id+","+member_no;
			mainApp.mainAppChatSocket.mainAppchatThread.send(client_info);
		}else {
			mainApp.mainAppChatSocket.mainAppchatThread.send("chatChanged:931006");
			mainApp.p_center_center.removeAll();
			mainApp.chatPanel = new ChatPanel();
			mainApp.chatPanel.p_north_chat_title.setText("");
			mainApp.chatPanel.p_north_chat_member_panel.removeAll();
			
			ArrayList<String> chatMemberName = new ArrayList<String>();
			ArrayList<Integer> chatMemberNo = new ArrayList<Integer>();
			for (int i = 0; i < mainApp.chatMemberVOList.size(); i++) {
				int memberno = mainApp.chatMemberVOList.get(i).getMember_no();
				chatMemberNo.add(memberno);
			}
			chatMemberName = changeMemberNotoName(chatMemberNo);
			for (int i = 0; i < chatMemberName.size(); i++) {
				JLabel membernameLabel = new JLabel(chatMemberName.get(i));
				membernameLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));
				membernameLabel.setForeground(Color.WHITE);
				membernameLabel.setPreferredSize(new Dimension(55, 60));
				mainApp.chatPanel.p_north_chat_member_panel.add(membernameLabel);
			}
			mainApp.chatPanel.p_north_chat_title.setText(mainApp.chatVO.getChat_title());
			mainApp.p_chat = mainApp.chatPanel.p_chat;
			mainApp.chat_panel = mainApp.chatPanel.getChatPanel();
			mainApp.p_center_center.add(mainApp.chat_panel, BorderLayout.CENTER);
			mainApp.chat_panel.updateUI();
			mainApp.p_center_center.updateUI();
			String chat_id = Integer.toString(mainApp.chatVO.getChat_id());
			String member_no = Integer.toString(mainApp.getRegistMemberVO().getMember_no());
			String client_info = chat_id+","+member_no;
			mainApp.mainAppChatSocket.mainAppchatThread.send(client_info);
		}
		try {
			pstmt = con.prepareStatement(sql_select_message_order);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				insertMyChat(rs.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	public void insertMyChat(){
		String msg = mainApp.chatTextArea.getText();
		JTextArea chatTextArea = new JTextArea();
		if(mainApp.messageVOList.size() <= 0) {
			chatTextArea = new JTextArea();
			chatTextArea.setFont(new Font("HY견고딕", Font.PLAIN, 16));
			chatTextArea.setForeground(Color.WHITE);
			chatTextArea.setAlignmentX(3.0f);
			chatTextArea.setAlignmentY(3.0f);
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
			chatTextArea.setAlignmentX(3.0f);
			chatTextArea.setAlignmentY(3.0f);
			chatTextArea.setBackground(SystemColor.activeCaption);
			chatTextArea.setEditable(false);
			chatTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			chatTextArea.setBorder(new LineBorder(SystemColor.activeCaption, 2, true));
			chatTextArea.setLineWrap(true);
			chatTextArea.setText(msg);
			chatTextArea.setBounds(200, mainApp.messageVOList.size()*80+20, 480, 80);
			mainApp.p_chat.add(chatTextArea);
			mainApp.p_center.updateUI();
		}
	}
	*/
	
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
	
	//멤버no가 담긴 arrayList를 넘기면 멤버Name리스트로 바꿔줌
	public ArrayList<String> changeMemberNotoName(ArrayList<Integer> chatmemberno) {
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
			selectedName.setFont(new Font("HY견고딕", Font.PLAIN, 14));
			selectedName.setPreferredSize(new Dimension(180, 30));
			mainApp.chatPopAddLabels.add(selectedName);
			mainApp.p_chat_set_pop_add_panel.add(selectedName);
	}
	
	public String seperateTextLine() {
		StringReader result = new StringReader(mainApp.textArea.getText());
		BufferedReader br = new BufferedReader(result);
		StringBuffer sb = new StringBuffer();

		if (mainApp.textArea.getLineCount() != 0) {
			for (int i = 0; i < mainApp.textArea.getLineCount(); i++) {
				try {
					sb.append(br.readLine()+"#n:931006");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
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
