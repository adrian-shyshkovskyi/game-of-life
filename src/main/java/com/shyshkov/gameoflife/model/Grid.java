package com.shyshkov.gameoflife.model;

import util.GridReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Object that represents game of life board.
 */
public class Grid {
    private Cell[][] cells;

    /**
     * Creates a new M x N grid filled with dead cells.
     *
     * @param height grid row count
     * @param width grid column count
     *
     * @throws IllegalArgumentException if invalid dimensions were provided
     */
    public Grid(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Invalid grid dimensions were provided");
        }
        this.cells = createDeadCellsBoard(height, width);
    }

    private Grid(Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * Creates a new Grid from a file.
     *
     * @param inputStream stream with grid content
     *
     * @throws IOException if an I/O error occurs
     * @return grid created based on file content
     */
    public static Grid fromStream(InputStream inputStream) throws IOException {
        Cell[][] cells = GridReader.readCells(inputStream);
        return new Grid(cells);
    }

    /**
     * Returns grid row count.
     *
     * @return grid row count
     */
    public int getHeight() {
        return cells.length;
    }

    /**
     * Returns grid column count
     *
     * @return grid column count
     */
    public int getWidth() {
        return cells[0].length;
    }

    /**
     * Returns the number of live cells that are direct neighbours of the cell
     * specified by <b>x</b> and <b>y</b> coordinates.
     *
     * @param x cell row number
     * @param y cell column number
     *
     * @return number of live cells for the provided cell
     */
    public int getLiveNeighboursCountAt(int x, int y) {
        validateCellCoordinates(x, y);

        int liveNeighbourCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // filter out current, out of border and dead cells
                if ((i != x || j != y)
                        && !areCoordinatesOutOfBorder(i, j)
                        && getCellAt(i, j) == Cell.ALIVE) {
                    liveNeighbourCount ++;
                }
            }
        }
        return liveNeighbourCount;
    }

    /**
     * Gets the cell at coordinates.
     *
     * @param x cell row number
     * @param y cell column number
     *
     * @throws IllegalArgumentException if coordinates are invalid
     * @return cell
     */
    public Cell getCellAt(int x, int y) {
        validateCellCoordinates(x, y);
        return cells[x][y];
    }

    /**
     * Sets the grid cell at coordinates to a new value.
     *
     * @param x cell row number
     * @param y cell column number
     * @param cell new cell value
     */
    public void setCellAt(int x, int y, Cell cell) {
        validateCellCoordinates(x, y);
        cells[x][y] = cell;
    }

    @Override
    public String toString() {
        return Stream.of(cells)
                .map(rows -> Stream.of(rows)
                        .map(Cell::toString)
                        .collect(Collectors.joining())
                )
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private static Cell[][] createDeadCellsBoard(int height, int width) {
        Cell[][] cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = Cell.DEAD;
            }
        }
        return cells;
    }

    private boolean areCoordinatesOutOfBorder(int x, int y) {
        return x < 0 || y < 0 || x >= getHeight() || y >= getWidth();
    }

    private void validateCellCoordinates(int x, int y) {
        if (areCoordinatesOutOfBorder(x, y)) {
            throw new IllegalArgumentException("Invalid cell coordinates");
        }
    }

}
