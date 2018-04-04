/**
 * 
 *  General Description:
 *      Definition of the Chef Thread - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Chef extends Thread {
    
    /**
     *  Internal Data
     */
    
    public enum ChefState {                                     // Internal State Enum
        WAITING_FOR_AN_ORDER,
        PREPARING_THE_COURSE,
        DISHING_THE_PORTIONS,
        DELIVERING_THE_PORTIONS,
        CLOSING_SERVICE;
    }
    private static Kitchen kitchen;                             // Kitchen Shared Region Access Point
    private static Bar bar;                                     // Bar Shared Region Access Point
    private ChefState chefState;                                // Thread's State
    private static int nstudents;                               // number of Students that are going to the Restaurant
    private int ndishes;                                        // number of dishes/portions per Student
    
    /**
     *  Constructor
     *  Allocates a new Chef Thread object.
     *  @param kitchen shared region object for the Waiter Thread to access the Kitchen
     */
    public Chef(Kitchen kitchen, Bar bar) {
        this.kitchen = kitchen;
        this.bar = bar;
        chefState = ChefState.WAITING_FOR_AN_ORDER;             // Initial State
        ndishes = kitchen.getNdishes();
    }

    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Chef Thread.
     */
    @Override
    public void run () {
        kitchen.watchTheNews(this);                                 // block while no order is made
        nstudents = kitchen.startPreparation(this);             // with the note handed to him, the Chef now knows how many students are at the table
        for(int nd=1; nd<=ndishes; nd++) {
            kitchen.proceedToPresentation(this);
            for(int p=0; p<nstudents; p++) {
                bar.alertTheWaiter();
                if(!kitchen.haveAllPortionsBeenDelivered()) {
                    kitchen.haveNextPortionReady();             // block while waiter has not returned yet
                }
            }
            if(!kitchen.hasTheOrderBeenCompleted()) {
                kitchen.continuePreparation();
            }
        }
        kitchen.cleanUp();
    }
    
    /**
     *  Auxiliar methods
     */
    
    /**
     *  Returns the current state of the Chef Thread.
     *  @return Element from the ChefState Enumerate.
     */
    public ChefState getChefState() { return chefState; }
    /**
     *  Sets a new state for the Chef Thread.
     *  @param newChefState State to replace the current Thread's state.
     */
    public void setChefState(ChefState newChefState) { chefState = newChefState; }
    
}