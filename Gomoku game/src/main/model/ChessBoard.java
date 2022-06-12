package model;

import exeption.WrongArrayException;
import exeption.WrongPieceException;
import org.json.JSONArray;
import org.json.JSONObject;

//ChessBoard class represents the attribute of the chessboard
public class ChessBoard {
    int size; // the size of the chessboard
    private int[][] board; //represent the chessboard
    Player player1;
    Player player2;
    int piece1;

    //MODIFIES: this
    //EFFECTS: construct the ChessBoard Class if the Chessboard is new
    //if the piece1 is not 1 or 2, throw WrongPieceException
    public ChessBoard(int size, String name1, String name2, int piece1) throws WrongPieceException {
        if ((piece1 != 1) && (piece1 != 2)) {
            throw new WrongPieceException();
        }
        this.size = size;
        initialize();
        this.player1 = new Player(name1, piece1);
        this.piece1 = piece1;
        if (piece1 == 1) {
            this.player2 = new Player(name2, 2);
        } else {
            this.player2 = new Player(name2, 1);
        }
    }

    //MODIFIES: this
    //EFFECTS: construct the ChessBoard Class if we have played this game
    //if the piece1 is not 1 or 2, throw WrongPieceException
    //if the board is not followed the requirements, throw WrongPieceException
    public ChessBoard(int size, String name1, String name2, int piece1, int[][] board) throws
            WrongArrayException, WrongPieceException {
        if (board.length != size) {
            throw new WrongArrayException();
        }

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (board[i][j] != 0 && board[i][j] != 1 && board[i][j] != 2) {
                    throw new WrongArrayException();
                }
            }
        }
        if ((piece1 != 1) && (piece1 != 2)) {
            throw new WrongPieceException();
        }

        this.size = size;
        this.board = board;
        this.player1 = new Player(name1, piece1);
        this.piece1 = piece1;
        if (piece1 == 1) {
            this.player2 = new Player(name2, 2);
        } else {
            this.player2 = new Player(name2, 1);
        }
    }


    //MODIFIES: this
    //EFFECTS: initialize the chessboard field
    public void initialize() {
        int line = 0;
        board = new int[size][size];
        for (int[] aline : board) {
            int colomn = 0;
            for (int b : aline) {
                board[line][colomn] = 0;
                colomn++;
            }
            line++;

        }
    }

    //EFFECTS: recognize if someone win the game on the line
    public Boolean winOnTheLine() {
        int count = 1;
        for (int l = 0; l < board.length; l++) {
            for (int c = 0; c < board.length - 1; c++) {
                if (board[l][c] == board[l][c + 1] && board[l][c] != 0) {
                    count++;
                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return false;
    }

    //EFFECTS: recognize if someone win the game on the colomn
    public Boolean winOnTheColomn() {
        int count = 1;
        for (int c = 0; c < board.length; c++) {
            for (int l = 0; l < board.length - 1; l++) {
                if (board[l][c] == board[l + 1][c] && board[l][c] != 0) {
                    count++;
                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 1;
                }
            }
        }
        return false;
    }

    //EFFECTS: recognize if someone win the game on the left-right diagonal
    public Boolean winLeftRightDiagonal1() {
        for (int l = 0; l < board.length; l++) {
            int i = l;
            int count = 1;
            for (int c = 0; c < board.length - 1; c++) {
                if (i == board.length - 1) {
                    break;
                }
                if (board[i][c] == board[i + 1][c + 1] && board[i][c] != 0) {
                    count++;

                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 1;
                }
                i++;
            }
        }
        return false;
    }

    //EFFECTS: recognize if someone win the game on the left-right diagonal2
    public Boolean winLeftRightDiagonal2() {
        for (int l = 0; l < board.length; l++) {
            int i = l;
            int count = 1;
            for (int c = 0; c < board.length - 1; c++) {
                if (i == board.length - 1) {
                    break;
                }
                if (board[c][i] == board[c + 1][i + 1] && board[c][i] != 0) {
                    count++;

                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 1;
                }
                i++;
            }
        }
        return false;
    }

    //EFFECTS: recognize if someone win the game on the right-left diagonal1
    public Boolean winRightLeftDiagonal1() {
        for (int l = board.length - 1; l >= 0; l--) {
            int i = l;
            int count = 1;
            for (int c = 0; c < board.length - 1; c++) {
                if (i == 0) {
                    break;
                }
                if (board[i][c] == board[i - 1][c + 1] && board[i][c] != 0) {
                    count++;

                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 1;
                }
                i--;
            }
        }
        return false;
    }

    //EFFECTS: recognize if someone win the game on the right-left diagonal2
    public Boolean winRightLeftDiagonal2() {

        for (int c = 0; c < board.length - 1; c++) {
            int i = c;
            int count = 1;
            for (int l = board.length - 1; l > 0; l--) {
                if (i == board.length - 1) {
                    break;
                }
                if (board[l][i] == board[l - 1][i + 1] && board[l][i] != 0) {
                    count++;

                    if (count == 5) {
                        return true;
                    }
                } else {
                    count = 1;
                }
                i++;
            }
        }
        return false;
    }

    //EFFECTS: recognize if someone win the game
    public Boolean ifWin() {
        return winOnTheLine()
                || winOnTheColomn()
                || winLeftRightDiagonal1()
                || winLeftRightDiagonal2()
                || winRightLeftDiagonal1()
                || winRightLeftDiagonal2();
    }

    //MODIFIES: this
    //EFFECTS: put the chessPiece o on the board
    //throw ArrayIndexOutOfBoundsException if the Index is out of bounds.
    public void putChessPieceO(int a, int b) throws ArrayIndexOutOfBoundsException {
        if (a > getSize() || b > getSize() || b < 1 || a < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        board[a - 1][b - 1] = 1;
    }

    //MODIFIES: this
    //EFFECTS: put the chesspiece x on the board
    //throw ArrayIndexOutOfBoundsException if the Index is out of bounds.
    public void putChessPieceX(int a, int b) throws ArrayIndexOutOfBoundsException {
        if (a > getSize() || b > getSize() || b < 1 || a < 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        board[a - 1][b - 1] = 2;

    }

    //EFFECTS: recognize if the chessboard is full
    public Boolean ifFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;

    }

    //EFFECTS: in order to return chessboard
    public int[][] getBoard() {
        return board;
    }


    //EFFECTS: return size
    public int getSize() {
        return size;
    }

    //EFFECTS: return the exact position of the chessBoard
    //throw ArrayIndexOutOfBoundsException if the Index is out of bounds.
    public int getOnePosition(int x, int y) throws ArrayIndexOutOfBoundsException {
        if (x > getSize() - 1 || y > getSize() - 1 || x < 0 || y < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return board[x][y];

    }

    //EFFECTS: return the player1
    public Player getPlayer1() {
        return player1;
    }

    //EFFECTS: return the player1
    public Player getPlayer2() {
        return player2;
    }

    //EFFECTS: return the chessBoard information as JsonObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player1", player1.name);
        json.put("player2", player2.name);
        json.put("chessboard size", size);
        json.put("2D-board", boardToJson());
        json.put("piece1", piece1);
        return json;
    }

    // EFFECTS: returns board as a JSON array
    public JSONArray boardToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                jsonArray.put(board[i][j]);
            }
        }
        return jsonArray;

    }

}



