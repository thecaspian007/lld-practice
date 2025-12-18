package chess.pieces;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Position;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    public Knight(Color color, Position position) {
        super(color, PieceType.KNIGHT, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        // L-shaped moves
        int[][] offsets = {{2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,2}, {1,-2}, {-1,2}, {-1,-2}};
        
        for (int[] offset : offsets) {
            Position newPos = new Position(position.getRow() + offset[0], position.getCol() + offset[1]);
            if (newPos.isValid()) {
                Piece pieceAtPos = board.getPieceAt(newPos);
                if (pieceAtPos == null || pieceAtPos.getColor() != this.color) {
                    moves.add(newPos);
                }
            }
        }
        return moves;
    }
}
