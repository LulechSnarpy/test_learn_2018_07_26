package club.iskyc.lulech.gomoku.util;

public class StateUtil {
	private StateUtil(){}
	public static String converseString(String source){
		char[] value = source.toCharArray();
		int n = value.length - 1;
		for (int i = value.length >> 1; i < value.length; i++) {
			char temp = value[i];
			value[i] = value[n-i];
			value[n-i] = temp;
		}
		return new String(value);
	}
}
