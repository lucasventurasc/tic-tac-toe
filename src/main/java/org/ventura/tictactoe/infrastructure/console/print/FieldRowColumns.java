package org.ventura.tictactoe.infrastructure.console.print;

import org.ventura.tictactoe.domain.gamefield.FieldPosition;

class FieldRowColumns {

    private StringBuilder column;

    FieldRowColumns() {
        this.column = new StringBuilder();
    }

    void addColumn(FieldPosition fieldPosition) {
        column.append(buildColumn(fieldPosition));
    }

    void addLastColumn(FieldPosition fieldPosition) {
        column.append(buildColumn(fieldPosition).replace("|", ""));
    }

    private String buildColumn(FieldPosition fieldPosition) {
        String character = fieldPosition != null ? fieldPosition.playerCharacter() : "";
        return getTemplate(character);
    }

    private String getTemplate(String characterToFill) {
        if(characterToFill.isEmpty()) {
            return "___|";
        }
        return "_" + characterToFill + "_|";
    }

    public String toString() {
        return column.toString();
    }
}

