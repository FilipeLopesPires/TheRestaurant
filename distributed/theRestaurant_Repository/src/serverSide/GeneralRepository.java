package serverSide;

import comInf.*;

import genclass.*;

/**
 *
 *  General description: Definition of the General Repository for the Restaurant - monitor-based solution.
 *
 *  @author Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class GeneralRepository {

    /**
     * Internal Data
     */
    
    public static int nstudents = 7;                                            // number of Students that are going to the Restaurant
    public static int ncourses = 3;                                             // number of courses per Student
    private EntityStates.ChefState chefState;                                           // Chef's current state
    private EntityStates.WaiterState waiterState;                                     // Waiter's current state
    private EntityStates.StudentState[] studentState;                                // Student's current state
    private String[] allEntities;                                               // Backup array of states
    private int currentCourse;                                                  // Current course

    private TextFile fileLog;                                                   // Log File Object
    private String filename;                                                    // Name of file

    /**
     * General Repository's Methods
     */
    
    /**
     *  Constructor Allocates a new GeneralRepository Shared Region.
     *
     *  @param filename name of the text file on which all updates will be saved to.
     */
    public GeneralRepository(String filename) {
        studentState = new EntityStates.StudentState[this.nstudents];
        allEntities = new String[this.nstudents + 2];
        this.filename = filename;
        fileLog = new TextFile();

        waiterState = EntityStates.WaiterState.AS;
        chefState = EntityStates.ChefState.WFAO;

        for (int s = 0; s < this.nstudents; s++) {
            studentState[s] = EntityStates.StudentState.GTTR;
        }

        initLog();
        updateLog();

    }

    /**
     *  Initialization of the log file, creating the title and the heads of the file.
     */
    private void initLog() {
        if (!fileLog.openForWriting(null, filename)) {
            GenericIO.writelnString("An error occurred when opening the file. Program will end now.");
            System.exit(1);
        }

        fileLog.writeString("\n                             - The Restaurant -                             \n\n");
        GenericIO.writeString("\n                             - The Restaurant -                             \n\n");

        fileLog.writeString("Chf      Wtr      ");
        GenericIO.writeString("Chf      Wtr      ");

        for (int i = 1; i <= this.nstudents; i++) {
            fileLog.writeString("Std" + i + "     ");
            GenericIO.writeString("Std" + i + "     ");
        }
        fileLog.writeString("\n\n");
        GenericIO.writeString("\n\n");

        fileLog.close();
        
    }

    /**
     *  Update of the current states of all entities, saving them on the text file.
     */
    private void updateLog() {

        if (chefState == null || waiterState == null) {
            return;
        }

        for (int s = 0; s < this.nstudents; s++) {
            if (studentState[s] == null) {
                return;
            }
        }

        if (!fileLog.openForAppending(null, filename)) {
            GenericIO.writelnString("An error occurred when opening the file. Program will end now.");
            System.exit(1);
        }

        allEntities[0] = chefState.toString();
        allEntities[1] = waiterState.toString();
        for (int i = 2; i < allEntities.length; i++) {
            allEntities[i] = studentState[i - 2].toString();
        }

        fileLog.writeFormString(this.nstudents + 2, allEntities);
        fileLog.writelnString();
        GenericIO.writeFormString(this.nstudents + 2, allEntities);
        GenericIO.writelnString();

        fileLog.close();
        //  try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
    }

    /**
     *  Update of the current course, saving it on the text file.
     */
    private void updateCourse() {
        if (!fileLog.openForAppending(null, filename)) {
            GenericIO.writelnString("An error occurred when opening the file. Program will end now.");
            System.exit(1);
        }
        fileLog.writelnString("Course no." + currentCourse);
        GenericIO.writelnString("Course no." + currentCourse);
        fileLog.close();
    }

    /**
     *  Internal update of the information about the Chef's state.
     *
     *  @param newChefState state to replace the Chef's current state
     */
    public synchronized void updateChefState(EntityStates.ChefState newChefState) {
        if (this.chefState != newChefState) {
            this.chefState = newChefState;
            updateLog();
        }
    }

    /**
     *  Internal update of the information about the Waiter's state.
     *
     *  @param newWaiterState state to replace the Waiter's current state
     */
    public synchronized void updateWaiterState(EntityStates.WaiterState newWaiterState) {
        if (this.waiterState != newWaiterState) {
            this.waiterState = newWaiterState;
            updateLog();
        }
    }

    /**
     *  Internal update of the information about a Student's state.
     *
     *  @param newStudentState state to replace the Student's current state
     *  @param studentID ID of the Student who's state will be updated
     */
    public synchronized void updateStudentState(EntityStates.StudentState newStudentState, int studentID) {
       if(this.studentState[studentID-1] != newStudentState){ 
        this.studentState[studentID - 1] = newStudentState;
        updateLog();
       }
    }

    /**
     *  Internal update of the information about the current course.
     *
     *  @param nCourse new course number
     */
    public synchronized void updateCourse(int nCourse) {
        if (currentCourse == nCourse) {
            return;
        }
        currentCourse = nCourse;
        updateCourse();
    }

    /**
     *  Verification of all Students' states (wether they have left the restaurant or not) for the Waiter to close the restaurant.
     *  @return true if all Students have left, false if not
     */
    public boolean HaveAllStudentLeft() {
        for (int i = 0; i < this.nstudents; i++) {
            if (studentState[i] != EntityStates.StudentState.GH) {
                return false;
            }
        }
        return true;
    }

    /**
     *  Verification of wether all the Students are eating the current course or not.
     *  @return true if every Student is eating, false if not
     */
    public boolean isEveryStudentEating() {
        for (int s = 0; s < nstudents; s++) {
            if (studentState[s] != EntityStates.StudentState.ETM) {
                return false;
            }
        }
        return true;
    }
    
    /**
     *  Process of closing the server once it is no longer necessary.
     */
    public void shutdown(){
        RepoMain.shutdown();
    }

}
