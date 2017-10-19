package my.xzq.concurrent.test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class WeakReferenceTest {
	public static void main(String[] args) throws InterruptedException {
		A a = new A();
		B b = new B(a);
		/*WeakReference<A> test = new WeakReference<A>(a){
			@Override
			protected void finalize() throws Throwable {
				System.out.println("weakReference 被回收了");
			}
		};*/
		a=null;
	//	System.gc();
		Thread.sleep(2000);
	}
	
	
	static class A{
		@Override
		protected void finalize() throws Throwable {
			System.out.println("A被回收了");
		}
	}
	static class B{
		A a;
		public B(A a){
			this.a = a;
		}
		@Override
		protected void finalize() throws Throwable {
			System.out.println("B被回收了");
		}
	}
}
