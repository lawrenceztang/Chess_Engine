package Game;

import Util.Util;

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

    public final boolean WHITE_TURN = false;
    public final boolean BLACK_TURN = true;

    public boolean turn = WHITE_TURN;


    public Board(ArrayList<ArrayList<Integer>> whitePieces, ArrayList<ArrayList<Integer>> blackPieces) {
        this.whitePieces = Util.createCopy(whitePieces);
        this.blackPieces = Util.createCopy(blackPieces);
    }

    public Board() {
        placePieces();

    }

    //output:{{x initial, y initial, x after, y after}...}
    public ArrayList<ArrayList<Integer>> getPossibleMoves() {
        ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();

        if(turn == WHITE_TURN) {
            for (int i = 0; i < whitePieces.size(); i++) {
                for(int x = 0; x < 8; x++) {
                    for(int y = 0; y < 8; y++) {
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(x);
                        list.add(y);
                        if(checkValidity(whitePieces.get(i), list)) {
                            ArrayList<Integer> list1 = new ArrayList<Integer>();
                            list1.add(whitePieces.get(i).get(0));
                            list1.add(whitePieces.get(i).get(1));
                            list1.add(list.get(0));
                            list1.add(list.get(1));
                            out.add(list1);
                        }
                    }
                }
            }
        }
    }

    public void movePieces(ArrayList<Integer> positionStart, ArrayList<Integer> positionEnd) {

        if (checkValidity(positionStart, positionEnd)) {
            if (turn == WHITE_TURN) {
                int temp = Util.searchEqualEntry(blackPieces, positionEnd);
                if (temp != Util.NO_ENTRY) {
                    blackPieces.remove(temp);
                }

                temp = Util.searchEqualEntry(whitePieces, positionStart);
                whitePieces.get(temp).set(2, positionEnd.get(2));
                whitePieces.get(temp).set(3, positionEnd.get(3));

            } else {
                int temp = Util.searchEqualEntry(whitePieces, positionEnd);
                if (temp != Util.NO_ENTRY) {
                    whitePieces.remove(temp);
                }

                temp = Util.searchEqualEntry(blackPieces, positionStart);
                blackPieces.get(temp).set(2, positionEnd.get(2));
                blackPieces.get(temp).set(3, positionEnd.get(3));
            }

        }

        turn = !turn;
    }

    public boolean checkValidity(ArrayList<Integer> positionStart, ArrayList<Integer> positionEnd) {

        if (turn == BLACK_TURN && Util.searchEqualEntry(blackPieces, positionEnd) != Util.NO_ENTRY) {
            return false;
        } else if (turn == WHITE_TURN && Util.searchEqualEntry(whitePieces, positionEnd) != Util.NO_ENTRY) {
            return false;
        }

        positionEnd.add(0, null);
        positionStart.add(0, null);

        int type;
        int temp = Util.searchEqualEntry(whitePieces, positionStart);
        int temp1 = Util.searchEqualEntry(blackPieces, positionStart);
        if (temp != Util.NO_ENTRY && turn == WHITE_TURN) {
            type = whitePieces.get(temp).get(0);
        } else if (temp1 != Util.NO_ENTRY && turn == BLACK_TURN) {
            type = blackPieces.get(temp1).get(0);
        } else {
            return false;
        }

        positionStart.set(0, type);

        //in check when piece moved?
        Board copy = new Board(whitePieces, blackPieces);
        if(turn == BLACK_TURN) {
            temp = Util.searchEqualEntry(whitePieces, positionEnd);
            if(temp != -1) {
                copy.whitePieces.remove(temp);
            }
            temp = Util.searchEqualEntry(copy.blackPieces, positionStart);

            copy.blackPieces.get(temp).set(1, positionEnd.get(1));
            copy.blackPieces.get(temp).set(2, positionEnd.get(2));

            ArrayList<Integer> kingFind = new ArrayList<Integer>();
            kingFind.add(PIECE_KING);
            kingFind.add(null);
            kingFind.add(null);

            for(int i = 0; i < copy.whitePieces.size(); i++) {
                if(checkAbleToMove(whitePieces.get(i), blackPieces.get(Util.searchEqualEntry(blackPieces, kingFind)))) {
                    return false;
                }
            }
        }
        else {
            temp = Util.searchEqualEntry(blackPieces, positionEnd);
            if(temp != -1) {
                copy.blackPieces.remove(temp);
            }
            temp = Util.searchEqualEntry(copy.whitePieces, positionStart);

            copy.whitePieces.get(temp).set(1, positionEnd.get(1));
            copy.whitePieces.get(temp).set(2, positionEnd.get(2));

            ArrayList<Integer> kingFind = new ArrayList<Integer>();
            kingFind.add(PIECE_KING);
            kingFind.add(null);
            kingFind.add(null);

            for(int i = 0; i < copy.blackPieces.size(); i++) {
                if(checkAbleToMove(blackPieces.get(i), whitePieces.get(Util.searchEqualEntry(whitePieces, kingFind)))) {
                    return false;
                }
            }
        }


        if (checkAbleToMove(positionStart, positionEnd)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkAbleToMove(ArrayList<Integer> pieceStart, ArrayList<Integer> pieceEnd) {





        if (pieceStart.get(0) == PIECE_PAWN) {
            if (turn == WHITE_TURN) {
                if ((pieceEnd.get(2) == pieceStart.get(2) + 1 || pieceEnd.get(2) == pieceStart.get(2) - 1 || pieceEnd.get(2) == pieceStart.get(2)) && pieceEnd.get(1) == pieceStart.get(1) + 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if ((pieceEnd.get(2) == pieceStart.get(2) + 1 || pieceEnd.get(2) == pieceStart.get(2) - 1 || pieceEnd.get(2) == pieceStart.get(2)) && pieceEnd.get(1) == pieceStart.get(1) - 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (pieceStart.get(0) == PIECE_KNIGHT) {

            for (int x = -2; x <= 2; x += 4) {
                for (int y = -1; y <= 1; y += 1) {
                    if (pieceEnd.get(1) == pieceStart.get(1) + x && pieceEnd.get(2) == pieceStart.get(2) + y) {
                        return true;
                    }
                }
            }
            for (int x = -1; x <= 1; x += 2) {
                for (int y = -2; y <= 2; y += 2) {
                    if (pieceEnd.get(1) == pieceStart.get(1) + x && pieceEnd.get(2) == pieceStart.get(2) + y) {
                        return true;
                    }
                }
            }

            return false;
        } else if (pieceStart.get(0) == PIECE_BISHOP) {
            for (int x = -1; x <= 1; x += 2) {
                for (int y = -1; y <= 1; y += 2) {
                    int distance = 1;
                    while (true) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(pieceStart.get(1) + x * distance);
                        list.add(pieceStart.get(2) + y * distance);

                        if (pieceStart.get(1) + x * distance == pieceEnd.get(1) && pieceStart.get(2) + y * distance == pieceEnd.get(2)) {
                            return true;
                        } else if (pieceStart.get(1) + x * distance > 7 || pieceStart.get(1) + x * distance < 0 || pieceStart.get(2) + y * distance > 7 || pieceStart.get(2) + y * distance < 0 || Util.searchEqualEntry(whitePieces, list) != Util.NO_ENTRY || Util.searchEqualEntry(blackPieces, list) != Util.NO_ENTRY) {
                            break;
                        }

                        distance++;
                    }
                }
            }
            return false;
        } else if (pieceStart.get(0) == PIECE_ROOK) {

            for (int y = 0 - 1; y <= 1; y += 2) {

                int distance = 0;
                while (true) {

                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(null);
                    list.add(pieceStart.get(1));
                    list.add(pieceStart.get(2) + y * distance);


                    if (pieceStart.get(1) == pieceEnd.get(1) && pieceStart.get(2) + y * distance == pieceEnd.get(2)) {
                        return true;
                    } else if (pieceStart.get(2) + y * distance > 7 || pieceStart.get(2) + y * distance < 0 || Util.searchEqualEntry(whitePieces, list) != Util.NO_ENTRY || Util.searchEqualEntry(blackPieces, list) != Util.NO_ENTRY) {
                        break;
                    }
                    distance++;
                }

                for (int x = 0 - 1; x <= 1; x += 2) {

                    distance = 0;
                    while (true) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(pieceStart.get(1) + x * distance);


                        if (pieceStart.get(1) + x * distance == pieceEnd.get(1) && pieceStart.get(2) == pieceEnd.get(2)) {
                            return true;
                        } else if (pieceStart.get(1) + x * distance > 7 || pieceStart.get(1) + x * distance < 0 || Util.searchEqualEntry(whitePieces, list) != Util.NO_ENTRY || Util.searchEqualEntry(blackPieces, list) != Util.NO_ENTRY) {
                            break;
                        }
                        distance++;
                    }
                }
            }
        } else if (pieceStart.get(0) == PIECE_QUEEN) {

            for (int y = 0 - 1; y <= 1; y += 2) {

                int distance = 0;
                while (true) {

                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(null);
                    list.add(pieceStart.get(1));
                    list.add(pieceStart.get(2) + y * distance);


                    if (pieceStart.get(1) == pieceEnd.get(1) && pieceStart.get(2) + y * distance == pieceEnd.get(2)) {
                        return true;
                    } else if (pieceStart.get(2) + y * distance > 7 || pieceStart.get(2) + y * distance < 0 || Util.searchEqualEntry(whitePieces, list) != Util.NO_ENTRY || Util.searchEqualEntry(blackPieces, list) != Util.NO_ENTRY) {
                        break;
                    }
                    distance++;
                }

                for (int x = 0 - 1; x <= 1; x += 2) {

                    distance = 0;
                    while (true) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(pieceStart.get(1) + x * distance);


                        if (pieceStart.get(1) + x * distance == pieceEnd.get(1) && pieceStart.get(2) == pieceEnd.get(2)) {
                            return true;
                        } else if (pieceStart.get(1) + x * distance > 7 || pieceStart.get(1) + x * distance < 0 || Util.searchEqualEntry(whitePieces, list) != Util.NO_ENTRY || Util.searchEqualEntry(blackPieces, list) != Util.NO_ENTRY) {
                            break;
                        }
                        distance++;
                    }
                }
            }

            for (int x = -1; x <= 1; x += 2) {
                for (int y = -1; y <= 1; y += 2) {
                    int distance = 1;
                    while (true) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(pieceStart.get(1) + x * distance);
                        list.add(pieceStart.get(2) + y * distance);

                        if (pieceStart.get(1) + x * distance == pieceEnd.get(1) && pieceStart.get(2) + y * distance == pieceEnd.get(2)) {
                            return true;
                        } else if (pieceStart.get(1) + x * distance > 7 || pieceStart.get(1) + x * distance < 0 || pieceStart.get(2) + y * distance > 7 || pieceStart.get(2) + y * distance < 0 || Util.searchEqualEntry(whitePieces, list) != Util.NO_ENTRY || Util.searchEqualEntry(blackPieces, list) != Util.NO_ENTRY) {
                            break;
                        }

                        distance++;
                    }
                }
            }
        } else if (pieceStart.get(0) == PIECE_KING) {

        }

    }

    public void placePieces() {

        whitePieces = new ArrayList<ArrayList<Integer>>();
        blackPieces = new ArrayList<ArrayList<Integer>>();


        //place pawns
        for (int i = 0; i < 8; i++) {
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
        for (int i = 0; i < 8; i++) {
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
