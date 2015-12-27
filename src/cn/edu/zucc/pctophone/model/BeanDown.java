package cn.edu.zucc.pctophone.model;

import java.util.Date;

public class BeanDown {
	//下载编号int 下载时间 datetime
	private int down_id;
	private int file_id;
	private String down_time;
	private String down_name;
	private double down_size;
	public String getDown_name() {
		return down_name;
	}
	public void setDown_name(String down_name) {
		this.down_name = down_name;
	}
	public double getDown_size() {
		return down_size;
	}
	public void setDown_size(double down_size) {
		this.down_size = down_size;
	}
	public int getDown_id() {
		return down_id;
	}
	public void setDown_id(int down_id) {
		this.down_id = down_id;
	}
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
	public String getDown_time() {
		return down_time;
	}
	public void setDown_time(String down_time) {
		this.down_time = down_time;
	}

}
