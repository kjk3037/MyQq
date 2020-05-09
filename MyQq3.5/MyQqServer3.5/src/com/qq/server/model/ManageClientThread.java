package com.qq.server.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
	static HashMap hm=new HashMap<String,SerConnClientThread>();
	
	public static void addClientThread(String uid, SerConnClientThread ct){
		hm.put(uid,ct);
	}
	public static SerConnClientThread getClientThread(String uid){
		return (SerConnClientThread)hm.get(uid);
	}
	public static String getAllOnLineUserNo()
	{
		//使用迭代器遍历已经登录的QQ用户
		Iterator it=hm.keySet().iterator();
		String response="";
		while(it.hasNext()){
			response=response+it.next().toString();
		}
		return response;
	}

}
