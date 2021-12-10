package util;

import com.shyshkov.gameoflife.model.Cell;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class GridReader {

    public static Cell[][] readCells(InputStream gridInputStream) throws IOException {
        Objects.requireNonNull(gridInputStream, "No grid source was provided");

        List<String> gridRows = IOUtils.readLines(gridInputStream, Charset.defaultCharset());
        validateGridSize(gridRows);

        return gridRows.stream()
                .map(GridReader::toCells)
                .toArray(Cell[][]::new);
    }
    
    private static Cell[] toCells(String row) {
        return row.trim().chars()
                .mapToObj(symbol -> Cell.fromSymbol((char)symbol))
                .toArray(Cell[]::new);
    }

    private static void validateGridSize(List<String> gridRows) {
        Integer columnLength = gridRows.stream().findFirst().map(row -> row.trim().length()).orElse(0);
        if (gridRows.size() == 0 || columnLength == 0) {
            throw new IllegalArgumentException("Invalid grid dimensions");
        }

        Optional<String> corruptedRow = gridRows.stream()
                .filter(row -> row.trim().length() != columnLength)
                .findFirst();
        if (corruptedRow.isPresent()) {
            throw new IllegalArgumentException("The grid is not a matrix");
        }
    }
}
