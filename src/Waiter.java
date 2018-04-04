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
    
    public enum WaiterState {                                   // Internal State Enum
        APPRAISING_SITUATION,
        PRESENTING_THE_MENU,
        TAKING_THE_ORDER,
        PLACING_THE_ORDER,
        WAITING_FOR_PORTION,
        PROCESSING_THE_BILL,
        RECEIVING_PAYMENT;
    }
    private static Kitchen kitchen;                             // Kitchen Shared Region Access Point
    private static Bar bar;                                     // Bar Shared Region Access Point
    private static Table table;                                 // Table Shared Region Access Point
    private WaiterState waiterState;                            // Thread's State
    private char alternative;                                   // tells the Waiter what to do
    
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
        waiterState = WaiterState.APPRAISING_SITUATION;     // Initial State
    }
    
    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Waiter Thread.
     */
    @Override
    public void run () {
        while((alternative = bar.lookAround(this)) != 'e') {    // block while nothing happens
            switch(alternative) {
                case 's':
                    table.saluteTheStudent();
                    bar.returnToTheBar(this);
                    break;
                case 'o':
                    table.getThePad(bar,this);
                    kitchen.handTheNoteToTheChef();             // block while chef does not start preparation
                    bar.returnToTheBar(this);
                    break;
                case 'p':
                    while(!kitchen.haveAllClientsBeenSirved()) {
                        kitchen.collectPortion();               // block while chef does not deliver him the portion
                        table.deliverPortion();
                    }
                    bar.returnToTheBar(this);
                    break;
                case 'b':
                    bar.prepareTheBill();
                    table.presentTheBill();
                    bar.returnToTheBar(this);
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
     *  Returns the current state of the Waiter Thread.
     *  @return Element from the WaiterState Enumerate.
     */
    public WaiterState getWaiterState() { return waiterState; }
    /**
     *  Sets a new state for the Waiter Thread.
     *  @param newWaiterState State to replace the current Thread's state.
     */
    public void setWaiterState(WaiterState newWaiterState) { waiterState = newWaiterState; }
    
}
