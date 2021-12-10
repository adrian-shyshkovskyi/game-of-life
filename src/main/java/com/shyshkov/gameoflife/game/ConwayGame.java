package com.shyshkov.gameoflife.game;

import com.shyshkov.gameoflife.model.Cell;
import com.shyshkov.gameoflife.model.Grid;

import java.util.Objects;

/**
 * A representation of Game of Life algorithm.
 * The game creates new cell generations based on the previous ones.
 */
public class ConwayGame {

    private Grid grid;

    /**
     * Creates a game and initializes it with the starting grid.
     *
     * @param grid starting grid
     */
    public ConwayGame(Grid grid) {
        Objects.requireNonNull(grid, "Game grid must be provided");
        this.grid = grid;
    }

    /**
     * Creates a new grid generation based on the previous one.
     *
     * @return new grid generation
     */
    public Grid createNextGeneration() {
        int height = grid.getHeight();
        int width = grid.getWidth();
        Grid nextGenGrid = new Grid(height, width);

        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                int liveNeighboursCount = grid.getLiveNeighboursCountAt(x, y);
                Cell nextGenCell = createNextGenerationCell(grid.getCellAt(x, y), liveNeighboursCount);

                nextGenGrid.setCellAt(x, y, nextGenCell);
            }
        }
        grid = nextGenGrid;
        return grid;
    }

    /**
     * Returns grid content as text.
     *
     * @return grid content as text
     */
    public String getGridAsText() {
        return grid.toString();
    }

    private Cell createNextGenerationCell(Cell previousGenCell, int liveNeighboursCount) {
        Cell nextGenCell = Cell.DEAD;
        if (previousGenCell == Cell.ALIVE) {
            if (liveNeighboursCount == 2 || liveNeighboursCount == 3) {
                nextGenCell = Cell.ALIVE;
            }
        } else if (liveNeighboursCount == 3) {
            nextGenCell = Cell.ALIVE;
        }
        return nextGenCell;
    }
}
