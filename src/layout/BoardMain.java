package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import utill.BoardModule;
import utill.DBManager;

public class BoardMain {
	private JFrame frame;
	public JTextField title;
	public JPanel containerPanel;
	public JPanel boardPanel;
	public JPanel boardPenel_north;
	public JPanel boardPanel_center;
	public JPanel writePanel;
	public JPanel cmmtPanel;
	public JTextArea content, cmmt;
	public JLabel labelPagePrev, labelPageNext;
	public JScrollPane scrollWrite, scrollCmmt, scrollCmmtPanel;
	public int pageForINum = 0;
	public int board_id, pagenum = 0;
	public int start_pageNum = 1, end_pageNum = 10;

	public Connection con;
	public DBManager dbCon;
	// 게시판 라벨 인덱스를 담아둘 컬렉션 프레임워크 선언
	public ArrayList<JLabel> labelPageIndex = new ArrayList<JLabel>();

	public JButton registBtn, prevBtn, updBtn, delBtn, cmmtBtn;
	public static final int GRIDCOUNT = 10; // 그리드 레이아웃의 층 갯수. 게사판 패널이 들어갈 갯수를 정한다.
	
	public JPanel boardPanel_center_header;
	public JPanel boardPanel_center_content;
	public JLabel contentNum;
	public JLabel contentTitle;
	public JLabel writerName;
	public JLabel writerTime;
	public JLabel writerHit;
	BoardModule boardModule;
	public MainApp mainApp;
	public int board_group_id;
	// 생성자 선언..
	public BoardMain(MainApp mainApp, int board_group_id) {
		
		this.mainApp = mainApp;
		this.board_group_id = board_group_id;
		
		//메서드 객체 생성
		boardModule = new BoardModule(this);
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		containerPanel = new JPanel();

		mainApp.p_center_center.add(containerPanel);
		containerPanel.setLayout(new BorderLayout(0, 0));

		dbCon = new DBManager();
		con = dbCon.connect();

		boardPanel = new JPanel();
		containerPanel.add(boardPanel);
		boardPanel.setLayout(new BorderLayout(0, 0));
		boardPanel.setVisible(true);

		boardPenel_north = new JPanel();
		boardPenel_north.setBackground(Color.DARK_GRAY);
		boardPenel_north.setPreferredSize(new Dimension(900, 30));
		boardPanel.add(boardPenel_north, BorderLayout.NORTH);
		boardPenel_north.setLayout(null);
		boardPenel_north.setVisible(true);

		JButton writeBtn = new JButton("Write");
		writeBtn.setFocusable(false);
		writeBtn.setPreferredSize(new Dimension(59, 30));
		writeBtn.setFont(new Font("HY견고딕", Font.PLAIN, 11));
		writeBtn.setBounds(799, 5, 75, 21);
		boardPenel_north.add(writeBtn);

		boardPanel_center = new JPanel();
		boardPanel_center.setBackground(Color.DARK_GRAY);
		boardPanel_center.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		boardPanel_center.setPreferredSize(new Dimension(900, 690));
		boardPanel.add(boardPanel_center, BorderLayout.CENTER);
		boardPanel_center.setLayout(new BorderLayout(0, 0));

		boardPanel_center_header = new JPanel();
		boardPanel_center_header.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		boardPanel_center_header.setBackground(Color.DARK_GRAY);
		boardPanel_center_header.setPreferredSize(new Dimension(900, 30));
		boardPanel_center.add(boardPanel_center_header, BorderLayout.NORTH);
		boardPanel_center_header.setLayout(null);
		boardPanel_center.setVisible(true);

		contentNum = new JLabel("글번호");
		contentNum.setForeground(Color.WHITE);
		contentNum.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		contentNum.setBounds(30, 5, 57, 15);
		boardPanel_center_header.add(contentNum);

		contentTitle = new JLabel("제목");
		contentTitle.setForeground(Color.WHITE);
		contentTitle.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		contentTitle.setBounds(278, 5, 57, 15);
		boardPanel_center_header.add(contentTitle);

		writerName = new JLabel("글쓴이");
		writerName.setForeground(Color.WHITE);
		writerName.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		writerName.setBounds(535, 5, 57, 15);
		boardPanel_center_header.add(writerName);

		writerTime = new JLabel("작성일");
		writerTime.setForeground(Color.WHITE);
		writerTime.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		writerTime.setBounds(670, 5, 57, 15);
		boardPanel_center_header.add(writerTime);

		writerHit = new JLabel("조회수");
		writerHit.setForeground(Color.WHITE);
		writerHit.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		writerHit.setBounds(815, 5, 57, 15);
		boardPanel_center_header.add(writerHit);

		boardPanel_center_content = new JPanel();
		boardPanel_center_content.setBackground(Color.DARK_GRAY);
		boardPanel_center.add(boardPanel_center_content, BorderLayout.CENTER);
		boardPanel_center_content.setLayout(new GridLayout(GRIDCOUNT, 0, 0, 0));
		// 리스트 갯수를 조회한다..
		boardModule.countRowBoard();
		// 처음 실행시 1번의 글자색이 검은색으로 변경해야됨..
		labelPageIndex.get(0).setForeground(Color.black);
		// 리스트 조회 후 boardPanel_center 에 넣기위해 아래 쪽에선언한다.
		boardModule.selectBoard();

		// 게시판 글쓰기 페이지
		writePanel = new JPanel();
		writePanel.setBounds(0, 0, 884, 681);
		writePanel.setBackground(Color.DARK_GRAY);
		writePanel.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		writePanel.setPreferredSize(new Dimension(900, 690));
		writePanel.setLayout(null);

		title = new JTextField();
		title.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		title.setForeground(Color.GRAY);
		title.setText("제목을 입력해주세요..");
		title.setBounds(112, 57, 686, 21);
		writePanel.add(title);
		title.setColumns(10);

		// 제목 Placeholder
		title.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (title.getText().equals("제목을 입력해주세요..")) {
					title.setText("");
					title.setForeground(Color.WHITE);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (title.getText().isEmpty()) {
					title.setText("제목을 입력해주세요..");
					title.setForeground(Color.GRAY);
				}
			}
		});

		content = new JTextArea();
		scrollWrite = new JScrollPane(content);
		scrollWrite.setBorder(new LineBorder(new Color(0, 0, 0)));
		content.setText("내용을 입력해주세요..");
		scrollWrite.setForeground(Color.GRAY);
		scrollWrite.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		writePanel.add(scrollWrite);

		boardModule.createCmmtScrollPanel();

		// 댓글 텍스트 에어리어 추가
		cmmt = new JTextArea();
		scrollCmmt = new JScrollPane(cmmt);
		scrollCmmt.setBorder(new LineBorder(new Color(0, 0, 0)));
		cmmt.setText("댓글을 입력해주세요..");
		cmmt.setForeground(Color.GRAY);
		scrollCmmt.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		scrollCmmt.setBounds(112, 460, 686, 40);
		writePanel.add(scrollCmmt);

		// 댓글등록 버튼 추가..
		cmmtBtn = new JButton("댓글등록");
		cmmtBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		cmmtBtn.setBounds(707, 510, 90, 25);
		writePanel.add(cmmtBtn);

		// 댓글 Placeholder
		cmmt.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (cmmt.getText().equals("댓글을 입력해주세요..")) {
					cmmt.setText("");
					cmmt.setForeground(Color.WHITE);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (cmmt.getText().isEmpty()) {
					cmmt.setText("댓글을 입력해주세요..");
					cmmt.setForeground(Color.GRAY);
				}
			}
		});

		content.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if (content.getText().equals("내용을 입력해주세요..")) {
					content.setText("");
					content.setForeground(Color.WHITE);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (content.getText().isEmpty()) {
					content.setText("내용을 입력해주세요..");
					content.setForeground(Color.GRAY);
				}
			}
		});

		registBtn = new JButton("등록하기");
		registBtn.setForeground(Color.WHITE);
		registBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		writePanel.add(registBtn);

		prevBtn = new JButton("이전으로");
		prevBtn.setForeground(Color.WHITE);
		prevBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		writePanel.add(prevBtn);

		updBtn = new JButton("수정하기");
		updBtn.setForeground(Color.WHITE);
		updBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		updBtn.setBounds(468, 627, 86, 23);
		updBtn.setVisible(false);
		writePanel.add(updBtn);

		delBtn = new JButton("삭제하기");
		delBtn.setForeground(Color.WHITE);
		delBtn.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		delBtn.setFocusable(false);
		delBtn.setBounds(234, 627, 86, 23);
		delBtn.setVisible(false);
		writePanel.add(delBtn);

		JLabel board_title = new JLabel("게시글 작성");
		board_title.setForeground(Color.WHITE);
		board_title.setFont(new Font("HY견고딕", Font.PLAIN, 14));
		board_title.setBounds(406, 22, 241, 15);
		writePanel.add(board_title);

		writeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardModule.writeBtnEvent();
			}
		});

		prevBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardModule.prevBtnEvent();
			}
		});

		// 글쓰기폼 등록 이벤트
		registBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (title.getText() == " " || title.getText().equals("제목을 입력해주세요..")) {
					JOptionPane.showMessageDialog(writePanel, "제목을 입력해주세요...");
				} else if (content.getText() == " " || content.getText().equals("내용을 입력해주세요..")) {
					JOptionPane.showMessageDialog(writePanel, "내용을 입력해주세요...");
				} else {
					// 게시판 추가
					boardModule.addBoard();
					// 등록 후 번호 초기화
					boardModule.ALLCOUNT = boardModule.ALLCOUNT + 1; 
					boardModule.selectBoard();
					// 추가 후 뒤로가기..
					boardModule.prevBtnEvent();
				}
			}
		});

		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 게시판 삭제
				boardModule.delBoard();
				// 등록 후 번호 초기화
				boardModule.ALLCOUNT = boardModule.ALLCOUNT - 1;
				boardModule.selectBoard();
				// 삭제 후 뒤로가기..
				boardModule.prevBtnEvent();
			}
		});

		updBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (title.getText() == " " || title.getText().equals("제목을 입력해주세요..")) {
					JOptionPane.showMessageDialog(writePanel, "제목을 입력해주세요...");
				} else if (content.getText() == " " || content.getText().equals("내용을 입력해주세요..")) {
					JOptionPane.showMessageDialog(writePanel, "내용을 입력해주세요...");
				} else {
					// 게시판 수정
					boardModule.updBoard();
				}
			}
		});

		// 댓글 등록 이벤트
		cmmtBtn.addActionListener((e) -> {
			if (cmmt.getText() == " " || cmmt.getText().equals("댓글을 입력해주세요..")) {
				JOptionPane.showMessageDialog(boardPanel, "댓글을 입력해주세요...");
			} else {
				boardModule.addCmmt();
				cmmtPanel.removeAll();
				boardModule.selectCmmt();
				boardModule.addCmmtPanel();
				cmmtPanel.updateUI();
				cmmt.setText("댓글을 입력해주세요..");
				cmmt.setForeground(Color.GRAY);
			}
		});
		containerPanel.updateUI();
	}
}
