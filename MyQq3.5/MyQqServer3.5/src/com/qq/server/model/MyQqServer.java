/**
 * 真正的QQ服务器：监听，等待某个QQ客户端的连接请求
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
			//在9999端口监听
			System.out.println("我是服务器，在1481监听。。。");
			ServerSocket ss=new ServerSocket(1481);
			//阻塞，等待连接
			while(true){
				Socket s=ss.accept();
				
				 //接收客户端发来的信息
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User u=(User)ois.readObject();
				System.out.println("服务器接收到用户"+u.getUser()+" 密码："+u.getPass());
				//登录验证
				Message m=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				if(u.getPass().equals("123456")){
					SerConnClientThread scThread=new SerConnClientThread(s);
					scThread.start();
					scThread.notifyOthers(u.getUser());
					//将线程加入到哈希集合
					ManageClientThread.addClientThread(u.getUser(),scThread);
					m.setMsType("1");
					m.setContent(ManageClientThread.getAllOnLineUserNo());
					oos.writeObject(m);
					//线程

				}else{
					m.setMsType("2");
					oos.writeObject(m);
					//关闭连接
					s.close();
				}

			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}

}
