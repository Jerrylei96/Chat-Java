
import java.io.IOException;
import java.net.*;

import javax.swing.JOptionPane;

public class ServerListener extends Thread{
	public void run() {
		try {
			ServerSocket serverSocket=new ServerSocket(80);
			while(true) {
				Socket socket=serverSocket.accept();
				ChatSocket cs=new ChatSocket(socket);
				cs.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
