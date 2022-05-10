import java.sql.*;

public class Duplicate_check {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	sqlConnect sql = new sqlConnect();
	
	public boolean idscheck(String ids) {
		Connection conn;
		PreparedStatement stmt;
		
		try {
			conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
			String sql = "select count(*) id from member where id = ?;";
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
		} catch (SQLException e) { e.printStackTrace(); }
	return false;
	}
}