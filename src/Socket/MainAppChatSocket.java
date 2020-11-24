package socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import layout.MainApp;
import utill.Chat_lib;

public class MainAppChatSocket {
	public Socket socket;
	public int port = 8888;
	public String ip = "localhost";
	public MainAppChatThread mainAppchatThread;
	public MainApp mainApp;
	public Chat_lib chat_lib;
	
	//mainApp실행시 우선 접속
	//후에 mainApp에서 채팅창 만들고. 채팅칠시 MainAppchatThread의 send 호출
	
	public MainAppChatSocket(MainApp mainApp, Chat_lib chat_lib) {
		this.chat_lib = chat_lib;
		this.mainApp = mainApp;
		connect();
	}
	
	public void connect() {
		try {
			socket = new Socket(ip, port);
			System.out.println("채팅서버 접속시도");
			
			//무한루프로 주고받을아이들 
			mainAppchatThread = new MainAppChatThread(mainApp, socket, this);
			mainAppchatThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
