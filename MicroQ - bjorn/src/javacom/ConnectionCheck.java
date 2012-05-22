package javacom;

public class ConnectionCheck implements Runnable {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(Simulation.javaNode.ping(Simulation.erlNodeName, 2000)) {			
		}
		SimulationMain.setConnected(false);
	}

}
