package org.ventura.tictactoe.infrastructure.console.userinterface;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException() {
        super("Available inputs are integers inside of field range 'x,y'");
    }
}
