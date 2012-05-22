package javacom;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import com.ericsson.otp.erlang.*;

public class Communicator implements Runnable {
	
	private static String tID = ""+Thread.currentThread().getId(); 
	static OtpMbox mail = Simulation.javaNode.createMbox(tID);
	private boolean newClient = false;
	private OtpErlangInt sRate;
	private OtpErlangPid pid;
	private OtpErlangAtom tid;
	private OtpErlangAtom atom;
	private Student s;
	
	
	public Communicator(int switchRate)
	{
		sRate = new OtpErlangInt(switchRate);
		newClient = true;
		atom = new OtpErlangAtom("new_client");
	}
	public boolean isNewClient() {
		return newClient;
	}
	public OtpErlangInt getsRate() {
		return sRate;
	}
	public Communicator(Student s)
	{
		this.s = s;
		atom = new OtpErlangAtom("ready");
		pid = s.getPID();
	}

	
	/*
	public static void newStudent(int switchRate, ComThread t, long tid)
	{
		tidMap.put("" + tid, t);
		sendMessage(new OtpErlangLong(tid), new OtpErlangAtom("new_client"), new OtpErlangInt(switchRate));
	}
		
		*/


	public OtpErlangPid getPid() {
		return pid;
	}
	public OtpErlangAtom getAtom() {
		return atom;
	}
	public Student getS() {
		return s;
	}
	public static void sendMessage(OtpErlangAtom tid, OtpErlangAtom atom, OtpErlangObject obj) {
		OtpErlangObject[] message = new OtpErlangObject[3];
		message[0] = tid;
		message[1] = atom;
		message[2] = obj;	
		mail.send(Simulation.erlNodePID, new OtpErlangTuple(message));
		
	}
	
	@Override
	public void run() {
		
		
		
		/*
		while(true)
		{
			OtpErlangTuple tuple = null;
			OtpErlangObject msg;
			try {
				msg = mbox.receive();
				tuple = (OtpErlangTuple) msg;
			} catch (OtpErlangExit e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OtpErlangDecodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ComThread t =  tidMap.get(tuple.elementAt(0));
			t.receive(msg);
			
		}
		*/
		OtpErlangObject recMsg;
		
		OtpErlangTuple tuple = null;
		if(newClient)
		{	
			sendMessage(new OtpErlangAtom(tID), atom, sRate);
			System.out.println("new_client message sent...");
			try {
				tuple = (OtpErlangTuple) mail.receive();
				System.out.println("new_client created with PID " + tuple.elementAt(1).toString());
			}
			catch (OtpErlangExit e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (OtpErlangDecodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			StudentSpawner.iter += 1;
			QueueManager.addStudent(new Student((OtpErlangPid)tuple.elementAt(1)));
		}
		else
		{
			sendMessage(new OtpErlangAtom(tID), atom, pid);
			System.out.println("Ready message sent...");
			try {
				tuple = (OtpErlangTuple) mail.receive(15000);
			}
			catch (OtpErlangExit e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (OtpErlangDecodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			
			
			if(tuple == null) {
				System.out.println("mail receive timeout...");
				Simulation.microwave_sem.release();
				QueueManager.addStudent(s);
			}
			else {
				System.out.println("student is ready");
				int i = SimulationMain.availableMicros.remove().intValue();
				SimulationMain.microwaves[i].setTime((int) (s.getHeatingTime()/1000));
				SimulationMain.microwaves[i].getTimer().start();
				try {
					Thread.sleep(s.getHeatingTime());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sendMessage(new OtpErlangAtom(tID), new OtpErlangAtom("kill"), pid);
				while(SimulationMain.microwaves[i].getTime() > 0) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Stuck in loop...");
				}
				SimulationMain.availableMicros.add(i);
				Simulation.studentsCompleted++;
				Simulation.microwave_sem.release();
				if(Simulation.studentsCompleted >= Simulation.numberOfStudents)
				{
					Simulation.queue_sem.release();
				}
				System.out.println("Students completed: " + Simulation.studentsCompleted + ", when queue has " + 
						Simulation.queue_sem.availablePermits() + " permits and microwave sem has " +
						Simulation.microwave_sem.availablePermits() + " permits...");
			}
		}
		
		// TODO Auto-generated method stub
		
	}

}
