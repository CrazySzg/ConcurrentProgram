package my.xzq.concurrent.test;

import java.util.concurrent.locks.ReentrantLock;

public class FairLock implements Runnable {
	/**
	 * 公平锁系统内部会维护一个队列保持线程执行的顺序
	 */
	public static ReentrantLock lock = new ReentrantLock(true);
	
	
	@Override
	public void run() {
		while(true){
			try{
				lock.lock();
				Thread.sleep(500);
				System.out.println(Thread.currentThread().getName() + "获得锁");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args) {
		FairLock lock = new FairLock();
		Thread t1 = new Thread(lock);
		Thread t2 = new Thread(lock);
		Thread t3 = new Thread(lock);
		Thread t4 = new Thread(lock);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	

}
