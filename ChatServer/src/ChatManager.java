
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 * 在登录应答和注册应答情况下:"OK"
 * 第二字段body2:
 * 在聊天情况下:发送方id,
 * 在登录请求和注册请求情况下:密码
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
