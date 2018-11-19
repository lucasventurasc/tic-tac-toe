package org.ventura.tictactoe.infrastructure.console.userinterface.configuration;

public class InvalidGameConfigurationFileException extends RuntimeException {

    InvalidGameConfigurationFileException(String msg) {
        super("Invalid game configuration: " + msg);
    }

}
