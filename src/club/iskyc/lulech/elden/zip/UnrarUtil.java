package club.iskyc.lulech.elden.zip;

import java.io.File;
import java.io.IOException;

import com.github.junrar.Junrar;
import com.github.junrar.exception.RarException;

public class UnrarUtil {
	private static void Extract(String rarPath,String targetPath) {
		try {
			Junrar.extract(rarPath,targetPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RarException e) {
			e.printStackTrace();
		}
	}
	
	private static void Example() throws IOException, RarException{
		// Extract from files:
		Junrar.extract("/tmp/foo.rar", "/tmp");
		//or
		final File rar = new File("foo.rar");  
		final File destinationFolder = new File("destinationFolder");
		Junrar.extract(rar, destinationFolder);    
		//or
		//final InputStream resourceAsStream = Foo.class.getResourceAsStream("foo.rar");//only for a single rar file
		//Junrar.extract(resourceAsStream, tempFolder);
		//List files:
		//final List<ContentDescription> contentDescriptions = Junrar.getContentsDescription(testDocuments);  
	}
}
