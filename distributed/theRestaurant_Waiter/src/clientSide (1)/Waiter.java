package clientSide;

import comInf.*;

/**
 *  General Description:
 *      Definition of the Waiter Thread - monitor-based solution.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class Waiter extends Thread implements WaiterInterface {
    
    /**
     *  Internal Data
     */
    
    private KitchenStub kitchen;                                                // Kitchen Shared Region Access Point
    private BarStub bar;                                                        // Bar Shared Region Access Point
    private TableStub table;                                                    // Table Shared Region Access Point
    private EntityStates.WaiterState waiterState;                      // Thread's State
    private char alternative;                                                   // tells the Waiter what to do
    
    /**
     *  Constructor Allocates a new Waiter Thread object.
     *
     *  @param kitchen shared region object for the Waiter Thread to access the Kitchen
     *  @param bar shared region object for the Waiter Thread to access the Bar
     *  @param table shared region object for the Waiter Thread to access the Table
     */
    public Waiter(KitchenStub kitchen, BarStub bar, TableStub table) {
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
            System.out.println("Cycle");
            switch (alternative) {
                case 's':
                    System.out.println("Time to salute");
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
    public EntityStates.WaiterState getWaiterState() {
        return waiterState;
    }

    /**
     *  Sets a new state for the Waiter Thread if current state is different from the new state. 
     *
     *  @param newWaiterState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    public boolean setWaiterState(EntityStates.WaiterState newWaiterState) {
        if (waiterState == null || waiterState != newWaiterState) {
            
            waiterState = newWaiterState;
            //System.out.println("Changin my internal state to: "+ newWaiterState);
            return true;
        }
        return false;
    }
}
