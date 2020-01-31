/* Name:Christopher Badolato
Course: CNT 4714 Spring 2020 Assignment title: 
Project 2 – Multi-threaded programming in Java Date:  
February 9, 2020
Class:  Conveyor
*/ 
package project2cbadolato_conveyor;

import java.util.concurrent.locks.ReentrantLock;


public class Conveyor {
    int conveyorNumber;
    
    public ReentrantLock accessLock = new ReentrantLock();
   /*     //is the conveyor occupied?
    boolean busy = false;
*/
        //conveyor constructor.
    public Conveyor(int conveyorNumber){
        this.conveyorNumber = conveyorNumber;
    }
    
    public void input(int stationNumber){
        System.out.println("Station " + stationNumber + ": successfully moves packages on input conveyor " + this.conveyorNumber);
    }

    public void output(int stationNum) {	
        System.out.println("Station " + stationNum + ": successfully moves packages on output conveyor " + this.conveyorNumber);
    }
}
