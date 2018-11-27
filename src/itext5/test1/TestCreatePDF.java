package itext5.test1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TestCreatePDF {
	public static void main(String[] args) throws Exception {
		try (OutputStream out = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\pdftest.pdf"))) {
			createPdf(out);
		}
	}
	
	public static void createPdf(OutputStream out) throws Exception {
		
		//1.first step create a document
		Document document = new Document(PageSize.A4, 500, 150, 50, 50);
		
		//2.second step create a output 
		PdfWriter.getInstance(document, out);
		
		//3.thrid open document
		document.open();
		
		//4.forth add content to document
		document.add(new Paragraph("Hello! PDF!!!"));
		
		//5.fifth close document
		document.close();
	}
}
