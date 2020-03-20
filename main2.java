package assign_2;

import assign_2.MultiplicationThread;
import java.util.ArrayList;
import java.util.Random;
//import java.util.concurrent.TimeUnit;
//
//import assign_2.Matrix.MultiplicationException;

public class main2 {
	
	public static void main (String[] args) throws InterruptedException {
		Matrix m1 = new Matrix(3,4);
		Matrix m2 = new Matrix(4,2);
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		Random rand = new Random(); 
		for (int i=0; i<12; i++) {
			//Random integer from 0 to 15
	    	int rand_int1 = rand.nextInt(15); 
			arr.add(rand_int1);
			if (arr.size() == 8)
				m2.setNumbers(arr);
			if (arr.size() == 12)
				m1.setNumbers(arr);
		}
		// Get a handler on the current main thread
        Thread t0 = Thread.currentThread();
        MultiplicationThread thread_obj = new MultiplicationThread(m1,m2);
        // Create 2 threads
        Thread t1 = new Thread(thread_obj);
        Thread t2 = new Thread(thread_obj);

        // Set the name of each thread
        t0.setName("Main Thread");
        t1.setName("1");
        t2.setName("2");
        
        //start the timer
        long startTime = System.nanoTime();
        
        // Start threads (t0 already running)
        t1.start();
        t2.start();

        // Wait for them to join the current thread
        t1.join(); 
        t2.join();
        //Q. why do we need the join function?
        //Ans. TODO
//        In this particular example, I dont have to wait for the two threads
//        to finish their job, as it's just [3,4]*[4,2] matrices, really small matrices
//        won't take much time,
//        BUT
//        I am using the same "thread_obj" to solve the requirment with
//        so if I didn't wait for the two threads to finish their jobs
//        Main could be much faster and overwrite the thread_obj
        
        long endTime = System.nanoTime();
        
        long timeElapsed = endTime - startTime;
		System.out.println("Execution time in nanoseconds : " + timeElapsed);
		thread_obj.print_result();
		
		//new matrices for time calculations
		Matrix m4 = new Matrix (500,500);
		Matrix m5 = new Matrix (500,500);
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		ArrayList<Integer> arr3 = new ArrayList<Integer>();
		for (int i=0; i<250000; i++)
		{
			//Random integer from 0 to 100
        	int rand_int1 = rand.nextInt(100); 
			arr2.add(rand_int1);
			int rand_int2 = rand.nextInt(100); 
			arr3.add(rand_int2);
		}
		m4.setNumbers(arr2);
		m5.setNumbers(arr3);
        
		thread_obj.setA(m4);
		thread_obj.setB(m5);
		thread_obj.setResult();
		Thread t3 = new Thread(thread_obj);
        Thread t4 = new Thread(thread_obj);
        t3.setName("1");
        t4.setName("2");
        
        startTime = System.nanoTime();
        
        // Start threads (t0 already running)
        t3.start();
        t4.start();

        // Wait for them to join the current thread
        t3.join(); 
        t4.join();
        
//        Using the join method here is necessary, because nothing is holding the main
//        from shutting down and making these two threads ORPHANS :'(
//        that's why we have to wait for them to finish
//        also because having [500,500]*[500,500] dimensions
//        this may take around 0.15 second
//        relatively large time
        
        endTime = System.nanoTime();
        
        timeElapsed = endTime - startTime;
		System.out.println("Execution time in milli : " + timeElapsed/1e6);
		
	}
}
