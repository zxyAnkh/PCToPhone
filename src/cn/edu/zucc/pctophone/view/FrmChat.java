package cn.edu.zucc.pctophone.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JScrollBar;

import cn.edu.zucc.pctophone.control.AdminManager;
import cn.edu.zucc.pctophone.control.ChatManager;
import cn.edu.zucc.pctophone.control.ConnectManager;
import cn.edu.zucc.pctophone.control.ServiceThread;
import cn.edu.zucc.pctophone.model.BeanChat;
import cn.edu.zucc.pctophone.util.BaseException;

public class FrmChat extends JDialog {
	private static DefaultListModel<String> msgModel = new DefaultListModel<String>();
	private static JList listMsg = new JList(msgModel);
	private JTextField txt_msg;
	private static String user;
	private static String outMsg = "";

	List<BeanChat> bcList = null;

	public FrmChat(Frame f, String s, boolean b, String user, boolean bool) {
		super(f, s, b);

		this.user = user;
		JPanel WorkPane = new JPanel();
		getContentPane().add(WorkPane, BorderLayout.SOUTH);

		txt_msg = new JTextField();

		WorkPane.add(txt_msg);
		txt_msg.setColumns(50);

		JButton btn_send = new JButton("\u53D1\u9001");
		WorkPane.add(btn_send);
		if (bool == false)
			btn_send.setEnabled(false);

		getContentPane().add(listMsg, BorderLayout.CENTER);
		this.getContentPane().add(new JScrollPane(this.listMsg),
				BorderLayout.CENTER);

		// JScrollBar scrollBar = new JScrollBar();
		// scrollBar.add(listMsg);
		// getContentPane().add(scrollBar, BorderLayout.EAST);
		loadList();
		this.setSize(650, 400);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		btn_send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!txt_msg.getText().toString().equals("")) {
					setOutMsg(txt_msg.getText().toString());
					reloadList(outMsg, "atou");
				}
			}
		});
		// 防止每次进入重复读取
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				msgModel = new DefaultListModel<String>();
				listMsg = new JList(msgModel);
			}
		});
	}

	public void loadList() {
		BeanChat bc = new BeanChat();
		bc.setAdmin_name(AdminManager.currentAdmin.getAdmin_name());
		bc.setUser_name(user);
		try {
			bcList = ChatManager.loadChatHistory(bc);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < bcList.size(); i++) {
			if (bcList.get(i).getChat_wtw().equals("atou")) {
				msgModel.addElement("我 " + bcList.get(i).getChat_time() + "说：");
				msgModel.addElement(bcList.get(i).getChat_word());
			} else {
				msgModel.addElement("对方 " + bcList.get(i).getChat_time() + "说：");
				msgModel.addElement(bcList.get(i).getChat_word());
			}
		}
	}

	public static void reloadList(String msg, String bl) {
		BeanChat bc = new BeanChat();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String str = sdf.format(new java.sql.Date(date.getTime()));
		if (bl.equals("atou"))
			msgModel.addElement("我 " + str + "说：");
		else
			msgModel.addElement("对方 " + str + "说：");
		try {
			bc.setAdmin_name(AdminManager.currentAdmin.getAdmin_name());
			bc.setUser_name(user);
			bc.setChat_time(str);
			bc.setChat_word(msg);
			bc.setChat_wtw(bl);
			ChatManager.addChat(bc);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msgModel.addElement(msg);
		listMsg.validate();
		listMsg.repaint();
	}

	public static void reloadList(BeanChat bc) {
		if (bc.getChat_wtw().equals("atou"))
			msgModel.addElement("我 " + bc.getChat_time() + "说：");
		else
			msgModel.addElement("对方 " + bc.getChat_time() + "说：");
		msgModel.addElement(bc.getChat_word());
		listMsg.validate();
		listMsg.repaint();
	}

	// 消息内容
	public static String getOutMsg() {
		return outMsg;
	}

	public static void setOutMsg(String msg) {
		outMsg = msg;
	}

}
