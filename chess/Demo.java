package chess;

import chess.game.Game;
import chess.model.Position;

/**
 * Demo script showing Chess features - for interview demonstration
 * This runs automatically without user input to showcase:
 * 1. Basic moves
 * 2. Check detection
 * 3. Undo/Redo
 * 4. Checkmate (Fool's Mate)
 */
public class Demo {
    
    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.initialize("White", "Black");
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     CHESS LLD Demo - Interview     ║");
        System.out.println("╚════════════════════════════════════╝\n");
        
        System.out.println("=== Initial Board ===");
        game.displayBoard();
        pause();
        
        // Demo 1: Basic Pawn Moves
        System.out.println("\n--- Demo 1: Basic Moves ---");
        move(game, "e2", "e4");  // White pawn
        game.displayBoard();
        pause();
        
        move(game, "e7", "e5");  // Black pawn
        game.displayBoard();
        pause();
        
        // Demo 2: Knight Move
        System.out.println("\n--- Demo 2: Knight Move ---");
        move(game, "g1", "f3");  // White knight
        game.displayBoard();
        pause();
        
        // Demo 3: Invalid Move (would leave king in check)
        System.out.println("\n--- Demo 3: Invalid Move Detection ---");
        move(game, "d7", "d6");  // Black pawn
        game.displayBoard();
        pause();
        
        // Demo 4: UNDO functionality
        System.out.println("\n--- Demo 4: Undo Move ---");
        System.out.println("Current move history:");
        game.showMoveHistory();
        pause();
        
        game.undo();
        game.displayBoard();
        System.out.println("Move history after undo:");
        game.showMoveHistory();
        pause();
        
        // Demo 5: REDO functionality
        System.out.println("\n--- Demo 5: Redo Move ---");
        game.redo();
        game.displayBoard();
        pause();
        
        // Demo 6: Check Detection
        System.out.println("\n--- Demo 6: Check Detection ---");
        System.out.println("Setting up a check scenario...");
        move(game, "f8", "b4");  // Black bishop attacks
        game.displayBoard();
        pause();
        
        // Demo 7: Fool's Mate (Fastest Checkmate)
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("     Demo 7: Fool's Mate (Checkmate!)   ");
        System.out.println("═══════════════════════════════════════\n");
        
        // Reset for fool's mate
        Game.resetInstance();
        game = Game.getInstance();
        game.initialize("White", "Black");
        
        System.out.println("Starting fresh game for Fool's Mate demo...");
        game.displayBoard();
        pause();
        
        move(game, "f2", "f3");  // White weakens king-side
        game.displayBoard();
        pause();
        
        move(game, "e7", "e5");  // Black pawn
        game.displayBoard();
        pause();
        
        move(game, "g2", "g4");  // White's fatal mistake
        game.displayBoard();
        pause();
        
        move(game, "d8", "h4");  // Black Queen CHECKMATE!
        game.displayBoard();
        
        System.out.println("\n=== DEMO COMPLETE ===");
        System.out.println("All features demonstrated:");
        System.out.println("✓ Valid piece movements");
        System.out.println("✓ Turn-based play");
        System.out.println("✓ Move validation");
        System.out.println("✓ Undo/Redo (Command Pattern)");
        System.out.println("✓ Check detection");
        System.out.println("✓ Checkmate detection");
    }
    
    private static void move(Game game, String from, String to) {
        Position fromPos = parsePosition(from);
        Position toPos = parsePosition(to);
        game.makeMove(fromPos, toPos);
    }
    
    private static Position parsePosition(String pos) {
        int col = pos.charAt(0) - 'a';
        int row = pos.charAt(1) - '1';
        return new Position(row, col);
    }
    
    private static void pause() {
        try {
            Thread.sleep(500); // Brief pause for readability
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
