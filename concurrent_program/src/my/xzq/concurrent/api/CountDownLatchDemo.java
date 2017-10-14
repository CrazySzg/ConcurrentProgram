package my.xzq.concurrent.api;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 模拟火箭发射
 * @author Administrator
 *
 */
public class CountDownLatchDemo implements Runnable {
	public static final CountDownLatch end = new CountDownLatch(10);
	public static final CountDownLatchDemo demo = new CountDownLatchDemo();
	
	
	
	@Override
	public void run() {
		try {
			Thread.sleep(new Random().nextInt(10) * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " check complete");
		end.countDown();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		for(int i=0;i<10;i++){
			pool.submit(demo);
		}
		end.await();	
		System.out.println("火箭发射！");
		pool.shutdown();
	}

}
