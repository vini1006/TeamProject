package Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import utill.DBManager;

public class ServerSocket {
	DBManager dbManager;
	ServerSocket server;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	String content;
	ServerSocket serverSocket;
	private Connection con;
	
	public ServerSocket() {
		con = dbManager.connect();
	}
	
	
	//소켓연결해 놓기
	public void startServer() {
		try {
			server = new ServerSocket(7777);
			socket = server.accept(); //어셉트 이후에 실행부가 움직임 thread사용
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	public Connection getCon() {
		return con;
	}
}
