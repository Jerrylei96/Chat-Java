package view.subView;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import main.ChatManager;
import view.mainView.MainWindow;

public class ChatView extends JDesktopPane{
	 private JLabel chatLabel;
	 private JLabel roleLabel;
	 private JLabel logOutLabel;
	 private ChatFrame chatFrame;
	 private RoleFrame roleFrame;
	 private LogoutConfirm logoutConfirm;
	 private void init() {
		 chatLabel=new JLabel();
		 chatFrame=new ChatFrame(this);
		 roleFrame=new RoleFrame();
		 logoutConfirm=new LogoutConfirm();
	 }
	 public RoleFrame getRoleFrame() {
		 return roleFrame;
	 }
	 public ChatFrame getChatFrame() {
		 return chatFrame;
	 }
	 public JLabel getChatLabel() {
		 return chatLabel;
	 }
	 private void setComponent() {
		 ImageIcon image=new ImageIcon(".\\material\\chat.png");
		 chatLabel=new JLabel(image);
		 chatLabel.setBounds(10,10,20,15);
		 chatLabel.setToolTipText("ÁÄÌì");
		 image=new ImageIcon(".\\material\\role.png");
		 roleLabel=new JLabel(image);
		 roleLabel.setBounds(720,12,20,15);
		 roleLabel.setToolTipText("Íæ¼Ò");
		 image=new ImageIcon(".\\material\\logout.png");
		 logOutLabel=new JLabel(image);
		 logOutLabel.setBounds(760,10,20,15);
		 logOutLabel.setToolTipText("×¢Ïú");
	 }
	 private void addComponent() {
		 this.add(chatLabel);
		 this.add(chatFrame);
		 this.add(roleFrame);
		 this.add(roleLabel);
		 this.add(logOutLabel);
		 this.add(logoutConfirm);
	 }
	 private void addEvent() {
			chatLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					chatFrame.setVisible(true);
					chatLabel.setVisible(false);
				}
			});
			roleLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(roleFrame.isVisible()) {
						roleFrame.setVisible(false);
					}else {
						roleFrame.setVisible(true);
					}
				}
			});
			logOutLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					logoutConfirm.setVisible(true);
				}
			});
	 }
	 public ChatView() {
		 init();
		 setComponent();
		 addComponent();
		 addEvent();
	 }
}
