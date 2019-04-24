package main;

import view.mainView.MainWindow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.swing.JButton;

/**
 * ͨ��Э��
 * Ĭ�Ϸָ����Ϊ"*"
 * ��һ�ֶ�head:
 * 0000 ��ʾ��¼����
 * 0001 ��ʾ��¼Ӧ��
 * 0010 ��ʾע������
 * 0011 ��ʾע��Ӧ��
 * 0100 ��ʾȫ������
 * 0101 ��ʾ��������
 * 0110 ��ʾ˽������
 * 0110d ��ʾ˽������Ŀ�ķ�
 * 0110s ��ʾ˽�����췢�ͷ�
 * 0110n ��ʾ˽�������޷��ҵ��Է�
 * 0110e ��ʾ˽�����췢�����Լ�
 * 0110i ��ʾ˽������ϵͳ��Ϣ��ʾ��¼���
 * 0110o ��ʾ˽������ϵͳ��Ϣ��ʾ�ǳ����
 * �ڶ��ֶ�body1:
 * �����������:���ܷ�id,���headΪ110����ֶ���Ч�����headΪ101��100���ֶ�Ϊ��
 * �ڵ�¼�����ע�����������:�˻�
 * �ڵ�¼Ӧ���ע��Ӧ�������:"TRUE"��"FALSE"
 * �ڶ��ֶ�body2:
 * �����������:���ͷ�id
 * �ڵ�¼�����ע�����������:����
 * �ڵ�¼Ӧ�������:��¼��id
 * �����ֶ�body3:
 * ��������
 * 
 * 
 */



import javax.swing.JTextArea;

public class ChatManager {
	private ChatManager() {}
	private static final ChatManager instance=new ChatManager();
	public static ChatManager getChatManager() {
		return instance;
	}
	private MainWindow window;
	private Socket socket;
	private String id;
	//private static String IP="127.0.0.1";
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	public void setWindow(MainWindow window) {
		this.window=window;
	}
	public String getClientUserId() {
		return id;
	}
	public MainWindow getClientWindow() {
		return window;
	}
	public void connect(String IP) {
		new Thread() {
			public void run() {
				try {
					socket=new Socket(IP,80);
					window.getLoginPane().addLoginFrame();
					window.setVisible(true);
					printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"GBK"));
					bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
					String head=null;
					String body1=null;
					String body2=null;
					String body3=null;
					StringTokenizer receivedMsg=null; 
					String msg=null;
					JTextArea txtArea=null;
					Calendar now=null;
					while((msg=getMsg())!=null) {
						now = Calendar.getInstance();
						receivedMsg=new StringTokenizer(msg,"*");
						head=(String)receivedMsg.nextElement();
						body1=(String)receivedMsg.nextElement();
						body2=(String)receivedMsg.nextElement();
						body3=(String)receivedMsg.nextElement();
						switch(head) {
						case "0001":
							//��¼Ӧ����
							if(body3.equals("TRUE")) {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								window.setContentPane(window.getChatPane());
								window.validate();
								id=body1;
								JButton buttonLogin=window.getLoginPane().getLoginFrame().getButtonLogin();
								buttonLogin.setBounds(156, 99, 68, 21);
								buttonLogin.setText("��¼");
								window.getChatPane().getRoleFrame().getLabelId().setText(id);
								txtArea=window.getChatPane().getChatFrame().getGlobalChat();
								txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
										":"+now.get(Calendar.SECOND)+"][ϵͳ����]:Welcome to PokeMMo!Enjoy your stay.\n");
								txtArea=window.getChatPane().getChatFrame().getTradeChat();
								txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
										":"+now.get(Calendar.SECOND)+"][ϵͳ����]:Welcome to PokeMMo!Enjoy your stay.\n");
								txtArea=window.getChatPane().getChatFrame().getWhipserChat();
								txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
										":"+now.get(Calendar.SECOND)+"][ϵͳ����]:Welcome to PokeMMo!Enjoy your stay.\n");
							}
							else {
								System.out.println("��¼ʧ��");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								JButton buttonLogin=window.getLoginPane().getLoginFrame().getButtonLogin();
								buttonLogin.setBounds(156, 99, 68, 21);
								buttonLogin.setText("��¼");
							}
							break;
						case "0011":
							//ע��Ӧ����
							break;
						case "0100":
							txtArea=window.getChatPane().getChatFrame().getGlobalChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][ȫ��]"+body2+": "+body3+"\n");
							break;
						case "0101":
							txtArea=window.getChatPane().getChatFrame().getTradeChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][����]"+body2+": "+body3+"\n");
							break;
						case "0110d":
							//˽��������շ�
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][˽��]"+"����"+body2+": "+body3+"\n");
							break;
						case "0110s":
							//˽�����췢�ͷ�
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][˽��]"+"����"+body1+": "+body3+"\n");
							break;
						case "0110n":
							//˽�������޷��ҵ��Է�
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][˽��]"+body3+".\n");
							break;
						case "0110e":
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][˽��]"+body3+"\n");
							break;
						case "0110i":
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][ϵͳ��Ϣ]"+body2+body3+"\n");
							break;
						case "0110o":
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][ϵͳ��Ϣ]"+body2+body3+"\n");
							break;
						default:
							System.out.println("Error message!");
							break;
						}
					}
					printWriter.close();
					try {
						bufferedReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}catch(UnknownHostException e) {
					e.printStackTrace();
				}catch(ConnectException e) {
					window.getLoginPane().addNetError();
					window.setVisible(true);
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	public void sendMsg(String msg)throws IOException {
		printWriter.write(msg+"\n");
		printWriter.flush();
	}
	public String getMsg()throws IOException{
		return bufferedReader.readLine();
	}
}
