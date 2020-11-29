package models;

public class BoardVO {
	 private int board_id;
	   private String board_title;
	   private String board_content;
	   private String board_username;
	   private String board_ip;
	   private String board_wtime;
	   private String board_status;
	   private int board_comment_count;
	   private int board_hit;
	   
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public String getBoard_username() {
		return board_username;
	}
	public void setBoard_username(String board_username) {
		this.board_username = board_username;
	}
	public String getBoard_ip() {
		return board_ip;
	}
	public void setBoard_ip(String board_ip) {
		this.board_ip = board_ip;
	}
	public String getBoard_wtime() {
		return board_wtime;
	}
	public void setBoard_wtime(String board_wtime) {
		this.board_wtime = board_wtime;
	}
	public String getBoard_status() {
		return board_status;
	}
	public void setBoard_status(String board_status) {
		this.board_status = board_status;
	}
	public int getBoard_comment_count() {
		return board_comment_count;
	}
	public void setBoard_comment_count(int board_comment_count) {
		this.board_comment_count = board_comment_count;
	}
	public int getBoard_hit() {
		return board_hit;
	}
	public void setBoard_hit(int board_hit) {
		this.board_hit = board_hit;
	}
	   
}
