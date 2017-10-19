package my.xzq.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;

public class PCDataFactory implements EventFactory<PCData> {

	@Override
	public PCData newInstance() {
		// TODO Auto-generated method stub
		return new PCData();
	}

}
