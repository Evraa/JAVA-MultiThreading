package assign_2;
class Threadsrequirement {
	 public static void main(String... args) throws InterruptedException {
	 	BookStock b = new BookStock (10);
		
		/*
		 * TODO 1: Create 4 threads,
		 * 1 thread for Supplier, named "Supplier".
		 * 3 threads for StoreBranches and name them: "Giza Branch", "Cairo Branch", and "Alex Branch".
	 	 */
	 	Thread supplier = new Thread(new Supplier(b));
	 	Thread Giza_br = new Thread(new StoreBranch(b));
	 	Thread Cairo_br = new Thread(new StoreBranch(b));
	 	Thread Alex_br = new Thread(new StoreBranch(b));
	 	
	 	
		/*
		 * TODO-2: Run the 4 threads.
		 */
	 	supplier.start();
	 	Giza_br.start();
	 	Cairo_br.start();
	 	Alex_br.start();
	 	
	 	//Should we add join to all of them ? will see !
	 	//yeah we do, main doesn't include any other work that might take time..
	 	//but we don'e have to join all of them
	 	//it's an infinite loop, so we can just join the first one of them...
	 	supplier.join();
//	 	Giza_br.join();
//	 	Cairo_br.join();
//	 	Alex_br.join();
	 	//Unreachable line
	 	System.out.println("All done :D");
	 	
    }
}

class BookStock {
	private int bookCount;
	private final int maxCount;
	public BookStock  (int max) {
		this.maxCount = max;
	}
	public void produce() {
		bookCount++;
	}

	public void consume () {
		bookCount--;
	}

	public int getCount () {
		return bookCount;
	}
	
	public final int getMaxCount() {
		return maxCount; 
	}
}

/*
 * TODO-3: Should the class Supplier extend any class or implement any interface?
 * Yes, because the supplier thread needs a runnable object to get started
 * and since we can't treat the supplier as a regular store branch, due to its different functionalities
 * we added another different Runnable interface.
 */
class Supplier implements Runnable{
	private BookStock b;

	public Supplier (BookStock b) {
		this.b = b;
	}
	
	/*
	 * TODO-4: Is there a function missing here? What does this function do?
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		doWork();
	}
	
	public void doWork () {
		while (true) {
			/*
			 * TODO-5: How to make the supplier stop producing when it reaches maxCount,
			 *  without adding extra sleeps or busy waiting ?
			 *  Check Example 11 in the lab code examples.
			 *  
			 *  by adding a wait() on the supplier object.
			 *  thus it releases the lock, for other threads to work when it's entirely full
			 *  and when a consumer/thread/store_branch needs more, it will notify all of the other working threads
			 *  then, our guy (supplier) exits the waiting state and resume producing.
			 */
			
			/*
			 *That b object is a shared variable among all of the threads
			 *so it needs to get locked when I'm using it. 
			 */
			synchronized(this.b) {
				while (b.getCount() >= b.getMaxCount()) 
				{
					try
					{
//						this.wait();
						//This wrong!!
						b.wait();
					} catch (InterruptedException e) {}	
				}
				
				b.produce();
				//Should I notify them? of course
				//this.notifyAll();
				//this is wrong cuz we're not locking on the supplier object
				//instead we are locking on the bockstock object.
				b.notifyAll();
				System.out.println (Thread.currentThread().getName() + " provided a book, total " +b.getCount());
			}
			try {
				Thread.sleep (300);
			} catch (InterruptedException e) {
				System.out.println (Thread.currentThread().getName() + "is awaken");
			}
        }
	}

	

}

/*
 * TODO-6: Should the class StoreBranch extend any class or implement any interface?
 * 		Yes, StoreBranch should implement the Runnable interface
 * 		and -must- add the run() function implementation.
 * 		
 * 		Every thread (among the 3) is a branch, so, this class represent each one of them.
 * 		That's why this class is the appropriate one for being the runnable interface.
 */
class StoreBranch implements Runnable {
	private BookStock b;

	public StoreBranch (BookStock b) {
		this.b = b;
	}

	/*
	 * TODO-7: Is there a function missing here? What does this function do?
	 * 	Since we are implementing the Runnable interface we must add an implementation to all of its
	 * abstract functions such as "run()".
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		doWork ();
	}
	
	public void doWork () {
		while (true) {
			/*
			 * TODO-8: How to make the store branch stop consuming when the store is empty,
			 *  without adding extra sleeps or busy waiting ?
			 *  Check Example 11 in lab code examples. 
			 */
			//SAME AS SUPPLIER...
			synchronized(this.b) {
				while (b.getCount() <= 0) 
				{
					try
					{
						//we wait upon the b object as well as our synchronization depends on it.
						b.wait();
					} catch (InterruptedException e) {}	
				}
			
				b.notifyAll();
				b.consume();
				System.out.println (Thread.currentThread().getName() + " sold a book");
			}
			try {
				Thread.sleep (2000);
			} catch (InterruptedException e) {
				System.out.println (Thread.currentThread().getName() + "is awaken");
			}
        }
	}

	
}