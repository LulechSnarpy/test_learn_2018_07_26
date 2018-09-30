package club.iskyc.lulech.gomoku.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnumUtil {
	private static final String GETNAME = "getName";
	private static final String GETVALUE = "getValue";
	
	private EnumUtil(){}
	
	public static Map<String,Object> getEnumMap(Class<?> clz){
		Map<String, Object> result = new ConcurrentHashMap<String, Object>();
		Object[] enums = clz.getEnumConstants();
		try {
			Method getName = clz.getMethod(GETNAME);
			Method getValue = clz.getMethod(GETVALUE);
			for (Object obj : enums) {
				String key = (String) getName.invoke(obj);
				Object value = getValue.invoke(obj);
				result.put(key, value);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}
}
