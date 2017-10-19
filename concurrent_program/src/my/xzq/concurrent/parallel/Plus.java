package my.xzq.concurrent.parallel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Plus implements Runnable {

	public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<>();
	
	
	@Override
	public void run() {

		while(true){
			Msg msg = null;
			try {
				msg = bq.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg.j = msg.i + msg.j;
			Multiply.bq.add(msg);
		}
	}

}
