package chess.pieces;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Position;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    
    public King(Color color, Position position) {
        super(color, PieceType.KING, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int[] directions = {-1, 0, 1};
        
        for (int dr : directions) {
            for (int dc : directions) {
                if (dr == 0 && dc == 0) continue;
                
                Position newPos = new Position(position.getRow() + dr, position.getCol() + dc);
                if (newPos.isValid()) {
                    Piece pieceAtPos = board.getPieceAt(newPos);
                    if (pieceAtPos == null || pieceAtPos.getColor() != this.color) {
                        moves.add(newPos);
                    }
                }
            }
        }
        return moves;
    }
}
