package club.iskyc.lulech.elden.jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class TestHtmlChange2 {
	public static void main(String[] args) {
		try ( InputStream htmlStream = new FileInputStream(new File("./source/file/add.jsp"))) {
			// JSOUP 解析 html 获取一个文档
			org.jsoup.nodes.Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
			System.out.println(doc);
			Elements elements = doc.getElementsByTag("glf:dictOptions");
			for(Element element: elements){
				String id = element.attr("dictId");
				String value = element.attr("value");
				System.out.println(id + ":" + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
