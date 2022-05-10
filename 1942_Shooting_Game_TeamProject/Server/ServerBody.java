import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class ServerBody extends Thread {
	static User user;
	static DatagramSocket ds;
	static int count = 0;
	static {
		user = new User();
		try {
			ds = new DatagramSocket(9000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void sendAlramToAll(String alram) {
		DatagramPacket dp = new DatagramPacket(alram.getBytes(), alram.getBytes().length);
		for(Account a : user.getCurrentUser()) {
			dp.setSocketAddress(a.getSocketAddress());
			try {
				System.out.println("server UDP send");
				ds.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Account account;

	public ServerBody(Socket socket) {
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		String[] command = null;
		while(socket.isConnected()) {
			String received = null;
			try {
				received = (String)ois.readObject();
			} catch(IOException | ClassNotFoundException e) {
				account.setSocketAddress(null);
				user.getCurrentUser().remove(user);
			}
			
			System.out.println("[server] received : " + received);
			command = received.split("#");
			Object resp = null;
			
			switch(command[0]) {
			case "create" :	
				resp = user.create(command[1], command[2], command[3]);
				sendToClient(resp);
				break;
				
			case "join" : 
				String result = user.login(command[1], command[2], socket.getRemoteSocketAddress());
				account = user.getAccountMap().get(command[1]);
				sendToClient(result);
				if(result.equals("true"))
					sendAlramToAll("userListChange");
				break;
				
			case "ready" : 
				sendAlramToAll("ready");
				count++;
				if (count == 2) {
					sendAlramToAll("GameStart");
				}
				break;
				
			case "x" : 
				sendAlramToAll("x#" + command[1]);
				break;
				
			case "y" : 
				sendAlramToAll("y#" + command[1]);
				break;
				
			case "space" :
				sendAlramToAll("space#" + command[1]);
				break;
				
			case "spacenot" :
				sendAlramToAll("spacenot#" + command[1]);
				break;
				
			case "score" :
				
				break;
			}
		}
	}

	private void sendToClient(Object resp) {
		try {
			oos.reset();	
			oos.writeObject(resp);
			System.out.println(resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}