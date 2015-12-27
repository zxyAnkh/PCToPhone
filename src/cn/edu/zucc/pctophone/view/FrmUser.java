package cn.edu.zucc.pctophone.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pctophone.control.UpManager;
import cn.edu.zucc.pctophone.control.UserManager;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.util.BaseException;

public class FrmUser extends JDialog implements ActionListener {
	private JPanel statusBar = new JPanel();
	private JTextField edtSearch = new JTextField(16);
	private JButton btnAdd = new JButton("添加");
	private JButton btnDel = new JButton("删除");
	private Object tblTitle[] = { "用户名", "用户密码" };
	private Object tblData[][];
	DefaultTableModel tabModel = new DefaultTableModel();
	private final JTable tbl = new JTable(tabModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	List<BeanUser> userList = null;

	private void reloadTable() {
		try {
			userList = UserManager.loadAllUser();
			tblData = new Object[userList.size()][2];
			for (int i = 0; i < userList.size(); i++) {
				// 数据
				tblData[i][0] = userList.get(i).getUser_name();
				tblData[i][1] = userList.get(i).getUser_password();
			}
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tabModel.setDataVector(tblData, tblTitle);
		tbl.validate();
		tbl.repaint();
	}

	private void search(String arg0) {
		userList = UserManager.searchUser(arg0);
		tblData = new Object[userList.size()][2];
		for (int i = 0; i < userList.size(); i++) {
			// 数据
			tblData[i][0] = userList.get(i).getUser_name();
			tblData[i][1] = userList.get(i).getUser_password();
		}
		tabModel.setDataVector(tblData, tblTitle);
		tbl.validate();
		tbl.repaint();
	}

	public FrmUser(JFrame f, String s, boolean b) {
		// TODO Auto-generated constructor stub
		super(f, s, b);
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusBar.add(edtSearch);
		statusBar.add(btnAdd);
		statusBar.add(btnDel);
		btnDel.addActionListener(this);
		btnAdd.addActionListener(this);
		getContentPane().add(statusBar, BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(this.tbl),
				BorderLayout.CENTER);
		reloadTable();
		this.setSize(400, 320);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		// 搜索框动态查找，参考自booklib
		this.edtSearch.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void changedUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						String str = FrmUser.this.edtSearch.getText()
								.toString();
						FrmUser.this.search(str);
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						String str = FrmUser.this.edtSearch.getText()
								.toString();
						FrmUser.this.search(str);
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						String str = FrmUser.this.edtSearch.getText()
								.toString();
						FrmUser.this.search(str);
					}

				});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource() == this.btnAdd) {
			FrmAddUser dlgAdd = new FrmAddUser(this, "添加用户", true);
			dlgAdd.setVisible(true);
			FrmUser.this.reloadTable();
		} else if (arg0.getSource() == this.btnDel) {
			int row = FrmUser.this.tbl.getSelectedRow();
			if (row < 0)
				return;
			String name = FrmUser.this.tblData[row][0].toString();
			UserManager.deleteUser(name);
			FrmUser.this.reloadTable();
		}
	}

}
