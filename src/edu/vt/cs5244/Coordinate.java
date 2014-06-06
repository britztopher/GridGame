/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.vt.cs5244;

/**
 *
 * @author christopherbritz
 */
public class Coordinate {
    
    private int myRow;
    private int myCol;
    
    public Coordinate(int row, int col){
        
        myRow = row;
        myCol = col;
        
    }
    
    public void setRow(int row){
       
        this.myRow = row;
    }
    
    
    public int getRow(){
       
        return this.myRow;
    }
      
    public void setCol(int col){
       
        this.myCol = col;
    }
    
    
    public int getCol(){
       
        return this.myCol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.myRow;
        hash = 71 * hash + this.myCol;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.myRow != other.myRow) {
            return false;
        }
        if (this.myCol != other.myCol) {
            return false;
        }
        return true;
    }
   
  
}
