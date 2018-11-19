package org.ventura.tictactoe.domain.gamefield;

import org.ventura.tictactoe.domain.move.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class Field implements FieldState {

    private int size;
    private FieldPosition lastMove;
    private Map<String, FieldPosition> positions;

    public Field(int size) {
        this.size = size;
        this.positions = new HashMap<>();
        validateSizeField(size);
    }

    private void validateSizeField(int size) {
        if (size < 3 || size > 10) {
            throw new FieldSizeOutsideOfAllowedRangeException();
        }
    }

    public int size() {
        return this.size;
    }

    public void move(Move move, String playerCharacter) {
        String key = rowColumnPositionsKey(move.x(), move.y());
        validateFieldPosition(move, key);

        FieldPosition fieldPosition = new FieldPosition(move.x(), move.y(), playerCharacter);
        positions.put(key, fieldPosition);
        lastMove = fieldPosition;
    }

    private void validateFieldPosition(Move move, String key) {
        if (positions.containsKey(key)) {
            throw new PositionAlreadyFilledException();
        }

        if(isPositionOutsideOfRange(move)) {
            throw new PositionOutOfRangeException();
        }
    }

    private boolean isPositionOutsideOfRange(Move move) {
        return move.x() >= size || move.y() >= size;
    }

    public Optional<FieldPosition> position(int row, int column) {
        String positionKey = rowColumnPositionsKey(row, column);
        return Optional.ofNullable(positions.get(positionKey));
    }

    private String rowColumnPositionsKey(int row, int column) {
        return row + "_" + column;
    }

    public FieldPosition lastExecutedMove() {
        return lastMove;
    }

    public List<FieldPosition> positions() {
        return new CopyOnWriteArrayList<>(positions.values());
    }

    public boolean isCompleted() {
        return positions().size() == (size * size());
    }
}
