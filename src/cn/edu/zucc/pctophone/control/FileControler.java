package cn.edu.zucc.pctophone.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//参考http://blog.csdn.net/jdsjlzx/article/details/44900511

public class FileControler {
	// 拷贝文件
	public static void copyfile(File fromFile, File toFile, Boolean rewrite) {

		if (!fromFile.exists()) {
			return;
		}// 如果文件不存在
		if (!fromFile.isFile()) {
			return;
		}// 如果选择的不是文件
		if (!fromFile.canRead()) {
			return;
		}// 如果文件不可读
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}// 如果文件路径不存在则新建文件
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}// 如果文件需要重写则将原文件删除

		try {
			FileInputStream fis = new FileInputStream(fromFile);
			FileOutputStream fos = new FileOutputStream(toFile);

			byte[] bt = new byte[1024];
			int c;
			while ((c = fis.read(bt)) > 0) {
				fos.write(bt, 0, c);
			}
			// 关闭输入、输出流
			fis.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 删除文件
	public static void deleteFile(File targetFile) {
		if (!targetFile.exists()) {
			return;
		}// 如果文件不存在
		if (!targetFile.isFile()) {
			return;
		}// 如果选择的不是文件
		targetFile.delete();
	}
}
