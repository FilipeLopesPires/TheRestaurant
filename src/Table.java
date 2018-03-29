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
    public synchronized void enter() {
        
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
    public synchronized void getThePad() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void readTheMenu() {
        
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
