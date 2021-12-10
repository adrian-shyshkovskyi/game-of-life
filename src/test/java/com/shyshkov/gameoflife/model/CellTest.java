package com.shyshkov.gameoflife.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CellTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testFromSymbol() {
        Cell liveCell = Cell.fromSymbol('■');
        Cell deadCell = Cell.fromSymbol('□');

        assertEquals(Cell.ALIVE, liveCell);
        assertEquals(Cell.DEAD, deadCell);
    }

    @Test
    public void testFromSymbolThrowsErrorForUnknownChar() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Cell with \"a\" symbol does not exist");

        Cell.fromSymbol('a');
    }

    @Test
    public void testGetSymbol() {
        assertEquals('□', Cell.DEAD.getSymbol());
        assertEquals('■', Cell.ALIVE.getSymbol());
    }

    @Test
    public void testToString() {
        assertEquals("□", Cell.DEAD.toString());
        assertEquals("■", Cell.ALIVE.toString());
    }
}