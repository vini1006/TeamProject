package models;

public class ChatMemberVO {
	private int chat_id;
	private int chatMember_id;
	private String member;
	
	public int getChat_id() {
		return chat_id;
	}
	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}
	public int getChatMember_id() {
		return chatMember_id;
	}
	public void setChatMember_id(int chatMember_id) {
		this.chatMember_id = chatMember_id;
	}
	public String getMember() {
		return member;
	}
	public void setMember(String member) {
		this.member = member;
	}
	
	
}
