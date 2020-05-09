package com.qq.client.tools;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;


public class ClientConnServerThread implements Runnable{
	public Socket s;
	
	public ClientConnServerThread(Socket s){
		this.s=s;
	}
	public Socket getS(){
		return s;
	}
	@Override
	public void run(){
		while(true){
			try{
				ObjectInputStream ois =new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				if(m.getMsType().equals(MessageType.message_comm_mess))
				{
						//如果服务器返回的是普通聊天包，则显示到相应聊天界面
					System.out.println("接受到"+m.getSender()+"发来的信息:"+m.getContent());
						//V4--7  把从服务器获得的消息，显示到该显示的聊天界面
						QqChat qqChat=ManageQqChat.getQqChat(m.getReceiver()+"yu"+m.getSender());
						//V4--7 显示
						qqChat.gettalk(m);
				}else if(m.getMsType().equals(MessageType.message_response_onLineFriend))
				{
						//如果服务器返回的是请求在线好友情况的应答包，则取得在线好友，高亮显示
						System.out.println("客户端接收到"+m.getContent());

						//V5--如果从服务器接收的包是在线好友列表请求包的回送包，则取出相应信息
						String content=m.getContent();
						//V5--如果从服务器接收的包是在线好友列表请求包的回送包，则取出相应信息
						String friends[]=content.split("");
						String receiver=m.getReceiver();//此应答包的接收者（即向服务器请求好友列表的QQ用户）

						//取得相应QQ号的好友列表界面（因为要调用此界面的高亮显示在线好友方法）
						QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(receiver);
						//更新在线好友--调用QqFriendList里的高亮显示好友方法
						int count=friends.length;
						int num[]=new int[count];
						for(int i=0;i<count;i++) {
							num[i]=Integer.parseInt(friends[i]);
							qqFriendList.HightLightOnLineFriend(num[i]);
						}
				}


			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
