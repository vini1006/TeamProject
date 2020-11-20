package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Main {
   JScrollPane chatListScroll;
   private JFrame frame;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               Main window = new Main();
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
   public Main() {
      initialize();
   }
      
   /**
    * Initialize the contents of the frame.
    */
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
      
      JButton btnNewButton_1 = new JButton("+");
      btnNewButton_1.setBounds(185, 7, 41, 27);
      p_west_north_boardTitle.add(btnNewButton_1);
      
      JPanel p_west_south = new JPanel();
      p_west_south.setBackground(Color.RED);
      p_west_south.setPreferredSize(new Dimension(240, 220));
      p_west.add(p_west_south, BorderLayout.SOUTH);
      p_west_south.setLayout(new BorderLayout(0, 0));
      
      JPanel p_west_south_chatTitle = new JPanel();
      p_west_south_chatTitle.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
      p_west_south_chatTitle.setBackground(new Color(64, 64, 64));
      p_west_south_chatTitle.setPreferredSize(new Dimension(240, 40));
      p_west_south.add(p_west_south_chatTitle, BorderLayout.NORTH);
      p_west_south_chatTitle.setLayout(null);
      
      JButton btnNewButton_2 = new JButton("+");
      btnNewButton_2.setBounds(173, 4, 41, 27);
      p_west_south_chatTitle.add(btnNewButton_2);
      
      JLabel la_chatTitle = new JLabel("채팅(Chat)");
      la_chatTitle.setForeground(Color.WHITE);
      la_chatTitle.setFont(new Font("HY견고딕", Font.PLAIN, 15));
      la_chatTitle.setBounds(14, 10, 140, 18);
      p_west_south_chatTitle.add(la_chatTitle);
      JPanel p_west_south_chatList = new JPanel();
      chatListScroll = new JScrollPane(p_west_south_chatList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      p_west_south_chatList.setBackground(new Color(64, 64, 64));
      p_west_south_chatList.setPreferredSize(new Dimension(240, 180));
      p_west_south.add(chatListScroll, BorderLayout.SOUTH);
      
      JPanel p_west_list = new JPanel();
      p_west_list.setBackground(new Color(64, 64, 64));
      p_west_list.setPreferredSize(new Dimension(240, 460));
      p_west.add(p_west_list, BorderLayout.CENTER);
      p_west_list.setLayout(new GridLayout(1, 0, 0, 0));
      
      JPanel p_east = new JPanel();
      p_east.setBackground(new Color(64, 64, 64));
      p_east.setPreferredSize(new Dimension(60, 960));
      frame.getContentPane().add(p_east, BorderLayout.EAST);
      
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
      
      JPanel p_center_south = new JPanel();
      p_center_south.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
      p_center_south.setPreferredSize(new Dimension(900, 160));
      p_center_south.setBackground(new Color(64, 64, 64));
      p_center.add(p_center_south, BorderLayout.SOUTH);
      p_center_south.setLayout(null);
      
      JButton btnNewButton = new JButton("+");
      btnNewButton.setBounds(14, 12, 41, 27);
      p_center_south.add(btnNewButton);
      
      JTextArea textArea = new JTextArea();
      textArea.setBounds(69, 14, 865, 116);
      p_center_south.add(textArea);
      
      JPanel p_chat = new JPanel();
      p_chat.setBorder(new LineBorder(new Color(0, 0, 0)));
      p_center.add(p_chat, BorderLayout.CENTER);
      p_chat.setBackground(new Color(64, 64, 64));
   }
}