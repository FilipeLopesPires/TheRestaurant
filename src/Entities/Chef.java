package Entities;

import Main.*;
import SharedRegions.*;

/**
 *  General Description:
 *      Definition of the Chef Thread - monitor-based solution.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Chef extends Thread {

    /**
     *  Internal Data
     */
    
    /**
     *  Internal Enumerate holding all the possible states of the entity.
     */
    public enum ChefState {                                                     // Internal State Enum
        /**
         *  WAITING_FOR_AN_ORDER – blocking state (initial state); the chef is waken up by the operation handTheNoteToTheChef of the waiter
         */
        WFAO,
        /**
         *  PREPARING_THE_COURSE – transition state
         */
        PTC,
        /**
         *  DISHING_THE_PORTIONS – transition state
         */
        DiTP,
        /**
         *  DELIVERING_THE_PORTIONS – blocking state; the chef is waken up by the operation collectPortion of the waiter
         */
        DeTP,
        /**
         *  CLOSING_SERVICE – final state
         */
        CS;
    }

    private static Kitchen kitchen;                                             // Kitchen Shared Region Access Point
    private static Bar bar;                                                     // Bar Shared Region Access Point
    private volatile ChefState chefState;                                       // Thread's State

    /**
     *  Constructor Allocates a new Chef Thread object.
     *
     *  @param kitchen shared region object for the Chef Thread to access the Kitchen
     *  @param bar shared region object for the Chef Thread to access the Bar
     */
    public Chef(Kitchen kitchen, Bar bar) {
        this.kitchen = kitchen;
        this.bar = bar;
        //chefState = ChefState.WFAO;                                             // Initial State
    }

    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Chef Thread.
     */
    @Override
    public void run() {
        kitchen.watchTheNews();                                                 // WFAO til order is true;
        kitchen.startPreparation(1);                                            // PTC
        for (int nc = 1; nc <= TheRestaurantMain.ncourses; nc++) {
            kitchen.proceedToPresentation();                                    // DiTP
            int i = 1;
            while (true) {

                if (kitchen.haveAllPortionsBeenDelivered()) {                   
                    break;
                }
                bar.alertTheWaiter();                                           // DeTP
                if(i<TheRestaurantMain.nstudents)
                kitchen.haveNextPortionReady();                                 // DiTP
                i++;
                
            }
            
            if (!kitchen.hasTheOrderBeenCompleted()) {                          // DeTP
               
                kitchen.continuePreparation(nc);                                // PTC
            }

        }
        kitchen.cleanUp();                                                      // CS
    }

    /**
     *  Auxiliar methods
     */
    
    /**
     *  Getter function that serves as an auxiliar for the entities, to know the Chef's State. 
     *
     *  @return Returns the current state of the Chef Thread.
     */
    public ChefState getChefState() {
        return chefState;
    }

    /**
     *  Sets a new state for the Chef Thread if current state is different from the new state. 
     *
     *  @param newChefState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    public boolean setChefState(ChefState newChefState) {
        if (chefState == null || chefState != newChefState) {
            chefState = newChefState;
            return true;
        }
        return false;
    }

}
