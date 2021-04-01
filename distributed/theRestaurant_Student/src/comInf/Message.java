package comInf;

import java.io.*;

public class Message implements Serializable {
    private static final long serialVersionUID = 1001L;
    
    /* Tipos de Mensagens */
    
    // Return Values
    public static final int FALSE = -1;
    public static final int ACK = 0;
    public static final int TRUE = 1;
    public static final int FIRST = 2;
    public static final int MIDDLE = 3;
    public static final int LAST = 4;
    public static final int S = 83;
    public static final int O = 79;
    public static final int P = 80;
    public static final int B = 66;
    public static final int G = 71;
    public static final int E = 69;
    
    // Students
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
    
    /* Campos das mensagens */
    
    private int msgType = -1;
    private int studentId = -1;
    private String fName = null;

    /* Construtores */
    
    public Message(int msgType) {
        this.msgType = msgType;
    }
    
    public Message(int msgType, int studentId) {
        this.msgType = msgType;
        this.studentId = studentId;
    }
    
    /* Métodos */

    public int getMsgType() { return msgType; }
    public int getStudentId() { return studentId; }
    public String getfName() { return fName; }
    
    @Override
    public String toString() {
        if(studentId==-1) {
            return ("Type = " + getMsgType() + "; Logging File = " + getfName() + ".");
        }
        return ("Type = " + getMsgType() + "; Student ID = " + getStudentId() + "; Logging File = " + getfName() + ".");
    }
}
