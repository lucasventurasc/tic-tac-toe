package org.ventura.tictactoe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.gamefield.FieldSizeOutsideOfAllowedRangeException;
import org.ventura.tictactoe.domain.gamefield.PositionAlreadyFilledException;
import org.ventura.tictactoe.domain.gamefield.PositionOutOfRangeException;
import org.ventura.tictactoe.domain.move.Move;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldTest {

    @BeforeEach
    void setup() {

    }

    @Test
    void shouldReturnOptionalEmptyOnFieldPositionWhenPositionIsNotFilled() {
        Field field = new Field(3);

        assertThat(field.position(1, 1)).isNotPresent();
    }

    @Test
    void shouldThrowIllegalPositionExceptionWhenReceivedPositionIsAlreadyFiled() {
        Field field = new Field(3);
        field.move(new Move(0, 1), "X");

        assertThrows(PositionAlreadyFilledException.class, () -> {
            field.move(new Move(0, 1), "O");
        });

    }

    @Test
    void shouldThrowIllegalPositionExceptionWhenReceivedPositionOutOfRange_y() {
        Field field = new Field(3);

        assertThrows(PositionOutOfRangeException.class, () -> {
            field.move(new Move(0, 4), "X");
        });

    }

    @Test
    void shouldThrowIllegalPositionExceptionWhenReceivedPositionOutOfRange_Y_sameValueOfField() {
        Field field = new Field(3);

        assertThrows(PositionOutOfRangeException.class, () -> {
            field.move(new Move(0, 3), "X");
        });
    }

    @Test
    void shouldThrowIllegalPositionExceptionWhenReceivedPositionOutOfRange_x() {
        Field field = new Field(3);

        assertThrows(PositionOutOfRangeException.class, () -> {
            field.move(new Move(4, 0), "X");
        });
    }

    @Test
    void shouldThrowIllegalPositionExceptionWhenReceivedPositionOutOfRange_X_sameValueOfField() {
        Field field = new Field(3);

        assertThrows(PositionOutOfRangeException.class, () -> {
            field.move(new Move(3, 1), "X");
        });
        
    }

    @Test
    void fieldShouldReturnCompletedWhenHasAllPositionsFIlled() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");
        field.move(new Move(1, 0), "X");
        field.move(new Move(2, 0), "X");
        field.move(new Move(0, 2), "O");
        field.move(new Move(1, 1), "O");
        field.move(new Move(2, 1), "O");
        field.move(new Move(0, 1), "A");
        field.move(new Move(2, 2), "A");
        field.move(new Move(1, 2), "A");

        assertThat(field.isCompleted()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenFieldSizeIsGreaterThanMaximumAllowed() {
        assertThrows(FieldSizeOutsideOfAllowedRangeException.class, () -> {
            new Field(11);
        });

    }

    @Test
    void shouldThrowExceptionWhenFieldSizeIsLessGreaterThanMinimumAllowed() {
        assertThrows(FieldSizeOutsideOfAllowedRangeException.class, () -> {
            new Field(2);
        });
    }

    @Test
    void shouldNotThrownExceptionWhenFieldSizeIsTheMaximumAllowed() {
        new Field(10);
    }

}