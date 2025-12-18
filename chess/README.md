# Chess LLD - Interview Solution

## 📋 Interview Flow (30-40 mins)

### Phase 1: Requirements (2-3 mins)
- Standard 8x8 board, 2 players
- All 6 piece types with valid moves
- Check/Checkmate detection
- Undo/Redo functionality

### Phase 2: Design (5 mins)
```
Game (Singleton) → Board → Cell[8][8] → Piece (Abstract)
                                          ├── King, Queen, Rook
                                          ├── Bishop, Knight, Pawn
           │
           ├── Player (White/Black)
           └── MoveCommand (Command Pattern)
```

### Phase 3: Implementation (25-30 mins)
Working code with demonstrations.

---

## 🏗️ Design Patterns Used

| Pattern | Class | Purpose |
|---------|-------|---------|
| **Singleton** | `Game` | Single game instance |
| **Command** | `MoveCommand` | Encapsulates moves for Undo/Redo |
| **Polymorphism** | `Piece` subclasses | Each piece has its own movement logic |

---

## 📁 Project Structure

```
chess/
├── Main.java                 # Entry point (interactive console)
├── enums/
│   ├── Color.java           # WHITE, BLACK
│   └── PieceType.java       # KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
├── model/
│   ├── Position.java        # Board coordinates (row, col)
│   ├── Piece.java           # Abstract base class
│   ├── Board.java           # 8x8 grid with attack detection
│   └── Player.java          # Name + Color
├── pieces/
│   ├── King.java            # 1-square all directions
│   ├── Queen.java           # Rook + Bishop combined
│   ├── Rook.java            # Horizontal + Vertical
│   ├── Bishop.java          # Diagonal
│   ├── Knight.java          # L-shape (can jump)
│   └── Pawn.java            # Forward + diagonal capture
├── command/
│   └── MoveCommand.java     # Command pattern for Undo/Redo
└── game/
    └── Game.java            # Singleton game controller
```

---

## 🎮 How to Run

```bash
# Compile
javac chess/Main.java chess/**/*.java

# Run
java chess.Main
```

### Commands
```
e2 e4    - Move piece from e2 to e4
undo     - Undo last move
redo     - Redo undone move
history  - Show move history
board    - Redisplay board
quit     - Exit game
```

---

## ✅ Key Features

1. **Valid Moves**: Each piece implements `getPossibleMoves(board)`
2. **Check Detection**: `Board.isInCheck(color)` scans all opponent moves
3. **Checkmate**: No legal moves while in check
4. **Stalemate**: No legal moves while NOT in check
5. **Undo/Redo**: MoveCommand stores state for reversal

---

## 💡 Interview Tips

1. **Start with interfaces/abstracts** - Show good OOP
2. **Explain trade-offs** - "I'm keeping it simple for time"
3. **Be incremental** - Get basic movement working first
4. **Test as you code** - Show it works at each step
