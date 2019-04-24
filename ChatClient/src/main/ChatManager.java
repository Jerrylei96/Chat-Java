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
 * 通信协议
 * 默认分割符号为"*"
 * 第一字段head:
 * 0000 表示登录请求
 * 0001 表示登录应答
 * 0010 表示注册请求
 * 0011 表示注册应答
 * 0100 表示全服聊天
 * 0101 表示交易聊天
 * 0110 表示私人聊天
 * 0110d 表示私人聊天目的方
 * 0110s 表示私人聊天发送方
 * 0110n 表示私人聊天无法找到对方
 * 0110e 表示私人聊天发给了自己
 * 0110i 表示私人聊天系统消息提示登录玩家
 * 0110o 表示私人聊天系统消息提示登出玩家
 * 第二字段body1:
 * 在聊天情况下:接受方id,如果head为110则此字段有效，如何head为101、100此字段为空
 * 在登录请求和注册请求情况下:账户
 * 在登录应答和注册应答情况下:"TRUE"和"FALSE"
 * 第二字段body2:
 * 在聊天情况下:发送方id
 * 在登录请求和注册请求情况下:密码
 * 在登录应答情况下:登录方id
 * 第三字段body3:
 * 发送内容
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
							//登录应答处理
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
								buttonLogin.setText("登录");
								window.getChatPane().getRoleFrame().getLabelId().setText(id);
								txtArea=window.getChatPane().getChatFrame().getGlobalChat();
								txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
										":"+now.get(Calendar.SECOND)+"][系统公告]:Welcome to PokeMMo!Enjoy your stay.\n");
								txtArea=window.getChatPane().getChatFrame().getTradeChat();
								txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
										":"+now.get(Calendar.SECOND)+"][系统公告]:Welcome to PokeMMo!Enjoy your stay.\n");
								txtArea=window.getChatPane().getChatFrame().getWhipserChat();
								txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
										":"+now.get(Calendar.SECOND)+"][系统公告]:Welcome to PokeMMo!Enjoy your stay.\n");
							}
							else {
								System.out.println("登录失败");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								JButton buttonLogin=window.getLoginPane().getLoginFrame().getButtonLogin();
								buttonLogin.setBounds(156, 99, 68, 21);
								buttonLogin.setText("登录");
							}
							break;
						case "0011":
							//注册应答处理
							break;
						case "0100":
							txtArea=window.getChatPane().getChatFrame().getGlobalChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][全服]"+body2+": "+body3+"\n");
							break;
						case "0101":
							txtArea=window.getChatPane().getChatFrame().getTradeChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][交易]"+body2+": "+body3+"\n");
							break;
						case "0110d":
							//私人聊天接收方
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][私聊]"+"来自"+body2+": "+body3+"\n");
							break;
						case "0110s":
							//私人聊天发送方
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][私聊]"+"发给"+body1+": "+body3+"\n");
							break;
						case "0110n":
							//私人聊天无法找到对方
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][私聊]"+body3+".\n");
							break;
						case "0110e":
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][私聊]"+body3+"\n");
							break;
						case "0110i":
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][系统消息]"+body2+body3+"\n");
							break;
						case "0110o":
							txtArea=window.getChatPane().getChatFrame().getWhipserChat();
							txtArea.append("["+now.get(Calendar.HOUR_OF_DAY) +":"+ now.get(Calendar.MINUTE)+
									":"+now.get(Calendar.SECOND)+"][系统消息]"+body2+body3+"\n");
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
