package ui;

//A class represents the information of chessPiece
public class PiecePosition {
    private int positionX; // the x coordinate
    private int positionY; // the y coordinate
    private int black; // 1 represent the black piece, 2 represent the white piece

    //MODIFIES:this
    //EFFECTS:construct the piecePosition class
    public PiecePosition(int x, int y, int a) {
        this.positionX = x;
        this.positionY = y;
        this.black = a;
    }

    //EFFECTS:get the x-coordinate of the chess
    public int getPositionX() {
        return positionX;
    }

    //EFFECTS:get the y-coordinate of the chess
    public int getPositionY() {
        return positionY;

    }

    //EFFECTS:get the information of black or white
    public int getBlack() {
        return black;
    }
}
