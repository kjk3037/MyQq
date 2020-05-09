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
					System.out.println(m.getSender()+"��"+m.getReceiver()+"˵:"+m.getContent());
					//V3.3�������ͨ�����--ת��
					//ȡ�ý����˵�ͨ���߳�
					SerConnClientThread sc=ManageClientThread.getClientThread(m.getReceiver());
					// ���������ת��
					ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
				}else if(m.getMsType().equals(MessageType.message_request_onLineFriend)){
					//������������ߺ��Ѱ�
					//--��ӹ����¼�û�������ȡ�������û�response����װ��һ�������ظ��ͻ��ˡ�
					System.out.println("�ӿͻ��˽��յ�"+m.getSender()+"��������Ѱ�");
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
		//�õ����������˵��߳�
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext()){
			Message m=new Message();
			m.setContent(myself);
			m.setMsType(MessageType.message_response_onLineFriend);
			//ȡ�������˵�id
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
