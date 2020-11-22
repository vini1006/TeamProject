package models;

public class RegistMemberVO {

	private int member_no;
	private String member_id;
	private String member_password;
	private String member_name; 
	private String member_email; 
	private String member_rank;
	
	
	
	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_password() {
		return member_password;
	}

	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}

	public String getMember_rank() {
		return member_rank;
	}

	public void setMember_rank(String member_rank) {
		this.member_rank = member_rank;
	}
}
