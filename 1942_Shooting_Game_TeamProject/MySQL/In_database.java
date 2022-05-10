import java.sql.*;
import javax.swing.*;

public class In_database {
	
}

class Insert_Membership {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	sqlConnect sql = new sqlConnect();
	MD5 md = new MD5();
	
	public Insert_Membership(String names, String ids, String passwds) {
		try {
			Connection conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
			PreparedStatement stmt;
			
			String sql = "insert into member (name, id, password) values (?, ?, ?);";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, names);
			stmt.setString(2, ids);
			stmt.setString(3, md.MD5(passwds));
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Insert_score {
	sqlConnect sql = new sqlConnect();
	Insert_score2 ch = new Insert_score2();
	
	public Insert_score(String id, int sum) {
		try {
			Connection conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
			PreparedStatement stmt;
			
			if(ch.idscheck(id) == true) {
				String sql1 = "update game set score=(?) where id=(?);";
				stmt = conn.prepareStatement(sql1);
				stmt.setInt(1, sum);
				stmt.setString(2, id);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
			else {
				String sql = "insert into game (id, score) values (?, ?);";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, id);
				stmt.setInt(2, sum);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) { e.printStackTrace(); }
		catch (Exception e) { e.printStackTrace(); }
	}
}

class Insert_score2 {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	sqlConnect sql = new sqlConnect();
	
	public boolean idscheck(String ids) {
		Connection conn;
		PreparedStatement stmt;
		
		try {
			conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
			String sql = "select count(*) id from game where id = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ids);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				int count = rs.getInt("id");
				if(count > 0)
					return true;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return false;
	}
}