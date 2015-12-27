package cn.edu.zucc.pctophone.model;

public class BeanFile {
	//文件名 varchar(50) 文件路径 varchar(255) 文件大小 Double 文件类型varchar(50)
	private int file_id;
	private String file_name;
	private String file_path;
	private Double file_size;
	private String file_type;
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public Double getFile_size() {
		return file_size;
	}
	public void setFile_size(Double file_size) {
		this.file_size = file_size;
	}
}
