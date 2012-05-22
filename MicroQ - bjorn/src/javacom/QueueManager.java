package javacom;

import java.util.concurrent.ConcurrentLinkedQueue;



public class QueueManager {
	
	public static String erlNode;
	public static String javaNode;
	public static ConcurrentLinkedQueue<Student> Queue;
	//public static ArrayList<Thread> runningThreads = new ArrayList<Thread>();
	
	public QueueManager() {
		Queue = new ConcurrentLinkedQueue<Student>();
	}
	
	public static void addStudent(Student s)
	{
		Queue.add(s);
		Simulation.queue_sem.release();
	}
	
	public void grabMicrowave()
	{
		System.out.println("Queue size: " + Queue.size());
		Student s = Queue.remove();
		Thread t = new Thread(new Communicator(s));
		t.start();
		//runningThreads.add(t);
	}

}
