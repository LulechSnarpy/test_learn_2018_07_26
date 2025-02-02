package club.iskyc.lulech.elden.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HttpClientTest {
	
	public static void main(String[] args) throws Exception{
		 // getCon();
		 getCon1();
	}
	
	// HttpGet
	public static void getCon() throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httpget
		HttpGet httpget = new HttpGet("http://ishare.iask.sina.com.cn/f/14938810.html");
		
		// 执行get请求 
		CloseableHttpResponse response = httpClient.execute(httpget);
		// 获取响应实体
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			// 打印响应内容长度
			System.out.println("Response content length: " + entity.getContentLength());
			// 打印响应内容
			System.out.println("Response content :" + EntityUtils.toString(entity));
		}
		// 关闭响应
		response.close();
		// 关闭请求
		httpClient.close();
	}
	
	// WebDriver
	public static void getCon1() throws Exception {
		// Chrome 
		System.setProperty("webdriver.chrome.driver", "./source/file/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		// FireFoxweb
		//System.setProperty("webdriver.gecko.driver", "./source/file/geckodriver.exe");
		//WebDriver driver = new FirefoxDriver();
		driver.get("http://ishare.iask.sina.com.cn/f/14938810.html");
		WebElement nextLink = driver.findElement(By.className(".page-next"));
		WebElement pageSize = driver.findElement(By.className("page-input-con")).findElement(By.tagName("span"));
		System.out.println(pageSize.getText());
		nextLink.click();
		//driver.quit();
	}
}
