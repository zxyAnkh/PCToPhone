package cn.edu.zucc.pctophone.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.pctophone.model.BeanDown;
import cn.edu.zucc.pctophone.model.BeanUp;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.model.DownInfo;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.util.DBUtil;

public class DownManager {
	public static List<DownInfo> loadAllDown() throws BaseException {
		List<DownInfo> buList = new ArrayList<DownInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from downinfo";// id name type time name size
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				DownInfo upi = new DownInfo();
				upi.setFile_name(rs.getString(2));
				upi.setFile_type(rs.getString(3));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//由于rs.getdate() 忽略掉HH:mm:ss 所以使用rs.getTimestamp
				upi.setDown_time(sdf.format(rs.getTimestamp(4)));
				upi.setDown_name(rs.getString(5));
				upi.setDown_size(rs.getDouble(6));
				buList.add(upi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buList;
	}
	public static List<DownInfo> search(String str){
		Connection conn = null;
		List<DownInfo> mdi = new ArrayList<DownInfo>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from downinfo where file_name like ? or down_name like ? or file_type like ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + str + "%");
			pst.setString(2, "%" + str + "%");
			pst.setString(3, "%" + str + "%");
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				DownInfo upi = new DownInfo();
				upi.setFile_name(rs.getString(2));
				upi.setFile_type(rs.getString(3));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//由于rs.getdate() 忽略掉HH:mm:ss 所以使用rs.getTimestamp
				upi.setDown_time(sdf.format(rs.getTimestamp(4)));
				upi.setDown_name(rs.getString(5));
				upi.setDown_size(rs.getDouble(6));
				mdi.add(upi);
			}
			pst.close();
			rs.close();
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
		return mdi;
	}
	public static void addDownInfo(BeanDown bd){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into beandown(file_id,down_time,down_name,down_size) values(?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, bd.getFile_id());
			pst.setString(2, bd.getDown_time());
			pst.setString(3, bd.getDown_name());
			pst.setDouble(4, bd.getDown_size());
			pst.execute();
			pst.close();
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
	}
}
