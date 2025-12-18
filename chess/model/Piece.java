package chess.model;

import chess.enums.Color;
import chess.enums.PieceType;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected Color color;
    protected PieceType type;
    protected Position position;
    protected boolean hasMoved = false;
    
    public Piece(Color color, PieceType type, Position position) {
        this.color = color;
        this.type = type;
        this.position = position;
    }
    
    public Color getColor() { return color; }
    public PieceType getType() { return type; }
    public Position getPosition() { return position; }
    public boolean hasMoved() { return hasMoved; }
    
    public void setPosition(Position position) { this.position = position; }
    public void setMoved(boolean moved) { this.hasMoved = moved; }
    
    // Template method pattern - each piece implements its own movement logic
    public abstract List<Position> getPossibleMoves(Board board);
    
    // Helper for sliding pieces (Rook, Bishop, Queen)
    protected List<Position> getMovesInDirection(Board board, int rowDir, int colDir) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow() + rowDir;
        int col = position.getCol() + colDir;
        
        while (row >= 0 && row < 8 && col >= 0 && col < 8) {
            Position newPos = new Position(row, col);
            Piece pieceAtPos = board.getPieceAt(newPos);
            
            if (pieceAtPos == null) {
                moves.add(newPos);
            } else {
                if (pieceAtPos.getColor() != this.color) {
                    moves.add(newPos); // Can capture
                }
                break; // Blocked
            }
            row += rowDir;
            col += colDir;
        }
        return moves;
    }
    
    @Override
    public String toString() {
        return (color == Color.WHITE ? "W" : "B") + type.getSymbol();
    }
}
