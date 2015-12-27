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

import cn.edu.zucc.pctophone.control.AdminManager;
import cn.edu.zucc.pctophone.model.BeanAdmin;
import cn.edu.zucc.pctophone.util.BaseException;

public class FrmLogin extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private JButton btnLogin = new JButton("Login");
	private Button btnCancel = new Button("Exit");
	
	private JRootPane jrp = new JRootPane();
	
	
	private JLabel lblAdmin = new JLabel("”√ªß£∫");
	private JLabel lblPwd = new JLabel("√‹¬Î£∫");
	private static JTextField edtAdminName = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	public FrmLogin(Frame f, String s, boolean b){
		super(f,s,b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(lblAdmin);
		workPane.add(edtAdminName);
		workPane.add(lblPwd);
		workPane.add(edtPwd);
		jrp=getRootPane();
		jrp.setDefaultButton(btnLogin);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 140);
		// ∆¡ƒªæ”÷–œ‘ æ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == this.btnLogin) {
			String adminName = FrmLogin.edtAdminName.getText();
			String pwd = new String(this.edtPwd.getPassword());
			try {
				BeanAdmin admin = AdminManager.loadAdmin(adminName);
				if(pwd.equals(admin.getAdmin_password())){
					AdminManager.currentAdmin = admin;
					setVisible(false);
				}else{
					JOptionPane.showMessageDialog(null,  "√‹¬Î¥ÌŒÛ","¥ÌŒÛÃ· æ",JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage(), "¥ÌŒÛÃ· æ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}
}
