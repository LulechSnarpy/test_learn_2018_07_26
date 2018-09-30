package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
	public static void main(String[] args) {
		//testStringRepalce1();
		//""
		//testStringRepacle2();
		//testStringReplace3();
		//testStringReplace4();
		String k = "测试3测试3测试3测试3测试<br />3测试3测试3测试3测试3测试3".replaceAll("\\s*<br\\s*/>\\s*", "");
		System.out.println(k);
	}

	private static void testStringReplace4() {
		String reg = "[\u4e00-\u9fa5]+:";
		String aim = "                	的境况时拉飞机数量:";
		Pattern patter = Pattern.compile("[\u4e00-\u9fa5]+:");
		Matcher m = patter.matcher(aim);
	
		System.out.println(m.find());
		System.out.println(aim.replaceAll(reg, "do"));
	}

	private static void testStringReplace3() {
		String reg = "(?<=\\.)(.*)";
		String aim = "test2.chj.zj.sx";
		System.out.println(aim.replaceAll(reg, "chj.zj.sh"));
	}

	private static void testStringRepacle2() {
		String reg = "(?<=[\\d]{3})(.)(?=[\\d]{7})";
		String aim = "13335351414";
		System.out.println(aim.replaceAll(reg, "****"));
	}

	private static void testStringRepalce1() {
		testStringReplace3();
	}
}
