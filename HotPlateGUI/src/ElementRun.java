/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC1
 */
public class ElementRun implements Runnable{
	
    private double currentTemp;
    private boolean stopRequested;
    public static double heatConstant;
    private List<ElementRun> neigbour;
    double averageTemps = 130;
    double currentNewTemp;
    

    
	public ElementRun(double currentTemp, String elementName) {
		
        this.currentTemp = currentTemp;
        this.neigbour = new ArrayList<>();
        heatConstant = 1.9;
        this.elementName = elementName;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
        String element = null;
        stopRequested = false;
        
        while (!stopRequested) {
            
            
            synchronized (this) {
	            
	            currentTemp += (averageTemps - getTemperature()) * heatConstant;
	            currentTemp += (averageTemps - getTemperature()) * heatConstant;
	            
            }
	        
            requestStop();
	            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ElementRun.class.getName()).log(Level.SEVERE, null, ex);
            }    
          
        }
	}
	
    public synchronized double getTemperature()
    {
        return currentTemp;
    }
    
    public void requestStop()
    {    
        ElementRun element1 = new ElementRun(getTemperature(), "A");
        ElementRun element2 = new ElementRun(neigbour.get(0).getTemperature(), "B");
        
       if(Math.abs(element1.getTemperature() - element2.getTemperature()) < 0.01)
       {
           stopRequested  = true;
       }
    }
    
    
    public void addNeighbour(ElementRun element)
    {
        neigbour.add(element);
        
    }
    
    
    public static void main(String[] args) {
        
        ElementRun e1 = new ElementRun(300, "A");
        ElementRun e2 = new ElementRun(0, "B");
        
        e1.addNeighbour(e2);
        e2.addNeighbour(e1);
        
        e1.start();
        e2.start();
        
        while((Math.abs(e1.getTemperature() - e2.getTemperature()) > 0.01)) {

            System.out.println("Element A" + ": " + e1.getTemperature());
            System.out.println("Element B" + ": " + e2.getTemperature());
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ElementRun.class.getName()).log(Level.SEVERE, null, ex);
            }    
          
            
        }
        
        System.out.println("Values are equal!");

    }
    
    public void start()
    {
        Thread t1 = new Thread(this);
        t1.start();    
   }
    
}
