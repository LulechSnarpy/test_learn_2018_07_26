package club.iskyc.lulech.elden.test1;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/**
	 * 读取PDF即模板的使用
	 * 1、使用Adobe Acrobat 制作PDF模板（可以用word先编辑，另存为PDF格式）
	 * 	1) 文本域： 工具-内容编辑-编辑文本域图像（自动会选中）
	 *  2) 表单域: 工具-表单-编辑-编辑-添加新域（或者编辑域）
	 *  3) 编辑表单域可以设置一个name，如 ConNo;也可以设置显示的字体、大小、对齐方式等等。
	 * 2、涉及的核心类: PdfReader，PdfStamper
	 * 3、实现:
	 * 1) 读取PDF文档（PdfReader）-> 交给解析器(PdfStamper)
	 * 2) 获取表单域(AcroFields) -> 获取所有表单域数据(Map)
	 * 3) 处理: 循环Map，拿到key（即表单域的name）给表单域赋值即可。
	 * 	  AcroFields.setField(fieldName, fieldValue);
	 * 4) 关闭PdfReader， PdfStamper。
	 * */
public class PdfTemplateWithText {
 
	public static void main(String[] args) throws Exception{
		PdfTemplateWithText pdfTemplete = new PdfTemplateWithText();
		// 1-给PDF表单域赋值  
        pdfTemplete.fillFormDatas();  

        // 2-给PDF表格赋值  
        pdfTemplete.fillTableDatas();  
	}
	
	/**
	 * 获取模板表单，并赋值 固定用法
	 * */
	public void fillFormDatas() throws Exception {
		
		// 1-封装的数据，这边的key与pdf模板的域名保持一致
		Map<String, String> mMapDatas = new HashMap<String, String>();
		mMapDatas.put("CunstomerName", "SAM-SHO"); // 客户姓名
		mMapDatas.put("ContNo", "123456789098765"); // 合同号
		mMapDatas.put("ContCount", "1"); // 保单个数
		mMapDatas.put("EdorType", "CT-退保"); // 保全类型
		mMapDatas.put("GetMoney", "999.99"); // 保全失算金额
		mMapDatas.put("AcceptName", "人寿保险"); // 受理人
		mMapDatas.put("AcceptDate", "2014-11-01"); // 受理日期
		
		// 2-模板和生成的pdf
		Random a = new Random();
		a.nextInt();
		String tPdfTemplateFile = "./source/pdf/template/EdorTemplate.pdf"; // 获取模板路径
		String tPdfResultFile = "./temp/pdf/Edor_" + a.nextInt() + ".pdf"; // 生成的文件路径
		
		// 3-解析PDF模板
		FileOutputStream fos = new FileOutputStream(tPdfResultFile); // 需要生成PDF
		PdfReader reader = new PdfReader(tPdfTemplateFile); // 模板
		PdfStamper mPdfStamper = new PdfStamper(reader,fos); // 解析
		
		// 4-获取到模板上预定义的参数域
		AcroFields form = mPdfStamper.getAcroFields();
		// 获取模板中定义的变量
		Map<String, Item> acroFieldMap = form.getFields();
		// 循环解析模板定义的表单域
		for (Map.Entry<String, Item> entry : acroFieldMap.entrySet()) {
			// 获得块名
			String fieldName = entry.getKey();
			String fieldValue = mMapDatas.get(fieldName); // 通过名字，获取传入的参数值
			if (!"".equals(fieldValue)) {
				// 为模板中的变量赋值(key与pdf模板定义的域名一致)
				form.setField(fieldName, fieldValue);
				System.out.println(fieldName + "," + fieldValue);
			}
		}
		
		// 模板中的变量赋值之后不能编辑
		mPdfStamper.setFormFlattening(true);
		reader.close(); // 阅读器关闭，解析器暂时不关闭，因为创建动态表格还需要使用
		mPdfStamper.close();
	}
	
	/**
	 * 给PDF表格赋值 值动态的， 一般建议使用模板  直接创建绝对位置的表格
	 * */
	public void fillTableDatas() throws Exception {
		
		// 1-模板和生成和pdf
		
		String tPdfTemplateFile = "./source/pdf/template/EdorTemplate.pdf"; // 获取模板路径
		String tPdfResultFile = "./temp/pdf/Edor_" + new Random().nextInt() + ".pdf"; // 生成的文件路径
		
		// 2-解析PDF模板
		FileOutputStream fos = new FileOutputStream(tPdfResultFile); // 需要生成PDF
		PdfReader reader = new PdfReader(tPdfTemplateFile); // 模板
		PdfStamper mPdfStamper = new PdfStamper(reader, fos); // 解析
		
		// 3-获取到模板上预定义的参数域
		AcroFields form = mPdfStamper.getAcroFields();
		// 获取模板中定义的变量
		Map<String, Item> acroFieldMap = form.getFields();
		// 循环解析模板定义的表单域
		int len = 4;
		for (Map.Entry<String, Item> entry : acroFieldMap.entrySet()) {
			// 获得块名
			String fieldName = entry.getKey();
			String fieldValue = "fill_" + len;
			System.out.println(fieldName + ":" + fieldValue);
			form.setField(fieldName, fieldValue);
			len++;
		}
		
		// 模板中的变量赋值之后不能编辑
		mPdfStamper.setFormFlattening(true);
		reader.close(); // 阅读器关闭，解析器暂时不关闭，因为创建动态表格还需要使用
		mPdfStamper.close();
	}
	
	
	/** 
     * PDF文件合并 使用PdfCopy 
     *  
     * @author 
     * @param files 
     * @param os 
     */  
    public boolean mergePdfFiles(String[] files, String newfile) {  
        boolean retValue = false;  
        Document document = null;  
        try {  
            document = new Document();  
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));  
            document.open();  
  
            for (int i = 0; i < files.length; i++) {// 几个pdf文件循环  
                PdfReader reader = new PdfReader(files[i]);  
                int n = reader.getNumberOfPages();  
                for (int j = 1; j <= n; j++) {// 一个文件有多少页循环  
                    document.newPage();  
                    PdfImportedPage page = copy.getImportedPage(reader, j);  
                    copy.addPage(page);  
                }  
            }  
            retValue = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            document.close();  
        }  
        return retValue;  
    }  

    
    /** 
     * 合并PDF 
     *  
     * @author ShaoMin 
     * @throws Exception 
     *  
     */  
    public void mergePdf() throws Exception {  
  
        String[] files = { "source/pdf/1.pdf", "source/pdf/2.pdf" };  
        String savepath = "source/pdf/mergePdf.pdf";  
  
        Document document = new Document();  
  
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(savepath));  
        // PdfCopy copy = new PdfCopy(document, new  
        // FileOutputStream(newfile));//使用copy  
        document.open();  
        PdfContentByte cb = writer.getDirectContent();// 得到层  
  
        for (int i = 0; i < files.length; i++) {  
            PdfReader reader = new PdfReader(files[i]);  
            int n = reader.getNumberOfPages();  
            for (int j = 1; j <= n; j++) {  
                document.newPage();  
                // PdfImportedPage page = copy.getImportedPage(reader, j);  
                // copy.addPage(page);  
                PdfImportedPage page = writer.getImportedPage(reader, j);  
                cb.addTemplate(page, 0, 0);// 使用writer需要使用pdf的层,然后后添加  
            }  
        }  
        document.close();  
  
        // 使用PdfCopy 实现Pdf合并  
//      mergePdfFiles(files, savepath);  
  
    }  
    
    /** 
     * 删除页 
     *  
     * @author ShaoMin 
     * @throws Exception 
     *  
     */  
    public void deletePage() throws Exception {  
    	OutputStream out = new FileOutputStream("");
//      Document document = new Document();  
//      PdfWriter.getInstance(document, out);  
//      document.open();  
//      document.add(new Paragraph("First page"));  
//      document.add(new Paragraph(Document.getVersion()));  
//      document.newPage();  
//      document.add(new Paragraph("New page1"));  
//      document.newPage();  
//      document.add(new Paragraph("New page2"));  
//      document.close();  
  
        // 删除的方法在于读取，然后选择页数，然后在输出到另一个pdf  
        PdfReader reader = new PdfReader("deletePage.pdf");// 读取pdf  
        reader.selectPages("1,3");// 选择页数  
        PdfStamper stamp = new PdfStamper(reader, out);// 输出  
        stamp.close();  
        reader.close();  
  
    }  


}
