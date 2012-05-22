package javacom;

import com.ericsson.otp.erlang.OtpErlangPid;
/**
 * 
 * @author Marcus Enderskog
 *
 */
public class Student {

	/*
	 * @
	 */
	private OtpErlangPid PID;
	public int waitingTime;
	public int heatingTime;
	
	public Student(OtpErlangPid PID) {
		this.PID = PID;
		this.waitingTime = Simulation.waitingTime;
		heatingTime = (int) (Simulation.heatingTimeMin + 
				Math.random()*(Simulation.heatingTimeMax - Simulation.heatingTimeMin));
		
	}
	
	public OtpErlangPid getPID() {
		return PID;
	}

	public void setPID(OtpErlangPid pID2) {
		PID = pID2;
	}

	public long getHeatingTime() {
		// TODO Auto-generated method stub
		return heatingTime;
	}

}
