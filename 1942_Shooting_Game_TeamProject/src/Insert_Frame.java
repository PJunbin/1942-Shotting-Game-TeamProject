import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class Insert_Frame extends JFrame implements KeyListener {
	JFrame frame = new JFrame();
	image s = new image();
	choiceMemu c = new choiceMemu();
	hIGHSCORE h = new hIGHSCORE();
	high h1 = new high();
	player1 p1 = new player1();
	player2 p2 = new player2();
	ranking rankmemu = new ranking();
	
	class image extends JPanel {
		Image img;
		BufferedImage image = null;
		public image() {
			img = Toolkit.getDefaultToolkit().createImage("image\\keypress.gif");
			try {
				image = ImageIO.read(new File("image\\insert_image.png"));
			} catch (IOException e) { }
		}
		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
			g.drawImage(img, 70, 500, this);
		}
	}
	
	class choiceMemu extends JPanel implements KeyListener {
		BufferedImage image = null;
		public choiceMemu() {
			try {
				image = ImageIO.read(new File("image\\play_game.png"));
			} catch (IOException e) { }
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
		}

		public void paintComponent(Graphics g) {
			g.drawImage(image,0,0, null);
		}
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				c.setVisible(false);
				frame.add(h);
				h.setVisible(true);
				h.requestFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				c.setVisible(false);
				frame.add(p1);
				p1.setVisible(true);
				p1.requestFocus();
			}
		}
		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	class hIGHSCORE extends JPanel implements KeyListener {		
		BufferedImage image = null;
		public hIGHSCORE() {
			try {
				image = ImageIO.read(new File("image\\highscore.png"));
			} catch (IOException e) { }
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
		}
			
		public void paintComponent(Graphics g) {
			g.drawImage(image,0,0, null);
		}
		
		public void keyPressed(KeyEvent e) { 
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				h.setVisible(false);
				frame.add(c);
				c.setVisible(true);
				c.requestFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {	
				h.setVisible(false);
				frame.add(rankmemu);
				rankmemu.setVisible(true);
				rankmemu.requestFocus();
			}
			
		}
		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	class player2 extends JPanel implements KeyListener {	
		BufferedImage image = null;
		boolean click = false;
		public player2() {
			try {
				image = ImageIO.read(new File("image\\2player.png"));
			} catch (IOException e) { e.printStackTrace(); }
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(image,0,0, null);
		}
		
		public void keyPressed(KeyEvent e) { 
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				frame.dispose();
				Login.body.sendReadyRequest();
				Login.body.ready = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				p2.setVisible(false);
				frame.add(p1);
				p1.setVisible(true);
				p1.requestFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				p2.setVisible(false);
				frame.add(h1);
				h1.setVisible(true);
				h1.requestFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				p2.setVisible(false);
				frame.add(c);
				c.setVisible(true);
				c.requestFocus();
			}
		}
		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	class player1 extends JPanel implements KeyListener {	
		BufferedImage image = null, image2 = null;
		public player1() {
			try {
				image = ImageIO.read(new File("image\\1player.png"));
			} catch (IOException e) { }
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(image,0,0, null);
		}
		
		public void keyPressed(KeyEvent e) { 
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				frame.dispose();
				Thread t = new Thread(new Shoot());
				t.start();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				p1.setVisible(false);
				frame.add(p2);
				p2.setVisible(true);
				p2.requestFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				p1.setVisible(false);
				frame.add(c);
				c.setVisible(true);
				c.requestFocus();
			}
		}
		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	class high extends JPanel implements KeyListener {	
		BufferedImage image = null, image2 = null;
		public high() {
			try {
				image = ImageIO.read(new File("image\\highscore2.png"));
			} catch (IOException e) { }
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
		}
		
		public void paintComponent(Graphics g) {
			g.drawImage(image,0,0, null);
		}
		
		public void keyPressed(KeyEvent e) { 
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				h1.setVisible(false);
				frame.add(rankmemu);
				rankmemu.setVisible(true);
				rankmemu.requestFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				h1.setVisible(false);
				frame.add(p2);
				p2.setVisible(true);
				p2.requestFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				h1.setVisible(false);
				frame.add(c);
				c.setVisible(true);
				c.requestFocus();
			}
		}
		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	class ranking extends JPanel implements KeyListener {
		private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		private int gread = 0;
		private String id = null;
		JLabel rank[] = new JLabel[5];
		ArrayList <String> list = new ArrayList<String>();
//		sqlConnect sql = new sqlConnect();
		BufferedImage image = null;
		
		public ranking() {
			setLayout(null);
			String ranker = null;
			Connection conn;
			PreparedStatement stmt;
			
			try {
				image = ImageIO.read(new File("image\\ranking.png"));
			} catch (IOException e1) { e1.printStackTrace(); }
			
			
//			try {
//				conn = DriverManager.getConnection(sql.getUrl(), sql.getName(), sql.getPwd());
//				String sql = "select id, score from game order by score desc";
//				stmt = conn.prepareStatement(sql);
//				ResultSet rs = stmt.executeQuery();
//					
//				while(rs.next()) {
//					id = rs.getString("id");
//					gread = rs.getInt("score");
//					ranker = id + " : " + gread + "Á¡";
//					list.add(ranker);
//				}
//				rs.close();
//				stmt.close();
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			Iterator e = list.iterator();
			String a;
			int i= 0;
			
			while(i < rank.length) {
				if(e.hasNext()) {
					a = (String) e.next();
					rank[i] = new JLabel(a);
				}
				else
					rank[i] = new JLabel("");
				i++;
			}
			
			add(rank[0]);
			add(rank[1]);
			add(rank[2]);
			add(rank[3]);
			add(rank[4]);
			
			rank[0].setBounds(200, 100, 500, 200);
			rank[1].setBounds(200, 200, 500, 200);
			rank[2].setBounds(200, 300, 500, 200);
			rank[3].setBounds(200, 400, 500, 200);
			rank[4].setBounds(200, 500, 500, 200);
			
			for(int j=0; j < rank.length; j++) {
				rank[j].setForeground(Color.white);
				rank[j].setFont(new Font("¹ÙÅÁÃ¼", Font.PLAIN, 30));
			}
			
			addKeyListener(this);
			setFocusable(true);
			requestFocus();
		}
		public void paintComponent(Graphics g) {
			g.drawImage(image,0,0, null);
		}
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				rankmemu.setVisible(false);
				frame.add(h);
				h.setVisible(true);
				h.requestFocus();
			}
		}
		public void keyReleased(KeyEvent arg0) { }
		public void keyTyped(KeyEvent arg0) { }
	}
	
	public Insert_Frame() {
		
		frame.setTitle("1942");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,900);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(s);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			default : 
				s.setVisible(false);
				frame.add(c);
				c.setVisible(true);
				c.requestFocus();
				break;
		}
	}
	public void keyReleased(KeyEvent arg0) { }
	public void keyTyped(KeyEvent arg0) { }
}