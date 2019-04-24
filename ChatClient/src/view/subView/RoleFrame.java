package view.subView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import main.ChatManager;
import view.mainView.MainWindow;

public class RoleFrame extends JInternalFrame{
	private JLayeredPane layeredPane;
	private JPanel backPane;
	private JPanel componentPane;
	private static Point roleFrameLocation=new Point();
	private JLabel labelId;
	private ImageIcon imageIcon;
	public JLabel getLabelId() {
		return labelId;
	}
	private void init() {
		imageIcon=new ImageIcon(".\\material\\RoleTexture.jpg");
		backPane=new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(imageIcon.getImage(),0,0,null);
			}
		};
		componentPane=new JPanel(null);
		layeredPane=new JLayeredPane();
		labelId=new JLabel();
	}
	private void setComponent() {
		
		
		this.setBounds(200,100,360,230);
		this.setLayeredPane(layeredPane);
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		this.setVisible(false);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		backPane.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		componentPane.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		componentPane.setOpaque(false);
		labelId.setBounds(10,13,220,20);
		
	}
	private void addComponent() {
		componentPane.add(labelId);
		layeredPane.add(backPane, JLayeredPane.DEFAULT_LAYER);
		layeredPane.add(componentPane, JLayeredPane.MODAL_LAYER);
		
	}
	private void addEvent() {
		layeredPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				roleFrameLocation.x=e.getX();
				roleFrameLocation.y=e.getY();
			}
		});
		layeredPane.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point point=RoleFrame.this.getLocation();
				int x=point.x+e.getX()-roleFrameLocation.x;
				int y=point.y+e.getY()-roleFrameLocation.y;
				MainWindow window=ChatManager.getChatManager().getClientWindow();
				if(x<0) x=0;
				if(y<0) y=0;
				if(x+RoleFrame.this.getWidth()>window.getWidth()-15) x=window.getWidth()-RoleFrame.this.getWidth()-15;
				if(y+RoleFrame.this.getHeight()>window.getHeight()-38) y=window.getHeight()-RoleFrame.this.getHeight()-38;
				RoleFrame.this.setLocation(x,y);
			}
		});
	}
	public RoleFrame() {
		this.setMaximizable(true);
		init();
		setComponent();
		addComponent();
		addEvent();
	}
}
