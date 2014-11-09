package com.qdapps.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestExcuter {

	public class MyRunnable implements Runnable {
		private final long countUntil;
		private final String name;

		MyRunnable(long countUntil) {
			this.countUntil = countUntil;
			this.name = countUntil-100 +"";
		}

		@Override
		public void run() {
			long sum = 0;
			while (true){
			for (long i = 1; i < countUntil*10; i++) {
				//sum += i;
				
			}
			System.out.println(sum + "@Thread " +name);
			}
			
		}
	}

	private static final int NTHREDS = 3;

	public static void main(String[] args)throws Exception {
		TestExcuter te = new TestExcuter();
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		for (int i = 0; i < 50; i++) {
			Runnable worker = te.new MyRunnable(100 + i);
			executor.execute(worker);
		}
		// This will make the executor accept no new threads
		// and finish all existing threads in the queue
		executor.shutdown();
		// Wait until all threads are finish
		executor.awaitTermination(50, TimeUnit.SECONDS);
		System.out.println("Finished all threads");
	}
}
