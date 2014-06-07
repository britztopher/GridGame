/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs5244;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        
        assert(size>=2);
        
        for(int row = 0; row<size; row++){
          
            for(int col=0; col<size; col++){
                gameGridMap.put(new Coordinate(row, col), new Box(row, col));
            }
        }
        
        //reseting player to Player One
       currPlayer = Player.ONE;
       
        //setting size to size of game board
        this.size = size;
           
        //reseting score;
        scoreMap.put(Player.ONE, 0);
        scoreMap.put(Player.TWO, 0);
        
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
           
            
            if(adjBox != null ){
                isAdjEdgeDrawn = adjBox.drawEdge(edge.opposite(), currPlayer);
            }
                   

            //if the edge is drawn then next player should go unless player scored
            if(selectedBox.getOwner()==null){
                currPlayer = currPlayer.next();
            }else{
                //get the previous score of player in map 
                Integer score= scoreMap.get(selectedBox.getOwner());

               //add one to that score
                scoreMap.put(selectedBox.getOwner(), ++score);
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
   
    //need to refactor this because we only care about one box.  The one
    //that is opposite of the side we are drawing the edge on.  ie - 
    //if box is 1,1 and edge = top then only box we care about is 0,1 not all of
    //the surrounding ones
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
    
    private Box getSelectedBox(int row, int col){
        
        Box selectedBox = gameGridMap.get(new Coordinate(row, col));
        
        return selectedBox;
    }
}
