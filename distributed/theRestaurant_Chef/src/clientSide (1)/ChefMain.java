package clientSide;

/**
 *
 *  General description: The Restaurant Problem - monitor-based solution.
 *  Main Class for the Chef project.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class ChefMain {
    
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
        Chef chef;

        /* Problem Initialization */
        
        repo = new GeneralRepositoryStub(args[1], args[2]);

        bar = new BarStub( args[3], args[4]);
        kitchen = new KitchenStub( args[5], args[6]);
         
        chef = new Chef(kitchen, bar);

        /* Start of the Simulation */
        
        chef.start();
        
        /* Wait for the End of the Simulation */
        
        try {
           chef.join();
        } catch (Exception e) { }
        kitchen.shutdown();
        bar.shutdown();
        repo.shutdown();
    }
}
