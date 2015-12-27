package cn.edu.zucc.pctophone.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanChat {
	private String admin_name;
	private String user_name;
	private String chat_wtw;
	private String chat_word;
	private String chat_time;
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getChat_wtw() {
		return chat_wtw;
	}
	public void setChat_wtw(String chat_wtw) {
		this.chat_wtw = chat_wtw;
	}
	public String getChat_word() {
		return chat_word;
	}
	public void setChat_word(String chat_word) {
		this.chat_word = chat_word;
	}
	public String getChat_time() {
		return chat_time;
	}
	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}
}
