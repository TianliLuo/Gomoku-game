package model;

import exeption.WrongArrayException;
import exeption.WrongPieceException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChessBoardTest {
    private ChessBoard chessBoard1;
    private ChessBoard chessBoard2;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void initialize() {
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 1);
            this.chessBoard2 = new ChessBoard(10, "player1", "player2", 2);
            this.player1 = new Player("player1", 1);
            this.player2 = new Player("player2", 2);
        } catch (Exception e) {

        }
    }

    @Test
    public void testChessBoardConstructor1throwException() {
        try {
            this.chessBoard2 = new ChessBoard(10, "player1", "player2", 4);
            fail("should not run here");
        } catch (WrongPieceException w) {
            //expected here
        }

        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 5);
            fail("should not run here");
        } catch (WrongPieceException w) {
            //expected here
        }

    }

    @Test
    public void testChessBoardConstructor1NoException() {
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 2);
        } catch (WrongPieceException w) {
            fail("should not run here");
        }
        assertEquals(chessBoard1.getSize(),10);
        assertEquals(chessBoard1.getPlayer2().getPiece(),1);
        assertEquals(chessBoard1.getPlayer1().getPiece(),2);
        assertEquals(chessBoard1.getPlayer1().getName(),"player1");
        assertEquals(chessBoard1.getPlayer2().getName(),"player2");
        int[][] testArray = chessBoard1.getBoard();
        for(int i = 0;i<testArray.length;i++){
            for(int j = 0;j<testArray.length;j++){
                assertEquals(testArray[i][j],0);
            }
        }
    }

    @Test
    public void testChessBoardConstructor2NoException() {
        int[][] testArray = new int[10][10];
        for(int i = 0;i<testArray.length;i++){
            for(int j = 0;j<testArray.length;j++){
                testArray[i][j] = 1;
            }
        }
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 2,testArray);
        } catch (WrongPieceException w) {
            fail("should not run here");
        }
        catch (WrongArrayException w1){
            fail("should not run here");
        }
        for(int i = 0;i<testArray.length;i++){
            for(int j = 0;j<testArray.length;j++){
                assertEquals(chessBoard1.getBoard()[i][j],1);
            }
        }
    }

    @Test
    public void testChessBoardConstructorThrowWrongPieceException() {
        int[][] testArray = new int[10][10];
        for(int i = 0;i<testArray.length;i++){
            for(int j = 0;j<testArray.length;j++){
                testArray[i][j] = 1;
            }
        }
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 4,testArray);
            fail("should not run here");
        } catch (WrongPieceException w) {
        }
        catch (WrongArrayException w1){
            fail("should not run here");
        }
    }

    @Test
    public void testChessBoardConstructorThrowWrongArrayException() {
        int[][] testArray = new int[11][11];
        for(int i = 0;i<testArray.length;i++){
            for(int j = 0;j<testArray.length;j++){
                testArray[i][j] = 1;
            }
        }
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 4,testArray);
            fail("should not run here");
        } catch (WrongPieceException w) {
            fail("should not run here");
        }
        catch (WrongArrayException w1){
        }

        int[][] testArray1 = new int[11][11];
        for(int i = 0;i<testArray1.length;i++){
            for(int j = 0;j<testArray1.length;j++){
                testArray[i][j] = 4;
            }
        }
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 1,testArray1);
            fail("should not run here");
        } catch (WrongPieceException w) {
            fail("should not run here");
        }
        catch (WrongArrayException w1){
        }
    }

    @Test
    public void testChessBoardConstructorThrowBothException() {
        int[][] testArray = new int[10][10];
        for(int i = 0;i<testArray.length;i++){
            for(int j = 0;j<testArray.length;j++){
                testArray[i][j] = 1;
            }
        }
        testArray[1][3] = 4;
        try {
            this.chessBoard1 = new ChessBoard(10, "player1", "player2", 4,testArray);
            fail("should not run here");
        } catch (WrongPieceException w) {
            fail("should not run here");
        }
        catch (WrongArrayException w1){
        }
    }





    @Test
    public void testWinOnTheLine() {
        chessBoard1.putChessPieceO(1, 1);
        chessBoard1.putChessPieceO(1, 2);
        chessBoard1.putChessPieceO(1, 3);
        chessBoard1.putChessPieceO(1, 4);
        chessBoard1.putChessPieceO(1, 5);
        assertTrue(chessBoard1.winOnTheLine());


        chessBoard2.putChessPieceX(1, 3);
        chessBoard2.putChessPieceO(1, 2);
        chessBoard2.putChessPieceO(1, 3);
        chessBoard2.putChessPieceO(1, 4);
        chessBoard2.putChessPieceO(1, 5);
        assertFalse(chessBoard2.winOnTheLine());
    }

    @Test
    public void testWinOnTheColomn() {

        chessBoard1.putChessPieceO(1, 3);
        chessBoard1.putChessPieceO(2, 3);
        chessBoard1.putChessPieceX(3, 3);
        chessBoard1.putChessPieceO(4, 3);
        chessBoard1.putChessPieceO(5, 3);
        chessBoard1.putChessPieceO(6, 3);
        assertFalse(chessBoard1.winOnTheColomn());


        chessBoard2.putChessPieceO(1, 3);
        chessBoard2.putChessPieceO(2, 3);
        chessBoard2.putChessPieceO(3, 3);
        chessBoard2.putChessPieceO(4, 3);
        chessBoard2.putChessPieceO(5, 3);
        assertTrue(chessBoard2.winOnTheColomn());
    }

    @Test
    public void testWinLeftRightDiagonal1() {

        chessBoard1.putChessPieceO(5, 1);
        chessBoard1.putChessPieceO(6, 2);
        chessBoard1.putChessPieceO(7, 3);
        chessBoard1.putChessPieceO(8, 4);
        chessBoard1.putChessPieceO(9, 5);
        assertTrue(chessBoard1.winLeftRightDiagonal1());


        chessBoard2.putChessPieceX(1, 3);
        chessBoard2.putChessPieceO(1, 2);
        chessBoard2.putChessPieceO(1, 3);
        chessBoard2.putChessPieceO(1, 4);
        chessBoard2.putChessPieceO(1, 5);
        assertFalse(chessBoard2.winLeftRightDiagonal1());
    }

    @Test
    public void testWinLeftRightDiagonal2() {

        chessBoard1.putChessPieceO(1, 2);
        chessBoard1.putChessPieceO(2, 3);
        chessBoard1.putChessPieceO(3, 4);
        chessBoard1.putChessPieceO(4, 5);
        chessBoard1.putChessPieceO(5, 6);
        assertTrue(chessBoard1.winLeftRightDiagonal2());


        chessBoard2.putChessPieceX(1, 3);
        chessBoard2.putChessPieceO(1, 2);
        chessBoard2.putChessPieceO(1, 3);
        chessBoard2.putChessPieceO(1, 4);
        chessBoard2.putChessPieceO(1, 5);
        assertFalse(chessBoard2.winLeftRightDiagonal2());
    }

    @Test
    public void testWinRightLeftDiagonal1() {

        chessBoard1.putChessPieceO(6, 1);
        chessBoard1.putChessPieceO(5, 2);
        chessBoard1.putChessPieceO(4, 3);
        chessBoard1.putChessPieceO(3, 4);
        chessBoard1.putChessPieceO(2, 5);
        assertTrue(chessBoard1.winRightLeftDiagonal1());


        chessBoard2.putChessPieceX(6, 1);
        chessBoard2.putChessPieceO(5, 2);
        chessBoard2.putChessPieceO(4, 3);
        chessBoard2.putChessPieceO(3, 4);
        chessBoard2.putChessPieceO(2, 5);
        assertFalse(chessBoard2.winRightLeftDiagonal1());


    }

    @Test
    public void testWinRightLeftDiagonal2() {

        chessBoard1.putChessPieceO(9, 3);
        chessBoard1.putChessPieceO(8, 4);
        chessBoard1.putChessPieceO(7, 5);
        chessBoard1.putChessPieceO(6, 6);
        chessBoard1.putChessPieceO(5, 7);
        assertTrue(chessBoard1.winRightLeftDiagonal2());


        chessBoard2.putChessPieceX(9, 3);
        chessBoard2.putChessPieceO(8, 4);
        chessBoard2.putChessPieceO(7, 5);
        chessBoard2.putChessPieceO(6, 6);
        chessBoard2.putChessPieceO(5, 7);
        assertFalse(chessBoard2.winRightLeftDiagonal2());


    }

    @Test
    public void testIfWin() {
        try {
            ChessBoard chessBoard3 = new ChessBoard(10, "player1", "player2", 1);
            ChessBoard chessBoard4 = new ChessBoard(10, "player1", "player2", 2);
            ChessBoard chessBoard5 = new ChessBoard(10, "player1", "player2", 1);
            ChessBoard chessBoard6 = new ChessBoard(10, "player1", "player2", 2);
            ChessBoard chessBoard7 = new ChessBoard(10, "player1", "player2", 2);
            ChessBoard chessBoard8 = new ChessBoard(10, "player1", "player2", 2);


            chessBoard4.putChessPieceO(5, 1);
            chessBoard4.putChessPieceO(6, 2);
            chessBoard4.putChessPieceO(7, 3);
            chessBoard4.putChessPieceO(8, 4);
            chessBoard4.putChessPieceO(9, 5);
            assertTrue(chessBoard4.ifWin());

            chessBoard6.putChessPieceO(10, 2);
            chessBoard6.putChessPieceO(9, 3);
            chessBoard6.putChessPieceO(8, 4);
            chessBoard6.putChessPieceO(7, 5);
            chessBoard6.putChessPieceO(6, 6);
            assertTrue(chessBoard6.ifWin());

            chessBoard3.putChessPieceO(1, 3);
            chessBoard3.putChessPieceO(2, 3);
            chessBoard3.putChessPieceO(3, 3);
            chessBoard3.putChessPieceO(4, 3);
            chessBoard3.putChessPieceO(5, 3);
            assertTrue(chessBoard3.ifWin());

            chessBoard5.putChessPieceO(1, 2);
            chessBoard5.putChessPieceO(2, 3);
            chessBoard5.putChessPieceO(3, 4);
            chessBoard5.putChessPieceO(4, 5);
            chessBoard5.putChessPieceO(5, 6);
            assertTrue(chessBoard5.ifWin());


            chessBoard2.putChessPieceO(1, 1);
            chessBoard2.putChessPieceO(1, 2);
            chessBoard2.putChessPieceO(1, 3);
            chessBoard2.putChessPieceO(1, 4);
            chessBoard2.putChessPieceO(1, 5);
            assertTrue(chessBoard2.ifWin());


            chessBoard1.putChessPieceO(9, 1);
            chessBoard1.putChessPieceO(8, 2);
            chessBoard1.putChessPieceO(7, 3);
            chessBoard1.putChessPieceO(6, 4);
            chessBoard1.putChessPieceO(5, 5);
            assertTrue(chessBoard1.ifWin());

            chessBoard7.putChessPieceO(10, 5);
            chessBoard7.putChessPieceO(9, 6);
            chessBoard7.putChessPieceO(8, 7);
            chessBoard7.putChessPieceO(7, 8);
            chessBoard7.putChessPieceO(6, 9);
            assertTrue(chessBoard7.ifWin());

            chessBoard8.putChessPieceO(7, 5);
            chessBoard8.putChessPieceO(9, 6);
            chessBoard8.putChessPieceO(8, 7);
            chessBoard8.putChessPieceO(7, 8);
            chessBoard8.putChessPieceO(6, 9);
            assertFalse(chessBoard8.ifWin());
        } catch (Exception e) {
            fail("will not fail here");
        }

    }

    @Test
    public void TestIfFull() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                chessBoard1.putChessPieceO(i, j);
            }
        }
        assertTrue(chessBoard1.ifFull());


        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 10; j++) {
                chessBoard1.putChessPieceO(i, j);
            }
        }
        assertFalse(chessBoard2.ifFull());
    }

    @Test
    public void TestInitialize() {

        int[][] board = chessBoard1.getBoard();
        int[][] expectedBoard = new int[10][10];
        int line = 0;
        for (int[] aline : expectedBoard) {
            int colomn = 0;
            for (int b : aline) {
                expectedBoard[line][colomn] = 0;
                colomn++;
            }
            line++;

        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(board[i][j], expectedBoard[i][j]);


            }
        }

    }

    @Test
    public void testGetSize() {
        assertEquals(chessBoard1.getSize(), 10);
    }

    @Test
    public void testGetOnePosition() {
        assertEquals(chessBoard1.getOnePosition(1, 2), 0);
        chessBoard1.putChessPieceO(1, 2);
        assertEquals(chessBoard1.getOnePosition(0, 1), 1);
        chessBoard1.putChessPieceX(2, 3);
        assertEquals(chessBoard1.getOnePosition(1, 2), 2);
    }

    @Test
    public void tesGetPlayer1() {
        try {
            Player player1 = new Player("player1", 1);
            assertEquals(chessBoard1.getPlayer1().getPiece(), player1.getPiece());
            assertEquals(chessBoard1.getPlayer1().getName(), player1.getName());
        } catch (Exception e) {
            fail("will not have exception here");
        }
    }

    @Test
    public void testGetPlayer2() {
        try {
            Player player2 = new Player("player2", 2);
            assertEquals(chessBoard1.getPlayer2().getPiece(), player2.getPiece());
            assertEquals(chessBoard1.getPlayer2().getName(), player2.getName());
        } catch (Exception e) {
            fail("will not have exception here");
        }
    }

    @Test
    public void testGetName() {
        assertEquals(player1.getName(), "player1");
    }

    @Test
    public void testGetPiece() {
        assertEquals(player1.getPiece(), 1);
    }

    @Test
    public void testToJson() {
        JSONObject json = chessBoard1.toJson();

        int[][] board = chessBoard1.getBoard();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                jsonArray.put(board[i][j]);
            }
        }
        assertEquals(player1.name, json.getString("player1"));
        assertEquals(player2.name, json.getString("player2"));
        assertEquals(10, json.getInt("chessboard size"));
        assertEquals(1, json.getInt("piece1"));
        for (int i = 0; i < 100; i++) {
            assertEquals(jsonArray.get(i), json.getJSONArray("2D-board").get(i));
        }
    }

    @Test
    public void testBoardToJson() {
        JSONArray jsonArray = chessBoard1.boardToJson();
        int[][] board = chessBoard1.getBoard();
        JSONArray jsonArray2 = new JSONArray();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                jsonArray2.put(board[i][j]);
            }
        }
        for (int i = 0; i < 100; i++) {
            assertEquals(jsonArray.get(i), jsonArray2.get(i));
        }

    }

    @Test
    public void testPutChessPieceXThrowException(){
        try{
            chessBoard1.putChessPieceX(13,11);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
        try{
            chessBoard1.putChessPieceX(4,1111);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
        try{
            chessBoard1.putChessPieceX(1111,1);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
        try{
            chessBoard1.putChessPieceX(-1,3);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
        try{
            chessBoard1.putChessPieceX(4,-1);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }

        try{
            chessBoard1.putChessPieceX(-1,-1);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }

        try{
            chessBoard1.putChessPieceX(111,-1);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }

        try{
            chessBoard1.putChessPieceX(-1,-1);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
    }

    @Test
    public void testPutChessPieceXNothrowException(){
        try{
            chessBoard1.putChessPieceX(1,1);
        }catch (IndexOutOfBoundsException i){
            fail("should not run here");
        }

        assertEquals(chessBoard1.getBoard()[0][0],2);
    }

    @Test
    public void testPutChessPieceOThrowException(){
        try{
            chessBoard1.putChessPieceO(13,11);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }

        try{
            chessBoard1.putChessPieceO(11,2);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }

        try{
            chessBoard1.putChessPieceO(2,111);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
        try{
            chessBoard1.putChessPieceO(-1,2);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
        try{
            chessBoard1.putChessPieceO(2,-2);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){

        }
    }

    @Test
    public void testPutChessPieceONothrowException(){
        try{
            chessBoard1.putChessPieceO(10,10);
        }catch (IndexOutOfBoundsException i){
            fail("should not run here");
        }

        assertEquals(chessBoard1.getBoard()[9][9],1);

        try{
            chessBoard1.putChessPieceO(3,4);
        }catch (IndexOutOfBoundsException i){
            fail("should not run here");
        }

        assertEquals(chessBoard1.getBoard()[2][3],1);
    }

    @Test
    public void testGetOnePositionThrowException(){
        try{
            chessBoard1.getOnePosition(1000,2);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){
        }

        try{
            chessBoard1.getOnePosition(4,13);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){
        }

        try{
            chessBoard1.getOnePosition(13,13);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){
        }

        try{
            chessBoard1.getOnePosition(-1,4);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){
        }

        try{
            chessBoard1.getOnePosition(5,-1);
            fail("should not run here");
        }catch (IndexOutOfBoundsException i){
        }

    }

    @Test
    public void testGetOnePositionThrowNoException(){
        try{
            chessBoard1.getOnePosition(3,3);
            assertEquals(chessBoard1.getOnePosition(3,3),0);

        }catch (IndexOutOfBoundsException i){
            fail("should not run here");
        }


    }


}
