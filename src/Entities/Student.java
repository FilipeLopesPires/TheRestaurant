package Entities;

import Main.*;
import SharedRegions.*;

/**
 *  General Description: 
 *      Definition of the Student Thread - monitor-based solution.
 *
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Student extends Thread {

    /**
     *  Internal Data
     */
    
    public enum StudentState {                                                  // Internal State Enum
        GTTR,
        TASATT,
        STC,
        OTO,
        CWC,
        ETM,
        PTB,
        GH;
    }

    private static Bar bar;                                                     // Bar Shared Region Access Point
    private static Table table;                                                 // Table Shared Region Access Point
    private static int nstudents = 0;                                           // Number of created Student Thread objects (to define the ID)
    private final int ID;                                                       // Student Thread's ID
    private volatile StudentState studentState;                                 // Student Thread's State
    private TheRestaurantMain.ArrivalOrder arrivedIn;                           // Student Thread's place of arrival

    /**
     *  Constructor Allocates a new Student Thread object, incrementing the static variable nstudents with one unit and defining the Student Thread ID.
     *
     *  @param bar shared region object for the Student Thread to access the Bar
     *  @param table shared region object for the Student Thread to access the Table
     */
    public Student(Bar bar, Table table) {
        this.bar = bar;
        this.table = table;
        studentState = StudentState.GTTR;
        nstudents++;
        ID = nstudents;
    }

    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Student Thread.
     */
    @Override
    public void run() {
        walkABit();                                                             // GTTR
        arrivedIn = bar.enter();
        table.enter();                                                          // TASATT
        table.readTheMenu();                                                    // STC  
        if (arrivedIn == TheRestaurantMain.ArrivalOrder.FIRST) {
            while (!table.hasEverybodyChosen()) {                               // OTO
                table.prepareTheOrder();
            }
            bar.callTheWaiter();
            table.describeTheOrder();
            table.joinTheTalk();                                                // CWC
        } else {
            table.informCompanion();
            table.joinTheTalk();                                                // CWC
        }
        
        for (int c = 0; c < TheRestaurantMain.ncourses; c++) {
            table.startEating(c + 1);                                           // ETM
        }
        table.endEating();                                                      // CWC
        
        if (table.hasEverybodyFinished()) {
            bar.signalTheWaiter();
        }
        
        if (arrivedIn == TheRestaurantMain.ArrivalOrder.LAST) {
            table.shouldHaveArrivedEarlier();                                   // PTB
            table.honorTheBill();
        }
        table.exit();                                                           // GH
    }

    /**
     *  Student's Methods
     */
    
    /**
     *  Simulates the travel time between Student's current position and final destination, the Restaurant.
     */
    private void walkABit() {
        table.walkABit();
        try {
            Thread.sleep((int) (100 * Math.random()));
        } catch (Exception e) {
        }
    }

    /**
     *  Auxiliar methods
     */
    
    /**
     *  Returns the ID of the Student Thread.
     *
     *  @return int variable holding the Student's ID.
     */
    public int getID() {
        return ID;
    }

    /**
     *  Returns the current state of the Student Thread.
     *
     *  @return StudentState variable holding the Student's state.
     */
    public StudentState getStudentState() {
        return studentState;
    }
    
    /**
     *  Returns the place of arrival of the Student.
     * 
     *  @return element from the ArrivalOrder enumerate holding the place of arrival of the Student.
     */
    public TheRestaurantMain.ArrivalOrder getArrivalOrder(){
        return arrivedIn;
    }

    /**
     *  Sets a new state for the Student Thread if current state is different from the new state. Returns true if state is changed, false if not.
     *
     *  @param newStudentState State to replace the current Thread's state.
     *  @return boolean variable holding the result of the operation.
     */
    public boolean setStudentState(StudentState newStudentState) {
        if (studentState != newStudentState) {
            studentState = newStudentState;
            return true;
        }
        return false;
    }
}
