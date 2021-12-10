package com.shyshkov.gameoflife.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.testng.asserts.SoftAssert;
import util.GridReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GridReader.class)
public class GridTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testNewGridCreatedUsingDimensions() {
        Grid grid = new Grid(2, 2);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(Cell.DEAD, grid.getCellAt(0, 0));
        softAssert.assertEquals(Cell.DEAD, grid.getCellAt(0, 1));
        softAssert.assertEquals(Cell.DEAD, grid.getCellAt(1, 0));
        softAssert.assertEquals(Cell.DEAD, grid.getCellAt(1, 1));
    }

    @Test
    public void testNewGridCannotBeCreatedWithInvalidRowCount() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid grid dimensions were provided");

        new Grid(0, 1);
    }

    @Test
    public void testNewGridCannotBeCreatedWithInvalidColumnCount() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid grid dimensions were provided");

        new Grid(15, 0);
    }

    @Test
    public void testFromFile() throws IOException {
        Cell[][] expectedCells = new Cell[][] { new Cell[] { Cell.ALIVE } };
        PowerMockito.mockStatic(GridReader.class);
        PowerMockito.when(GridReader.readCells(any())).thenReturn(expectedCells);

        Grid grid = Grid.fromStream(mock(InputStream.class));
        assertEquals(1, grid.getHeight());
        assertEquals(1, grid.getWidth());
        assertEquals(Cell.ALIVE, grid.getCellAt(0, 0));
    }

    @Test
    public void testGetHeight() {
        Grid grid = new Grid(25, 12);

        assertEquals(25, grid.getHeight());
    }

    @Test
    public void testGetWidth() {
        Grid grid = new Grid(25, 12);

        assertEquals(12, grid.getWidth());
    }

    @Test
    public void testGetLiveNeighboursReturnsOnlyNeighborsCount() throws IOException {
        String grid4x4Content =
                "■■■■\n" +
                "■■■■\n" +
                "■■■■\n" +
                "■■■■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid4x4Content.getBytes()));
        assertEquals(8, grid.getLiveNeighboursCountAt(2, 2));
    }

    @Test
    public void testGetLiveNeighboursForOneCellGrid() throws IOException {
        String grid1x1Content = "■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid1x1Content.getBytes()));
        assertEquals(0, grid.getLiveNeighboursCountAt(0, 0));
    }

    @Test
    public void testGetLiveNeighboursWontCountOutOfBorderNeighbours() throws IOException {
        String grid2x2Content =
                "□■\n" +
                "■■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid2x2Content.getBytes()));
        assertEquals(3, grid.getLiveNeighboursCountAt(0, 0));
        assertEquals(2, grid.getLiveNeighboursCountAt(1, 1));
    }

    @Test
    public void testGetLiveNeighboursWillCountOnlyLiveCells() throws IOException {
        String grid3x3Content =
                "□■□\n" +
                "□■■\n" +
                "□□■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid3x3Content.getBytes()));
        assertEquals(2, grid.getLiveNeighboursCountAt(0, 0));
        assertEquals(2, grid.getLiveNeighboursCountAt(0, 1));
        assertEquals(3, grid.getLiveNeighboursCountAt(0, 2));
        assertEquals(2, grid.getLiveNeighboursCountAt(1, 0));
        assertEquals(3, grid.getLiveNeighboursCountAt(1, 1));
        assertEquals(3, grid.getLiveNeighboursCountAt(1, 2));
        assertEquals(1, grid.getLiveNeighboursCountAt(2, 0));
        assertEquals(3, grid.getLiveNeighboursCountAt(2, 1));
        assertEquals(2, grid.getLiveNeighboursCountAt(2, 2));
    }

    @Test
    public void testGetLiveNeighboursNegativeRowNumber() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        String grid3x3Content =
                "□■□\n" +
                "□■■\n" +
                "□□■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid3x3Content.getBytes()));
        grid.getLiveNeighboursCountAt(-1, 1);
    }

    @Test
    public void testGetLiveNeighboursRowNumberOutOfBorder() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        String grid3x3Content =
                "□■□\n" +
                "□■■\n" +
                "□□■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid3x3Content.getBytes()));
        grid.getLiveNeighboursCountAt(3, 1);
    }

    @Test
    public void testGetLiveNeighboursNegativeColumnNumber() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        String grid3x3Content =
                "□■□\n" +
                "□■■\n" +
                "□□■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid3x3Content.getBytes()));
        grid.getLiveNeighboursCountAt(1, -1);
    }

    @Test
    public void testGetLiveNeighboursColumnNumberOutOfBorder() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        String grid3x3Content =
                "□■□\n" +
                "□■■\n" +
                "□□■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid3x3Content.getBytes()));
        grid.getLiveNeighboursCountAt(0, 5);
    }

    @Test
    public void testGetCellAt() throws IOException {
        String grid2x2Content =
                "□■\n" +
                "■■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid2x2Content.getBytes()));

        assertEquals(Cell.DEAD, grid.getCellAt(0, 0));
        assertEquals(Cell.ALIVE, grid.getCellAt(0, 1));
        assertEquals(Cell.ALIVE, grid.getCellAt(1, 0));
        assertEquals(Cell.ALIVE, grid.getCellAt(1, 1));
    }

    @Test
    public void testGetCellAtInvalid() throws IOException {
        String grid2x2Content =
                "□■\n" +
                "■■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid2x2Content.getBytes()));

        assertEquals(Cell.DEAD, grid.getCellAt(0, 0));
        assertEquals(Cell.ALIVE, grid.getCellAt(0, 1));
        assertEquals(Cell.ALIVE, grid.getCellAt(1, 0));
        assertEquals(Cell.ALIVE, grid.getCellAt(1, 1));
    }

    @Test
    public void testGetCellAtNegativeRowCount() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.getCellAt(-1, 0);
    }

    @Test
    public void testGetCellAtRowCountOutOfBorder() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.getCellAt(1, 0);
    }

    @Test
    public void testGetCellAtNegativeColumnCount() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.getCellAt(0, -1);
    }

    @Test
    public void testGetCellAtColumnCountOutOfBorder() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.getCellAt(0, 1);
    }

    @Test
    public void testSetCellAt() {
        Grid grid = new Grid(2, 2);

        grid.setCellAt(1, 0, Cell.ALIVE);
        assertEquals(Cell.ALIVE, grid.getCellAt(1, 0));
    }

    @Test
    public void testSetCellAtNegativeRowCount() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.setCellAt(-1, 0, Cell.ALIVE);
    }

    @Test
    public void testSetCellAtRowCountOutOfBorder() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.setCellAt(1, 0, Cell.ALIVE);
    }

    @Test
    public void testSetCellAtNegativeColumnCount() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.setCellAt(0, -1, Cell.ALIVE);
    }

    @Test
    public void testSetCellAtColumnCountOutOfBorder() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Invalid cell coordinates");

        Grid grid = new Grid(1, 1);
        grid.setCellAt(0, 1, Cell.ALIVE);
    }

    @Test
    public void testToString() throws IOException {
        String grid2x2Content =
                "□■\n" +
                "■■";

        Grid grid = Grid.fromStream(new ByteArrayInputStream(grid2x2Content.getBytes()));

        assertEquals(String.join(System.lineSeparator(), "□■", "■■"), grid.toString());
    }
}
