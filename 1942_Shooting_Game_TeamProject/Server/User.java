import java.io.*;
import java.net.*;
import java.util.*;

public class User {
	private Map<String,Account> accountMap;		// 유저의 계정 보관
	private Set<Account> currentUser;			// 현재 접속중인 유저
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
	
	public String create(String id, String pass, String name) {			// 아이디 생성 
		if(accountMap.containsKey(id)) {
			return "false#이미 아이디가 존재합니다.";
		} else {
			accountMap.put(id, new Account(id, pass , name));
			System.out.println(accountMap);
			fileOutput();
			return "true";
		}
	}
	
	public String login(String id, String pass, SocketAddress sa) {		// 로그인 
		if(accountMap.containsKey(id)) {
			System.out.println("getcontains");
			if(!currentUser.contains(new Account(id, "", ""))) {
				if(accountMap.get(id).getPass().equals(pass)) {
					currentUser.add(accountMap.get(id));
					accountMap.get(id).setSocketAddress(sa);
					fileOutput();
					return "true";
				}else {
					return "false#비밀번호가 일치하지 않습니다.";
				}
			} else {
				return "false#이미 접속중인 아이디입니다.";
			}
		} else {
			return "false#존재하지 않는 아이디입니다.";
		}
	}
	
	public void fileOutput() {				// 파일 출력 메소드
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