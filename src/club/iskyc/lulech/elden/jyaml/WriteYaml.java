package club.iskyc.lulech.elden.jyaml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ho.yaml.Yaml;

public class WriteYaml {
	public static void main(String[] args) throws Exception {
		//write();
		writeObject();
	}
	public static void write() throws Exception {
		Map<String, String> s = new HashMap<>();
		s.put("approach", "HELLO");
		s.put("key", "WORLD");
		Yaml.dump(s, new File("./source/file/config.yml"));
	}
	
	public static void writeObject() throws Exception {
		TempObject to = new TempObject();
		List<NodeObject> lists = new ArrayList<NodeObject>();
		for (int i = 1; i<=10; i++) {
			lists.add(new NodeObject("Name"+i, i, i*2.0, i*1000L));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Boom", "JOCK");
		map.put("HIS", 1);
		map.put("OPL", 7.8);
		map.put("IST", true);
		to.setAttr(map);
		to.setNames(lists);
		Yaml.dump(to, new File("./source/file/configObj.yml"));
	}
}
