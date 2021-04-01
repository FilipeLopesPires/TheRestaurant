package SharedRegions;

import Main.*;
import Entities.*;

/**
 *  General Description:
 *      Definition of the Kitchen Shared Region - monitor-based solution.
 * 
 *  Authors Filipe Pires (85122) S Isaac dos Anjos (78191)
 */
public class Kitchen {
    
    /**
     *  Internal Data
     */
    
    private static GeneralRepository repo;                                      // Restaurant's Repository
   
    private volatile boolean order,                                             // tells the Chef when an order is delivered
                             portionReady;                                      // tells the Waiter if the current portion is ready to be delivered at the table
    private volatile int     deliveredPortions,                                 // tells the Chef how many portions of the current course have been delivered
                             totalPortions;                                     // tells the Chef how many portions have been delivered (of all courses)
    
    /**
     *  Constructor
     *  Allocates a new Kitchen Shared Region.
     * 
     *  @param repo GeneralRepository object for the entities states' to be monitored through a file log.
     */
    public Kitchen(GeneralRepository repo){
        this.repo = repo;
        order = false;
        portionReady = false;
        deliveredPortions = 0;
        totalPortions = 0;
    }
    
    /**
     *  Kitchen's Methods
     */
    
    /**
     *  Used by Chef to wait for an order to be handed to him.
     */
    public synchronized void watchTheNews() {
        if(((Chef)Thread.currentThread()).setChefState(Chef.ChefState.WFAO) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
        while(!order) {
            try {
                this.wait();                                                    // Chef blocks while there is nothing to be prepared
            } catch (InterruptedException ie) {}
        }
    }
    
    /**
     *  Used by Waiter to notify the Chef once the Students have chosen their courses.
     */
    public synchronized void handTheNoteToTheChef() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.PTO) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        order = true;                                                           // Chef now knows the students' orders                                            
        this.notify();                                                          // Waiter notifies the Chef that an order has been delivered     
    }
    
    /**
     *  Used by Chef to start preparing the order.
     * 
     * @param nCourse integer variable holding the number of the current course
     */
    public synchronized void startPreparation(int nCourse) {
        repo.updateCourse(nCourse);
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.PTC) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        // simulates time of preparing the 1st course
        } catch (Exception e) {}
    }
    
    /**
     *  Used by Chef to start dishing the portions of the current course.
     * 
     *  
     */
    public synchronized void proceedToPresentation() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.DiTP) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
                                                   
        portionReady = true;
        deliveredPortions = 0;
    }
    
    /**
     *  Used by Chef to verify if all portions of the current course have been delivered to the Waiter. 
     * 
     *  @return Returns true if all portions of the current course have been delivered, false if not.
     */
    public synchronized boolean haveAllPortionsBeenDelivered() {
        if(deliveredPortions == TheRestaurantMain.nstudents) {
            return true;
        }
        return false;
    }
    
    /**
     *  Used by Chef to deliver one portion to the Waiter.
     */
    public synchronized void haveNextPortionReady() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.DiTP) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        if(portionReady)
            try{wait();}catch(Exception e){}
        
        portionReady = true;
        this.notifyAll();                                                       // Chef notifies the Waiter that the portion is ready to be delivered at the Table
    }
    
    /**
     *  Used by Waiter to collect the portion from the Chef.
     */
    public synchronized void collectPortion() {
        if (((Waiter)Thread.currentThread()).setWaiterState(Waiter.WaiterState.WFP) ) {
            repo.updateWaiterState(((Waiter)Thread.currentThread()).getWaiterState());
        }
        
        while(!portionReady) {
            try {
                this.wait();                                                    // Waiter blocks while Chef has not yet made next portion ready
            } catch (InterruptedException ie) {}
        }
        deliveredPortions++; totalPortions++;
        
        portionReady = false;
        this.notifyAll();                                                       // Waiter notifies the Chef once he collects the portion
    }
    
    /**
     *  Used by Waiter to verify if all portions of the current course have been delivered to the Students.
     * 
     *  @return Returns true if all portions of the current course have been delivered, false if not.
     */
    public synchronized boolean haveAllStudentsBeenServed() {
        if(deliveredPortions == TheRestaurantMain.nstudents ) {
            return true;
        }
        return false;
    }
    
    /**
     *  Used by Chef to verify if all portions from all courses have been delivered to the Waiter.
     * 
     *  @return Returns true if all portions from all courses have been delivered, false if not.
     */
    public synchronized boolean hasTheOrderBeenCompleted() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.DeTP) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
        if(totalPortions == TheRestaurantMain.ncourses*TheRestaurantMain.nstudents){
            return true;
        }
       return false;
    }
    
    /**
     *  Used by Chef to continue preparing the remaining courses from the order.
     * 
     * @param nCourse integer variable holding the number of the current course
     */
    public synchronized void continuePreparation(int nCourse) {
        repo.updateCourse(nCourse+1);
        ((Chef)Thread.currentThread()).setChefState(Chef.ChefState.PTC);
        
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        // simulates time of preparing the 2nd & 3r courses
        } catch (Exception e) {}
        portionReady = true;
    }
    
    /**
     *  Used by Chef to close the service and clean the Kitchen.
     */
    public synchronized void cleanUp() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.CS) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
    }
}
