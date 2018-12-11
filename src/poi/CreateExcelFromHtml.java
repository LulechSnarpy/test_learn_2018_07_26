package poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

public class CreateExcelFromHtml {
	public static void main(String[] args) throws Exception{
		HtmlToExcelUtil util = new HtmlToExcelUtil();
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		util.CreateHeadByHtml(new FileInputStream(new File("./source/file/thead2.html")),wb,sheet,"杭州市余杭区公务员招录需求计划一览表（参公单位）");
		try (OutputStream out = new FileOutputStream(new File("C:\\Users\\Java10.PC-20180718WMWE\\Desktop\\test.xls"))) {
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
