package serverSide;

import comInf.*;

/**
 *  Definition of the interface of the Bar shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class BarAdapter {
    
    /**
     *  Internal Data
     */
    
    /**
     *  Bar (representes the service to be provided)
     */
    private Bar b;

    /**
     *  Instantiation of the Bar interface.
     *  @param bar Bar shared region
     */
    public BarAdapter(Bar bar) {
        this.b = bar;
    }
    
    /**
     *  Message prossessing through execution of the corresponding tasks.
     *  Creation of a return message.
     *  @param m message with the request
     *  @return return message
     *  @throws MessageException if the request message is considered invalid
     */
    public Message processAndReply(Message m) throws MessageException {
        switch(m.getMsgType()){
            case Message.LOOKAROUND:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                char alternative = b.lookAround();
                
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ALTERNATIVE, ((ComHandler) Thread.currentThread()).getWaiterState(), alternative);
            
            case  Message.ENTERBAR:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                EntityStates.ArrivalOrder arrivalOrder = b.enter(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                if(arrivalOrder == EntityStates.ArrivalOrder.FIRST) {
                    return new Message(Message.FIRST, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
                } else if(arrivalOrder == EntityStates.ArrivalOrder.LAST) {
                    return new Message(Message.LAST, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
                }
                return new Message(Message.MIDDLE, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
                
            case  Message.RETURNBAR:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                b.returnToTheBar();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getWaiterState());
                
            case  Message.CALLWAITER:
                // execução do método na região partilhada
                b.callTheWaiter();
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK);
                
            case  Message.ALERTWAITER:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                b.alertTheWaiter();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
                
            case  Message.SIGNALWAITER:
                // execução do método na região partilhada
                b.signalTheWaiter();
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK);
                
            case  Message.PREPAREBILL:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                b.prepareTheBill();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getWaiterState());
                
            case  Message.SAYGOODBYE:
                // execução do método na região partilhada
                b.sayGoodbye();
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK);
            case Message.SHUTDOWN:
                BarMain.shutdown();
                return new Message(Message.ACK);
        }
        throw new MessageException("Mensagem corrompida.", m);
    }
    
}
