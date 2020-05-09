/**
 * 我的好友列表(包括陌生人、黑名单)
 */
package com.qq.client.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.qq.client.model.QqClientConnServer;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.client.tools.ManageQqChat;
import com.qq.client.tools.ManageQqFriendList;
import com.qq.common.Message;


public class QqFriendList extends JFrame implements ActionListener,MouseListener{
	String userNo,friendNo;
	int count;
	CardLayout cl;
	JPanel pFriend,pStranger,pBlack;
	String userID;
	/**
	 * 第一张卡片：好友列表
	 */
	JLabel[] labs_p1;
	JPanel pFriend_p1,pFriend_p2;
	JButton pFriend_btnFriend,pFriend_btnStranger,pFriend_btnBlack;
	JScrollPane pFriend_jsp;
	/**
	 * 第二张卡片：陌生人
	 */
    JPanel pStranger_p1,pStranger_p2;
    JButton pStranger_btnFriend,pStranger_btnStranger,pStranger_btnBlack;
    JScrollPane pStranger_jsp;
	
	/**
	 * 第三张卡片：黑名单
	 */
    JPanel pBlack_p1,pBlack_p2;
    JButton pBlack_btnFriend,pBlack_btnStranger,pBlack_btnBlack;
    JScrollPane pBlack_jsp;
	
	/*public static void main(String[] args) {
		new QqFriendList(userID);

	}*/
    String qqNum="";
	public QqFriendList(String qqNum)
	{
		this.qqNum=qqNum;
		
		/**
		 * 处理第一张卡片(显示好友列表)
		 */
		pFriend=new JPanel(new BorderLayout());
		
		//北部：按钮（我的好友）
		pFriend_btnFriend=new JButton("我的好友");
		//中间：假定有50个好友
		pFriend_p1=new JPanel(new GridLayout(50,1,4,4));
		pFriend_jsp=new JScrollPane(pFriend_p1);
		//初始化50个好友
		labs_p1=new JLabel[50];
		this.count=labs_p1.length;
		for(int i=0;i<labs_p1.length;i++)
		{
			ImageIcon im=new ImageIcon("image/mm.jpg");
			labs_p1[i]=new JLabel(i+1+"",im,JLabel.LEFT);
			labs_p1[i].setEnabled(false);
			if(labs_p1[i].getText().equals(qqNum))
			{
				labs_p1[i].setEnabled(true);
			}

			//给每个好友添加鼠标监听器，鼠标移到上面高亮显示
			labs_p1[i].addMouseListener(this);
			pFriend_p1.add(labs_p1[i]);
		}
		
		
		//南部：两个按钮（陌生人，黑名单）
		pFriend_p2=new JPanel(new GridLayout(2,1));
		pFriend_btnStranger=new JButton("陌生人");
		pFriend_btnStranger.addActionListener(this);
		
		pFriend_btnBlack=new JButton("黑名单");
		pFriend_btnBlack.addActionListener(this);
		
		pFriend_p2.add(pFriend_btnStranger);
		pFriend_p2.add(pFriend_btnBlack);
		
		//将北部、中部、南部控件放入第一张卡片中
		pFriend.add(pFriend_btnFriend,"North");
		pFriend.add(pFriend_jsp,"Center");
		pFriend.add(pFriend_p2,"South");
		
		/**
		 * 处理第二张卡片（显示陌生人）
		 */
		pStranger=new JPanel(new BorderLayout());
		//北部：按钮（我的好友、陌生人）
		pStranger_p1=new JPanel(new GridLayout(2,1));
		pStranger_btnFriend=new JButton("我的好友");
		pStranger_btnFriend.addActionListener(this);
		
		pStranger_btnStranger=new JButton("陌生人");
		pStranger_p1.add(pStranger_btnFriend);
		pStranger_p1.add(pStranger_btnStranger);
		
		//中间：假定有20个陌生人
		pStranger_p2=new JPanel(new GridLayout(50,1,4,4));
		pStranger_jsp=new JScrollPane(pStranger_p2);
		//初始化20个陌生人
		JLabel[] pStranger_labs=new JLabel[50];
		for(int i=0;i<pStranger_labs.length;i++)
		{
			pStranger_labs[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			pStranger_p2.add(pStranger_labs[i]);
			pStranger_labs[i].setEnabled(false);

		}
		
		//南部：按钮（黑名单）
		pStranger_btnBlack=new JButton("黑名单");
		pStranger_btnBlack.addActionListener(this);
		
		//将北部、中部、南部控件放入第二张卡片中
		pStranger.add(pStranger_p1,"North");
		pStranger.add(pStranger_jsp,"Center");
		pStranger.add(pStranger_btnBlack,"South");
		
		
		/**
		 * 处理第三张卡片（显示黑名单）
		 */
		pBlack=new JPanel(new BorderLayout());
		//北部：按钮（我的好友、陌生人、黑名单）
		pBlack_p1=new JPanel(new GridLayout(3,1));
		pBlack_btnFriend=new JButton("我的好友");
		pBlack_btnFriend.addActionListener(this);
		
		pBlack_btnStranger=new JButton("陌生人");
		pBlack_btnStranger.addActionListener(this);
		
		pBlack_btnBlack=new JButton("黑名单");
		pBlack_p1.add(pBlack_btnFriend);
		pBlack_p1.add(pBlack_btnStranger);
		pBlack_p1.add(pBlack_btnBlack);
		
		//中间：假定有10个黑名单
		pBlack_p2=new JPanel(new GridLayout(20,1,4,4));
		pBlack_jsp=new JScrollPane(pBlack_p2);
		//初始化10个黑名单
		JLabel[] pBlack_labs=new JLabel[20];
		for(int i=0;i<pBlack_labs.length;i++)
		{
			pBlack_labs[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			pBlack_p2.add(pBlack_labs[i]);
			pBlack_labs[i].setEnabled(false);
		}
		
		//将北部、中部控件放入第三张卡片中
		pBlack.add(pBlack_p1,"North");
		pBlack.add(pBlack_p2,"Center");
	
		
		/**
		 * 创建框架，设置属性
		 */
		this.setTitle(userID);//lan---3.1
		this.setLocation(1100,300);
		this.setSize(180,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(pStranger);
		
        //初始化卡片
		cl=new CardLayout();
		//设置框架为卡片布局
		this.setLayout(cl);
		//将三张卡片添加到框架中
		this.add(pFriend,"1");
		this.add(pStranger,"2");
		this.add(pBlack,"3");
		
		this.userNo=qqNum;

		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//如果点击好友按钮,卡片布局里 显示第一张卡片（好友卡片），否则依次显示其他卡片。。。
		if(e.getSource()==pStranger_btnFriend || e.getSource()==pBlack_btnFriend)
		{
			cl.show(this.getContentPane(), "1");
		}else if(e.getSource()==pFriend_btnStranger || e.getSource()==pBlack_btnStranger)
		{
			cl.show(this.getContentPane(), "2");
		}else if(e.getSource()==pFriend_btnBlack || e.getSource()==pStranger_btnBlack)
		{
			cl.show(this.getContentPane(), "3");
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e){
		// TODO Auto-generated method stub
		//响应双击事件
		if(e.getClickCount()==2)
		{
			//得到好友的编号
			String friendNo=((JLabel)e.getSource()).getText();
			//System.out.println("您正想和"+friendNo+"聊天");
			QqChat qqchat;

				try {
					qqchat = new QqChat(userNo,friendNo);
					ManageQqChat.addQqChat(this.userNo+"yu"+friendNo, qqchat);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jlab=(JLabel)e.getSource();
		jlab.setForeground(Color.red);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jlab=(JLabel)e.getSource();
		jlab.setForeground(Color.black);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void HightLightOnLineFriend(int i){
		JLabel[] jlab=this.labs_p1;
		jlab[i-1].setEnabled(true);
	}
}
