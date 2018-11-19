package org.ventura.tictactoe.domain.gamefield;

public class FieldPosition {

    private int rowIndex;
    private int columnIndex;
    private String playerCharacter;

    FieldPosition(int rowIndex, int columnIndex, String playerCharacter) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.playerCharacter = playerCharacter;
    }

    public int row() {
        return rowIndex;
    }

    public int column() {
        return columnIndex;
    }

    public String playerCharacter() {
        return playerCharacter;
    }
}
