package my.xzq.concurrent.disruptor;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<PCData> {

	@Override
	public void onEvent(PCData event) throws Exception {
		System.out.println(Thread.currentThread().getId() + ":event:--" + event.getValue() * event.getValue() + "--");
		
	}

}
