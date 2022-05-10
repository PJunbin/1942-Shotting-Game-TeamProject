import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class Shoot extends JFrame implements Runnable, KeyListener {
	private static Shoot one;
	public static boolean space; 
	private static BufferedImage bi = null;
	private static ArrayList msList = null;
	private static ArrayList enList = null;
	private static BufferedImage plane = null, bullet = null, png = null, explode = null, top = null;
	private static boolean left = false, right = false, up = false, down = false, fire = false, shift = false, start = true, end = false;
	private static int w = 600, h = 900, x = 250, y = 800, xw = 20, xh = 20, life = 3;
	static sqlConnect sql = new sqlConnect();
	static String s = sql.getId();
	static High_Score hs = new High_Score();
	music bgm = new music("bgm.mp3", true);
	static int max = hs.Score(s);
	static int sum = 0;
	
	public Shoot() {
		bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		msList = new ArrayList();
		enList = new ArrayList();
		this.addKeyListener(this);
		this.setSize(600, 900);
		this.setTitle("Shooting Game");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		try {
			top = ImageIO.read(new File("image\\top.png"));
			plane = ImageIO.read(new File("image\\my_plane.png"));
			bullet = ImageIO.read(new File("image\\bullet.png"));
			png = ImageIO.read(new File("image\\en_plane.png"));
			explode = ImageIO.read(new File("image\\explode.png"));
		} catch (IOException e) { }
	}
	
	public void run() {
		try {
			bgm.start();
			int msCnt = 0;
			int enCnt = 0;
			while(start) {
				Thread.sleep(10);
				if(true) {
					if(enCnt > 2000) {
						enCreate();
						enCnt = 0;
					}
					if(msCnt >= 100) {
						fireMs();
						fireMs2();
						msCnt = 0;
					}
					msCnt += 10;
					enCnt += 10;
					keyControl();
					crashChk();
				}
				draw();
				
				if(sum >= max)	
					max = sum;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void fireMs() {
		if(fire) {
			if(msList.size() < 100) {
				Ms m = new Ms(this.x, this.y);
				msList.add(m);
				new shootingsound();
			}
		}
	}
	
	public void fireMs2() {
		if(space) {
			if(msList.size() < 100) {
				Ms m = new Ms(this.x, this.y);
				msList.add(m);
				new shootingsound();
			}
		}
	}
	
	public static void enCreate() {
			for(int i = 0; i < 9; i++) {
			double rx = Math.random() * (w - xw) - 20;
			double ry = Math.random() * 50;
			Enemy en = new Enemy((int)rx, (int)ry);
			enList.add(en);
		}
	}
	
	public void crashChk() {
		Graphics g = this.getGraphics();
		Polygon p = null;
		for(int i = 0; i < msList.size(); i++) {
			Ms m = (Ms)msList.get(i);
			for(int j = 0; j < enList.size(); j++) {
				Enemy e = (Enemy)enList.get(j);
				int[] xpoints = {m.x, (m.x + m.w), (m.x + m.w), m.x};
				int[] ypoints = {m.y, m.y, (m.y + m.h), (m.y + m.h)};
				p = new Polygon(xpoints, ypoints, 4);
				try { 
					if(p.intersects((double)e.x-5, (double)e.y, (double)e.w, (double)e.h+30)) {
						msList.remove(i);
						enList.remove(j);
						new enremove();
						sum += 50; 
					}
				}catch (IndexOutOfBoundsException e1) { e1.printStackTrace(); }
			}
		}
		
		for(int i = 0; i < enList.size(); i++) {
			Enemy e = (Enemy)enList.get(i);
			int[] xpoints = {x, (x + xw), (x + xw), x};
			int[] ypoints = {y, y, (y + xh), (y + xh)};
			p = new Polygon(xpoints, ypoints, 4);
			if(p.intersects((double)e.x-10, (double)e.y, (double)e.w, (double)e.h)) {
				enList.remove(i);
				if(life != 0) {
					--life;
				}
				if(life == 0) {
					start = false;
					end = true;
					bgm.close();
					if(sum >= max) {
						String id = sql.getId();
						Insert_score sc = new Insert_score(id, sum);
					}
					sum = 0;
				}
			}
		}
	}
	
	public void draw() {
		Polygon pa = null;
		Graphics gs = bi.getGraphics();
		gs.setColor(new Color(6,88,144));
		gs.fillRect(0, 0, w, h);
		gs.setColor(Color.white);
		gs.setFont(new Font("Pixeboy", Font.PLAIN, 40));
		gs.drawString(sum+"", 40, 100);
		gs.drawString("LIFE : " + life, 15, 880);
		gs.drawString(""+max, 250, 100);
		
		if(end)
			gs.drawString("G A M E     O V E R", 180, 450);
		
		gs.drawImage(top, 0, 20, this);
		gs.drawImage(plane, x, y, null);
		
		for(int i = 0; i < msList.size(); i++) {
			Ms m = (Ms)msList.get(i);
			gs.setColor(Color.blue);
			gs.drawImage(bullet, m.x+33, m.y-15, null);
			if(m.y < 0) msList.remove(i);
			m.moveMs();
		}
		
		gs.setColor(Color.black);
		for(int i = 0; i < enList.size(); i++) {
			Enemy e = (Enemy)enList.get(i);
			gs.drawImage(png, e.x, e.y, null);
			if(e.y > h) enList.remove(i);
			e.moveEn();
		}
		
		Graphics ge = this.getGraphics();
		ge.drawImage(bi, 0, 0, w, h, this);
	}
	
	public void keyControl() {
		if(0 < x) {
			if(shift&&left) {
				x -= 2;
				Login.body.sendX();
			}
			else if(left) {
				x -= 6;
				Login.body.sendX();
			}
		}
		if(w > x + 100) {
			if(right&&shift) {
				x += 2;
				Login.body.sendX();
			}
			else if(right) {
				x += 6;
				Login.body.sendX();
			}
		}
		if(25 < y) {
			if(up&&shift) { 
				y -= 2;
				Login.body.sendY();
			}
			else if(up) { 
				y -= 6;
				Login.body.sendY();
			}
		}
		if(h > y + 100) {
			if(down&&shift) {
				y += 2;
				Login.body.sendY();
			}
			else if(down) { 
				y += 6;
				Login.body.sendY();
			}
		}
	}
	
	public void keyPressed(KeyEvent ke) {
		switch(ke.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = true;
			break;		
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_SPACE:
			fire = true;
			Login.body.sendSpace();
			break;
		case KeyEvent.VK_ENTER:
			if(end) {
				System.exit(0);
				break;
			}
			break;
		case KeyEvent.VK_SHIFT :
			shift = true;
			break;
		}
	}
	
	public void keyReleased(KeyEvent ke) {
		switch(ke.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_SPACE:
			fire = false;
			Login.body.sendNotSpace();
			break;
		case KeyEvent.VK_SHIFT :
			shift = false;
			break;
		}
	}
	
	public void keyTyped(KeyEvent ke) {}
	
	public static int getCondX() {
		return x;
	}
	public static int getCondY() {
		return y;
	}
	
	public static void setX(int x) {
		Shoot.x = x;
	}
	public static void setY(int y) {
		Shoot.y = y;
	}
}

class Ms {
	int x, y, w =20, h=20, speed = 5;
	
	public Ms(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void moveMs() { y-=5; }
}
	
class Enemy {
	int x, y, w = 30, h = 30;
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void moveEn() { y+=2; }
}