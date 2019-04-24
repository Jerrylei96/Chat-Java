package view.subView;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import main.ChatManager;
import view.mainView.MainWindow;

import java.awt.event.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ChatFrame extends JInternalFrame{
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JLabel closeLabel;
	private JScrollPane globalScrollPane;
	private JScrollPane whisperScrollPane;
	private JScrollPane tradeScrollPane;
	private JTextArea txtGlobal;
	private JTextArea txtWhisper;
	private JTextArea txtTrade;
	private JButton buttonChoose;
	private JList typeChoose;
	private String[] items;
	private JTextField txtSendContent;
	private JButton buttonSend;
	private ChatView parentFrame;
	private static Point chatFrameLocation=new Point();
	private static final int BUTTONCHOOSEWIDTH=60;
	private static final int BUTTONCHOOSEHEIGHT=20;
	private static final int TXTSENTCONTENTX=63;
	private static final int TXTSENTCONTENTY=474;
	private static final int TXTSENDCONTENTWIDTH=390;
	private static final int TXTSENTCONTENTHEIGHT=21;
	private static final int TXTCHANGEWIDTH=370;
	public JTextArea getGlobalChat() {
		return txtGlobal;
	}
	public JTextArea getTradeChat() {
		return txtTrade;
	}
	public JTextArea getWhipserChat() {
		return txtWhisper;
	}
	private void init(ChatView parent) {
		parentFrame=parent;
		contentPane=new JPanel(null);
		ImageIcon image=new ImageIcon(".\\material\\close.png");
		closeLabel=new JLabel(image);
		tabbedPane=new JTabbedPane(JTabbedPane.TOP);
		txtGlobal=new JTextArea();
		txtWhisper=new JTextArea();
		txtTrade=new JTextArea();
		globalScrollPane=new JScrollPane(txtGlobal);
		whisperScrollPane=new JScrollPane(txtWhisper);
		tradeScrollPane=new JScrollPane(txtTrade);
		buttonChoose=new JButton("全服");
		items=new String[] {"全服[/global]","交易[/trade]"};
		typeChoose=new JList<String>(items);
		txtSendContent=new JTextField();
		buttonSend=new JButton("发送");
	}
	private void setComponent() {
		
		closeLabel.setBounds(490,2,20,20);
		contentPane.setBackground(Color.BLACK);
		this.setContentPane(contentPane);
		this.setSize(520,500);
		this.setLocation(20,15);
		this.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		tabbedPane.setBounds(0, 0, 518, 470);
		txtGlobal.setBackground(new Color(59,79,79));
		txtGlobal.setForeground(new Color(255,255,255));
		txtGlobal.setEditable(false);
		
		txtTrade.setBackground(new Color(59,79,79));
		txtTrade.setForeground(new Color(255,255,255));
		txtTrade.setEditable(false);
		txtWhisper.setBackground(new Color(59,79,79));
		txtWhisper.setForeground(new Color(255,255,255));
		txtWhisper.setEditable(false);
		buttonChoose.setBounds(2, 474, BUTTONCHOOSEWIDTH, BUTTONCHOOSEHEIGHT);
		buttonChoose.setBackground(new Color(59,79,79));
		buttonChoose.setForeground(new Color(255,255,255));
		buttonChoose.setFont(new Font("宋体",Font.PLAIN,11));
		buttonChoose.setToolTipText(buttonChoose.getText());
		typeChoose.setBounds(2,440,75,30);
		typeChoose.setBackground(new Color(20,20,20));
		typeChoose.setForeground(new Color(255,255,255));
		typeChoose.setFont(new Font("宋体",Font.PLAIN,10));
		typeChoose.setVisible(false);
		this.add(typeChoose);
		buttonChoose.setEnabled(false);
		txtSendContent.setBounds(TXTSENTCONTENTX,TXTSENTCONTENTY,TXTSENDCONTENTWIDTH,TXTSENTCONTENTHEIGHT);
		txtSendContent.setBackground(new Color(59,79,79));
		txtSendContent.setForeground(new Color(255,255,255));
		buttonSend.setBounds(455, 474, 60, 20);
		buttonSend.setBackground(new Color(59,79,79));
		buttonSend.setForeground(new Color(255,255,255));
		buttonSend.setEnabled(false);
	}
	private void addComponent() {
		tabbedPane.addTab("Global", globalScrollPane);
		tabbedPane.addTab("Trade",tradeScrollPane);
		tabbedPane.addTab("Whisper",whisperScrollPane);
		this.add(closeLabel);
		this.add(buttonChoose);
		this.add(tabbedPane);
		this.add(txtSendContent);
		this.add(buttonSend);
	}
	private void addEvent() {
		buttonSend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String msg=ChatFrame.this.txtSendContent.getText().trim();
				if(msg.startsWith("/w")) {
					msg=msg.replace("/w","");
					msg=msg.trim();
					JButton buttonChoose=ChatFrame.this.buttonChoose;
					JTextField txtSendContent=ChatFrame.this.txtSendContent;
					buttonChoose.setSize(BUTTONCHOOSEWIDTH+20,BUTTONCHOOSEHEIGHT);
					txtSendContent.setBounds(TXTSENTCONTENTX+20,TXTSENTCONTENTY,
							TXTCHANGEWIDTH,TXTSENTCONTENTHEIGHT);
					buttonChoose.setText(msg);
					ChatFrame.this.txtSendContent.setText("");
					buttonChoose.setToolTipText(buttonChoose.getText());
					return;
				}
				StringBuffer sentMsg=null;
				//Calendar now = Calendar.getInstance();
				if((msg.length()!=0)) {
					String type=ChatFrame.this.buttonChoose.getText();
					if(type.equals("全服")) {
						sentMsg=new StringBuffer("0100*");
						sentMsg.append(type+"*");
						sentMsg.append(ChatManager.getChatManager().getClientUserId()+"*");
						sentMsg.append(msg);
						System.out.println(msg);
						try {
							ChatManager.getChatManager().sendMsg(sentMsg.toString());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else if(type.equals("交易")) {
						sentMsg=new StringBuffer("0101*");
						sentMsg.append(type+"*");
						sentMsg.append(ChatManager.getChatManager().getClientUserId()+"*");
						sentMsg.append(msg);
						try {
							ChatManager.getChatManager().sendMsg(sentMsg.toString());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else {
						sentMsg=new StringBuffer("0110*");
						sentMsg.append(type+"*");
						sentMsg.append(ChatManager.getChatManager().getClientUserId()+"*");
						sentMsg.append(msg);
						try {
							ChatManager.getChatManager().sendMsg(sentMsg.toString());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					ChatFrame.this.txtSendContent.setText("");
				}
			}
		});
		buttonChoose.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JList newList=ChatFrame.this.typeChoose;
				if(newList.isVisible())
					newList.setVisible(false);
				else
					newList.setVisible(true);
			}
		});
		closeLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ChatFrame.this.setVisible(false);
				ChatFrame.this.parentFrame.getChatLabel().setVisible(true);
			}
		});
		typeChoose.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String str = (String)ChatFrame.this.typeChoose.getSelectedValue();
				if(str.equals("全服[/global]")) {
					ChatFrame.this.buttonChoose.setText("全服");
					ChatFrame.this.typeChoose.setVisible(false);
				}
				else {
					ChatFrame.this.buttonChoose.setText("交易");
					ChatFrame.this.typeChoose.setVisible(false);
				}
				buttonChoose.setSize(BUTTONCHOOSEWIDTH,BUTTONCHOOSEHEIGHT);
				txtSendContent.setBounds(TXTSENTCONTENTX,TXTSENTCONTENTY,TXTSENDCONTENTWIDTH,TXTSENTCONTENTHEIGHT);
				buttonChoose.setToolTipText(buttonChoose.getText());
			}
		});
		tabbedPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				chatFrameLocation.x=e.getX();
				chatFrameLocation.y=e.getY();
			}
		});
		tabbedPane.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point point=ChatFrame.this.getLocation();
				int x=point.x+e.getX()-chatFrameLocation.x;
				int y=point.y+e.getY()-chatFrameLocation.y;
				MainWindow window=ChatManager.getChatManager().getClientWindow();
				if(x<0) x=0;
				if(y<0) y=0;
				if(x+ChatFrame.this.getWidth()>window.getWidth()-15) x=window.getWidth()-ChatFrame.this.getWidth()-15;
				if(y+ChatFrame.this.getHeight()>window.getHeight()-38) y=window.getHeight()-ChatFrame.this.getHeight()-38;
				ChatFrame.this.setLocation(x,y);
			}
		});
	}
	public ChatFrame(ChatView parent) {
		init(parent);
		setComponent();
		addComponent();
		addEvent();
	}
}
