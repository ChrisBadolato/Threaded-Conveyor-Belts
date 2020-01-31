/* Name:Christopher Badolato
Course: CNT 4714 Spring 2020 Assignment title: 
Project 2 – Multi-threaded programming in Java Date:  
February 9, 2020
Class:  Project2CBadolato_Conveyor*/ 
package project2cbadolato_conveyor;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Project2CBadolato_Conveyor {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException{
        // TODO code application logic here
        int numberOfRoutingStations, i;
        
        File file = new File("src\\project2cbadolato_conveyor\\config.txt");
        Scanner newScan = new Scanner(file);
        numberOfRoutingStations = newScan.nextInt();
            
       
            //we need to create object arrays for stations and conveyors
            //As well as an array for each stations workload
        Conveyor[] conveyors = new Conveyor[numberOfRoutingStations];
        Stations[] stations = new Stations[numberOfRoutingStations]; 
        int[] workPerStation  = new int[numberOfRoutingStations];   
         
            //construct conveyor objects
            
        for(i = 0; i < numberOfRoutingStations; i++){
            conveyors[i] = new Conveyor(i);
        }  
            //since we know how many threads we are ceating we can use a fixed Thread pool
            //https://howtodoinjava.com/java/multi-threading/java-fixed-size-thread-pool-executor-example/           
        ExecutorService stationThreads = Executors.newFixedThreadPool(numberOfRoutingStations);
           
        for(i = 0; i < numberOfRoutingStations; i++){         
            workPerStation[i] = newScan.nextInt();   
                //create new station object and store it on the array: work per station, station number, total number of stations.             
            stations[i] = new Stations(workPerStation[i], i, numberOfRoutingStations);
                //we need to set our input and output conveyors for the new station
            stations[i].setInputConveyor(conveyors[stations[i].getInputConveyorNumber()]);
            stations[i].setOutputConveyor(conveyors[stations[i].getOutputConveyorNumber()]);
                //Now, we have set up the currnent station with its works per station, station number, total number of stations,
                //input conveyor and output conveyer we can start up the station.
                stationThreads.execute(stations[i]);
        }
        stationThreads.shutdown();
    } 

}
