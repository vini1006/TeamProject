package featureTest;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

public class Testing {
   JScrollPane chatListScroll, boardListScroll;
   JPanel p_west_south_inChatList;
   private JFrame frame;
   JTextField t_chat_pop_name, t_board_pop_name;
   ArrayList<JPanel> panels = new ArrayList<JPanel>();
   ArrayList<JLabel> labels = new ArrayList<JLabel>();
   ArrayList<JPanel> boardPanels = new ArrayList<JPanel>();
   ArrayList<JLabel> boardPanellabels = new ArrayList<JLabel>();
   JPanel p_west_south_chatList,p_west_south_chat;
   JPanel p_chat_south_center;
   JTextField p_search_pop_t_searchField;
   JPanel p_search_pop_container;
   JPanel p_east;
   
   //보더 동쪽 버튼 팝업 패널들 공통요소들선언//
   
   
   //-----------------------------------
   
   PopupFactory popupFactory;
   JPanel p_chat_set_pop;
   JPanel p_board_set_pop;
   Popup popup_search; 
   
   public static boolean s_pop = false;
   
   JButton btnSearch;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Testing window = new Testing();
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
   public Testing() {
      initialize();
   }
      
   /**
    * Initialize the contents of the frame.
    */
   
   /*---------------------------------------------------
    * 게시판 패널 생성메서드
    ---------------------------------------------------*/
   public void createBoardList(JPanel panel, String folderName, int fontSize) {
			Font font = new Font("HY견고딕", Font.BOLD, fontSize);
			JLabel label = new JLabel(folderName, 10);
			label.setBounds(30, 0,240,40);
			label.setFont(font);
			boardPanellabels.add(label);
			JPanel panel1 = new JPanel();
			panel1.add(boardPanellabels.get(boardPanellabels.size()-1), BorderLayout.WEST);
			panel1.setBackground(new Color(64, 64, 64, 60));
			panel1.setPreferredSize(new Dimension(240, 40));
			panel1.setCursor(new Cursor(Cursor.HAND_CURSOR));			
			panel1.setLayout(null);
			
			if(boardPanels.size()<=0) {
				panel1.setBounds(0, 0, 240, 40);
			}else {
				panel1.setBounds(boardPanels.get(boardPanels.size()-1).getX(), boardPanels.get(boardPanels.size()-1).getY()+40,240,40);
			}
			boardPanels.add(panel1);
		
			panel.add(boardPanels.get(boardPanels.size()-1));
			if((boardPanels.size() * 40) > panel.getWidth()) {
				panel.setPreferredSize(new Dimension(240, boardPanels.size() * 40));
			}
			panel.updateUI();
		}
   
   /*---------------------------------------------------
    * 채팅창 패널 생성메서드
    ---------------------------------------------------*/
   public void createFolder(JPanel panel, String folderName, int fontSize) {
			Font font = new Font("HY견고딕", Font.BOLD, fontSize);
			JLabel label = new JLabel(folderName, 10);
			label.setHorizontalAlignment(SwingConstants.LEFT);
			label.setFont(font);
			labels.add(label);
			JPanel panel1 = new JPanel();
			panel1.add(labels.get(labels.size()-1));
			panel1.setBackground(new Color(0, 0, 0, 60));
			panel1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			panel1.setPreferredSize(new Dimension(240, 40));
			panel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
			MouseAdapter chat_m_adapt = new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					System.out.println("들");
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					System.out.println("나");
				}
			};
			
			if(panels.size()<=0) {
				panel1.setBounds(0, 0, 240, 40);
			}else {
				panel1.setBounds(panels.get(panels.size()-1).getX(), panels.get(panels.size()-1).getY()+40,240,40);
			}
			
			panels.add(panel1);
			for(int i =0; i < panels.size(); i++) {
				panels.get(i).addMouseListener(chat_m_adapt);
			}
			panel.add(panels.get(panels.size()-1));
			panel.setPreferredSize(new Dimension(240, panel.getHeight()+40));
			panel.updateUI();
			p_west_south_chat.updateUI();
		}
   
   /*---------------------------------------------------
    * 채팅창 패널 생성메서드 끝
    ---------------------------------------------------*/
   
   private void initialize() {
	   try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//	    	UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
//	    	UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

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
      p_west.setPreferredSize(new Dimension(240,960));
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
      
      JLabel la_userName = new JLabel("사용자명");
      la_userName.setFont(new Font("HY견고딕", Font.BOLD, 15));
      la_userName.setForeground(Color.WHITE);
      la_userName.setBounds(79, 48, 89, 18);
      p_west_north_title.add(la_userName);
      
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
      		/*------------------------------------------
      		 * 채팅생성버튼에 팝업달기
      		 --------------------------------------------*/
      	  int x = frame.getLocationOnScreen().x;
      	  int y = frame.getLocationOnScreen().y;
      	  if(s_pop == false) {
      		  popup_search = popupFactory.getPopup(frame, p_chat_set_pop, x+415,y+50);
      		  popup_search.show();
      		  Testing.s_pop = true;
      	  }else{
      		  Testing.s_pop = false;
      		  popup_search.hide();
      	  }
      		
      	}
      });
      bt_chat.setBounds(173, 4, 41, 27);
      p_west_south_chatTitle.add(bt_chat);
      
      //-------------bt_chat 끝------
      JLabel la_chatTitle = new JLabel("채팅(Chat)");
      la_chatTitle.setForeground(Color.WHITE);
      la_chatTitle.setFont(new Font("HY견고딕", Font.PLAIN, 15));
      la_chatTitle.setBounds(14, 10, 140, 18);
      p_west_south_chatTitle.add(la_chatTitle);
      
      
      p_west_south_chatList = new JPanel();
      p_chat_south_center = new JPanel();
      p_chat_south_center.setBackground(Color.DARK_GRAY);
      p_chat_south_center.setPreferredSize(new Dimension(240,80));
      chatListScroll = new JScrollPane(p_chat_south_center,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      p_chat_south_center.setLayout(null);
      p_west_south_chatList.setBackground(new Color(64, 64, 64));
      p_west_south_chatList.setPreferredSize(new Dimension(240, 0));
      
//      p_west_south_chatList.setLayout(null);
//      p_west_south.add(chatListScroll);
      
      p_west_south_chat = new JPanel();
      p_west_south_chat.setOpaque(false);
      p_west_south_chat.setBorder(new LineBorder(new Color(0, 0, 0)));
      p_west_south_chat.setBackground(new Color(64,64,64));
      p_west_south.add(p_west_south_chat, BorderLayout.CENTER);
      p_west_south_chat.setLayout(new BorderLayout(0, 0));
      p_west_south_chat.add(chatListScroll);
//      p_west_south_chat.setLayout();
      
      JPanel p_west_list = new JPanel();
      p_west_list.setBackground(new Color(64, 64, 64));
      boardListScroll = new JScrollPane(p_west_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      p_west_list.setPreferredSize(new Dimension(0, 0));
      p_west.add(boardListScroll, BorderLayout.CENTER);
      p_west_list.setLayout(null);
      
      JButton bt_board = new JButton("+");
      bt_board.setBounds(185, 7, 41, 27);
      p_west_north_boardTitle.add(bt_board);
      bt_board.addActionListener((e)->{
    	  //게시판 팝업
    	  int x = frame.getLocationOnScreen().x;
    	  int y = frame.getLocationOnScreen().y;
    	  if(s_pop == false) {
    		  popup_search = popupFactory.getPopup(frame, p_board_set_pop, x+415,y+50);
    		  popup_search.show();
    		  Testing.s_pop = true;
    	  }else{
    		  Testing.s_pop = false;
    		  popup_search.hide();
    	  }
      });
      
      p_east = new JPanel();
      p_east.setBackground(new Color(64, 64, 64));
      p_east.setPreferredSize(new Dimension(70, 960));
      frame.getContentPane().add(p_east, BorderLayout.EAST);
      p_east.setLayout(new GridLayout(9, 1, 0, 5));
      p_east.addMouseListener(new MouseAdapter() {
    	  @Override
    	public void mouseClicked(MouseEvent e) {
    		  System.out.println(e.getX());
    		  System.out.println(e.getY());
    	}
	});
      
      JButton btnHamburger = new JButton("");
      btnHamburger.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\hamburger.png"));
      btnHamburger.setFocusPainted(false);
      btnHamburger.setContentAreaFilled(false);
      btnHamburger.setBorder(null);
      p_east.add(btnHamburger);
      
      /*-------------------------------------------------------------------------------------
       *  서치 팝업창 panel_pop_container
       ------------------------------------------------------------------------------------*/
  	p_search_pop_container = new JPanel();
	JPanel p_search_pop_n_panel = new JPanel();
	p_search_pop_container.setBounds(12, 10, 240, 400);
	p_search_pop_container.setLayout(null);
	p_search_pop_container.add(p_search_pop_n_panel, BorderLayout.NORTH);
	
	
	p_search_pop_n_panel.setBounds(0, 0, 240, 144);
	p_search_pop_container.add(p_search_pop_n_panel);
	p_search_pop_n_panel.setBackground(new Color(64,64,64,200));
	p_search_pop_n_panel.setLayout(null);
	
	JLabel p_search_pop_la_closeX = new JLabel("X");
	p_search_pop_la_closeX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	p_search_pop_la_closeX.setFont(new Font("Arial Black", Font.BOLD, 18));
	p_search_pop_la_closeX.setHorizontalAlignment(SwingConstants.CENTER);
	p_search_pop_la_closeX.setBounds(197, 10, 31, 33);
	p_search_pop_n_panel.add(p_search_pop_la_closeX);
	p_search_pop_la_closeX.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			popup_search.hide();
			s_pop = false;
		}
	});
	
	JLabel p_search_pop_la_title = new JLabel("검색");
	p_search_pop_la_title.setForeground(new Color(169, 169, 169));
	p_search_pop_la_title.setFont(new Font("HY견고딕", Font.PLAIN, 22));
	p_search_pop_la_title.setHorizontalAlignment(SwingConstants.CENTER);
	p_search_pop_la_title.setBounds(66, 21, 108, 33);
	p_search_pop_n_panel.add(p_search_pop_la_title);
	
	p_search_pop_t_searchField = new JTextField();
	p_search_pop_t_searchField.setForeground(Color.WHITE);
	p_search_pop_t_searchField.setFont(new Font("HY견고딕", Font.BOLD, 16));
	p_search_pop_t_searchField.setBackground(Color.BLACK);
	p_search_pop_t_searchField.setBounds(22, 59, 198, 27);
	p_search_pop_n_panel.add(p_search_pop_t_searchField);
	p_search_pop_t_searchField.setColumns(10);
	
	Choice p_search_pop_choose_category = new Choice();
	p_search_pop_choose_category.setFont(new Font("HY견고딕", Font.PLAIN, 15));
	p_search_pop_choose_category.setBounds(22, 92, 91, 20);
	p_search_pop_choose_category.add("Chat");
	p_search_pop_choose_category.add("Board");
	p_search_pop_choose_category.add("Files");
	p_search_pop_n_panel.add(p_search_pop_choose_category);
	
	TextField t_date = new TextField();
	t_date.setFont(new Font("HY견고딕", Font.PLAIN, 15));
	t_date.setBounds(129, 92, 91, 20);
	p_search_pop_n_panel.add(t_date);
	
	JLabel p_search_pop_la_result = new JLabel("검색결과");
	p_search_pop_la_result.setForeground(Color.LIGHT_GRAY);
	p_search_pop_la_result.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	p_search_pop_la_result.setBounds(22, 120, 50, 15);
	p_search_pop_n_panel.add(p_search_pop_la_result);
	
	JPanel p_search_pop_p_result = new JPanel();
	p_search_pop_p_result.setBackground(new Color(64,64,64,200));
	p_search_pop_p_result.setBounds(0, 142, 240, 258);
	p_search_pop_container.add(p_search_pop_p_result);
	
	
	
	/*-------------------------------------------------------------------------------------
	 * 채팅생성버튼 팝업 p_chat_set_pop
       ------------------------------------------------------------------------------------*/
	p_chat_set_pop = new JPanel();
	p_chat_set_pop.setBackground(new Color(32,32,32,100));
	p_chat_set_pop.setBounds(0, 0, 350, 400);
	p_chat_set_pop.setLayout(null);
	
	JLabel la_chat_pop_title = new JLabel("채팅 시작");
	la_chat_pop_title.setForeground(Color.LIGHT_GRAY);
	la_chat_pop_title.setFont(new Font("HY견고딕", Font.PLAIN, 28));
	la_chat_pop_title.setBounds(115, 10, 150, 69);
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
	t_chat_pop_name.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEFT, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
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
	
	Choice ch_chat_pop_invite = new Choice();
	ch_chat_pop_invite.setPreferredSize(new Dimension(7, 21));
	ch_chat_pop_invite.setForeground(Color.WHITE);
	ch_chat_pop_invite.setBackground(Color.DARK_GRAY);
	ch_chat_pop_invite.setBounds(12, 262, 207, 20);
	p_chat_set_pop.add(ch_chat_pop_invite);
	
	JButton bt_chat_pop_cancel = new JButton("취소");
	bt_chat_pop_cancel.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	bt_chat_pop_cancel.setBounds(128, 359, 91, 23);
	p_chat_set_pop.add(bt_chat_pop_cancel);
	bt_chat_pop_cancel.addActionListener((e)->{
			popup_search.hide();
			s_pop = false;
	});
	JButton bt_chat_pop_ok = new JButton("시작");
	bt_chat_pop_ok.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	bt_chat_pop_ok.setBounds(231, 359, 91, 23);
	p_chat_set_pop.add(bt_chat_pop_ok);
	bt_chat_pop_ok.addActionListener((e)->{
		Object obj = e.getSource();
  		if(obj == bt_chat_pop_ok) {
  			createFolder(p_chat_south_center, t_chat_pop_name.getText(), 15);
  			t_chat_pop_name.setText("");
  			popup_search.hide();
			s_pop = false;
  		}
	});
	
	/*-------------------------------------------------------------------------------------
	 * 게시판생성버튼 팝업 p_board_set_pop
       ------------------------------------------------------------------------------------*/
	p_board_set_pop = new JPanel();
	p_board_set_pop.setBackground(new Color(32,32,32,100));
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
	t_board_pop_name.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEFT, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
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
	bt_board_pop_cancel.addActionListener((e)->{
			popup_search.hide();
			s_pop = false;
	});
	JButton bt_board_pop_ok = new JButton("시작");
	bt_board_pop_ok.setFont(new Font("HY견고딕", Font.PLAIN, 12));
	bt_board_pop_ok.setBounds(231, 359, 91, 23);
	p_board_set_pop.add(bt_board_pop_ok);
	bt_board_pop_ok.addActionListener((e)->{
		Object obj = e.getSource();
  		if(obj == bt_board_pop_ok) {
  			createBoardList(p_west_list, t_board_pop_name.getText(), 15);
  			t_board_pop_name.setText("");
  			popup_search.hide();
			s_pop = false;
  		}
	});
	
      /*-------------------------------------------------------------------------------------
       * 팝업 패널에 대한 설정끝
       ------------------------------------------------------------------------------------*/
      
      btnSearch = new JButton("");
      btnSearch.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\magnifier.png"));
      btnSearch.setFocusPainted(false);
      btnSearch.setContentAreaFilled(false);
      btnSearch.setBorder(null);
      p_east.add(btnSearch);
      //----------------------------
      //서치팝업 생성 후 검색버튼에 리스너부여
      popup_search = popupFactory.getPopup(frame, p_search_pop_container,1065,211);
      btnSearch.addActionListener((e)->{
    	  int x = btnSearch.getLocationOnScreen().x;
    	  int y = btnSearch.getLocationOnScreen().y;
    	  if(s_pop == false) {
    		  popup_search = popupFactory.getPopup(frame, p_search_pop_container, x-240,y);
    		  popup_search.show();
    		  Testing.s_pop = true;
    	  }else{
    		  Testing.s_pop = false;
    		  popup_search.hide();
    	  }
      });
      //-------------------------------
      
      
      JButton btnPreferences = new JButton("");
      btnPreferences.setIcon(new ImageIcon("C:\\workspace\\Java_workspace\\TeamProject\\src\\res\\gear.png.png"));
      btnPreferences.setFocusPainted(false);
      btnPreferences.setContentAreaFilled(false);
      btnPreferences.setBorder(null);
      p_east.add(btnPreferences);
      
      JPanel p_center = new JPanel();
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
      
      JPanel p_chat_big = new JPanel();
      p_chat_big.setPreferredSize(new Dimension(0, 0));
      p_chat_big.setBorder(new LineBorder(new Color(0, 0, 0)));
      p_center.add(p_chat_big, BorderLayout.CENTER);
      p_chat_big.setBackground(new Color(64, 64, 64));
   }
}