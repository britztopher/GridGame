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
public class Box {
   
    private Player winner;
    private Set<Edge> edges = new HashSet<>();
    
    public Box(){
        
    }
    
    public void setWinner(Player winner){
        this.winner = winner;
    }
    
    public Player getOwner(){
        return this.winner;
    }
    
    //get the edges drawn on this box object
    public Set<Edge> getDrawnEdges(){
        
        return this.edges;
    }
    
    public boolean drawEdge(Edge side, Player player, HashMap<String, Box> adjBoxMap){
        
        boolean isEdgeDrawn = false;
        
        for(Map.Entry<String, Box> entry : adjBoxMap.entrySet()){
            
            String key = entry.getKey();
            Box box = entry.getValue();
            
            if(box.getDrawnEdges().contains(side.opposite())){
                isEdgeDrawn = false;
            }
                 
        }
        
        if(!edges.contains(side)){
            
            edges.add(side);
            isEdgeDrawn = true;
            
            //if all edges are drawn then size of set should equal 4
            //and thus the winner is the current player
            if(edges.size() == 4){
                this.setWinner(player);
            }
        }
        
        return isEdgeDrawn;
        
    }
    
    public boolean isEdgeShared(Box box){
        
        boolean isEdgeShared = false;
        
        return true;
    }
}
