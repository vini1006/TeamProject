package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import utill.DBManager;

public class RegistForm extends JFrame {

//	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	private static final String user = "koreait"; // DB ID
//	private static final String pass = "1234"; // DB 패스워드
	private static final String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
	private static final String user = "user1104"; // DB ID
	private static final String pass = "user1104"; // DB 패스워드

	public static final int WIDTH = 450;
	public static final int HEIGHT = 630;

	private JFrame frame;
	JTextField t_name;
	JPanel opaqPanel;
	JTextField t_email;
	JTextField t_id;
	JPasswordField t_password;
	JComboBox t_rank;
	LoginPage loginPage;
	DBManager dbManager;
	Connection con;

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
		
		initialize(dbManager);
	}

	public void registShow() {
		frame.setVisible(true);
	}

	public void registHide() {
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize(DBManager dbManager) {
		this.dbManager = dbManager;

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
		opaqPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		opaqPanel.setBackground(SystemColor.controlShadow);
		opaqPanel.setBounds(12, 0, 446, 512);
		c_panel.add(opaqPanel);
		opaqPanel.setLayout(null);

		JLabel loginLabel = new JLabel("\uB85C\uADF8\uC778");
		loginLabel.setBounds(12, 10, 110, 22);
		opaqPanel.add(loginLabel);
		loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLabel.setFont(new Font("HY견고딕", Font.PLAIN, 16));

		JLabel name_label = new JLabel("NAME");
		name_label.setBounds(104, 30, 191, 32);
		opaqPanel.add(name_label);
		name_label.setFont(new Font("Arial Black", Font.BOLD, 22));

		t_name = new JTextField(20);
		t_name.setBounds(92, 73, 231, 32);
		opaqPanel.add(t_name);
		t_name.setToolTipText("");
		t_name.setFont(new Font("Arial Black", Font.BOLD, 21));
		t_name.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		t_name.setColumns(10);

		JLabel password_label = new JLabel("PASSWORD");
		password_label.setBounds(104, 263, 191, 22);
		opaqPanel.add(password_label);
		password_label.setFont(new Font("Arial Black", Font.BOLD, 22));

		JLabel email_label = new JLabel("E-mail");
		email_label.setFont(new Font("Arial Black", Font.BOLD, 22));
		email_label.setBounds(104, 115, 191, 22);
		opaqPanel.add(email_label);

		t_email = new JTextField(200);
		t_email.setColumns(10);
		t_email.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		t_email.setBounds(92, 147, 231, 32);
		opaqPanel.add(t_email);

		JButton bt_regist = new JButton("회원가입 완료");
		bt_regist.setBounds(92, 419, 231, 45);
		opaqPanel.add(bt_regist);
		bt_regist.setBackground(new Color(0, 153, 51));
		bt_regist.setForeground(new Color(255, 255, 255));
		bt_regist.setFont(new Font("HY견고딕", Font.BOLD, 20));
		bt_regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertMember();

			}
		});
		bt_regist.setBorder(new LineBorder(new Color(0, 153, 51), 5, true));

		JLabel id_label = new JLabel("ID");
		id_label.setFont(new Font("Arial Black", Font.BOLD, 22));
		id_label.setBounds(104, 189, 191, 22);
		opaqPanel.add(id_label);

		t_id = new JTextField(20);
		t_id.setColumns(10);
		t_id.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		t_id.setBounds(92, 221, 231, 32);
		opaqPanel.add(t_id);

		t_password = new JPasswordField(20);
		t_password.setEchoChar('*');
		t_password.setBounds(92, 295, 231, 32);
		opaqPanel.add(t_password);
		t_password.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		JLabel rank_label = new JLabel("RANK");
		rank_label.setFont(new Font("Arial Black", Font.BOLD, 22));
		rank_label.setBounds(104, 337, 191, 22);
		opaqPanel.add(rank_label);

		t_rank = new JComboBox();
		t_rank.setModel(new DefaultComboBoxModel(new String[] { "선택", "브라만", "크샤트리아", "바이샤", "수드라" }));
		t_rank.setBounds(92, 369, 231, 32);
		opaqPanel.add(t_rank);

		loginLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("로그인창");
				loginPage = new LoginPage();
				loginPage.loginShow();
				registHide();
			}
		});
	}

	public void insertMember() {

		PreparedStatement psmt = null; // 명령

		try {
			String sql = "insert into RegistMember(member_no, member_name,member_email,member_id ,member_password,member_rank)";
			sql += " values(seq_RegistMember.nextval, ?,?,?,?,?)";

			con = DriverManager.getConnection(DBManager.url, DBManager.user, DBManager.password);
			psmt = con.prepareStatement(sql);
			psmt.setString(1, t_name.getText());
			psmt.setString(2, t_email.getText());
			psmt.setString(3, t_id.getText());
			psmt.setString(4, new String(t_password.getPassword()));
			psmt.setString(5, t_rank.getSelectedItem().toString());
			int r = psmt.executeUpdate(); // 실행 -> 저장

			if (r > 0) {
				JOptionPane.showMessageDialog(null, "가입완료!!");
				loginPage = new LoginPage();
				loginPage.loginShow();
				registHide();
			} else {
				JOptionPane.showMessageDialog(null, "가입실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
