package my.xzq.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import my.xzq.concurrent.disruptor.MainMethod;

public class RealData implements Callable<String> {
	
	private String data;
	
	public RealData(String data){
		this.data = data;
	}
	
	
	
	@Override
	public String call() throws Exception {

		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < 10;i++){
			sb.append(data);
			Thread.sleep(100);
		}
		return sb.toString();
	}

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<String> future  = new FutureTask<>(new RealData("a"));
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(future);
		System.out.println("请求完毕");
		Thread.sleep(2000);
		
		System.out.println("数据 = " + future.get());
		executor.shutdown();
	}
}
