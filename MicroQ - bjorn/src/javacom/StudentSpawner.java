package javacom;

import com.ericsson.otp.erlang.OtpNode;




public class StudentSpawner implements Runnable{
	
	private int spawnRate;
	public int getSpawnRate() {
		return spawnRate;
	}

	public int getSwitchRate() {
		return switchRate;
	}

	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public static int getIter() {
		return iter;
	}

	public OtpNode getJavaNode() {
		return javaNode;
	}


	private int switchRate;
	private int numberOfStudents;
	public static int iter;
	private OtpNode javaNode;
	
	
	public StudentSpawner(int sRate, int switchRate, int numberOfStudents, OtpNode jNode){
		iter = 0;
		spawnRate = sRate;
		this.switchRate = switchRate;
		this.numberOfStudents = numberOfStudents;
		javaNode = jNode;
	}
	
	public boolean randomSpawn(){
		if((Math.random()*100) <= spawnRate){
			return true;
		}
		return false;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(iter < numberOfStudents)
		{
			if(randomSpawn())
			{
				System.out.println("Number of students " + numberOfStudents);
				System.out.println("Spawning student " + iter);
				Thread t = new Thread(new Communicator(switchRate));
				t.start();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
