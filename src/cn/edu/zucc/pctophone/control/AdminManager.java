package cn.edu.zucc.pctophone.control;

import java.sql.Connection;
import java.sql.SQLException;

import cn.edu.zucc.pctophone.model.BeanAdmin;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.util.BusinessException;
import cn.edu.zucc.pctophone.util.DBUtil;
import cn.edu.zucc.pctophone.util.DbException;

public class AdminManager {
	public static BeanAdmin currentAdmin = null;
	public static BeanAdmin loadAdmin(String adminName) throws BaseException{
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select admin_name,admin_password from BeanAdmin where admin_name = '"+adminName+"'";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if(!rs.next())
				throw new BusinessException("µÇÂ¼ÕËºÅ²»´æÔÚ");
			BeanAdmin admin = new BeanAdmin();
			admin.setAdmin_name(adminName);
			admin.setAdmin_password(rs.getString(1));
			rs.close();
			st.close();
			return admin;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DbException(e);
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
