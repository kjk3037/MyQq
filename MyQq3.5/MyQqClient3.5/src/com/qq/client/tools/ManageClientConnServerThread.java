package com.qq.client.tools;

import java.util.HashMap;



public class ManageClientConnServerThread {
	private static HashMap hm=new HashMap<String,ClientConnServerThread>();
	
	public static void addClientConnServerThread(String uid,ClientConnServerThread ccst){
		hm.put(uid,ccst);
	}
	public static ClientConnServerThread getClientThread(String uid){
		return (ClientConnServerThread)hm.get(uid);
	}
}
