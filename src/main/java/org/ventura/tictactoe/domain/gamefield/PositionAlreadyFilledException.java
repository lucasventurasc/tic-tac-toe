package org.ventura.tictactoe.domain.gamefield;

public class PositionAlreadyFilledException extends RuntimeException {

    public PositionAlreadyFilledException() {
        super("The chosen position is already filled already filled");
    }
}
