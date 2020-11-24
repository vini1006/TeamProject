package socket;

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

import javax.swing.JOptionPane;

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
			String clientInfo = null;
			while(isAlive) {
				if(checkClient) {
					//처음 채팅을 킬때, 채팅창의 정보와 그사람의 정보가 넘어올것임
					//chat_id, member_no
					clientInfo = buffr.readLine();
					System.out.println("첫정보 받는중 "+clientInfo);
					String[] info = clientInfo.split(",");
					chat_id = info[0];
					member_no = info[1];
					checkClient = false;
				}else {
					msg = buffr.readLine();
					System.out.println("앙");
					if(msg.equals("exit:931006")) {
						myServerSocket.threadList.remove(this);
//						isAlive = false;
						System.out.println("채팅창 나감");
					}else {
						dbWrite(msg);
						System.out.println(msg);
						send(msg);
					}
				}
			}	
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void dbWrite(String content) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into message(chat_id, message_id, content, chat_time, member_no)";
		sql += " values(?, seq_message.nextval, ?, ?, ?)";

		SimpleDateFormat date_format = new SimpleDateFormat("MM월dd일 HH시mm분ss초");
		String current_time = date_format.format(System.currentTimeMillis());

		try {
			pstmt = mainApp.con.prepareStatement(sql);
			pstmt.setString(1, chat_id);
			pstmt.setString(2, content);
			pstmt.setString(3, current_time);
			pstmt.setString(4, member_no);
			int isDone = pstmt.executeUpdate();
			if (isDone == 0) {
				JOptionPane.showMessageDialog(mainApp.frame, "입력실패!");
			} else {
				JOptionPane.showMessageDialog(mainApp.frame, "입력성공!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void send(String msg) {
		try {
			for (int i = 0; i < myServerSocket.threadList.size(); i++) {
				MyServerThread myServerThread = myServerSocket.threadList.get(i);
				myServerThread.buffw.write(member_no + "," + msg + "\n");
				myServerThread.buffw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
