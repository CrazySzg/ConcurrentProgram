package my.xzq.concurrent.executors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

	public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		
	}
	
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		super.execute(wrap(command,clientTrace(),Thread.currentThread().getName()));
	}
	
	@Override
	public Future<?> submit(Runnable task) {
		// TODO Auto-generated method stub
		return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
	}

	private Exception clientTrace(){
		return new Exception(Thread.currentThread().getName() + " Client stack trace");
	}
	
	private Runnable wrap(final Runnable task,final Exception clientStack,String clientThreadName){
		return new Runnable() {
			@Override
			public void run() {
				try {
					task.run();
				} catch (Exception e) {
					clientStack.printStackTrace();
					throw e;
				}
			}
		};
	}
	
	public static void main(String[] args) {
		ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0,
				Integer.MAX_VALUE,
				0L,
				TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		/**
		 * 使用 jdk 自带的线程池，当线程中出现异常时，只能得知具体异常在哪里被抛出，无法定位
		 * 到该线程任务在哪里被提交，即具体任务提交的位置已经被线程池完全淹没了
		 */
		
		
		/*ThreadPoolExecutor pools = new ThreadPoolExecutor(0,
				Integer.MAX_VALUE,
				0L,
				TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());*/
		
		
		
		
		for(int i = 0;i < 5;i++){
		//	pools.submit(new DivTask(100, i));
			pools.execute(new DivTask(100, i));
		}
		pools.shutdown();
	}
}
