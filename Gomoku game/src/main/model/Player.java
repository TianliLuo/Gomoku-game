package model;


//represent the information of each player
public class Player {
    String name;
    int piece;

    //MODIFIES: this
    //EFFECTS: initialize the player class
    public Player(String name, int piece) {
        this.name = name;
        this.piece = piece;
    }

    //EFFECTS: return the name
    public String getName() {
        return name;

    }

    //EFFECTS: return the piece
    public int getPiece() {
        return piece;

    }

}

