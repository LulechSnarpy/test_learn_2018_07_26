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
		
		// JDK8 使用Instant代替Date,LocalDateTime代替Calendar,
		// DateTimeFormatter代替SimpleDateFormat，官方給出的解釋:
		// simple beautiful strong immutable club.iskyc.lulech.elden.thread-safe
	}
}
