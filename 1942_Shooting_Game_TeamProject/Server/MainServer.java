import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {	
		try {
			ServerSocket server = new ServerSocket(9000);
			System.out.println("���� ���� ���� ��");
			while(!server.isClosed()) {
				Socket socket = server.accept();
				Thread p = new ServerBody(socket);
				p.start();
				System.out.println("User" + socket.getInetAddress() + " IP�� ���� �Ͽ����ϴ�.");
			}
			server.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}