package com.qq.common;

import java.io.*;
public class Message implements Serializable{
	private String msType;
	private String sender;
	private String receiver;
	private String content;
	private String sendTime;

	public String getMsType() {
		return msType;
	}

	public void setMsType(String msType) {
		this.msType = msType;
	}
	public void setSender(String sender) {
		this.sender=sender;
	}
	public String getSender() {
		return sender;
	}
	public void setReceiver(String receiver) {
		this.receiver=receiver;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setContent(String content) {
		this.content=content;
	}
	public String getContent() {
		return content;
	}
	public void setSendTime(String sendTime) {
		this.sendTime=sendTime;
	}
	public String getSendTime() {
		return sendTime;
	}
	
	
}
