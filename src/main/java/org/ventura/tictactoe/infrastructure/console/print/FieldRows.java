package org.ventura.tictactoe.infrastructure.console.print;

class FieldRows {

    private int size;
    private int rowsCount;
    private StringBuilder rows;

    FieldRows(int size) {
        this.size = size;
        this.rows = new StringBuilder();
    }

    void addRow(FieldRowColumns fieldRowColumns) {
        String row = fieldRowColumns.toString();

        rowsCount = rowsCount + 1;
        if(rowsCount < size) {
            rows.append(row);
            rows.append("\n");
        } else {
            rows.append(row.replace("_", " "));
        }
    }

    public String toString() {
        return rows.toString();
    }
}


