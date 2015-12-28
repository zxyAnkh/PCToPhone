package cn.edu.zucc.pctophone.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//�ο�http://blog.csdn.net/jdsjlzx/article/details/44900511

public class FileControler {
	// �����ļ�
	public static void copyfile(File fromFile, File toFile, Boolean rewrite) {

		if (!fromFile.exists()) {
			return;
		}// ����ļ�������
		if (!fromFile.isFile()) {
			return;
		}// ���ѡ��Ĳ����ļ�
		if (!fromFile.canRead()) {
			return;
		}// ����ļ����ɶ�
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}// ����ļ�·�����������½��ļ�
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}// ����ļ���Ҫ��д��ԭ�ļ�ɾ��

		try {
			FileInputStream fis = new FileInputStream(fromFile);
			FileOutputStream fos = new FileOutputStream(toFile);

			byte[] bt = new byte[1024];
			int c;
			while ((c = fis.read(bt)) > 0) {
				fos.write(bt, 0, c);
			}
			// �ر����롢�����
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

	// ɾ���ļ�
	public static void deleteFile(File targetFile) {
		if (!targetFile.exists()) {
			return;
		}// ����ļ�������
		if (!targetFile.isFile()) {
			return;
		}// ���ѡ��Ĳ����ļ�
		targetFile.delete();
	}
}
