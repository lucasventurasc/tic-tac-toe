package org.ventura.tictactoe;

import org.ventura.tictactoe.application.ApplicationFactory;
import org.ventura.tictactoe.application.game.Game;

public class TicTacToe {

    static ApplicationFactory applicationFactory = new ConsoleApplicationFactory();

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();

        try {
            ticTacToe.run();
        } catch (Exception e) {
            applicationFactory.consoleUserInteraction().showMessage(e.getMessage());
        }
    }

    void run() {
        Game game = applicationFactory.game();

        game.start();
    }
}
