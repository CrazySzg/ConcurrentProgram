package my.xzq.concurrent.api;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 可以理解为循环栅栏
 * @author Administrator
 *
 */
public class CyclicBarrierDemo {
	
	public static class Soldier implements Runnable{
		private String soldier;
		private final CyclicBarrier cyclic;
		
		public Soldier(CyclicBarrier cyclic,String soldier) {
			this.cyclic = cyclic;
			this.soldier = soldier;
		}
		
		@Override
		public void run() {
			try {
				cyclic.await();
				doWork();
				cyclic.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		void doWork() throws InterruptedException{
			Thread.sleep(new Random().nextInt(10) * 1000);
			System.out.println(soldier + ":任务完成");
			
		}
	}
	
	
	public static class BarrierRun implements Runnable{
		public boolean flag;
		int N;
		
		public BarrierRun(boolean flag,int N) {
			this.flag = flag;
			this.N = N;
		}
		
		
		@Override
		public void run() {
			if(flag){
				System.out.println("司令：[士兵 " + N + "个，任务完成");
			} else {
				System.out.println("司令：士兵" + N + "个，集合完毕");
				flag = true; 
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		final int N = 10;
		Thread[] allSolider = new Thread[N];
		boolean flag = true;
		CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
		
		System.out.println("集合队伍");
		
		for(int i = 0;i < N;i++){
			System.out.println("士兵 " + i +" 报道！");
			allSolider[i] = new Thread(new Soldier(cyclic, "士兵 " + i));
			allSolider[i].start();
			if(i == 5){
				allSolider[i].interrupt();
			}
		}
	}
	
}
