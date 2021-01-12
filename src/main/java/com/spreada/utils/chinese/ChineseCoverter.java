package com.spreada.utils.chinese;

public class ChineseCoverter {
	//����ת����
	public static String  SinpleToTraditional(String simplified) {
		String traditional = ZHConverter.convert(simplified,ZHConverter.TRADITIONAL);  
	    return traditional;
	}
	//����ת����
	public static String TraditionalToSinple(String Traditional) {
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED); 
	    String simplified = converter.convert(Traditional);  
	    return simplified;
	}
}
