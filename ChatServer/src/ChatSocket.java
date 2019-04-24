
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

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
 * 0110d 表示私人聊天目的方
 * 0110s 表示私人聊天发送方
 * 0110n 表示私人聊天无法找到对方
 * 0110e 表示私人聊天发给了自己
 * 0110i 表示私人聊天系统消息提示登录玩家
 * 0110o 表示私人聊天系统消息提示登出玩家
 * 第二字段body1:
 * 目的方id
 * 在聊天情况下:接受方id,如果head为110则此字段有效，如何head为101、100此字段为空
 * 在登录请求和注册请求情况下:账户
 * 在登录应答和注册应答情况下:"OK"
 * 第二字段body2:
 * 源方id
 * 在聊天情况下:发送方id,
 * 在登录请求和注册请求情况下:密码
 * 
 */

public class ChatSocket extends Thread{
	Socket socket;
	String id;
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	public ChatSocket(Socket s) {
		this.socket=s;
		try {
			bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
			printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"GBK"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		try {
			String head=null;
			String body1=null;
			String body2=null;
			String body3=null;
			StringTokenizer receivedMsg=null;
			String msg=null;
			StringBuffer sentMsg=null;
			while((msg=getMsg())!=null) {
				receivedMsg=new StringTokenizer(msg,"*");
				try {
					head=(String)receivedMsg.nextElement();
					body1=(String)receivedMsg.nextElement();
					body2=(String)receivedMsg.nextElement();
					body3=(String)receivedMsg.nextElement();
				}catch(NoSuchElementException e) {
					sentMsg=new StringBuffer("0001*");
					sentMsg.append("NULL*SERVER*FALSE");
					sendMsg(sentMsg.toString());
					continue;
				}
				switch(head) {
				case "0000":
					sentMsg=new StringBuffer("0001*");
					if(loginCheck(body1,body2)) {
						ChatManager.getChatManager().add(this);
						id=body1;
						sentMsg.append(id+"*SERVER*TRUE");
						StringBuffer sentOthers=new StringBuffer("0110i*");
						sentOthers.append("OHTERS*"+body1+"*上线了.");
						ChatManager.getChatManager().publishExceptSelf(this,sentOthers.toString());
					}
					else {
						sentMsg.append(body1+"*SERVER*FALSE");
					}
					sendMsg(sentMsg.toString());
					break;
				case "0010":
					//注册请求处理
					break;
				case "0100":
				case "0101":
					//交易聊天、全服聊天
					ChatManager.getChatManager().publishAll(msg);
					break;
				case "0110":
					//私人聊天
					StringBuffer dest=new StringBuffer("0110d*");
					dest.append(body1+"*"+body2+"*"+body3);
					StringBuffer sour=new StringBuffer("0110s*");
					sour.append(body1+"*"+body2+"*"+body3);
					StringBuffer self=new StringBuffer("0110e*");
					self.append(body1+"*"+body2+"*您不可以和自己私聊.");
					if(!ChatManager.getChatManager().publishOne(this,dest.toString(),sour.toString(),self.toString(),body1)) {
						sentMsg=new StringBuffer("0110n*");
						sentMsg.append(body1+"*");
						sentMsg.append(body2+"*");
						sentMsg.append("无法找到玩家"+body1);
						sendMsg(sentMsg.toString());
					}
					break;
				case "0110o":
					List<ChatSocket> list=ChatManager.getChatManager().list;
					list.remove(this);
					try {
						ChatManager.getChatManager().publishAll(msg.toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					System.out.println("Error message!");
					break;
				}
			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			printWriter.close();
		}catch(SocketException e) {
			StringBuffer msgOut=new StringBuffer("0110o*");
			msgOut.append("OTHERS*"+this.id+"*下线了.");
			List<ChatSocket> list=ChatManager.getChatManager().list;
			list.remove(this);
			try {
				ChatManager.getChatManager().publishAll(msgOut.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public boolean loginCheck(String id,String password) {
		List<ChatSocket> list=ChatManager.getChatManager().list;
		for(ChatSocket chatSocket:list)
			if(chatSocket.id.equals(id))
				return false;
		return true;
	}
	public void sendMsg(String msg){
		printWriter.write(msg+"\n");
		printWriter.flush();
	}
	public String getMsg() throws IOException{
		return bufferedReader.readLine();
	}
}
