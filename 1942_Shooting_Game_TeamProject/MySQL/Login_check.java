import java.sql.*;

public class Login_check {
	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private int result;
	sqlConnect sql = new sqlConnect();
	
	public int logincheck(String ids, String passwds) {
		Connection conn;
		PreparedStatement stmt;
		
		try {
			conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
			String sql = "select password from member where id = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ids);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).contentEquals(passwds))
					return result = 1;
				else
					return result = 0;
			}
			rs.close();
			stmt.close();
			conn.close();
			
			return result = -1;
		} catch (SQLException e) { e.printStackTrace(); }
		return result = -2;
	}
}