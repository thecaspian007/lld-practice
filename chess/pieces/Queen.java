package chess.pieces;

import chess.enums.Color;
import chess.enums.PieceType;
import chess.model.Board;
import chess.model.Piece;
import chess.model.Position;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    
    public Queen(Color color, Position position) {
        super(color, PieceType.QUEEN, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        // Queen = Rook + Bishop (all 8 directions)
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}, {1,1}, {1,-1}, {-1,1}, {-1,-1}};
        
        for (int[] dir : directions) {
            moves.addAll(getMovesInDirection(board, dir[0], dir[1]));
        }
        return moves;
    }
}
