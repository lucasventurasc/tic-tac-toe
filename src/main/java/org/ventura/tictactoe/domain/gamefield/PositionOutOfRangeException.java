package org.ventura.tictactoe.domain.gamefield;

public class PositionOutOfRangeException extends RuntimeException {

    public PositionOutOfRangeException() {
        super("Position typed is out of field range");
    }
}
