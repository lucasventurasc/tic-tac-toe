package org.ventura.tictactoe.infrastructure.console.userinterface.configuration;

import org.ventura.tictactoe.application.game.FieldConfiguration;
import org.ventura.tictactoe.application.game.GameConfiguration;
import org.ventura.tictactoe.application.players.PlayersConfiguration;
import org.ventura.tictactoe.infrastructure.console.userinterface.ScannerWrapper;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class GameConfigurationAsFile implements GameConfiguration {

    private Properties properties;
    private ScannerWrapper scannerWrapper;
    private PrintStream printStream;

    public GameConfigurationAsFile(ScannerWrapper scannerWrapper, PrintStream printStream) {
        this.scannerWrapper = scannerWrapper;
        this.printStream = printStream;
    }

    @Override
    public FieldConfiguration getFieldConfiguration() {
        loadIfPropertiesNull();
        return () -> parseInt(getMandatoryProperty("field.size"));
    }

    @Override
    public PlayersConfiguration getPlayersConfiguration() {
        loadIfPropertiesNull();
        return new PlayersConfiguration() {
            @Override
            public String characterForPlayer1() {
                return getMandatoryCharacter("player1.character");
            }

            @Override
            public String characterForPlayer2() {
                return getMandatoryCharacter("player2.character");
            }

            @Override
            public String characterForAI() {
                return getMandatoryCharacter("ai.character");
            }
        };
    }

    private void loadIfPropertiesNull() {
        if(properties != null) {
            return;
        }

        loadPropertiesFromConfigurationFile();
    }

    private void loadPropertiesFromConfigurationFile() {
        printStream.println("Please type the path for your configuration file:");
        try {
            this.properties = new Properties();
            this.properties.load(new FileReader(scannerWrapper.nextLine()));
        } catch (IOException e) {
            printStream.println(e.getMessage());
            loadPropertiesFromConfigurationFile();
        }
    }

    private String getMandatoryCharacter(String property) {
        String mandatoryProperty = getMandatoryProperty(property);

        if(mandatoryProperty.length() > 1) {
            throw new InvalidGameConfigurationFileException("Property " + property + " should have length 1");
        }

        return mandatoryProperty;
    }

    private String getMandatoryProperty(String property) {
        String mandatoryProperty = properties.getOrDefault(property, "").toString();
        if (!mandatoryProperty.isEmpty()) {
            return mandatoryProperty.trim();
        }
        throw new InvalidGameConfigurationFileException("All game options should be filled, missing: " + property);
    }

}
