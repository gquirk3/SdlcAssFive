import java.util.Random;

public class SdlcAssFive {

	public static void main(String[] args)
	{
		long startTime;
		long endTime;
		long time;
		
		// decided to time the array creation
		startTime = System.currentTimeMillis();
		// creating array of random numbers
		int[] randomNumbers = new int[200000000];
		for(int i=0; i < randomNumbers.length; i++)
		{
			randomNumbers[i] = randomNum(1 , 10);
		}
		endTime = System.currentTimeMillis();
		time = (endTime -startTime);
		System.out.println("It took " + time + "ms to create the array!");
		
		// creating 3 threads - thread 1 runs by itself - threads 2 and 3 split the workload
		Threads one = new Threads(randomNumbers, 0, randomNumbers.length);
		Thread t1 = new Thread(one);
		Threads two = new Threads(randomNumbers, 0, 100000000);
		Thread t2 = new Thread(two);
		Threads three = new Threads(randomNumbers, 100000000, randomNumbers.length);
		Thread t3 = new Thread(three);
		
		// starting thread 1
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// storing single thread sum and time and then outputting results
		int singleSum = one.total;
		long singleTime = one.time;
		System.out.println("Single Thread sum: " + singleSum + " time: " + singleTime);
		
		// starting threads 2 and 3
		t2.start();
		t3.start();
		try {
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// storing multithread sum and time and then outputting results
		int multiSum = two.total;
		multiSum += three.total;
		long multiTime = two.time;
		System.out.println("MultiThread sum: " + multiSum + " time: " + multiTime);
		
		
	
	}

	//random number method
	public static int randomNum(int min, int max) 
	{
		Random randGen = new Random();
		int randomNum = randGen.nextInt((max-min)+1)+min;
		return randomNum;
	}
	
}


class Threads extends Thread
{
	long startTime;
	long endTime;
	long time;
	int total;
	int[] n;
	int start;
	int end;
	
	public Threads (int[] n, int start, int end) 
	{
		this.n = n;
		this.start = start;
		this.end = end;
	}
	
	// method to calculate sum of array
	public int sum (int[] input) 
	{
		int sum = 0;
		
		for (int i = start; i<end; i++)
		{
			sum += input[i];
		}
		
		return sum;
	}
	
	public void run()
	{
		startTime = System.currentTimeMillis();  // getting the time before the thread starts
		total = sum(n);  // calling the method 
		endTime = System.currentTimeMillis();  // getting the time after the thread ends
		time = (endTime - startTime);  // calculating the time to complete the thread
	}

	
}
