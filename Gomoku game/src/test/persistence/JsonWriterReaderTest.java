package persistence;


import model.ChessBoard;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class JsonWriterReaderTest {
    int size = 10;
    String player1 = "player1";
    String player2 = "player2";
    int chessPiece = 1;


    @Test
    void testWriterInvalidFile() {
        try {
            ChessBoard chessBoard = null;
            try {
                chessBoard = new ChessBoard(size, player1, player2, chessPiece);
            } catch (Exception e) {

            }
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ChessBoard chessBoard = null;
            try {
                chessBoard = new ChessBoard(size, player1, player2, chessPiece);
            } catch (Exception e) {

            }
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyChessBoard.json");
            writer.open();
            writer.write(chessBoard);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyChessBoard.json");
            ChessBoard jsonChess = reader.read();
            assertEquals(10, jsonChess.getSize());

            assertEquals("player1", jsonChess.getPlayer1().getName());
            assertEquals(1, jsonChess.getPlayer1().getPiece());
            assertEquals("player2", jsonChess.getPlayer2().getName());
            assertEquals(2, jsonChess.getPlayer2().getPiece());
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    assertEquals(0, jsonChess.getOnePosition(i, j));
                }
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ChessBoard chessBoard = null;
            try {
                chessBoard = new ChessBoard(size, player1, player2, chessPiece);
            } catch (Exception e) {

            }
            chessBoard.putChessPieceX(3, 1);
            chessBoard.putChessPieceX(1, 3);
            chessBoard.putChessPieceO(2, 3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralChessBoard.json");
            writer.open();
            writer.write(chessBoard);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralChessBoard.json");
            ChessBoard readerBoard = reader.read();
            assertEquals(10, readerBoard.getSize());
            assertEquals("player1", readerBoard.getPlayer1().getName());
            assertEquals(1, readerBoard.getPlayer1().getPiece());
            assertEquals("player2", readerBoard.getPlayer2().getName());
            assertEquals(2, readerBoard.getPlayer2().getPiece());
            assertEquals(2, readerBoard.getOnePosition(2, 0));
            assertEquals(2, readerBoard.getOnePosition(0, 2));
            assertEquals(1, readerBoard.getOnePosition(1, 2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }}

    @Test
    void testParseChessBoardThrowException() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralChessBoard.json");

        assertEquals(reader.parseChessBoard(new JSONObject()), null);

    }

    }