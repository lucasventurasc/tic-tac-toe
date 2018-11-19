package org.ventura.tictactoe;

import org.ventura.tictactoe.application.ApplicationFactory;
import org.ventura.tictactoe.application.ai.AIMove;
import org.ventura.tictactoe.application.ai.RandomAIMove;
import org.ventura.tictactoe.application.game.GameConfiguration;
import org.ventura.tictactoe.application.players.PlayersSorter;
import org.ventura.tictactoe.application.players.RandomPlayersSorter;
import org.ventura.tictactoe.infrastructure.console.userinterface.ConsoleUserInputReader;
import org.ventura.tictactoe.infrastructure.console.userinterface.ConsoleUserInteraction;
import org.ventura.tictactoe.infrastructure.console.userinterface.ScannerWrapper;
import org.ventura.tictactoe.infrastructure.console.userinterface.configuration.GameConfigurationAsFile;

import java.io.PrintStream;

public class ConsoleApplicationFactory implements ApplicationFactory {

    @Override
    public GameConfiguration gameConfiguration() {
        return new GameConfigurationAsFile(scannerWrapper(), printStream());
    }

    @Override
    public AIMove aiMove() {
        return new RandomAIMove();
    }

    @Override
    public ConsoleUserInteraction consoleUserInteraction() {
        ConsoleUserInputReader consoleUserInputReader = new ConsoleUserInputReader(scannerWrapper());
        return new ConsoleUserInteraction(consoleUserInputReader, printStream());
    }

    @Override
    public PlayersSorter playersSorter() {
        return new RandomPlayersSorter();
    }

    PrintStream printStream() {
        return System.out;
    }

    ScannerWrapper scannerWrapper() {
        return new ScannerWrapper();
    }
}
