package org.ventura.tictactoe.infrastructure.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.infrastructure.console.print.FieldConsolePrinter;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class FieldConsolePrinterTest {

    private PrintStream printStreamMock;
    private FieldConsolePrinter printer;

    @BeforeEach
    public void setup() {
        printStreamMock = mock(PrintStream.class);
        printer = new FieldConsolePrinter(printStreamMock);
    }

    @Test
    public void shouldPrintAnEmpty3x3Field() {
        Field field = new Field(3);

        printer.print(field);

        verify(printStreamMock, once()).println(build3x3EmptyField());
    }

    @Test
    public void shouldPrintAnEmpty4x4Field() {
        Field field = new Field(4);

        printer.print(field);

        verify(printStreamMock, once()).println(build4x4EmptyField());
    }
    
    @Test
    public void shouldPrintAFieldWithData() {
        Field field = new Field(3);

        field.move(new Move(1, 1), "x");

        printer.print(field);
        
        String fieldRepresentation = "___|___|___\n" +
                                     "___|_x_|___\n" +
                                     "   |   |   ";

        verify(printStreamMock, once()).println(fieldRepresentation);
    }

    @Test
    public void shouldPrintAFieldRepresentingAWonGame() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "x");
        field.move(new Move(1, 1), "x");
        field.move(new Move(2, 2), "x");

        printer.print(field);

        String fieldRepresentation =
                "_x_|___|___\n" +
                "___|_x_|___\n" +
                "   |   | x ";

        verify(printStreamMock, once()).println(fieldRepresentation);
    }

    private String build3x3EmptyField() {
        return "___|___|___\n" +
               "___|___|___\n" +
               "   |   |   ";
    }

    private String build4x4EmptyField() {
        return "___|___|___|___\n" +
               "___|___|___|___\n" +
               "___|___|___|___\n" +
               "   |   |   |   ";
    }

    private VerificationMode once() {
        return times(1);
    }

}