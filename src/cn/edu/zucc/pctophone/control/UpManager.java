package cn.edu.zucc.pctophone.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.pctophone.model.BeanUp;
import cn.edu.zucc.pctophone.model.DownInfo;
import cn.edu.zucc.pctophone.model.UpInfo;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.util.DBUtil;

public class UpManager {
	public static List<UpInfo> loadAllUp() throws BaseException {
		List<UpInfo> buList = new ArrayList<UpInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from upinfo";// id name type time name size
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				UpInfo upi = new UpInfo();
				upi.setFile_name(rs.getString(2));
				upi.setFile_type(rs.getString(3));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//由于rs.getdate() 忽略掉HH:mm:ss 所以使用rs.getTimestamp
				upi.setUp_time(sdf.format(rs.getTimestamp(4)));
				upi.setUp_name(rs.getString(5));
				upi.setUp_size(rs.getDouble(6));
				buList.add(upi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buList;
	}

	public static List<UpInfo> search(String str) {
		Connection conn = null;
		List<UpInfo> mdi = new ArrayList<UpInfo>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from upinfo where (file_name like ? or up_name like ? or file_type like ?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + str + "%");
			pst.setString(2, "%" + str + "%");
			pst.setString(3, "%" + str + "%");
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				UpInfo upi = new UpInfo();
				upi.setFile_name(rs.getString(2));
				upi.setFile_type(rs.getString(3));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//由于rs.getdate() 忽略掉HH:mm:ss 所以使用rs.getTimestamp
				upi.setUp_time(sdf.format(rs.getTimestamp(4)));
				upi.setUp_name(rs.getString(5));
				upi.setUp_size(rs.getDouble(6));
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
	public static void addUpInfo(BeanUp bu){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into beanup(file_id,up_time,up_name,up_size) values(?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, bu.getFile_id());
			pst.setString(2, bu.getUp_time());
			pst.setString(3, bu.getUp_name());
			pst.setDouble(4, bu.getUp_size());
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
