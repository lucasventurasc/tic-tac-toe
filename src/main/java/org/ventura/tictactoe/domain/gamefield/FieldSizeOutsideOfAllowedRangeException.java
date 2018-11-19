package org.ventura.tictactoe.domain.gamefield;

public class FieldSizeOutsideOfAllowedRangeException extends RuntimeException {

    public FieldSizeOutsideOfAllowedRangeException() {
        super("Field size must be between 3 and 10");
    }
}
