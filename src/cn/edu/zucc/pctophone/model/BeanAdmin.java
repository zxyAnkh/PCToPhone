package cn.edu.zucc.pctophone.model;

public class BeanAdmin {
	//管理员名 varchar(10) 管理员密码 varchar(16)
	private String admin_name;
	private String admin_password;
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getAdmin_password() {
		return admin_password;
	}
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}
}
