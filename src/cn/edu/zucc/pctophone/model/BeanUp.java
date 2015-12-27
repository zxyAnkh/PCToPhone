package cn.edu.zucc.pctophone.model;

import java.util.Date;

public class BeanUp {
	//上传编号int 上传时间 datetime 上传进度up_size
	private int up_id;
	private int file_id;
	private String up_time;
	private String up_name;
	private double up_size;
	public double getUp_size() {
		return up_size;
	}
	public void setUp_size(double up_size) {
		this.up_size = up_size;
	}
	public int getUp_id() {
		return up_id;
	}
	public void setUp_id(int up_id) {
		this.up_id = up_id;
	}
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
	public String getUp_time() {
		return up_time;
	}
	public void setUp_time(String up_time) {
		this.up_time = up_time;
	}
	public String getUp_name() {
		return up_name;
	}
	public void setUp_name(String up_name) {
		this.up_name = up_name;
	}
	
}
