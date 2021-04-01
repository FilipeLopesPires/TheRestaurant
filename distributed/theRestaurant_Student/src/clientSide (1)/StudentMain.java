package clientSide;

/**
 *
 *  General description: The Restaurant Problem - monitor-based solution.
 *  Main Class for the Student project.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class StudentMain {

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
        Student[] students = new Student[GeneralRepositoryStub.nstudents];      // list of Students that are going to the Restaurant
        BarStub bar;                                                            // Restaurant's Bar
        TableStub table;                                                        // Restaurant's Table

        /* Problem Initialization */
        
        repo = new GeneralRepositoryStub(args[1], args[2]);
       
        bar = new BarStub( args[3], args[4]);
       
        table = new TableStub(args[5], args[6]);

        for (int i = 0; i < GeneralRepositoryStub.nstudents; i++) {
            students[i] = new Student(bar, table);
        }

        /* Start of the Simulation */
        
        for (int i = 0; i < GeneralRepositoryStub.nstudents; i++) {
            students[i].start();
        }
        
        /* Wait for the End of the Simulation */
        
        try {
            for (int i = 0; i < GeneralRepositoryStub.nstudents; i++) {
                students[i].join();
            }
        } catch (InterruptedException e) { }
        table.shutdown();
        bar.shutdown();
        repo.shutdown();
    }
}
