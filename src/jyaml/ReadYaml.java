package jyaml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.ho.yaml.Yaml;

public class ReadYaml {
	
	public static void main(String[] args) throws Exception {
		//read();
		readObject();
	}
	
	public static void read() throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> s = Yaml.loadType(new File("./source/file/config.yml"),(new HashMap<String, String>()).getClass());
		System.out.println(s.toString());
	}
	
	public static void readObject() throws Exception {
		TempObject s = Yaml.loadType(new File("./source/file/configObj.yml"), TempObject.class);
		System.out.println(s.toString());
	}
}
