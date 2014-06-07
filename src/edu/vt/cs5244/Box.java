/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs5244;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author christopherbritz
 */
public class Box {
   
    private Player owner;
    private Set<Edge> edges = new HashSet<>();
    private int row;
    private int col;
    
    public Box(int row, int col){
        this.row = row;
        this.col = col;
        
    }
    
    public void setOwner(Player winner){
        this.owner = winner;
    }
    
    public Player getOwner(){
        return this.owner;
    }
    
    //get the edges drawn on this box object
    public Set<Edge> getDrawnEdges(){
        
        return this.edges;
    }
    
    public boolean drawEdge(Edge side, Player player, HashMap<String, Box> adjBoxMap){
        
        boolean isEdgeDrawn = false;
        
        
        if(!edges.contains(side) && this.checkAdjBoxEdges(adjBoxMap, side)){

            edges.add(side);
            isEdgeDrawn = true;

            //if all edges are drawn then size of set should equal 4
            //and thus the winner is the current player
            if(edges.size() == 4){
                this.setOwner(player);
            }
        }
        
        
        return isEdgeDrawn;

        
    }
    
    private boolean checkAdjBoxEdges(HashMap<String, Box> adjBoxMap, Edge edgeBeingDrawn){
         
        boolean canDrawEdge = true;
        
        for(Box box: adjBoxMap.values()){
            
            if(box != null && box.getDrawnEdges().contains(edgeBeingDrawn.opposite())){
                canDrawEdge = false;
                break;
            }
                 
        }
        
        return canDrawEdge;
        
    }
}
