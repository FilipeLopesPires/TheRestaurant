package SharedRegions;
import Assets.Meals;
import Main.*;
import Entities.*;
import java.util.Stack;
import genclass.GenericIO;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 *  General Description:
 *      Definition of the Table Shared Region - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Table {
    
    /**
     *  Internal Data
     */
    
    private static GeneralRepository repo;                                      // Restaurant's Repository
    private Stack<Integer> toBeSaluted;                                         // stack of Students not yet Saluted
    private volatile int chosenStudents;                                                 // tells the First Student how many Students have chosen their courses
    private boolean readyToGetThePad;                                           // tells the First Student if the Waiter is ready to recieve the order
    private int deliveredPortions;                                              // tells the Students how many portions have been delivered
    private int currentCourse;                                                  // tells the Students which course are they currently having
   
    
    /**
     *  Constructor
     *  Allocates a new Table Shared Region.
     */
    public Table(GeneralRepository repo){
       
        this.repo = repo;
        toBeSaluted = new Stack<>();
        chosenStudents = 0;
        readyToGetThePad = false;
        deliveredPortions = 0;
        currentCourse = 0;
    }
    
    /**
     *  Table's Methods
     */
    
    /**
     *  Used by Student to define his inital state of GTTR.
     */
    public synchronized void walkABit() {
        repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
    }
    
    /**
     *  Used by Student to sit at the table.
     */
    public synchronized void enter() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.TASATT)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        toBeSaluted.push(((Student)Thread.currentThread()).getID());
        this.notifyAll();                                                       // sinchronization of Student entering and Waiter going to salute him
        
        while(!toBeSaluted.isEmpty()) {
            try {
                this.wait();                                                    // Student blocks while Waiter does not salute him
            } catch (InterruptedException ie) {}
        }
    }
    /**
     *  Used by Waiter to salute the arriving Students.
     */
    public synchronized void saluteTheStudent() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.PTM) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        
        while(toBeSaluted.isEmpty()) {
            try {
                this.wait();                                                    // sinchronization of Student entering and Waiter going to salute him
            } catch (InterruptedException ie) {}
        }
        
        while(!toBeSaluted.isEmpty()) {
            toBeSaluted.pop();
        }
        this.notifyAll();                                                       // Waiter notifies Students when he salutes them
    }
    /**
     *  Used by Student to 
     */
    public synchronized void readTheMenu() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.STC)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        try {
            Thread.sleep((int) (10 * Math.random ()));                          // simulates time of reading the menu
        } catch (Exception e) {}
    }
    /** 
     *  Used by Student to 
     */
    public synchronized void informCompanion() {
        try {
            Thread.sleep((int) (10 * Math.random ()));                          // simulates time of informing the companion
        } catch (Exception e) {}
        
        chosenStudents++;
        this.notifyAll();                                                       // Student notifies First Student of his choice
        
    }
    /**
     *  Used by Student to 
     *  @return 
     */
    public synchronized boolean hasEverybodyChosen() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.OTO)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        if(chosenStudents == TheRestaurantMain.nstudents-1) {
            return true;
        }
        return false;
    }
    /**
     *  Used by Student to 
     */
    public synchronized void prepareTheOrder() {
       if(!hasEverybodyChosen())
        try {
            this.wait();                                                        // First Student waits while others choose their courses
        } catch (InterruptedException ie) {}
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void getThePad() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.TTO) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        readyToGetThePad = true;
        this.notifyAll();                                                       // sinchronization of Waiter going to the Table and First Student describing the order
       
    }
    /**
     *  Used by Student to 
     */
    public synchronized void describeTheOrder() {
        while(!readyToGetThePad) {
            try {
                this.wait();                                                    // sinchronization of Waiter going to the Table and First Student describing the order
            } catch (InterruptedException ie) {}
        }
        
        try {
            Thread.sleep((int) (10 * Math.random ()));                          // simulates time of describing the order to the Waiter
        } catch (Exception e) {}
    }
    /**
     *  Used by Student to 
     */
    public synchronized void joinTheTalk() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.CWC)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        while(deliveredPortions < TheRestaurantMain.nstudents) {
            try {
                this.wait();                                                    // Students block while not all of them have been sirved
            } catch (InterruptedException ie) {}
        }
        
        if(currentCourse == TheRestaurantMain.ncourses) {
            try {
                this.wait();                                                    // tmp
            } catch (InterruptedException ie) {}
        }
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void deliverPortion() {
       //System.out.println("Delivering portion");
        deliveredPortions++;
        this.notifyAll();                                                       // Waiter notifies the Students that another portion has been delivered
    }
    /**
     *  Used by Student to 
     */
    public synchronized void startEating() {
        currentCourse++;
        while(currentCourse != 1 && deliveredPortions < TheRestaurantMain.nstudents*currentCourse) {
            try {
                this.wait();                                                    // Students block while not all of them have been sirved of the next course
            } catch (InterruptedException ie) {}
        }
        
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.ETM)) {
            repo.updateAllStudentStates(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        // simulates time of eating the meal
        } catch (Exception e) {}
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void endEating() {
        try {
            Thread.sleep((int) (10 * Math.random ()));                          // simulates time of putting the silverware down
        } catch (Exception e) {}
        
    }
    /**
     *  Used by Student to 
     *  @return 
     */
    public synchronized boolean hasEverybodyFinished() {
        
        return false;
    }
    /**
     *  Used by Student to
     */
    public synchronized void signalTheWaiter() {
        
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void presentTheBill() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.RP) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void shouldHaveArrivedEarlier() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void honorTheBill() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.PTB)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void exit() {
        
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.GH)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
    }
    
    /**
     *  Auxiliar Methods
     */
    
}
