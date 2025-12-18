package chess.enums;

public enum PieceType {
    KING("K"), QUEEN("Q"), ROOK("R"), BISHOP("B"), KNIGHT("N"), PAWN("P");
    
    private final String symbol;
    
    PieceType(String symbol) {
        this.symbol = symbol;
    }
    
    public String getSymbol() {
        return symbol;
    }
}
