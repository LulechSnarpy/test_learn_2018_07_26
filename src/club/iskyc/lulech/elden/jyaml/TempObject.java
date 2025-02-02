package club.iskyc.lulech.elden.jyaml;

import java.util.List;
import java.util.Map;

public class TempObject {
	private List<NodeObject> names;
	private Map<String, Object> attr;
	
	public List<NodeObject> getNames() {
		return names;
	}
	public void setNames(List<NodeObject> names) {
		this.names = names;
	}
	public Map<String, Object> getAttr() {
		return attr;
	}
	public void setAttr(Map<String, Object> attr) {
		this.attr = attr;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{")
			.append("names").append(":").append(names.toString()).append(",")
			.append("attr").append(":").append(attr.toString())
		.append("}");
		return sb.toString();
	}
}
 