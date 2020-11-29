package layout;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HeaderIssue {
	MainApp mainApp;
	int board_id,board_group_id;
	String title, content, username;

	public HeaderIssue(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void selectBoard() {
		String sql = "SELECT * FROM BOARD WHERE BOARD_ID = ";
		sql += "(SELECT MAX(BOARD_ID) FROM BOARD)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = mainApp.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board_id = rs.getInt("board_id");
				board_group_id = rs.getInt("board_group_id");
				title = rs.getString("board_title");
				content = rs.getString("board_content");
				username = rs.getString("board_username");
				mainApp.la_boardIssue.setText(rs.getString("board_title"));
				mainApp.la_boardIssue.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainApp.dbManager.close(pstmt, rs);
		}		
		
		mainApp.la_boardIssue.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				mainApp.p_center_center.removeAll();
				BoardMain boardMain = new BoardMain(mainApp, board_group_id);
				boardMain.boardModule.headerBoardShow(board_id, title, content, username);
			}
		});
	}
}
