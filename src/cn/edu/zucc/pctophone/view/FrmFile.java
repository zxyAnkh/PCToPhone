package cn.edu.zucc.pctophone.view;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pctophone.control.DownManager;
import cn.edu.zucc.pctophone.control.FileControler;
import cn.edu.zucc.pctophone.control.UpManager;
import cn.edu.zucc.pctophone.model.DownInfo;
import cn.edu.zucc.pctophone.model.UpInfo;
import cn.edu.zucc.pctophone.util.BaseException;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;

public class FrmFile extends JDialog {
	private JPanel statusBar = new JPanel();
	private JComboBox cmbUD = new JComboBox();
	private JTextField edtSearch = new JTextField(50);

	private Object tblUpTitle[] = { "文件名", "文件类型", "上传进度(字节)", "上传时间", "上传者" };
	private Object tblData[][];
	DefaultTableModel tabModel = new DefaultTableModel();
	private final JTable tbl = new JTable(tabModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private Object tblDownTitle[] = { "文件名", "文件类型", "下载进度(字节)", "下载时间", "下载者" };

	List<UpInfo> mUI = null;
	List<DownInfo> mDI = null;

	// 实时显示上传文件列表
	private void reloadTable(String str) {
		if (str.equals("up")) {
			try {
				mUI = UpManager.loadAllUp();
				tblData = new Object[mUI.size()][5];
				for (int i = 0; i < mUI.size(); i++) {
					// 数据
					tblData[i][0] = mUI.get(i).getFile_name();
					tblData[i][1] = mUI.get(i).getFile_type();
					tblData[i][2] = mUI.get(i).getUp_size();
					tblData[i][3] = mUI.get(i).getUp_time();
					tblData[i][4] = mUI.get(i).getUp_name();
				}
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tabModel.setDataVector(tblData, tblUpTitle);
			tbl.validate();
			tbl.repaint();
		} else if (str.equals("down")) {
			try {
				mDI = DownManager.loadAllDown();
				tblData = new Object[mDI.size()][5];
				for (int i = 0; i < mDI.size(); i++) {
					// 数据
					tblData[i][0] = mDI.get(i).getFile_name();
					tblData[i][1] = mDI.get(i).getFile_type();
					tblData[i][2] = mDI.get(i).getDown_size();
					tblData[i][3] = mDI.get(i).getDown_time();
					tblData[i][4] = mDI.get(i).getDown_name();
				}
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tabModel.setDataVector(tblData, tblDownTitle);
			tbl.validate();
			tbl.repaint();
		}
	}

	private void search(String arg0, String arg1) {
		if (arg0.equals("down")) {
			mDI = DownManager.search(arg1);
			tblData = new Object[mDI.size()][5];
			for (int i = 0; i < mDI.size(); i++) {
				// 数据
				tblData[i][0] = mDI.get(i).getFile_name();
				tblData[i][1] = mDI.get(i).getFile_type();
				tblData[i][2] = mDI.get(i).getDown_size();
				tblData[i][3] = mDI.get(i).getDown_time();
				tblData[i][4] = mDI.get(i).getDown_name();
			}
			tabModel.setDataVector(tblData, tblDownTitle);
			tbl.validate();
			tbl.repaint();
		} else if (arg0.equals("up")) {
			mUI = UpManager.search(arg1);
			tblData = new Object[mUI.size()][5];
			for (int i = 0; i < mUI.size(); i++) {
				// 数据
				tblData[i][0] = mUI.get(i).getFile_name();
				tblData[i][1] = mUI.get(i).getFile_type();
				tblData[i][2] = mUI.get(i).getUp_size();
				tblData[i][3] = mUI.get(i).getUp_time();
				tblData[i][4] = mUI.get(i).getUp_name();
			}
			tabModel.setDataVector(tblData, tblUpTitle);
			tbl.validate();
			tbl.repaint();
		}
	}

	public FrmFile(Frame f, String s, boolean b) {
		super(f, s, b);
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		statusBar.add(cmbUD);
		statusBar.add(edtSearch);
		cmbUD.addItem("上传");
		cmbUD.addItem("下载");
		this.getContentPane().add(new JScrollPane(this.tbl),
				BorderLayout.CENTER);
		FrmFile.this.reloadTable("up");
		cmbUD.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (cmbUD.getSelectedItem().toString().equals("上传")) {
					FrmFile.this.reloadTable("up");
				} else {
					FrmFile.this.reloadTable("down");
				}
			}
		});

		getContentPane().add(statusBar, BorderLayout.NORTH);
		this.setSize(780, 600);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.edtSearch.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void changedUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						String str = FrmFile.this.edtSearch.getText()
								.toString();
						if (cmbUD.getSelectedItem().toString().equals("上传"))
							FrmFile.this.search("up", str);
						else
							FrmFile.this.search("down", str);
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						String str = FrmFile.this.edtSearch.getText()
								.toString();
						if (cmbUD.getSelectedItem().toString().equals("上传"))
							FrmFile.this.search("up", str);
						else
							FrmFile.this.search("down", str);
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						// TODO Auto-generated method stub
						String str = FrmFile.this.edtSearch.getText()
								.toString();
						if (cmbUD.getSelectedItem().toString().equals("上传"))
							FrmFile.this.search("up", str);
						else
							FrmFile.this.search("down", str);
					}

				});
	}

}
