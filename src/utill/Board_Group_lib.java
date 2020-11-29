package utill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import layout.BoardMain;
import layout.MainApp;
import models.BoardGroupVO;

public class Board_Group_lib {
	MainApp mainApp;
	ArrayList<BoardGroupVO> boardGroupList = new ArrayList<BoardGroupVO>();
	ArrayList<JPanel> boardPanels = new ArrayList<JPanel>();
	ArrayList<JLabel> boardPanellabels = new ArrayList<JLabel>();
	
	public Board_Group_lib(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void addBoardGroup() {
		String sql = "insert into board_group values(board_group_seq.nextval, ?, ?, to_char(sysdate, 'yyyy-mm-dd hh24:mm:ss'), 0)";
		PreparedStatement pstmt = null;

		try {
			pstmt = mainApp.con.prepareStatement(sql);
			pstmt.setString(1, mainApp.t_board_pop_name.getText());
			pstmt.setString(2, mainApp.getRegistMemberVO().getMember_id());

			int result = pstmt.executeUpdate();

			if (result == 0) {
				JOptionPane.showMessageDialog(mainApp.frame, "등록 실패");
			} else {
				JOptionPane.showMessageDialog(mainApp.frame, "등록 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainApp.dbManager.close(pstmt);
		}
	}
	
	public void selectBoardGroup() {
		boardGroupList.removeAll(boardGroupList);
		boardPanellabels.removeAll(boardPanellabels);
		boardPanels.removeAll(boardPanels);
		mainApp.p_west_list.removeAll();
		String sql = "select * from board_group where board_group_status = 0 order by board_group_id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = mainApp.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardGroupVO vo = new BoardGroupVO();
				vo.setBoard_group_id(rs.getInt("board_group_id"));
				vo.setBoard_group_title(rs.getString("board_group_title"));
				vo.setBoard_group_username(rs.getString("board_group_username"));
				vo.setBoard_group_wtime(rs.getString("board_group_wtime"));
				vo.setBoard_group_status(rs.getString("board_group_status"));
				
				boardGroupList.add(vo);
			}
			
			for(int i = 0; i < boardGroupList.size(); i++) {
				createBoardList(mainApp.p_west_list, boardGroupList.get(i).getBoard_group_id(),boardGroupList.get(i).getBoard_group_title(), 15);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			mainApp.dbManager.close(pstmt, rs);
		}
	}
	/*---------------------------------------------------
	* 게시판 그룹 패널 생성메서드
	---------------------------------------------------*/
	public void createBoardList(JPanel panel, int board_group_id,String folderName, int fontSize) {
		Font font = new Font("HY견고딕", Font.BOLD, fontSize);
		JLabel label = new JLabel(folderName, 10);
		label.setBounds(30, 0, 240, 40);
		label.setFont(font);
		boardPanellabels.add(label);
		JPanel panel1 = new JPanel();
		panel1.add(boardPanellabels.get(boardPanellabels.size() - 1), BorderLayout.WEST);
		panel1.setBackground(new Color(64, 64, 64, 60));
		panel1.setPreferredSize(new Dimension(240, 40));
		panel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel1.setLayout(null);
		
		if (boardPanels.size() <= 0) {
			panel1.setBounds(0, 0, 240, 40);
		} else {
			panel1.setBounds(boardPanels.get(boardPanels.size() - 1).getX(),
					boardPanels.get(boardPanels.size() - 1).getY() + 40, 240, 40);
		}
		boardPanels.add(panel1);

		panel.add(boardPanels.get(boardPanels.size() - 1));
		if ((boardPanels.size() * 40) > panel.getWidth()) {
			panel.setPreferredSize(new Dimension(240, boardPanels.size() * 40));
		}
		
		panel1.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				mainApp.p_center_center.removeAll();
				mainApp.p_center_south.setVisible(false);
				mainApp.p_center.updateUI();
				new BoardMain(mainApp, board_group_id);
			}
		});
		panel.updateUI();
	}
}
