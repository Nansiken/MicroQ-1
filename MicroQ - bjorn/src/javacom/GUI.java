package javacom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame implements Runnable {
	JPanel jp = new JPanel();
	JLabel jl = new JLabel();
	
	JTextField srfield = new JTextField("50", 10);
	JTextField swfield = new JTextField("50", 10);
	JTextField stfield = new JTextField("50", 10);
	JTextField htminfield = new JTextField("10", 10);
	JTextField htmaxfield = new JTextField("300", 10);
	JTextField wtfield = new JTextField("15", 10);
	JTextField mcfield = new JTextField("4", 10);
	
	JButton button = new JButton("Start simulation");
	
	public static Thread simWin;
	
	public GUI(){
		setTitle("Micro-Q Simulator");
		setVisible(true);
		setSize(400,200);
		setDefaultCloseOperation(3);
		
		jp.add(srfield);
		jp.add(swfield);
		jp.add(stfield);
		jp.add(htminfield);
		jp.add(htmaxfield);
		jp.add(wtfield);
		jp.add(mcfield);
		
		jp.add(button);
		
		
		jp.add(jl);
		add(jp);
	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int sR = Integer.parseInt(srfield.getText());
				int swR = Integer.parseInt(swfield.getText());
				int nOS = Integer.parseInt(stfield.getText());
				int hTMax = Integer.parseInt(htmaxfield.getText())*1000;
				int hTMin = Integer.parseInt(htminfield.getText())*1000;
				int wT = Integer.parseInt(wtfield.getText())*1000;
				int mW = Integer.parseInt(mcfield.getText());
				GUI.this.setVisible(false);
				SimulationMain.microwaves = new Microwave[mW];
				for(int i = 0; i < mW; i++) {
					SimulationMain.microwaves[i] = new Microwave(i);
					SimulationMain.availableMicros.add(i);
				}
				SimulationMain.simulation = new Thread(new Simulation(sR, nOS, hTMax, hTMin, wT, mW, swR));
				SimulationMain.setup_done.release();
				/*
				simWin = new Thread(new SimWindow(mW));
				GUI.this.setVisible(false);
				simWin.start();
				*/
				
			}
				
		});
		
		
	}

}
	
	




