package Entities;

import SharedRegions.*;


/**
 *  General Description:
 *      Definition of the Waiter Thread - monitor-based solution.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Waiter extends Thread {

    /**
     *  Internal Data
     */
    
    /**
     *  Internal Enumerate holding all the possible states of the entity.
     */
    public enum WaiterState {                                                   // Internal State Enum
        /**
         *  APPRAISING_SITUATION – blocking state with transition (initial / final state); the waiter is waken up by one of the following operations: alertThe- Waiter of the chef, enter and exit of all the students, callTheWaiter of the first student to sit at the table, signalTheWaiter of the last student to finish a course and shouldHaveArrivedEarlier of the last student to sit at the table; transition occurs when the last student has left the restaurant
         */
        AS,
        /**
         *  PRESENTING_THE_MENU – blocking state; the waiter is waken up by the operation readTheMenu of the student
         */
        PTM,
        /**
         *  TAKING_THE_ORDER – blocking state; the waiter is waken up by the operation describeTheOrder of the student
         */
        TTO,
        /**
         *  PLACING_THE_ORDER – blocking state; the waiter is waken up by the operation startPreparation of the chef
         */
        PTO,
        /**
         *  WAITING_FOR_PORTION – blocking state; the waiter is waken up by the operation haveAllPortionsBeenDelivered of the chef
         */
        WFP,
        /**
         *  PROCESSING_THE_BILL – transition state
         */
        PTB,
        /**
         *  RECEIVING_PAYMENT – blocking state; the waiter is waken up by the operation honorTheBill of the student
         */
        RP;
    }

    private Kitchen kitchen;                                                    // Kitchen Shared Region Access Point
    private Bar bar;                                                            // Bar Shared Region Access Point
    private Table table;                                                        // Table Shared Region Access Point
    private WaiterState waiterState;                                            // Thread's State
    private char alternative;                                                   // tells the Waiter what to do
    
    /**
     *  Constructor Allocates a new Waiter Thread object.
     *
     *  @param kitchen shared region object for the Waiter Thread to access the Kitchen
     *  @param bar shared region object for the Waiter Thread to access the Bar
     *  @param table shared region object for the Waiter Thread to access the Table
     */
    public Waiter(Kitchen kitchen, Bar bar, Table table) {
        this.kitchen = kitchen;
        this.bar = bar;
        this.table = table;
        //waiterState = WaiterState.AS;                                           // Initial State
    }

    /**
     *  Life cycle
     */
    
    /**
     *  Thread's run method. Execution of the Waiter Thread.
     */
    @Override
    public void run() {
        while ((alternative = bar.lookAround()) != 'e') {                       // AS
            switch (alternative) {
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
                    while (!kitchen.haveAllStudentsBeenServed()) {
                        kitchen.collectPortion();                               // WFP
                        table.deliverPortion();
                    }
                    bar.returnToTheBar();
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
     *  Getter function that serves as an auxiliar for the entities, to know the Waiter's State. 
     *
     *  @return Returns the current state of the Waiter Thread.
     */
    public WaiterState getWaiterState() {
        return waiterState;
    }

    /**
     *  Sets a new state for the Waiter Thread if current state is different from the new state. 
     *
     *  @param newWaiterState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    public boolean setWaiterState(WaiterState newWaiterState) {
        if (waiterState == null || waiterState != newWaiterState) {
            waiterState = newWaiterState;
            return true;
        }
        return false;
    }
}
