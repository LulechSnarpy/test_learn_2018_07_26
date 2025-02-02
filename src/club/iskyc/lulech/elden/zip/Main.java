package club.iskyc.lulech.elden.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


public class Main {
	public static void main(String[] args) throws Exception{
		//ZipOutputStream club.iskyc.lulech.elden.zip = new ZipOutputStream(new FileOutputStream(new File("D:/k.club.iskyc.lulech.elden.zip")));
		//club.iskyc.lulech.elden.zip.setEncoding(StandardCharsets.UTF_8.name());
		//File file = new File("D:/Documents/Tencent Files/1272045703/Image/Group");
		//File file = new File("https://192.168.1.214:8080/person/sys/file/download/5339");
		
		URL url= new URL("http://192.168.1.247:8080/acl/sys/file/download/912");
		FileUtils.copyURLToFile(url, new File("D://tt.jpg"));
		
		//System.out.println(url.getContent());
		//URI uri = new URI("https://192.168.1.214:8080/person/sys/file/download/5339");
		//File file = new File(uri);
		//System.out.println(file.exists());
		//compressFileWithOutBaseDirName(file, "GroupBy", club.iskyc.lulech.elden.zip);
		//club.iskyc.lulech.elden.zip.close();
	}
	
/*	private static void compressFileWithOutBaseDirName(File file,String pPath, ZipOutputStream zos){
		if (!file.exists()) return;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				compressFile(files[i], pPath, zos);
			}
			return;
		} 
	}*/
	
	/**
	 * 添加压缩文件内容 去除当前文件夹名称
	 * 
	 * @param file
	 * @param pPath
	 * @param zos
	 * @return 
	 * @author chenxy
	 * @data 2018-09-29
	 */
	private static void compressFileWithOutBaseDirName(File file,String pPath, ZipOutputStream zos){
		if (!file.exists()) return;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				compressFile(files[i], pPath, zos);
			}
			return;
		} 
		if (file.isFile()) {
			compressFile(file, pPath, zos);
			return;
		}
	}
	
	private static void compressFile(File file,String pPath, ZipOutputStream zos){
		if (!file.exists()) return;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			String tpPath = pPath + "/"+file.getName();
			for (int i = 0; i < files.length; i++) {
				compressFile(files[i], tpPath, zos);
			}
			return;
		} 
		BufferedInputStream bis = null;
		if (file.isFile()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				zos.putNextEntry(new ZipEntry(pPath+"/"+file.getName()));
				int k = -1;
				while((k = bis.read()) != -1){
					zos.write(k);
				} 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != bis){
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/*
	 * 如何读取超链接
 
    HSSFSheet sheet = workbook.getSheetAt(0);
 
HSSFCell cell = sheet.getRow(0).getCell((short)0);
 
    HSSFHyperlink link = cell.getHyperlink();
 
    if(link != null){
 
        System.out.println(link.getAddress());
 
    }
 
如何设置超链接
 
    HSSFWorkbook wb = new HSSFWorkbook();
 
//超链接的单元格风格
 
    //超链接默认的是蓝色底边框
 
    HSSFCellStyle hlink_style = wb.createCellStyle();
 
    HSSFFont hlink_font = wb.createFont();
 
    hlink_font.setUnderline(HSSFFont.U_SINGLE);
 
    hlink_font.setColor(HSSFColor.BLUE.index); 
 
    hlink_style.setFont(hlink_font);
 
HSSFCell cell;
 
    HSSFSheet sheet = wb.createSheet("Hyperlinks");
 
//URL
 
    cell = sheet.createRow(0).createCell((short)0);
 
    cell.setCellValue("URL Link");
 
    HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
 
    link.setAddress("");
 
    cell.setHyperlink(link);
 
    cell.setCellStyle(hlink_style);
 
//链接到当前路径的一个文件
 
    cell = sheet.createRow(1).createCell((short)0);
 
    cell.setCellValue("File Link");
 
    link = new HSSFHyperlink(HSSFHyperlink.LINK_FILE);
 
    link.setAddress("link1.xls");
 
    cell.setHyperlink(link);
 
    cell.setCellStyle(hlink_style);
 
//链接到e-mail 
 
    cell = sheet.createRow(2).createCell((short)0);
 
    cell.setCellValue("Email Link");
 
    link = new HSSFHyperlink(HSSFHyperlink.LINK_EMAIL);
 
    //note, if subject contains white spaces, make sure they are url-encoded
 
    link.setAddress("mailto:poi@apache.org?subject=Hyperlinks");
 
    cell.setHyperlink(link);
 
    cell.setCellStyle(hlink_style);
 
//链接到 workbook的某个地方
 
//创建一个目标Sheet和单元格
 
    HSSFSheet sheet2 = wb.createSheet("Target Sheet");
 
    sheet2.createRow(0).createCell((short)0).setCellValue("Target Cell");
 
cell = sheet.createRow(3).createCell((short)0);
 
    cell.setCellValue("Worksheet Link");
 
    link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
 
    link.setAddress("'Target Sheet'!A1");
 
    cell.setHyperlink(link);
 
    cell.setCellStyle(hlink_style);
 
FileOutputStream out = new FileOutputStream("c://hssf-links.xls");
 
    wb.write(out);
 
    out.close();

	 * */
	
}
