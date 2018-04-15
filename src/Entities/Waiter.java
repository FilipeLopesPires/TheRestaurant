package Entities;
import Main.*;
import SharedRegions.*;

/**
 * 
 *  General Description:
 *      Definition of the Waiter Thread - monitor-based solution.
 * 
 *  @authors Filipe Pires (85122) & Isaac dos Anjos (78191)
 */
public class Waiter extends Thread {
    
    /**
     *  Internal Data
     */
    
    public enum WaiterState {                                                   // Internal State Enum
        AS,
        PTM,
        TTO,
        PTO,
        WFP,
        PTB,
        RP;
    }
    
    private Kitchen kitchen;                                                    // Kitchen Shared Region Access Point
    private Bar bar;                                                            // Bar Shared Region Access Point
    private Table table;                                                        // Table Shared Region Access Point
    private WaiterState waiterState;                                            // Thread's State
    private char alternative;                                                   // tells the Waiter what to do
    
    /**
     *  Constructor
     *  Allocates a new Waiter Thread object.
     *  @param kitchen shared region object for the Waiter Thread to access the Kitchen
     *  @param bar shared region object for the Waiter Thread to access the Bar
     *  @param table shared region object for the Waiter Thread to access the Table
     */
    public Waiter(Kitchen kitchen, Bar bar, Table table) {
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
        waiterState = WaiterState.AS;                                           // Initial State
    }
    
    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Waiter Thread.
     */
    @Override
    public void run () {
        while((alternative = bar.lookAround()) != 'e') {                        // AS
            switch(alternative) {
                case 's':
                    table.saluteTheStudent();                                   // PTM
                    bar.returnToTheBar();
                    break;
                case 'o':
                    table.getThePad();                                          // TTO
                    kitchen.handTheNoteToTheChef();                             // PTO
                    bar.returnToTheBar();
                    break;
                case 'p':
                    while(!kitchen.haveAllClientsBeenServed()) {                // WFP
                        kitchen.collectPortion();
                        table.deliverPortion();
                        // waiter must alert the students here (probably going to have to create a function here from table)
                        bar.returnToTheBar();
                    }
                    break;
                case 'b':
                    bar.prepareTheBill();                                       // PTB
                    table.presentTheBill();                                     // RP
                    bar.returnToTheBar();
                    break;
                case 'g':
                    bar.sayGoodbye();
                    break;
            }
        }
    }
    
    /**
     *  Auxiliar methods
     */
    
    /**
     * Returns the current state of the Waiter Thread.
     * @return WaiterState variable holding the Waiter's state.
     */
    public WaiterState getWaiterState() {
        return waiterState;
    }

    /**
     *  Sets a new state for the Waiter Thread if current state is different from the new state.
     *  @param newWaiterState State to replace the current Thread's state.
     */
    public boolean setWaiterState(WaiterState newWaiterState) {
        if(waiterState != newWaiterState) {
            waiterState = newWaiterState;
            return true;
        }
        return false;
    }
    
}
