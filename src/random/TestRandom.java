package random;

import java.util.concurrent.ThreadLocalRandom;

public class TestRandom {
	public static void main(String[] args) {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		for(int i=0; i<1000; i++){
			int num = threadLocalRandom.nextInt(56);
			System.out.println(num);
		}
	}
}
