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
    private int arrivedStudents;                            // number of Students that have arrived at the Restaurant
    private int salutedStudents;                            // number of Students that have already been saluted
    private int chosenStudents;                             // number of Students that have already chosen their course
    
    /**
     *  Constructor
     *  Allocates a new Table Shared Region.
     */
    public Table(int nstudents){
        this.nstudents = nstudents;
        arrivedStudents = 0;
        salutedStudents = 0;
        chosenStudents = 0;
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
        GenericIO.writelnString("The " + student.getArrivedIn() + " Student is " + student.getStudentState() + ".");
        while(salutedStudents < nstudents) {
            try {
                wait();                             // the Student Thread must block while all Students have not arrived
            } catch (InterruptedException e) {}
        }
    }
    /**
     *  Used by Waiter to salute the arriving Students.
     */
    public synchronized void saluteTheStudent() {
        GenericIO.writelnString("Waiter salutes the student.");
        salutedStudents++;
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void getThePad(Bar bar, Waiter waiter) {
        waiter.setWaiterState(Waiter.WaiterState.PRESENTING_THE_MENU);
        try {
            Thread.sleep(1000);                          // simulates time for Waiter to grab the pad and get to the table
        } catch(Exception e) {}
        GenericIO.writelnString("Waiter is now " + waiter.getWaiterState() + " to the Students.");
        try {
            wait();                                  // the Waiter Thread must block while Students dont get the menu
        } catch (InterruptedException e) {}
        
        waiter.setWaiterState(Waiter.WaiterState.TAKING_THE_ORDER);
        GenericIO.writelnString("Waiter is now " + waiter.getWaiterState() + " from the Students.");
        while(!bar.getHasEveryoneChosen()) {
            try {
                wait();                                  // the Waiter Thread must block while Students have not yet ordered
            } catch (InterruptedException e) {}
        }
    }
    /**
     *  Used by Student to 
     */
    public synchronized void readTheMenu(Student student) {
        student.setStudentState(Student.StudentState.SELECTING_THE_COURSES);
        GenericIO.writelnString("Student no." + student.getID() + " is " + student.getStudentState() + ".");
        try {
            Thread.sleep(1000);                         // simulates time for Student to decide what to choose from the menu
        } catch (InterruptedException e) {}
        if (student.getArrivedIn() == TheRestaurantMain.ArrivalOrder.FIRST) {
            notifyAll();                                // the FIRST Student Thread must notify the Waiter once the Students start reading the menu
        }
    }
    /**
     *  Used by Student to 
     *  @return 
     */
    public synchronized boolean hasEverybodyChosen() {
        if (chosenStudents+1 == nstudents) {
            return true;
        }
        return false;
    }
    /**
     *  Used by Student to 
     */
    public synchronized void prepareTheOrder(Student student) {
        student.setStudentState(Student.StudentState.ORGANIZING_THE_ORDER);
        GenericIO.writelnString("Student no." + student.getID() + ", the " + student.getArrivedIn() + " Student to arrive, is " + student.getStudentState() + ".");
        try {
            wait();                                  // the FIRST Student Thread must block while other Students are choosing
        } catch (InterruptedException e) {}
    }
    /**
     *  Used by Student to 
     */
    public synchronized void describeTheOrder(Student student) {
        GenericIO.writelnString("Student no." + student.getID() + ", the " + student.getArrivedIn() + " Student to arrive, describes the order to the Waiter");
        notifyAll();                                   // the FIRST Student Thread must notify the Waiter Thread once everyone has chosen
    }
    /**
     *  Used by Student to 
     */
    public synchronized void joinTheTalk(Student student) {
        student.setStudentState(Student.StudentState.CHATTING_WITH_COMPANIONS);
        GenericIO.writelnString("Student no." + student.getID() + " is " + student.getStudentState() + ".");
        while() {
            try {
                wait();                                  // the Student Thread must block while the food has not yet arrived
            } catch (InterruptedException e) {}
        }
    }
    /** 
     *  Used by Student to 
     */
    public synchronized void informCompanion() {
        chosenStudents++;
        notifyAll();
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
