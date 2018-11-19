package org.ventura.tictactoe.application.game;

import org.junit.jupiter.api.Test;
import org.ventura.tictactoe.domain.gamefield.Field;
import org.ventura.tictactoe.domain.gamefield.GameResultChecker;
import org.ventura.tictactoe.domain.move.Move;
import org.ventura.tictactoe.domain.move.MoveResult;

import static org.assertj.core.api.Assertions.assertThat;


class GameResultCheckerTest {

    @Test
    void shouldReturnNoResultWhenMoveDoesNotHaveWinnerOrLooser() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");

        assertCheckNoResult(field);
    }

    @Test
    /*
     * _x_|_o_|_a_
     * _x_|_o_|_a_
     *  a | x | o
     */
    void shouldReturnWithdrawAllPositionsAreFilledWithoutWinners() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");
        field.move(new Move(0, 1), "O");
        field.move(new Move(0, 2), "A");

        field.move(new Move(1, 0), "X");
        field.move(new Move(1, 1), "O");
        field.move(new Move(1, 2), "A");

        field.move(new Move(2, 1), "X");
        field.move(new Move(2, 2), "O");
        field.move(new Move(2, 0), "A");

        assertCheckDraw(field);
    }

    @Test
    /*
     * _x_|_o_|_a_
     * _x_|_o_|_a_
     *  x |   |
     */
    void shouldReturnWinnerWhenPlayerFillsAnEntireColumn_left() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");
        field.move(new Move(0, 1), "O");
        field.move(new Move(0, 2), "A");

        field.move(new Move(1, 0), "X");
        field.move(new Move(1, 1), "O");
        field.move(new Move(1, 2), "A");

        field.move(new Move(2, 0), "X");

        assertCheckWinner(field);
    }

    @Test
    /*
     * _x_|_o_|___
     * _x_|_o_|_a_
     *  a | o | x
     */
    void shouldReturnWinnerWhenPlayerFillsAnEntireColumn_middle() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");
        field.move(new Move(0, 1), "O");
        field.move(new Move(0, 2), "A");

        field.move(new Move(1, 0), "X");
        field.move(new Move(1, 1), "O");
        field.move(new Move(1, 2), "A");
        field.move(new Move(2, 2), "X");
        field.move(new Move(2, 1), "O");

        assertCheckWinner(field);
    }

    @Test
    /*
     * _o_|_o_|_o_
     * _x_|___|_a_
     *  x | a | x
     */
    void shouldReturnWinnerWhenPlayerFillsAnEntireRow_top() {
        Field field = new Field(3);

        field.move(new Move(2, 2), "X");
        field.move(new Move(0, 0), "O");
        field.move(new Move(2, 1), "A");

        field.move(new Move(1, 0), "X");
        field.move(new Move(0, 1), "O");
        field.move(new Move(1, 2), "A");

        field.move(new Move(2, 0), "X");
        field.move(new Move(0, 2), "O");

        assertCheckWinner(field);
    }

    @Test
    /*
     * _x_|___|_a_
     * _o_|_o_|_o_
     *  x | a | x
     */
    void shouldReturnWinnerWhenPlayerFillsAnEntireRow_middle() {
        Field field = new Field(3);

        field.move(new Move(2, 2), "X");
        field.move(new Move(1, 0), "O");
        field.move(new Move(2, 1), "A");

        field.move(new Move(0, 0), "X");
        field.move(new Move(1, 1), "O");
        field.move(new Move(0, 2), "A");

        field.move(new Move(2, 0), "X");
        field.move(new Move(1, 2), "O");

        assertCheckWinner(field);
    }

    @Test
    /*
     * _x_|___|_a_
     * _o_|_o_|_o_
     *  x | a | x
     */
    void shouldNotBeWithdrawWhenLastMovementWinsTheGame() {
        Field field = new Field(3);

        field.move(new Move(2, 2), "X");
        field.move(new Move(2, 1), "O");
        field.move(new Move(1, 0), "A");

        field.move(new Move(0, 0), "X");
        field.move(new Move(0, 2), "O");
        field.move(new Move(1, 1), "A");

        field.move(new Move(2, 0), "X");
        field.move(new Move(0, 1), "O");
        field.move(new Move(1, 2), "A");

        assertCheckWinner(field);
    }

    @Test
    /*
     * _a_|_o_|_a_
     * _o_|___|_o_
     *  x | x | x
     */
    void shouldReturnWinnerWhenPlayerFillsAnEntireRow_bottom() {
        Field field = new Field(3);

        field.move(new Move(2, 2), "X");
        field.move(new Move(0, 1), "O");
        field.move(new Move(0, 0), "A");

        field.move(new Move(2, 1), "X");
        field.move(new Move(1, 0), "O");
        field.move(new Move(0, 2), "A");

        field.move(new Move(2, 0), "X");

        assertCheckWinner(field);
    }

    @Test
    /*
     * _x_|___|___
     * ___|_x_|___
     *    |   | x
     */
    void shouldReturnWinnerWhenPlayerFillsADiagonal() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");
        field.move(new Move(1, 1), "X");
        field.move(new Move(2, 2), "X");

        assertCheckWinner(field);
    }

    @Test
    /*
     * ___|___|_x_
     * ___|_x_|___
     *  x |   |
     */
    void shouldReturnWinnerWhenPlayerFillsAnInverseDiagonal() {
        Field field = new Field(3);

        field.move(new Move(0, 2), "X");
        field.move(new Move(1, 1), "X");
        field.move(new Move(2, 0), "X");

        assertCheckWinner(field);
    }

    @Test
    /*
     * ___|_x_|___
     * ___|_x_|___
     *    |   | x
     */
    void shouldReturnNoResult_test1() {
        Field field = new Field(3);

        field.move(new Move(0, 0), "X");
        field.move(new Move(0, 2), "X");
        field.move(new Move(1, 1), "X");

        assertCheckNoResult(field);
    }

    @Test
    /*
     * ___|_x_|___
     * _x_|___|_x_
     *    |   |
     */
    void shouldReturnNoResult_test2() {
        Field field = new Field(3);

        field.move(new Move(0, 1), "X");
        field.move(new Move(1, 1), "X");
        field.move(new Move(2, 2), "X");

        assertCheckNoResult(field);
    }

    private void assertCheckNoResult(Field field) {
        MoveResult gameResult = new GameResultChecker().check(field);
        assertThat(gameResult).isEqualTo(MoveResult.NO_RESULT);
    }

    private void assertCheckWinner(Field field) {
        MoveResult gameResult = new GameResultChecker().check(field);
        assertThat(gameResult).isEqualTo(MoveResult.WINNER);
    }

    private void assertCheckDraw(Field field) {
        MoveResult gameResult = new GameResultChecker().check(field);
        assertThat(gameResult).isEqualTo(MoveResult.DRAW);
    }
}