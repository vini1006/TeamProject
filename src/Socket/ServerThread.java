package Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread {
	ServerSocket server;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	String content;
	ServerSocket serverSocket;
	
	public ServerThread(ServerSocket serverSocket, Socket socket) {
			this.serverSocket = serverSocket;
	}
	
	
	public void listen() {
		
		try {
			content = buffr.readLine();
			dbWrite();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public void dbWrite() {
		String sql = "insert into message(chat_id, message_id, content, chat_time, member_no";
		sql += " values(?, seq_message.nextval, ?, ?, ?)";
		
		
	}
	

	public static void main(String[] args) {
		
	}
}
