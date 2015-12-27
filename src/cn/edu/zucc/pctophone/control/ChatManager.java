package cn.edu.zucc.pctophone.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.pctophone.model.BeanChat;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.util.DBUtil;
import cn.edu.zucc.pctophone.util.DbException;

public class ChatManager {
	public static List<BeanChat> loadChatHistory(BeanChat bc) throws BaseException {
		List<BeanChat> beanChat = new ArrayList<BeanChat>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select chat_time,chat_word,chat_wtw from beanchat where user_name=? and admin_name=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, bc.getUser_name());
			pst.setString(2, bc.getAdmin_name());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanChat bchat = new BeanChat();
				bchat.setAdmin_name(bc.getAdmin_name());
				bchat.setUser_name(bc.getUser_name());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//由于rs.getdate() 忽略掉HH:mm:ss 所以使用rs.getTimestamp
				bchat.setChat_time(sdf.format(rs.getTimestamp(1)));
				bchat.setChat_word(rs.getString(2));
				bchat.setChat_wtw(rs.getString(3));
				beanChat.add(bchat);
			}
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
		return beanChat;
	}

	public static void addChat(BeanChat bc) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into beanchat(user_name,admin_name,chat_time,chat_word,chat_wtw) values(?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, bc.getUser_name());
			pst.setString(2, bc.getAdmin_name());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				pst.setTimestamp(3, new java.sql.Timestamp(sdf.parse(bc.getChat_time()).getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pst.setString(4, bc.getChat_word());
			pst.setString(5, bc.getChat_wtw());
			pst.execute();
			pst.close();
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
	}
}
