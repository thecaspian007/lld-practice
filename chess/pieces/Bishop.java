package chess.pieces;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Position;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    
    public Bishop(Color color, Position position) {
        super(color, PieceType.BISHOP, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        // Diagonals
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        
        for (int[] dir : directions) {
            moves.addAll(getMovesInDirection(board, dir[0], dir[1]));
        }
        return moves;
    }
}
