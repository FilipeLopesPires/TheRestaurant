package clientSide;

import genclass.GenericIO;
import comInf.*;

/**
 *  Definition of the Stub to the Bar shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class BarStub {
    
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
     *  Instantiation of the Bar Stub.
     *  @param serverHostName name of the computational system where the server is localized
     *  @param serverPortNumb number of the server's listening port
     */
    public BarStub( String serverHostName, String serverPortNumb) {
       
        this.serverHostName = serverHostName;
        this.serverPortNumb = Integer.valueOf(serverPortNumb);
    }

    /* Methods */
    /*
    public char lookAround() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.LOOKAROUND, ((Waiter) Thread.currentThread()).getWaiterState());
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if (inMessage.getMsgType() != Message.ALTERNATIVE) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Waiter) Thread.currentThread()).getWaiterState() != inMessage.getWaiterState()) {
            ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
        }
        
        return inMessage.getWaiterAlternative();
    }
    */
    /**
     *  Used by Student (server solicitation) to inform the Waiter of his arrival.
     *  @return Returns the Student's place of arrival (variable type = ArrivalOrder).
     */
    public EntityStates.ArrivalOrder enter() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        EntityStates.ArrivalOrder return_value = null;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.ENTERBAR, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
        cc.writeObject (outMessage);
        
        // recepção da msg,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.FIRST && inMessage.getMsgType() != Message.MIDDLE && inMessage.getMsgType() != Message.LAST)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        if(inMessage.getMsgType() == Message.FIRST)
            return_value = EntityStates.ArrivalOrder.FIRST;
        else if(inMessage.getMsgType() == Message.LAST)
            return_value = EntityStates.ArrivalOrder.LAST;
        else
            return_value = EntityStates.ArrivalOrder.MIDDLE;
        
        cc.close();
        return return_value;
    }
    /*
    public void returnToTheBar() { 
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.RETURNBAR, ((Waiter) Thread.currentThread()).getWaiterState());
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) ) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Waiter) Thread.currentThread()).getWaiterState() != inMessage.getWaiterState()) {
            ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
        }
        
        return;
    }
    */
    /**
     *  Used by Student (server solicitation) to notify the Waiter once every Student has chosen their courses.
     */
    public void callTheWaiter() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.CALLWAITER);
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) ) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        return;
    }
    /*
    public void alertTheWaiter() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.ALERTWAITER, ((Chef) Thread.currentThread()).getChefState());
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) ) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
    
        // processamento da msg de retorno
        if(((Chef) Thread.currentThread()).getChefState() != inMessage.getChefState()) {
            ((Chef) Thread.currentThread()).setChefState(inMessage.getChefState());
        }
        
        return;
    }
    */
    /**
     *  Used by Student (server solicitation) to notify the Waiter once he is ready to pay the bill.
     */
    public void signalTheWaiter() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.SIGNALWAITER);
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) ) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        return;
    }
    /*
    public void prepareTheBill() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.PREPAREBILL, ((Waiter) Thread.currentThread()).getWaiterState());
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) ) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Waiter) Thread.currentThread()).getWaiterState() != inMessage.getWaiterState()) {
            ((Waiter) Thread.currentThread()).setWaiterState(inMessage.getWaiterState());
        }
        
        return;
    }
    
    public void sayGoodbye() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.SAYGOODBYE);
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) ) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        return;
    }
    */
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
