package cn.edu.zucc.pctophone.model;

public class DownInfo {
	private String file_name;
	private String file_type;
	private String down_time;
	private String down_name;
	private double down_size;
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getDown_time() {
		return down_time;
	}
	public void setDown_time(String down_time) {
		this.down_time = down_time;
	}
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
}
