package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

import layout.MainApp;
import utill.DBManager;

public class MyServerSocket {
	MainApp mainApp;
	DBManager dbManager;
	ServerSocket server;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	String content;
	ServerSocket serverSocket;
	private Connection con;
	
	Thread thread; //접속자 감지용 쓰레드(server.accept())
	ArrayList<MyServerThread> threadList = new ArrayList<MyServerThread>();
	
	
	public MyServerSocket(MainApp mainApp) {
		this.mainApp = mainApp;
		thread = new Thread() {
			@Override
			public void run() {
				startServer();
			}
		};
		thread.start();
	}
	
	
	//소켓가동, MainApp에서 실행
	public void startServer() {
		try {
			server = new ServerSocket(8888);
			//서버는 여러명의 접속자를 감지해야 한다면, 각 접속자마다 비동기적으로 즉 독립적으로서
			//상관없이 대화를 주고받는 주체는 쓰레드의 인스턴스로 처리하자
			while(true) {
				Socket socket = server.accept(); //접속자 감지와 동시에 대화용 소켓반환
				//다수의 접속자 수 정보를 어딘가에 저장해놓자.
				System.out.println("손님받는중");
				
				//대화용 쓰레드 생성, 소켓넘기기
				MyServerThread myServerThread = new MyServerThread(this, socket,mainApp);
				myServerThread.start();
				threadList.add(myServerThread); //지금 접속한 클라이언트와 쌍을 이루는 서버측대화쓰레드를
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Connection getCon() {
		return con;
	}
	
}
