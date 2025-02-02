package club.iskyc.lulech.elden.poi;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlToExcelUtil {
	public int CreateHeadByHtml (InputStream htmlStream, Workbook wb,Sheet sheet,String title) throws IOException {
		Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
		Elements  tables = doc.select("table");
		if (tables.isEmpty()) return 0;
		Element table = tables.first();
		Elements  trs = table.select("tr");
		if (trs.isEmpty()) return 0;
		// 预计算单元格大小范围
		int totalrow = 0;
		int totalcol = 0;
		for (Element tr : trs) {
			int col = 0;
			int row = 1;
			Elements tds = tr.select("td");
			for (Element td : tds) {
				int rowspan = 0;
				int colspan = 0;
				String srowspan = td.attr("rowspan");
				String scolspan = td.attr("colspan");
				if (isNumberic(srowspan)) rowspan = Integer.parseInt(srowspan)-1;
				if (isNumberic(scolspan)) colspan = Integer.parseInt(scolspan)-1;
				col += colspan;
				col ++;
				row = rowspan > row ? rowspan : row;
			}
			totalcol = col > totalcol? col : totalcol;
			totalrow += row;
		}
		boolean[][] vis = new boolean[totalrow+1][totalcol];
		// 合并单元格并赋值
		int trow = 0;
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); 
		if (null != title && title.trim() != "") {
			Row titleRow = sheet.createRow(trow);
			titleRow.setHeightInPoints((short)22);
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(
					0, //first row (0-based)
					0, //last row  (0-based)
					0, //first column (0-based)
					totalcol-1 //last column  (0-based)
					));
			Cell cell = titleRow.createCell(0);
			cell.setCellValue(title);
			cell.setCellStyle(cellStyle);
			trow++;
		}
		for (Element tr : trs) {
			int col = 0;
			Elements tds = tr.select("td");
			Row row = sheet.createRow(trow);
			for (Element td : tds) {
				while (vis[trow][col]) {
					col++;
				}
				int rowspan = 0;
				int colspan = 0;
				String srowspan = td.attr("rowspan");
				String scolspan = td.attr("colspan");
				if (isNumberic(srowspan)) rowspan = Integer.parseInt(srowspan)-1;
				if (isNumberic(scolspan)) colspan = Integer.parseInt(scolspan)-1;
				//合并单元格
				if (rowspan > 0 || colspan > 0) {
					sheet.addMergedRegion(new CellRangeAddress(
							trow, //first row (0-based)
							trow+rowspan, //last row  (0-based)
							col, //first column (0-based)
							col+ colspan //last column  (0-based)
							));
				}
				fillVis(trow, trow+rowspan, col, col+ colspan, vis);
				Cell cell = row.createCell(col);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(td.text());
				col += colspan;
				col ++;
			}
			trow++;
		}
		return trow;
	}
	
	private void fillVis(int s1, int e1, int s2, int e2, boolean[][] vis){
		for (int i = s1; i <= e1; i++) {
			for (int j = s2; j <= e2; j++ ) {
				vis[i][j] = true;
			}
		}
	}
	
	private boolean isNumberic(String str) {
		if (str == null || str.trim() == "") return false;
		char[] value = str.toCharArray();
		for (int i = 0 ; i < value.length; i++) {
			if (!Character.isDigit(value[i])) return false;
		}
		return true;
	}
}
