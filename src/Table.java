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
    
    private int nstudents;                                  // number of Students to sit at the Restaurant's Table
    private int arrivedStudents = 0;                        // number of Students that have arrived at the Restaurant
    
    /**
     *  Constructor
     *  Allocates a new Table Shared Region.
     */
    public Table(int nstudents){
        this.nstudents = nstudents;
    }
    
    /**
     *  Table's Methods
     */
    
    /**
     *  Used by Student to sit at the table.
     */
    public synchronized void enter(Student student) {
        arrivedStudents++;
        student.setStudentState(Student.StudentState.TAKING_A_SEAT_AT_THE_TABLE);
        GenericIO.writelnString("The Student is " + student.getStudentState() + ".");
        while(arrivedStudents != nstudents) {
            try {
                wait();                             // the Student Thread must block while all Students have not arrived
            } catch (InterruptedException e) {}
        }
        if(student.getArrivedIn() == TheRestaurantMain.ArrivalOrder.LAST) {
            notifyAll();                            // the last Student Thread to arrive must notify all Students that everyone has arrived
        }
    }
    /**
     *  Used by Waiter to salute the arriving Students.
     */
    public synchronized void saluteTheStudent() {
        GenericIO.writelnString("Waiter salutes the student.");
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void getThePad(Waiter waiter) {
        GenericIO.writelnString("Waiter salutes the last student.\n");
        waiter.setWaiterState(Waiter.WaiterState.PRESENTING_THE_MENU);
        GenericIO.writelnString("Waiter is now " + waiter.getWaiterState() + " to the Students.");
        while(!waiter.getHasEveryoneChosen()) {
            try {
                wait();                                  // the Waiter Thread must block while all Students are choosing their courses
            } catch (InterruptedException e) {}
        }
    }
    /**
     *  Used by Student to 
     */
    public synchronized void readTheMenu(Student student) {
        GenericIO.writelnString("I am going to block here! :)");
        try {
            wait();                     
        } catch (InterruptedException e) {}
    }
    /**
     *  Used by Student to 
     *  @return 
     */
    public synchronized boolean hasEverybodyChosen() {
        
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
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void exit() {
        
    }
    
    /**
     *  Auxiliar Methods
     */
    
}
