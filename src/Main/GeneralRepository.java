package Main;
import Entities.*;
import genclass.*;

/**
 *
 *  General description:
 *      Definition of the General Repository for the Restaurant - monitor-based solution.
 * 
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class GeneralRepository {
    
    /**
     *  Internal Data
     */
    
    private Chef.ChefState chefState;                                           // Chef's current state
    private Waiter.WaiterState waiterState;                                     // Waiter's current state
    private Student.StudentState[] studentState;                                // Student's current state
    private String[] allEntities;                                               // Backup array of states
    private int currentCourse;                                                  // Current course
    
    private TextFile fileLog;                                                   // Log File Object
    private String filename;                                                    // Name of file
    
    /**
     *  General Repository's Methods
     */
    
    /**
     *  Constructor
     *  Allocates a new GeneralRepository Shared Region.
     * 
     *  @param filename name of the text file on which all updates will be saved to.
     */
    public GeneralRepository(String filename) {
        studentState = new Student.StudentState[TheRestaurantMain.nstudents];
        allEntities = new String[TheRestaurantMain.nstudents+2];
        this.filename = filename;
        fileLog = new TextFile();
        initLog();
    }
    
    /**
     *  Initialization of the log file, creating the title and the heads of the file.
     */
    private void initLog() {
        if(!fileLog.openForWriting(null,filename)) {
            GenericIO.writelnString("An error occurred when opening the file. Program will end now.");
            System.exit(1);
        } 
        
        fileLog.writeString("\n                             - The Restaurant -                             \n\n");
        GenericIO.writeString("\n                             - The Restaurant -                             \n\n");
        
        fileLog.writeString("Chf      Wtr      ");
        GenericIO.writeString("Chf      Wtr      ");
        for(int i=1; i<=TheRestaurantMain.nstudents; i++) {
            fileLog.writeString("Std"+i+"     ");
            GenericIO.writeString("Std"+i+"     ");
        }
        fileLog.writeString("\n\n");
        GenericIO.writeString("\n\n");
        
        fileLog.close();
    }
    
    /**
     *  Update of the current states of all entities, saving them on the text file.
     */
    private void updateLog() {
        if(chefState==null || waiterState==null) {
            return;
        }
        for(int s=0; s<TheRestaurantMain.nstudents; s++) {
            if(studentState[s]==null) {
                return;
            }
        }
        
        if(!fileLog.openForAppending(null,filename)) {
            GenericIO.writelnString("An error occurred when opening the file. Program will end now.");
            System.exit(1);
        }
        
        allEntities[0] = chefState.toString();
        allEntities[1] = waiterState.toString();
        for(int i=2; i<allEntities.length; i++) {
            allEntities[i] = studentState[i-2].toString();
        }
        
        fileLog.writeFormString(TheRestaurantMain.nstudents+2,allEntities);
        fileLog.writelnString();
        GenericIO.writeFormString(TheRestaurantMain.nstudents+2,allEntities);
        GenericIO.writelnString();
        
        fileLog.close();
      //  try{Thread.sleep(1000);}catch(Exception e){e.printStackTrace();}
    }
    
    /**
     *  Update of the current course, saving it on the text file.
     */
    private void updateCourse() {
        if(!fileLog.openForAppending(null,filename)) {
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
     *  @param newChefState ChefState variable holding the new state.
     */
    public synchronized void updateChefState(Chef.ChefState newChefState) {
        this.chefState = newChefState;
        updateLog();
    }
    
    /**
     *  Internal update of the information about the Waiter's state.
     * 
     *  @param newWaiterState WaiterState variable holding the new state.
     */
    public synchronized void updateWaiterState(Waiter.WaiterState newWaiterState) {
        this.waiterState = newWaiterState;
        updateLog();
    }
    
    /**
     *  Internal update of the information about a Student's state.
     * 
     *  @param newStudentState StudentState variable holding the new state.
     *  @param studentID integer variable holding the ID of the Student who's sate was changed.
     */
    public synchronized void updateStudentState(Student.StudentState newStudentState, int studentID) {
        this.studentState[studentID-1] = newStudentState;
        updateLog();
    }
    
    /**
     *  Internal update of the information about the current course.
     *  
     *  @param nCourse int variable holding the new course.
     */
    public synchronized void updateCourse(int nCourse) {
        if(currentCourse==nCourse) {
            return;
        }
        currentCourse = nCourse;
        updateCourse();
    }
    
    /**
     *  Final verification of all thread states. Waiter checks if everyone has left and closes the Restaurant. Returns true if all Students have left the Restaurant, false if not.
     * 
     *  @return boolean variable holding the result of the method's execution.
     */
    public boolean HaveAllStudentLeft(){
        for(int i=0; i<TheRestaurantMain.nstudents; i++) {
                if(studentState[i] != Student.StudentState.GH)
                    return false;
            }
        return true;
    }
}
