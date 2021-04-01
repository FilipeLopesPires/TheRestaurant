package clientSide;
import comInf.*;
/**
 *  General Description:
 *      Definition of the Chef Thread - monitor-based solution.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Chef extends Thread implements ChefInterface{

    /**
     *  Internal Data
     */

    private static KitchenStub kitchen;                                         // Kitchen Shared Region Access Point
    private static BarStub bar;                                                 // Bar Shared Region Access Point
    private volatile EntityStates.ChefState chefState;                 // Thread's State

    /**
     *  Constructor Allocates a new Chef Thread object.
     *
     *  @param kitchen shared region object for the Chef Thread to access the Kitchen
     *  @param bar shared region object for the Chef Thread to access the Bar
     */
    public Chef(KitchenStub kitchen, BarStub bar) {
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
        for (int nc = 1; nc <= GeneralRepositoryStub.ncourses; nc++) {
            kitchen.proceedToPresentation();                                    // DiTP
            int i = 1;
            while (true) {

                if (kitchen.haveAllPortionsBeenDelivered()) {                   
                    break;
                }
                bar.alertTheWaiter();                                           // DeTP
                if(i<GeneralRepositoryStub.nstudents)
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
    public EntityStates.ChefState getChefState() {
        return chefState;
    }

    /**
     *  Sets a new state for the Chef Thread if current state is different from the new state. 
     *
     *  @param newChefState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    public boolean setChefState(EntityStates.ChefState newChefState) {
        if (chefState == null || chefState != newChefState) {
            
            chefState = newChefState;
            return true;
        }
        return false;
    }

}
