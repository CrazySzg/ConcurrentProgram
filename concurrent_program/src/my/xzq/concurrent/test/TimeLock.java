package my.xzq.concurrent.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 所申请等待限时
 * @author Administrator
 *
 */
public class TimeLock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();
	
	
	@Override
	public void run() {
		try {
			if(lock.tryLock(5, TimeUnit.SECONDS)){
				Thread.sleep(6000);
			}else{
				System.out.println(Thread.currentThread().getName() + ":获取锁失败");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
		
	}
	
	public static void main(String[] args) {
		TimeLock lock = new TimeLock();
		Thread t1 = new Thread(lock,"线程1");
		Thread t2 = new Thread(lock,"线程2");
		t1.start();
		t2.start();
	}

}
