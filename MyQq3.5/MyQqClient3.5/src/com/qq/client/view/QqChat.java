/**
 *和好友聊天界面 
 */

package com.qq.client.view;

import com.qq.client.tools.ClientConnServerThread;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class QqChat extends JFrame implements ActionListener{
	
	
	JTextArea txtArea;
	JPanel p;
	JTextField txt;
	JButton btn;
	
	String userNo,friendNo;
	/**
	 * 
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		QqChat qqChat=new QqChat("1");
	}*/

	public QqChat(String userNo,String friendNo)throws Exception{
		txtArea=new JTextArea();
		p=new JPanel();
		txt=new JTextField(20);
		btn=new JButton("发送");
		btn.addActionListener(this);
		p.add(txt);
		p.add(btn);
		//设置userNo,friendNo
		this.userNo=userNo;
		this.friendNo=friendNo;
		//创建框架，并对其进行基本设置
		this.setSize(350,200);
		this.setLocation(1100,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.setTitle(userNo+"和"+friendNo+"聊天中");
		this.setIconImage(new ImageIcon("image/qq.gif").getImage());
		
		//将组件添加进框架
		this.add(txtArea,"Center");
		this.add(p,"South");

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		txtArea.append(userNo+"说:\n"+txt.getText()+"\n");		
		Message m=new Message();
		m.setSender(userNo);
		m.setReceiver(friendNo);
		m.setContent(txt.getText());
		m.setMsType(MessageType.message_comm_mess);
		try {
			ClientConnServerThread cs=ManageClientConnServerThread.getClientThread(userNo);
			ObjectOutputStream oos=new ObjectOutputStream(ManageClientConnServerThread.getClientThread(userNo).getS().getOutputStream());
			oos.writeObject(m);
		}catch(IOException e1) {
			e1.printStackTrace();
		}
	}
	public void gettalk(Message m)throws Exception{
		System.out.println(this.userNo+"收到消息");
			txtArea.append(m.getSender()+"说：\n"+m.getContent()+"\n");
		
	}
}