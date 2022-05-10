import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {	
		try {
			ServerSocket server = new ServerSocket(9000);
			System.out.println("게임 서버 구동 중");
			while(!server.isClosed()) {
				Socket socket = server.accept();
				Thread p = new ServerBody(socket);
				p.start();
				System.out.println("User" + socket.getInetAddress() + " IP로 접속 하였습니다.");
			}
			server.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}