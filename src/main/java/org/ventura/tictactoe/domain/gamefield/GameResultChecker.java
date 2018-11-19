package org.ventura.tictactoe.domain.gamefield;

import org.ventura.tictactoe.domain.move.MoveResult;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class GameResultChecker {

    private Field field;

    public GameResultChecker() {
    }

    public MoveResult check(Field field) {
        this.field = field;

        if (hasWinner(field.lastExecutedMove())) {
            return MoveResult.WINNER;
        }

        if (field.isCompleted()) {
            return MoveResult.DRAW;
        }

        return MoveResult.NO_RESULT;
    }

    private boolean hasWinner(FieldPosition lastPositionChanged) {
        String playerCharacter = lastPositionChanged.playerCharacter();

        List<FieldPosition> positions = getPositionsFilledBy(playerCharacter);

        return isWinnerByRow(lastPositionChanged.row(), positions)
                || isWinnerByColumn(lastPositionChanged.column(), positions)
                || isWinnerByDiagonal(positions)
                || isWinnerByInverseDiagonal(positions);
    }

    private List<FieldPosition> getPositionsFilledBy(String playerCharacter) {
        return field.positions()
                .stream()
                .filter(f -> f.playerCharacter().equals(playerCharacter))
                .collect(toList());
    }

    private boolean isWinnerByRow(int lastRowMove, List<FieldPosition> filledPositions) {
        return filledPositions.stream()
                .filter(f -> f.row() == lastRowMove)
                .count() == field.size();
    }

    private boolean isWinnerByColumn(int lastColumnMove, List<FieldPosition> filledPositions) {
        return filledPositions.stream()
                .filter(f -> f.column() == lastColumnMove)
                .count() == field.size();
    }

    private boolean isWinnerByDiagonal(List<FieldPosition> filledPositions) {
        return filledPositions.stream()
                .filter(f -> f.row() == f.column())
                .count() == field.size();
    }

    private boolean isWinnerByInverseDiagonal(List<FieldPosition> filledPositions) {
        return filledPositions.stream()
                .filter(f -> f.row() + f.column() == (field.size() - 1))
                .count() == field.size();
    }

}
