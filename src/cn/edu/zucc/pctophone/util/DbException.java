package cn.edu.zucc.pctophone.util;

public class DbException extends BaseException {
	public DbException(java.lang.Throwable ex){
		super("db error "+ex.getMessage());
	}
}
