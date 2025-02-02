package club.iskyc.lulech.elden.jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.dom4j.dom.DOMCDATA;
import org.dom4j.io.HTMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;


public class AddFieldToMybatis {
	private final static String BASERESULTMAP = "<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" />";
	private final static String BASECOLUMNLIST = ",t.%s";
	private final static String TESTSTRING = "%s != null";
	private final static String BASEWHERECLAUSE = " and t.%s = #{%s} ";
	private final static String INSERT1 = " %s,";
	private final static String INSERT2 = " #{%s,jdbcType=%s},";
	private final static String UPDATEBYIDSELECTIVE  = " %s = #{%s,jdbcType=%s},";
	private final static String UPDATEBYID =", %s = #{%s,jdbcType=%s}";
	private final static Map<String, String> JDBCTYPE = new HashMap<String, String>();
	private List<String> fieldnames = new ArrayList<>();
	private Map<String, String> typemapper = new HashMap<>();
	private Document doc;
	{
		JDBCTYPE.put("char", "CHAR");
		JDBCTYPE.put("varchar", "VARCHAR");
		JDBCTYPE.put("tinyint", "TINYINT");
		JDBCTYPE.put("smallint", "SMALLINT");
		JDBCTYPE.put("int", "INTEGER");
		JDBCTYPE.put("float", "FLOAT");
		JDBCTYPE.put("bigint", "BIGINT");
		JDBCTYPE.put("double", "DOUBLE");
		JDBCTYPE.put("bit", "BOOLEAN");
		JDBCTYPE.put("date", "Date");
		JDBCTYPE.put("time", "TIME");
		JDBCTYPE.put("timestamp", "TIMESTAMP");
		JDBCTYPE.put("text", "VARCHAR");
		JDBCTYPE.put("longtext", "LONGVARCHAR");
	}
	public static void main(String[] args) {
		AddFieldToMybatis m = new AddFieldToMybatis();
		m.setFields();
		m.addFields();
	}
	
	public void setFields () {
		System.out.println("--------请输入MYSQL插入字段的语句  以-1结束------");
		Scanner sc = new Scanner(System.in);
		while (true) {
			String s = sc.nextLine();
			if ("-1".equals(s.trim())) {
				break;
			}
			if(!setField(s)) {
				System.out.println("解析失败 : "+s);
			}
		}
		sc.close();
	}
	
	private boolean setField(String s) {
		int index1 = s.indexOf("ADD COLUMN ");
		if (index1 == -1) {
			return false;
		}
		s = s.substring(index1).replaceFirst("ADD COLUMN ", "");
		int index2 = s.indexOf(" ");
		if (index2 == -1) {
			return false;
		} 
		String fieldname = s.substring(0, index2).trim().toLowerCase().replaceAll("`", "");
		s = s.substring(index2+1);
		int index3 = s.indexOf(" ");
		int index4 = s.indexOf(";");
		int index5 = s.indexOf("(");
		if (index3 > index4 && index4 != -1) {
			index3 = index4;
		}
		if (index3 == -1) {
			index3 = index4;
		}
		if (index3 > index5 && index5 != -1) {
			index3 = index5;
		}
		if (index3 == -1) {
			index3 = index5;
		}
		if (index3 == -1) {
			return false;
		}
		String type = s.substring(0, index3).toLowerCase();
		type = JDBCTYPE.get(type);
		if (null == type) {
			return false;
		}
		fieldnames.add(fieldname);
		typemapper.put(fieldname, type);
		return true;
	}
	
	public void addFields (){
		try ( InputStream htmlStream = new FileInputStream(new File("./source/file/UploadTempDao.xml"));
				FileOutputStream out = new FileOutputStream("./source/file/testMybatis.xml")) {
			// JSOUP 解析 html 获取一个文档
			doc = Jsoup.parse(htmlStream, "UTF-8", "");
			Element body = doc.body();
			Element mapper = body.children().first();
			Element baseResultMap = mapper.getElementById("BaseResultMap");
			addFildsToResultMap(baseResultMap);
			Element baseColumnList = mapper.getElementById("Base_Column_List");
			addFildsToColumnList(baseColumnList);
			Element baseWhereClause = mapper.getElementById("Base_Where_Clause");
			addFildsToWhereClause(baseWhereClause, baseResultMap.childNodeSize() - fieldnames.size());
			Element insert = mapper.getElementById("insert");
			addFildsToInsert(insert);
			Element updateByIdSelective = mapper.getElementById("updateByIdSelective");
			addFildsToUpdateByIdSelective(updateByIdSelective);
			Element updateById = mapper.getElementById("updateById");
			addFildsToUpdateById(updateById);
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

	private void addFildsToResultMap (Element baseResultMap) {
		for (String fieldname : fieldnames) {
			addToResultMap(fieldname, baseResultMap);
		}
	}
	
	private void addToResultMap (String fieldname, Element baseResultMap) {
		String upper = fieldname.toUpperCase();
		String lower = fieldname.toLowerCase();
		String result = String.format(BASERESULTMAP, upper,lower,typemapper.get(lower));
		baseResultMap.append(result);
	}
	
	private void addFildsToColumnList (Element baseColumnList){
		for (String fieldname : fieldnames) {
			addToColumnList(fieldname, baseColumnList);
		}
	}

	private void addToColumnList(String fieldname, Element baseColumnList) {
		String text = baseColumnList.text();
		text += String.format(BASECOLUMNLIST, fieldname.toUpperCase());
		baseColumnList.text(text);
	}
	
	private void addFildsToWhereClause(Element baseWhereClause, int start) {
		Element trim = baseWhereClause.getElementsByTag("trim").first();
		List<Node> children = new ArrayList<>();
		for (String fieldname : fieldnames) {
			String lower = fieldname.toLowerCase();
			Element tif = doc.createElement("if");
			tif.attr("test", String.format(TESTSTRING, lower));
			tif.text(String.format(BASEWHERECLAUSE, lower, lower));
			children.add(tif);
		}
		trim.insertChildren(start, children);
	}
	
	private void addFildsToInsert(Element insert) {
		Element trim1 = insert.getElementsByTag("trim").first();
		Element trim2 = insert.getElementsByTag("trim").last();
		for (String fieldname : fieldnames) {
			String lower = fieldname.toLowerCase();
			String upper = fieldname.toUpperCase();
			Element tif = trim1.appendElement("if");
			tif.attr("test", String.format(TESTSTRING, lower));
			tif.text(String.format(INSERT1, upper));
			tif = trim2.appendElement("if");
			tif.attr("test", String.format(TESTSTRING, lower));
			tif.text(String.format(INSERT2, lower, typemapper.get(lower)));
		}
	}
	
	private void addFildsToUpdateByIdSelective(Element updateByIdSelective) {
		Element set = updateByIdSelective.getElementsByTag("set").first();
		for (String fieldname : fieldnames) {
			String lower = fieldname.toLowerCase();
			String upper = fieldname.toUpperCase();
			Element tif = set.appendElement("if");
			tif.attr("test", String.format(TESTSTRING, lower));
			tif.text(String.format(UPDATEBYIDSELECTIVE, upper, lower, typemapper.get(lower)));
		}
	}
	
	private void addFildsToUpdateById(Element updateById) {
		String text = updateById.text();
		int index = text.lastIndexOf(" where");
		String textsuffix = text.substring(index);
		text = text.substring(0, index);
		for (String fieldname : fieldnames) {
			String lower = fieldname.toLowerCase();
			String upper = fieldname.toUpperCase();
			text += String.format(UPDATEBYID, upper, lower, typemapper.get(lower));
		}
		updateById.text(text+textsuffix);
	}	
}
