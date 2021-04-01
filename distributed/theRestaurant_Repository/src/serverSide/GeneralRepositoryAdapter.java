package serverSide;
import comInf.*;

/**
 *  Definition of the interface of the General Repository shared region on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 */
public class GeneralRepositoryAdapter {
    
    /**
     *  Internal Data
     */
    
    /**
     *  GeneralRepository (representes the service to be provided)
     */
    private volatile GeneralRepository repo;
    
    /**
     *  Instantiation of the GeneralRepository interface.
     *  @param repo GeneralRepository shared region
     */
    public GeneralRepositoryAdapter(GeneralRepository repo) {
        this.repo = repo;
    }
    
    /**
     *  Message prossessing through execution of the corresponding tasks.
     *  Creation of a return message.
     *  @param m message with the request
     *  @return return message
     *  @throws MessageException if the request message is considered invalid
     */
    public Message processAndReply(Message m) throws MessageException {
        // Decode Message, execute function, return result
       
        switch(m.getMsgType()){
            case Message.UPDCHEF:
                repo.updateChefState(m.getChefState());
                break;
            case Message.UPDCOURSE:
                repo.updateCourse(m.getnCourse());
                break;
            case Message.UPDSTUDENT:
                repo.updateStudentState(m.getStudentState(), m.getStudentId());
                break;
            case Message.UPDWAITER:
                repo.updateWaiterState(m.getWaiterState());
                break;
            case Message.ISSTUDENTSEATING:
               if(repo.isEveryStudentEating())
                    return new Message(Message.TRUE);
                else
                    return new Message(Message.FALSE);
            case Message.HAVESTUDENTSLEFT:
                if(repo.HaveAllStudentLeft())
                    return new Message(Message.TRUE);
                else
                    return new Message(Message.FALSE);
            case Message.SHUTDOWN:
                repo.shutdown();
                break;
           
        }
        return new Message(Message.ACK);
    }
}
