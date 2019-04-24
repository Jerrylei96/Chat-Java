package view.subView;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;



import main.ChatManager;

public class LoginFrame extends JInternalFrame{
	private JPanel loginPane;
	private JLabel labelAccount;
	private JLabel labelPassword;
	private JTextField txtAccount;
	private JPasswordField txtPassword;
	private JButton buttonRegister;
	private JButton buttonLogin;
	private LoginView parentPane;
	public JButton getButtonLogin() {
		return buttonLogin;
	}
	public String getAccount() {
		return txtAccount.getText();
	}
	public void setAccount(String str) {
		txtAccount.setText(str);
	}
	public String getPassword() {
		return txtPassword.getPassword().toString();
	}
	private void init(LoginView parentPane) {
		loginPane=new JPanel();
		this.parentPane=parentPane;
		labelAccount=new JLabel("ÓÃ»§Ãû:");
		txtAccount=new JTextField();
		labelPassword=new JLabel("ÃÜ    Âë:");
		txtPassword=new JPasswordField();
		buttonRegister=new JButton("×¢²á");
		buttonLogin=new JButton("µÇÂ¼");
	}
	private void setComponent() {
		this.setBounds(800/3+8,600/3,250,150);
		this.setContentPane(loginPane);
		this.setResizable(false);
		this.setVisible(true);
		loginPane.setLayout(null);
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		loginPane.setBackground(new Color(50,50,50));
		labelAccount.setBounds(20, 19, 58, 21);
		labelAccount.setForeground(new Color(255,255,255));
		txtAccount.setBounds(64, 19, 158, 21);
		txtAccount.setForeground(new Color(255,255,255));
		txtAccount.setBackground(new Color(20,20,20));
		labelPassword.setBounds(20, 59, 58, 21);
		labelPassword.setForeground(new Color(255,255,255));
		labelPassword.setBackground(new Color(20,20,20));
		txtPassword.setBounds(64, 59, 158, 21);
		txtPassword.setForeground(new Color(255,255,255));
		txtPassword.setBackground(new Color(20,20,20));
		buttonRegister.setBounds(20, 99, 73, 21);
		buttonRegister.setForeground(new Color(255,255,255));
		buttonRegister.setBackground(new Color(20,20,20));
		buttonLogin.setBounds(156, 99, 68, 21);
		buttonLogin.setForeground(new Color(255,255,255));
		buttonLogin.setBackground(new Color(20,20,20));
	}
	private void addComponent() {
		loginPane.add(labelAccount);
		loginPane.add(txtAccount);
		loginPane.add(labelPassword);
		loginPane.add(txtPassword);
		loginPane.add(buttonRegister);
		loginPane.add(buttonLogin);
	}
	private void addEvent() {
		buttonRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					LoginFrame.this.buttonLogin.setBounds(142, 99, 82, 21);
					LoginFrame.this.buttonLogin.setText("µÇÂ¼ÖÐ...");
					StringBuffer msg=new StringBuffer("0000*");
					msg.append(txtAccount.getText()+"*");
					msg.append(txtPassword.getText()+"*");
					msg.append("NULL");
					ChatManager.getChatManager().sendMsg(msg.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	public LoginFrame(LoginView parentPane) {
		init(parentPane);
		setComponent();
		addComponent();
		addEvent();
	}
}
