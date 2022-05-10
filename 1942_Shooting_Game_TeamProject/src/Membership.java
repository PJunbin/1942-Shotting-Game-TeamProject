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
		setTitle("ȸ�� ����");
		setSize(440,300);
		setResizable(false);
		setLocationRelativeTo(null);
		
		id = new JLabel("����� ���̵�");
		ids = new JTextField(15);
		passwd = new JLabel("�� �� �� ȣ");
		passwds = new JPasswordField(15);
		repasswd = new JLabel("�� �� �� ȣ Ȯ ��");
		repasswds = new JPasswordField(15);
		name = new JLabel("�����  �̸�");
		names = new JTextField(15);
		renew = new JButton("ȸ�� ����");
		cancel = new JButton("�� ��");
		check = new JButton("�ߺ� �˻�");
		
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
				JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ� �Ǿ����ϴ�.");
				dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�. Ȯ�����ּ���");
		});
	
		cancel.addActionListener(e -> {
			dispose();
		});
		
		check.addActionListener(e -> {
			Duplicate_check ch = new Duplicate_check();
			if(ch.idscheck(ids.getText()) == true) {
				JOptionPane.showMessageDialog(null, "�̹� ������� ���̵� �Դϴ�.");
				renew.setEnabled(false);
			}
			else {
				JOptionPane.showMessageDialog(null, "��� ������ ���̵��Դϴ�.");
				renew.setEnabled(true);
			}
		});
		add(panel);
		setVisible(true);
	}
}