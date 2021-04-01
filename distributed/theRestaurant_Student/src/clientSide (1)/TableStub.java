package clientSide;

import genclass.GenericIO;
import comInf.*;

/**
 *  Definition of the Stub to the Table shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class TableStub {
    
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
     *  Instantiation of the Table Stub.
     *  @param serverHostName name of the computational system where the server is localized
     *  @param serverPortNumb number of the server's listening port
     */
    public TableStub( String serverHostName, String serverPortNumb) {
        
        this.serverHostName = serverHostName;
        this.serverPortNumb = Integer.valueOf(serverPortNumb);
    }
    
    /* Methods */
    
    /**
     *  Used by Student (server solicitation) to define his inital state of GTTR and walk to the Restaurant.
     */
    public void walkABit() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.WALKABIT, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
        cc.writeObject (outMessage);
        
        // recepção da msg de retorno,só avança quando chegar uma mensagem
        inMessage = (Message) cc.readObject ();
      
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        // processamento da msg de retorno
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }

    /**
     *  Used by Student (server solicitation) to sit at the table.
     */
    public void enter() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.ENTERTABLE, ((Student) Thread.currentThread()).getID(), 1);
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

    /*
    public  void saluteTheStudent() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.SALUTE, ((Waiter) Thread.currentThread()).getWaiterState());
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
     *  Used by Student (server solicitation) to select the courses they are having.
     */
    public void readTheMenu() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.READMENU, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
       
        cc.close();
        
    }

    /** 
     *  Used by Student (server solicitation) to inform their choice to the Student responsible for the order.
     */
    public void informCompanion() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.INFORM, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }

    /**
     *  Used by Student (server solicitation) to verify if everyone has made their choices for the courses. 
     * 
     *  @return Returns true if every Student has chosen their courses, false if not.
     */
    public boolean hasEverybodyChosen() {
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
        outMessage = new Message (Message.HASCHOSEN, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        if(inMessage.getMsgType() == Message.TRUE)
            return_value = true;
       
        cc.close();
        return return_value;
    }

    /**
     *  Used by Student (server solicitation) to prepare the Table's order.
     */
    public void prepareTheOrder() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.PREPAREORDER, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }

    /*
    public void getThePad() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.GETPAD, ((Waiter) Thread.currentThread()).getWaiterState());
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
     *  Used by Student (server solicitation) to describe the order to the Waiter.
     */
    public void describeTheOrder() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.DESCRIBEORDER, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }

    /**
     *  Used by Student (server solicitation) to chat with companions while they wait for the food.
     */
    public void joinTheTalk() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.JOINTALK, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState(), ((Student) Thread.currentThread()).getArrivalOrder());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }
    /*
    public void deliverPortion() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.DELIVERPORTION);
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
    */
    /**
     *  Used by Student (server solicitation) to start eating the current course.
     * 
     *  @param nc integer variable with the number of a course.
     */
    public void startEating(int nc) {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.STARTEATING, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState(), nc);
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }

    /**
     *  Used by Student (server solicitation) to finish eating.
     */
     public void endEating() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.ENDEATING, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
        
        cc.close();
    }
    
    /**
     *  Used by Student (server solicitation) to verify if everyone has finished eating. 
     * 
     *  @return Returns true if every Student has finished eating, false if not.
     */
    public boolean hasEverybodyFinished() {
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
        outMessage = new Message (Message.HASFINISHED, ((Student) Thread.currentThread()).getID(), 1);
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
    
    /*
    public void presentTheBill() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.PRESENTBILL, ((Waiter) Thread.currentThread()).getWaiterState());
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
     *  Used by Student (server solicitation) to accept the fact that he will have to pay for the entire meal.
     */
    public void shouldHaveArrivedEarlier() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.ARRIVEDEARLIER, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
        }
       
        cc.close();
    }

    /**
     *  Used by Student (server solicitation) to pay the Table's bill.
     */
    public void honorTheBill() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.HONORBILL, ((Student) Thread.currentThread()).getID(), 1);
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
     *  Used by Student (server solicitation) to leave the Restaurant.
     */
    public void exit() {
        ClientCom cc = new ClientCom(serverHostName,serverPortNumb);
        Message inMessage, outMessage;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
       
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.EXIT, ((Student) Thread.currentThread()).getID(), ((Student) Thread.currentThread()).getStudentState());
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
        if(((Student) Thread.currentThread()).getStudentState() != inMessage.getStudentState()) {
            ((Student) Thread.currentThread()).setStudentState(inMessage.getStudentState());
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
