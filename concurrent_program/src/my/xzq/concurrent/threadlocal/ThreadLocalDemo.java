package my.xzq.concurrent.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Administrator
 *
 */
public class ThreadLocalDemo {
	static volatile ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected void finalize() throws Throwable {
			System.out.println(this.toString() + " is gc ");
		}
	};

	static volatile CountDownLatch cd = new CountDownLatch(10000);

	public static class ParseDate implements Runnable {
		int i = 0;
		public ParseDate(int i) {
			this.i = i;
		}
		
		@Override
		public void run() {
			if(tl.get() == null){
				tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
					@Override
					protected void finalize() throws Throwable {
						System.out.println(this.toString() + " is gc");
					}
				});
				
				System.out.println(Thread.currentThread().getId() + ":create SimpleDateFormat");
			}
			try {
				Date t = tl.get().parse("2017-10-15 16:54:" + i % 60);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				cd.countDown();
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for(int i = 0;i < 10000;i++){
			pool.execute(new ParseDate(i));
		}
		cd.await();
		System.out.println("mission complete!!");
		tl = null;
		System.gc();
		System.out.println("first GC complete");
		tl  = new ThreadLocal<SimpleDateFormat>();
		cd = new CountDownLatch(10000);
		for(int i = 0;i < 10000;i++){
			pool.execute(new ParseDate(i));
		}
		cd.await();
		System.gc();
		Thread.sleep(1000);
		System.out.println("second GC complete!!");
		pool.shutdown();
	}
}
