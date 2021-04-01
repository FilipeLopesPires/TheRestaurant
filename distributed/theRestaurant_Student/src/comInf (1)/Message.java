package comInf;

import clientSide.GeneralRepositoryStub;
import java.io.*;

public class Message implements Serializable {
    private static final long serialVersionUID = 1001L;

        /* Message Types */
    
    // Return Values
    public static final int FALSE = -1;
    public static final int ACK = 0;
    public static final int TRUE = 1;
    public static final int FIRST = 2;
    public static final int MIDDLE = 3;
    public static final int LAST = 4;
    public static final int ALTERNATIVE = 5;
    public static final int SHUTDOWN = 7;
    
    // Students
    public static final int WALKABIT = 100;
    public static final int ENTERTABLE = 101;
    public static final int SALUTE = 102;
    public static final int READMENU = 103;
    public static final int INFORM = 104;
    public static final int HASCHOSEN = 105;
    public static final int PREPAREORDER = 106;
    public static final int GETPAD = 107;
    public static final int DESCRIBEORDER = 108;
    public static final int JOINTALK = 109;
    public static final int DELIVERPORTION = 110;
    public static final int STARTEATING = 111;
    public static final int ENDEATING = 112;
    public static final int HASFINISHED = 113;
    public static final int PRESENTBILL = 114;
    public static final int ARRIVEDEARLIER = 115;
    public static final int HONORBILL = 116;
    public static final int EXIT = 117;
    
    // Waiter
    public static final int LOOKAROUND = 201;
    public static final int ENTERBAR = 202;
    public static final int RETURNBAR = 203;
    public static final int CALLWAITER = 204;
    public static final int ALERTWAITER = 205;
    public static final int SIGNALWAITER = 206;
    public static final int PREPAREBILL = 207;
    public static final int SAYGOODBYE = 208;
    
    // Chef
    public static final int WATCHNEWS = 301;
    public static final int HANDNOTETOCHEF = 302;
    public static final int STARTPREPARATION = 303;
    public static final int PROCEEDPRESENTATION = 304;
    public static final int HAVEPORTIONSDELIVERED = 305;
    public static final int HAVENEXTPORTIONREADY = 306;
    public static final int COLLECTPORTION = 307;
    public static final int HAVESTUDENTSSERVED = 308;
    public static final int HAVEORDERCOMPLETED = 309;
    public static final int CONTINUEPREPARATION = 310;
    public static final int CLEANUP = 311;
    
    // General Repository
    public static final int UPDCHEF = 401;
    public static final int UPDWAITER = 402;
    public static final int UPDSTUDENT = 403;
    public static final int UPDCOURSE = 404;
    public static final int ISSTUDENTSEATING = 405;
    public static final int HAVESTUDENTSLEFT = 406;
    
    
        /* Message Fields */
    
    private String fName = null;
    private int msgType;
    private int studentId;
    private char waiterAlternative;
    private EntityStates.ArrivalOrder arrivalOrder;
    
    private EntityStates.StudentState studentState;
    private EntityStates.WaiterState waiterState;
    private EntityStates.ChefState chefState;
    private int nCourse;
    
        /* Constructors */
    
    // usada em mensagens simples
    public Message(int msgType) {   
        this.msgType = msgType;
    }
    
    // usada quando (1) recetor precisa apenas do studentId ou quando (2) há um update no course number (repo.updateCourse())
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
    public Message(int msgType, int studentId, EntityStates.StudentState studentState) {
        this.msgType = msgType;
        this.studentId = studentId;
        this.studentState = studentState;
    }

    // usada quando há alteração de estado de um Student e é necessário saber a sua arrival order (table.joinTheTalk())
    public Message(int msgType, int studentId, EntityStates.StudentState studentState, EntityStates.ArrivalOrder arrivalOrder) {
        this.msgType = msgType;
        this.studentId = studentId;
        this.studentState = studentState;
        this.arrivalOrder = arrivalOrder;
    }
    
    // usada quando há alteração de estado de um Student e é necessário saber em que course estão (table.startEating())
    public Message(int msgType, int studentId, EntityStates.StudentState studentState, int nCourse) {
        this.msgType = msgType;
        this.studentId = studentId;
        this.studentState = studentState;
        this.nCourse = nCourse;
    }
    
    // usada quando há alteração de estado do Waiter
    public Message(int msgType, EntityStates.WaiterState waiterState) {
        this.msgType = msgType;
        this.waiterState = waiterState;
    }

    // usada quando há alteração de estado do Chef
    public Message(int msgType, EntityStates.ChefState chefState) {
        this.msgType = msgType;
        this.chefState = chefState;
    }
    
    // ... pode vir a haver mais construtores
    
        /* Methods */

    public String getfName() { return fName; }
    public int getMsgType() { return msgType; }
    public int getStudentId() { return studentId; }
    public int getnCourse() { return nCourse; }
    public char getWaiterAlternative() { return waiterAlternative; }
    public EntityStates.ArrivalOrder getArrivalOrder() { return arrivalOrder; }
    public EntityStates.StudentState getStudentState() { return studentState; }
    public EntityStates.WaiterState getWaiterState() { return waiterState; }
    public EntityStates.ChefState getChefState() { return chefState; }
    
    @Override
    public String toString() {
        if(studentId==-1) {
            return ("Type = " + getMsgType() + "; Logging File = " + getfName() + ".");
        }
        return ("Type = " + getMsgType() + "; Student ID = " + getStudentId() + "; Logging File = " + getfName() + ".");
    }
}
