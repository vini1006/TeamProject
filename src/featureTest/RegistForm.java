//회원가입
//회원가임
package featureTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;

public class RegistForm {
   
   public static final int WIDTH = 450;
   public static final int HEIGHT = 630;
   

   private JFrame frame;
   private JTextField t_name;
   JPanel opaqPanel;
   private JTextField t_email;
   private JTextField t_phone;
   private JTextField t_id;
   private JPasswordField passwordField;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               RegistForm window = new RegistForm();
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
   public RegistForm() {
      initialize();
   }
   
   public void show() {
      frame.setVisible(true);
   }
   
   public void hide() {
      frame.setVisible(false);
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize() {
      frame = new JFrame();
      frame.setResizable(false);
      frame.setBounds(100, 100, WIDTH, HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

      
      JPanel panel = new JPanel();
      frame.getContentPane().add(panel);
      panel.setLayout(new BorderLayout(0, 0));
      
      JPanel n_panel = new JPanel();
      n_panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      n_panel.setBackground(Color.WHITE);
      n_panel.setPreferredSize(new Dimension(WIDTH, 50));
      
      panel.add(n_panel, BorderLayout.NORTH);
      
      JPanel c_panel = new JPanel(); 
      
      c_panel.setOpaque(false);
      c_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
      c_panel.setBackground(SystemColor.controlLtHighlight);
      panel.add(c_panel, BorderLayout.CENTER);
      c_panel.setLayout(null);
      
      opaqPanel = new JPanel();
      opaqPanel.setBackground(SystemColor.controlShadow);
      opaqPanel.setBounds(0, 0, 446, 512);
      c_panel.add(opaqPanel);
      opaqPanel.setLayout(null);
      
      JLabel signinLabel = new JLabel("\uB85C\uADF8\uC778");
      signinLabel.setBounds(12, 10, 110, 22);
      opaqPanel.add(signinLabel);
      signinLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      signinLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));
      
      JLabel name_label = new JLabel("NAME");
      name_label.setBounds(104, 30, 191, 32);
      opaqPanel.add(name_label);
      name_label.setFont(new Font("Arial Black", Font.BOLD, 22));
      
      t_name = new JTextField();
      t_name.setBounds(92, 73, 231, 32);
      opaqPanel.add(t_name);
      t_name.setToolTipText("");
      t_name.setFont(new Font("Arial Black", Font.BOLD, 21));
      t_name.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      t_name.setColumns(10);
      
      JLabel pass_label = new JLabel("PASSWORD");
      pass_label.setBounds(104, 263, 191, 22);
      opaqPanel.add(pass_label);
      pass_label.setFont(new Font("Arial Black", Font.BOLD, 22));
      
      
      JLabel email_label = new JLabel("E-mail");
      email_label.setFont(new Font("Arial Black", Font.BOLD, 22));
      email_label.setBounds(104, 115, 191, 22);
      opaqPanel.add(email_label);
      
      t_email = new JTextField();
      t_email.setColumns(10);
      t_email.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      t_email.setBounds(92, 147, 87, 32);
      opaqPanel.add(t_email);
      
      JLabel phone_label = new JLabel("PHONE");
      phone_label.setFont(new Font("Arial Black", Font.BOLD, 22));
      phone_label.setBounds(104, 344, 191, 22);
      opaqPanel.add(phone_label);
      
      JButton bt_login = new JButton("\uD68C\uC6D0\uAC00\uC785");
      bt_login.setBounds(92, 432, 231, 32);
      opaqPanel.add(bt_login);
      bt_login.setBackground(new Color(0, 153, 51));
      bt_login.setForeground(new Color(255, 255, 255));
      bt_login.setFont(new Font("HY견고딕", Font.BOLD, 20));
      bt_login.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            System.out.println("회원가입 가자잇!");
         }
      });
      bt_login.setBorder(new LineBorder(new Color(0, 153, 51), 5, true));
      
      t_phone = new JTextField();
      t_phone.setColumns(10);
      t_phone.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      t_phone.setBounds(164, 376, 159, 32);
      opaqPanel.add(t_phone);
      
      JLabel id_label = new JLabel("ID");
      id_label.setFont(new Font("Arial Black", Font.BOLD, 22));
      id_label.setBounds(104, 189, 191, 22);
      opaqPanel.add(id_label);
      
      t_id = new JTextField();
      t_id.setColumns(10);
      t_id.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      t_id.setBounds(92, 221, 231, 32);
      opaqPanel.add(t_id);
      
      JComboBox comboBox = new JComboBox();
      comboBox.setModel(new DefaultComboBoxModel(new String[] {"010", "011", "017", "019", "080", "070"}));
      comboBox.setBounds(92, 376, 65, 32);
      opaqPanel.add(comboBox);
      
      passwordField = new JPasswordField();
      passwordField.setEchoChar('*');
      passwordField.setBounds(92, 295, 231, 32);
      opaqPanel.add(passwordField);
      passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
      
      JComboBox comboBox_1 = new JComboBox();
      comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "gamil.com", "hanmail.net", "nate.com", "nexon.com", "netmarble.com", "daun.com"}));
      comboBox_1.setBounds(202, 147, 121, 32);
      opaqPanel.add(comboBox_1);
      
      JLabel name_label_1 = new JLabel("@");
      name_label_1.setFont(new Font("Arial Black", Font.BOLD, 22));
      name_label_1.setBounds(180, 147, 21, 32);
      opaqPanel.add(name_label_1);
      signinLabel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseReleased(MouseEvent e) {
            System.out.println("로그인창");
         }
      });
   }
   

}