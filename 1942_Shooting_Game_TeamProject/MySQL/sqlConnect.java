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
�� ������Ʈ�� �̿��ϱ� ���ؼ��� ������ �Ǵ� �������� MySQL�� ��ġ�Ǿ� �־�� �ϸ�, MySQL�� �����ϴ� ����� 'user' �н������ 'user'�� ����ڰ� �����Ǿ� �־�� �Ѵ�.
���� �ٸ� ����ڸ� ����ҷ��� 3, 4������ Name�� PWD�� �ڽſ� �°� �����ϸ� �ȴ�.
*/