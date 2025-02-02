package club.iskyc.lulech.elden.jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.dom.DOMCDATA;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
	
public class TestMybatisTranslate {
	public static void main(String[] args) {
		test2();
	}
	
	public static void test1(){
		try ( InputStream htmlStream = new FileInputStream(new File("./source/file/UploadTempDao.xml"));
				FileOutputStream out = new FileOutputStream("./source/file/testMybatis.xml")) {
			// JSOUP 解析 html 获取一个文档
			org.jsoup.nodes.Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
			org.jsoup.nodes.Element body = doc.body();
			org.jsoup.nodes.Element mapper = body.children().first();
			org.jsoup.nodes.Element baseResultMap = mapper.getElementById("BaseResultMap");
			// 使用 dom4j 创建 HTML 
			HTMLWriter writer = new HTMLWriter();
			writer.setOutputStream(out);
			// 写出文档
			writer.write(new DOMCDATA("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"));
			writer.write(new DOMCDATA("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n"));
			writer.write(new DOMCDATA(mapper.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void test2(){
		try ( InputStream htmlStream = new FileInputStream(new File("./source/file/UploadTempDao.xml"));
				FileOutputStream out = new FileOutputStream("./source/file/testMybatis.xml")) {
			// 使用dom4j 解析 HTML
			SAXReader reader = new SAXReader();
			Document document = reader.read(htmlStream);
			OutputFormat of = new OutputFormat();//.createPrettyPrint();
			of.setIndent(true);
			of.setIndent("\t");
			of.setNewlines(true);
			of.setNewLineAfterNTags(0);
			of.setSuppressDeclaration(false);
			of.setEncoding("UTF-8");
			// 使用 dom4j 创建 HTML 
			XMLWriter writer = new XMLWriter(of);
			writer.setOutputStream(out);
			// 写出文档
			// writer.write(new DOMCDATA("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"));
			// writer.write(new DOMCDATA("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n"));
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
