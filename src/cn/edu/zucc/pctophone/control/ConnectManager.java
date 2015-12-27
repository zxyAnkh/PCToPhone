package cn.edu.zucc.pctophone.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectManager {
	public static ServerSocket mServerSocket;
	private final int BACKLOG = 10;
	private final int POOL_SIZE = 4;
	private ExecutorService mExecutorService;
	public static List<Socket> mSocketList = new ArrayList<Socket>();

	public ConnectManager() {
		try {
			mServerSocket = new ServerSocket(5648, BACKLOG,
					InetAddress.getLocalHost());
			mExecutorService = Executors.newFixedThreadPool(Runtime
					.getRuntime().availableProcessors() * POOL_SIZE);
			System.out.println("TCP服务器已开启");
			while (true) {
				Socket socket = null;
				socket = mServerSocket.accept();
				mExecutorService.execute(new ServiceThread(socket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
