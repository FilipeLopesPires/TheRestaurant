package comInf;

import java.io.*;

/**
 *  Definition of the messages exchanged between clients and servers on this solution of the Restaurant's Problem, implementing the client-server model 
 *  of type 2 (server replication) with static launching of the entities envolved.
 *  The communication is based on the exchange of object of type Message through a TCP channel.
 */
public class Message implements Serializable {
    /**
     *  Serialization key
     */
    private static final long serialVersionUID = 1001L;

        /* Message Types */
    
    // Return Values
    /**
     *  Return type with value of False
     */
    public static final int FALSE = -1;
    /**
     *  Return type to tell the client that the message was successfuly received
     */
    public static final int ACK = 0;
    /**
     *  Return type with value of True
     */
    public static final int TRUE = 1;
    /**
     *  Return type to tell the Student client that he arrived at the restaurant in First place
     */
    public static final int FIRST = 2;
    /**
     *  Return type to tell the Student client that he arrived at the restaurant in one of the Middle places
     */
    public static final int MIDDLE = 3;
    /**
     *  Return type to tell the Student client that he arrived at the restaurant in Last place
     */
    public static final int LAST = 4;
    /**
     *  Return type to tell the Waiter client that he has an action to do
     */
    public static final int ALTERNATIVE = 5;
    /**
     *  Return type to tell the client that the server must be shutdown
     */
    public static final int SHUTDOWN = 7;
    
    // Table
    /**
     *  Sending type to tell the Table that a Student has started walking to the restaurant
     */
    public static final int WALKABIT = 100;
    /**
     *  Sending type for the Students to sit at the table
     */
    public static final int ENTERTABLE = 101;
    /**
     *  Sending type for the Waiter to salute the arriving Students
     */
    public static final int SALUTE = 102;
    /**
     *  Sending type for the Students to read the menu
     */
    public static final int READMENU = 103;
    /**
     *  Sending type for the Students to inform their companion about their choice
     */
    public static final int INFORM = 104;
    /**
     *  Sending type for a Student to check if everyone has made their choice
     */
    public static final int HASCHOSEN = 105;
    /**
     *  Sending type for a Student to prepare the order for the entire meal
     */
    public static final int PREPAREORDER = 106;
    /**
     *  Sending type for the Waiter to get the pad from the Table
     */
    public static final int GETPAD = 107;
    /**
     *  Sending type for a Student to describe the order to the Waiter
     */
    public static final int DESCRIBEORDER = 108;
    /**
     *  Sending type for the Students to chat with each other
     */
    public static final int JOINTALK = 109;
    /**
     *  Sending type for the Waiter to deliver a portion at the Table
     */
    public static final int DELIVERPORTION = 110;
    /**
     *  Sending type for the Students to start eating the current course
     */
    public static final int STARTEATING = 111;
    /**
     *  Sending type for the Students to finish the current course
     */
    public static final int ENDEATING = 112;
    /**
     *  Sending type for a Student to check if everyone has finished the current course
     */
    public static final int HASFINISHED = 113;
    /**
     *  Sending type  for the Waiter to present the bill
     */
    public static final int PRESENTBILL = 114;
    /**
     *  Sending type for the Last Student to prepare himself to pay the bill
     */
    public static final int ARRIVEDEARLIER = 115;
    /**
     *  Sending type for the Last Student to honor the bill
     */
    public static final int HONORBILL = 116;
    /**
     *  Sending type for the Students to exit the restaurant
     */
    public static final int EXIT = 117;
    
    // Bar
    /**
     *  Sending type for the Waiter to check if there is any task for him to do or anyone waiting for him
     */
    public static final int LOOKAROUND = 201;
    /**
     *  Sending type for the Students to warn the Waiter of their arrival at the restaurant
     */
    public static final int ENTERBAR = 202;
    /**
     *  Sending type for the Waiter to return to the Bar once he finishes his current task 
     */
    public static final int RETURNBAR = 203;
    /**
     *  Sending type for a Student to call the Waiter for him to get the pad
     */
    public static final int CALLWAITER = 204;
    /**
     *  Sending type for the Chef to alert the Waiter for him to get a new portion
     */
    public static final int ALERTWAITER = 205;
    /**
     *  Sending type for a Student to signal the Waiter for him to prepare the bill
     */
    public static final int SIGNALWAITER = 206;
    /**
     *  Sending type for the Waiter to prepare the Table's bill
     */
    public static final int PREPAREBILL = 207;
    /**
     *  Sending type for the Waiter to say goodbye to the Students
     */
    public static final int SAYGOODBYE = 208;
    
    // Kitchen
    /**
     *  Sending type for the Chef to wait and watch the news while there is no order to prepare
     */
    public static final int WATCHNEWS = 301;
    /**
     *  Sending type for the Waiter to hand the Table's order to the Chef 
     */
    public static final int HANDNOTETOCHEF = 302;
    /**
     *  Sending type for the Chef to start preparing the first course 
     */
    public static final int STARTPREPARATION = 303;
    /**
     *  Sending type for the Chef to present the portions 
     */
    public static final int PROCEEDPRESENTATION = 304;
    /**
     *  Sending type for the Chef to check if all portions have been delivered
     */
    public static final int HAVEPORTIONSDELIVERED = 305;
    /**
     *  Sending type for the Chef to grab the next portion and deliver it to the Waiter 
     */
    public static final int HAVENEXTPORTIONREADY = 306;
    /**
     *  Sending type for the Waiter to collect one portion from the Chef 
     */
    public static final int COLLECTPORTION = 307;
    /**
     *  Sending type for the Waiter to check if all Students have been served of the current course
     */
    public static final int HAVESTUDENTSSERVED = 308;
    /**
     *  Sending type for the Waiter to check if all Students have been served of all courses 
     */
    public static final int HAVEORDERCOMPLETED = 309;
    /**
     *  Sending type for the Chef to start preparing the 2nd / 3rd course 
     */
    public static final int CONTINUEPREPARATION = 310;
    /**
     *  Sending type for the Chef to close service and clean up the Kitchen
     */
    public static final int CLEANUP = 311;
    
    // General Repository
    /**
     *  Sending type to update the Chef's state on the log file
     */
    public static final int UPDCHEF = 401;
    /**
     *  Sending type to update the Waiter's state on the log file
     */
    public static final int UPDWAITER = 402;
    /**
     *  Sending type to update a Student's state on the log file
     */
    public static final int UPDSTUDENT = 403;
    /**
     *  Sending type to update the current course number on the log file
     */
    public static final int UPDCOURSE = 404;
    /**
     *  Sending type for the Students to verify if all are eating the current course
     */
    public static final int ISSTUDENTSEATING = 405;
    /**
     *  Sending type for the Waiter to check if all Students have left the restaurant
     */
    public static final int HAVESTUDENTSLEFT = 406;
    
        /* Message Fields */
    /**
     *  Name of the log file
     */
    private String fName = null;
    /**
     *  Type of the message to be sent / recieved
     */
    private int msgType;
    /**
     *  ID of the Student envolved in the message. Null if message does not envolve any Student
     */
    private int studentId;
    /**
     *  Task to be done by the Waiter if he is envolved in the message. Null if message does not envolve the Waiter
     */
    private char waiterAlternative;
    /**
     *  Place of arrival of the Student envolved in the message. Null if message does not envolve any Student
     */
    private EntityStates.ArrivalOrder arrivalOrder;
    /**
     *  State of the Student envolved in the message. Null if message does not envolve any Student
     */
    private EntityStates.StudentState studentState;
    /**
     *  State of the Waiter envolved in the message. Null if message does not envolve the Waiter
     */
    private EntityStates.WaiterState waiterState;
    /**
     *  State of the Chef envolved in the message. Null if message does not envolve the Chef
     */
    private EntityStates.ChefState chefState;
    /**
     *  Updated course number. Null if message is not ment to update this field
     */
    private int nCourse;
    
        /* Constructors */
    
    // usada em mensagens simples
    /**
     *  Message instantiation (type 1).
     *  @param msgType message type
     */
    public Message(int msgType) {   
        this.msgType = msgType;
    }
    
    // usada quando (1) recetor precisa apenas do studentId ou quando (2) há um update no course number (repo.updateCourse())
    /**
     *  Message instantiation (type 2).
     *  @param msgType message type
     *  @param secondArg represents either the ID of a Student or the updated number of the course, depending on the type
     *  @param type decides if secondArg is an ID (type = 1) or a course number (type = 2)
     */
    public Message(int msgType, int secondArg, int type) {
        this.msgType = msgType;
        switch(type) {
            case 1:
                this.studentId = secondArg;
                break;
            case 2:
                this.nCourse = secondArg;
                break;
        }
    }
    
    // usada quando há alteração de estado de um Student
    /**
     *  Message instantiation (type 3).
     *  @param msgType message type
     *  @param studentId ID of the Student whos state may be updated
     *  @param studentState (updated) state of the Student
     */
    public Message(int msgType, int studentId, EntityStates.StudentState studentState) {
        this.msgType = msgType;
        this.studentId = studentId;
        this.studentState = studentState;
    }

    // usada quando há alteração de estado de um Student e é necessário saber a sua arrival order (table.joinTheTalk())
    /**
     *  Message instantiation (type 4).
     *  @param msgType message type
     *  @param studentId ID of the Student whos state may be updated
     *  @param studentState (updated) state of the Student
     *  @param arrivalOrder place of arrival of the Student at the restaurant
     */
    public Message(int msgType, int studentId, EntityStates.StudentState studentState, EntityStates.ArrivalOrder arrivalOrder) {
        this.msgType = msgType;
        this.studentId = studentId;
        this.studentState = studentState;
        this.arrivalOrder = arrivalOrder;
    }
    
    // usada quando há alteração de estado de um Student e é necessário saber em que course estão (table.startEating())
    /**
     *  Message instantiation (type 5).
     *  @param msgType message type
     *  @param studentId ID of the Student whos state may be updated
     *  @param studentState (updated) state of the Student
     *  @param nCourse updated course number
     */
    public Message(int msgType, int studentId, EntityStates.StudentState studentState, int nCourse) {
        this.msgType = msgType;
        this.studentId = studentId;
        this.studentState = studentState;
        this.nCourse = nCourse;
    }
    
    // usada quando há alteração de estado do Waiter
    /**
     *  Message instantiation (type 6).
     *  @param msgType message type
     *  @param waiterState (updated) state of the Waiter
     */
    public Message(int msgType, EntityStates.WaiterState waiterState) {
        this.msgType = msgType;
        this.waiterState = waiterState;
    }

    // usada quando há alteração de estado do Waiter e retorno de um char (bar.lookaround())
    /**
     *  Message instantiation (type 7).
     *  @param msgType message type
     *  @param waiterState (updated) state of the Waiter
     *  @param waiterAlternative task to be done by the Waiter
     */
    public Message(int msgType, EntityStates.WaiterState waiterState, char waiterAlternative) {
        this.msgType = msgType;
        this.waiterState = waiterState;
        this.waiterAlternative = waiterAlternative;
    }

    // usada quando há alteração de estado do Chef
    /**
     *  Message instantiation (type 8).
     *  @param msgType message type
     *  @param chefState (updated) state of the Chef
     */
    public Message(int msgType, EntityStates.ChefState chefState) {
        this.msgType = msgType;
        this.chefState = chefState;
    }
    
        /* Methods */

    /**
     *  Getter function to know the name of the log file.
     *  @return name of the log file
     */
    public String getfName() { return fName; }
    /**
     *  Getter function to know the type of the message.
     *  @return message type
     */
    public int getMsgType() { return msgType; }
    /**
     *  Getter function to know the ID of the Student envolved in the message.
     *  @return Student's ID (or null if there is no Student envolved in the message)
     */
    public int getStudentId() { return studentId; }
    /**
     *  Getter function to know the updated course number
     *  @return new course number (or null if the message isn't updating the course number)
     */
    public int getnCourse() { return nCourse; }
    /**
     *  Getter function to know the task to be done by the Waiter
     *  @return Waiter's task to be done (or null if the message isn't giving the Waiter a task to do)
     */
    public char getWaiterAlternative() { return waiterAlternative; }
    /**
     *  Getter function to know the place of arrival of a Student.
     *  @return Student's place of arrival (or null if no Student is envolved in the message)
     */
    public EntityStates.ArrivalOrder getArrivalOrder() { return arrivalOrder; }
    /**
     *  Getter function to know the new state of a Student.
     *  @return new state
     */
    public EntityStates.StudentState getStudentState() { return studentState; }
    /**
     *  Getter function to know the new state of the Waiter.
     *  @return new state
     */
    public EntityStates.WaiterState getWaiterState() { return waiterState; }
    /**
     *  Getter function to know the new state of the Chef.
     *  @return new state
     */
    public EntityStates.ChefState getChefState() { return chefState; }
    
    /**
     *  Printing of the internal fields.
     *  Used for debugging.
     *  @return string with the internal fields properly separated
     */
    @Override
    public String toString() {
        String extra = "";
        
            extra = ""+waiterAlternative;
        if(studentId==-1) {
            return ("Type = " + getMsgType() + "; Logging File = " + getfName() + "." + " Alternative: " + extra);
        }
        return ("Type = " + getMsgType() + "; Student ID = " + getStudentId() + "; Logging File = " + getfName() + "." + " Alternative: " + extra);
    }
}
