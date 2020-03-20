package assign_2;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
public class wait_notify {
	public static void main (String [] args) throws InterruptedException {

		NewRunnable n = new NewRunnable();
		Thread t1 = new Thread (n); t1.setName("1");
		Thread t2 = new Thread (n); t2.setName("2");

		t1.start(); 
		t2.start();

	}
}

class NewRunnable implements Runnable {
	private ArrayList array = new ArrayList();
	AtomicInteger ai1 = new AtomicInteger();

	public void run () {
		while (true) {
			if (Thread.currentThread().getName().equals("1")) {
				do_work1(); 
			}

			else {
				do_work2();
			}
		}
	}

	void do_work1 () {
		synchronized (this) {
			array.add(ai1.incrementAndGet());
			System.out.println("Added: "+ai1);
			notifyAll();
		}

		try {
			Thread.sleep (5000);
		} catch(InterruptedException e) {

		}
	}

	void do_work2 () {
		synchronized (this) {
			while (array.size() <= 0) {
				try { 
					System.out.println("I'll wait for u!");
					wait();
				} catch (InterruptedException e) {}	
			}
			System.out.println("Removed: "+array.remove(0));
		}

		try {
			Thread.sleep(100);
		} catch(InterruptedException e) {

		}
	}
}
