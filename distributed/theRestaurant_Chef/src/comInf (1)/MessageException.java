package comInf;

/**
 *  Definition of an exception thrown if a message is invalid.
 */
public class MessageException extends Exception {
    
    /**
     *  Message that originated the exception
     */
    private Message msg;

    /**
     *  Instantiation of a message.
     *  @param errorMessage text signaling the error condition
     *  @param msg message on the origin of the exception
     */
    public MessageException (String errorMessage, Message msg) {
        super (errorMessage);
        this.msg = msg;
    }

    /**
     *  Getter function to know the message that originated the exception.
     *  @return message
     */
    public Message getMessageVal () {
        return (msg);
    }
}
