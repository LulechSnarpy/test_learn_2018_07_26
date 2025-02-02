package club.iskyc.lulech.elden.thread.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThread {
	public static void main(String[] args) {
		TimeUnit unit = null;
		long keepAliveTime = 0;
		int corePoolSize = 0;
		int maximumPoolSize = 0;
		BlockingQueue<Runnable> workQueue = null;
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
}
