package club.iskyc.lulech.elden.thread.calc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

//++ -- 非原子性
public class TestCalc {
	static int i=0;
	final static Thread thread1 = new Thread(){
		@Override
		public void run(){
			i++;
		}
	};
	final static Thread thread2 = new Thread(){
		@Override
		public void run(){
			i--;
		}
	};
	public static void main(String[] args) {
		thread1.start();
		thread2.start();
		AtomicInteger count = new AtomicInteger();
		count.addAndGet(1);
		AtomicLong countlong = new AtomicLong();
		countlong.addAndGet(1L);
		//JDK8 LongAdder
	}
}
