package main;

import java.awt.*;

import view.mainView.MainWindow;

public class StartClient {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame=new MainWindow();
					ChatManager.getChatManager().setWindow(frame);
					ChatManager.getChatManager().connect("127.0.0.1");
				}catch(Exception e) {
					e.printStackTrace();        
				}
			}
		});
	}
}
