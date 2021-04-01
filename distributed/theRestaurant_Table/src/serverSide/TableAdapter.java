package serverSide;

import comInf.*;

/**
 *  Definition of the interface of the Table shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class TableAdapter {
    
    /**
     *  Internal Data
     */
    
    /**
     *  Table (representes the service to be provided)
     */
    private Table t;
     
    /**
     *  Instantiation of the Table interface.
     *  @param table Table shared region
     */
    public TableAdapter(Table table){
        this.t = table;
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
            
            case Message.WALKABIT:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.walkABit(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.ENTERTABLE:
                // execução do método na região partilhada
                t.enter(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK);
            
            case Message.SALUTE:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                t.saluteTheStudent();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK,((ComHandler) Thread.currentThread()).getWaiterState());
            
            case Message.READMENU:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.readTheMenu(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.INFORM:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.informCompanion(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.HASCHOSEN:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                if(t.hasEverybodyChosen(m.getStudentId())) {
                    return new Message(Message.TRUE, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
                }
                return new Message(Message.FALSE, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.PREPAREORDER:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.prepareTheOrder(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.GETPAD:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                t.getThePad();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK,((ComHandler) Thread.currentThread()).getWaiterState());
            
            case Message.DESCRIBEORDER:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.describeTheOrder(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.JOINTALK:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                switch(m.getArrivalOrder()) {
                    case FIRST:
                        t.joinTheTalk(m.getStudentId(), EntityStates.ArrivalOrder.FIRST);
                    case MIDDLE:
                        t.joinTheTalk(m.getStudentId(), EntityStates.ArrivalOrder.MIDDLE);
                    case LAST:
                        t.joinTheTalk(m.getStudentId(), EntityStates.ArrivalOrder.LAST);
                }
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.DELIVERPORTION:
                // execução do método na região partilhada
                t.deliverPortion();
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK);
            
            case Message.STARTEATING:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.startEating(m.getStudentId(),m.getnCourse());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.ENDEATING:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.endEating(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.HASFINISHED:
                // execução do método na região partilhada
                // retorno de uma nova mensagem, contendo o resultado da operação
                if(t.hasEverybodyFinished(m.getStudentId())) {
                    return new Message(Message.TRUE);
                }
                return new Message(Message.FALSE);
            
            case Message.PRESENTBILL:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                t.presentTheBill();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK,((ComHandler) Thread.currentThread()).getWaiterState());
            
            case Message.ARRIVEDEARLIER:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.shouldHaveArrivedEarlier(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            
            case Message.HONORBILL:
                // execução do método na região partilhada
                t.honorTheBill(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK);
                
            case Message.EXIT:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setStudentState(m.getStudentState());
                // execução do método na região partilhada
                t.exit(m.getStudentId());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, m.getStudentId(), ((ComHandler) Thread.currentThread()).getStudentState());
            case Message.SHUTDOWN:
                TableMain.shutdown();
                return new Message(Message.ACK);
        }
        
        throw new MessageException("Mensagem corrompida.", m);
    }
}
