
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 * �ڵ�¼Ӧ���ע��Ӧ�������:"OK"
 * �ڶ��ֶ�body2:
 * �����������:���ͷ�id,
 * �ڵ�¼�����ע�����������:����
 * 
 */

public class ChatManager {
	List<ChatSocket> list=new ArrayList<ChatSocket>();
	private ChatManager() {}
	private static final ChatManager CM=new ChatManager();
	public static ChatManager getChatManager() {
		return CM;
	}
	public void add(ChatSocket cs) {
		list.add(cs);
	}
	public boolean publishOne(ChatSocket cs,String msgDest,String msgSour,String smgSelf,String destId)throws IOException{
		for(ChatSocket chatSocket:list) {
			if(chatSocket.id.equals(destId)) {
				if(cs.id.equals(destId)) {
					cs.sendMsg(smgSelf);
					return true;
				}else {
					chatSocket.sendMsg(msgDest);
					cs.sendMsg(msgSour);
					return true;
				}
			}
		}
			
		return false;
		
	}
	public void publishExceptSelf(ChatSocket cs,String msg) {
		for(ChatSocket chatSocket:list){
			if(cs.id.equals(chatSocket.id))
				continue;
			chatSocket.sendMsg(msg);
		}
	}
	public void publishAll(String msg) throws IOException{
		for(ChatSocket chatSocket:list) 
			chatSocket.sendMsg(msg);
	}
}
