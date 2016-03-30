package com.util;

public class EnumTest {

	
	public static void main(String[] args){
		
		for(DocumentType type : DocumentType.values()){
			System.out.println(type + "1");
			System.out.println(type.toString());
		}
	}
}
