package regex;

public class TestRegex {
	public static void main(String[] args) {
		//testStringRepalce1();
		//""
		//testStringRepacle2();
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
		String reg = "(?<=\\.)(.*)";
		String aim = "test2.chj.zj.sx";
		System.out.println(aim.replaceAll(reg, "chj.zj.sh"));
	}
}
