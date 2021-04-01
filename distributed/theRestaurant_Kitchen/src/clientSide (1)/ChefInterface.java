package clientSide;

import comInf.EntityStates;

/**
 *  Implementing this interface allows a Thread object to act as the Restaurant's Chef.
 */
public interface ChefInterface {
    /**
     *  Getter function to know the Chef's current state.
     *  @return Chef's state
     */
    public EntityStates.ChefState getChefState();
    /**
     *  Setter function to update the Chef's state.
     *  @param newChefState new state to replace Chef's current state
     *  @return true if state is changed/updated, false if not
     */
    public boolean setChefState(EntityStates.ChefState newChefState);
}
