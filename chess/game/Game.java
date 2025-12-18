package chess.game;

import chess.command.MoveCommand;
import chess.enums.Color;
import chess.model.*;
import chess.pieces.*;
import java.util.*;

/**
 * Singleton Pattern - Single game instance
 */
public class Game {
    private static Game instance;
    
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentPlayer;
    private boolean gameOver;
    private String result;
    
    // Command Pattern - for Undo/Redo
    private Stack<MoveCommand> moveHistory;
    private Stack<MoveCommand> redoStack;
    
    private Game() {
        moveHistory = new Stack<>();
        redoStack = new Stack<>();
    }
    
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
    
    public static void resetInstance() {
        instance = null;
    }
    
    public void initialize(String whiteName, String blackName) {
        board = new Board();
        whitePlayer = new Player(whiteName, Color.WHITE);
        blackPlayer = new Player(blackName, Color.BLACK);
        currentPlayer = whitePlayer;
        gameOver = false;
        result = null;
        moveHistory.clear();
        redoStack.clear();
        setupBoard();
    }
    
    private void setupBoard() {
        // Place pawns
        for (int c = 0; c < 8; c++) {
            board.setPieceAt(new Position(1, c), new Pawn(Color.WHITE, new Position(1, c)));
            board.setPieceAt(new Position(6, c), new Pawn(Color.BLACK, new Position(6, c)));
        }
        
        // Place other pieces
        placePieces(0, Color.WHITE);
        placePieces(7, Color.BLACK);
    }
    
    private void placePieces(int row, Color color) {
        board.setPieceAt(new Position(row, 0), new Rook(color, new Position(row, 0)));
        board.setPieceAt(new Position(row, 1), new Knight(color, new Position(row, 1)));
        board.setPieceAt(new Position(row, 2), new Bishop(color, new Position(row, 2)));
        board.setPieceAt(new Position(row, 3), new Queen(color, new Position(row, 3)));
        board.setPieceAt(new Position(row, 4), new King(color, new Position(row, 4)));
        board.setPieceAt(new Position(row, 5), new Bishop(color, new Position(row, 5)));
        board.setPieceAt(new Position(row, 6), new Knight(color, new Position(row, 6)));
        board.setPieceAt(new Position(row, 7), new Rook(color, new Position(row, 7)));
    }
    
    public boolean makeMove(Position from, Position to) {
        if (gameOver) {
            System.out.println("Game is over! " + result);
            return false;
        }
        
        Piece piece = board.getPieceAt(from);
        if (piece == null) {
            System.out.println("No piece at " + from);
            return false;
        }
        
        if (piece.getColor() != currentPlayer.getColor()) {
            System.out.println("Not your piece! It's " + currentPlayer.getName() + "'s turn.");
            return false;
        }
        
        // Check if move is valid for this piece
        List<Position> possibleMoves = piece.getPossibleMoves(board);
        if (!possibleMoves.contains(to)) {
            System.out.println("Invalid move for " + piece.getType());
            return false;
        }
        
        // Check if move would leave king in check
        Piece capturedPiece = board.getPieceAt(to);
        boolean wasFirstMove = !piece.hasMoved();
        
        // Execute move temporarily
        MoveCommand move = new MoveCommand(from, to, piece, capturedPiece, wasFirstMove);
        move.execute(board);
        
        // Verify king is not in check after move
        if (board.isInCheck(currentPlayer.getColor())) {
            move.undo(board);
            System.out.println("Invalid move! Would leave your King in check.");
            return false;
        }
        
        // Move is valid - record it
        moveHistory.push(move);
        redoStack.clear(); // Clear redo stack on new move
        
        System.out.println(currentPlayer.getName() + " played: " + move);
        
        // Check opponent's state
        Color opponentColor = currentPlayer.getColor().opposite();
        if (board.isInCheck(opponentColor)) {
            if (isCheckmate(opponentColor)) {
                gameOver = true;
                result = currentPlayer.getName() + " wins by CHECKMATE!";
                System.out.println("CHECKMATE! " + result);
            } else {
                System.out.println("CHECK!");
            }
        } else if (isStalemate(opponentColor)) {
            gameOver = true;
            result = "STALEMATE - Draw!";
            System.out.println(result);
        }
        
        // Switch turns
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
        return true;
    }
    
    private boolean hasAnyLegalMove(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getPieceAt(new Position(r, c));
                if (piece != null && piece.getColor() == color) {
                    for (Position move : piece.getPossibleMoves(board)) {
                        // Try the move
                        Position from = piece.getPosition();
                        Piece captured = board.getPieceAt(move);
                        boolean wasFirst = !piece.hasMoved();
                        
                        MoveCommand cmd = new MoveCommand(from, move, piece, captured, wasFirst);
                        cmd.execute(board);
                        
                        boolean stillInCheck = board.isInCheck(color);
                        cmd.undo(board);
                        
                        if (!stillInCheck) return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean isCheckmate(Color color) {
        return board.isInCheck(color) && !hasAnyLegalMove(color);
    }
    
    private boolean isStalemate(Color color) {
        return !board.isInCheck(color) && !hasAnyLegalMove(color);
    }
    
    // UNDO functionality
    public boolean undo() {
        if (moveHistory.isEmpty()) {
            System.out.println("No moves to undo!");
            return false;
        }
        
        MoveCommand lastMove = moveHistory.pop();
        lastMove.undo(board);
        redoStack.push(lastMove);
        
        // Switch back turn
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
        gameOver = false;
        result = null;
        
        System.out.println("Undid: " + lastMove);
        return true;
    }
    
    // REDO functionality
    public boolean redo() {
        if (redoStack.isEmpty()) {
            System.out.println("No moves to redo!");
            return false;
        }
        
        MoveCommand move = redoStack.pop();
        move.execute(board);
        moveHistory.push(move);
        
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
        System.out.println("Redid: " + move);
        return true;
    }
    
    public void displayBoard() {
        board.display();
        if (!gameOver) {
            System.out.println("Current turn: " + currentPlayer);
            if (board.isInCheck(currentPlayer.getColor())) {
                System.out.println("*** YOUR KING IS IN CHECK! ***");
            }
        }
    }
    
    public void showMoveHistory() {
        System.out.println("\n=== Move History ===");
        int moveNum = 1;
        for (MoveCommand move : moveHistory) {
            System.out.println(moveNum++ + ". " + move);
        }
        if (moveHistory.isEmpty()) {
            System.out.println("No moves yet.");
        }
    }
    
    // Getters
    public Board getBoard() { return board; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public boolean isGameOver() { return gameOver; }
    public String getResult() { return result; }
}
