package chess.pieces;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Position;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    
    public Pawn(Color color, Position position) {
        super(color, PieceType.PAWN, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = (color == Color.WHITE) ? 1 : -1;
        int startRow = (color == Color.WHITE) ? 1 : 6;
        
        // Forward move
        Position oneStep = new Position(position.getRow() + direction, position.getCol());
        if (oneStep.isValid() && board.getPieceAt(oneStep) == null) {
            moves.add(oneStep);
            
            // Two-step from start
            if (position.getRow() == startRow) {
                Position twoStep = new Position(position.getRow() + 2 * direction, position.getCol());
                if (board.getPieceAt(twoStep) == null) {
                    moves.add(twoStep);
                }
            }
        }
        
        // Diagonal captures
        int[] captureOffsets = {-1, 1};
        for (int offset : captureOffsets) {
            Position capturePos = new Position(position.getRow() + direction, position.getCol() + offset);
            if (capturePos.isValid()) {
                Piece pieceAtPos = board.getPieceAt(capturePos);
                if (pieceAtPos != null && pieceAtPos.getColor() != this.color) {
                    moves.add(capturePos);
                }
            }
        }
        
        return moves;
    }
}
