package SharedRegions;
import Main.*;
import Entities.*;
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
    
    private static GeneralRepository repo;                                      // Restaurant's Repository
    private boolean order;                                                      // tells the Chef when an order is delivered
        
    /**
     *  Constructor
     *  Allocates a new Kitchen Shared Region.
     */
    public Kitchen(GeneralRepository repo){
        this.repo = repo;
        order = false;
    }
    
    /**
     *  Kitchen's Methods
     */
    
    /**
     *  Used by Chef to 
     */
    public synchronized void watchTheNews() {
        repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        
        while(!order) {
            try {
                this.wait();                                                    // Chef blocks while there is nothing to be prepared
            } catch (InterruptedException ie) {}
        }
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void handTheNoteToTheChef() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.PTO) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void startPreparation() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.PTC) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void proceedToPresentation() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.DiTP) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
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
    public synchronized boolean haveAllClientsBeenServed() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.WFP) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        
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
    public synchronized void cleanUp() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.CS) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
    }
    
    /**
     *  Auxiliar Methods
     */
    
}
