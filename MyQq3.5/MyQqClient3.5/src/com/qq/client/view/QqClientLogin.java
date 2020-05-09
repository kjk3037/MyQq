/**
 * qq客户端登录界面
 */
package com.qq.client.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

import com.qq.client.model.QqClientUser;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class QqClientLogin extends JFrame implements ActionListener{

	//定义北部需要的组件
	JLabel jbl1;
	
	//定义中部需要的组件
	//中部有三个JPanel,选项卡窗口管理
	JTabbedPane jtp;
	JPanel p1,p2,p3;
	JLabel p1_lab1,p1_lab2,p1_lab3,p1_lab4;
	JButton p1_btn1;
	JTextField p1_txtQQNum;
	JPasswordField p1_txtQQPass;
	JCheckBox p1_ch1,p1_ch2;
	
	
	//定义南部需要的组件
	JPanel p;
	JButton p_jbtn1,p_jbtn2,p_jbtn3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QqClientLogin qqClientLogin=new QqClientLogin();
	}
	
	public QqClientLogin()
	{
		/**
		 *  处理北部
		 */
		
		jbl1=new JLabel(new ImageIcon("image/tou.gif"));
		
		/**
		 * 处理中部
		 */
		//P1面板
		p1=new JPanel(new GridLayout(3,3));
		p1_lab1=new JLabel("QQ号码",JLabel.CENTER);
		p1_lab2=new JLabel("QQ密码",JLabel.CENTER);
		p1_lab3=new JLabel("忘记密码",JLabel.CENTER);
		p1_lab3.setForeground(Color.blue);
		p1_lab4=new JLabel("申请密码保护",JLabel.CENTER);
		p1_btn1=new JButton(new ImageIcon("image/clear.gif"));
		p1_txtQQNum=new JTextField();
		p1_txtQQPass=new JPasswordField();
		p1_ch1=new JCheckBox("隐身登录");
		p1_ch2=new JCheckBox("记住密码");
		//将控件加入到P1面板
		p1.add(p1_lab1);
		p1.add(p1_txtQQNum);
		p1.add(p1_btn1);
		p1.add(p1_lab2);
		p1.add(p1_txtQQPass);
		p1.add(p1_lab3);
		p1.add(p1_ch1);
		p1.add(p1_ch2);
		p1.add(p1_lab4);
		//p2面板（省略细节）
		p2=new JPanel();
		//p3面板（省略细节）
		p3=new JPanel();
		
		//创建选项卡窗口
		jtp=new JTabbedPane();
		jtp.add("QQ号码",p1);
		jtp.add("手机号码",p2);
		jtp.add("电子邮件",p3);
		
		/**
		 * 处理南部
		 */
		p=new JPanel();
		p_jbtn1=new JButton(new ImageIcon("image/denglu.gif"));
		//给登录按钮添加事件监听器
		p_jbtn1.addActionListener(this);
		p_jbtn2=new JButton(new ImageIcon("image/quxiao.gif"));
		p_jbtn3=new JButton(new ImageIcon("image/xiangdao.gif"));
		p.add(p_jbtn1);
		p.add(p_jbtn2);
		p.add(p_jbtn3);
		
		
		/**
		 * 将北部、中部、南部控件加入到框架中
		 */
		this.add(jbl1,"North");
		this.add(jtp,"Center");
		this.add(p,"South");
		/**
		 * 对框架进行设置
		 */
		this.setLocation(800,350);
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//如果用户点击了登录
		if(e.getSource()==p_jbtn1){
			QqClientUser qqClientUser=new QqClientUser();
			User u=new User();
			String qqNum=p1_txtQQNum.getText().trim();
			String qqPass=p1_txtQQPass.getText().trim();
			u.setUser(qqNum);
			u.setPass(qqPass);
			if(qqClientUser.checkUser(u)){
				//new QqFriendList(u.getUser());
				//同时关闭登录界面
				this.dispose();
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnServerThread.getClientThread(u.getUser()).getS().getOutputStream());
					Message m=new Message();
					m.setMsType(MessageType.message_request_onLineFriend);
					m.setSender(u.getUser());
					oos.writeObject(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(this, "用户名密码错误");
			}
			
		}
	}
	
}
