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
						//������������ص�����ͨ�����������ʾ����Ӧ�������
					System.out.println("���ܵ�"+m.getSender()+"��������Ϣ:"+m.getContent());
						//V4--7  �Ѵӷ�������õ���Ϣ����ʾ������ʾ���������
						QqChat qqChat=ManageQqChat.getQqChat(m.getReceiver()+"yu"+m.getSender());
						//V4--7 ��ʾ
						qqChat.gettalk(m);
				}else if(m.getMsType().equals(MessageType.message_response_onLineFriend))
				{
						//������������ص����������ߺ��������Ӧ�������ȡ�����ߺ��ѣ�������ʾ
						System.out.println("�ͻ��˽��յ�"+m.getContent());

						//V5--����ӷ��������յİ������ߺ����б�������Ļ��Ͱ�����ȡ����Ӧ��Ϣ
						String content=m.getContent();
						//V5--����ӷ��������յİ������ߺ����б�������Ļ��Ͱ�����ȡ����Ӧ��Ϣ
						String friends[]=content.split("");
						String receiver=m.getReceiver();//��Ӧ����Ľ����ߣ������������������б��QQ�û���

						//ȡ����ӦQQ�ŵĺ����б���棨��ΪҪ���ô˽���ĸ�����ʾ���ߺ��ѷ�����
						QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(receiver);
						//�������ߺ���--����QqFriendList��ĸ�����ʾ���ѷ���
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
