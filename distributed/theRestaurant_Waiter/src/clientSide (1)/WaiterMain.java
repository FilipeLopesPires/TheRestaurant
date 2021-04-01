package clientSide;

/**
 *
 *  General description: The Restaurant Problem - monitor-based solution.
 *  Main Class for the Waiter project.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class WaiterMain {
    
    /**
     * Program's Data
     */
    
    /**
     * Main method.
     *
     * @param args runtime arguments
     */
    public static void main(String[] args) {

        /* Repository, Entities & Shared Regions */
        
        GeneralRepositoryStub repo;                                             // Restaurant's Repository
        
        BarStub bar;                                                            // Restaurant's Bar
        KitchenStub kitchen;                                                    // Restaurant's Kitchen
        TableStub table;
        Waiter waiter;
        

        /* Problem Initialization */
        
        repo = new GeneralRepositoryStub(args[1], args[2]);

        bar = new BarStub( args[3], args[4]);
        kitchen = new KitchenStub( args[5], args[6]);
         
        table = new TableStub(args[7], args[8]);
       
        waiter = new Waiter(kitchen, bar, table);

        /* Start of the Simulation */
        
        waiter.start();
        
        /* Wait for the End of the Simulation */
        
        try {
           waiter.join();
        } catch (Exception e) { }
        table.shutdown();
        kitchen.shutdown();
        bar.shutdown();
        repo.shutdown();
    }
}
