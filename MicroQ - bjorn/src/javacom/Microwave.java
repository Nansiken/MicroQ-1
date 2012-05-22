package javacom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Microwave {
	
	private int time = 0;
	private Timer timer;
	private int x, y;
	
	private String stringState = "00:00";
	private Color colorState = Color.GREEN;
	private int ovalSize = 28;
	private int offset = (500 - ovalSize*10) / 11;
	
	//private Image imageState = Toolkit.getDefaultToolkit().createImage("/res/green_circle.jpg");
	
	//private Image greenCircle = Toolkit.getDefaultToolkit().createImage("/res/green_circle.jpg");
	//private Image redCircle = Toolkit.getDefaultToolkit().createImage("/res/red_circle.jpg");
	
	public Microwave(int microNum) {
		x = offset + (ovalSize+offset)*(microNum%10);
		y = offset + (ovalSize+offset)*(microNum/10);
		timer = new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				colorState = Color.RED;
				time--;
				if (time == 0) {
					timer.stop();
					colorState = Color.GREEN;
				}
				if (time/600 < 1) {
					if (time%60 <= 9) stringState = "0" + time/60 + ":" + "0" + time%60;
					else stringState = "0" + time/60 + ":" + time%60;
				} else {
					if (time%60 <= 9) stringState = time/60 + ":" + "0" + time%60;
					else stringState = time/60 + ":" + time%60;
				}
				
			}
		});
		
		
	}
	
	public String getStringState() {
		return this.stringState;
	}
	
	public Color getColorState() {
		return this.colorState;
	}
	
	public void setTime(int i) {
		time = i;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void draw(Graphics bbg) {
		
		bbg.setColor(colorState);
		bbg.fillOval(x, y, ovalSize, ovalSize);
		bbg.drawString(stringState, x, y+ovalSize+12);
		
	}

	public int getTime() {
		return time;
	}
	
}
