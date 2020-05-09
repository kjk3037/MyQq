package com.qq.client.model;

import com.qq.client.tools.ClientConnServerThread;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.client.tools.ManageQqFriendList;
import com.qq.client.view.QqFriendList;
import com.qq.common.*;
import java.net.*;
import java.io.*;
public class QqClientConnServer {
	public  Socket s;
	//�ͻ��˷��͵�һ������
	public boolean sendLoginInfoToServer(Object o)
	{
		boolean b=false;
		try{
			s=new Socket("localhost",1481);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			//����һ����Ӧ
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			if(ms.getMsType().equals("1")){
				b=true;
				ClientConnServerThread ccst=new ClientConnServerThread(s);
				Thread t1=new Thread(ccst);	
				t1.start();
				ManageClientConnServerThread.addClientConnServerThread(((User) o).getUser(), ccst);
				QqFriendList qqfriend =new QqFriendList(((User)o).getUser());
				ManageQqFriendList.addQqFriendList(((User) o).getUser(), qqfriend);

			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return b;
	}
}
