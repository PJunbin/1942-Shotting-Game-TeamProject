import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientBody extends Thread {
	private Socket soc;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private DatagramSocket ds;
	private Login ui;
	private String nick;
	boolean ready, readaction;
	public Shoot game;
	
	public ClientBody(Login c) {
		this.ui = c;
		try {
			soc = new Socket("127.0.0.1", 9000);
			oos = new ObjectOutputStream(soc.getOutputStream());
			ois = new ObjectInputStream(soc.getInputStream());
			System.out.println("[client] connected to server");
			ds = new DatagramSocket(soc.getLocalPort());

		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}
	
	public void run() {
		while (!ds.isClosed()) {
			DatagramPacket dp = new DatagramPacket(new byte[2048], 2048);
			try {
				ds.receive(dp);
				System.out.println("client UDP received");
				String data = new String(dp.getData(), 0, dp.getLength());
				String[] str = data.split("#");
				switch (str[0]) {
				case "x" :
					int x = (Integer.parseInt(str[1]));
					game.setX(x);
					break;
				case "y" : 
					int y = (Integer.parseInt(str[1]));
					game.setY(y);
					break;
				case "space" :
					game.space = true;
					break;
				case "spacenot" :
					game.space = false;
					break;
				case "ready" :
					readaction = true;
					System.out.println(readaction);
					break;
				case "GameStart" :
					if(ui.body.ready && ui.body.readaction) {
						Thread t = new Thread(new Shoot());
						t.start();
					}
					break;
				
				}
			} catch (IOException e) {
				e.printStackTrace();
				ds.close();
				break;
			}
		}
	}
	
	public void sendCreateRequest(String nick, String pass, String name, String repass) {
		String resp = null;
		System.out.println(" [client] request : ");
		if (nick.trim().equals("") || pass.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
			return;
		}
		if (!pass.equals(repass)) {
			JOptionPane.showMessageDialog(null, "비밀번호를 확인하세요");
			return;
		}
		synchronized (oos) {
			try {
				oos.writeObject("create#" + nick + "#" + pass + "#" + name);

				resp = (String) ois.readObject();
				System.out.println("[client] response : " + resp);
				String[] data = resp.split("#");

				// 여기서 ui 제어.
				if (data[0].equals("true")) {
					ui.ids.setText(nick);
					ui.member.passwds.setText("");
					ui.member.names.setText("");
					ui.member.repasswds.setText("");
					ui.member.dispose();

				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendLoginRequest(String nick, String pass) {
		this.nick = nick;
		String resp = null;
		System.out.println("[client] request : ");
		if (nick.trim().equals("") || pass.trim().equals("")) {
			JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
			return;
		}
		synchronized (oos) {
			try {
				oos.writeObject("join#" + nick + "#" + pass);

				resp = (String) ois.readObject();
				System.out.println("[client] response : " + resp);

				String[] data = resp.split("#");
				if (data[0].equals("true")) {
					ui.setVisible(false);
					ui.dispose();
					ui.frame = new Insert_Frame();
					
				} else {
					ui.ids.setText("");
					ui.passwds.setText("");
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendReadyRequest() {
		synchronized (oos) {
			try {
				if (true) {
					oos.writeObject("ready");
				} else {
					oos.writeObject("readycancel");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendX() {
		synchronized (oos) {
			try {
				oos.writeObject("x#" + game.getCondX()); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendY() {
		synchronized (oos) {
			try {
				oos.writeObject("y#" + game.getCondY()); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendSpace() {
		synchronized (oos) {
			try {
				oos.writeObject("space#" + "fire"); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void sendNotSpace() {
		synchronized (oos) {
			try {
				oos.writeObject("spacenot#" + "fire"); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}