package utill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import layout.BoardMain;
import models.BoardVO;
import models.cmmtVO;

public class BoardModule {
   BoardMain boardMain;
   
   //게시판 그룹 패널을 담아둘 컬렉션 프레임워크 선언
   
   // boardVO를 담아둘 컬렉션 프레임워크 선언
   ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
   // 게시판 리스트 패널을 담아둘 컬렉션 프레임워크 선언
   ArrayList<JPanel> panelList = new ArrayList<JPanel>();
   // 게시판 라벨 출력을 위해 담아둘 컬렉션 프레임워크 선언
   ArrayList<JLabel> labelListNum = new ArrayList<JLabel>();
   ArrayList<JLabel> labelListTitle = new ArrayList<JLabel>();
   ArrayList<JLabel> labelListUser = new ArrayList<JLabel>();
   ArrayList<JLabel> labelListTime = new ArrayList<JLabel>();
   ArrayList<JLabel> labelListHit = new ArrayList<JLabel>();

   // cmmtVO를 담아둘 컬렉션 프레임워크 선언
   ArrayList<cmmtVO> cmmtList = new ArrayList<cmmtVO>();
   // 댓글 리스트 패널을 담아둘 컬렉션 프레임워크 선언
   ArrayList<JPanel> cmmtPanelList = new ArrayList<JPanel>();
   // 댓글 라벨 출력을 위해 담아둘 컬렉션 프레임 워크 선언
   ArrayList<JLabel> cmmtLabelListUser = new ArrayList<JLabel>();
   ArrayList<JLabel> cmmtLabelListTime = new ArrayList<JLabel>();
   ArrayList<JTextArea> cmmtLabelListContent = new ArrayList<JTextArea>();
   
   //IP를 받아올 변수
   InetAddress local;
   
   JLabel delCmmt;
   
   //페이지 인덱스를 만들기위해 선언
   public static int PAGECOUNT = 0, ALLCOUNT = 0, PAGECOUNTSPLIT = 0, PAGECOUNTSPLITNUM = 10;
   
   public BoardModule(BoardMain boardMain) {
      this.boardMain = boardMain;
   }


   
   // 페이지수 이전으로 넘어갈때 .. 삭제하고 다시 만들어줌..
   public void delPageLabelPrev() {
      boardMain.boardPenel_north.remove(boardMain.labelPagePrev);
      boardMain.boardPenel_north.remove(boardMain.labelPageNext);
      for (int i = boardMain.pageForINum; i < PAGECOUNTSPLITNUM; i++) {
         boardMain.boardPenel_north.remove(boardMain.labelPageIndex.get(i));
      }
      for (int i = boardMain.pageForINum; i < boardMain.labelPageIndex.size();) {
         boardMain.labelPageIndex.remove(boardMain.pageForINum);
      }
      boardMain.boardPenel_north.updateUI();
   }

   // 페이지수 10번째에서 11번째로 넘어갈때.. 삭제하고 다시 만들어줌..
   public void delPageLabelNext() {
      boardMain.boardPenel_north.remove(boardMain.labelPagePrev);
      boardMain.boardPenel_north.remove(boardMain.labelPageNext);
      for (int i = 0; i < PAGECOUNTSPLITNUM; i++) {
         boardMain.boardPenel_north.remove(boardMain.labelPageIndex.get(i));
      }
      boardMain.boardPenel_north.updateUI();
   }

   // 페이지 목록을 선택했을때..
   public void pageSelect() {
      boardMain.end_pageNum = (boardMain.pagenum + 1) * boardMain.GRIDCOUNT;
      boardMain.start_pageNum = boardMain.end_pageNum - 9;
      selectBoard();
      boardMain.boardPanel_center_content.updateUI();
   }

   // board를 조회한다 !
   public void selectBoard() {
      // 게시판 조회된 목록을 다 지운 뒤 조회 한다.
      boardList.clear();

      String sql = "select * from (SELECT rownum num, aa.*FROM  ";
      sql += " ( SELECT * from board WHERE BOARD_STATUS  = 0 AND BOARD_GROUP_ID  =  ? ORDER BY board_id desc) aa ) ";
      sql += " WHERE num >= ? AND num <= ? ";
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setInt(1, boardMain.board_group_id);
         pstmt.setInt(2, boardMain.start_pageNum);
         pstmt.setInt(3, boardMain.end_pageNum);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            BoardVO vo = new BoardVO();

            vo.setBoard_id(rs.getInt("board_id"));
            vo.setBoard_title(rs.getString("board_title"));
            vo.setBoard_content(rs.getString("board_content"));
            vo.setBoard_username(rs.getString("board_username"));
            vo.setBoard_ip(rs.getString("board_ip"));
            vo.setBoard_wtime(rs.getString("board_wtime"));
            vo.setBoard_status(rs.getString("board_status"));
            vo.setBoard_comment_count(rs.getInt("board_comment_count"));
            vo.setBoard_hit(rs.getInt("board_hit"));

            boardList.add(vo);
         }
         ;

         // board에 패널추가 method 호출.
         addBoardPanel();

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt, rs);
      }
   }

   // 글쓰기 등록 시 board content에 패널추가.
   public void addBoardPanel() {
      boardMain.boardPanel_center_content.removeAll();
      panelList.removeAll(panelList);
      labelListNum.removeAll(labelListNum);
      labelListTitle.removeAll(labelListTitle);
      labelListUser.removeAll(labelListUser);
      labelListTime.removeAll(labelListTime);
      labelListHit.removeAll(labelListHit);

      for (int i = 0; i < boardList.size(); i++) {
         // 해당 num값을 받아놓는다.. 이벤트 처리를 위하여..
         int num = i;

         // 패널 생성
         panelList.add(new JPanel());
         panelList.get(i).setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
         panelList.get(i).setBackground(Color.DARK_GRAY);
         panelList.get(i).setLayout(null);
         boardMain.boardPanel_center_content.add(panelList.get(i));
         panelList.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

         // 글 번호 라벨
         labelListNum.add(new JLabel(Integer.toString((ALLCOUNT - ((boardMain.pagenum * boardMain.GRIDCOUNT) + i)))));
         labelListNum.get(i).setForeground(Color.WHITE);
         labelListNum.get(i).setFont(new Font("HY견고딕", Font.PLAIN, 12));
         labelListNum.get(i).setBounds(38, 20, 57, 15);
         panelList.get(i).add(labelListNum.get(i));

         // 글 제목 라벨
         labelListTitle.add(new JLabel(boardList.get(i).getBoard_title()));
         labelListTitle.get(i).setForeground(Color.WHITE);
         labelListTitle.get(i).setFont(new Font("HY견고딕", Font.PLAIN, 12));
         labelListTitle.get(i).setBounds(135, 20, 400, 15);
         panelList.get(i).add(labelListTitle.get(i));

         // 글 작성자 라벨
         labelListUser.add(new JLabel(boardList.get(i).getBoard_username()));
         labelListUser.get(i).setForeground(Color.WHITE);
         labelListUser.get(i).setFont(new Font("HY견고딕", Font.PLAIN, 12));
         labelListUser.get(i).setBounds(540, 20, 57, 15);
         panelList.get(i).add(labelListUser.get(i));

         // 글 작성시간 라벨
         labelListTime.add(new JLabel(boardList.get(i).getBoard_wtime()));
         labelListTime.get(i).setForeground(Color.WHITE);
         labelListTime.get(i).setFont(new Font("HY견고딕", Font.PLAIN, 12));
         labelListTime.get(i).setBounds(620, 20, 150, 15);
         panelList.get(i).add(labelListTime.get(i));

         // 글 조회수 라벨
         labelListHit.add(new JLabel(Integer.toString(boardList.get(i).getBoard_hit())));
         labelListHit.get(i).setForeground(Color.WHITE);
         labelListHit.get(i).setFont(new Font("HY견고딕", Font.PLAIN, 12));
         labelListHit.get(i).setBounds(827, 20, 57, 15);
         panelList.get(i).add(labelListHit.get(i));

         // 패널 클릭 이벤트
         panelList.get(i).addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
               panelList.get(num).setBackground(Color.black);
            }

            public void mouseExited(MouseEvent e) {
               panelList.get(num).setBackground(Color.DARK_GRAY);
            }

            public void mouseReleased(MouseEvent e) {
               writeBtnEvent();
               boardMain.mainApp.headerIssue.selectBoard();
               boardMain.scrollWrite.setBounds(112, 88, 686, 311);
               boardMain.delBtn.setBounds(260, 420, 86, 23);
               boardMain.prevBtn.setBounds(410, 420, 86, 23);
               boardMain.updBtn.setBounds(560, 420, 86, 23);
               // 수정, 삭제등에 사용할 board_id를 담아둔다!
               boardMain.board_id = boardList.get(num).getBoard_id();
               // 조회수 올리기 메서드.
               updHit();
               // 댓글 조회 메서드
               selectCmmt();
               boardMain.title.setText(boardList.get(num).getBoard_title());
               boardMain.content.setText(boardList.get(num).getBoard_content());
               // placeholder 기능으로 인해 회색으로 글씨가 보여 하얀색으로 초기화 시킴..
               boardMain.title.setForeground(Color.white);
               boardMain.content.setForeground(Color.white);
               // 삭제 수정 버튼을 보여줄지 여부..
               if(boardList.get(num).getBoard_username().equals(boardMain.mainApp.getRegistMemberVO().getMember_id())) {
                  boardMain.delBtn.setVisible(true);
                  boardMain.updBtn.setVisible(true);
               }
               boardMain.registBtn.setVisible(false);                  
               // 클릭 시 검은 배경이 남는 현상 발생으로 색깔 변경..
               panelList.get(num).setBackground(Color.DARK_GRAY);
            }
         });
      }
   }
   public void headerBoardShow(int board_id, String title, String content, String username) {
      writeBtnEvent();
      boardMain.scrollWrite.setBounds(112, 88, 686, 311);
      boardMain.delBtn.setBounds(260, 420, 86, 23);
      boardMain.prevBtn.setBounds(410, 420, 86, 23);
      boardMain.updBtn.setBounds(560, 420, 86, 23);
      // 수정, 삭제등에 사용할 board_id를 담아둔다!
      boardMain.board_id = board_id;
      // 조회수 올리기 메서드.
      updHit();
      // 댓글 조회 메서드
      selectCmmt();
      boardMain.title.setText(title);
      boardMain.content.setText(content);
      // placeholder 기능으로 인해 회색으로 글씨가 보여 하얀색으로 초기화 시킴..
      boardMain.title.setForeground(Color.white);
      boardMain.content.setForeground(Color.white);
      // 삭제 수정 버튼을 보여줄지 여부..
      if(username.equals(boardMain.mainApp.getRegistMemberVO().getMember_id())) {
         boardMain.delBtn.setVisible(true);
         boardMain.updBtn.setVisible(true);
      }
      boardMain.registBtn.setVisible(false);                  
   }
   // 게시판 등록 메서드
   public void addBoard() {
      String sql = "insert into board (board_id, board_group_id, board_title, board_content, board_username, board_ip, board_wtime, board_status, board_comment_count, board_hit) ";
      sql = sql + "values(board_seq.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'yyyy-mm-dd hh24:mm:ss'), 0,  0, 0)";
      PreparedStatement pstmt = null;
      try {
         // ip가지고 오기 ..!!
         local = InetAddress.getLocalHost();
         String ip = local.getHostAddress();

         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setInt(1, boardMain.board_group_id);
         pstmt.setString(2, boardMain.title.getText());
         pstmt.setString(3, boardMain.content.getText());
         pstmt.setString(4, boardMain.mainApp.getRegistMemberVO().getMember_id());
         pstmt.setString(5, ip);
         int result = pstmt.executeUpdate();

         if (result == 0) {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "등록 오류 발생.");
         } else {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "등록 성공.");
         }

      } catch (SQLException e) {
         e.printStackTrace();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt);
      }
   }

   // board를 delete 한다. board_status만 1로 변경..
   public void delBoard() {
      String sql = "UPDATE board SET BOARD_STATUS = 1 WHERE  BOARD_ID = ?";
      PreparedStatement pstmt = null;
      try {
         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setInt(1, boardMain.board_id);
         int result = pstmt.executeUpdate();

         if (result == 0) {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "삭제실패..");
         } else {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "삭제성공!!");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt);
      }
   }

   // board를 update(수정) 한다.
   public void updBoard() {
      String sql = "update board set board_title = ?, board_content = ? where board_id = ?";
      PreparedStatement pstmt = null;

      try {
         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setString(1, boardMain.title.getText());
         pstmt.setString(2, boardMain.content.getText());
         pstmt.setInt(3, boardMain.board_id);
         int result = pstmt.executeUpdate();
         if (result == 0) {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "수정실패..");
         } else {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "수정성공!!");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt);
      }
   }

   // 선택한 board의 Hit 수를 증가시킨다
   public void updHit() {
      String sql = "UPDATE board SET BOARD_HIT  = NVL(TO_NUMBER(BOARD_HIT), 0) + 1 WHERE  BOARD_ID = ?";
      PreparedStatement pstmt = null;

      try {
         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setInt(1, boardMain.board_id);
         int result = pstmt.executeUpdate();
         if (result == 0) {
            System.out.println("조회수 증가 오류 발생...");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt);
      }
   }

   // 댓글 등록 이벤트..
   public void addCmmt() {
//      cmmt_id, board_id, cmmt_content, cmmt_wtime, cmmt_ip 
      String sql = "INSERT INTO CMMT VALUES(CMMT_SEQ.NEXTVAL, ?, ?, ?,to_char(sysdate, 'yyyy-mm-dd hh24:mm:ss'), ?)";
      PreparedStatement pstmt = null;

      try {
         local = InetAddress.getLocalHost();
         String ip = local.getHostAddress();

         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setInt(1, boardMain.board_id);
         pstmt.setString(2, boardMain.mainApp.getRegistMemberVO().getMember_id());
         pstmt.setString(3, boardMain.cmmt.getText());
         pstmt.setString(4, ip);

         int result = pstmt.executeUpdate();
         if (result == 0) {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "댓글 등록 실패..");
         } else {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "댓글 등록 성공..");
         }

      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt);
      }
   }

   // 댓글 조회 메소드
   public void selectCmmt() {
      cmmtList.removeAll(cmmtList);
      String sql = "SELECT * FROM CMMT WHERE BOARD_ID = ? ORDER BY CMMT_ID DESC";
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         pstmt = boardMain.con.prepareStatement(sql);
         pstmt.setInt(1, boardMain.board_id);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            cmmtVO vo = new cmmtVO();
            vo.setCmmt_id(rs.getInt("cmmt_id"));
            vo.setBoard_id(rs.getInt("board_id"));
            vo.setCmmt_username(rs.getString("cmmt_username"));
            vo.setCmmt_content(rs.getString("cmmt_content"));
            vo.setCmmt_wtime(rs.getString("cmmt_wtime"));
            vo.setCmmt_ip(rs.getString("cmmt_ip"));
            cmmtList.add(vo);
         }
         addCmmtPanel();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt, rs);
      }
   }

//   텍스트에어리어.getlinecount

   // 댓글 레코드 수만큼 패널추가 ..
   public void addCmmtPanel() {
      boardMain.writePanel.remove(boardMain.scrollCmmtPanel);
      createCmmtScrollPanel();
      cmmtPanelList.removeAll(cmmtPanelList);
      cmmtLabelListUser.removeAll(cmmtLabelListUser);
      cmmtLabelListTime.removeAll(cmmtLabelListTime);
      cmmtLabelListContent.removeAll(cmmtLabelListContent);
      boardMain.cmmtPanel.updateUI();
      int textAreaSum = 0;
      for (int i = 0; i < cmmtList.size(); i++) {
         int num = i;
         cmmtPanelList.add(new JPanel());
         cmmtPanelList.get(i).setPreferredSize(new Dimension(686, 45));
         cmmtPanelList.get(i).setBackground(Color.DARK_GRAY);
         cmmtPanelList.get(i).setLayout(null);
         cmmtPanelList.get(i).setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
         boardMain.cmmtPanel.add(cmmtPanelList.get(i));

         cmmtLabelListUser.add(new JLabel(cmmtList.get(i).getCmmt_username()));
         cmmtLabelListUser.get(i).setBounds(20, 0, 100, 10);
         cmmtLabelListUser.get(i).setForeground(Color.WHITE);
         cmmtPanelList.get(i).add(cmmtLabelListUser.get(i));

         cmmtLabelListTime.add(new JLabel(cmmtList.get(i).getCmmt_wtime()));
         cmmtLabelListTime.get(i).setBounds(100, 0, 115, 10);
         cmmtLabelListTime.get(i).setForeground(Color.WHITE);
         cmmtPanelList.get(i).add(cmmtLabelListTime.get(i));

         cmmtLabelListContent.add(new JTextArea(cmmtList.get(i).getCmmt_content()));
         cmmtLabelListContent.get(i).setEnabled(false);
         cmmtLabelListContent.get(i).setDisabledTextColor(Color.WHITE);
         cmmtLabelListContent.get(i).setBackground(Color.DARK_GRAY);
         
         
         delCmmt = new JLabel("X");
         delCmmt.setForeground(Color.WHITE);
         delCmmt.setFont(new Font("Arial", Font.PLAIN , 30));
         delCmmt.setBounds(640, 12, 115, 10);
         delCmmt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         cmmtPanelList.get(num).add(delCmmt);
         cmmtPanelList.get(num).updateUI();
         
         delCmmt.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
               if(cmmtLabelListUser.get(num).getText().equals(boardMain.mainApp.getRegistMemberVO().getMember_id())) {
                  deleteCmmt(cmmtList.get(num).getCmmt_id());
                  selectCmmt();
               } else {
                  JOptionPane.showMessageDialog(boardMain.boardPanel, "작성자가 아니면 삭제 할 수 없습니다.");
               }
            };
         });
         
         // 채팅 줄 수에 따라 패널, 에어리어의 크기를 변경해준다..
         if (cmmtLabelListContent.get(i).getLineCount() > 1) {
            cmmtPanelList.get(i)
                  .setPreferredSize(new Dimension(686, 40 + (cmmtLabelListContent.get(i).getLineCount() * 15)));
            cmmtLabelListContent.get(i).setBounds(100, 20, 630, cmmtLabelListContent.get(i).getLineCount() * 18);
            textAreaSum = textAreaSum + (cmmtLabelListContent.get(i).getLineCount() * 18);
         } else {
            cmmtLabelListContent.get(i).setBounds(100, 20, 630, 20);
         }

         cmmtLabelListContent.get(i).setForeground(Color.WHITE);
         cmmtPanelList.get(i).add(cmmtLabelListContent.get(i));
      }
      // 리스트가 생기는 만큼 패널의 크기도 조절해준다..
      if ((cmmtList.size() * 40) > boardMain.cmmtPanel.getHeight()) {
         boardMain.cmmtPanel.setPreferredSize(new Dimension(240, ((cmmtList.size() + 1) * 45) + textAreaSum));
      }

      // 스크롤바를 맨위로 보내기 위해 사용..
      Runnable doScroll = new Runnable() {
         public void run() {
            boardMain.scrollCmmtPanel.getVerticalScrollBar().setValue(0);
         }
      };
      SwingUtilities.invokeLater(doScroll);
   }
   
   public void deleteCmmt(int cmmt_id) {
      String sql = "delete from cmmt where cmmt_id = ? ";
      PreparedStatement pstmt = null;
      
      try {
         pstmt = boardMain.mainApp.con.prepareStatement(sql);
         pstmt.setInt(1, cmmt_id);
         int result = pstmt.executeUpdate();
         if(result == 0) {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "삭제 실패..");
         } else {
            JOptionPane.showMessageDialog(boardMain.boardPanel, "댓글 삭제 성공!");
         }
         
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         boardMain.mainApp.dbManager.close(pstmt);
      }
   }
   
   public void writeBtnEvent() {
      boardMain.boardPanel.removeAll();
      boardMain.boardPanel.updateUI();
      boardMain.boardPanel.add(boardMain.writePanel, BorderLayout.CENTER);
      boardMain.scrollWrite.setBounds(112, 88, 686, 511);
      boardMain.registBtn.setBounds(468, 627, 86, 23);
      boardMain.prevBtn.setBounds(351, 627, 86, 23);
      boardMain.scrollCmmtPanel.setVisible(false);
      compoInit();
   }
   
   
   //이전 페이지로 돌아가는 기능...
   public void prevBtnEvent() {
      boardMain.boardPanel.removeAll();
      boardMain.boardPanel.updateUI();
      boardMain.boardPanel.add(boardMain.boardPenel_north, BorderLayout.NORTH);
      boardMain.boardPanel.add(boardMain.boardPanel_center, BorderLayout.CENTER);
      boardMain.delBtn.setVisible(false);
      boardMain.updBtn.setVisible(false);
      boardMain.registBtn.setVisible(true);
      boardMain.mainApp.headerIssue.selectBoard();
      // 뒤로가기에 재조회 기능 추가..
      selectBoard();
   };

   public void compoInit() {
      boardMain.title.setText("제목을 입력해주세요..");
      boardMain.title.setForeground(Color.GRAY);
      boardMain.content.setText("내용을 입력해주세요..");
      boardMain.content.setForeground(Color.GRAY);
      boardMain.cmmt.setText("댓글을 입력해주세요..");
      boardMain.cmmt.setForeground(Color.GRAY);
   }

   // board의 갯수를 조회한다.
   public void countRowBoard() {
      String sql = "select board_id from board where board_status = 0 and board_group_id = ?";
      PreparedStatement pstmt = null;
      ResultSet rs = null;

      try {
         pstmt = boardMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
         pstmt.setInt(1, boardMain.board_group_id);
         rs = pstmt.executeQuery();
         rs.last();
         ALLCOUNT = rs.getRow();
         PAGECOUNT = (rs.getRow() + (boardMain.GRIDCOUNT - 1)) / boardMain.GRIDCOUNT;
         // page 갯수 계산을 위해 넣어둔다.. 10개 이상이될때 분리 시키는 작업..
         PAGECOUNTSPLIT = PAGECOUNT;
         if(PAGECOUNT < 10) {
            PAGECOUNTSPLITNUM = PAGECOUNT;            
            if(PAGECOUNT == 0) {
               PAGECOUNTSPLITNUM = 1;
            }
         } else {
            PAGECOUNTSPLITNUM = 10;
         }
         rs.beforeFirst();

         addPage();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         boardMain.dbCon.close(pstmt, rs);
      }
   }

   // 게시판 레코드가 10개가 넘어갈때마다 페이지를 생성한다.
   public void addPage() {
      int x = 33;
      // 화살표 라벨을 만들어줌
      boardMain.labelPagePrev = new JLabel("◀");
      boardMain.labelPagePrev.setFont(new Font("HY견고딕", Font.PLAIN, 12));
      boardMain.labelPagePrev.setForeground(Color.WHITE);
      boardMain.labelPagePrev.setBounds(10, 7, 16, 15);
      boardMain.labelPagePrev.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      boardMain.boardPenel_north.add(boardMain.labelPagePrev);

      for (int i = boardMain.pageForINum; i < PAGECOUNTSPLITNUM; i++) {
         int num = i;
         boardMain.labelPageIndex.add(new JLabel(Integer.toString(i + 1)));
         boardMain.labelPageIndex.get(i).setFont(new Font("HY견고딕", Font.PLAIN, 12));
         boardMain.labelPageIndex.get(i).setForeground(Color.WHITE);
         boardMain.labelPageIndex.get(i).setBounds(x, 7, 16, 15);
         // page수가 많아지면 x값을 증가시켜 이동시키기 위해..
         x += 20;
         boardMain.boardPenel_north.add(boardMain.labelPageIndex.get(i));
         boardMain.labelPageIndex.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         // page쪽수 라벨을 누르면 작동.. 페이지 이동
         boardMain.labelPageIndex.get(i).addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
               boardMain.labelPageIndex.get(boardMain.pagenum).setForeground(Color.white);
               boardMain.pagenum = num;
               boardMain.labelPageIndex.get(num).setForeground(Color.black);
               pageSelect();
            }
         });
      }
      boardMain.labelPageNext = new JLabel("▶");
      boardMain.labelPageNext.setFont(new Font("HY견고딕", Font.PLAIN, 12));
      boardMain.labelPageNext.setForeground(Color.WHITE);
      boardMain.labelPageNext.setBounds(x + 10, 7, 16, 15);
      boardMain.labelPageNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      boardMain.boardPenel_north.add(boardMain.labelPageNext);

      boardMain.labelPagePrev.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            // 최소 페이지 수를 넘기면 안넘어 가도록 조정
            if (boardMain.pagenum > 0) {
               if (boardMain.pagenum % 10 == 0) {
                  boardMain.pageForINum = (boardMain.pageForINum - 10);
                  delPageLabelPrev();
                  if (PAGECOUNTSPLITNUM == PAGECOUNT) {
                     PAGECOUNTSPLITNUM -= PAGECOUNT - (boardMain.pageForINum + 10);
                  } else {
                     PAGECOUNTSPLITNUM -= 10;
                  }
                  addPage();
               }
               if (boardMain.labelPageIndex.size() != boardMain.pagenum) {
                  boardMain.labelPageIndex.get(boardMain.pagenum).setForeground(Color.white);
               }
               boardMain.pagenum = boardMain.pagenum - 1;
               boardMain.labelPageIndex.get(boardMain.pagenum).setForeground(Color.black);
               pageSelect();
            }
         }
      });

      boardMain.labelPageNext.addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            // 최대 페이지 수를 넘기면 안넘어 가도록 조정
            if (PAGECOUNT - 1 >boardMain.pagenum) {
               if (boardMain.pagenum == boardMain.labelPageIndex.size() - 1) {
                  boardMain.pageForINum = (boardMain.pageForINum + 10);
                  delPageLabelNext();
                  PAGECOUNTSPLITNUM += 10;
                  if (PAGECOUNTSPLITNUM > PAGECOUNT) {
                     PAGECOUNTSPLITNUM = PAGECOUNT;
                  }
                  addPage();
               }
               boardMain.labelPageIndex.get(boardMain.pagenum).setForeground(Color.white);
               boardMain.pagenum = boardMain.pagenum + 1;
               boardMain.labelPageIndex.get(boardMain.pagenum).setForeground(Color.black);
               pageSelect();
            }
         }
      });
   }
   
   public void createCmmtScrollPanel() {
      // 댓글 레이아웃 프레임 패널 추가
      boardMain.cmmtPanel = new JPanel();
      boardMain.scrollCmmtPanel = new JScrollPane(boardMain.cmmtPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      boardMain.scrollCmmtPanel.setBounds(112, 550, 686, 120);
      boardMain.cmmtPanel.setBackground(Color.DARK_GRAY);
      boardMain.scrollCmmtPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
      boardMain.cmmtPanel.setLayout(new FlowLayout());
      boardMain.writePanel.add(boardMain.scrollCmmtPanel);
   }
}