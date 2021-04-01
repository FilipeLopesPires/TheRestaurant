package clientSide;

/**
 *  General Description: 
 *      Definition of the Student Thread - monitor-based solution.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Student extends Thread {

    /**
     *  Internal Data
     */
    
    /**
     *  Internal Enumerate holding all the possible states of the entity.
     */
    public enum StudentState {                                                  // Internal State Enum
        /**
         *  GOING_TO_THE_RESTAURANT - transition state with random time (initial state)
         */
        GTTR,
        /**
         *  TAKING_A_SEAT_AT_THE_TABLE – blocking state; the student is waken up by the operation saluteTheClient of the waiter
         */
        TASATT,
        /**
         *  SELECTING_THE_COURSES – transition state
         */
        STC,
        /**
         *  ORGANIZING_THE_ORDER – blocking state; the student is waken up by the operation informCompanion of another student and, when all students are already at the table, by the operation getThePad of the waiter
         */
        OTO,
        /**
         *  CHATTING_WITH_COMPANIONS – blocking state with transition; the student blocks while waiting for a course to be served and when he/she has finished eating it; transition occurs when the last course has been served and eaten
         */
        CWC,
        /**
         *  ENJOYING_THE_MEAL – transition state
         */
        ETM,
        /**
         *  PAYING_THE_MEAL – blocking state; the student is waken up by the operation presentTheBill of the waiter
         */
        PTB,
        /**
         *  GOING_HOME – final state
         */
        GH;
    }

    private static Bar bar;                                                     // Bar Shared Region Access Point
    private static Table table;                                                 // Table Shared Region Access Point
    private static int nstudents = 0;                                           // Number of created Student Thread objects (to define the ID)
    private final int ID;                                                       // Student Thread's ID
    private StudentState studentState;                                          // Student Thread's State
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
        //studentState = StudentState.GTTR;
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
            while (!table.hasEverybodyChosen()) {                                // OTO
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
            table.endEating();                                                  // CWC
        }
        
        if (table.hasEverybodyFinished())                                        
            bar.signalTheWaiter();
                           
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
     *  Getter function that serves as an auxiliar for the entities, to know a Student's ID. 
     *
     *  @return Returns the ID of the Student Thread.
     */
    public int getID() {
        return ID;
    }

    /**
     *  Getter function that serves as an auxiliar for the entities, to know a Student's State. 
     *
     *  @return Returns the current state of the Student Thread.
     */
    public StudentState getStudentState() {
        return studentState;
    }

    /**
     *  Sets a new state for the Student Thread if current state is different from the new state. 
     *
     *  @param newStudentState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    public boolean setStudentState(StudentState newStudentState) {
        if (studentState == null || studentState != newStudentState) {
            studentState = newStudentState;
            return true;
        }
        return false;
    }
    
    /**
     *  Getter function that serves as an auxiliar for the entities, to know a Student's place of arrival. 
     * 
     *  @return Returns the place of arrival of the Student.
     */
    public TheRestaurantMain.ArrivalOrder getArrivalOrder(){
        return arrivedIn;
    }

}
