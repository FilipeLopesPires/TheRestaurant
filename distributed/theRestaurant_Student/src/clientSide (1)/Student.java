package clientSide;

import comInf.*;

/**
 *  General Description: 
 *      Definition of the Student Thread - monitor-based solution.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Student extends Thread implements StudentInterface {

    /**
     *  Internal Data
     */

    private static BarStub bar;                                                 // Bar Shared Region Access Point
    private static TableStub table;                                             // Table Shared Region Access Point
    private static int nstudents = 0;                                           // Number of created Student Thread objects (to define the ID)
    private final int ID;                                                       // Student Thread's ID
    private EntityStates.StudentState studentState;                    // Student Thread's State
    private EntityStates.ArrivalOrder arrivedIn;                       // Student Thread's place of arrival

    /**
     *  Constructor Allocates a new Student Thread object, incrementing the static variable nstudents with one unit and defining the Student Thread ID.
     *
     *  @param bar shared region object for the Student Thread to access the Bar
     *  @param table shared region object for the Student Thread to access the Table
     */
    public Student(BarStub bar, TableStub table) {
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
       
        table.walkABit();                                                             // GTTR
        arrivedIn = bar.enter();
       
        table.enter();                                                          // TASATT
        
        table.readTheMenu();                                                    // STC  
        
        if (arrivedIn == EntityStates.ArrivalOrder.FIRST) {
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
        for (int c = 0; c < GeneralRepositoryStub.ncourses; c++) {
            table.startEating(c + 1);                                           // ETM
            table.endEating();                                                  // CWC
        }
        
        if (table.hasEverybodyFinished())                                        
            bar.signalTheWaiter();
                           
        if (arrivedIn == EntityStates.ArrivalOrder.LAST) {
            table.shouldHaveArrivedEarlier();                                   // PTB
            table.honorTheBill();                                               
        }

        table.exit();                                                           // GH

    }

    /**
     *  Student's Methods
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
    public EntityStates.StudentState getStudentState() {
        
        return studentState;
    }

    /**
     *  Sets a new state for the Student Thread if current state is different from the new state. 
     *
     *  @param newStudentState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    public boolean setStudentState(EntityStates.StudentState newStudentState) {
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
    public EntityStates.ArrivalOrder getArrivalOrder(){
        return arrivedIn;
    }

}
