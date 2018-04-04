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
    private int ndishes;                                    // number of dishes/portions per Student
    private boolean order = false;                          // conditional variable to warn the Chef when to start cooking
    
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
    public synchronized void watchTheNews(Chef chef) {
        chef.setChefState(Chef.ChefState.WAITING_FOR_AN_ORDER);
        GenericIO.writelnString("Chef is watching the news while he is " + chef.getChefState());
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
        notifyAll();                                   // the Waiter Thread must notify the Chef when the order is made
        GenericIO.writelnString ("Waiter hands the note to the Chef.");
    }
    /**
     *  Used by Chef to 
     */
    public synchronized int startPreparation(Chef chef) {
        chef.setChefState(Chef.ChefState.PREPARING_THE_COURSE);
        GenericIO.writelnString ("Chef starts " + chef.getChefState() + ".");
        try{
            chef.sleep(1000);                                // simulates preparation time (preparing, cooking, etc.)
        } catch (InterruptedException e) {}
        return nstudents;
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void proceedToPresentation(Chef chef) {
        chef.setChefState(Chef.ChefState.DISHING_THE_PORTIONS);
        GenericIO.writelnString ("Chef finishes all preparations and proceeds to " + chef.getChefState() + " to the Waiter.");
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
    public synchronized void cleanUp() {
        
    }
    
    /**
     *  Auxiliar Methods
     */
    
    
    public synchronized int getNdishes() {
        return ndishes;
    }
}
