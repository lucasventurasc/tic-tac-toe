package org.ventura.tictactoe.infrastructure.console.userinterface;

import org.ventura.tictactoe.domain.move.Move;

import static java.lang.Integer.parseInt;

public class ConsoleUserInputReader {

    private ScannerWrapper scanner;

    public ConsoleUserInputReader(ScannerWrapper scanner) {
        this.scanner = scanner;
    }

    public Move read() {
        try {
            String[] userInput = scanner.nextLine().split(",");
            int x = parseInt(userInput[0]);
            int y = parseInt(userInput[1]);
            return new Move(x, y);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            throw new InvalidInputException();
        }
    }
}
