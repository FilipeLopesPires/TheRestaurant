package Main;
import Entities.*;
import genclass.*;

/**
 *
 *  General description:
 *      Definition of the General Repository for the Restaurant - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class GeneralRepository {
    
    /**
     *  Internal Data
     */
    
    private Chef.ChefState chefState;
    private Waiter.WaiterState waiterState;
    private Student.StudentState[] studentState;
    private String[] allEntities;
    private int currentCourse;
    
    private TextFile fileLog;
    private String filename;
    
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
     *  @param int variable holding the ID of the Student who's sate was changed.
     */
    public synchronized void updateStudentState(Student.StudentState newStudentState, int studentID) {
        this.studentState[studentID-1] = newStudentState;
        updateLog();
    }
    
    /**
     *  Internal update of the information about all Student's states at once.
     * 
     *  @param newStudentState StudentState variable holding the new state.
     *  @param int variable holding the ID of the Student who called the method.
     */
    public synchronized void updateAllStudentStates(Student.StudentState newStudentState, int studentID) {
        if(this.studentState[studentID-1] != newStudentState) {
            for(int i=0; i<TheRestaurantMain.nstudents; i++) {
                studentState[i] = newStudentState;
            }
            updateLog();
        }
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
}
