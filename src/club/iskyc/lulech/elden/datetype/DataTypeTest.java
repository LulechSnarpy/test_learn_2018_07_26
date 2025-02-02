package club.iskyc.lulech.elden.datetype;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataTypeTest {
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	
	public static void main(String[] args) {
		// "20091010121212"
		DateFormat format = df.get();
		String s = format.format(System.currentTimeMillis());
		System.out.println(s);
		
		// JDK8 ʹ��Instant����Date,LocalDateTime����Calendar,
		// DateTimeFormatter����SimpleDateFormat���ٷ��o���Ľ��:
		// simple beautiful strong immutable club.iskyc.lulech.elden.thread-safe
	}
}
