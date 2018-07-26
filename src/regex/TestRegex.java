package regex;

public class TestRegex {
	public static void main(String[] args) {
		String reg = "(?<=\\.)(.*)";
		String aim = "test2.chj.zj.sx";
		System.out.println(aim.replaceAll(reg, "chj.zj.sh"));
	}
}
