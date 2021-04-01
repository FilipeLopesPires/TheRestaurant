package comInf;

/**
 *  A family of enumerates holding all possible entity states and places of arrival for the Restaurant.
 */
public class EntityStates {
    
    /**
     *  All possible places of arrival at the Restaurant.
     */
    public static enum ArrivalOrder {                                           // Order of Arrival Enum
        /**
         *  First Student to arrive.
         */
        FIRST,
        /**
         *  One of the Students arriving neither in first nor in last place.
         */
        MIDDLE,
        /**
         *  Last Student to arrive.
         */
        LAST;
    }
    
    /**
     *  All possible states of the Student entity.
     */
    public static enum StudentState {                                                  // Internal State Enum
        /**
         *  GOING_TO_THE_RESTAURANT - transition state with random time (initial state)
         */
        GTTR,
        /**
         *  TAKING_A_SEAT_AT_THE_TABLE – blocking state; the student is waken up by the operation saluteTheClient of the waiter
         */
        TASATT,
        /**
         *  SELECTING_THE_COURSES – transition state
         */
        STC,
        /**
         *  ORGANIZING_THE_ORDER – blocking state; the student is waken up by the operation informCompanion of another student and, when all students are already at the table, by the operation getThePad of the waiter
         */
        OTO,
        /**
         *  CHATTING_WITH_COMPANIONS – blocking state with transition; the student blocks while waiting for a course to be served and when he/she has finished eating it; transition occurs when the last course has been served and eaten
         */
        CWC,
        /**
         *  ENJOYING_THE_MEAL – transition state
         */
        ETM,
        /**
         *  PAYING_THE_MEAL – blocking state; the student is waken up by the operation presentTheBill of the waiter
         */
        PTB,
        /**
         *  GOING_HOME – final state
         */
        GH;
    }
    
    /**
     *  All possible states of the Waiter entity.
     */
    public static enum WaiterState {                                                   // Internal State Enum
        /**
         *  APPRAISING_SITUATION – blocking state with transition (initial / final state); the waiter is waken up by one of the following operations: alertThe- Waiter of the chef, enter and exit of all the students, callTheWaiter of the first student to sit at the table, signalTheWaiter of the last student to finish a course and shouldHaveArrivedEarlier of the last student to sit at the table; transition occurs when the last student has left the restaurant
         */
        AS,
        /**
         *  PRESENTING_THE_MENU – blocking state; the waiter is waken up by the operation readTheMenu of the student
         */
        PTM,
        /**
         *  TAKING_THE_ORDER – blocking state; the waiter is waken up by the operation describeTheOrder of the student
         */
        TTO,
        /**
         *  PLACING_THE_ORDER – blocking state; the waiter is waken up by the operation startPreparation of the chef
         */
        PTO,
        /**
         *  WAITING_FOR_PORTION – blocking state; the waiter is waken up by the operation haveAllPortionsBeenDelivered of the chef
         */
        WFP,
        /**
         *  PROCESSING_THE_BILL – transition state
         */
        PTB,
        /**
         *  RECEIVING_PAYMENT – blocking state; the waiter is waken up by the operation honorTheBill of the student
         */
        RP;
    }
    
    /**
     *  All possible states of the Chef entity.
     */
    public static enum ChefState {                                                     // Internal State Enum
       /**
        *  WAITING_FOR_AN_ORDER – blocking state (initial state); the chef is waken up by the operation handTheNoteToTheChef of the waiter
        */
       WFAO,
       /**
        *  PREPARING_THE_COURSE – transition state
        */
       PTC,
       /**
        *  DISHING_THE_PORTIONS – transition state
        */
       DiTP,
       /**
        *  DELIVERING_THE_PORTIONS – blocking state; the chef is waken up by the operation collectPortion of the waiter
        */
       DeTP,
       /**
        *  CLOSING_SERVICE – final state
        */
       CS;
    }
}
