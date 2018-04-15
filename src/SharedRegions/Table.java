package SharedRegions;
import Main.*;
import Entities.*;
import java.util.Stack;
import genclass.GenericIO;

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
    
    /**
     *  Constructor
     *  Allocates a new Table Shared Region.
     */
    public Table(GeneralRepository repo){
        this.repo = repo;
        toBeSaluted = new Stack<>();
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
    public synchronized void enter(int studentID) {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.TASATT)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        toBeSaluted.push(studentID);
        this.notifyAll();                                                       // sincronização do Student a entrar e o Waiter a se dirigir para o cumprimentar
        
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
                this.wait();                                                    // sincronização do Student a entrar e o Waiter a se dirigir para o cumprimentar
            } catch (InterruptedException ie) {}
        }
        
        while(!toBeSaluted.isEmpty()) {
            toBeSaluted.pop();
        }
        this.notifyAll();                                                       // Waiter notifies Students when he salutes them
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void getThePad() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.TTO) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        
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
        
        while(true) {
            try {
                this.wait();                                                    // tmp
            } catch (InterruptedException ie) {}
        }
        
    }
    /**
     *  Used by Student to 
     *  @return 
     */
    public synchronized boolean hasEverybodyChosen() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.OTO)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
        return false;
    }
    /**
     *  Used by Student to 
     */
    public synchronized void prepareTheOrder() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void describeTheOrder() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void joinTheTalk() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.CWC)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
    }
    /** 
     *  Used by Student to 
     */
    public synchronized void informCompanion() {
        
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void deliverPortion() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void startEating() {
        if(((Student)Thread.currentThread()).setStudentState(Student.StudentState.ETM)) {
            repo.updateStudentState(((Student)Thread.currentThread()).getStudentState(), ((Student)Thread.currentThread()).getID());
        }
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void endEating() {
        
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
