package clientSide;

/**
*  Internal Enumerate holding all the possible places of arrival at the Restaurant.
*/
public enum ArrivalOrder {                                           // Order of Arrival Enum
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