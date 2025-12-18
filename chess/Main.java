package chess;

import chess.game.Game;
import chess.model.Position;
import java.util.Scanner;

/**
 * Chess Game - LLD Interview Implementation
 * 
 * Features:
 * - 2-player game with all valid piece movements
 * - Check and Checkmate detection
 * - Undo/Redo functionality (Command Pattern)
 * - Singleton Game instance
 * 
 * Design Patterns Used:
 * 1. Singleton - Game class
 * 2. Command - MoveCommand for Undo/Redo
 * 3. Polymorphism - Piece subclasses for movement
 */
public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = Game.getInstance();
        
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║       CHESS - LLD Interview        ║");
        System.out.println("╚════════════════════════════════════╝\n");
        
        game.initialize("White", "Black");
        game.displayBoard();
        
        printHelp();
        
        while (!game.isGameOver()) {
            System.out.print("> Enter command: ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("quit") || input.equals("q")) {
                System.out.println("Thanks for playing!");
                break;
            } else if (input.equals("help") || input.equals("h")) {
                printHelp();
            } else if (input.equals("undo") || input.equals("u")) {
                game.undo();
                game.displayBoard();
            } else if (input.equals("redo") || input.equals("r")) {
                game.redo();
                game.displayBoard();
            } else if (input.equals("history")) {
                game.showMoveHistory();
            } else if (input.equals("board") || input.equals("b")) {
                game.displayBoard();
            } else {
                // Parse move command: e.g., "e2 e4" or "e2e4"
                try {
                    String[] parts = input.replaceAll("\\s+", " ").split(" ");
                    Position from, to;
                    
                    if (parts.length == 2) {
                        from = parsePosition(parts[0]);
                        to = parsePosition(parts[1]);
                    } else if (parts.length == 1 && parts[0].length() == 4) {
                        from = parsePosition(parts[0].substring(0, 2));
                        to = parsePosition(parts[0].substring(2, 4));
                    } else {
                        System.out.println("Invalid format. Use: 'e2 e4' or 'e2e4'");
                        continue;
                    }
                    
                    if (game.makeMove(from, to)) {
                        game.displayBoard();
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Type 'help' for commands.");
                }
            }
        }
        
        if (game.isGameOver()) {
            System.out.println("\n=== GAME OVER ===");
            System.out.println(game.getResult());
        }
        
        scanner.close();
    }
    
    private static Position parsePosition(String pos) {
        if (pos.length() != 2) throw new IllegalArgumentException("Invalid position");
        int col = pos.charAt(0) - 'a';
        int row = pos.charAt(1) - '1';
        return new Position(row, col);
    }
    
    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  e2 e4  - Move piece from e2 to e4");
        System.out.println("  undo   - Undo last move");
        System.out.println("  redo   - Redo undone move");
        System.out.println("  history- Show move history");
        System.out.println("  board  - Redisplay board");
        System.out.println("  help   - Show this help");
        System.out.println("  quit   - Exit game\n");
    }
}
