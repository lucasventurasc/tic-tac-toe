package org.ventura.tictactoe.infrastructure.console.userinterface;

import org.ventura.tictactoe.application.game.UserInteraction;
import org.ventura.tictactoe.domain.gamefield.FieldState;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.domain.player.Player;
import org.ventura.tictactoe.infrastructure.console.print.FieldConsolePrinter;

import java.io.PrintStream;

public class ConsoleUserInteraction implements UserInteraction {

    private FieldConsolePrinter fieldConsolePrinter;
    private ConsoleUserInputReader consoleUserInputReader;
    private PrintStream printStream;

    public ConsoleUserInteraction(ConsoleUserInputReader consoleUserInputReader, PrintStream printStream) {
        this.printStream = printStream;
        this.consoleUserInputReader = consoleUserInputReader;
        this.fieldConsolePrinter = new FieldConsolePrinter(printStream);
    }

    @Override
    public Move waitForMoveFrom(Player player) {
        showMessage(player.getPlayerName() + ": your turn, type x,y");
        try {
            return consoleUserInputReader.read();
        } catch (InvalidInputException ex) {
            showMessage(ex.getMessage());
            return waitForMoveFrom(player);
        }
    }

    @Override
    public void updateField(FieldState fieldState) {
        fieldConsolePrinter.print(fieldState);
    }

    @Override
    public void showMessage(String message) {
        printStream.println(message);
    }
}
