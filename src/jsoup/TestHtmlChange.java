package jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.dom4j.io.HTMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.select.Elements;


public class TestHtmlChange {
	public static void main(String[] args) {
		try ( InputStream htmlStream = new FileInputStream(new File("./source/file/thead3.html"));
				FileOutputStream out = new FileOutputStream("./source/file/test1.html")) {
			// JSOUP 解析 html 获取一个文档
			org.jsoup.nodes.Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
			// 使用 dom4j 创建 HTML 
			HTMLWriter writer = new HTMLWriter();
			writer.setOutputStream(out);
			// 创建一个 写出文档 
			Document document = DocumentHelper.createDocument();
			// 创建跟节点
			Element root = document.addElement("html");
			// 创建头节点
			Element head = root.addElement("head");
			head.addElement("style").addText("table th,table td{ border:1px solid;} ");
			// 创建body
			Element body = root.addElement("body");
			// 获取并创建table元素
			org.jsoup.nodes.Element tableelement = doc.getElementsByTag("table").first();
			Element table = body.addElement("table");
			table.addAttribute("style", "width:100%; border-collapse:collapse;");
			Element thead = table.addElement("thead");
			Elements trs = tableelement.getElementsByTag("tr");
			// 遍历获取 tr
			for (org.jsoup.nodes.Element trelement : trs) {
				Element tr = thead.addElement("tr");
				String sclass = trelement.className();
				if (null != sclass && sclass != "") {
					tr.addAttribute("class", sclass);
				}
				Elements tds = trelement.getElementsByTag("td");
				// 遍历获取 td
				for (org.jsoup.nodes.Element tdelement : tds) {
					Element td = tr.addElement("td");
					td.addAttribute("style","word-break: break-all;");
					td.addAttribute("valign", "middle");
					td.addAttribute("align", "center");
					Attributes attributes = tdelement.attributes();
					for (Attribute attribute : attributes) {
						if ("style".equals(attribute.getKey())) {
							continue;
						}
						td.addAttribute(attribute.getKey(), attribute.getValue());
					}
					td.addText(tdelement.html().replaceAll("[(\\s)(&nbsp;)(<br/>)]+", ""));
				}
				
			}
			// 设置编码
			document.setXMLEncoding("UTF-8");
			// 写出文档
			writer.write(new DOMCDATA("<!DOCTYPE html>"));
			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
