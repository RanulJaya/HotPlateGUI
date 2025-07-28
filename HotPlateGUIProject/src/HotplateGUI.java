/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author Ranul
 */
@SuppressWarnings("serial")
public class HotplateGUI extends JPanel implements ActionListener, Runnable, MouseListener{

   
	private JSlider slider;
    private JSlider slider1;
    private int width;
    private int height;
    private Timer time;
    private JLabel label;
    private DrawPanel panel;
    Element elements[][];
    Element element;
    JPanel jpanel;
    private static int xGet = -1;
    private static int yGet = -1;
    JPanel panel1;
    static double sliderHotConst;
	public final static int FPS = 60;
	private boolean mousePressed;
	public int xcoord;
	public int ycoord;
    

	
    public HotplateGUI() { 
        
        super();
        setLayout(new BorderLayout());
        panel = new DrawPanel();
        slider = new JSlider(0,1000,0);
        slider.setPaintTicks(true);

        slider.setMinorTickSpacing(100);
        slider.setMajorTickSpacing(10);
        
        elements = new Element[20][20];
        
        slider1 = new JSlider(SwingConstants.HORIZONTAL, 0,100,0);
        slider1.setMinorTickSpacing(0);
        slider1.setMajorTickSpacing(50);
        slider1.setPaintTicks(true);
        
        jpanel = new JPanel();
        panel1 = new JPanel();

        mousePressed = false;
        
       
        Hashtable label = new Hashtable();
        
        int i = 0;
        
        for (int iterator = 0; iterator <= 10; iterator++) {
            label.put(i, new JLabel("" + iterator));
            
            i+=100;
		}
        
        Hashtable label1 = new Hashtable();
        
        int j = 0;
        
	        for (int iterator = 0; iterator <= 2; iterator++) {
	            label1.put(j, new JLabel("" + j));
	            
	            j+=50;
			}
	       
        for (int k = 0; k < elements.length; k++ )
        {
        	for (int l = 0; l < elements.length; l++ )
        	{
        		this.element = new Element(0);
        		this.elements[k][l] = element;
        	}
        }
        
        for (int row = 0; row < elements.length; row++) 
        {	
        	 for (int col= 0; col < elements.length; col++) {
        		 
        		 if(row == 0)
        		 {
        			 elements[row][col].addNeighbour(elements[row + 1][col]);
        		 }
        		 
        		 else if(row == elements.length - 1)
        		 {
        			 elements[row][col].addNeighbour(elements[row  - 1][col]);
        		 }
        		 else
        		 {
        			 elements[row][col].addNeighbour(elements[row + 1][col]);
        			 elements[row][col].addNeighbour(elements[row - 1][col]);
        		 }
        		 
        		 if(col == 0) {
        			 elements[row][col].addNeighbour(elements[row][col + 1]);
        		 }
        		 
        		 else if(col == elements.length - 1)
        		 {
        			 elements[row][col].addNeighbour(elements[row][col - 1]);
        		 }
        		 
        		 else
        		 {
        			 elements[row][col].addNeighbour(elements[row][col + 1]);
        			 elements[row][col].addNeighbour(elements[row][col - 1]);
        		 }
     		}
		}
	        
        
        for(int s = 0; s < elements.length; s++) {
        	for(int t = 0; t < elements.length; t++)
        	{
                elements[s][t].start();
        	}
		}
        
	     
        JLabel labeling = new JLabel();
        labeling.setText("Temperature: " + slider.getValue());
        labeling.setLayout(new GridBagLayout());
        labeling.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        JLabel labeling1 = new JLabel();
        labeling1.setText("Heat Constant: " + slider.getValue());
        labeling1.setLayout(new GridBagLayout());
        labeling1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        slider.addChangeListener((ChangeEvent e) -> {
       	 labeling.setText("Temperature: " + slider.getValue());

        });
        
        //listener for mouse click and release
        panel.addMouseListener(this);
        
        slider1.addChangeListener((ChangeEvent e) -> {
        	labeling1.setText("Hot Constant: " + (double) slider1.getValue()/100);
        	sliderHotConst = (double) slider1.getValue()/100;
        });
        
        slider.setLabelTable(label);
        slider.setPaintLabels(true);
        
        slider1.setLabelTable(label1);
        slider1.setPaintLabels(true);

        sliderHotConst = (double) slider1.getValue()/100;
        
        add(panel, BorderLayout.NORTH);
        add(labeling, BorderLayout.WEST);
        
        jpanel.add(slider, BorderLayout.NORTH);	
        add(jpanel, BorderLayout.CENTER);
        panel1.add(labeling1, BorderLayout.NORTH);
        panel1.add(slider1, BorderLayout.SOUTH);
  
        add(panel1, BorderLayout.SOUTH);
        
        time = new Timer(20, this);

        time.start();
        
    }
    
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("HOT PLATE GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HotplateGUI());
        frame.pack();  //pack frame
        frame.setVisible(true);     
    }

    public class DrawPanel extends JPanel
    {
        
        public DrawPanel()
        {
             setPreferredSize(new Dimension(400,400));
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
        
            width = this.getWidth();
            height = this.getHeight();
        
            int rowCount = 20;
            int colCount = 20;
        
            int widthBox = (width / colCount);
            int heightBox = height / rowCount;
        
            int x = (width - (colCount * widthBox));
            int y = (height - (rowCount * heightBox));
            
            
            //draw boxes and paint the background of the panel
            for(int row = 0; row < rowCount; row++)
            {
                for(int col = 0; col < colCount; col++)
                {
                	//if the value is more than 255, then rgb for red should stay at 255
                	int changeToRed = 255;
                	
                	if((int) elements[row][col].getTemperature() > 255)
                	{
                		changeToRed = 255;
                	}
                	else
                	{
                		changeToRed = (int) elements[row][col].getTemperature();
                	}
                    g.setColor(new Color(changeToRed,  0, 255 - changeToRed));
                    g.fillRect((row * widthBox)  ,(col * heightBox) , widthBox  , heightBox);            
                    

                }
            } 
            
            
        
            for(int row = 0; row < rowCount; row++)
            {
                for(int col = 0; col < colCount; col++)
                {
                    g.setColor(new Color(255 ,  0, 0));
                    g.drawRect(x + (row * widthBox)  , y + (col * heightBox) , widthBox  , heightBox);        
                    
                }
            }
            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        Object source = e.getSource();
        
        
        if(e.getSource() == time)
        {
            panel.repaint();
        }
        
    }



	public boolean isMousePressed() {
		return mousePressed;
	}



	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		// run the thread to apply picked square  
		while(isMousePressed())
		{	
			elements[xGet][yGet].applyTemperature((double)slider.getValue());	
			
			try {
				Thread.sleep(100 / HotplateGUI.FPS);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		//coordinates of the x and y of the picked box
		xGet = e.getX()/20;
		yGet = e.getY()/20;
		setMousePressed(true);
		
		//run the thread
		if(isMousePressed())
		{
	        Thread runTask = new Thread(this);
			runTask.start();
		}
			
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		setMousePressed(false);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
