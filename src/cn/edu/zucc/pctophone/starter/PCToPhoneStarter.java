package cn.edu.zucc.pctophone.starter;

import java.net.UnknownHostException;

import cn.edu.zucc.pctophone.view.FrmMain;
import cn.edu.zucc.pctophone.control.ConnectManager;
import cn.edu.zucc.pctophone.util.BaseException;

public class PCToPhoneStarter {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		try {
			new FrmMain();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
