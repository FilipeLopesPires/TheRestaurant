package clientSide;

import genclass.GenericIO;
import comInf.*;

public class barStub {
    
    private String serverHostName;
    private int serverPortNumb;

    public barStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    public char lookAround() {
        ClientCom cc = new ClientCom(serverHostName, serverPortNumb);
        Message inMessage, outMessage;
        char returnValue;
        
        // estabelecimento de ligação
        while (!cc.open ()) {
            try {
                Thread.currentThread ().sleep ((long) (10));
            } catch (InterruptedException e) {}
        }
        
        // pedido de serviço e envio da msg
        outMessage = new Message (Message.LOOKAROUND);
        cc.writeObject (outMessage);
        
        // recepção da msg 
        inMessage = (Message) cc.readObject ();
        
        // tratamento de erros
        if ((inMessage.getMsgType() != Message.ACK) || (inMessage.getMsgType() != Message.S) || (inMessage.getType() != Message.O) || (inMessage.getType() != Message.P) || (inMessage.getType() != Message.G) || (inMessage.getType() != Message.E)) {
            GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        
        cc.close();
        
        // forma de aceder ao valor de retorno presente na inMessage
        return inMessage.getReturnValue();
    }
    
    public ArrivalOrder enter() {
        
    }
    
    public void returnToTheBar() { 
        
    }
    
    public void callTheWaiter() {
        
    }
    
    public void alertTheWaiter() {
        
    }
    
    public void signalTheWaiter() {
        
    }
    
    public void prepareTheBill() {
        
    }
    
    public void sayGoodbye() {
        
    }
    
}
