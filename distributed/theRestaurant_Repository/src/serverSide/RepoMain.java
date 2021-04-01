package serverSide;
import comInf.ServerCom;
import genclass.GenericIO;
import java.net.SocketTimeoutException;

/**
 *
 *  General description: The Restaurant Problem - monitor-based solution.
 *  Main Class for the Repository project.
 *
 *  Authors Filipe Pires (85122) and Isaac dos Anjos (78191)
 */
public class RepoMain {

    private static final int portNumb = 22730;
    public static boolean waitConnection;                              // sinalização de actividade
    public static int shutdown;

    /**
     * Main method.
     *
     * @param args runtime arguments
     */
    public static void main (String [] args) {

        ServerCom scon, sconi;                               // canais de comunicação
        ComHandler comhandler;                                // thread agente prestador do serviço
        GeneralRepository repo = new GeneralRepository("output.txt");

        GeneralRepositoryAdapter generalRepositoryA = new GeneralRepositoryAdapter(repo);
        /* estabelecimento do servico */

        scon = new ServerCom (portNumb);                     // criação do canal de escuta e sua associação
        scon.start ();                                       // com o endereço público
        //table = new Table (repo);                           // activação do serviço

        GenericIO.writelnString ("O serviço foi estabelecido!");
        GenericIO.writelnString ("O servidor esta em escuta.");

        /* processamento de pedidos */

        waitConnection = true;
        while (waitConnection)
            try { 
                sconi = scon.accept ();                          // entrada em processo de escuta
                comhandler = new ComHandler (sconi, generalRepositoryA);  // lançamento do agente prestador do serviço
                comhandler.start ();
            } catch (SocketTimeoutException e) { }
        scon.end ();                                         // terminação de operações
        GenericIO.writelnString ("O servidor foi desactivado.");
    }
    
    /**
     *  Process of closing the server once it is no longer necessary.
     */
    public static void shutdown(){
        shutdown++;
        if(shutdown == 3)
            waitConnection = false;
    }
}
