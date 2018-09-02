package com.lanayru.javademo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import okio.BufferedSource;
import okio.Okio;

/**
 * @author seven
 * @version V1.0
 * @since 2018/7/23
 */
public class MyClassLoader extends ClassLoader {

	private String path = "/Users/seven/github.com/Lanayru/JavaDemo/build/classes/java/main/";

	public MyClassLoader() {
	}

	public MyClassLoader(String path) {
		this.path = path;
	}

	@Override
	protected Class<?> findClass(String s) throws ClassNotFoundException {

		byte[] data = loadClassData(getFileName(s));
		if (null == data) {
			throw new ClassNotFoundException(s);

		}

		return defineClass(s, data, 0, data.length);
	}

	private byte[] loadClassData(String name) {
		File f = new File(path, name);

		try {
			BufferedSource bs = Okio.buffer(Okio.source(f));
			return bs.readByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] loadClassd(String name) {
		File f = new File(path, name);
		InputStream in = null;
		ByteArrayOutputStream out = null;

		try {
			in = new FileInputStream(f);
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];

			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}

			return out.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	private static String getFileName(String name) {
		int index = name.indexOf(".");
		if (index == -1) {
			return name + ".class";
		} else {
			return name.substring(index + 1) + ".class";
		}
	}
}
