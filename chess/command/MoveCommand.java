package chess.command;

import chess.model.Board;
import chess.model.Piece;
import chess.model.Position;

/**
 * Command Pattern - Encapsulates a move for Undo/Redo functionality
 */
public class MoveCommand {
    private final Position from;
    private final Position to;
    private final Piece movedPiece;
    private final Piece capturedPiece;
    private final boolean wasFirstMove;
    
    public MoveCommand(Position from, Position to, Piece movedPiece, Piece capturedPiece, boolean wasFirstMove) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.wasFirstMove = wasFirstMove;
    }
    
    public void execute(Board board) {
        board.removePiece(from);
        board.setPieceAt(to, movedPiece);
        movedPiece.setMoved(true);
    }
    
    public void undo(Board board) {
        board.removePiece(to);
        board.setPieceAt(from, movedPiece);
        if (wasFirstMove) {
            movedPiece.setMoved(false);
        }
        if (capturedPiece != null) {
            board.setPieceAt(to, capturedPiece);
        }
    }
    
    public Position getFrom() { return from; }
    public Position getTo() { return to; }
    public Piece getMovedPiece() { return movedPiece; }
    public Piece getCapturedPiece() { return capturedPiece; }
    
    @Override
    public String toString() {
        String move = movedPiece + " " + from + " -> " + to;
        if (capturedPiece != null) {
            move += " (captured " + capturedPiece + ")";
        }
        return move;
    }
}
