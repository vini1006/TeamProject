/*
 * 데이터베이스와 관련된 업무를 처리하고, 또는 중복되는 로직을 공통화시켜
 * 재사용성을 높이기 위한 클래스..
 * */

/*
 * 
 */
package utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	private String driver="oracle.jdbc.driver.OracleDriver";
//	private String url="jdbc:oracle:thin:@localhost:1521:XE";
//	private String user="koreait";
//	private String password="1234";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String user = "koreait"; // DB ID
	private static final String password = "1234"; // DB 패스워드
	
	//이 메서드를 호출하는 자는 Connection 객체를 반환받을수 있도록..
	public Connection connect() {
		Connection con=null;
		
		try {
			Class.forName(driver);//드라이버 로드
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//쿼리문 수행과 관련된 자원을 닫아주는 메서드 (DML:insert,update,delete)
	public void close(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//select 문 수행과 관련된 자원을 닫을때..
	public void close(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//닫고 싶은 Connection을 받아와서 처리
	public void disConnect(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
















