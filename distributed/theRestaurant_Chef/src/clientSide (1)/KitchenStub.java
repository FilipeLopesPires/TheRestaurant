package clientSide;

import genclass.GenericIO;
import comInf.*;

/**
 *  Definition of the Stub to the Kitchen shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class KitchenStub {
    
    /* Attributes */
    
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
     *  Instantiation of the Kitchen Stub.
     *  @param serverHostName name of the computational system where the server is localized
     *  @param serverPortNumb number of the server's listening port
     */
    public KitchenStub( String serverHostName, String serverPortNumb) {
       
        this.serverHostName = serverHostName;
        this.serverPortNumb = Integer.valueOf(serverPortNumb);
    }
    
    /* Methods */
    
    /**
     *  Used by Chef (server solicitation) to wait for an order to be handed to him.
     */
    public synchronized void watchTheNews() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.WATCHNEWS);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        cc.close();
    }
    /*
    public synchronized void handTheNoteToTheChef() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.HANDNOTETOCHEF);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Waiter) Thread.currentThread()).getWaiterState() != inMessage.getWaiterState()) {
            ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
        }
        
        cc.close();
    }
    */
    /**
     *  Used by Chef (server solicitation) to start preparing the order.
     * 
     * @param nCourse integer variable holding the number of the current course
     */
    public synchronized void startPreparation(int nCourse) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.STARTPREPARATION, nCourse, 2);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        cc.close();
    }
    
    /**
     *  Used by Chef (server solicitation) to start dishing the portions of the current course.
     */
    public synchronized void proceedToPresentation() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.PROCEEDPRESENTATION);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        cc.close();
    }
    
    /**
     *  Used by Chef (server solicitation) to verify if all portions of the current course have been delivered to the Waiter. 
     * 
     *  @return Returns true if all portions of the current course have been delivered, false if not.
     */
    public synchronized boolean haveAllPortionsBeenDelivered() {
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
        outMessage = new Message (Message.HAVEPORTIONSDELIVERED);
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
     *  Used by Chef (server solicitation) to deliver one portion to the Waiter.
     */
    public synchronized void haveNextPortionReady() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.HAVENEXTPORTIONREADY);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        cc.close();
    }
    /*
    public synchronized void collectPortion() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.COLLECTPORTION);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Waiter) Thread.currentThread()).getWaiterState() != inMessage.getWaiterState()) {
            ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
        }
        
        cc.close();
    }
    
    public synchronized boolean haveAllStudentsBeenServed() {
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
        outMessage = new Message (Message.HAVESTUDENTSSERVED);
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
    */
    /**
     *  Used by Chef (server solicitation) to verify if all portions from all courses have been delivered to the Waiter.
     * 
     *  @return Returns true if all portions from all courses have been delivered, false if not.
     */
    public synchronized boolean hasTheOrderBeenCompleted() {
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
        outMessage = new Message (Message.HAVEORDERCOMPLETED);
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
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        if(inMessage.getMsgType() == Message.TRUE)
            return_value = true;
       
        cc.close();
        return return_value;
    }
    
    /**
     *  Used by Chef (server solicitation) to continue preparing the remaining courses from the order.
     * 
     * @param nCourse integer variable holding the number of the current course
     */
    public synchronized void continuePreparation(int nCourse) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.CONTINUEPREPARATION, nCourse, 2);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        cc.close();
    }
    
    /**
     *  Used by Chef (server solicitation) to close the service and clean the Kitchen.
     */
    public synchronized void cleanUp() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.CLEANUP);
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        cc.close();
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
        
        // processamento da msg de retorno
        
        
        cc.close();
    }
}
