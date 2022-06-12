package persistence;


import model.ChessBoard;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//I use the structure of JsonReader class of JsonSerialization Demo in this class
// read ChessBoard from JSON data stored in file
public class JsonReader {
    private String source;

    //MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads chessBoard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ChessBoard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChessBoard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //MODIFIES: this
    // EFFECTS: parses Chessboard from JSON object and returns it
    public ChessBoard parseChessBoard(JSONObject jsonObject) {
        try {
            int size = jsonObject.getInt("chessboard size");
            String player1 = jsonObject.getString("player1");
            String player2 = jsonObject.getString("player2");
            JSONArray board = jsonObject.getJSONArray("2D-board");
            int piece1 = jsonObject.getInt("piece1");
            int[][] renderBoard = getArray(board);
            ChessBoard chessBoard = new ChessBoard(size, player1, player2, piece1, renderBoard);
            return chessBoard;
        } catch (Exception w) {
            return null;
        }

    }


    // EFFECTS: return the JSONArray as the type of 2D-Array
    public int[][] getArray(JSONArray board) {
        int colomn = 0;
        int row = 0;
        int size = (int) Math.sqrt(board.length() + 1);
        int[][] renderBoard = new int[size][size];
        for (Object json : board) {
            int chessPiece = (int) json;
            renderBoard[row][colomn] = chessPiece;
            if (colomn == size - 1) {
                colomn = 0;
                row++;
            } else {
                colomn++;
            }
        }
        return renderBoard;

    }


}
