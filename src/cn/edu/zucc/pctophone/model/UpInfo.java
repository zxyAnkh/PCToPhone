package cn.edu.zucc.pctophone.model;

public class UpInfo {
	private String file_name;
	private String file_type;
	private String up_time;
	private String up_name;
	private double up_size;
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
	public double getUp_size() {
		return up_size;
	}
	public void setUp_size(double up_size) {
		this.up_size = up_size;
	}
}
