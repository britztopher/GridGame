package edu.vt.cs5244;

import java.util.Map;
import java.util.Set;

/**
 * This interface defines the methods needed to manage the basic two-player game of Dots And Boxes.
 * 
 * This game is played on a array of dots, each representing one vertex of a
 * grid of adjacent boxes.  Players alternate turns, drawing a single line connecting
 * two adjacent dots horizontally or vertically.  A player who completes a box owns
 * that box, and gets another turn.  Play continues until all boxes are completed,
 * at which point the winner is the player who owns the most boxes.
 * <p>
 * Each instance of an implementation of this interface must be entirely independent 
 * of any other instances.
 * Please note: This interface must not be altered in any way; you must implement it exactly as-is.
 * 
 */
public interface DABEngine {
    
    /**
     * Initialize the engine for a new game.
     * 
     * Applications must initialize the engine before calling any other methods.
     * This method creates a grid with no drawn edges, resets all scores to zero,
     * and sets the current player to ONE.
     * A game grid always has equal height and width; 
     * the smallest grid size allowed is 2 (meaning 2 by 2, or 4 boxes).
     * 
     * @param size the width (and thus height) of the game grid, in boxes.
     * @throws DABException if the specified size is less than 2.
     */
    public void init(int size);
    
    /**
     * Get the current size of the game grid.
     * 
     * This will always be the same as the size used to invoke the most
     * recent successful initialization.
     * 
     * @return  the width (and height) of the game grid, in squares.
     * @throws DABException if the game is not initialized.
     */
    public int getSize();
    
    /**
     * Get a collection of edges currently drawn at a specified location.
     * 
     * If there are no edges currently drawn here, the returned collection has zero elements.
     * The sequence of elements within the collection is unspecified, and may vary by implementation.
     * The returned collection is a fixed snapshot in time, and must not reflect any future changes.
     * 
     * @see edu.vt.cs5244.Edge
     * 
     * @param row the row within the grid; the top row of squares is 0.
     * @param col the column within the grid; the left column of squares is 0.
     * @return a Set containing zero or more Edge objects, representing drawn lines.
     * @throws DABException if the location is invalid, or the game is not initialized.
     */
    public Set<Edge> getEdgesAt(int row, int col);
    
    /**
     * Get the owner of the fully-drawn box at a specified location.
     * 
     * The owner of a box is the Player who drew the final (fourth) edge of that box.
     * If the specified box has fewer than four edges, this method returns null.
     * 
     * @see edu.vt.cs5244.Player
     * 
     * @param row the row within the grid; the top row of squares is 0.
     * @param col the column within the grid; the left column of squares is 0.
     * @return the Player who owns this box, or null if this box has fewer than four edges.
     * @throws DABException if the location is invalid, or the game is not initialized.
     */
    public Player getOwnerAt(int row, int col);

    /**
     * Draw an edge of the box at a specified location by the current player.
     * 
     * This method returns true if the edge is drawn as a result of this call,
     * and returns false if the edge was already drawn.
     * If this edge is drawn, and it is the fourth edge of one or two boxes,
     * the current player will become the owner of each newly-completed box.
     * <p>
     * If this is the last edge to be drawn of the entire grid, the game is over.
     * If the game is not over, and a box was completed, the current player 
     * gets another turn.  If the game is not over, but no box was completed,
     * the turn is over and other player becomes the current player.
     * <p>
     * Note that each edge within the grid is shared by two distinct adjacent
     * boxes, except for the outermost edges of the entire grid.
     * For example, the BOTTOM edge of the box at (row 0, col 0) is the same as
     * the TOP edge of the box at (row 1, col 0).
     * 
     * @see edu.vt.cs5244.Edge
     * 
     * @param row the row within the grid; the top row of squares is 0.
     * @param col the column within the grid; the left column of squares is 0.
     * @param edge which Edge to draw.
     * @return true if the edge was drawn successfully; 
     * false otherwise (if the edge had already been drawn by either player)
     * @throws DABException if the location is invalid, the edge is null, 
     * the game is over, or the game is not initialized.
     * 
     */
    public boolean drawEdge(int row, int col, Edge edge);

    /**
     * Get a collection of the scores of the players.
     * 
     * The score of each player is the number of boxes currently owned by that player.
     * 
     * @return a Map of current scores, where the Player is the key,
     * and the score is the value.
     * @throws DABException if the game is not initialized.
     * 
     */
    public Map<Player,Integer> getScores();

    /**
     * Get the player for the current turn.
     * 
     * Each game begins with player ONE.  A turn consists of the current player
     * drawing a single edge.  If the drawn edge does not complete a box, the
     * turn is over and the other player is the current player.  If the drawn
     * edge completes at least one box, the same player gets another turn.
     * A player can continue in this manner until a turn does not result in a 
     * completed box (thus ending this player's turn), or until all possible 
     * edges have been drawn (thus ending the game).
     * 
     * @return the current player, or null if the game is over.
     * @throws DABException if the game is not initialized.
     */
    public Player getTurn();
    
}