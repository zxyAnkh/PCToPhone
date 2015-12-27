package cn.edu.zucc.pctophone.model;

public class BeanUser {
	//用户名 varchar(10) 用户密码 varchar(16)
	private String user_name;
	private String user_password;
	private Boolean isOnline = false;
	public Boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
}
