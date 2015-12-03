package exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Client {
	
	public static void main(String[] args) {
		String a = "abcdef";
		int b = 23;
		
		try {
			Class<?> c = Class.forName("stringConverter.Converter");
			Method m = c.getMethod("convert", a.getClass(), Integer.TYPE);
			Object d = m.invoke(null, a, 2);
			String v = (String)d;
			System.out.println(v);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("Kann nicht konvertieren");
		}
	}

}
