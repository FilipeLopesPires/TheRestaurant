package serverSide;

import comInf.ServerCom;
import genclass.GenericIO;
import comInf.Message;
import comInf.MessageException;

/**
 *  Definition of the service provider agent thread for this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 *  The communication is based on the exchange of object of type Message through a TCP channel.
 */
public class ComHandler extends Thread {

    /**
     *  Launched threads counter
     */
    private static int nProxy = 0;

    /**
     *  Communication channel
     */
    private ServerCom sconi;

    /**
     *  Interface to the shared region
     */
    private serverSide.GeneralRepositoryAdapter repoAdapter;
    
    /**
     *  Instantiation of the service provider agent thread.
     *  @param sconi communication channel
     *  @param obj shared region's interface
     */
    public ComHandler (ServerCom sconi, serverSide.GeneralRepositoryAdapter obj) {
        super ("Proxy_" + ComHandler.getProxyId ());

        this.sconi = sconi;
        this.repoAdapter = obj;
    }

    /**
     *  Service provider agent thread's lifecycle.
     */
    @Override
    public void run () {
        Message inMessage = null,                                      // mensagem de entrada
                outMessage = null;                      // mensagem de saída

        inMessage = (Message) sconi.readObject ();                     // ler pedido do cliente
        
        try {
            
            outMessage = repoAdapter.processAndReply (inMessage);         // processá-lo
        } catch (MessageException e) { 
            GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // enviar resposta ao cliente
        sconi.close ();                                                // fechar canal de comunicação
    }

    /**
     *  Creation of the instantiation identifier.
     *  @return instantiation identifier
     */
    private static int getProxyId () {
        Class<?> cl = null;                                  // representação do tipo de dados ClientProxy na máquina
                                                             //   virtual de Java
        int proxyId;                                         // identificador da instanciação

        try {
            cl = Class.forName ("ServerSide.ComHandler");
        } catch (ClassNotFoundException e) { 
            GenericIO.writelnString ("O tipo de dados ClientProxy não foi encontrado!");
            e.printStackTrace ();
            System.exit (1);
        }

        synchronized (cl) { 
            proxyId = nProxy;
            nProxy += 1;
        }

        return proxyId;
    }

    /**
     *  Getter function to retrieve the communication channel.
     *  @return communication channel
     */
    public ServerCom getScon () {
       return sconi;
    }
}
