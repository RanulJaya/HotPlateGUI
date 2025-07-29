/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ranul
 */
public class Element implements Runnable{

    private List<Element> neigbour;
    private double currentTemp;
    public static double heatConstant;
    private boolean stopRequested;

    public Element(double currentTemp ) {
        
        this.currentTemp = currentTemp;
        this.neigbour = new ArrayList<>();
    }
    
    public synchronized double getTemperature()
    {
        return currentTemp;
    }
    
    public void start()
    {
        Thread t1 = new Thread(this);
        t1.start();
   }
    
    public void requestStop()
    {
    	stopRequested = true;
    }
    
    @Override
    public void run()
    {
		double averageTemps = 0;
		
		while (!stopRequested)
		{
			for (int i = 0; i < neigbour.size(); i++)
			{
				averageTemps += neigbour.get(i).getTemperature();
			}
			
			averageTemps /= neigbour.size() + 1;
			

			currentTemp += (averageTemps - currentTemp) * heatConstant;
			

			try {
				Thread.sleep(1000 / HotplateGUI.FPS);
			} catch (InterruptedException e) {
				
			}
		}
        
    }
    
    public void addNeighbour(Element element)
    {
        neigbour.add(element);
        
    }
    
    public void applyTemperature(double appliedTemp)
    {
        currentTemp += (appliedTemp - currentTemp) * HotplateGUI.sliderHotConst;
        heatConstant = HotplateGUI.sliderHotConst;
        
    }
    
}
