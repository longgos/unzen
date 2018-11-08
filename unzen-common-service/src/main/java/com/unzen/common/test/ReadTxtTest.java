package com.unzen.common.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadTxtTest {

	public static void main(String[] args) throws IOException {
		try {
			FileReader fildReader = new FileReader("C:/Users/zhouzhenyi/Desktop/No.txt");
			BufferedReader bfReader = new BufferedReader(fildReader); 
			StringBuilder  stringBuilder =new StringBuilder();
		   	List<String>  strings =new ArrayList<>();
		   	String  str=null;
		   	int i =0;
		   	while ((str = bfReader.readLine())!=null) {
		   	  System.out.println(str);
		   	  if (str.trim().length()>2) {
                 strings.add(str);
		   	  }
		   	  i++;
			}
		   	System.out.println("总行数："+i);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
   	
}
