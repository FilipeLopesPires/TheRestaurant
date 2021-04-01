package serverSide;

import clientSide.GeneralRepositoryStub;
import comInf.*;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

/**
 *
 *  General description: The Restaurant Problem - monitor-based solution.
 *  Main Class for the Kitchen project.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class KitchenMain {
    
    /**
     * Program's Data
     */
    
    private static final int portNumb = 22731;
    public static boolean waitConnection;
    static int shutdown = 0;
    
    /**
     * Main method.
     *
     * @param args runtime arguments
     */
    public static void main(String[] args) {
        
        /* Repository, Entities & Shared Regions */
        
        GeneralRepositoryStub repo;
        
        Kitchen kitchen;
        KitchenAdapter kitchenAdapter;
        
        ServerCom scon, sconi;
        ComHandler comhandler;
        
        /* Problem Initialization */
        
        repo = new GeneralRepositoryStub(args[1], args[2]);
       
        scon = new ServerCom(portNumb);
        scon.start();
        
        kitchen = new Kitchen(repo);
        kitchenAdapter = new KitchenAdapter(kitchen);

        GenericIO.writelnString ("O serviço foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");

        /* Start of the Simulation */
        
        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                
                comhandler = new ComHandler(sconi, kitchenAdapter);
                comhandler.start ();
            } catch(SocketTimeoutException e) {}
        }
        
        /* Wait for the End of the Simulation */
        
        scon.end();
        GenericIO.writelnString ("O servidor foi desactivado.");
    }
    
    /**
     *  Process of closing the server once it is no longer necessary.
     */
    public static void shutdown() {
        shutdown++;
        if(shutdown == 2)
            waitConnection = false;
    }
}
