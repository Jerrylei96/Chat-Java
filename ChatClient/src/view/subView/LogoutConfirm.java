package view.subView;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import main.ChatManager;
import view.mainView.MainWindow;

public class LogoutConfirm extends JInternalFrame{
	private JLabel title=null;
	private JButton buttonOk=null;
	private JButton buttonCancel=null;
	private JPanel contentPane=null;
	private static Point framePoint=new Point();
	private void init() {
		title=new JLabel("确定注销?");
		buttonOk=new JButton("是");
		buttonCancel=new JButton("否");
		contentPane=new JPanel();
	}
	private void setComponent() {
		this.setLocation(360,200);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setBackground(new Color(0,0,0));
		title.setBackground(new Color(0,0,0));
		title.setForeground(new Color(255,255,255));
		buttonOk.setBackground(new Color(0,0,0));
		buttonOk.setForeground(new Color(255,255,255));
		buttonCancel.setBackground(new Color(0,0,0));
		buttonCancel.setForeground(new Color(255,255,255));
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		this.setSize(100,100);
	}
	private void addComponent() {
		this.add(title, BorderLayout.NORTH);
		this.add(buttonOk,BorderLayout.CENTER);
		this.add(buttonCancel,BorderLayout.SOUTH);
	}
	private void addEvent() {
		/*title.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				framePoint.x=e.getX();
				framePoint.y=e.getY();
			}
		});
		title.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point point=LogoutConfirm.this.getLocation();
				int x=point.x+e.getX()-framePoint.x;
				int y=point.y+e.getY()-framePoint.y;
				MainWindow window=ChatManager.getChatManager().getClientWindow();
				if(x<0) x=0;
				if(y<0) y=0;
				if(x+LogoutConfirm.this.getWidth()>window.getWidth()-15) x=window.getWidth()-LogoutConfirm.this.getWidth()-15;
				if(y+LogoutConfirm.this.getHeight()>window.getHeight()-38) y=window.getHeight()-LogoutConfirm.this.getHeight()-38;
				LogoutConfirm.this.setLocation(x,y);
			}
		});*/
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogoutConfirm.this.setVisible(false);
				StringBuilder msg=new StringBuilder("0110o*");
				ChatManager chatmanager=ChatManager.getChatManager();
				msg.append("OTHERS*"+chatmanager.getClientUserId()+"*下线了.");
				try {
					chatmanager.sendMsg(msg.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				MainWindow window=chatmanager.getClientWindow();
				window.setContentPane(window.getLoginPane());
				window.getChatPane().getChatFrame().setVisible(false);
				window.getChatPane().getRoleFrame().setVisible(false);
				window.getChatPane().getChatLabel().setVisible(true);
			}
		});
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LogoutConfirm.this.setVisible(false);
			}
		});
	}
	public LogoutConfirm() {
		init();
		setComponent();
		addComponent();
		this.pack();
		addEvent();
	}
}
