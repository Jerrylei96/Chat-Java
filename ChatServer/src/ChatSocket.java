
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

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
 * 0110d ��ʾ˽������Ŀ�ķ�
 * 0110s ��ʾ˽�����췢�ͷ�
 * 0110n ��ʾ˽�������޷��ҵ��Է�
 * 0110e ��ʾ˽�����췢�����Լ�
 * 0110i ��ʾ˽������ϵͳ��Ϣ��ʾ��¼���
 * 0110o ��ʾ˽������ϵͳ��Ϣ��ʾ�ǳ����
 * �ڶ��ֶ�body1:
 * Ŀ�ķ�id
 * �����������:���ܷ�id,���headΪ110����ֶ���Ч�����headΪ101��100���ֶ�Ϊ��
 * �ڵ�¼�����ע�����������:�˻�
 * �ڵ�¼Ӧ���ע��Ӧ�������:"OK"
 * �ڶ��ֶ�body2:
 * Դ��id
 * �����������:���ͷ�id,
 * �ڵ�¼�����ע�����������:����
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
						sentOthers.append("OHTERS*"+body1+"*������.");
						ChatManager.getChatManager().publishExceptSelf(this,sentOthers.toString());
					}
					else {
						sentMsg.append(body1+"*SERVER*FALSE");
					}
					sendMsg(sentMsg.toString());
					break;
				case "0010":
					//ע��������
					break;
				case "0100":
				case "0101":
					//�������졢ȫ������
					ChatManager.getChatManager().publishAll(msg);
					break;
				case "0110":
					//˽������
					StringBuffer dest=new StringBuffer("0110d*");
					dest.append(body1+"*"+body2+"*"+body3);
					StringBuffer sour=new StringBuffer("0110s*");
					sour.append(body1+"*"+body2+"*"+body3);
					StringBuffer self=new StringBuffer("0110e*");
					self.append(body1+"*"+body2+"*�������Ժ��Լ�˽��.");
					if(!ChatManager.getChatManager().publishOne(this,dest.toString(),sour.toString(),self.toString(),body1)) {
						sentMsg=new StringBuffer("0110n*");
						sentMsg.append(body1+"*");
						sentMsg.append(body2+"*");
						sentMsg.append("�޷��ҵ����"+body1);
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
			msgOut.append("OTHERS*"+this.id+"*������.");
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
