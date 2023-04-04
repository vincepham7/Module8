//Vincent Pham 04/02/2023

package module8;
import java.util.Random;

public class ArraySum {
	  private static final int arraySize = 200_000_000; // size of array to sum
	  private static final int numOfThreads = 6; // number of threads to use

	  public static void main(String[] args) {
	    int[] array = new int[arraySize]; // create array of specified size
	    Random random = new Random(); // create a random number generator
	    for (int i = 0; i < arraySize; i++) {
	      array[i] = random.nextInt(10) + 1; // generate a random number between 1 and 10 and store it in the array
	    }

	    long start = System.currentTimeMillis(); // record current time
	    int singleThreadSum = singleThreadSum(array); // sum the array using a single thread
	    long singleThreadTime = System.currentTimeMillis() - start; // calculate time taken

	    start = System.currentTimeMillis(); // record current time again
	    int multiThreadSum = multiThreadSum(array); // sum the array using multiple threads
	    long multiThreadTime = System.currentTimeMillis() - start; // calculate time taken

	    System.out.println("Single thread sum: " + singleThreadSum + " Time: " + singleThreadTime + "ms");
	    System.out.println("Multi-thread sum: " + multiThreadSum + " Time: " + multiThreadTime + "ms");
	  }

	  // sums the given array using a single thread
	  private static int singleThreadSum(int[] array) {
	    int sum = 0;
	    for (int i = 0; i < arraySize; i++) {
	      sum += array[i]; // add current element to sum
	    }
	    return sum;
	  }

	  // sums the given array using multiple threads
	  private static int multiThreadSum(int[] array) {
	    int[] partialSums = new int[numOfThreads]; // create array to hold partial sums
	    Thread[] threads = new Thread[numOfThreads]; // create array to hold threads

	    
	    for (int i = 0; i < numOfThreads; i++) {
	      final int start = i * (arraySize / numOfThreads); // calculate start index for current thread
	      final int end = (i == numOfThreads - 1) ? arraySize : (i + 1) * (arraySize / numOfThreads); // calculate end index for current thread
	      final int threadIndex = i; // capture current thread index in final variable to use in lambda

	      threads[i] = new Thread(() -> { // create a new thread
	        int sum = 0;
	        for (int j = start; j < end; j++) {
	          sum += array[j]; // add current element to sum
	        }
	        partialSums[threadIndex] = sum; // store partial sum in array
	      });

	      threads[i].start(); // start current thread
	      try {
	        Thread.sleep(500); // sleep the thread for 500 milliseconds
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }

	    // wait for all threads to complete
	    for (Thread thread : threads) {
	      try {
	        thread.join(); 
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }

	    // sum up partial sums
	    int sum = 0;
	    for (int partialSum : partialSums) {
	      sum += partialSum; 
	    }
	    return sum; 
	  }
	}
