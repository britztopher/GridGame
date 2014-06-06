package edu.vt.cs5244;

/**
 * This class represents a single edge of a box.
 * 
 * TOP, RIGHT, BOTTOM, and LEFT indicate their natural meanings.
 * Please note: This class must not be altered in any way; you must use it exactly as-is.
 * 
 */
public enum Edge {
    TOP, RIGHT, BOTTOM, LEFT;

    /**
     * Get the opposite edge.
     * 
     * @return the edge directly opposite of the current edge.
     * @throws DABException if the current edge is invalid (due to this class being altered)
     */
    public Edge opposite() {
        switch(this) {
            case TOP: return BOTTOM;
            case BOTTOM: return TOP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        throw new DABException();
    }
}
