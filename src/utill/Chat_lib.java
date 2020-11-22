package utill;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import layout.MainApp;

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
	Connection con;
	MainApp mainApp;

	public Chat_lib(MainApp mainApp) {
		this.dbManager = new DBManager();
		this.mainApp = mainApp;
		this.con = mainApp.con;
	}
	
	
	//채팅 목록에 리스트생성시 확인버튼누르면 chat, chatmember에 db입력메소드
	public void insertChatTable() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int chat_id = 0;

		// chat 테이블에 작성
		String sql_insert_chat = "insert into chat(chat_id, chat_title, chat_status)";
		sql_insert_chat += " values(chat_seq.nextval, ?, '1')";

		// chat 티이블의 pk 얻어오기
		String sql_select_pk_chat = "select chat_id from chat where chat_title= ?";
		// t_chat_pop_name.getText()

		// chatmember에 chat 테이블의 pk 넣기
		String sql_insert_chatmember = "insert into chatmember(chat_id, chatmember_id, member)"
				+ " values(?, chatmember_seq.nextval, ?)";

		try {
			pstmt = con.prepareStatement(sql_insert_chat);
			pstmt.setString(1, mainApp.t_chat_pop_name.getText());
			int isExecute = pstmt.executeUpdate();
			pstmt.close();
			if (isExecute == 0) {
				JOptionPane.showMessageDialog(mainApp.frame, "chat insert 쿼리실패");
			} else {
				JOptionPane.showMessageDialog(mainApp.frame, "chat insert 쿼리성공");
				pstmt = con.prepareStatement(sql_select_pk_chat);
				pstmt.setString(1, mainApp.t_chat_pop_name.getText());
				rs = pstmt.executeQuery();
				rs.next();
				chat_id = rs.getInt("chat_id");
				// 생성된 chat table의 pk 불러오기 성공(String chat_id)
				if (chat_id == 0) {
					JOptionPane.showMessageDialog(mainApp.frame, "chat_id 불러오기실패");
				} else {

					JOptionPane.showMessageDialog(mainApp.frame, "chat_id 불러옴");
					pstmt = con.prepareStatement(sql_insert_chatmember);
					pstmt.setInt(1, chat_id);
					for (int i = 0; i < mainApp.chat_settedMember.size(); i++) {
						pstmt.setString(2, mainApp.chat_settedMember.get(i));
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

	//좌측하단의 채팅 목록에 리스트 생성하는 메소드
	public void createChatList(JPanel panel, String folderName, int fontSize) {
		Font font = new Font("HY견고딕", Font.BOLD, fontSize);
		JLabel label = new JLabel(folderName, 10);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(font);
		mainApp.chatSmallLabels.add(label);
		JPanel panel1 = new JPanel();
		panel1.add(mainApp.chatSmallLabels.get(mainApp.chatSmallLabels.size() - 1));
		panel1.setBackground(new Color(0, 0, 0, 60));
		panel1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel1.setPreferredSize(new Dimension(240, 40));
		panel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		MouseAdapter chat_m_adapt = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
//					System.out.println("들");
			}

			@Override
			public void mouseExited(MouseEvent e) {
//					System.out.println("나");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println(label.getText());
				
			}
		};

		if (mainApp.chatSmallPanels.size() <= 0) {
			panel1.setBounds(0, 0, 240, 40);
		} else {
			panel1.setBounds(mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1).getX(),
					mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1).getY() + 40, 240, 40);
		}

		mainApp.chatSmallPanels.add(panel1);
		for (int i = 0; i < mainApp.chatSmallPanels.size(); i++) {
			mainApp.chatSmallPanels.get(i).addMouseListener(chat_m_adapt);
		}
		panel.add(mainApp.chatSmallPanels.get(mainApp.chatSmallPanels.size() - 1));
		panel.setPreferredSize(new Dimension(240, panel.getHeight() + 40));
		panel.updateUI();
		mainApp.p_west_south_chat.updateUI();
	}

	
	
}
