import java.io.*;
import java.net.*;
import java.util.*;

public class User {
	private Map<String,Account> accountMap;		// ������ ���� ����
	private Set<Account> currentUser;			// ���� �������� ����
	private File address;
	public User() {
		File file = new File(System.getProperty("user.home") , "dropthebeat_Account");
		if(!file.exists()) {
			file.mkdirs();
		}
		address = new File(file.toString() + "\\Account.txt");
		if(!address.exists()) {
			accountMap = new HashMap<>();
			accountMap.clear();
		} else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(address));
				accountMap = (Map<String, Account>)ois.readObject();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		currentUser = new HashSet<>();
		currentUser.clear();
	}
	
	public String create(String id, String pass, String name) {			// ���̵� ���� 
		if(accountMap.containsKey(id)) {
			return "false#�̹� ���̵� �����մϴ�.";
		} else {
			accountMap.put(id, new Account(id, pass , name));
			System.out.println(accountMap);
			fileOutput();
			return "true";
		}
	}
	
	public String login(String id, String pass, SocketAddress sa) {		// �α��� 
		if(accountMap.containsKey(id)) {
			System.out.println("getcontains");
			if(!currentUser.contains(new Account(id, "", ""))) {
				if(accountMap.get(id).getPass().equals(pass)) {
					currentUser.add(accountMap.get(id));
					accountMap.get(id).setSocketAddress(sa);
					fileOutput();
					return "true";
				}else {
					return "false#��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
				}
			} else {
				return "false#�̹� �������� ���̵��Դϴ�.";
			}
		} else {
			return "false#�������� �ʴ� ���̵��Դϴ�.";
		}
	}
	
	public void fileOutput() {				// ���� ��� �޼ҵ�
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(address));
			oos.writeObject(accountMap);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Account> getAccountMap() {
		return accountMap;
	}

	public void setAccountMap(Map<String, Account> accountMap) {
		this.accountMap = accountMap;
	}

	public Set<Account> getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Set<Account> currentUser) {
		this.currentUser = currentUser;
	}

	public File getAddress() {
		return address;
	}

	public void setAddress(File address) {
		this.address = address;
	}
}