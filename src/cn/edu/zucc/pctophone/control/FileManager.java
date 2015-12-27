package cn.edu.zucc.pctophone.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.pctophone.model.BeanFile;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.util.DBUtil;

public class FileManager {
	public static List<BeanFile> loadAllFile() throws BaseException{
		Connection conn = null;
		List<BeanFile> mBeanFile = new ArrayList<BeanFile>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select file_id,file_type,file_name,file_path,file_size from beanfile";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				BeanFile bf = new BeanFile();
				bf.setFile_id(rs.getInt(1));
				bf.setFile_type(rs.getString(2));
				bf.setFile_name(rs.getString(3));
				bf.setFile_path(rs.getString(4));
				bf.setFile_size(rs.getDouble(5));
				mBeanFile.add(bf);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return mBeanFile;
	}

	public static void addFile(BeanFile bf) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into BeanFile(file_name,file_path,file_size,file_type) values(?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, bf.getFile_name());
			pst.setString(2, bf.getFile_path());
			pst.setDouble(3, bf.getFile_size());
			pst.setString(4, bf.getFile_type());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public static void deleteFile(int id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from BeanFile where file_id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public static int getFileId(String name){
		Connection conn = null;
		int id = 0;
		try {
			conn = DBUtil.getConnection();
			String sql = "select file_id from beanfile where file_name = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}
