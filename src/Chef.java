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
    
    public static enum ChefState {                          // Internal State Enum
        WAITING_FOR_AN_ORDER,
        PREPARING_THE_COURSE,
        DISHING_THE_PORTIONS,
        DELIVERING_THE_PORTIONS,
        CLOSING_SERVICE;
    }
    private Kitchen kitchen;                                // Kitchen Shared Region Access Point
    private ChefState chefState;                            // Thread's State
    
    /**
     *  Constructor
     *  Allocates a new Chef Thread object.
     *  @param kitchen shared region object for the Waiter Thread to access the Kitchen
     */
    public Chef(Kitchen kitchen) {
        this.kitchen = kitchen;
        chefState = ChefState.WAITING_FOR_AN_ORDER;         // Initial State
    }

    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Chef Thread.
     */
    @Override
    public void run () {
        
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