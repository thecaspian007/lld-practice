package chess.model;

import chess.enums.Color;
import chess.enums.PieceType;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private Piece[][] grid;
    private Map<Color, Position> kingPositions;
    
    public Board() {
        grid = new Piece[8][8];
        kingPositions = new HashMap<>();
    }
    
    public Piece getPieceAt(Position pos) {
        if (!pos.isValid()) return null;
        return grid[pos.getRow()][pos.getCol()];
    }
    
    public void setPieceAt(Position pos, Piece piece) {
        grid[pos.getRow()][pos.getCol()] = piece;
        if (piece != null) {
            piece.setPosition(pos);
            if (piece.getType() == PieceType.KING) {
                kingPositions.put(piece.getColor(), pos);
            }
        }
    }
    
    public void removePiece(Position pos) {
        grid[pos.getRow()][pos.getCol()] = null;
    }
    
    public Position getKingPosition(Color color) {
        return kingPositions.get(color);
    }
    
    // Check if a position is under attack by opponent
    public boolean isUnderAttack(Position pos, Color attackerColor) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = grid[r][c];
                if (piece != null && piece.getColor() == attackerColor) {
                    for (Position move : piece.getPossibleMoves(this)) {
                        if (move.equals(pos)) return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isInCheck(Color color) {
        Position kingPos = kingPositions.get(color);
        return isUnderAttack(kingPos, color.opposite());
    }
    
    public void display() {
        System.out.println("\n    a   b   c   d   e   f   g   h");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for (int r = 7; r >= 0; r--) {
            System.out.print((r + 1) + " |");
            for (int c = 0; c < 8; c++) {
                Piece piece = grid[r][c];
                String symbol = piece == null ? "  " : piece.toString();
                System.out.print(symbol + " |");
            }
            System.out.println(" " + (r + 1));
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }
        System.out.println("    a   b   c   d   e   f   g   h\n");
    }
}
