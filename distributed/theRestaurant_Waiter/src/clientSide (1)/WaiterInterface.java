package clientSide;

import comInf.EntityStates;

/**
 *  Implementing this interface allows a Thread object to act as the Restaurant's Waiter.
 */
public interface WaiterInterface {
    /**
     *  Getter function to know the Waiter's current state.
     *  @return Waiter's state
     */
    public EntityStates.WaiterState getWaiterState();
    /**
     *  Setter function to update the Waiter's state.
     *  @param newWaiterState new state to replace Waiter's current state
     *  @return true if state is changed/updated, false if not
     */
    public boolean setWaiterState(EntityStates.WaiterState newWaiterState);
}
