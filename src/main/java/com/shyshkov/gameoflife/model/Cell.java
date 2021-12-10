package com.shyshkov.gameoflife.model;

import java.util.stream.Stream;

/**
 * Object that represents a single cell in the grid.
 */
public enum Cell {
    ALIVE('■'),
    DEAD('□');

    private char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Provides text representation of the cell.
     *
     * @return text representation of the cell
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns a corresponding cell for the provided symbols.
     *
     * @param symbol cell symbol
     *
     * @throws IllegalArgumentException if no cell could be found for the provided symbol
     * @return cell
     */
    public static Cell fromSymbol(char symbol) {
        return Stream.of(values())
                .filter(cell -> cell.getSymbol() == symbol)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Cell with \"%s\" symbol does not exist", symbol)));
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
