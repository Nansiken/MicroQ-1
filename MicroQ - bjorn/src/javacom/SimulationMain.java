package javacom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/** 
 * Main class for the game 
 */ 
public class SimulationMain extends JFrame 
{        
        static boolean isRunning = true; 
        int fps = 30; 
        public JFrame window = this;
        int windowWidth = 500; 
        int windowHeight = 500;
        
        static Microwave[] microwaves;
		static Thread simulation;
		public static ConcurrentLinkedQueue<Integer> availableMicros = new ConcurrentLinkedQueue<Integer>();
		static Semaphore setup_done = new Semaphore(0);
		private static boolean connected = true;
		private Thread simulationSetup;
		
        BufferedImage backBuffer; 
        Insets insets; 
        //InputHandler input; 
        
        int x = 0; 
        
        public static void main(String[] args) 
        { 
            SimulationMain simMain = new SimulationMain(); 
            simMain.run(); 
            System.exit(0); 
        } 
        
        /** 
         * This method starts the game and runs it in a loop 
         */ 
        public void run() 
        { 
                initialize(); 
                System.out.println("Init done...");
                try {
					setup_done.acquire();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                System.out.println("Setup done...");
                simulation.start();
                while(isRunning && connected) 
                { 
                        long time = System.currentTimeMillis(); 
                        
                        
                        update(); 
                        draw(); 
                        
                        //  delay for each frame  -   time it took for one frame 
                        time = (1000 / fps) - (System.currentTimeMillis() - time); 
                        
                        if (time > 0) 
                        { 
                                try 
                                { 
                                        Thread.sleep(time); 
                                } 
                                catch(Exception e){} 
                        } 
                }
                if(connected) {
                	JOptionPane.showMessageDialog(this, "Simulation done!");
                }
                else {
                	JOptionPane.showMessageDialog(this, "Connection lost!");
                }
                setVisible(false); 
        } 
        
        /** 
         * This method will set up everything need for the game to run 
         */ 
        void initialize() 
        { 
                setTitle("MicroQ"); 
                setSize(windowWidth, windowHeight); 
                setResizable(false); 
                setDefaultCloseOperation(EXIT_ON_CLOSE); 
                setVisible(true); 
                
                insets = getInsets(); 
                setSize(insets.left + windowWidth + insets.right, 
                                insets.top + windowHeight + insets.bottom); 
                
                backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB); 
                //input = new InputHandler(this);
                simulationSetup = new Thread(new GUI());
                simulationSetup.start();
        } 
        
        /** 
         * This method will check for input, move things 
         * around and check for win conditions, etc 
         */ 
        void update() 
        { 
        	/*
                if (input.isKeyDown(KeyEvent.VK_RIGHT)) 
                { 
                        x += 5; 
                } 
                if (input.isKeyDown(KeyEvent.VK_LEFT)) 
                { 
                        x -= 5; 
                }
               */
        } 
        
        /** 
         * This method will draw everything 
         */ 
        void draw() 
        {               
                Graphics g = getGraphics(); 
                
                Graphics bbg = backBuffer.getGraphics(); 
                
                bbg.setColor(Color.WHITE); 
                bbg.fillRect(0, 0, windowWidth, windowHeight); 
                
                for(int i = 0; i < microwaves.length; i++) {
                	microwaves[i].draw(bbg);
                }
                
                g.drawImage(backBuffer, insets.left, insets.top, this);
        }

		public static void setConnected(boolean b) {
			// TODO Auto-generated method stub
			connected = b;
		} 
} 