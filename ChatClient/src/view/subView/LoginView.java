package view.subView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JDesktopPane{
	private LoginFrame loginFrame=null;
	private JPanel backPane=null;
	private JPanel componentPane=null;
	private ImageIcon backImage=null;
	private JLabel netError=null;
	private ImageIcon errorImage=null;
	public LoginFrame getLoginFrame() {
		return loginFrame;
	}
	private void init() {
		backImage=new ImageIcon(".\\material\\back.png");
		backPane=new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(backImage.getImage(),0,0,null);
			}
		};
		componentPane=new JPanel(null);
	}
	private void setComponent() {
		backPane.setBounds(0, 0, backImage.getIconWidth(), backImage.getIconHeight());
		componentPane.setBounds(0, 0, backImage.getIconWidth(), backImage.getIconHeight());
	}
	private void addComponent() {
		this.add(backPane, JLayeredPane.DEFAULT_LAYER);
		this.add(componentPane, JLayeredPane.MODAL_LAYER);
		componentPane.setOpaque(false);
	}
	public void addNetError() {
		errorImage=new ImageIcon(".\\material\\error.jpg");
		netError=new JLabel(errorImage);
		netError.setBounds(280,220,250,60);
		componentPane.add(netError);
	}
	public void addLoginFrame() {
		loginFrame=new LoginFrame(this);
		componentPane.add(loginFrame);
	}
	public LoginView() {
		this.setLayout(null);
		init();
		setComponent();
		addComponent();
	}
}
