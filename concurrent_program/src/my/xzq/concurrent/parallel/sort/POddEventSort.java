package my.xzq.concurrent.parallel.sort;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并行奇偶交换排序
 * @author Administrator
 *
 */
public class POddEventSort {
	static int exchFlag = 1;
	public static int[]	arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	static ExecutorService pool = Executors.newCachedThreadPool();
	static synchronized void setExchFlag(int v){
		exchFlag = v;
	}
	static synchronized int getExchFlag() {
		return exchFlag;
	}
	
	static class OddEventSortTask implements Runnable {
		int  i;
		CountDownLatch latch;
		
		public OddEventSortTask(int i, CountDownLatch latch) {
			super();
			this.i = i;
			this.latch = latch;
		}

		@Override
		public void run() {
			if(arr[i] > arr[i+1]){
				int temp = arr[i];
				arr[i] = arr[i+1];
				arr[i+1] = temp;
				setExchFlag(1);
			}
			latch.countDown();
		}
	}
	
	public static void pOddEventSort(int[] arr) throws InterruptedException {
		int start = 0;
		while(getExchFlag() == 1 || start ==1) {
			setExchFlag(0);
			//偶数的数组长度，当start为1时，只有len/2-1个线程
			CountDownLatch latch = new CountDownLatch(arr.length/2-(arr.length%2==0?start:0));
			for(int i = start;i < arr.length  -1;i+=2){
				pool.submit(new OddEventSortTask(i, latch));
			}
			//等待所有线程结束
			latch.await();
			if(start == 0)
				start =1;
			else start = 0;
		}
	}
}
