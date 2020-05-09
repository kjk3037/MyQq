package com.qq.server.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.qq.common.Message;
import com.qq.common.MessageType;

public class SerConnClientThread extends Thread{
	Socket s;
	public SerConnClientThread(Socket s) {
		this.s=s;
	}
	public void run() {
		while(true){
			try {
				
				ObjectInputStream ois =new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				if(m.getMsType().equals(MessageType.message_comm_mess))
				{
					System.out.println(m.getSender()+"对"+m.getReceiver()+"说:"+m.getContent());
					//V3.3如果是普通聊天包--转发
					//取得接收人的通信线程
					SerConnClientThread sc=ManageClientThread.getClientThread(m.getReceiver());
					// 创建输出流转发
					ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
				}else if(m.getMsType().equals(MessageType.message_request_onLineFriend)){
					//如果是请求在线好友包
					//--则从管理登录用户的类中取出在线用户response，封装进一个包返回给客户端。
					System.out.println("从客户端接收到"+m.getSender()+"的请求好友包");
					String res_content=ManageClientThread.getAllOnLineUserNo();
					Message m_res=new Message();
					m_res.setMsType(MessageType.message_response_onLineFriend);
					m_res.setContent(res_content);
					m_res.setReceiver(m.getSender());
				
					ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(m.getSender()).s.getOutputStream());
					//System.out.println(m_res.getContent());
					oos.writeObject(m_res);	
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void notifyOthers(String myself)
	{
		//得到所有在线人的线程
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext()){
			Message m=new Message();
			m.setContent(myself);
			m.setMsType(MessageType.message_response_onLineFriend);
			//取出在线人的id
			String onLineUserId=it.next().toString();
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setReceiver(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
