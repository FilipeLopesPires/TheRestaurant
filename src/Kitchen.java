import genclass.GenericIO;
/**
 * 
 *  General Description:
 *      Definition of the Kitchen Shared Region - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Kitchen {
    
    /**
     *  Internal Data
     */
    
    private int nstudents;                                  // number of meals to be prepared by the Restaurant's Chef
    private int ndishes;
    private boolean order = false;
    
    /**
     *  Constructor
     *  Allocates a new Kitchen Shared Region.
     */
    public Kitchen(int nstudents, int ndishes){
        this.nstudents = nstudents;
        this.ndishes = ndishes;
    }
    
    /**
     *  Kitchen's Methods
     */
    
    /**
     *  Used by Chef to 
     */
    public synchronized void watchTheNews() {
        while (!order) {
            try{
                wait();                                     // the Chef Thread must block if there is no order to process
            } catch (InterruptedException e) {}
        }
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void handTheNoteToTheChef() {
        order = true;
        GenericIO.writelnString ("Waiter hands the note to the Chef.");
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void startPreparation() {
        
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void proceedToPresentation() {
        
    }
    /**
     *  Used by Chef to 
     *  @return 
     */
    public synchronized boolean haveAllPortionsBeenDelivered() {
        
        return false;
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void haveNextPortionReady() {
        
    }
    /**
     *  Used by Waiter to 
     *  @return 
     */
    public synchronized boolean haveAllClientsBeenSirved() {
        
        return false;
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void collectPortion() {
        
    }
    /**
     *  Used by Chef to 
     *  @return 
     */
    public synchronized boolean hasTheOrderBeenCompleted() {
        
        return false;
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void continuePreparation() {
        
    }
    /**
     *  Used by Chef to 
     */
    public void cleanUp() {
        
    }
    
    /**
     *  Auxiliar Methods
     */
    
}
