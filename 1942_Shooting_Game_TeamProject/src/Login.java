import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
	private JLabel id, passwd;
	private JButton logins, renew;
	JTextField ids; 
	public JPasswordField passwds;
	static ClientBody body;
	static Membership member;
	static Insert_Frame frame;
	public User user = new User();
	sqlConnect sql = new sqlConnect();
	
	public Login() {
		body = new ClientBody(this);
		JPanel panel = new JPanel();
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(440,300);
		setResizable(false);
		panel.setLayout(null);
		setLocationRelativeTo(null);
		
		id = new JLabel("아  이  디");
		ids = new JTextField(15);
		passwd = new JLabel("비 밀 번 호");
		passwds = new JPasswordField(15);
		logins = new JButton("로그인");
		renew = new JButton("회원가입");
		
		panel.add(id);
		panel.add(ids);
		panel.add(passwd);
		panel.add(passwds);
		panel.add(logins);
		panel.add(renew);
		
		id.setBounds(60,40,60,20);
		ids.setBounds(150,35,200,30);
		passwd.setBounds(60,105,60,20);
		passwds.setBounds(150,100,200,30);
		logins.setBounds(250,150,100,30);
		renew.setBounds(250,200,100,30);

		logins.addActionListener(e -> {
			Login_check lc = new Login_check();
			MD5 md = new MD5();
			
			try {
				if(lc.logincheck(ids.getText(), md.MD5(passwds.getText())) == 1) {
					JOptionPane.showMessageDialog(null, "로그인 성공.");
					sql.setId(ids.getText());
					body.sendLoginRequest(ids.getText(), passwds.getText());
					dispose();
					new Insert_Frame();
				}
				else if(lc.logincheck(ids.getText(), md.MD5(passwds.getText())) == 0)
					JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
				else if(lc.logincheck(ids.getText(), md.MD5(passwds.getText())) == -1)
					JOptionPane.showMessageDialog(null, "아이디가 없습니다.");
				else
					JOptionPane.showMessageDialog(null, "죄송합니다. 서버 점검 중입니다.");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
				
		renew.addActionListener(e -> {
			Membership re = new Membership();
		});
			
		add(panel);
		setVisible(true);
	}
}