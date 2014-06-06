/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs5244;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author christopherbritz
 */
public class HW1_DAB implements DABEngine{
    
    private boolean notInitialized = false;
    
    private HashMap<Player, Integer> scoreMap = new HashMap<>();
    private HashMap<Coordinate, Box> gameGridMap = new HashMap<>();
    private Player currPlayer;
    
    private int size;

    @Override
    public void init(int size) {
        
        assert(size>=2);
        
        for(int row = 0; row<size; row++){
          
            for(int col=0; col<size; col++){
                gameGridMap.put(new Coordinate(row, col), new Box());
            }
        }
        
        //reseting player to Player One
       currPlayer = Player.ONE;
       
        //setting size to size of game board
        this.size = size;
           
        //reseting score;
        scoreMap.put(Player.ONE, 0);
        scoreMap.put(Player.TWO, 0);
        
        notInitialized = true;
    
    }

    @Override
    public int getSize() {
        
        if(notInitialized){
            throw new DABException();
        }
        
        return size;
    }

    @Override
    public Set<Edge> getEdgesAt(int row, int col) {
        
        Box selectedBox = this.getSelectedBox(row, col);
      
        return selectedBox.getDrawnEdges();
        
    }

    @Override
    public Player getOwnerAt(int row, int col) {
         Box selectedBox = this.getSelectedBox(row, col);
         
         return selectedBox.getOwner();
    }

    @Override
    public boolean drawEdge(int row, int col, Edge edge) {
       
         Box selectedBox = this.getSelectedBox(row, col);
         
         //TODO-need to check if game is over too
         if(selectedBox == null || edge == null || notInitialized ){
             throw new DABException();
         }
         
         
         
         boolean isEdgeDrawn = selectedBox.drawEdge(edge, currPlayer, this.getAd);
         
         //if the edge is drawn then next player should go unless player scored
         if(isEdgeDrawn){
             currPlayer.next();
         }
         
         return isEdgeDrawn;
         
    }

    @Override
    public Map<Player, Integer> getScores() {
        
        if(notInitialized){
            throw new DABException();
        }
        
        return scoreMap;
      
    }

    @Override
    public Player getTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    private HashMap<String, Box> getAdjacentBoxes(int row, int col){
        
        Box selectedBox = this.getSelectedBox(row, col);
        
        HashMap<String, Box> adjBoxMap = new HashMap<>();
        
        adjBoxMap.put("leftBox",this.getSelectedBox(row, --col));
        adjBoxMap.put("rightBox",this.getSelectedBox(row, ++col));
        adjBoxMap.put("topBox",this.getSelectedBox(--row, col));
        adjBoxMap.put("bottomBox",this.getSelectedBox(++row, col));
        
        return adjBoxMap;
    }
    
    private Box getSelectedBox(int row, int col){
        
        Box selectedBox = gameGridMap.get(new Coordinate(row, col));
        
        return selectedBox;
    }
}
