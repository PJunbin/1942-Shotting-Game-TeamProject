import java.sql.*;

public class High_Score {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private int gread = 0;
	sqlConnect sql = new sqlConnect();
	
	public int Score(String ids) {
		Connection conn;
		PreparedStatement stmt;
		
		try {
			conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
			String sql = "select score from game where id = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ids);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next())
				gread = rs.getInt("score");
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) { e.printStackTrace(); }
		return gread;
	}
}