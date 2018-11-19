package org.ventura.tictactoe.infrastructure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ventura.tictactoe.application.game.FieldConfiguration;
import org.ventura.tictactoe.application.players.PlayersConfiguration;
import org.ventura.tictactoe.infrastructure.console.userinterface.ScannerWrapper;
import org.ventura.tictactoe.infrastructure.console.userinterface.configuration.GameConfigurationAsFile;
import org.ventura.tictactoe.infrastructure.console.userinterface.configuration.InvalidGameConfigurationFileException;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameConfigurationAsFileTest {

    @Mock
    private PrintStream printStream;

    @Mock
    private ScannerWrapper scannerWrapper;

    @InjectMocks
    private GameConfigurationAsFile gameConfiguration;

    @Test
    void shouldLoadConfigurationFileFromPropertiesFile() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test1.properties"));

        PlayersConfiguration playersConfiguration = gameConfiguration.getPlayersConfiguration();
        FieldConfiguration fieldConfiguration = gameConfiguration.getFieldConfiguration();

        assertThat(playersConfiguration.characterForPlayer1()).isEqualTo("X");
        assertThat(playersConfiguration.characterForPlayer2()).isEqualTo("O");
        assertThat(playersConfiguration.characterForAI()).isEqualTo("A");
        assertThat(fieldConfiguration.fieldSize()).isEqualTo(3);
    }

    @Test
    void shouldLoadConfigurationFileFromPropertiesFileWithSpaces() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test2.properties"));

        PlayersConfiguration playersConfiguration = gameConfiguration.getPlayersConfiguration();
        FieldConfiguration fieldConfiguration = gameConfiguration.getFieldConfiguration();

        assertThat(playersConfiguration.characterForPlayer1()).isEqualTo("X");
        assertThat(playersConfiguration.characterForPlayer2()).isEqualTo("O");
        assertThat(playersConfiguration.characterForAI()).isEqualTo("A");
        assertThat(fieldConfiguration.fieldSize()).isEqualTo(3);
    }

    @Test
    void characterForPlayer1ShouldBePresent() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test3.properties"));

        loadConfigurations();

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getPlayersConfiguration().characterForPlayer1();    
        });
        
    }

    @Test
    void characterForPlayer2ShouldBePresent() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test4.properties"));

        loadConfigurations();

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getPlayersConfiguration().characterForPlayer2();
        });
    }

    @Test
    void characterForAIShouldBePresent() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test5.properties"));

        loadConfigurations();

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getPlayersConfiguration().characterForAI();
        });
    }

    @Test
    void fieldSizeShouldBePresent() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test6.properties"));

        loadConfigurations();

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getFieldConfiguration().fieldSize();
        });
        
    }

    @Test
    void shouldPrintMessageToTheUserAskingWhereIsTheConfigurationFile() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test1.properties"));

        loadConfigurations();

        verify(printStream).println("Please type the path for your configuration file:");
    }

    @Test
    void shouldPrintMessageToTheUserWhenOccurProblemsOnFileRead() {
        String incorrectPath = "non_existing_configuration_file.properties";
        String correctPath = getAbsolutePathFor("game_configuration_test7.properties");

        when(scannerWrapper.nextLine()).thenReturn(incorrectPath, correctPath);

        PlayersConfiguration playersConfiguration = gameConfiguration.getPlayersConfiguration();
        FieldConfiguration fieldConfiguration = gameConfiguration.getFieldConfiguration();

        InOrder inOrder = Mockito.inOrder(printStream);
        inOrder.verify(printStream).println("Please type the path for your configuration file:");
        inOrder.verify(printStream).println(matches("(.*" + incorrectPath + ".*)"));
        inOrder.verify(printStream).println("Please type the path for your configuration file:");

        assertThat(playersConfiguration.characterForPlayer1()).isEqualTo("T");
        assertThat(playersConfiguration.characterForPlayer2()).isEqualTo("K");
        assertThat(playersConfiguration.characterForAI()).isEqualTo("H");
        assertThat(fieldConfiguration.fieldSize()).isEqualTo(5);
    }

    @Test
    void justAllowSingleCharacterForPlayerCharacters() {
        when(scannerWrapper.nextLine()).thenReturn(getAbsolutePathFor("game_configuration_test8.properties"));

        loadConfigurations();

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getPlayersConfiguration().characterForPlayer1();
        });

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getPlayersConfiguration().characterForPlayer2();
        });

        assertThrows(InvalidGameConfigurationFileException.class, () -> {
            gameConfiguration.getPlayersConfiguration().characterForAI();
        });
    }

    private void loadConfigurations() {
        GameConfigurationAsFile fileGameConfiguration = new GameConfigurationAsFile(scannerWrapper, printStream);
        fileGameConfiguration.getPlayersConfiguration();
        fileGameConfiguration.getFieldConfiguration();
    }

    private String getAbsolutePathFor(String file) {
        return getClass().getClassLoader().getResource(file).getPath().replace("%20", " ");
    }
}