package edu.vt.cs5244;

/**
 * This class represents a player by number.
 * 
 * ONE is the first player; TWO is the second player.
 * Please note: This class must not be altered in any way; you must use it exactly as-is.
 * 
 */
public enum Player {
    ONE, TWO;
   
    /**
     * Get the next player.
     * 
     * @return the next player, based on the current player.
     * @throws DABException if the current player is invalid (due to this class being altered)
     */
    public Player next() {
        switch(this) {
            case ONE: return TWO;
            case TWO: return ONE;
        }
        throw new DABException();
    }
}
