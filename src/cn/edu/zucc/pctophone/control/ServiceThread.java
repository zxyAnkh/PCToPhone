package cn.edu.zucc.pctophone.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import cn.edu.zucc.pctophone.model.BeanChat;
import cn.edu.zucc.pctophone.model.BeanDown;
import cn.edu.zucc.pctophone.model.BeanFile;
import cn.edu.zucc.pctophone.model.BeanUser;
import cn.edu.zucc.pctophone.model.FileLoadInfo;
import cn.edu.zucc.pctophone.util.BaseException;
import cn.edu.zucc.pctophone.view.FrmChat;
import cn.edu.zucc.pctophone.view.FrmMain;

public class ServiceThread implements Runnable {

	// ���嵱ǰ�̴߳����Socket
	private static Socket mSocket;
	// ���߳��������Socket����Ӧ��������
	private Handler revHandler;
	private BufferedReader br;
	private static PrintWriter pw;

	public ServiceThread(Socket s) {
		this.mSocket = s;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut, "UTF-8"), true);
	}

	// ���ڻ����룬����ͳһ���ñ����ʽΪUTF-8
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn, "UTF-8"));
	}

	private DataOutputStream getDOS(Socket socket) throws IOException {
		return new DataOutputStream(socket.getOutputStream());
	}

	private FileInputStream getFIS(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}

	@Override
	public void run() {
		try {
			System.out.println("new user accpted" + mSocket.getInetAddress()
					+ ":" + mSocket.getPort());
			pw = getWriter(mSocket);
			br = getReader(mSocket);
			String msg = null;
			while ((msg = br.readLine()) != null) {
				// �����¼��Ϣ
				if (msg.substring(0, 3).equals("log")) {
					boolean flg = false;
					msg = msg.substring(3);
					for (int i = 0; i < UserManager.sBeanUser.size(); i++) {
						String user = UserManager.sBeanUser.get(i)
								.getUser_name()
								+ UserManager.sBeanUser.get(i)
										.getUser_password();
						// System.out.println(user);
						if (msg.equals(user)) {
							flg = true;
							// ��ʾ���û����߲����ص�¼�ɹ���Ϣ
							UserManager.sBeanUser.get(i).setIsOnline(true);
							// System.out.println(mSocket.isClosed());
							// 2015-12-24 21:17
							// ��Ҫ�ͻ��˵ڶ������Ӳ�����ʾ�ѵ�¼�����Ӧ���ǿͻ��˵�����
							pw.println("Login");
							pw.flush();
							System.out.println(msg.equals(user));
							FrmMain.reloadUserTable();
							break;
						}
					}
					if (flg == false) {
						pw.println("�û������������");
						pw.flush();
					}
					break;
				} else if (msg.substring(0, 3).equals("qui")) {// ����ע����¼��Ϣ
					msg = msg.substring(3);
					for (int i = 0; i < UserManager.sBeanUser.size(); i++) {
						String user = UserManager.sBeanUser.get(i)
								.getUser_name()
								+ UserManager.sBeanUser.get(i)
										.getUser_password();
						// System.out.println(user);
						if (msg.equals(user)) {
							UserManager.sBeanUser.get(i).setIsOnline(false);
							// System.out.println(mSocket.isClosed());
							// System.out.println(msg.equals(user));
							FrmMain.reloadUserTable();
							break;
						}
					}
					break;
				} else if (msg.substring(0, 3).equals("cha")) {// ����������Ϣ
					msg = msg.substring(3);
					System.out.println("�յ���Ϣ��" + msg);
					// 2015-12-24 21:40
					// mSocket δ�ر�
					// ��ʱֻ��ʵ�ֱ�������
					String outMsg = null;
					while (!(outMsg = FrmChat.getOutMsg()).equals("")) {
						pw.println(outMsg);
						pw.flush();
						sendRecord(outMsg);
						FrmChat.setOutMsg("");
					}
					revRecord(msg);
					System.out.println("mSocket�Ƿ�رգ�" + mSocket.isClosed());
				} else if (msg.substring(0, 3).equals("loa")) {// ��ȡ�ļ�����
					ArrayList<FileLoadInfo> array = fileLoader();
					for (int i = 0; i < array.size(); i++) {
						pw.println(array.get(i).getFile_name() + "."
								+ array.get(i).getFile_type() + "."
								+ array.get(i).getFile_size());
						// test.txt.2 test.txt�ļ� 2�ֽڴ�С
						pw.flush();
						// System.out.println(array.get(i));
					}
				} else if (msg.substring(0, 3).equals("dow")) {
					String strfile = msg.substring(3);
					File file = getFile(strfile);
					if (file != null) {
						DataOutputStream dos = getDOS(mSocket);
						FileInputStream fis = getFIS(file);
						byte[] bt = new byte[1024];
						int length;
						double sumL = 0;// ���͵��ܳ���
						while ((length = fis.read(bt, 0, bt.length)) > 0) {
							sumL += length;
							dos.write(bt, 0, length);
							dos.flush();
						}
						// ��¼������Ϣ file_id,down_time,down_name,down_size
						downloadRecord(strfile);
					}
				}
			}
			pw.close();
			br.close();
			mSocket.close();
			// System.out.println(mSocket.isClosed());
		} catch (IOException | BaseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (mSocket != null) {
					pw.close();
					br.close();
					mSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void downloadRecord(String strfile) {
		String name = strfile.substring(0, strfile.indexOf("."));
		String type = strfile.substring(strfile.indexOf(".") + 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(System.currentTimeMillis());
		int id = FileManager.getFileId(name);
		BeanDown bd = new BeanDown();
		bd.setDown_time(time);
		bd.setDown_name("zxy");
		bd.setDown_size(0);
		bd.setFile_id(id);
		DownManager.addDownInfo(bd);
	}

	private void revRecord(String msg) {
		BeanChat bc = new BeanChat();
		bc.setChat_word(msg);
		bc.setAdmin_name(AdminManager.currentAdmin.getAdmin_name());
		bc.setUser_name("zxy");
		// ��ʱ������һ���û����û�����������ͻ��˷��͵�TCP���ݰ������userName��Ϣ
		// �����û������ʮ���ַ� ���ݰ�����Ϊ chaxxxxxxxxxxmessage ����xΪ�û���������ʮ���ַ��м���Ϊ' '
		// ��Ȼò���е��˷�
		bc.setChat_wtw("utoa");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bc.setChat_time(sdf.format(System.currentTimeMillis()));
		try {
			ChatManager.addChat(bc);
			FrmChat.reloadList(bc);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendRecord(String msg) {
		BeanChat bc = new BeanChat();
		bc.setChat_word(msg);
		bc.setAdmin_name(AdminManager.currentAdmin.getAdmin_name());
		bc.setUser_name("zxy");
		// ��ʱ������һ���û����û�����������ͻ��˷��͵�TCP���ݰ������userName��Ϣ
		bc.setChat_wtw("atou");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		bc.setChat_time(sdf.format(System.currentTimeMillis()));
		try {
			ChatManager.addChat(bc);
			FrmChat.reloadList(bc);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<FileLoadInfo> fileLoader() throws BaseException {
		List<BeanFile> bf = FileManager.loadAllFile();
		ArrayList<FileLoadInfo> arrayList = new ArrayList<FileLoadInfo>();
		for (int i = 0; i < bf.size(); i++) {
			FileLoadInfo fli = new FileLoadInfo();
			fli.setFile_name(bf.get(i).getFile_name());
			fli.setFile_type(bf.get(i).getFile_type());
			fli.setFile_size(bf.get(i).getFile_size().intValue());
			arrayList.add(fli);
			// System.out.println(file);
		}
		return arrayList;
	}

	private File getFile(String strfile) throws BaseException {
		List<BeanFile> bf = FileManager.loadAllFile();
		File file = null;
		for (int i = 0; i < bf.size(); i++) {
			String str = bf.get(i).getFile_name() + "."
					+ bf.get(i).getFile_type();
			if (strfile.equals(str))
				file = new File(bf.get(i).getFile_path(), bf.get(i)
						.getFile_name() + "." + bf.get(i).getFile_type());
		}
		return file;
	}
}
