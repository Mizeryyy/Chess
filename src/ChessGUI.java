import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI extends JFrame {
    private JPanel chessBoard;
    private JLabel[][] squares;
    private ChessBoard boardLogic;
    private ChessPiece selectedPiece;
    private int startX, startY;
    private ChessPiece[][] previousBoardState; // To store the previous board state

    public ChessGUI() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize chessboard
        chessBoard = new JPanel(new GridLayout(8, 8));
        getContentPane().add(chessBoard);

        // Create squares array
        squares = new JLabel[8][8];

        // Create ChessBoard instance
        boardLogic = new ChessBoard();

        // Populate chessboard
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel square = new JLabel();
                square.setOpaque(true);
                square.setBackground((i + j) % 2 == 0 ? Color.GRAY : Color.DARK_GRAY);
                chessBoard.add(square);
                squares[i][j] = square;
            }
        }

        // Set piece icons on the board
        setPieceIcons();

        // Add MouseListener to each piece label
        addPieceListeners();

        setVisible(true);

    }

    private void setPieceIcons() {
        // Set piece icons on the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = boardLogic.getPieceAt(i, j);
                if (piece != null) {
                    String fileName = "assets/" + piece.getClass().getSimpleName().toLowerCase() + "-"
                            + piece.getColor().substring(0, 1).toLowerCase() + ".png";
                    int width = 75; // Adjust as needed
                    int height = 75; // Adjust as needed
                    ImageIcon icon = loadImage(fileName, width, height);
                    if (icon != null) {
                        squares[i][j].setIcon(icon);
                    }
                }
            }
        }
    }

    private ImageIcon loadImage(String fileName, int width, int height) {
        // Load image from file
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private void addPieceListeners() {
        // Add MouseListener to each piece label
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel square = squares[i][j];
                final int finalI = i; // Create a final variable to capture the current value of i
                final int finalJ = j;
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Check if it's the current player's turn
                        if (boardLogic.getCurrentPlayer().equals(selectedPiece != null ? selectedPiece.getColor() : boardLogic.getCurrentPlayer())) {
                            // Get the chess piece associated with this square
                            ChessPiece piece = boardLogic.getPieceAt(finalI, finalJ);
                            if (piece != null) {
                                // If a piece is selected, attempt to move it
                                if (selectedPiece != null) {
                                    // Check if the move is valid
                                    if (selectedPiece.isValidMove(boardLogic.getBoard(), startX, startY, finalI, finalJ)) {
                                        // Create a copy of the board state before the move
                                        previousBoardState = copyBoardState(boardLogic.getBoard());
                                        
                                        // Move the piece on the GUI
                                        squares[finalI][finalJ].setIcon(squares[startX][startY].getIcon());
                                        squares[startX][startY].setIcon(null);
    
                                        // Update the board logic to reflect the move
                                        boardLogic.getBoard()[finalI][finalJ] = selectedPiece;
                                        boardLogic.getBoard()[startX][startY] = null;
    
                                        // Check for check
                                        if (boardLogic.isCheck(boardLogic.getBoard(), boardLogic.getCurrentPlayer())) {
                                            System.out.println("Check!");
                                            selectedPiece = null;
                                            restorePreviousBoardState(); // Restore the previous board state
                                            return; // Do not proceed with the move if in check
                                        }
    
                                        // Reset selectedPiece
                                        selectedPiece = null;
    
                                        // Switch player turn
                                        boardLogic.switchPlayer();
                                    } else {
                                        System.out.println("Invalid move. Try again.");
                                    }
                                } else {
                                    // Select the piece for movement
                                    selectedPiece = piece;
                                    startX = finalI;
                                    startY = finalJ;
                                    System.out.println("Selected piece: " + selectedPiece.getClass().getSimpleName() + " at ("
                                            + finalI + ", " + finalJ + ")");
                                }
                            } else {
                                // If no piece is present, and a piece is selected, attempt to move the selected piece
                                if (selectedPiece != null) {
                                    // Check if the move is valid
                                    if (selectedPiece.isValidMove(boardLogic.getBoard(), startX, startY, finalI, finalJ)) {
                                        // Create a copy of the board state before the move
                                        previousBoardState = copyBoardState(boardLogic.getBoard());
                                        
                                        // Move the piece on the GUI
                                        squares[finalI][finalJ].setIcon(squares[startX][startY].getIcon());
                                        squares[startX][startY].setIcon(null);
    
                                        // Update the board logic to reflect the move
                                        boardLogic.getBoard()[finalI][finalJ] = selectedPiece;
                                        boardLogic.getBoard()[startX][startY] = null;
    
                                        // Check for check
                                        if (boardLogic.isCheck(boardLogic.getBoard(), boardLogic.getCurrentPlayer())) {
                                            System.out.println("Check!");
                                            selectedPiece = null;
                                            restorePreviousBoardState(); // Restore the previous board state
                                            return; // Do not proceed with the move if in check
                                        }
    
                                        // Reset selectedPiece
                                        selectedPiece = null;
    
                                        // Switch player turn
                                        boardLogic.switchPlayer();
                                    } else {
                                        System.out.println("Invalid move. Try again.");
                                    }
                                } else {
                                    System.out.println("No piece selected.");
                                }
                            }
                        } else {
                            // Reset selectedPiece if it's not the player's turn
                            selectedPiece = null;
                            System.out.println("It's not your turn.");
                        }
                    }
                });
            }
        }
    }
    
    // Method to create a copy of the board state
    private ChessPiece[][] copyBoardState(ChessPiece[][] board) {
        ChessPiece[][] copy = new ChessPiece[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    // Method to restore the previous board state
// Method to restore the previous board state
// Method to restore the previous board state
private void restorePreviousBoardState() {
    for (int i = 0; i < previousBoardState.length; i++) {
        for (int j = 0; j < previousBoardState[0].length; j++) {
            boardLogic.getBoard()[i][j] = previousBoardState[i][j];
            if (previousBoardState[i][j] == null && boardLogic.getPieceAt(i, j) == null) {
                // Set the icon of the square to null for the square where the attempted move was made
                squares[i][j].setIcon(null);
            } else if (previousBoardState[i][j] != null) {
                // If there was a piece in the previous state, update its position on the GUI
                ChessPiece piece = previousBoardState[i][j];
                String fileName = "assets/" + piece.getClass().getSimpleName().toLowerCase() + "-"
                        + piece.getColor().substring(0, 1).toLowerCase() + ".png";
                int width = 75; // Adjust as needed
                int height = 75; // Adjust as needed
                ImageIcon icon = loadImage(fileName, width, height);
                if (icon != null) {
                    squares[i][j].setIcon(icon);
                }
            }
        }
    }
}

private boolean isCheckMate() {
    // Get the current player's color
    String currentPlayerColor = boardLogic.getCurrentPlayer();

    // Check if the king is in check
    if (!boardLogic.isCheck(boardLogic.getBoard(), currentPlayerColor)) {
        return false;
    }

    // Iterate through all pieces of the current player
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            ChessPiece piece = boardLogic.getPieceAt(i, j);
            if (piece != null && piece.getColor().equals(currentPlayerColor)) {
                // Check if any legal move can get the king out of check
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        if (piece.isValidMove(boardLogic.getBoard(), i, j, x, y)) {
                            // Simulate the move and check if the king is still in check
                            ChessPiece[][] tempBoard = copyBoard(boardLogic.getBoard());
                            tempBoard[x][y] = tempBoard[i][j];
                            tempBoard[i][j] = null;
                            if (!boardLogic.isCheck(tempBoard, currentPlayerColor)) {
                                // If the king is not in check, return false (not checkmate)
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }

    // If no legal move can get the king out of check, it's checkmate
    return true;
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
