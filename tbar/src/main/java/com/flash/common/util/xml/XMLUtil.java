package com.flash.common.util.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;

public class XMLUtil {
	private static String charset="UTF-8"; 
	/** 
	    * doc2String 
	    * 将xml文档内容转为String 
	    * @return 字符串 
	    * @param document 
	    */ 
	   public static String doc2String(Document document) 
	   { 
	      String s = ""; 
	      try 
	      { 
	           //使用输出流来进行转化 
	           ByteArrayOutputStream out = new ByteArrayOutputStream(); 
	           //使用GB2312编码 
	           OutputFormat format = new OutputFormat(null, true, charset); 
	           XMLWriter writer = new XMLWriter(out, format); 
	           writer.write(document); 
	           s = out.toString(charset); 
	      }catch(Exception ex) 
	      {             
	           ex.printStackTrace(); 
	      }       
	      return s; 
	   }
	   /** 
	    * string2Document 
	    * 将字符串转为Document 
	    * @return  
	    * @param s xml格式的字符串 
	    */ 
	   public static Document string2Document(String s) 
	   { 
	      Document doc = null; 
	      try 
	      { 
	           doc = DocumentHelper.parseText(s); 
	      }catch(Exception ex) 
	      {             
	           ex.printStackTrace(); 
	      } 
	      return doc; 
	   }
}
