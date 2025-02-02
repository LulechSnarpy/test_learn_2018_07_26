package club.iskyc.lulech.elden.test1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @see https://blog.csdn.net/qq_39181568/article/details/80003903
 */
public class TestCreatePDF {
	
	private static String[] tableHeader;
	private static String[] tableCont;
	
	public static void main(String[] args) throws Exception {
		tableHeader = "T1,T2,T3,T4,T5,T6".split(",");
		tableCont = "C1,C2,C3,C4,C5,C6".split(",");
	    File file = new File("C:\\Users\\Administrator\\Desktop\\pdftest.pdf");
		// File file = new File("./source/file/club.iskyc.lulech.elden.test.pdf");
		// new File("./source/file").mkdirs();
		try (OutputStream out = new FileOutputStream(file)) {
			//1.
			//createPdf(out);
			//3.
			//myPDF(out);
			//4.
			//addContent(out);
			//5.
			//insertObject(out);
			//6.
			//insertTable(out);
			//7.
			//myTable(out);
			//8.
			//addShuyinByWritter(out);
			//9.
			//addShuiYinByTempete(out);
			//10.
			//insertHeadAndFoot(out);
			//11.
			CreatePdfByXml(out);
		}
	}
	
	/**
	 * 1.PDF文档生成的5步
	 */
	public static void createPdf(OutputStream out) throws Exception {
		
		//1.first step create a document
		Document document = new Document(PageSize.A4, 500f, 150f, 50f, 50f);
		
		//2.second step create a output 
		PdfWriter.getInstance(document, out);
		
		//3.thrid open document
		document.open();
		
		//4.forth add content to document
		document.add(new Paragraph("Hello! PDF!!!"));
		
		//5.fifth close document
		document.close();
	}
	
	/**
	 * 2.文档对象：Document、Rectangle、PageSize
	 */
	public static Document createDocumentUseRectangle(){

		// 1-创建一个pdf文档,document  
		Document document = null;
		document = new Document();//默认PageSize.A4,36,36,36,36
		document = new Document(PageSize.A4);//等效于上面的
		document = new Document(PageSize.A4, 50f, 50f, 50f, 50f);//PageSize封装了大量常用的Rectangle数据
		
		// 2-Rectangle(pdf页面)创建Document
		// 一般是四个参数表示：左下角坐标和右上角的坐标
		Rectangle tRectangle = null;
		tRectangle = PageSize.A4;// PageSize封装了大量常用的Rectangle数据
		tRectangle = new Rectangle(800f, 600f);// 长宽
		tRectangle = new Rectangle(0f, 0f, 800f, 600f);// 等于上面
		
		// 2-1 其他页面属性： 不能和PageSize封装的静态一起使用
		tRectangle.setBackgroundColor(BaseColor.BLACK);// 背景色
		tRectangle.setBorder(1220);// 边框
		tRectangle.setBorderColor(BaseColor.BLUE);
		tRectangle.setBorderWidth(244.2f);
		
		document = new Document(tRectangle);
		
		// 2-2 横向打印
		document = new Document(PageSize.A4.rotate());// 横向打印
		document = new Document(tRectangle.rotate());// 横向打印
		
		try {
			// 解析器
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf/pdfText.pdf"));
			
			// 3-为pdf添加属性信息
			document.addAuthor("作者"); 
			document.addTitle("标题");
			document.addSubject("主题");
			document.addKeywords("关键字");
			
			//页边空白
			document.setMargins(10f, 20f, 30f, 40f);
			
			document.open();
			
			// 4-PDF添加内容
			document.add(new Paragraph("Hello world"));
			
			// 5-添加Page
			document.newPage();
//			writer.setPageEmpty(false);//显示空内容的页
			writer.setPageEmpty(true); //不会显示空内容的页
			
			document.newPage();
			document.add(new Paragraph("New page"));
			
			//创建结束
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * 3.综合案例代码
	 *
	 * 页面大小，页面背景色，页边空白，Title,Author,Subject,Keywords
	 * */
	public static void myPDF(OutputStream out) throws DocumentException {
		// 1 - 页面属性
		Rectangle tRectangle = new Rectangle(PageSize.A4);// 页面大小
		// tRectangle = new Rectangel(0, 0, 800, 600);
		
		tRectangle.setBackgroundColor(BaseColor.ORANGE);// 页面背景颜色
		tRectangle.setBorder(1220);// 边框
		tRectangle.setBorderColor(BaseColor.BLUE);// 边框颜色
		tRectangle.setBorderWidth(244.2f);// 边框宽度
		
		Document doc = new Document(tRectangle);// 定义文档
		doc = new Document(tRectangle.rotate());// 横向打印
		
		PdfWriter writer = PdfWriter.getInstance(doc, out);// 书写器
		
		// 2 - PDF文档属性
		doc.addTitle("Title@sample");// 标题
		doc.addAuthor("Author@lulech");// 作者
		doc.addSubject("Subject@iText sample");// 主题
		doc.addKeywords("Keyword@iText");// 关键字
		doc.addCreator("Creator@iText");// 谁创建的
		
		// 3 - 综合属性:
		doc.setMargins(10f, 20f, 30f, 40f);// 页边空白
		
		doc.open();// 打开文档
		doc.add(new Paragraph("Hello World"));// 添加内容
		
		// 4 - 添加下一页
		doc.newPage();
		writer.setPageEmpty(true);// false-显示空内容的页;true-不会显示空内容的页
		
		doc.newPage();
		doc.add(new Paragraph("New page"));
		
		doc.close();
	}
	
	/**
	 * 4.内容对象：
	 * 1.中文支持
	 * 1)BaseFont 确认支持中文
	 * 2)Font -字体设置，如颜色，字体，大小等
	 * 3)固定用法如下
	 * */
	public static Font getChineseFont() {
		BaseFont bfChinese;
		Font fontChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			//fontChinese = new Font(bfChinese, 12, Font.NORMAL);
			fontChinese = new Font(bfChinese, 12f, Font.NORMAL, BaseColor.BLUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fontChinese;
	}
	
	/**
	 *  Element接口
	 *  1)内容对象基本都实现这个接口。如Chunk、Phrase、Paragraph
	 *  2)一些有用的方位参数:
	 * 	 ALIGN_LEFT, ALIGN_CENTER, ALGIN_RIGHT, ALIGN_JUSTIFIED。
	 *   如何设置居中对齐: setAlignment(Element.ALIGN_CENTER)
	 *  
	 *  Chunk
	 *  1)块对象: a String, a Font, and some attributes
	 *  2)方法: Chunk.NEWLINE-换行
	 *  	setUnderLine(0.2f, -2f) - 下划线
	 *  	setTextRise(6)-上浮
	 *  
	 *  Phrase
	 *  1) Phrase短语对象： a List of Chunks with leading
	 *  2)方法: add(Element)-添加方法，add(Chunk.NEWLINE)-内部换行
	 *    setLeading(14f)-行间距
	 *    
	 *  Paragraph -- (新段落另起一行)
	 *  1)段落对象 : a Phrase with extra club.iskyc.lulech.elden.properties and a newline
	 *  2)方法: 
	 *  	add(Element)-添加; setLeading(20f)-行间距 ,一个Paragraph只有一个行间距
	 *  	setIndentationLeft()-左缩进, setIndentationRight-右缩进，
	 *  	setFirstLineIndent -首行缩进;
	 *  	
	 *  	setSpacingBefore-设置上空白, setSpacingAfter(10f)-设置段落下空白；
	 *  	
	 *  	setAlignment(Element.ALIGN_CENTER)-居中对齐
	 * */
	 public static void addContent(OutputStream out) throws Exception {
		 Document document = new Document(PageSize.A4);
		 PdfWriter.getInstance(document, out);
		 document.open();
		 
		 // 1-Chunk块对象 : a String, a Font, and some attributes
		 document.add(new Chunk("中文输出:  ", getChineseFont()));
		 
		 Chunk tChunk2 = new Chunk("输出的内容", getChineseFont());
		 tChunk2.setBackground(BaseColor.CYAN, 1f, 0.5f, 1f, 1.5f); // 设置背景色
		 tChunk2.setTextRise(6f); // 上浮
		 tChunk2.setUnderline(0.2f, -2f); // 下划线
		 document.add(tChunk2);
		 
		 document.add(Chunk.NEWLINE); // 新建一行
		 // document.add(new Phrase("Phrase page :")); // 会上浮，不知道原因 ??
		 
		 // 2-Phrase短语对象 : a List of Chunks with leading
		 document.add(new Phrase("Phrase page :"));
		 
		 Phrase tPhrase = new Phrase();
		 Chunk name = new Chunk("China");
		 name.setUnderline(0.2f, -2f);
		 tPhrase.add(name);
		 tPhrase.add(Chunk.NEWLINE); // 放在容器中好用
		 tPhrase.add(new Chunk("换行了 :", getChineseFont()));
		 tPhrase.add(new Chunk("chinese"));
		 tPhrase.setLeading(14f); // 行间距
		 document.add(tPhrase);
		 
		 // 这边就好用， 上面是Chunk, 就不好用
		 // 放在段落或者短语汇中又好用
		 document.add(Chunk.NEWLINE);
		 
		 Phrase director2 = new Phrase();
		 Chunk name2 = new Chunk("换行了 --Japan", getChineseFont());
		 name2.setUnderline(0.2f, -2f);
		 director2.add(name2);
		 director2.add(new Chunk(","));
		 director2.add(new Chunk(" "));
		 director2.add(new Chunk("japanese上浮下", getChineseFont()).setTextRise(3f));
		 director2.setLeading(24f);
		 document.add(director2);
		 
		 // 3-Paragraph段落对象： a Phrase with extra club.iskyc.lulech.elden.properties and a newline
		 document.add(new Paragraph("Paragraph page"));
		 Paragraph info = new Paragraph();
		 info.add(new Chunk("China"));
		 info.add(new Chunk("chinese"));
		 info.add(Chunk.NEWLINE); // 好用的
		 info.add(new Phrase("Japan "));
		 info.add(new Phrase("japanese"));
		 info.setSpacingAfter(10f); // 设置段落下空白
		 document.add(info);
		 
		 // 段落是比较好用的
		 Paragraph tParagraph = new Paragraph("段落是文章中最基本的单位。内容上它是具有一个相对完整的意思;"
		 		+ "在文章中，段落具有换行的标。段落是有句子和句群组成的，在文章用于体现作者的思路发展或全篇文章的层次。"
		 		+ "有的段落只有一个句子，称为独句断，独句段一般是文章的开头段、结尾段、过渡段强调段等特殊的段落。"
		 		+ "多数段落包括不止一个句子或句群，叫多句段。中文段落开头前一般空两格。", getChineseFont());
		 tParagraph.setAlignment(Element.ALIGN_JUSTIFIED); // 对齐方式
		 
		 tParagraph.setIndentationLeft(12f); // 左缩进
		 tParagraph.setIndentationRight(12f); // 右缩进
		 tParagraph.setFirstLineIndent(24f); // 首行缩进
		 
		 tParagraph.setLeading(20f); // 行间距
		 tParagraph.setSpacingBefore(5f); // 设置上空白
		 tParagraph.setSpacingAfter(10f); // 设置段落下空白 
		 document.add(tParagraph);
		 
		 // 每个新的段落会另起一行
		 tParagraph = new Paragraph("新的段落", getChineseFont());
		 tParagraph.setAlignment(Element.ALIGN_CENTER); // 居中
		 document.add(tParagraph);
		 
		 document.close();
	 }
	 
	 
	 /**
	  * Image继承自Rectangle
	  * 1) 初始化: Image img = Image.getInstance("./source/imag/bage.png")
	  * 2) 方法:
	  * 	setAlignment(Image.LEFT) - 对齐方式，setBorder(Image.BOX) - 边框
	  * 	setBorderWidth(10) - 边框宽度, setBorderColor(BaseColor.WHITE) - 边框颜色
	  * 	scaleToFit(1000, 72) - 大小, setRotationDegrees(-30) - 旋转
	  * 	setAbsolutePosition() - 绝对位置
	  * 
	  * Anchor（锚点、超链接）、Chapter\Section（目录章节）等：
	  * */
	  public static void insertObject(OutputStream out) throws Exception {
		  
		  Document document = new Document(PageSize.A4);
		  PdfWriter.getInstance(document, out);
		  document.open();
		  
		  // Anchor超链接和锚点对象: internal and external links
		  Paragraph country = new Paragraph();
		  Anchor dest = new Anchor("我是锚点，也是超链接", getChineseFont());
		  dest.setName("CN"); // 设置锚点的名字
		  dest.setReference("http://www.iskyc.club"); // 链接
		  country.add(dest);
		  country.add(String.format(": %d sites", 10000));
		  document.add(country);
		  
		  Anchor toUS = new Anchor("链接到设置的CN锚点。", getChineseFont());
		  toUS.setReference("#CN"); // 取到锚点
		  document.add(toUS);
		  
		  // 图片Image对象
		  Image img = Image.getInstance("./source/imag/bage.png");
		  img.setAlignment(Image.LEFT);
		  img.setBorder(Image.BOX);
		  img.setBorderWidth(10f);
		  img.setBorderColor(BaseColor.WHITE);
		  img.scaleToFit(1000f, 72f); // 大小
		  img.setRotationDegrees(-30); // 旋转
		  document.add(img);
		  
		  // Chapter, Secation对象（目录对象）
		  Paragraph title = new Paragraph("一级标题", getChineseFont());
		  Chapter chapter = new Chapter(title,1);
		  
		  Paragraph title2 = new Paragraph("二级标题1", getChineseFont());
		  Section section = chapter.addSection(title2);
		  section.setBookmarkTitle("bmk"); // 左边目录显示的名字，不写就默认名
		  section.setIndentation(30f);
		  section.setIndentationLeft(5f);
		  section.setBookmarkOpen(false);
		  section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
		  
		  Section section2 = chapter.addSection(new Paragraph("二级标题2", getChineseFont()));
		  section2.setIndentation(30f);
		  section2.setIndentationLeft(5f);
		  section2.setBookmarkOpen(false);
		  section2.setNumberStyle(Section.NUMBERSTYLE_DOTTED);
		  
		  Section subsection = section.addSection(new Paragraph("三级标题1", getChineseFont()));
		  subsection.setIndentationLeft(10f);
		  // subsection.setNumberDepth(1);
		  subsection.setNumberStyle(Section.NUMBERSTYLE_DOTTED);
		  
		  Section subsection2 = section2.addSection(new Paragraph("三级标题2", getChineseFont()));
		  subsection2.setIndentationLeft(10f);
		  subsection2.setNumberStyle(Section.NUMBERSTYLE_DOTTED);
		  document.add(chapter);
		  
		  document.close();
	  }
	  
	  /**
	   * 表格对象：Table、PdfPTable
	   * 1.构造方法:
	   * 	PdfPTable datatable = new PdfPTable(6); // 列数
	   * 	PdfPTable datatable = new PdfPTable(new float[]{1,2,3})-每个单元格宽度
	   * 2.结构:
	   * 	PdfPTable[PdfPTable[PdfPCell[Paragraph]]]
	   * 3.方法
	   * 	1) setWidths(数组)-单元格宽度， setTotalWidth(300f)-表格的总宽度
	   * 	   setWidthPercentage(100)-表格的宽度百分比， setLockedWidth(true)-宽度锁定
	   * 	2) getDefaultCell() - 得到默认单元格， addCell() - 添加单元格
	   * 	   getPadding(2) - 单元格的间隔， setBackgroundColor(BaseColor.GREEN)-背景色
	   * 	3) setSpacingAfter(40f) - 设置表格下面空白行， setSpacingBofore(20f) - 设置表格上面空白行
	   * 	   new Paragraph("\n\n") - 可以实现换行，留白
	   * 	4) setBorderWidth(2) - 边框宽度
	   * 	5) setHorizontalAlignment(Element.ALIGN_CENTER) - 对齐方式
	   * 	6) 写入绝对位置:
	   * 			PdfContentByte tContent = writer.getDirectContent() - 得到层
	   * 			table.writeSelectedRows(0, -1, 0, -1, 100, 200, tContent) - 写入绝对位置	
	   * */
	   /**
	    * 插入表格
	    * */
	   public static void insertTable(OutputStream out) throws Exception {
		   Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		   // 使用PDFWriter进行写文件操作
		   PdfWriter.getInstance(document, out);
		   document.open();
		   
		   // 中文字体（现在高版本的不支持中文包）
		   BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H"
				   , BaseFont.NOT_EMBEDDED);
		   Font fontChinese = new Font(bfChinese, 12f, Font.NORMAL); // 中文，12，正常
		   
		   int colNumber = 6;
		   
		   // PdfPTable[PdfPTable[PdfPCell[Paragraph]]]
		   // 创建有6列的表格
		   PdfPTable datatable = new PdfPTable(colNumber);
		   // 定义表格的宽度
		   int[] cellsWidth = { 1, 1, 1, 1, 1, 1 };
		   datatable.setWidths(cellsWidth); // 单元格宽度
		   // datatable.setTotalWidth(300f); // 表格的总宽度
		   datatable.setWidthPercentage(100); // 表格的宽度百分比
		   
		   datatable.getDefaultCell().setPadding(2f); // 单元格的间隔
		   datatable.getDefaultCell().setBorderWidth(2f); // 边框宽度
		   // 设置表格的底色
		   datatable.getDefaultCell().setBackgroundColor(BaseColor.GREEN);
		   datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		   
		   // PdfPTable[PdfPCell[Paragraph]]
		   // 添加表头元素
		   for (int i = 0; i < colNumber; i++) {
			   datatable.addCell(new Paragraph(tableHeader[i], fontChinese));
		   }
		   
		   // 添加表格的内容
		   for (int i = 0; i < colNumber; i++) {
			   datatable.addCell(new Paragraph(tableCont[i], fontChinese));
		   }
		   
		   // 空白表格
		   for (int i = 0; i < colNumber; i++) {
			   PdfPCell cell = new PdfPCell(new Paragraph(""));
			   cell.setFixedHeight(10f); // 单元格高度
			   datatable.addCell(cell);
		   }
		   
		   datatable.setSpacingAfter(40f); // 设置表格下面空白行
		   document.add(datatable); // 把表格加入文档
		   
		   // 跨行跨列表格
		   PdfPTable table = new PdfPTable(3); // 3列表格
		   PdfPCell cell; // 单元格
		   cell = new PdfPCell(new Phrase("跨3列", getChineseFont()));
		   cell.setColspan(3); // 跨3列
		   table.addCell(cell);
		   
		   cell = new PdfPCell(new Phrase("跨2行", getChineseFont()));
		   cell.setRowspan(2); // 跨2行
		   table.addCell(cell);
		   table.addCell("row 1; cell 1");
		   table.addCell("row 1; cell 2");
		   table.addCell("row 2; cell 1");
		   table.addCell("row 2; cell 2");
		   
		   document.add(table);
		   
		   // 表格的嵌套
		   PdfPTable tableFather = new PdfPTable(4);
		   tableFather.setSpacingBefore(20f); // 设置表格上面空白行
		   // 1行2列
		   PdfPTable nested1 = new PdfPTable(2);
		   nested1.addCell("1.1");
		   nested1.addCell("1.2");
		   // 2行1列
		   PdfPTable nested2 = new PdfPTable(1);
		   nested2.addCell("2.1");
		   nested2.addCell("2.2");
		   
		   // 将表格插入到指定位置
		   for (int k = 0; k < 12; k++) {
			   if (k == 1) {
				   tableFather.addCell(nested1);
			   } else if (k == 6) {
				   tableFather.addCell(nested2);
			   } else {
				   tableFather.addCell("cell" + k);
			   }
		   }
		   document.add(tableFather);
		   
		   document.close();
	   }
	   /**
	    * 单元格对象： PdfPCell
	    * 1.构造函数
	    * 	PdfCell cell = PdfPCell(new Paragraph("表格", 中文支持))
	    * 2.方法
	    * 	1) setBackgroundColor(BaseColor.CYAN) - 背景色
	    * 	2) setMinimumHeight(30f) - 最小高度
	    *	   set FixedHeight(40f) - 固定高度。表格的高度通过单元格高度完成
	    *	3) setBorder(Rectangle.NO_BORDER) - 无边框 ， setBorderWidth(0) - 无边框。
	    *	         不设，默认是有边框的
	    *	   setBorderColor(new BaseColor(255, 0, 0)) - 边框颜色
	    *	4) setHorizontalAlignment(Element.ALIGN_CENTER) - 水平居中
	    *	   setVerticalAlignment(Element.ALIGN_MIDDLE) - 垂直居中。
	    *	         设置单元格内容的显示
	    * 	5) setRowspan(2) - 跨2行， setColspan - 跨2列
	    * */
	   public static void myTable(OutputStream out) throws Exception {
		   
		   Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		   PdfWriter writer = PdfWriter.getInstance(document, out);
		   document.open();
		   
		   PdfPTable table = new PdfPTable(6);
		   // 添加表头元素
		   for (int i = 0; i < 6; i++) {
			   table.addCell(new Paragraph(tableHeader[i], getChineseFont()));
		   }
		   // 添加表格的内容
		   for (int i = 0; i < 6; i++) {
			   table.addCell(new Paragraph(tableCont[i], getChineseFont()));
		   }
		   table.setSpacingBefore(10f); // 设置表格上面空白宽度
		   
		   // 1 - 表格的宽度和布局
		   table.setHorizontalAlignment(Element.ALIGN_LEFT); // 居左
		   table.setTotalWidth(369.7f); // 表格的总宽度
		   table.setWidths(new float[] { 0.1565f, 0.15f, 0.15f
				   , 0.145f, 0.15f, 0.145f}); // 单元格宽度
		   table.setWidthPercentage(100f); // 设置表格宽度为 100%
		   // table.setLockedWidth(true); // 宽度锁定，不锁定，下面有变化
		   document.add(table);
		   document.add(new Paragraph("\n\n"));
		   
		   // 居中
		   table.setHorizontalAlignment(Element.ALIGN_CENTER);
		   document.add(table);
		   document.add(new Paragraph("\n\n"));
		   
		   // 居右
		   table.setWidthPercentage(50f); // 宽度减半
		   table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		   document.add(table);
		   document.add(new Paragraph("\n\n"));
		   
		   // 固定宽度
		   table.setTotalWidth(300f);
		   table.setLockedWidth(true); // 锁定宽度
		   document.add(table);
		   
		   // 2 - 表格的边框、高度、设置单元格颜色、前后距离
		   PdfPCell cell = new PdfPCell(new Paragraph("合并3个单元格", getChineseFont()));
		   cell.setColspan(3);
		   cell.setBackgroundColor(BaseColor.CYAN);
		   cell.setMinimumHeight(30f); // 最小高度
		   cell.setFixedHeight(40f); // 固定高度
		   table.addCell(cell);
		   
		   // 单元格内文本
		   Paragraph tParagraph = new Paragraph("居中", getChineseFont());
		   tParagraph.setAlignment(Element.ALIGN_CENTER);
		   cell = new PdfPCell(tParagraph);
		   cell.setBorderColor(new BaseColor(255, 0, 0)); // 边框 ， 下面的表格有可能会覆盖
		   cell.setFixedHeight(45f); // 固定高度，覆盖前面的固定高度
		   cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平居中
		   cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 垂直居中
		   table.addCell(cell);
		   
		   //  单元格背景色
		   cell = new PdfPCell(new Paragraph("无边框", getChineseFont()));
		   cell.setBorder(Rectangle.NO_BORDER); // 无边框
		   cell.setBorderWidth(0); // 无边框
		   table.addCell(cell);
		   
		   // 边框颜色
		   cell = new PdfPCell(new Paragraph("单元格边框颜色", getChineseFont()));
		   cell.setBorderColor(BaseColor.YELLOW);
		   table.addCell(cell);
		   
		   document.add(new Paragraph("使用'SpacingBefore'和'setSpacingAfter'", getChineseFont()));
		   table.setSpacingBefore(15f); // 前距离
		   document.add(table);
		   table.setSpacingAfter(15f); // 后距离
		   document.add(table);
		   
		   // 3 - 写入文档的绝对位置
		   // 参数rowStart是你想开始的行的数目，参数rowEnd是你想显示的最后的行（如果你想显示所有的行，用-1）
		   // xPos和yPos是表格的坐标，canvas是一个PdfContentByte对象
		   document.add(new Paragraph(
				   "写入文档的绝对位置:(writerSelectedRows(rowStart, rowEnd, xPos, yPos, canvas))，"
				   + "参数rowStart是你想开始的行的数目， 参数是rowEnd是你想显示的最后的行(如果你想显示所有的行，用-1)，"
				   + "xPos和yPos是表格的坐标，canvas是一个PdfContentByte对象。",
				   getChineseFont()
			));
		   
		   PdfContentByte tContent = writer.getDirectContent();
		   table.writeSelectedRows( 0, -1,  0, -1, 100f, 200f, tContent);
		   
		   document.add(new Paragraph("第1行到最后,从0开始计数", getChineseFont()));
		   table.writeSelectedRows(1, -1, 100, 100, tContent);
		   document.close();
	   } 
	   
	   /**
	    * PDF结构 - 4层结构
	    * 1.四层结构
	    * 2.层对象： PdfContentByte
	    * 3.一、四层可操作； 二、三层Itext内部处理
	    * 4.操作
	    * 	(1)PdfWriter对象：
	    * 		第 1 层操作: PdfWriter.getDirectContent(),
	    * 		第 2 层操作: getDirectContentUnder()。
	    * 	(2)PdfStamper对象:
	    * 		第 1 层操作: PdfStamper.getUnderContent(1), - 可以加页
	    * 		第 2 层操作: PdfStamper.getOverContent(1)。
	    * 5.作用:添加水印、背景、添加内容到绝对位置、合并PDF
	    * 
	    * 添加水印
	    * 1.方法:
	    * 	PdfContentByte under = writer.getDirectContentUnder(); // 默认当前页
	    * 	PdfContentByte under = stamp.getUnderContent(1); // 拿到层，可以有页数
	    * 2.文本水印
	    * 1) beginText(): 开始， endText() 结束
	    * 2) showTextAligned()写入文档，这个方法有很多重载，可以添加方位，旋转等。
	    * */
	   	public static void addShuyinByWritter(OutputStream out) throws Exception {
	   		Document document = new Document(PageSize.A4);
	   		PdfWriter writer = PdfWriter.getInstance(document, out);
	   		document.open();
	   		
	   		/**
	   		 * PDF分为四层，第一层和第四层由低级操作来进行操作，第二层、第三层由高级对象操作（从下往上）
	   		 * 第一层操作只能使用PdfWriter.DirectContent操作，第四层使用DirectContentUnder操作，
	   		 * 第二层和第三层的PdfContentByte是由IText内部操作，没有提供api接口。
	   		 * */
	   		PdfContentByte under = writer.getDirectContentUnder();
	   		// under = writer.getDirectContent()
	   		
	   		under.beginText();
	   		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI
	   				,BaseFont.EMBEDDED);
	   		under.setFontAndSize(bf, 18f);
	   		// under.setTextMatrix(30f, 30f);
	   		under.showTextAligned(Element.ALIGN_LEFT, "ShuiYin..........", 230f, 430f, 45f);
	   		under.endText();
	   		
	   		document.close();
	   	}
	   
	   	/**
	   	 * 图片水印与背景
	   	 * 1.添加水印
	   	 * 2.水印与背景的区别： 背景只需要把绝对位置为 从 文档的左下角开始。 
	   	 * 	  即设置setAbsolutePosition(0, 0)
	   	 * 3.位置的定位: 理解页面对象 -- Rectangle
	   	 * 	 Rectangle tRectangle = new Rectangle(0, 0, 800, 600);
	   	 * */
	   	public static void addShuiYinByTempete(OutputStream out) throws Exception {
	   		// 读取器
	   		PdfReader reader = new PdfReader("./source/file/club.iskyc.lulech.elden.test.pdf");
	   		// 解析器与输出
	   		PdfStamper stamp = new PdfStamper(reader, out);
	   		
	   		// 图片水印
	   		Image img = Image.getInstance("./source/imag/bage.png");
	   		img.setAbsolutePosition(100f, 100f);
	   		PdfContentByte under = stamp.getUnderContent(1); // 拿到层，页数
	   		under.addImage(img);
	   		
	   		// 文字水印
	   		PdfContentByte over = stamp.getOverContent(1); // 拿到层，字显示在图片上
	   		over.beginText();
	   		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI
	   				, BaseFont.EMBEDDED);
	   		over.setFontAndSize(bf, 18f);
	   		over.setTextMatrix(30f, 30f);
	   		over.showTextAligned(Element.ALIGN_LEFT, "ShuiYin", 230f, 430f, 45f);
	   		over.endText();
	   		
	   		// 背景图
	   		Image img2 = Image.getInstance("./source/imag/club.iskyc.lulech.elden.test.jpg");
	   		img2.setAbsolutePosition(0f, 0f);
	   		img2.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
	   		PdfContentByte under2 = stamp.getUnderContent(reader.getNumberOfPages());
	   		under2.addImage(img2);
	   		
	   		// 关闭
	   		stamp.close();
	   		reader.close();
	   	}
	   	
	   	/**
	   	 * 添加头尾注和页码等
	   	 * 1.需要使用监听器，类似于Sax解析Xml:
	   	 * 	1) PdfWriter.setPageEvent(PdfPageEvent event) - PdfPageEven接口
	   	 *  2) PdfPageEventHelper类实现了PdfPageEven接口。我们自定义只要实现
	   	 *     PdfPageEventHelper即可，随意重写需要的方法即可。
	   	 * 2.重写的方法:
	   	 * 		onOpenDocument(PdfWriter writer, Document document)
	   	 * 		onEndPage(PdfWriter writer, Document document) 等等
	   	 * 3.添加头尾注和页码： -- 添加文本到绝对位置
	   	 * */
	   	public static void insertHeadAndFoot(OutputStream out) throws Exception {
	   		Document doc = new Document();
	   		PdfWriter writer = PdfWriter.getInstance(doc, out);
	   		
	   		// 内部类，处理器
	   		writer.setPageEvent(new PdfPageHelper());
	   		
	   		doc.open();
	   		doc.add(new Paragraph("1 page"));
	   		doc.newPage();
	   		doc.add(new Paragraph("2 page"));
	   		
	   		doc.close();
	   	}
	   	
	   	/**
	   	* 将非标准的html强转换成形式
	   	*/
	   	public static String html2xhtml(String html) {
			org.jsoup.nodes.Document doc = Jsoup.parse(html);
			doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
			return doc.html();
		}
	   	
	  	public static void CreatePdfByXml(OutputStream out) throws Exception{
	  		Document document = new Document(PageSize.A4, 30, 30, 30, 30);
	  		PdfWriter writer = PdfWriter.getInstance(document, out);
	  		document.open();
	  		BufferedReader reader = new BufferedReader(new FileReader("./source/file/club.iskyc.lulech.elden.test.html"));
	  		StringBuffer buffer = new StringBuffer();
	  		String temp = null;
	  		while ((temp = reader.readLine()) != null) {
	  			buffer.append(temp);
	  		}
	  		reader.close();
	  		// html 文件输入流 
	   		InputStream inputStream = new ByteArrayInputStream(html2xhtml(buffer.toString()).getBytes("UTF-8"));
			// css 文件输入流
			InputStream cssStream = new BufferedInputStream(new FileInputStream("./source/file/club.iskyc.lulech.elden.test.css"));
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream, cssStream,Charset.forName("UTF-8"));
			document.close();
			cssStream.close();
	  	}
}

class PdfPageHelper extends PdfPageEventHelper {
	
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		
		PdfContentByte cb = writer.getDirectContent(); // 得到层
		cb.saveState();
		// 开始
		cb.beginText();
		cb.setFontAndSize(getBaseFont(), 10f);
		// Header
		float x = document.top(-20f); // 位置
		// 左
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "H-Left", document.left(), x, 0f);
		// 中
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "第" + writer.getPageNumber() + "页",
				(document.right() + document.left())/2, x, 0f);
		// 右
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "H-Right", document.right(), x, 0f);
		// Footer
		float y = document.bottom(-20f);
		// 左
		cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "F-left", document.left(), y, 0f);
		// 中
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "第" + writer.getPageNumber() +" 页", 
				(document.right() + document.left()/2), y, 0f);
		// 右
		cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "F-Right", document.right(), y, 0f);
		cb.endText();
		cb.restoreState();
		super.onEndPage(writer, document);
	}
	
	private BaseFont getBaseFont(){
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bfChinese;
	}
}


