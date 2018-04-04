import genclass.GenericIO;
/**
 * 
 *  General Description:
 *      Definition of the Bar Shared Region - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Bar {
    
    /**
     *  Internal Data
     */
    
    private int nstudents;                                  // number of Students that are going to the Restaurant
    private int arrivedStudents = 0;                        // number of Students that have arrived at the Restaurant
    private char alternative = '\0';                        // tells the Waiter what to do
                            /**
                             * 's' means salute student
                             * 'o' means take table order
                             * 'p' means wait for portions
                             * 'b' means process table bill
                             * 'g' means say goodbye
                             * 'e' means end
                             */
    
    /**
     *  Constructor
     *  Allocates a new Bar Shared Region.
     *  @param waiter Waiter Thread responsible for the Restaurant's Bar
     */
    public Bar(int nstudents){
        this.nstudents = nstudents;
    }
    
    /**
     *  Bar's Methods
     */
    
    /**
     * Used by Waiter to return his current alternative (action to be done) or empty if there is nothing to be done.
     * @return Character representing the current alternative
     */
    public synchronized char lookAround(Waiter waiter) {
        waiter.setWaiterState(Waiter.WaiterState.APPRAISING_SITUATION);
        GenericIO.writelnString("Waiter is " + waiter.getWaiterState());
        while(alternative== '\0') {
            try{
                wait();                              // the Waiter Thread must block while nothing happens 
            } catch (InterruptedException e) {}
        }
        return alternative;
    }
    /**
     *  Used by Student to inform the Waiter of his arrival and returns the Student's place of arrival.
     *  @return Element from the ArrivalOrder Enumerate
     */
    public synchronized TheRestaurantMain.ArrivalOrder enter() {
        arrivedStudents++;
        alternative = 's';
        notifyAll();                                   // the Student Thread must notify the Waiter Thread when he arrives at the Restaurant
        if(arrivedStudents == 1) {
            return TheRestaurantMain.ArrivalOrder.FIRST;
        } else if (arrivedStudents == nstudents) {
            alternative = 'o';
            notifyAll();                               // the last Student Thread to arrive must also notify the Waiter for him to give the students the pad
            return TheRestaurantMain.ArrivalOrder.LAST;
        }
        return TheRestaurantMain.ArrivalOrder.MIDDLE;
    }
    /**
     *  Used by Waiter to return to the Bar and reset the alternative (action to be done).
     */
    public synchronized void returnToTheBar(Waiter waiter) { 
        alternative = '\0'; 
        waiter.setWaiterState(Waiter.WaiterState.APPRAISING_SITUATION);
    }
    /**
     *  Used by Student to 
     */
    public synchronized void callTheWaiter() {
        
    }
    /**
     *  Used by Chef to 
     */
    public synchronized void alertTheWaiter() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void signalTheWaiter() {
        
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void prepareTheBill() {
        
    }
    /**
     *  Used by Student to 
     */
    public synchronized void shouldHaveArrivedEarlier() {
        
    }
    /**
     *  Used by Waiter to 
     */
    public synchronized void sayGoodbye() {
        
    }
    
    /**
     *  Auxiliar Methods
     */
    
}
