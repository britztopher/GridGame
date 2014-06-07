/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs5244;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author christopherbritz
 */
public class HW1_DAB implements DABEngine{
    
    private boolean isInitialized = false;
    
    private HashMap<Player, Integer> scoreMap = new HashMap<>();
    private HashMap<Coordinate, Box> gameGridMap = new HashMap<>();
    private Player currPlayer;
    
    private int size;

    @Override
    public void init(int size) {
        
        if(size < 2){
            throw new DABException();
        }
        
        for(int row = 0; row<size; row++){
          
            for(int col=0; col<size; col++){
                gameGridMap.put(new Coordinate(row, col), new Box(row, col));
            }
        }
        
        //reseting player to Player One
       currPlayer = Player.ONE;
       
        //setting size to size of game board
        this.size = size*size;
           
        //reseting score;
        scoreMap.put(Player.ONE, 0);
        scoreMap.put(Player.TWO, 0);
        
        //game is initialized
        isInitialized = true;
    
    }

    @Override
    public int getSize() {
        
        if(!isInitialized){
            throw new DABException();
        }
        
        return size;
    }

    @Override
    public Set<Edge> getEdgesAt(int row, int col) {
        
        Set<Edge> usedEdgesSet = new HashSet<>();
        
        Box selectedBox = this.getSelectedBox(row, col);
        if(selectedBox ==null){
            throw new DABException();
        }
        
        //since this is a snapshot of the edges drawn we must create a new set
        //and add all the edges to it.
        usedEdgesSet.addAll(selectedBox.getDrawnEdges());
        
        return usedEdgesSet;
        
    }

    @Override
    public Player getOwnerAt(int row, int col) {
         Box selectedBox = this.getSelectedBox(row, col);
         
         return selectedBox.getOwner();
    }

    @Override
    public boolean drawEdge(int row, int col, Edge edge) {
       
         Box selectedBox = this.getSelectedBox(row, col);
         //find the adjacent box and draw the edge on opposite side
         Box adjBox =this.getAdjBox(row, col, edge);
         
         boolean isEdgeDrawn = false;
         boolean isAdjEdgeDrawn = false;
        //TODO-need to check if game is over too
        if(selectedBox == null || edge == null || !isInitialized ){
            throw new DABException();
        }
        
        //if the box is already owned then we dont want to draw an edge on it
        if(selectedBox.getOwner() == null){
        
            isEdgeDrawn = selectedBox.drawEdge(edge, currPlayer);
           
            //if selected box is not on the outer edge of the grid
            if(adjBox != null ){
                isAdjEdgeDrawn = adjBox.drawEdge(edge.opposite(), currPlayer);
            }
             
            //if the edge is drawn then next player should go unless player 
            //scored
            if(selectedBox.getOwner()==null){
                currPlayer = currPlayer.next();
            }else{
                this.addPointToPlayer(currPlayer);
                
                //if the adjacent box has an owner and there was a line drawn 
                //on the last turn on that adjacent box then award another point
                if(adjBox.getOwner()!=null && isAdjEdgeDrawn){
                    this.addPointToPlayer(currPlayer);
                }
                
            }
         }
         
         return isEdgeDrawn;
         
    }

    @Override
    public Map<Player, Integer> getScores() {
        
        if(!isInitialized){
            throw new DABException();
        }
        
        return scoreMap;
      
    }

    @Override
    public Player getTurn() {
        if(!isInitialized){
            throw new DABException();
        }
        return currPlayer;
    }
   
    /**
     * Get the box on the edge side of the selected Box.
     * 
     * if top edge.TOP chosen then get the box located above the selected box.
     * This returns null if the adjacent box sits outside of the grid.
     * 
     * @param row the row within the grid; the top row of squares is 0.
     * @param col the column within the grid; the left column of squares is 0.
     * @return a Box that is located on chosen edge direction
     */
    private Box getAdjBox(int row, int col, Edge edge){
        
        Box adjBox = new Box(row, col);
        
        if(edge == Edge.BOTTOM){
            adjBox = this.getSelectedBox(++row, col);
        }else if(edge == Edge.TOP){
            adjBox = this.getSelectedBox(--row, col);
        }else if(edge == Edge.LEFT){
            adjBox = this.getSelectedBox(row, --col);
        }else if(edge == Edge.RIGHT){
            adjBox = this.getSelectedBox(row, ++col);
        }
        
        return adjBox;
    }
    
    /**
     * Gets the selected box from the Collection.
     * 
     * This is a helper method for just searching the collection for the 
     * box that was selected.
     * 
     * @param row the row within the grid; the top row of squares is 0.
     * @param col the column within the grid; the left column of squares is 0.
     * @return the Box that contains the edge that was selected.
     */
    private Box getSelectedBox(int row, int col){
        
        Box selectedBox = gameGridMap.get(new Coordinate(row, col));
        
        return selectedBox;
    }
    
    /**
     * Add a point to player.
     * 
     * helper method for just adding a point to a player that scores.
     * 
     * @param player player to add the point to 
     * @return void because scoreBoard is a member variable, so it just modifies
     * the variable.
     */
    private void addPointToPlayer(Player player){
        //get the previous score of player in map 
        Integer score= scoreMap.get(player);

       //add one to that score
        scoreMap.put(player, ++score);
        
    }
}
