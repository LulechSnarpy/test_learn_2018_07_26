package club.iskyc.lulech.elden.jsoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class AddTrStyle {
	public static void main(String[] args) {
		AddTrStyle at = new AddTrStyle();
		at.addTdStyle();
	}
	
	public void addTdStyle() {
		try ( InputStream htmlStream = new FileInputStream(new File("./source/file/人社-干部任免表.html"));
				FileOutputStream out = new FileOutputStream("./source/file/人社-干部任免表out.html")) {
			// JSOUP 解析 html 获取一个文档
			Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
			Element body = doc.body();
			Elements tds = body.getElementsByTag("td");
			for (Element td : tds) {
				String style = td.attr("style");
				td.attr("style", style+"height:50px;");
			}
			PrintWriter pw = new PrintWriter(out);
			pw.print(doc.toString());
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
