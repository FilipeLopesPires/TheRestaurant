package SharedRegions;
import Assets.Meals;
import Main.*;
import Entities.*;
import genclass.GenericIO;
import java.util.HashMap;

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
   
    private volatile boolean order;                                                      // tells the Chef when an order is delivered
    private volatile boolean portionReady;                                               // tells the Waiter if the current portion is ready to be delivered at the table
    private volatile int deliveredPortions;                                              // tells the Chef how many portions of the current course have been delivered
    private volatile int totalPortions;                                                  // tells the Chef how many portions have been delivered (of all courses)
    
    /**
     *  Constructor
     *  Allocates a new Kitchen Shared Region.
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
        order = true;
                                               // Chef now knows the students' orders
        this.notifyAll();                                                       // Waiter notifies the Chef that an order has been delivered
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void startPreparation() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.PTC) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        // simulates time of preparing the 1st course
        } catch (Exception e) {}
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void proceedToPresentation(int nCourse) {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.DiTP) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        repo.updateCourse(nCourse);                                             //   !!!   algo esta a correr mal quando corre este update   !!!
        
       
        deliveredPortions = 0;
    }
    /**
     *  Used by Chef to 
     *  @return 
     */
    public synchronized boolean haveAllPortionsBeenDelivered() {
       // System.out.println("Condition:"+deliveredPortions+"=="+TheRestaurantMain.nstudents);
        if(deliveredPortions == TheRestaurantMain.nstudents) {
            return true;
        }
        return false;
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void haveNextPortionReady() {
        
        portionReady = true;
        this.notifyAll();                                                       // Chef notifies the Waiter that the portion is ready to be delivered at the Table
       
    }
    /**
     *  Used by Waiter to 
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
        portionReady = false;
        deliveredPortions++; totalPortions++;
    }
    /**
     *  Used by Waiter to 
     *  @return 
     */
    public synchronized boolean haveAllStudentsBeenServed() {
        if(totalPortions == (TheRestaurantMain.nstudents * (TheRestaurantMain.ncourses))) {
            return true;
        }
        return false;
    }
    /**
     *  Used by Chef to 
     *  @return 
     */
    public synchronized boolean hasTheOrderBeenCompleted() {
       // System.out.println("Condition: "+totalPortions+"=="+((TheRestaurantMain.nstudents * (TheRestaurantMain.ncourses))));
        return haveAllStudentsBeenServed();                                     //   !!!   Problemas aqui e na funcao acima!   !!!
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void continuePreparation() {
        if (((Chef)Thread.currentThread()).setChefState(Chef.ChefState.PTC) ) {
            repo.updateChefState(((Chef)Thread.currentThread()).getChefState());
        }
        portionReady=false;
        try {
            Thread.sleep((int) (1000 * Math.random ()));                        // simulates time of preparing the 2nd & 3r courses
        } catch (Exception e) {}
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
