package com.lanayru.javademo;

import java.lang.reflect.Method;

public class Main {

	public static void main(String[] args) {
//		System.out.println("hello");
//		testClassLoader();

		new VolatileTest().test();
	}

	private static void testClassLoader() {
		MyClassLoader loader = new MyClassLoader();

		try {
			Class cls = loader.loadClass("com.lanayru.javademo.Logs");

			Method m = cls.getDeclaredMethod("todo", String.class);

			m.setAccessible(true);
			m.invoke(cls, "class loader");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
