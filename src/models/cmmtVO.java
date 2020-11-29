package models;

public class cmmtVO {
	 private int cmmt_id;
	 private int board_id;
	 private String cmmt_username;
	 private String cmmt_content;
	 private String cmmt_wtime;
	 private String cmmt_ip;
	 
	public int getCmmt_id() {
		return cmmt_id;
	}
	public void setCmmt_id(int cmmt_id) {
		this.cmmt_id = cmmt_id;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getCmmt_username() {
		return cmmt_username;
	}
	public void setCmmt_username(String cmmt_username) {
		this.cmmt_username = cmmt_username;
	}
	public String getCmmt_content() {
		return cmmt_content;
	}
	public void setCmmt_content(String cmmt_content) {
		this.cmmt_content = cmmt_content;
	}
	public String getCmmt_wtime() {
		return cmmt_wtime;
	}
	public void setCmmt_wtime(String cmmt_wtime) {
		this.cmmt_wtime = cmmt_wtime;
	}
	public String getCmmt_ip() {
		return cmmt_ip;
	}
	public void setCmmt_ip(String cmmt_ip) {
		this.cmmt_ip = cmmt_ip;
	}
	 
}
