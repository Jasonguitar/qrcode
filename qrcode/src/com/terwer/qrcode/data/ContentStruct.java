package com.terwer.qrcode.data;

public class ContentStruct {

	public int index = 0;
	public String daString = "";
	
	public ContentStruct(){
		
	}
	
	public String toString(){
		String result = "index = " + index + ", string = " + daString;
		return result;
	}
	
}
