package my.xzq.concurrent.parallel.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class PStreamSearch {
	
	public static int[]	arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	public static ExecutorService pool = Executors.newCachedThreadPool();
	public static final int THREAD_NUM = 2;
	public static AtomicInteger result = new AtomicInteger(-1);
	
	public static int search(int searchValue,int start,int end){
		int i=0;
		for(i=start;i<end;i++){
			if(result.get() >= 0){
				return result.get();
			}
			if(arr[i] == searchValue){
				//如果设置失败，表示其他线程已经先找到了
				if(!result.compareAndSet(-1, i)){
					return result.get();
				}
				return i;
			}
		}
		return -1;
	}
	
	public static class SearchTask	implements Callable<Integer> {
		int begin,end,searchValue;
		public SearchTask(int searchValue,int begin,int end) {
			this.begin = begin;
			this.end = end;
			this.searchValue = searchValue;
		}
		
		
		@Override
		public Integer call() throws Exception {
			int re = search(searchValue,begin,end);
			return re;
		}
		
	}
	
	public static int pSearch(int searchValue) throws InterruptedException, ExecutionException{
		int subArrSize = arr.length / THREAD_NUM+1;
		List<Future<Integer>> re = new ArrayList<>();
		
		for(int i=0;i<arr.length;i+=subArrSize){
			int end = i+subArrSize;
			if(end >= arr.length) end = arr.length;
			re.add(pool.submit(new SearchTask(searchValue, i, end)));
		}
		
		for(Future<Integer> future:re){
			if(future.get() >= 0)
				return future.get();			
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int index = pSearch(20);
		System.out.println(index);
		pool.shutdown();
	}
}
