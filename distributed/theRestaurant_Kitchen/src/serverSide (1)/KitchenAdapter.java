package serverSide;

import comInf.*;

/**
 *  Definition of the interface of the Kitchen shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class KitchenAdapter {
    
    /**
     *  Internal Data
     */
    
    /**
     *  Kitchen (representes the service to be provided)
     */
    private Kitchen k;

    /**
     *  Instantiation of the Kitchen interface.
     *  @param kitchen Kitchen shared region
     */
    public KitchenAdapter(Kitchen kitchen) {
        this.k = kitchen;
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
            case Message.WATCHNEWS:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                k.watchTheNews();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
            
            case Message.HANDNOTETOCHEF:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                k.handTheNoteToTheChef();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getWaiterState());
            
            case Message.STARTPREPARATION:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                k.startPreparation(m.getnCourse());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
            
            case Message.PROCEEDPRESENTATION:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                k.proceedToPresentation();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
            
            case Message.HAVEPORTIONSDELIVERED:
                // execução do método na região partilhada
                // retorno de uma nova mensagem, contendo o resultado da operação
                if(k.haveAllPortionsBeenDelivered()) {
                    return new Message(Message.TRUE);
                }
                return new Message(Message.FALSE);
            
            case Message.HAVENEXTPORTIONREADY:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                k.haveNextPortionReady();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
            
            case Message.COLLECTPORTION:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setWaiterState(m.getWaiterState());
                // execução do método na região partilhada
                k.collectPortion();
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getWaiterState());
            
            case Message.HAVESTUDENTSSERVED:
                // execução do método na região partilhada
                // retorno de uma nova mensagem, contendo o resultado da operação
                if(k.haveAllStudentsBeenServed()) {
                    return new Message(Message.TRUE);
                }
                return new Message(Message.FALSE);
            
            case Message.HAVEORDERCOMPLETED:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                if(k.hasTheOrderBeenCompleted()) {
                    return new Message(Message.TRUE, ((ComHandler) Thread.currentThread()).getChefState());
                }
                return new Message(Message.FALSE, ((ComHandler) Thread.currentThread()).getChefState());
            
            case Message.CONTINUEPREPARATION:
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                k.continuePreparation(m.getnCourse());
                // retorno de uma nova mensagem, contendo o resultado da operação e possíveis alterações de estado
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
            
            case Message.CLEANUP:                   //chef
                // atribuição do estado atual da entidade ao seu representante
                ((ComHandler) Thread.currentThread()).setChefState(m.getChefState());
                // execução do método na região partilhada
                k.cleanUp();
                // retorno de uma nova mensagem, contendo o resultado da operação
                return new Message(Message.ACK, ((ComHandler) Thread.currentThread()).getChefState());
            case Message.SHUTDOWN:
                KitchenMain.shutdown();
                return new Message(Message.ACK);
        }
        throw new MessageException("Mensagem corrompida.", m);
    }

}
