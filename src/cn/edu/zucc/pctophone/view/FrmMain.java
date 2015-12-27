package cn.edu.zucc.pctophone.view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import cn.edu.zucc.pctophone.control.AdminManager;
import cn.edu.zucc.pctophone.control.ConnectManager;
import cn.edu.zucc.pctophone.control.DownManager;
import cn.edu.zucc.pctophone.control.FileControler;
import cn.edu.zucc.pctophone.control.FileManager;
import cn.edu.zucc.pctophone.control.UpManager;
import cn.edu.zucc.pctophone.control.UserManager;
import cn.edu.zucc.pctophone.model.BeanDown;
import cn.edu.zucc.pctophone.model.BeanFile;
import cn.edu.zucc.pctophone.model.BeanUp;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.util.BaseException;

import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmMain extends JFrame implements Runnable, ActionListener {
	private FrmLogin dlgLogin;
	private JButton btnFile = new JButton("文件管理");
	private JButton btnUser = new JButton("用户管理");
	private JButton btnUp = new JButton("上传文件");
	private JButton btnDown = new JButton("下载文件");
	private JButton btnDel = new JButton("删除文件");
	private JPanel statusBar = new JPanel();
	private JPanel operationBar = new JPanel();

	private static Object tblUserTitle[] = { "用户名", "状态" };
	private static Object tblUserData[][];
	static DefaultTableModel tabUserModel = new DefaultTableModel();
	private final static JTable tblUser = new JTable(tabUserModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};// 设置不可修改

	private static Object tblFileTitle[] = { "文件编号", "文件名", "文件类型", "文件路径",
			"文件大小(字节)" };
	private static Object tblFileData[][];
	static DefaultTableModel tabFileModel = new DefaultTableModel();
	private final static JTable tblFile = new JTable(tabFileModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	static List<BeanUser> mBeanUser = null;
	static List<BeanFile> mBeanFile = null;

	// 实时刷新所有用户状态
	public static void reloadUserTable() {

		try {
			mBeanUser = UserManager.loadAllUser();
			// System.out.println(mBeanUser.size());
			tblUserData = new Object[mBeanUser.size()][2];
			for (int i = 0; i < mBeanUser.size(); i++) {
				tblUserData[i][0] = mBeanUser.get(i).getUser_name();
				if (UserManager.sBeanUser.get(i).getIsOnline() == null)
					UserManager.sBeanUser.get(i).setIsOnline(false);
				boolean flag = UserManager.sBeanUser.get(i).getIsOnline();
				if (flag)
					tblUserData[i][1] = "在线";
				else
					tblUserData[i][1] = "离线";
			}

		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabUserModel.setDataVector(tblUserData, tblUserTitle);
		tblUser.validate();
		tblUser.repaint();
	}

	// 实时刷新所有文件状态
	public static void reloadFileTable() {
		try {
			mBeanFile = FileManager.loadAllFile();
			tblFileData = new Object[mBeanFile.size()][5];
			for (int i = 0; i < mBeanFile.size(); i++) {
				tblFileData[i][0] = mBeanFile.get(i).getFile_id();
				tblFileData[i][1] = mBeanFile.get(i).getFile_name();
				tblFileData[i][2] = mBeanFile.get(i).getFile_type();
				tblFileData[i][3] = mBeanFile.get(i).getFile_path();
				tblFileData[i][4] = mBeanFile.get(i).getFile_size();
			}

		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabFileModel.setDataVector(tblFileData, tblFileTitle);
		tblFile.validate();
		tblFile.repaint();
	}

	public FrmMain() throws BaseException, UnknownHostException {
		this.setTitle("文件助手");
		dlgLogin = new FrmLogin(this, "登录", true);
		dlgLogin.setVisible(true);
		UserManager.sBeanUser = UserManager.loadAllUser();
		new Thread(this).start();
		// 左下角提示信息
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblInfo = new JLabel("您好!"
				+ AdminManager.currentAdmin.getAdmin_name());
		statusBar.add(lblInfo);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		JLabel lblDns = new JLabel("域名地址:zxyankh.imwork.net");
		JLabel lblPort = new JLabel(" 端口号：5648");
		lblDns.setHorizontalAlignment(SwingConstants.RIGHT);
		statusBar.add(lblDns);
		statusBar.add(lblPort);
		// 上方操作按钮
		operationBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		operationBar.add(btnFile);
		operationBar.add(btnUser);
		operationBar.add(btnUp);
		operationBar.add(btnDown);
		operationBar.add(btnDel);
		this.getContentPane().add(operationBar, BorderLayout.NORTH);

		this.getContentPane().add(new JScrollPane(this.tblUser),
				BorderLayout.WEST);
		this.getContentPane().add(new JScrollPane(this.tblFile),
				BorderLayout.CENTER);

		btnFile.addActionListener(this);
		btnUser.addActionListener(this);
		btnDel.addActionListener(this);
		btnUp.addActionListener(this);
		btnDown.addActionListener(this);

		FrmMain.this.reloadUserTable();
		FrmMain.this.reloadFileTable();

		// tbluser鼠标双击事件
		this.tblUser.setRowSelectionAllowed(true);
		this.tblUser.setColumnSelectionAllowed(false);
		this.tblUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = FrmMain.this.tblUser.getSelectedRow();
					if (row < 0)
						return;
					String isLogin = FrmMain.this.tblUserData[row][1]
							.toString();
					if (isLogin.equals("在线")) {
						String username = FrmMain.this.tblUserData[row][0]
								.toString();
						FrmChat dlgChat = new FrmChat(FrmMain.this, "与"
								+ username + "聊天中", false, username, true);
						dlgChat.setVisible(true);
					} else if (isLogin.equals("离线")) {
						String username = FrmMain.this.tblUserData[row][0]
								.toString();
						FrmChat dlgChat = new FrmChat(FrmMain.this, "与"
								+ username + "聊天中", false, username, false);
						dlgChat.setVisible(true);
					}
				}
			}
		});

		this.setSize(1200, 700);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new ConnectManager();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == this.btnFile) {
			FrmFile dlgFile = new FrmFile(this, "文件管理", true);
			dlgFile.setVisible(true);
			FrmMain.this.reloadFileTable();
		} else if (arg0.getSource() == this.btnUser) {
			FrmUser dlgUser = new FrmUser(this, "用户管理", true);
			dlgUser.setVisible(true);
			FrmMain.this.reloadUserTable();
		} else if (arg0.getSource() == this.btnDel) {
			int row = FrmMain.this.tblFile.getSelectedRow();
			if (row < 0)
				return;
			int id = Integer.parseInt(FrmMain.this.tblFileData[row][0]
					.toString());
			String name = FrmMain.this.tblFileData[row][1].toString();
			String type = FrmMain.this.tblFileData[row][2].toString();
			String path = FrmMain.this.tblFileData[row][3].toString();
			FileManager.deleteFile(id);
			FileControler.deleteFile(new File(path, name + "." + type));
			FrmMain.this.reloadFileTable();
		} else if (arg0.getSource() == this.btnUp) {
			JFileChooser jfc = new JFileChooser();
			// jfc.setVisible(true);//不用设置也可见 2015-12-26 21:18
			// 参考自http://blog.csdn.net/liang5630/article/details/25651491
			jfc.setMultiSelectionEnabled(false); // 设置不可多选
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设置只能选择文件
			jfc.showDialog(new JLabel(), "上传");
			File file = jfc.getSelectedFile();
			if (file != null && file.isFile()) {
				// System.out.println("文件:" + file.getAbsolutePath());
				FileControler.copyfile(file,
						new File("E:\\FileDatabase", file.getName()), true);
				// 记录上传记录
				BeanFile bf = new BeanFile();
				String name = file.getName();
				String type = name.substring(name.lastIndexOf(".") + 1);
				name = name.substring(0, name.lastIndexOf("."));
				System.out.println(name + "   " + type);
				bf.setFile_name(name);
				bf.setFile_type(type);
				bf.setFile_path("E:\\FileDatabase");
				double size = file.length();
				bf.setFile_size(size);
				FileManager.addFile(bf);
				int id = FileManager.getFileId(name);
				System.out.println(id);
				BeanUp bu = new BeanUp();
				bu.setFile_id(id);
				bu.setUp_name(AdminManager.currentAdmin.getAdmin_name());
				bu.setUp_size(size);
				// 可以新写一个方法获得指定格式的时间，每次都写一遍太累
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				bu.setUp_time(sdf.format(System.currentTimeMillis()));
				UpManager.addUpInfo(bu);
				FrmMain.this.reloadFileTable();
			}
		} else if (arg0.getSource() == this.btnDown) {
			int row = FrmMain.this.tblFile.getSelectedRow();
			if (row < 0)
				return;
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(false);
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设置只能选择文件夹
			jfc.showDialog(new JLabel(), "下载");
			File file = jfc.getSelectedFile();
			if (file != null && file.isDirectory()) {
				// System.out.println("文件夹:" + file.getAbsolutePath());
				// 12-27 10:38 26日晚忘记文件类型，所以导致无法复制，27日早醒来解决
				String name = FrmMain.this.tblFileData[row][1].toString();
				String type = FrmMain.this.tblFileData[row][2].toString();
				double size = (double) FrmMain.this.tblFileData[row][4];
				int id = (int) FrmMain.this.tblFileData[row][0];
				FileControler.copyfile(new File("E:\\FileDatabase", name + "."
						+ type), new File(file.getAbsoluteFile(), name + "."
						+ type), true);
				// 添加下载记录
				BeanDown bd = new BeanDown();
				bd.setDown_name(AdminManager.currentAdmin.getAdmin_name());
				bd.setDown_size(size);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				bd.setDown_time(sdf.format(System.currentTimeMillis()));
				bd.setFile_id(id);
				DownManager.addDownInfo(bd);
			}
		}
	}
}
