package layout;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import models.ChatMemberVO;
import models.ChatVO;
import models.MessageVO;
import models.RegistMemberVO;
import utill.Chat_lib;
import utill.DBManager;

public class MainApp {
	JScrollPane chatListScroll, boardListScroll;
	JPanel p_west_south_inChatList;
	public JFrame frame;
	public JTextField t_chat_pop_name;
	JTextField t_board_pop_name;
	DBManager dbManager;

	/*---------------------------------------------------------------
	* 접속관련 변수
	---------------------------------------------------------------*/
	public Connection con;
	
	/*---------------------------------------------------------------
	* 회원관련 변수
	---------------------------------------------------------------*/
	private RegistMemberVO registMemberVO;

	/*---------------------------------------------------------------
	* 채팅 관련 
	---------------------------------------------------------------*/
	public ChatVO chatVO;
	public MessageVO messageVO;
	Chat_lib chat_lib;
	CenterChattingPanel centerChattingPanel;
	public ArrayList<String> chat_settedMember = new ArrayList<String>();
	public ArrayList<JPanel> chatSmallPanels = new ArrayList<JPanel>();
	public ArrayList<JLabel> chatSmallLabels = new ArrayList<JLabel>();
	public ArrayList<ChatVO> chatVOList = new ArrayList<ChatVO>(); //아마 안쓸듯..
	public ArrayList<MessageVO> messageVOList = new ArrayList<MessageVO>();
	public ArrayList<ChatMemberVO> chatMemberVOList = new ArrayList<ChatMemberVO>();
	

	/*---------------------------------------------------------------
	* 채팅 관련 끝
	---------------------------------------------------------------*/
	

	ArrayList<JPanel> boardPanels = new ArrayList<JPanel>();
	ArrayList<JLabel> boardPanellabels = new ArrayList<JLabel>();
	ArrayList<JLabel> chatPopAddLabels = new ArrayList<JLabel>();

	public JPanel p_center;
	
	JPanel p_west_south_chatList;
	public JPanel p_west_south_chat;
	JPanel p_chat_south_center;
	JTextField t_searchField;
	JPanel panel_search_pop_container;
	JPanel p_east;
	JPanel p_hamburger_pop_container;

	PopupFactory popupFactory;
	JPanel p_chat_set_pop;
	JPanel p_chat_set_pop_add_container;
	JPanel p_board_set_pop;
	JPanel p_chat_set_pop_add_panel;
	JScrollPane p_chat_pop_add_scrolls;
	Choice ch_chat_pop_invite;
	Popup popup;
	Popup popup_ch;
	Popup popup_ch_add;
	JLabel la_userName;

	public static boolean s_pop = false;
	public static boolean c_pop = false;
	private boolean HasSession = false;

	JButton btnSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		/*------------------------------------------------------
		* 외부 클래스 new 하는곳 
		-----------------------------------------------------*/
		dbManager = new DBManager();

		/*------------------------------------------------------
		접속 관련 
		-----------------------------------------------------*/
		con = dbManager.connect();
		/*------------------------------------------------------
		채팅 관련 
		-----------------------------------------------------*/

		chatVO = new ChatVO();
		chat_lib = new Chat_lib(this);
		

		initialize();

	}
	
	
	

	public boolean isHasSession() {
		return HasSession;
	}

	public void setHasSession(boolean setHasSession) {
		this.HasSession = setHasSession;
	}

	public RegistMemberVO getRegistMemberVO() {
		return registMemberVO;
	}

	public void setRegistMemberVO(RegistMemberVO registMemberVO) {
		this.registMemberVO = registMemberVO;
	}

	/*---------------------------------------------------
	* 게시판 패널 생성메서드
	---------------------------------------------------*/
	public void createBoardList(JPanel panel, String folderName, int fontSize) {
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
		panel.updateUI();
	}

	/*---------------------------------------------------
	* 채팅창 리스트 생성메서드 createChatList //chat_lib클래스로 옮김
	* ====================================================
	---------------------------------------------------*/

	/*---------------------------------------------------
	* 채팅 pop add 참여인원 라벨추가 메소드
	---------------------------------------------------*/
	public void chatPopAddappendLabel(String name) {
		if (chatPopAddLabels.size() == 0) {
			chat_settedMember.add(name);
			JLabel selectedName = new JLabel(name);
			selectedName.setFont(new Font("HY견고딕", Font.PLAIN, 14));
			selectedName.setPreferredSize(new Dimension(180, 30));
			chatPopAddLabels.add(selectedName);
			p_chat_set_pop_add_panel.add(selectedName);
		} else {
			chat_settedMember.add(name);
			JLabel selectedName = new JLabel(name);
			selectedName.setFont(new Font("HY견고딕", Font.PLAIN, 14));
			selectedName.setPreferredSize(new Dimension(180, 30));
			chatPopAddLabels.add(selectedName);
			p_chat_set_pop_add_panel.add(selectedName);
		}
	}

	/*---------------------------------------------
	* 채팅 pop add 참여인원 라벨추가 메소드 끝
	---------------------------------------------*/

	private void initialize() {

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

		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popupFactory = new PopupFactory();

		JPanel p_west = new JPanel();
		p_west.setBackground(Color.YELLOW);
		p_west.setPreferredSize(new Dimension(240, 960));
		frame.getContentPane().add(p_west, BorderLayout.WEST);
		p_west.setLayout(new BorderLayout(0, 0));

		JPanel p_west_north = new JPanel();
		p_west_north.setPreferredSize(new Dimension(240, 120));
		p_west_north.setBackground(Color.BLUE);
		p_west.add(p_west_north, BorderLayout.NORTH);
		p_west_north.setLayout(new BorderLayout(0, 0));

		JPanel p_west_north_title = new JPanel();
		p_west_north_title.setPreferredSize(new Dimension(240, 80));
		p_west_north_title.setBackground(new Color(64, 64, 64));
		p_west_north.add(p_west_north_title, BorderLayout.NORTH);
		p_west_north_title.setLayout(null);

		JLabel la_groupName = new JLabel("KoreaStudy");
		la_groupName.setForeground(Color.WHITE);
		la_groupName.setFont(new Font("HY견고딕", Font.BOLD, 18));
		la_groupName.setBounds(77, 20, 130, 18);
		p_west_north_title.add(la_groupName);

		la_userName = new JLabel("사용자명");
		la_userName.setFont(new Font("HY견고딕", Font.BOLD, 15));
		la_userName.setForeground(Color.WHITE);
		la_userName.setBounds(79, 48, 89, 18);
		p_west_north_title.add(la_userName);

		JPanel panel = new JPanel();
		panel.setBounds(12, 20, 53, 46);
		p_west_north_title.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\minizi.png"));
		panel.add(lblNewLabel_1, BorderLayout.CENTER);

		JPanel p_west_north_boardTitle = new JPanel();
		p_west_north_boardTitle.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		p_west_north_boardTitle.setPreferredSize(new Dimension(240, 40));
		p_west_north_boardTitle.setBackground(new Color(64, 64, 64));
		p_west_north.add(p_west_north_boardTitle, BorderLayout.SOUTH);
		p_west_north_boardTitle.setLayout(null);

		JLabel la_boardTitle = new JLabel("게시판(Board)");
		la_boardTitle.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		la_boardTitle.setForeground(new Color(255, 255, 255));
		la_boardTitle.setBounds(25, 11, 140, 18);
		p_west_north_boardTitle.add(la_boardTitle);

		JPanel p_west_south = new JPanel();
		p_west_south.setBackground(new Color(64, 64, 64));
		p_west_south.setPreferredSize(new Dimension(240, 220));
		p_west.add(p_west_south, BorderLayout.SOUTH);
		p_west_south.setLayout(new BorderLayout(0, 0));

		JPanel p_west_south_chatTitle = new JPanel();
		p_west_south_chatTitle.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		p_west_south_chatTitle.setBackground(new Color(64, 64, 64));
		p_west_south_chatTitle.setPreferredSize(new Dimension(240, 40));
		p_west_south.add(p_west_south_chatTitle, BorderLayout.NORTH);
		p_west_south_chatTitle.setLayout(null);

		JButton bt_chat = new JButton("+");
		bt_chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int x = frame.getLocationOnScreen().x;
				int y = frame.getLocationOnScreen().y;
				if (c_pop == false) {
					t_chat_pop_name.setText("");
					popup_ch = popupFactory.getPopup(frame, p_chat_set_pop_add_container, x + 650, y + 50);
					popup_ch_add = popupFactory.getPopup(frame, p_chat_set_pop, x + 415, y + 50);
					popup_ch.show();
					popup_ch_add.show();
					MainApp.c_pop = true;
				} else {
					MainApp.c_pop = false;
					popup_ch.hide();
					popup_ch_add.hide();
				}
			}
		});
		bt_chat.setBounds(173, 4, 41, 27);
		p_west_south_chatTitle.add(bt_chat);

		JLabel la_chatTitle = new JLabel("채팅(Chat)");
		la_chatTitle.setForeground(Color.WHITE);
		la_chatTitle.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		la_chatTitle.setBounds(14, 10, 140, 18);
		p_west_south_chatTitle.add(la_chatTitle);

		p_west_south_chatList = new JPanel();
		p_chat_south_center = new JPanel();
		p_chat_south_center.setBackground(Color.DARK_GRAY);
		p_chat_south_center.setPreferredSize(new Dimension(240, 80));
		chatListScroll = new JScrollPane(p_chat_south_center, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p_chat_south_center.setLayout(null);
		p_west_south_chatList.setBackground(new Color(64, 64, 64));
		p_west_south_chatList.setPreferredSize(new Dimension(240, 0));

//      p_west_south_chatList.setLayout(null);
//      p_west_south.add(chatListScroll);

		p_west_south_chat = new JPanel();
		p_west_south_chat.setOpaque(false);
		p_west_south_chat.setBorder(new LineBorder(new Color(0, 0, 0)));
		p_west_south_chat.setBackground(new Color(64, 64, 64));
		p_west_south.add(p_west_south_chat, BorderLayout.CENTER);
		p_west_south_chat.setLayout(new BorderLayout(0, 0));
		p_west_south_chat.add(chatListScroll);
//      p_west_south_chat.setLayout();

		JPanel p_west_list = new JPanel();
		p_west_list.setBackground(new Color(64, 64, 64));
		boardListScroll = new JScrollPane(p_west_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		p_west_list.setPreferredSize(new Dimension(0, 0));
		p_west.add(boardListScroll, BorderLayout.CENTER);
		p_west_list.setLayout(null);

		JButton bt_board = new JButton("+");
		bt_board.setBounds(185, 7, 41, 27);
		p_west_north_boardTitle.add(bt_board);
		bt_board.addActionListener((e) -> {
			// 게시판 팝업
			int x = frame.getLocationOnScreen().x;
			int y = frame.getLocationOnScreen().y;
			if (s_pop == false) {
				popup = popupFactory.getPopup(frame, p_board_set_pop, x + 415, y + 50);
				popup.show();
				MainApp.s_pop = true;
			} else {
				MainApp.s_pop = false;
				popup.hide();
			}
		});

		p_east = new JPanel();
		p_east.setBackground(new Color(64, 64, 64));
		p_east.setPreferredSize(new Dimension(70, 960));
		frame.getContentPane().add(p_east, BorderLayout.EAST);
		p_east.setLayout(new GridLayout(9, 1, 0, 5));

		JButton btnHamburger = new JButton("");
		btnHamburger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = btnHamburger.getLocationOnScreen().x;
				int y = btnHamburger.getLocationOnScreen().y;
				if (s_pop == false) {
					popup = popupFactory.getPopup(frame, p_hamburger_pop_container, x - 240, y + 10);
					popup.show();
					MainApp.s_pop = true;
				} else {
					MainApp.s_pop = false;
					popup.hide();
				}

			}
		});
		btnHamburger.setIcon(new ImageIcon("C:/workspace/Java_workspace/TeamProject/src/res/hamburger.png"));
		btnHamburger.setFocusPainted(false);
		btnHamburger.setContentAreaFilled(false);
		btnHamburger.setBorder(null);
		p_east.add(btnHamburger);

		/*-------------------------------------------------------------------------------------
		 *  서치 팝업창 panel_search_pop_container
		 ------------------------------------------------------------------------------------*/
		panel_search_pop_container = new JPanel();
		JPanel n_panel = new JPanel();
		panel_search_pop_container.setBounds(12, 10, 240, 400);
		panel_search_pop_container.setLayout(null);
		panel_search_pop_container.add(n_panel, BorderLayout.NORTH);

		n_panel.setBounds(0, 0, 240, 144);
		panel_search_pop_container.add(n_panel);
		n_panel.setBackground(new Color(64, 64, 64, 200));
		n_panel.setLayout(null);

		JLabel la_closeX = new JLabel("X");
		la_closeX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		la_closeX.setFont(new Font("Arial Black", Font.BOLD, 18));
		la_closeX.setHorizontalAlignment(SwingConstants.CENTER);
		la_closeX.setBounds(197, 10, 31, 33);
		n_panel.add(la_closeX);
		la_closeX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				popup.hide();
				s_pop = false;
			}
		});

		JLabel la_title = new JLabel("검색");
		la_title.setForeground(new Color(169, 169, 169));
		la_title.setFont(new Font("HY견고딕", Font.PLAIN, 22));
		la_title.setHorizontalAlignment(SwingConstants.CENTER);
		la_title.setBounds(66, 21, 108, 33);
		n_panel.add(la_title);

		t_searchField = new JTextField();
		t_searchField.setForeground(Color.WHITE);
		t_searchField.setFont(new Font("HY견고딕", Font.BOLD, 16));
		t_searchField.setBackground(Color.BLACK);
		t_searchField.setBounds(22, 59, 198, 27);
		n_panel.add(t_searchField);
		t_searchField.setColumns(10);

		Choice choose_category = new Choice();
		choose_category.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		choose_category.setForeground(Color.black);
		choose_category.setBounds(22, 92, 91, 20);
		choose_category.add("Chat");
		choose_category.add("Board");
		choose_category.add("Files");
		n_panel.add(choose_category);

		TextField t_date = new TextField();
		t_date.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		t_date.setBounds(129, 92, 91, 20);
		n_panel.add(t_date);

		JLabel lblNewLabel = new JLabel("검색결과");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		lblNewLabel.setBounds(22, 120, 50, 15);
		n_panel.add(lblNewLabel);

		JPanel c_panel = new JPanel();
		c_panel.setBackground(new Color(64, 64, 64, 200));
		c_panel.setBounds(0, 142, 240, 258);
		panel_search_pop_container.add(c_panel);

		/*-------------------------------------------------------------------------------------
		 * 채팅생성 버튼 팝업 p_chat_set_pop
		   ------------------------------------------------------------------------------------*/

		p_chat_set_pop = new JPanel();
		p_chat_set_pop.setBackground(new Color(50, 50, 50, 180));
		p_chat_set_pop.setBounds(0, 0, 230, 405);
		p_chat_set_pop.setLayout(null);

		JLabel la_chat_pop_title = new JLabel("채팅 시작");
		la_chat_pop_title.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_title.setFont(new Font("HY견고딕", Font.PLAIN, 28));
		la_chat_pop_title.setBounds(57, 10, 150, 69);
		p_chat_set_pop.add(la_chat_pop_title);

		JLabel la_chat_pop_name = new JLabel("이름");
		la_chat_pop_name.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_name.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_chat_pop_name.setBounds(12, 68, 91, 40);
		p_chat_set_pop.add(la_chat_pop_name);

		t_chat_pop_name = new JTextField();
		t_chat_pop_name.setForeground(Color.WHITE);
		t_chat_pop_name.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		t_chat_pop_name.setBackground(Color.DARK_GRAY);
		t_chat_pop_name.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEFT, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		t_chat_pop_name.setBounds(12, 101, 207, 30);
		p_chat_set_pop.add(t_chat_pop_name);
		t_chat_pop_name.setColumns(10);

		JLabel la_chat_pop_auth = new JLabel("참여가능권한");
		la_chat_pop_auth.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_auth.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_chat_pop_auth.setBounds(12, 150, 101, 30);
		p_chat_set_pop.add(la_chat_pop_auth);

		Choice ch_chat_pop_auth = new Choice();
		ch_chat_pop_auth.setForeground(Color.WHITE);
		ch_chat_pop_auth.setBackground(Color.DARK_GRAY);
		ch_chat_pop_auth.setPreferredSize(new Dimension(7, 21));
		ch_chat_pop_auth.setBounds(12, 186, 207, 49);
		p_chat_set_pop.add(ch_chat_pop_auth);

		JLabel la_chat_pop_invite = new JLabel("참여 인원 선택");
		la_chat_pop_invite.setForeground(Color.LIGHT_GRAY);
		la_chat_pop_invite.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_chat_pop_invite.setBounds(12, 226, 126, 30);
		p_chat_set_pop.add(la_chat_pop_invite);

		JPanel p_chat_set_pop_checkPanel = new JPanel();
		p_chat_set_pop_checkPanel.setPreferredSize(new Dimension(10, 999999999));
		p_chat_set_pop_checkPanel.setBackground(Color.GRAY);

		JScrollPane p_chat_set_pop_scroll = new JScrollPane(p_chat_set_pop_checkPanel);
		p_chat_set_pop_scroll.setBounds(12, 266, 207, 81);
		p_chat_set_pop.add(p_chat_set_pop_scroll);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("babo!");
		chckbxNewCheckBox_1.setPreferredSize(new Dimension(160, 25));
		chckbxNewCheckBox_1.setFont(new Font("HY견고딕", Font.PLAIN, 13));
		chckbxNewCheckBox_1.setBackground(Color.GRAY);
		p_chat_set_pop_checkPanel.add(chckbxNewCheckBox_1);
		chckbxNewCheckBox_1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int x = frame.getLocationOnScreen().x;
					int y = frame.getLocationOnScreen().y;
					JCheckBox box = (JCheckBox) e.getItem();
					chatPopAddappendLabel(box.getText());
					popup_ch_add.hide();
					popup_ch_add = popupFactory.getPopup(frame, p_chat_set_pop, x + 415, y + 50);
					popup_ch_add.show();
					p_chat_set_pop_add_panel.updateUI();
					p_chat_set_pop_add_panel.repaint();
					p_chat_set_pop_add_container.updateUI();
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					JCheckBox box = (JCheckBox) e.getItem();
					int x = frame.getLocationOnScreen().x;
					int y = frame.getLocationOnScreen().y;
					popup_ch_add.hide();
					for (int i = 0; i < p_chat_set_pop_add_panel.getComponentCount(); i++) {
						JLabel label = (JLabel) p_chat_set_pop_add_panel.getComponent(i);
						if (box.getText().equals(label.getText())) {
							p_chat_set_pop_add_panel.remove(i);
							chatPopAddLabels.remove(i);
						} else {

						}
					}
					p_chat_set_pop_add_panel.updateUI();
					p_chat_set_pop_add_container.updateUI();
					popup_ch_add = popupFactory.getPopup(frame, p_chat_set_pop, x + 415, y + 50);
					p_chat_set_pop_add_panel.repaint();
					popup_ch_add.show();
				}

			}
		});

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("사용자이름");
		chckbxNewCheckBox_2.setPreferredSize(new Dimension(160, 25));
		chckbxNewCheckBox_2.setFont(new Font("HY견고딕", Font.PLAIN, 13));
		chckbxNewCheckBox_2.setBackground(Color.GRAY);
		p_chat_set_pop_checkPanel.add(chckbxNewCheckBox_2);
		chckbxNewCheckBox_2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int x = frame.getLocationOnScreen().x;
					int y = frame.getLocationOnScreen().y;
					JCheckBox box = (JCheckBox) e.getItem();
					chatPopAddappendLabel(box.getText());
					popup_ch_add.hide();
					popup_ch_add = popupFactory.getPopup(frame, p_chat_set_pop, x + 415, y + 50);
					popup_ch_add.show();
					p_chat_set_pop_add_panel.repaint();
					p_chat_set_pop_add_panel.updateUI();
					p_chat_set_pop_add_container.updateUI();
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					JCheckBox box = (JCheckBox) e.getItem();
					int x = frame.getLocationOnScreen().x;
					int y = frame.getLocationOnScreen().y;
					popup_ch_add.hide();
					for (int i = 0; i < p_chat_set_pop_add_panel.getComponentCount(); i++) {
						JLabel label = (JLabel) p_chat_set_pop_add_panel.getComponent(i);
						if (box.getText().equals(label.getText())) {
							p_chat_set_pop_add_panel.remove(i);
							chatPopAddLabels.remove(i);
						}
					}
					p_chat_set_pop_add_panel.repaint();
					p_chat_set_pop_add_panel.updateUI();
					p_chat_set_pop_add_container.updateUI();
					popup_ch_add = popupFactory.getPopup(frame, p_chat_set_pop, x + 415, y + 50);
					popup_ch_add.show();
				}
			}
		});

		JButton bt_chat_pop_cancel = new JButton("취소");
		bt_chat_pop_cancel.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		bt_chat_pop_cancel.setBounds(29, 359, 91, 23);
		p_chat_set_pop.add(bt_chat_pop_cancel);
		bt_chat_pop_cancel.addActionListener((e) -> {
			popup_ch.hide();
			popup_ch_add.hide();
			for (int i = 0; i < chatPopAddLabels.size(); i++) {
				p_chat_set_pop_add_panel.remove(chatPopAddLabels.get(i));
			}
			chatPopAddLabels.clear();
			c_pop = false;
			t_chat_pop_name.setText("");
		});

		JButton bt_chat_pop_ok = new JButton("시작");
		bt_chat_pop_ok.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		bt_chat_pop_ok.setBounds(132, 359, 91, 23);
		p_chat_set_pop.add(bt_chat_pop_ok);
		bt_chat_pop_ok.addActionListener((e) -> {

			/*------------------------------------------
			 * 쿼리문 실행 담기 (chat 테이블과, chatmember까지 채움)
			 --------------------------------------------*/
			chat_lib.insertChatTable();
			/*------------------------------------------
			 * 채팅생성버튼 -> 채팅팝업, 채팅팝업에드 2개패널생성
			 --------------------------------------------*/
			Object obj = e.getSource();
			if (obj == bt_chat_pop_ok) {
				chat_lib.createChatList(p_chat_south_center, t_chat_pop_name.getText(), 15);
				t_chat_pop_name.setText("");
				popup_ch.hide();
				popup_ch_add.hide();
				for (int i = 0; i < chatPopAddLabels.size(); i++) {
					p_chat_set_pop_add_panel.remove(chatPopAddLabels.get(i));
				}
				chatPopAddLabels.clear();
				c_pop = false;
				t_chat_pop_name.setText("");
			}
		});
		/*-------------------------------------------------------------------------------------
		 * 채팅 생성 add pop up 패널 p_chat_set_pop_add_container
		   ------------------------------------------------------------------------------------*/

		p_chat_set_pop_add_container = new JPanel();
		p_chat_set_pop_add_container.setBounds(0, 0, 180, 405);
		p_chat_set_pop_add_container.setBackground(new Color(50, 50, 50, 180));

		p_chat_set_pop_add_container.setLayout(new BorderLayout(0, 0));

		p_chat_set_pop_add_panel = new JPanel();
		p_chat_set_pop_add_panel.setPreferredSize(new Dimension(180, 405));
		p_chat_set_pop_add_panel.setBackground(new Color(50, 50, 50, 180));

		p_chat_pop_add_scrolls = new JScrollPane(p_chat_set_pop_add_panel);
		p_chat_pop_add_scrolls.setBackground(new Color(50, 50, 50, 180));
		p_chat_pop_add_scrolls.setPreferredSize(new Dimension(180, 405));

		p_chat_set_pop_add_container.add(p_chat_pop_add_scrolls, BorderLayout.CENTER);

		/*-------------------------------------------------------------------------------------
		 * 채팅 생성 add pop up 패널 끝
		   ------------------------------------------------------------------------------------*/

		/*-------------------------------------------------------------------------------------
		 * 게시판생성버튼 팝업 p_board_set_pop
		   ------------------------------------------------------------------------------------*/
		p_board_set_pop = new JPanel();
		p_board_set_pop.setBackground(new Color(32, 32, 32, 100));
		p_board_set_pop.setBounds(0, 0, 350, 400);
		p_board_set_pop.setLayout(null);

		JLabel la_board_pop_title = new JLabel("게시판 생성");
		la_board_pop_title.setForeground(Color.LIGHT_GRAY);
		la_board_pop_title.setFont(new Font("HY견고딕", Font.PLAIN, 28));
		la_board_pop_title.setBounds(115, 10, 150, 69);
		p_board_set_pop.add(la_board_pop_title);

		JLabel la_board_pop_name = new JLabel("이름");
		la_board_pop_name.setForeground(Color.LIGHT_GRAY);
		la_board_pop_name.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_board_pop_name.setBounds(12, 68, 91, 40);
		p_board_set_pop.add(la_board_pop_name);

		t_board_pop_name = new JTextField();
		t_board_pop_name.setForeground(Color.WHITE);
		t_board_pop_name.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		t_board_pop_name.setBackground(Color.DARK_GRAY);
		t_board_pop_name.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "",
				TitledBorder.LEFT, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		t_board_pop_name.setBounds(12, 101, 207, 30);
		p_board_set_pop.add(t_board_pop_name);
		t_board_pop_name.setColumns(10);

		JLabel la_board_pop_auth = new JLabel("참여가능권한");
		la_board_pop_auth.setForeground(Color.LIGHT_GRAY);
		la_board_pop_auth.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_board_pop_auth.setBounds(12, 150, 101, 30);
		p_board_set_pop.add(la_board_pop_auth);

		Choice ch_board_pop_auth = new Choice();
		ch_board_pop_auth.setForeground(Color.WHITE);
		ch_board_pop_auth.setBackground(Color.DARK_GRAY);
		ch_board_pop_auth.setPreferredSize(new Dimension(7, 21));
		ch_board_pop_auth.setBounds(12, 186, 207, 49);
		p_board_set_pop.add(ch_board_pop_auth);

		JLabel la_board_pop_invite = new JLabel("참여 인원 선택");
		la_board_pop_invite.setForeground(Color.LIGHT_GRAY);
		la_board_pop_invite.setFont(new Font("HY견고딕", Font.PLAIN, 16));
		la_board_pop_invite.setBounds(12, 226, 126, 30);
		p_board_set_pop.add(la_board_pop_invite);

		Choice ch_board_pop_invite = new Choice();
		ch_board_pop_invite.setPreferredSize(new Dimension(7, 21));
		ch_board_pop_invite.setForeground(Color.WHITE);
		ch_board_pop_invite.setBackground(Color.DARK_GRAY);
		ch_board_pop_invite.setBounds(12, 262, 207, 20);
		p_board_set_pop.add(ch_board_pop_invite);

		JButton bt_board_pop_cancel = new JButton("취소");
		bt_board_pop_cancel.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		bt_board_pop_cancel.setBounds(128, 359, 91, 23);
		p_board_set_pop.add(bt_board_pop_cancel);
		bt_board_pop_cancel.addActionListener((e) -> {
			popup.hide();
			s_pop = false;
		});
		JButton bt_board_pop_ok = new JButton("시작");
		bt_board_pop_ok.setFont(new Font("HY견고딕", Font.PLAIN, 12));
		bt_board_pop_ok.setBounds(231, 359, 91, 23);
		p_board_set_pop.add(bt_board_pop_ok);
		bt_board_pop_ok.addActionListener((e) -> {
			Object obj = e.getSource();
			if (obj == bt_board_pop_ok) {
				createBoardList(p_west_list, t_board_pop_name.getText(), 15);
				t_board_pop_name.setText("");
				popup.hide();
				s_pop = false;
			}
		});

		/*-------------------------------------------------------------------------------------
		 * 햄버거 버튼 팝업 생성 p_hamburger_pop_container
		   ------------------------------------------------------------------------------------*/
		p_hamburger_pop_container = new JPanel();
		JPanel p_hamburger_pop_n_panel = new JPanel();
		p_hamburger_pop_container.setBounds(12, 10, 240, 202);
		p_hamburger_pop_container.setLayout(null);
		p_hamburger_pop_container.add(p_hamburger_pop_n_panel, BorderLayout.NORTH);

		p_hamburger_pop_n_panel.setBounds(0, 0, 240, 35);
		p_hamburger_pop_container.add(p_hamburger_pop_n_panel);
		p_hamburger_pop_n_panel.setBackground(new Color(64, 64, 64, 200));
		p_hamburger_pop_n_panel.setLayout(null);

		JLabel p_hamburger_la_closeX = new JLabel("X");
		p_hamburger_la_closeX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_la_closeX.setFont(new Font("Arial Black", Font.BOLD, 18));
		p_hamburger_la_closeX.setHorizontalAlignment(SwingConstants.CENTER);
		p_hamburger_la_closeX.setBounds(197, 0, 31, 33);
		p_hamburger_pop_n_panel.add(p_hamburger_la_closeX);
		p_hamburger_la_closeX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				popup.hide();
				s_pop = false;
			}
		});

		JPanel p_hamburger_team_panel = new JPanel();
		p_hamburger_team_panel.setBounds(0, 32, 240, 67);
		p_hamburger_team_panel.setBackground(new Color(64, 64, 64, 200));
		p_hamburger_pop_container.add(p_hamburger_team_panel);
		p_hamburger_team_panel.setLayout(null);

		JLabel p_hamburger_pop_la_dev = new JLabel("dev");
		p_hamburger_pop_la_dev.setBounds(12, 10, 59, 26);
		p_hamburger_pop_la_dev.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_pop_la_dev.setForeground(new Color(169, 169, 169));
		p_hamburger_pop_la_dev.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		p_hamburger_team_panel.add(p_hamburger_pop_la_dev);

		JLabel p_hamburger_la_devContact = new JLabel("개발 팀 및 연락처");
		p_hamburger_la_devContact.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_la_devContact.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_la_devContact.setForeground(new Color(169, 169, 169));
		p_hamburger_la_devContact.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p_hamburger_la_devContact.setBounds(12, 36, 171, 26);
		p_hamburger_team_panel.add(p_hamburger_la_devContact);

		JPanel p_hamburger_logout_panel = new JPanel();
		p_hamburger_logout_panel.setBounds(0, 97, 240, 106);
		p_hamburger_logout_panel.setBackground(new Color(64, 64, 64, 200));
		p_hamburger_pop_container.add(p_hamburger_logout_panel);
		p_hamburger_logout_panel.setLayout(null);

		JLabel p_hamburger_pop_la_accSetting = new JLabel("개인설정");
		p_hamburger_pop_la_accSetting.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_pop_la_accSetting.setBounds(12, 20, 92, 24);
		p_hamburger_pop_la_accSetting.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_pop_la_accSetting.setForeground(new Color(169, 169, 169));
		p_hamburger_pop_la_accSetting.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p_hamburger_logout_panel.add(p_hamburger_pop_la_accSetting);

		JLabel p_hamburger_la_logout = new JLabel("로그아웃");
		p_hamburger_la_logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		p_hamburger_la_logout.setHorizontalAlignment(SwingConstants.LEFT);
		p_hamburger_la_logout.setForeground(new Color(169, 169, 169));
		p_hamburger_la_logout.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p_hamburger_la_logout.setBounds(12, 54, 99, 24);
		p_hamburger_logout_panel.add(p_hamburger_la_logout);

		/*-------------------------------------------------------------------------------------
		 * 팝업 패널에 대한 설정끝
		 ------------------------------------------------------------------------------------*/

		btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon("C:/workspace/Java_workspace/TeamProject/src/res/magnifier.png"));
		btnSearch.setFocusPainted(false);
		btnSearch.setContentAreaFilled(false);
		btnSearch.setBorder(null);
		p_east.add(btnSearch);
		// ----------------------------
		// 서치팝업 생성 후 검색버튼에 리스너부여
		popup = popupFactory.getPopup(frame, panel_search_pop_container, 1065, 211);
		btnSearch.addActionListener((e) -> {
			int x = btnSearch.getLocationOnScreen().x;
			int y = btnSearch.getLocationOnScreen().y;
			if (s_pop == false) {
				popup = popupFactory.getPopup(frame, panel_search_pop_container, x - 240, y);
				popup.show();
				MainApp.s_pop = true;
			} else {
				MainApp.s_pop = false;
				popup.hide();
			}
		});
		// -------------------------------

		JButton btnPreferences = new JButton("");
		btnPreferences.setIcon(new ImageIcon("C:/workspace/Java_workspace/TeamProject/src/res/gear.png.png"));
		btnPreferences.setFocusPainted(false);
		btnPreferences.setContentAreaFilled(false);
		btnPreferences.setBorder(null);
		p_east.add(btnPreferences);

		p_center = new JPanel();
		p_center.setBackground(new Color(64, 64, 64));
		p_center.setPreferredSize(new Dimension(900, 560));
		frame.getContentPane().add(p_center, BorderLayout.CENTER);
		p_center.setLayout(new BorderLayout(0, 0));

		JPanel p_center_north = new JPanel();
		p_center_north.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
		p_center_north.setBackground(new Color(64, 64, 64));
		p_center_north.setPreferredSize(new Dimension(900, 80));
		p_center.add(p_center_north, BorderLayout.NORTH);
		p_center_north.setLayout(null);

		JLabel la_Anounce = new JLabel("공지사항");
		la_Anounce.setBounds(14, 12, 107, 18);
		la_Anounce.setForeground(Color.WHITE);
		la_Anounce.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		p_center_north.add(la_Anounce);

		JLabel la_boardIssue = new JLabel("공지사항 이슈 들어갈 라벨");
		la_boardIssue.setForeground(Color.WHITE);
		la_boardIssue.setFont(new Font("HY견고딕", Font.PLAIN, 15));
		la_boardIssue.setBounds(37, 40, 659, 23);
		p_center_north.add(la_boardIssue);

		JPanel p_center_south = new JPanel();
		p_center_south.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		p_center_south.setPreferredSize(new Dimension(900, 180));
		p_center_south.setBackground(new Color(64, 64, 64));
		p_center.add(p_center_south, BorderLayout.SOUTH);
		p_center_south.setLayout(null);

		JButton btnNewButton = new JButton("+");
		btnNewButton.setBounds(14, 12, 41, 27);
		p_center_south.add(btnNewButton);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(69, 14, 865, 116);
		p_center_south.add(textArea);

		/*-------------------------------------------------------------------------------
		 * 가운데 채팅 패널 설정! 
		 -------------------------------------------------------------------------------*/

	}

}