package Main;
import Entities.*;
import SharedRegions.*;
import genclass.GenericIO;

/**
 *
 *  General description:
 *      The Restaurant Problem - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class TheRestaurantMain {
    
    /**
     *  Program's Data
     */
    
    public static int nstudents = 7;                                            // number of Students that are going to the Restaurant
    public static int ncourses = 3;                                             // number of courses per Student
    public static enum ArrivalOrder {                                           // Order of Arrival Enum
        FIRST,
        MIDDLE,
        LAST;
    }
    
    /**
     *  Main method.
     *  @param args runtime arguments
     */
    public static void main(String[] args) {
        
        /* Repository, Entities & Shared Regions */
        
        GeneralRepository repo;                                                 // Restaurant's Repository
        Chef chef;                                                              // Chef of the Restaurant
        Waiter waiter;                                                          // Waiter of the Restaurant
        Student[] students = new Student[nstudents];                            // list of Students that are going to the Restaurant
        Kitchen kitchen;                                                        // Restaurant's Kitchen
        Bar bar;                                                                // Restaurant's Bar
        Table table;                                                            // Restaurant's Table
        
        /* Problem Initialization */
        
        repo = new GeneralRepository("logFile.txt");
        
        kitchen = new Kitchen(repo);
        bar = new Bar(repo);
        table = new Table(repo);
        
        chef = new Chef(kitchen, bar);
        waiter = new Waiter(kitchen, bar, table);
        for(int i=0; i<nstudents; i++) {
            students[i] = new Student(bar, table);
        }
        
        /* Start of the Simulation */
        
        chef.start();
        waiter.start();
        for(int i=0; i<nstudents; i++) {
            students[i].start();
        }
        
        /* Wait for the End of the Simulation */
        
        // ...
    }
}
