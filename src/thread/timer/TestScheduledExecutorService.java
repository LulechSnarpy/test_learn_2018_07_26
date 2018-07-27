package thread.timer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledExecutorService {
	public static void main(String[] args) {
		Task t1 = new Task(1, "ping", 3000L);
		Task t2 = new Task(2, "pong", 5000L);
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		pool.scheduleWithFixedDelay(t1, 0, 2, TimeUnit.SECONDS);
		pool.scheduleWithFixedDelay(t2, 0, 2, TimeUnit.SECONDS);
	}
	private static class Task extends TimerTask {
		private int number = -1;
		private long sleepTimes = 0L;
		private String msg = "";
		public Task (int number, String msg, long sleepTimes){
			this.number = number;
			this.msg = msg;
			this.sleepTimes = sleepTimes;
		}
		@Override
		public void run() {
			System.out.println(number + ":" + msg);
			try {
				Thread.sleep(sleepTimes);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("--------" + number + ": sleep" + sleepTimes + "ms later---------");
		}
	}
}
