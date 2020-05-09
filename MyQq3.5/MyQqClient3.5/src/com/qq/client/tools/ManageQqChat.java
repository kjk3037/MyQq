package com.qq.client.tools;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

import com.qq.client.view.QqChat;

public class ManageQqChat{
	private static HashMap hm=new HashMap<String,QqChat>();
	public static void addQqChat(String s,QqChat qc){
		hm.put(s, qc);
	}
	public static QqChat getQqChat(String s){
		return (QqChat)hm.get(s);
	}
}

