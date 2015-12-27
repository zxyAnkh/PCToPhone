package cn.edu.zucc.pctophone.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.util.BusinessException;
import cn.edu.zucc.pctophone.util.DBUtil;
import cn.edu.zucc.pctophone.util.DbException;

public class UserManager {
	public static List<BeanUser> sBeanUser = new ArrayList<BeanUser>();

	public static List<BeanUser> loadAllUser() throws BaseException {
		Connection conn = null;
		List<BeanUser> mBeanUser = new ArrayList<BeanUser>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,user_password from BeanUser";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				BeanUser user = new BeanUser();
				user.setUser_name(rs.getString(1));
				user.setUser_password(rs.getString(2));
				mBeanUser.add(user);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return mBeanUser;
	}

	public static void addUser(BeanUser bu) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into BeanUser(user_name,user_password) values(?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, bu.getUser_name());
			pst.setString(2, bu.getUser_password());
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

	public static void deleteUser(String name) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from BeanUser where user_name = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, name);
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

	public static List<BeanUser> searchUser(String str) {
		Connection conn = null;
		List<BeanUser> mBeanUser = new ArrayList<BeanUser>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from BeanUser where user_name like ? or user_password like ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + str + "%");
			pst.setString(2, "%" + str + "%");
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanUser bu = new BeanUser();
				bu.setUser_name(rs.getString(1));
				bu.setUser_password(rs.getString(2));
				mBeanUser.add(bu);
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
		return mBeanUser;
	}
}
