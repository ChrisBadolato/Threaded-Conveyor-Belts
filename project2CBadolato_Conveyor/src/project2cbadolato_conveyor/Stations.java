/* Name:Christopher Badolato
Course: CNT 4714 Spring 2020 Assignment title: 
Project 2 – Multi-threaded programming in Java Date:  
February 9, 2020
Class:  Stations
*/ 
package project2cbadolato_conveyor;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Stations implements Runnable {
    private final int maxNumberOfStations;
    private final int stationNumber;
    private int workPerStation = 0; 
        //each station has 2 conveyors, one input, one output.
    private int inputConveyorNumber;
    private int outputConveyorNumber;
    private Conveyor inputConveyor;
    private Conveyor outputConveyor;
    
    public Stations(int workPerStation, int stationNumber, int maxNumberOfStations){
        this.stationNumber = stationNumber;
        this.workPerStation = workPerStation;
        this.maxNumberOfStations = maxNumberOfStations;
        this.setInputConveyorNumber();
        this.setOutputConveyorNumber();
        System.out.println("Station " + stationNumber + ": Work per Station set. Station " + this.stationNumber + " has " + this.workPerStation + " packages to move");

    }
        
    @Override
    public void run() {
        while(workPerStation != 0){
                //If tryLock returns True, grant access to the conveyor
            if(inputConveyor.accessLock.tryLock()){
                System.out.println("Station " + this.stationNumber + ": granted access to input conveyor: " + this.inputConveyorNumber);
                if(outputConveyor.accessLock.tryLock()){
                        //both conveyors are not being used, therefore this station may do work.
                    System.out.println("Station " + this.stationNumber + ": granted access to output conveyor: " + this.outputConveyorNumber);
                    doWork();
                }
                else{
                    //the outputConveyor on this station is currently being used, therefore we cannot access this station at the moment
                    //we need to free up the inputConveyor just in case an adjacent station is 
                    inputConveyor.accessLock.unlock();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Stations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    //
                if(inputConveyor.accessLock.isHeldByCurrentThread()){
                    System.out.println("Station " + this.stationNumber + ": released access to conveyor: " + this.inputConveyorNumber);
                    inputConveyor.accessLock.unlock();
                }
                if(outputConveyor.accessLock.isHeldByCurrentThread()){
                    System.out.println("Station " + this.stationNumber + ": released access to conveyor: " + this.outputConveyorNumber);
                    outputConveyor.accessLock.unlock();
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Stations.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println(); 
        System.out.println("Station " + stationNumber + ": Workload successfully completed!");
        System.out.println();
    }
    
    public void doWork() 
    {	
            //set up current station with inpput and output connectors.
            //decrement the work per station.
        this.inputConveyor.input(this.stationNumber);
        this.outputConveyor.output(this.stationNumber);
        this.workPerStation--; 
        System.out.println("Station " + this.stationNumber + ": has " + this.workPerStation + " packages left to move");
    }
        //setters for Conveyor objects.
    public void setInputConveyor(Conveyor inputConveyor){
        this.inputConveyor = inputConveyor;
    }
    public void setOutputConveyor(Conveyor outputConveyor){
        this.outputConveyor = outputConveyor;
    }
        //getters for conveyor objects
    public int getInputConveyorNumber(){
        return this.inputConveyorNumber;
    }
    public int getOutputConveyorNumber(){
        return this.outputConveyorNumber;
    }
        //setters for Conveyors numbers.
    public void setInputConveyorNumber(){
        if(stationNumber == 0){
            this.inputConveyorNumber = 0;
        }
        else{
                //set the input conveyor number as 1 less than the current station number
            this.inputConveyorNumber = this.stationNumber - 1;
        }
        System.out.println("Station " + stationNumber + ": Input set to conveyor " + this.inputConveyorNumber);
    }
        //if we are on our last station, the conveyor output will be the last conveyor and first conveyor
    public void setOutputConveyorNumber(){
        if(stationNumber == 0){
            this.outputConveyorNumber = this.maxNumberOfStations - 1;
        }
        else{
            this.outputConveyorNumber = this.stationNumber;
        }
        System.out.println("Station " + stationNumber + ": Output set to conveyor " + this.outputConveyorNumber);
    }
    
}
