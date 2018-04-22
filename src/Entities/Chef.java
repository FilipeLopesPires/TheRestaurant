package Entities;

import Main.*;
import SharedRegions.*;

/**
 *  General Description:
 *      Definition of the Chef Thread - monitor-based solution.
 *
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Chef extends Thread {

    /**
     *  Internal Data
     */
    public enum ChefState {                                                     // Internal State Enum
        WFAO,
        PTC,
        DiTP,
        DeTP,
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
        chefState = ChefState.WFAO;                                             // Initial State
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
        kitchen.startPreparation();                                             // PTC
        for (int nc = 1; nc <= TheRestaurantMain.ncourses; nc++) {
            kitchen.proceedToPresentation(nc);                                  // DiTP

            while (true) {
                if (kitchen.haveAllPortionsBeenDelivered()) {
                    break;
                }
                bar.alertTheWaiter();                                           // DeTP
                kitchen.haveNextPortionReady();                                 // DiTP
            }
            if (!kitchen.hasTheOrderBeenCompleted()) {
                kitchen.continuePreparation();                                  // PTC
            }
        }
        kitchen.cleanUp();                                                      // CS
    }

    /**
     *  Auxiliar methods
     */
    
    /**
     *  Returns the current state of the Chef Thread.
     *
     *  @return ChefState variable holding the Chef's state.
     */
    public ChefState getChefState() {
        return chefState;
    }

    /**
     *  Sets a new state for the Chef Thread if the current state is different from the new state.
     *
     *  @param newChefState State to replace the current Thread's state.
     */
    public boolean setChefState(ChefState newChefState) {
        if (chefState != newChefState) {
            chefState = newChefState;
            return true;
        }
        return false;
    }
}
