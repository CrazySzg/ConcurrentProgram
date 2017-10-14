package my.xzq.concurrent.api;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemapDemo implements Runnable{
	
	final Semaphore semp = new Semaphore(5); 
	
	
	@Override
	public void run() {
		try {
			semp.acquire();
			Thread.sleep(5000);
			System.out.println(Thread.currentThread().getName() + "done！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semp.release();
		}
		
	}
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(20);
		final SemapDemo demo  = new SemapDemo();
		for(int i=0;i<20;i++){
			service.submit(demo);
		}
		service.shutdown();
	}
	
}
