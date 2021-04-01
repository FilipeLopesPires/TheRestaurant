package serverSide;

import clientSide.GeneralRepositoryStub;
import comInf.*;

import java.net.*;
import genclass.GenericIO;
import java.util.concurrent.TimeoutException;

/**
 *
 *  General description: The Restaurant Problem - monitor-based solution.
 *  Main Class for the Table project.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class TableMain {
    
    /**
     * Program's Data
     */
    
    private static final int portNumb = 22733;
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
        
        Table table;
        TableAdapter tableAdapter;
        
        ServerCom scon, sconi;
        ComHandler comhandler;
        
        /* Problem Initialization */
        
        repo = new GeneralRepositoryStub(args[1], args[2]);
        //repo = new GeneralRepositoryStub("127.0.0.1", "22742");
        scon = new ServerCom(portNumb);
        scon.start();
        
        table = new Table(repo);
        tableAdapter = new TableAdapter(table);

        GenericIO.writelnString ("O serviço foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");

        /* Start of the Simulation */
        
        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();
                
                comhandler = new ComHandler(sconi, tableAdapter);
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
