import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javazoom.jl.player.MP3Player;
import javazoom.jl.player.Player;

class MusicVO {
    private String path;
    private boolean loop;
    public MusicVO(String path, boolean loop) {
        this.path = path;
        this.loop = loop;
    }
    
    public String getPath() {
        return path;
    }
    
    public boolean getLoop() {
    	return loop;
    }
}

class Musicplay {
	private ArrayList<MusicVO> playlist = new ArrayList<MusicVO>();
	private MP3Player mp3Player;
	private int index;
	int count = 0;
	boolean loop;
	
	public Musicplay(ArrayList<MusicVO> playlist) {
		this.playlist = playlist;
		this.mp3Player = new MP3Player();
	}
	
	public ArrayList<MusicVO> getPlaylist() {
		return playlist;
	}
	
	public void play() {
		MusicVO m = playlist.get(index);
		mp3Player.play(m.getPath());
	}
	
	public void stop() {
		mp3Player.stop();
	}
}

public class Soundsys {
	ArrayList<MusicVO> playlist = new ArrayList<MusicVO>();
	Musicplay mp = new Musicplay(playlist);
	MP3Player mp3Player;
	
}

class shootingsound extends Soundsys {
	public shootingsound() {
		playlist.add(new MusicVO("SoundSystem\\Shooting.mp3", false));
		mp.play();
	}
}

class enremove extends Soundsys {
	public enremove() {
		playlist.add(new MusicVO("SoundSystem\\enremove.mp3", false));
		mp.play();
	}
}

class music extends Thread {
    boolean isloop;
    public String name;
    Player player;
    FileInputStream fis;
    BufferedInputStream bis;

    public music(String filename, boolean isloop) {
        try {
            this.isloop = isloop;
            name = filename;
            fis = new FileInputStream("SoundSystem\\" + name);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void close() {
        isloop = false;
        player.close();
        this.interrupt();
    }

    public void run() {
        try {
            do {
                fis = new FileInputStream("SoundSystem\\" + name);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
                player.play();
            } while (isloop);
        } catch (Exception e) {

        }
    }
}