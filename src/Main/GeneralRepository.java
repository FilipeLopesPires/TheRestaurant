package Main;
import Entities.*;
import genclass.GenericIO;
import genclass.TextFile;

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
    
    public GeneralRepository(String filename) {
        studentState = new Student.StudentState[TheRestaurantMain.nstudents];
        allEntities = new String[TheRestaurantMain.nstudents+2];
        this.filename = filename;
        fileLog = new TextFile();
        initLog();
    }
    
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
    
    private void updateCourse() {
        if(!fileLog.openForAppending(null,filename)) {
            GenericIO.writelnString("An error occurred when opening the file. Program will end now.");
            System.exit(1);
        }
        fileLog.writelnString("Course no." + currentCourse);
        GenericIO.writelnString("Course no." + currentCourse);
    }
    
    public synchronized void updateChefState(Chef.ChefState newChefState) {
        this.chefState = newChefState;
        updateLog();
    }
    
    public synchronized void updateWaiterState(Waiter.WaiterState newWaiterState) {
        this.waiterState = newWaiterState;
        updateLog();
    }
    
    public synchronized void updateStudentState(Student.StudentState newStudentState, int studentID) {
        this.studentState[studentID-1] = newStudentState;
        updateLog();
    }
    
    public synchronized void updateAllStudentStates(Student.StudentState newStudentState, int studentID) {
        if(this.studentState[studentID-1] != newStudentState) {
            for(int i=0; i<TheRestaurantMain.nstudents; i++) {
                studentState[i] = newStudentState;
            }
            updateLog();
        }
    }
    
    public synchronized void updateCourse(int nCourse) {
        if(currentCourse==nCourse) {
            return;
        }
        currentCourse = nCourse;
        updateCourse();
    }
    
}
