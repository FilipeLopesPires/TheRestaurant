package clientSide;
import comInf.EntityStates;

/**
 *  Implementing this interface allows a Thread object to act as one of the Restaurant's clients, the Student.
 */
public interface StudentInterface {
    /**
     *  Getter function to know the Student's ID.
     *  @return Student's ID
     */
    public int getID();
    /**
     *  Getter function to know the Student's state.
     * @return Student's State
     */
    public EntityStates.StudentState getStudentState();
    /**
     *  Setter function to update the Student's state.
     *  @param newStudentState new state to replace Student's current state
     *  @return true if state is changed/updated, false if not
     */
    public boolean setStudentState(EntityStates.StudentState newStudentState);
    /**
     *  Getter function to know the Student's place of arrival.
     *  @return Student's place of arrival (FIRST, MIDDLE or LAST)
     */
    public EntityStates.ArrivalOrder getArrivalOrder();
}
