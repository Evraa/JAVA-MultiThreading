package assign_2;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import assign_2.Matrix.MultiplicationException;


public class main1 {
	
	public static void main (String[] args) {
		Matrix m1 = new Matrix(3,4);
		Matrix m2 = new Matrix(4,2);
		Matrix m3 = new Matrix(2,5);

		ArrayList<Integer> arr = new ArrayList<Integer>();
		Random rand = new Random(); 
		for (int i=0; i<12; i++) {
			//Random integer from 0 to 15
        	int rand_int1 = rand.nextInt(15); 
			arr.add(rand_int1);
			if (arr.size() == 8)
				m2.setNumbers(arr);
			if (arr.size() == 10)
				m3.setNumbers(arr);
			if (arr.size() == 12)
				m1.setNumbers(arr);
		}
		
		Matrix m1_2;
		Matrix m1_3;
		m1.Print();
		m2.Print();
		m3.Print();
		
		long startTime = System.nanoTime();
		try {
		    m1_2 = m1.multiply(m2);
		}catch(MultiplicationException ex) {
		    ex.Message();
		}
		long endTime = System.nanoTime();
		
		long timeElapsed = endTime - startTime;
		System.out.println("Execution time in nanoseconds : " + timeElapsed);
		
		try {
		    m1_3 = m1.multiply(m3);
		}catch(MultiplicationException ex) {
		    ex.Message();
		}
		
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
		
		System.out.println("Experimenting the time of execution for two 500x500 matrices multiplication!");
		
		Matrix m4_5;
		startTime = System.nanoTime();
		try {
		    m4_5 = m4.multiply(m5);
		}catch(MultiplicationException ex) {
		    ex.Message();
		}
		endTime = System.nanoTime();
		timeElapsed = endTime - startTime;
		System.out.println("Execution time in milli : " + timeElapsed/1000000);
	}
}
