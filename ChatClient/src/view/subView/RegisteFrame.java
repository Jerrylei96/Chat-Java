package view.subView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class RegisteFrame extends JInternalFrame{
	private JPanel registePane;
	private JLabel labelAccount;
	private JTextField txtAccount;
	private JLabel labelPassword;
	private JTextField txtPassword;
	private JLabel labelUserName;
	private JTextField txtUserName;
	private JButton buttonCancel;
	private JButton buttonOk;
	private LoginView parentPane;
	
	private void init(LoginView parentPane) {
		this.parentPane=parentPane;
		registePane=new JPanel();
		labelAccount=new JLabel("用户名");
		txtAccount=new JTextField();
		labelPassword=new JLabel("密码");
		txtPassword=new JTextField();
		labelUserName=new JLabel("昵称");
		txtUserName=new JTextField();
		buttonCancel=new JButton("取消");
		buttonOk=new JButton("注册");
	}
	private void setComponent() {
		this.setBounds(230, 600/7, 332, 404);
		this.setContentPane(registePane);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("注册");
		((BasicInternalFrameUI)this.getUI()).setNorthPane(null);
		registePane.setLayout(null);
		
		labelAccount.setBounds(65, 58, 58, 15);
		txtAccount.setBounds(133, 55, 138, 21);
		labelPassword.setBounds(65, 131, 58, 15);
		txtPassword.setBounds(133, 128, 138, 21);
		labelUserName.setBounds(65, 213, 58, 15);
		txtUserName.setBounds(133, 210, 138, 21);
		buttonCancel.setBounds(65, 297, 79, 23);
		buttonOk.setBounds(187, 297, 84, 23);
	}
	private void addComponent() {
		registePane.add(labelAccount);
		registePane.add(txtAccount);
		registePane.add(labelPassword);
		registePane.add(txtPassword);
		registePane.add(labelUserName);
		registePane.add(txtUserName);
		registePane.add(buttonCancel);
		registePane.add(buttonOk);
	}
	private void addEvent() {
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RegisteFrame.this.doDefaultCloseAction();
			}
		});
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			}
		});
	}
	public RegisteFrame(LoginView parentPane) {
		init(parentPane);
		setComponent();
		addComponent();
		addEvent();
	}
}
