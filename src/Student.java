/**
 * 
 *  General Description:
 *      Definition of the Student Thread - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Student extends Thread {
    
    /**
     *  Internal Data
     */
    
    public static enum StudentState {                       // Internal State Enum
        GOING_TO_THE_RESTAURANT,
        TAKING_A_SEAT_AT_THE_TABLE,
        SELECTING_THE_COURSES,
        ORGANIZING_THE_ORDER,
        CHATTING_WITH_COMPANIONS,
        ENJOYING_THE_MEAL,
        PAYING_THE_MEAL,
        GOING_HOME;
    }

    private Bar bar;                                        // Bar Shared Region Access Point
    private Table table;                                    // Table Shared Region Access Point
    private static int nstudents = 0;                       // Number of created Student Thread objects
    private final int ID;                                   // Thread's ID
    private StudentState studentState;                      // Thread's State
    private TheRestaurantMain.ArrivalOrder arrivedIn;       // Thread's place of arrival
    
    /**
     *  Constructor
     *  Allocates a new Student Thread object, incrementing the static variable nstudents with one unit and defining the Student Thread ID.
     *  @param bar shared region object for the Student Thread to access the Bar
     *  @param table shared region object for the Student Thread to access the Table
     */
    public Student(Bar bar, Table table) {
        this.bar = bar;
        this.table = table;
        studentState = StudentState.GOING_TO_THE_RESTAURANT;
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
    public void run () {
        walkABit();
        arrivedIn = bar.enter();
        table.enter();
        table.readTheMenu();
        if(arrivedIn == TheRestaurantMain.ArrivalOrder.FIRST) {
            while(!table.hasEverybodyChosen()) {
                table.prepareTheOrder();
            }
            bar.callTheWaiter();
            table.describeTheOrder();
            table.joinTheTalk();
        } else {
            table.informCompanion();
        }
    }
    
    /**
     *  Student's Methods
     */
    
    /**
     *  Simulates the travel time between Student's current position and final destination, the Restaurant.
     */
    private void walkABit() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {}
    }
    
    /**
     *  Auxiliar methods
     */
    
    /**
     *  Returns the current state of the Student Thread.
     *  @return Element from the ChefState Enumerate.
     */
    public StudentState getStudentState() { return studentState; }
    /**
     *  Returns the Sudent Thread's ID.
     *  @return Integer representing the internal ID.
     */
    public int getID() { return ID; }
    /**
     *  Sets a new state for the Student Thread.
     *  @param newStudentState State to replace the current Thread's state.
     */
    public void setStudentState(StudentState newStudentState) { studentState = newStudentState; }
}
