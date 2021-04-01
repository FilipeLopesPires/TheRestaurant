package serverSide;

import clientSide.WaiterInterface;
import clientSide.*;
import comInf.*;
import genclass.GenericIO;

/**
 *  Definition of the service provider agent thread for this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 *  The communication is based on the exchange of object of type Message through a TCP channel.
 */
public class ComHandler extends Thread implements StudentInterface, WaiterInterface, ChefInterface {
    
    /**
     *  Launched threads counter
     */
    private static int nProxy = 0;

    /**
     *  Communication channel
     */
    private ServerCom sconi;

    //private TableAdapter tableAdapter;
    //private BarAdapter barAdapter;
    /**
     *  Interface to the shared region
     */
    private KitchenAdapter kitchenAdapter;
    
    /*
     *  Student Attributes
     */
    
    private int ID;                                                             // Student Thread's ID if representing a Student
    private EntityStates.StudentState studentState;                    // Student Thread's State if representing a Student
    private EntityStates.ArrivalOrder arrivedIn;                       // Student Thread's place of arrival if representing a Student
    
    /*
     *  Waiter Attributes
     */
    
    private EntityStates.WaiterState waiterState;                      // Thread's State if representing Waiter
    private char alternative;                                                   // tells the Waiter what to do
    
    /*
     *  Chef Attributes
     */
    
    private EntityStates.ChefState chefState;                          // Thread's State if representing Chef
    
    /*
     *  thread Methods
     */
    /*
    public ComHandler (ServerCom sconi, TableAdapter obj) {
        super ("Proxy_" + ComHandler.getProxyId ());

        this.sconi = sconi;
        this.tableAdapter = obj;
    }
    
    public ComHandler (ServerCom sconi, BarAdapter obj) {
        super ("Proxy_" + ComHandler.getProxyId ());

        this.sconi = sconi;
        this.barAdapter = obj;
    }
    */
    /**
     *  Instantiation of the service provider agent thread.
     *  @param sconi communication channel
     *  @param obj shared region's interface
     */
    public ComHandler (ServerCom sconi, KitchenAdapter obj) {
        super ("Proxy_" + ComHandler.getProxyId ());

        this.sconi = sconi;
        this.kitchenAdapter = obj;
    }
    /*
    public ComHandler (ServerCom sconi, serverSide.GeneralRepositoryAdapter obj) {
        super ("Proxy_" + ComHandler.getProxyId ());

        this.sconi = sconi;
        this.repoAdapter = obj;
    }
    */
    
    /**
     *  Service provider agent thread's lifecycle.
     */
    @Override
    public void run () {
        Message inMessage = null,                                               // mensagem de entrada
                outMessage = null;                                              // mensagem de saída

        inMessage = (Message) sconi.readObject ();                              // ler pedido do cliente
        try {
            //outMessage = tableAdapter.processAndReply (inMessage);              // processá-lo
            //outMessage = barAdapter.processAndReply (inMessage);                // processá-lo
            outMessage = kitchenAdapter.processAndReply (inMessage);            // processá-lo
        } catch (MessageException e) { 
            GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                         // enviar resposta ao cliente
        sconi.close ();                                                         // fechar canal de comunicação
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
            cl = Class.forName ("serverSide.ComHandler");
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
    
    /**
     *  Getter function that serves as an auxiliar for the entities, to know a Student's ID. 
     *
     *  @return Returns the ID of the Student Thread.
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     *  Getter function that serves as an auxiliar for the entities, to know a Student's State. 
     *
     *  @return Returns the current state of the Student Thread.
     */
    @Override
    public EntityStates.StudentState getStudentState() {
        
        return studentState;
    }

    /**
     *  Sets a new state for the Student Thread if current state is different from the new state. 
     *
     *  @param newStudentState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    @Override
    public boolean setStudentState(EntityStates.StudentState newStudentState) {
        if (studentState == null || studentState != newStudentState) {
            studentState = newStudentState;
           
            return true;
        }
        return false;
    }
    
    /**
     *  Getter function that serves as an auxiliar for the entities, to know a Student's place of arrival. 
     * 
     *  @return Returns the place of arrival of the Student.
     */
    @Override
    public EntityStates.ArrivalOrder getArrivalOrder(){
        return arrivedIn;
    }
    
    /**
     *  Getter function that serves as an auxiliar for the entities, to know the Waiter's State. 
     *
     *  @return Returns the current state of the Waiter Thread.
     */
    @Override
    public EntityStates.WaiterState getWaiterState() {
        return waiterState;
    }

    /**
     *  Sets a new state for the Waiter Thread if current state is different from the new state. 
     *
     *  @param newWaiterState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    @Override
    public boolean setWaiterState(EntityStates.WaiterState newWaiterState) {
        if (waiterState == null || waiterState != newWaiterState) {
            
            waiterState = newWaiterState;
            //System.out.println("Changin my internal state to: "+ newWaiterState);
            return true;
        }
        return false;
    }
    
    /**
     *  Getter function that serves as an auxiliar for the entities, to know the Chef's State. 
     *
     *  @return Returns the current state of the Chef Thread.
     */
    @Override
    public EntityStates.ChefState getChefState() {
        return chefState;
    }

    /**
     *  Sets a new state for the Chef Thread if current state is different from the new state. 
     *
     *  @param newChefState State to replace the current Thread's state.
     *  @return Returns true if state is changed, false if not.
     */
    @Override
    public boolean setChefState(EntityStates.ChefState newChefState) {
        if (chefState == null || chefState != newChefState) {
            
            chefState = newChefState;
            return true;
        }
        return false;
    }
}
