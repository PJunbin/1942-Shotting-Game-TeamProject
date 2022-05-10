public class sqlConnect {
	private final String DB_URL = "jdbc:mysql://127.0.0.1:3306/game";
	private final String NAME = "user";
	private final String PWD = "user";
	public static String id;

	public String getName() { return NAME; }
	public String getPwd() { return PWD; }
	public String getUrl() { return DB_URL; }
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
}
/*
이 프로젝트를 이용하기 위해서는 윈도우 또는 리눅스에 MySQL이 설치되어 있어야 하며, MySQL의 접근하는 사용자 'user' 패스워드는 'user'인 사용자가 생성되어 있어야 한다.
만약 다른 사용자를 사용할려면 3, 4번줄의 Name과 PWD를 자신에 맞게 변경하면 된다.
*/