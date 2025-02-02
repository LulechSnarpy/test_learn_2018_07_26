package club.iskyc.lulech.elden.thread.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class TimerTest {
	public static void main(String[] args) {
		TimerExcutor.getTimerExcutor();
	}
	
	private static class TimerExcutor {
		private static TimerExcutor timerExcutor;
		private Timer timer = new Timer();
		private CountDownLatch latch = new CountDownLatch(10);
		private TimerExcutor() {	
			TaskWork task = null;
			long delay = 3000L;
			String[] msgs = ("boom start,break down,broken,distory,heat up"
					+ ",burning,cooling,die out,crash,ruin").split(",");
			for(int i=0; i<10; i++){
				task = new TaskWork(i++, msgs[--i]);
				timer.schedule(task, delay);
			}
		}
		public static synchronized TimerExcutor getTimerExcutor() {
			if (timerExcutor == null) {
				timerExcutor = new TimerExcutor();
			}
			return timerExcutor;
		}
		public void cancelT() {
			timer.cancel();
		}
		private class TaskWork extends TimerTask {
			private int number = -1;
			private String msg = "";
			public TaskWork (int number, String msg) {
				this.number = number;
				this.msg = msg;
			}
			@Override
			public void run() {
				System.out.println(number+":"+msg);
				if (number == 7) {
					//�����쳣��֮���task������ִ��
					//throw new NumberFormatException();
				}
				latch.countDown();
				if (latch.getCount() == 0L) {
					cancelT();
				}
			}
			
		}
	}
}
