package com.busvancar.cinema;

import java.io.File;
import java.lang.reflect.Field;

class A{
	private String fld = "A private field";
}

public class Test {
	public static void main(String[] args)  {
		A a = new A();
		
		try {
			Field f = A.class.getDeclaredField("fld");
			f.setAccessible(true);
			String fieldValue = (String)f.get(a);
			System.out.print(fieldValue);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
