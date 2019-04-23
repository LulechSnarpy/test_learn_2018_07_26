package test.doub;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

public class TestDouble {
	
	// 测试结果未知 0.1 为何会变成 .1 
	public static void main(String[] args) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BigDecimal db = new BigDecimal(".1");
		System.out.println(db);
		Float i = .1F;
		Node node = new Node();
		BeanInfo beanInfo = Introspector.getBeanInfo(Node.class);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			if ("s".equals(pd.getName())) {
				pd.getWriteMethod().invoke(node, i.doubleValue());
			}
		}
		System.out.println(node.getS());
	}
	
}

class Node {
	private Double s;

	public Double getS() {
		return s;
	}

	public void setS(Double s) {
		this.s = s;
	}
	
}