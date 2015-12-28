package cn.edu.zucc.pctophone.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import cn.edu.zucc.pctophone.control.UserManager;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.util.BaseException;

public class FrmAddUser extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnAdd = new JButton("���");
	private Button btnCancel = new Button("ȡ��");

	private JRootPane jrp = new JRootPane();

	private JLabel lblName = new JLabel("�û�����");
	private JLabel lblPwd = new JLabel("���룺");
	private static JTextField edtName = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	public FrmAddUser(FrmUser f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnAdd);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(lblName);
		workPane.add(edtName);
		workPane.add(lblPwd);
		workPane.add(edtPwd);
		jrp = getRootPane();
		jrp.setDefaultButton(btnAdd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 140);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnAdd.addActionListener(this);
		btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnAdd) {
			String name = FrmAddUser.edtName.getText().toString();
			String pwd = new String(this.edtPwd.getPassword());
			BeanUser bu = new BeanUser();
			bu.setUser_name(name);
			bu.setUser_password(pwd);
			UserManager.addUser(bu);
			try {
				UserManager.sBeanUser = UserManager.loadAllUser();
				for (int i = 0; i < UserManager.sBeanUser.size(); i++)
					if (UserManager.sBeanUser.get(i).getUser_name()
							.equals(bu.getUser_name()))
						UserManager.sBeanUser.get(i).setIsOnline(false);
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.setVisible(false);
		} else if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
		}
	}
}
