/**
 * ������QQ���������������ȴ�ĳ��QQ�ͻ��˵���������
 */
package com.qq.server.model;

import com.qq.common.Message;
import com.qq.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyQqServer{


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyQqServer();
	}
	public MyQqServer(){
		try{
			//��9999�˿ڼ���
			System.out.println("���Ƿ���������1481����������");
			ServerSocket ss=new ServerSocket(1481);
			//�������ȴ�����
			while(true){
				Socket s=ss.accept();
				
				 //���տͻ��˷�������Ϣ
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User u=(User)ois.readObject();
				System.out.println("���������յ��û�"+u.getUser()+" ���룺"+u.getPass());
				//��¼��֤
				Message m=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				if(u.getPass().equals("123456")){
					SerConnClientThread scThread=new SerConnClientThread(s);
					scThread.start();
					scThread.notifyOthers(u.getUser());
					//���̼߳��뵽��ϣ����
					ManageClientThread.addClientThread(u.getUser(),scThread);
					m.setMsType("1");
					m.setContent(ManageClientThread.getAllOnLineUserNo());
					oos.writeObject(m);
					//�߳�

				}else{
					m.setMsType("2");
					oos.writeObject(m);
					//�ر�����
					s.close();
				}

			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}

}
