package view.mainView;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import view.subView.ChatView;
import view.subView.LoginView;

import java.awt.*;

public class MainWindow extends JFrame{
	private static final long serialVersionUID=1L;
	private static final int WIDTH=800;
	private static final int HEIGHT=600;
	private LoginView loginPane=null;
	private ChatView chatPane=null;
	
	private void init() {
		loginPane=new LoginView();
		chatPane=new ChatView();
		
	}
	public LoginView getLoginPane() {
		return loginPane;
	}
	public ChatView getChatPane() {
		return chatPane;
	}
	private void setComponent() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(loginPane);
		this.setResizable(false);
		this.setSize(WIDTH,HEIGHT);
		this.setLocation(400, 100);
		this.setIconImage(new ImageIcon(".\\material\\128x128.png").getImage());
		this.setVisible(false);
	}
	public MainWindow() {
		init();
		setComponent();
	}
	public static void main(String[] args) {
		new MainWindow();
	}
}
