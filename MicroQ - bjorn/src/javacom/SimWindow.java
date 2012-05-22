package javacom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;

public class SimWindow extends JFrame implements Runnable {

	private JPanel contentPane;
	
	private JLabel[] labelArr;
	public static int[] timeArr;
	public static Timer[] timerArr;
	private int numOfMicros;
	private int i;
	private Rectangle timerBounds = new Rectangle(15, 15, 60, 30);
	
	public static ConcurrentLinkedQueue<Integer> availableMicros;
	
	private ImageIcon greenCircle = new ImageIcon(SimWindow.class.getResource("/res/green_circle.jpg"));
	private ImageIcon redCircle = new ImageIcon(SimWindow.class.getResource("/res/red_circle.jpg"));
	
	/**
	 * Create the frame.
	 */
	public SimWindow(int micros) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		availableMicros = new ConcurrentLinkedQueue<Integer>();
		numOfMicros = micros;
		
		setupTimers();
	}
	
	private void setupTimers() {
		labelArr = new JLabel[numOfMicros];
		timeArr = new int[numOfMicros];
		timerArr = new Timer[numOfMicros];
		
		for (i = 0; i < numOfMicros; i++) {
			availableMicros.add(i);
			timeArr[i] = 0;
			
			if (timeArr[i]%60 <= 9) labelArr[i] = new JLabel(timeArr[i]/60 + ":" + 0 + timeArr[i]%60);
			else labelArr[i] = new JLabel(timeArr[i]/60 + ":" + timeArr[i]%60);
			
			labelArr[i].setBounds(timerBounds.x + 80*(i%5), timerBounds.y + 50*(i/5), 60, 30);
			labelArr[i].setIcon(greenCircle);
			labelArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			contentPane.add(labelArr[i]);
			
			timerArr[i] = new Timer(1000, new ActionListener() {
				int microNum = i;
				
				public void actionPerformed(ActionEvent e) {
					if (timeArr[microNum] == 0) {
						System.out.println("Countdown finished for micro " + (microNum+1));
						timerArr[microNum].stop();
						labelArr[microNum].setIcon(greenCircle);
					} else {
						labelArr[i].setIcon(redCircle);
						timeArr[microNum]--;
						
						if (timeArr[microNum]%60 <= 9) labelArr[microNum].setText(timeArr[microNum]/60 + ":" + 0 + timeArr[microNum]%60);
						else labelArr[microNum].setText(timeArr[microNum]/60 + ":" + timeArr[microNum]%60);
					}
					
				}
			});
			
		}
	}

	@Override
	public void run() {
		this.setVisible(true);
		this.setResizable(false);
		
		JLabel bajs = new JLabel("hejsan");
		contentPane.add(bajs);
		
		/*for (i = 0; i < numOfMicros; i++) {
			
			if (timeArr[i]%60 <= 9) labelArr[i] = new JLabel(timeArr[i]/60 + ":" + 0 + timeArr[i]%60);
			else labelArr[i] = new JLabel(timeArr[i]/60 + ":" + timeArr[i]%60);
			
			labelArr[i].setBounds(timerBounds.x + 80*(i%5), timerBounds.y + 50*(i/5), 60, 30);
			if(timeArr[i] <= 0) {
				labelArr[i].setIcon(greenCircle);
			}
			else {
				labelArr[i].setIcon(redCircle);
			}
			labelArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			contentPane.add(labelArr[i]);
			
			timerArr[i] = new Timer(1000, new ActionListener() {
				int microNum = i;
				
				public void actionPerformed(ActionEvent e) {
					if (timeArr[microNum] == 0) {
						System.out.println("Countdown finished for micro " + (microNum+1));
						timerArr[microNum].stop();
						labelArr[microNum].setIcon(greenCircle);
					}
					
					labelArr[i].setIcon(redCircle);
					timeArr[microNum]--;
					
					if (timeArr[microNum]%60 <= 9) labelArr[microNum].setText(timeArr[microNum]/60 + ":" + 0 + timeArr[microNum]%60);
					else labelArr[microNum].setText(timeArr[microNum]/60 + ":" + timeArr[microNum]%60);
					
					
				}
			});
			
		}*/
	}
}
