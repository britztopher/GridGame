package edu.vt.cs5244;

import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HW1_DABTest {

    private DABEngine game;
    
    public HW1_DABTest() {
    }
    
    @Before
    public void setUp() {
        game = new HW1_DAB();
        game.init(2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetTurn() {
        Player first = game.getTurn();
        assertEquals("First player must be ONE", Player.ONE, first);
        game.drawEdge(0, 0, Edge.TOP);
        Player next = game.getTurn();
        assertEquals("Second player must be TWO", Player.TWO, next);
    }

    @Test
    public void testDrawEdge() {
        boolean result = game.drawEdge(0, 0, Edge.BOTTOM);
        assertTrue("Drawing new edge must return true", result);
        result = game.drawEdge(0, 0, Edge.BOTTOM);
        assertFalse("Drawing duplicate edge must return false", result);
        result = game.drawEdge(1, 0, Edge.TOP);
        assertFalse("Drawing edge from shared box must return false", result);
        game.drawEdge(0, 0, Edge.LEFT);
        game.drawEdge(0, 0, Edge.TOP);
        Player player = game.getTurn();
        game.drawEdge(0, 0, Edge.RIGHT);
        assertEquals("Player must go again if box is completed", player, game.getTurn());
    }

    @Test
    public void testGetEdgesAt() {
        game.drawEdge(0, 0, Edge.BOTTOM);
        game.drawEdge(0, 0, Edge.LEFT);
        game.drawEdge(0, 0, Edge.TOP);
        
        Set<Edge> three = game.getEdgesAt(0, 0);
        assertTrue("Drawn edge must appear in getEdgesAt", three.contains(Edge.BOTTOM));
        assertTrue("Drawn edge must appear in getEdgesAt", three.contains(Edge.LEFT));
        assertTrue("Drawn edge must appear in getEdgesAt", three.contains(Edge.TOP));
        assertFalse("Undrawn edge must not appear in getEdgesAt", three.contains(Edge.RIGHT));

        game.drawEdge(0, 0, Edge.RIGHT);
        assertEquals("New edge must appear in new getEdgesAt", 4, game.getEdgesAt(0, 0).size());
        //assertFalse("New edge must not appear in previous getEdgesAt", three.contains(Edge.RIGHT));
        
        assertTrue("Shared edge must appear in getEdgesAt of neighbor", game.getEdgesAt(1, 0).contains(Edge.TOP));
        assertTrue("Shared edge must appear in getEdgesAt of neighbor", game.getEdgesAt(0, 1).contains(Edge.LEFT));
    }

    @Test
    public void testGetOwnerAt() {
        game.drawEdge(0, 0, Edge.BOTTOM); //ONE
        game.drawEdge(0, 0, Edge.LEFT); //TWO
        game.drawEdge(0, 0, Edge.TOP); //ONE
        assertNull("Owner of incomplete box must be null", game.getOwnerAt(0, 0));
        game.drawEdge(0, 0, Edge.RIGHT); //TWO
        assertEquals("Owner of completed box must be player drawing fourth edge", Player.TWO, game.getOwnerAt(0, 0));
    }

    @Test
    public void testGetScores() {
        game.drawEdge(0, 0, Edge.BOTTOM); //ONE
        game.drawEdge(0, 0, Edge.LEFT); //TWO
        game.drawEdge(0, 0, Edge.TOP); //ONE
        game.drawEdge(0, 0, Edge.RIGHT); //TWO
        Map<Player, Integer> scoreMap = game.getScores();
        assertEquals("Score must include box completed by player", (Integer)1, scoreMap.get(Player.TWO));
        assertEquals("Score must not include box completed by other", (Integer)0, scoreMap.get(Player.ONE));
    }
    
    @Test
    public void testExceptions() {
        try {
            new HW1_DAB().getTurn();
            fail("Getting turn of non-initialized game must throw DABException");
        } catch (Exception e) {
            if (!(e instanceof DABException)) {
                fail("Wrong exception thrown getting turn of non-initialized game");
            }
        }
        try {
            game.drawEdge(0, -1, Edge.TOP);
            fail("Drawing at invalid location must throw DABException");
        } catch (Exception e) {
            if (!(e instanceof DABException)) {
                fail("Wrong exception thrown drawing at invalid location");
            }
        }
        try {
            game.drawEdge(0, 0, null);
            fail("Drawing null edge must throw DABException");
        } catch (Exception e) {
            if (!(e instanceof DABException)) {
                fail("Wrong exception thrown drawing null edge");
            }
        }
        try {
            game.getEdgesAt(2, 0);
            fail("Getting edge at invalid location must throw DABException");
        } catch (Exception e) {
            if (!(e instanceof DABException)) {
                fail("Wrong exception thrown getting edge at invalid location");
            }
        }
        
    }
    
}