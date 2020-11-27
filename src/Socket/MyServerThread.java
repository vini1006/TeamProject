package socket;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import layout.MainApp;

public class MyServerThread extends Thread {
	ServerSocket server;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	MyServerSocket myServerSocket;
	MainApp mainApp;
	boolean isAlive = true;
	boolean checkClient = true;
	String chat_id;
	String message_id; 
	String member_no;
	String current_memberName;
	
	SimpleDateFormat date_format = new SimpleDateFormat("MM/dd HH:mm");
	String current_time = date_format.format(System.currentTimeMillis());
	
	String current_messageId = "1"; //의미없는기본값.
	
	public int cnt;

	public MyServerThread(MyServerSocket myServerSocket, Socket socket,MainApp mainApp) {
		this.myServerSocket = myServerSocket;
		this.socket = socket;
		this.mainApp = mainApp;
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		listen();
	}

	public void listen() {
		try {
			String msg = null;
			JPanel youAlone_panel = null;
			while(isAlive) {
				if(cnt == 0) {
					msg = buffr.readLine();
					if(msg.equals("exit:931006")){
						myServerSocket.threadList.remove(this);
						isAlive = false;
						System.out.println("채팅창 나간데 ! msg : "+msg);
					}else if(msg.equals("#oneLeft:931006")) {
						getInfo(msg);
						send(msg);
					}else if(msg.equals("#outFromChat:931006")) {
						getInfo(msg);
						send(msg);
					}else {
						getInfo(msg);
						System.out.println("난 채팅창 바꿀떄 한번만 나타나야해!");
					}
				}else if(cnt > 0) {
					msg = buffr.readLine();
					if(msg.equals("exit:931006")) {
						myServerSocket.threadList.remove(this);
						isAlive = false;
						System.out.println("채팅창 나간데 ! msg : "+msg);
					}else if(msg.equals("chatChanged:931006")) {
						this.cnt = 0;
						System.out.println("채팅창 바꾼데 ! msg : "+msg);
					}else if(msg.equals("#oneLeft:931006")) {
						send(msg);
					}else if(msg.equals("#outFromChat:931006")) {
						send(msg);
					}else {
						System.out.println("정상적으로 메시지 받는다 ! msg : "+msg);
						dbWrite(msg);
						send(msg);
					}
				}
			}	
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void getInfo(String clientInfo) {
		String[] info = clientInfo.split(",");
		chat_id = info[0];
		System.out.println("받은 채팅 pk는 : "+chat_id);
		member_no = info[1];
		System.out.println("받은 회원 pk는 : "+member_no);
		current_memberName = info[2];
		System.out.println("받은 회원 이름은 : "+current_memberName);
		this.cnt = 1;
	}

	public void dbWrite(String content) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into message(chat_id, message_id, content, chat_time, member_no)";
		sql += " values(?, seq_message.nextval, ?, ?, ?)";
		String sql_getCurrentId = "select max(message_id) as message_id from message";


		try {
			pstmt = myServerSocket.getCon().prepareStatement(sql);
			pstmt.setString(1, chat_id);
			System.out.println("받은 챗 아이디 : "+chat_id);
			pstmt.setString(2, content);
			System.out.println("받은 콘텐트 : "+content);
			pstmt.setString(3, current_time);
			System.out.println("받은 현재시간 : " + current_time);
			pstmt.setString(4, member_no);
			System.out.println("받은 회원pk : "+ member_no);
			int isDone = pstmt.executeUpdate();
			if (isDone == 0) {
				System.exit(0);
			} else {
			}
		
			pstmt = myServerSocket.getCon().prepareStatement(sql_getCurrentId);
			rs = pstmt.executeQuery();
			rs.next();
			current_messageId = rs.getString("message_id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void send(String msg) {
		try {
			for (int i = 0; i < myServerSocket.threadList.size(); i++) {
				MyServerThread myServerThread = myServerSocket.threadList.get(i);
				myServerThread.buffw.write(chat_id + ","+ current_time+","+ member_no + ","+ current_messageId+","+current_memberName+","+msg + "\n");
				System.out.println(chat_id + ","+ current_time+","+ member_no + ","+ current_messageId+","+current_memberName+","+msg + "\n");
				myServerThread.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
