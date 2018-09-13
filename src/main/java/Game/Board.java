package Game;

import java.util.ArrayList;

public class Board {

    public final int PIECE_PAWN = 0;
    public final int PIECE_KNIGHT = 1;
    public final int PIECE_BISHOP = 2;
    public final int PIECE_ROOK = 3;
    public final int PIECE_QUEEN = 4;
    public final int PIECE_KING = 5;

    //{{pieceID, columnNum, rowNum}...}
    public ArrayList<ArrayList<Integer>> whitePieces;

    public ArrayList<ArrayList<Integer>> blackPieces;


    public Board() {
        placePieces();

    }

    public void movePieces(ArrayList<Integer> positionStart, ArrayList<Integer> positionEnd) {

        if(checkValidity(positionStart, positionEnd)) {

        }
    }

    public boolean checkValidity(ArrayList<Integer> positionStart, ArrayList<Integer> positionEnd) {

    }

    public void placePieces() {

        whitePieces = new ArrayList<ArrayList<Integer>>();
        blackPieces = new ArrayList<ArrayList<Integer>>();


        //place pawns
        for(int i = 0; i < 8; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(PIECE_PAWN);
            list.add(i);
            list.add(2);
            whitePieces.add(list);
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(PIECE_KNIGHT);
        list.add(1);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_KNIGHT);
        list.add(6);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_BISHOP);
        list.add(2);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_BISHOP);
        list.add(5);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_ROOK);
        list.add(0);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_ROOK);
        list.add(7);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_QUEEN);
        list.add(3);
        list.add(0);
        whitePieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_KING);
        list.add(4);
        list.add(0);
        whitePieces.add(list);



        //place pawns
        for(int i = 0; i < 8; i++) {
            list = new ArrayList<Integer>();
            list.add(PIECE_PAWN);
            list.add(i);
            list.add(6);
            blackPieces.add(list);
        }

        list = new ArrayList<Integer>();
        list.add(PIECE_KNIGHT);
        list.add(1);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_KNIGHT);
        list.add(6);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_BISHOP);
        list.add(2);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_BISHOP);
        list.add(5);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_ROOK);
        list.add(0);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_ROOK);
        list.add(7);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_QUEEN);
        list.add(3);
        list.add(7);
        blackPieces.add(list);

        list = new ArrayList<Integer>();
        list.add(PIECE_KING);
        list.add(4);
        list.add(7);
        blackPieces.add(list);
    }

}
