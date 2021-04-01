package clientSide;

import comInf.*;
import genclass.GenericIO;

/**
 *  Definition of the Stub to the General Repository shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class GeneralRepositoryStub {
    
    /* Attributes */
    
    /**
     *  Number of Students going to the restaurant
     */
    public static int nstudents = 7;                                            // number of Students that are going to the Restaurant
    /**
     *  Number of courses to be eaten by each Student
     */
    public static int ncourses = 3;                                             // number of courses per Student
    /**
     *  Name of the computational system where the server is localized
     */
    private String serverHostName;
    /**
     *  Number of the server's listening port
     */
    private int serverPortNumb;

    /* Constructors */

    /**
     *  Instantiation of the GeneralRepository Stub.
     *  @param serverHostName name of the computational system where the server is localized
     *  @param serverPortNumb number of the server's listening port
     */
    public GeneralRepositoryStub(String serverHostName, String serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = Integer.valueOf(serverPortNumb);
    }
    
    /* Methods */
    
    /**
     *  Internal update of the information about the Chef's state (server solicitation).
     *
     *  @param newChefState state to replace the Chef's current state
     */
    public synchronized void updateChefState(EntityStates.ChefState newChefState) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.UPDCHEF, newChefState);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        cc.close();
    }
    
    /**
     *  Internal update of the information about the Waiter's state (server solicitation).
     *
     *  @param newWaiterState state to replace the Waiter's current state
     */
    public synchronized void updateWaiterState(EntityStates.WaiterState newWaiterState) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.UPDWAITER, newWaiterState);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        cc.close();
    }
    
    /**
     *  Internal update of the information about a Student's state (server solicitation).
     *
     *  @param newStudentState state to replace the Student's current state
     *  @param studentID ID of the Student who's state will be updated
     */
    public synchronized void updateStudentState(EntityStates.StudentState newStudentState, int studentID) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.UPDSTUDENT, studentID, newStudentState);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        cc.close();
    }
    
    /**
     *  Update of the current course, saving it on the text file (server solicitation).
     */
    public synchronized void updateCourse(int nCourse) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.UPDCOURSE, nCourse, 2);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        cc.close();
    }
    
    /**
     *  Verification of all Students' states (wether they have left the restaurant or not) for the Waiter to close the restaurant (server solicitation).
     *  @return true if all Students have left, false if not
     */
    public boolean isEveryStudentEating() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        boolean return_value = false;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.ISSTUDENTSEATING);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if (inMessage.getMsgType() != Message.TRUE && inMessage.getMsgType() != Message.FALSE) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(inMessage.getMsgType() == Message.TRUE)
            return_value = true;
       
        cc.close();
        return return_value;
    }
    
    /**
     *  Verification of wether all the Students are eating the current course or not (server solicitation).
     *  @return true if every Student is eating, false if not
     */
    public boolean haveAllStudentLeft(){
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        boolean return_value = false;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.HAVESTUDENTSLEFT);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if (inMessage.getMsgType() != Message.TRUE && inMessage.getMsgType() != Message.FALSE) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        if(inMessage.getMsgType() == Message.TRUE)
            return_value = true;
       
        cc.close();
        return return_value;
    }
    
    /**
     *  Process of closing the server once it is no longer necessary (server solicitation).
     */
    public void shutdown(){
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.SHUTDOWN);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        System.out.println("Received an ok to leave");
        // processamento da msg de retorno
        
        
        cc.close();
    }
}
