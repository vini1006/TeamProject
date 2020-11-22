package models;

public class ChatVO {
	private int chat_id;
	private String chat_title;
	private char chat_status;
	
	
	public int getChat_id() {
		return chat_id;
	}
	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	public String getChat_title() {
		return chat_title;
	}
	public void setChat_title(String chat_title) {
		this.chat_title = chat_title;
	}
	public char getChat_status() {
		return chat_status;
	}
	public void setChat_status(char chat_status) {
		this.chat_status = chat_status;
	}
	
}
