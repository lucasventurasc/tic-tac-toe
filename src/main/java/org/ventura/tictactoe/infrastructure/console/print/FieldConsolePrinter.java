package org.ventura.tictactoe.infrastructure.console.print;

import org.ventura.tictactoe.domain.gamefield.FieldPosition;
import org.ventura.tictactoe.domain.gamefield.FieldState;

import java.io.PrintStream;

public class FieldConsolePrinter {

    private PrintStream out;

    public FieldConsolePrinter(PrintStream out) {
        this.out = out;
    }

    public void print(FieldState fieldState) {
        FieldRows fieldRows = new FieldRows(fieldState.size());
        for (int row = 0; row < fieldState.size(); row++) {
            fieldRows.addRow(buildRow(row, fieldState));
        }
        out.println(fieldRows.toString());
    }

    private FieldRowColumns buildRow(int row, FieldState field) {
        FieldRowColumns fieldRowColumns = new FieldRowColumns();
        for (int column = 0; column < field.size(); column++) {
            FieldPosition fieldPosition = field.position(row, column).orElse(null);
            if (isLastColumn(field, column)) {
                fieldRowColumns.addLastColumn(fieldPosition);
            } else {
                fieldRowColumns.addColumn(fieldPosition);
            }
        }
        return fieldRowColumns;
    }

    private boolean isLastColumn(FieldState field, int column) {
        return column + 1 == field.size();
    }

}

