import java.awt.*;
import javax.swing.*;

public class Membership extends JFrame {
	private JLabel id, passwd, repasswd, name;
	private JButton renew, cancel, check;
	public JTextField ids, names;
	public JPasswordField passwds, repasswds;
	public User user = new User();
	
	public Membership() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		setTitle("회원 가입");
		setSize(440,300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		id = new JLabel("사용할 아이디");
		ids = new JTextField(15);
		passwd = new JLabel("비 밀 번 호");
		passwds = new JPasswordField(15);
		repasswd = new JLabel("비 밀 번 호 확 인");
		repasswds = new JPasswordField(15);
		name = new JLabel("사용자  이름");
		names = new JTextField(15);
		renew = new JButton("회원 가입");
		cancel = new JButton("취 소");
		check = new JButton("중복 검사");
		
		panel.add(id); panel.add(ids); panel.add(passwd); panel.add(passwds);
		panel.add(repasswd); panel.add(repasswds); panel.add(name); panel.add(names);
		panel.add(renew); panel.add(cancel); panel.add(check);
		
		name.setBounds(50, 0, 100, 50);
		names.setBounds(150, 10, 150, 30);
		id.setBounds(50, 50, 100, 50);
		ids.setBounds(150, 60, 150, 30);
		passwd.setBounds(50, 100, 100, 50);
		passwds.setBounds(150, 110, 150, 30);
		repasswd.setBounds(50, 150, 100, 50);
		repasswds.setBounds(150, 160, 150, 30);
		renew.setBounds(50, 220, 100, 30);
		cancel.setBounds(200, 220, 100, 30);
		check.setBounds(310, 60, 100, 30);
			
		renew.addActionListener(e -> {
			if(passwds.getText().equals(repasswds.getText())) {
				Insert_Membership im = new Insert_Membership(names.getText(), ids.getText(), passwds.getText());
				Login.body.sendCreateRequest(ids.getText(), passwds.getText(), names.getText(), repasswds.getText());
				JOptionPane.showMessageDialog(null, "회원가입이 완료 되었습니다.");
				dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다. 확인해주세요");
		});
	
		cancel.addActionListener(e -> {
			dispose();
		});
		
		check.addActionListener(e -> {
			Duplicate_check ch = new Duplicate_check();
			if(ch.idscheck(ids.getText()) == true) {
				JOptionPane.showMessageDialog(null, "이미 사용중인 아이디 입니다.");
				renew.setEnabled(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
				renew.setEnabled(true);
			}
		});
		add(panel);
		setVisible(true);
	}
}