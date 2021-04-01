package SharedRegions;

import Main.*;
import Entities.*;
import java.util.*;

/**
 * 
 *  General Description:
 *      Definition of the Table Shared Region - monitor-based solution.
 * 
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Table {

    /**
     *  Internal Data
     */
    
    private static GeneralRepository repo;                                      // Restaurant's Repository
    private volatile Stack<Integer> toBeSaluted;                                // stack of Students not yet Saluted

    private boolean readyToGetThePad;                                           // tells the First Student if the Waiter is ready to recieve the order
    private volatile int deliveredPortions,                                     // tells the Students how many portions have been delivered This Variable needs volatile because multiple processes access this variable.
                         currentCourse,                                         // tells the Students which course are they currently having
                         ate,                                                   // the amount of students that have eaten
                         last_id,                                               // make sure the last student that has eaten executes certain regions
                         chosenStudents;                                        // tells the First Student how many Students have chosen their courses

    private volatile boolean payed,                                             // has the Last student that arrived payed
                             finished,                                          // have everyone finished eating
                             billattable;                                       // has the waiter placed the bill on the table

    /**
     *  Constructor
     *  Allocates a new Table Shared Region.
     * 
     *  @param repo GeneralRepository object for the entities states' to be monitored through a file log.
     */
    public Table(GeneralRepository repo) {
        this.repo = repo;
        toBeSaluted = new Stack<>();
        chosenStudents = deliveredPortions = currentCourse = ate = 0;
        readyToGetThePad = finished = billattable = payed = false;
    }

    /**
     *  Table's Methods
     */
    
    /**
     *  Used by Student to define his inital state of GTTR and walk to the Restaurant.
     */
    public synchronized void walkABit() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.GTTR)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
    }

    /**
     *  Used by Student to sit at the table.
     */
    public synchronized void enter() {
        toBeSaluted.push(((Student) Thread.currentThread()).getID());
        this.notifyAll();                                                       // sinchronization of Student entering and Waiter going to salute him

        while (toBeSaluted.contains(((Student) Thread.currentThread()).getID())) {  // !toBeSaluted.isEmpty() 
            try {
                this.wait();                                                    // Student blocks while Waiter does not salute him
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     *  Used by Waiter to salute the arriving Students.
     */
    public synchronized void saluteTheStudent() {
        if (((Waiter) Thread.currentThread()).setWaiterState(Waiter.WaiterState.PTM)) {
            repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        }

        while (toBeSaluted.isEmpty()) {
            try {
                this.wait();                                                    // sinchronization of Student entering and Waiter going to salute him
            } catch (InterruptedException ie) {
            }
        }

        toBeSaluted.pop();
        this.notifyAll();                                                       // Waiter notifies Students when he salutes them
    }

    /**
     *  Used by Student to select the courses they are having.
     */
    public synchronized void readTheMenu() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.STC)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
    }

    /** 
     *  Used by Student to inform their choice to the Student responsible for the order.
     */
    public synchronized void informCompanion() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.CWC)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
        
        try {
            Thread.sleep((int) (10 * Math.random()));                           // simulates time of informing the companion
        } catch (Exception e) {}

        chosenStudents++;
        this.notifyAll();                                                       // notifies First Student about his choice
    }

    /**
     *  Used by Student to verify if everyone has made their choices for the courses. 
     * 
     *  @return Returns true if every Student has chosen their courses, false if not.
     */
    public synchronized boolean hasEverybodyChosen() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.OTO)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }

        if (chosenStudents == TheRestaurantMain.nstudents - 1) {
            return true;
        }
        return false;
    }

    /**
     *  Used by Student to prepare the Table's order.
     */
    public synchronized void prepareTheOrder() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.OTO)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
        if(!hasEverybodyChosen()) {
            try {
                this.wait();                                                        // First Student waits while others choose their courses
            } catch (InterruptedException ie) {}
        }
    }

    /**
     *  Used by Waiter to recieve the information about the Table's order.
     */
    public synchronized void getThePad() {
        if (((Waiter) Thread.currentThread()).setWaiterState(Waiter.WaiterState.TTO)) {
            repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        }
        readyToGetThePad = true;
        this.notifyAll();                                                       // sinchronization of Waiter going to the Table and First Student describing the order

        try {                                                                   //No need for a while because at this point everyone is on hold except first student
            this.wait();
        } catch (Exception e) {}
    }

    /**
     *  Used by Student to describe the order to the Waiter.
     */
    public synchronized void describeTheOrder() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.OTO)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }

        while (!readyToGetThePad) {
            try {
                wait();
            } catch (Exception e) {}
        }
        this.notifyAll();                                                       // sinchronization of Waiter going to the Table and First Student describing the order
        
        try {
            Thread.sleep((int) (10 * Math.random()));                           // simulates time of describing the order to the Waiter
        } catch (Exception e) {}
    }

    /**
     *  Used by Student to chat with companions while they wait for the food.
     */
    public synchronized void joinTheTalk() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.CWC)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }

        while (currentCourse == 0 || currentCourse == TheRestaurantMain.ncourses || (currentCourse == TheRestaurantMain.ncourses + 1 && ((Student) Thread.currentThread()).getArrivalOrder() != TheRestaurantMain.ArrivalOrder.LAST)) {
            try {
                wait();
            } catch (Exception e) {}
        }
    }

    /**
     *  Used by Waiter to deliver a portion at the table.
     */
    public synchronized void deliverPortion() {
        if (deliveredPortions % TheRestaurantMain.nstudents == 0) {
            deliveredPortions = 0;
            currentCourse++;
        }
        deliveredPortions++;                                                    // Waiter notifies the Students that another portion has been delivered
        this.notifyAll();
    }

    /**
     *  Used by Student to start eating the current course.
     * 
     *  @param nc integer variable with the number of a course.
     */
    public synchronized void startEating(int nc) {
        while (nc != currentCourse) {
            try {
                this.wait();
            } catch (Exception e) {}
        }
        
        while (currentCourse == nc && deliveredPortions < TheRestaurantMain.nstudents) {
            try {
                wait();
            } catch (Exception e) {}
        }
        
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.ETM)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
        this.notifyAll();
    }

    /**
     *  Used by Student to finish eating.
     */
    public synchronized void endEating() {
        if(!repo.isEveryStudentEating()) {
            try {
                wait();
            } catch (Exception e) {}
        }
        
        ate++;
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.CWC)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }

        if (ate == TheRestaurantMain.nstudents*3) {
            finished = true;
            currentCourse++;
            last_id = ((Student) Thread.currentThread()).getID();
        }
    }

    /**
     *  Used by Student to verify if everyone has finished eating. 
     * 
     *  @return Returns true if every Student has finished eating, false if not.
     */
    public synchronized boolean hasEverybodyFinished() {
        if (finished && last_id == ((Student) Thread.currentThread()).getID()) {
            this.notifyAll();
            return true;
        }
        
        while (!finished) {
            try {
                wait();
            } catch (Exception e) {}
        }
        return false;
    }

    /**
     *  Used by Waiter to present the bill to the Last Student.
     */
    public synchronized void presentTheBill() {
        if (((Waiter) Thread.currentThread()).setWaiterState(Waiter.WaiterState.RP)) {
            repo.updateWaiterState(((Waiter) Thread.currentThread()).getWaiterState());
        }

        billattable = true;
        this.notifyAll();

        while (billattable) {
            try {
                this.wait();
            } catch (Exception e) {}
        }
    }

    /**
     *  Used by Student to accept the fact that he will have to pay for the entire meal.
     */
    public synchronized void shouldHaveArrivedEarlier() {
        while (!billattable) {
            try {
                this.wait();
            } catch (Exception e) {}
        }
        
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.PTB)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
    }

    /**
     *  Used by Student to pay the Table's bill.
     */
    public synchronized void honorTheBill() {
        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.PTB)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }

        billattable = false;
        payed = true;
    }

    /**
     *  Used by Student to leave the Restaurant.
     */
    public synchronized void exit() {
        while (!payed || billattable) {
            try {
                wait();
            } catch (Exception e) {}
        }

        if (((Student) Thread.currentThread()).setStudentState(Student.StudentState.GH)) {
            repo.updateStudentState(((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getID());
        }
        this.notifyAll();
    }
}
