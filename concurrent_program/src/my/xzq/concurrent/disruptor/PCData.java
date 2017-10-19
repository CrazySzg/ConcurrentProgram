package my.xzq.concurrent.disruptor;

public class PCData {
	private long value;
	public void setValue(long value) {
		this.value = value;
	}
	public long getValue() {
		return value;
	}
}
