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

    public final static int PAWN_VALUE = 1;
    public final static int KNIGHT_VALUE = 3;
    public final static int BISHOP_VALUE = 3;
    public final static int ROOK_VALUE = 5;
    public final static int QUEEN_VALUE = 9;
    public final static int KING_VALUE = 100;


    //{{pieceID, columnNum, rowNum}...}
    public ArrayList<ArrayList<Integer>> whitePieces;

    public ArrayList<ArrayList<Integer>> blackPieces;

    public final boolean WHITE = false;
    public final boolean BLACK = true;

    public boolean turn = WHITE;


    public Board(ArrayList<ArrayList<Integer>> whitePieces, ArrayList<ArrayList<Integer>> blackPieces, boolean turn) {
        this.whitePieces = Util.createCopy(whitePieces);
        this.blackPieces = Util.createCopy(blackPieces);
        this.turn = turn;
    }

    public Board() {
        placePieces();

    }

    //output:{{x initial, y initial, x after, y after}...}
    public ArrayList<ArrayList<ArrayList<Integer>>> getPossibleMoves() {
        ArrayList<ArrayList<ArrayList<Integer>>> out = new ArrayList<ArrayList<ArrayList<Integer>>>();

        if(turn == WHITE) {
            for (int i = 0; i < whitePieces.size(); i++) {
                for(int x = 0; x < 8; x++) {
                    for(int y = 0; y < 8; y++) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(x);
                        list.add(y);

                        ArrayList<Integer> temp = Util.create1DCopy(whitePieces.get(i));
                        temp.set(0, null);

                        if(checkValidity(temp, list)) {
                            ArrayList<ArrayList<Integer>> list1 = new ArrayList<ArrayList<Integer>>();
                            list1.add(new ArrayList<Integer>());
                            list1.add(new ArrayList<Integer>());
                            list1.get(0).add(null);
                            list1.get(0).add(whitePieces.get(i).get(1));
                            list1.get(0).add(whitePieces.get(i).get(2));
                            list1.get(1).add(null);
                            list1.get(1).add(list.get(1));
                            list1.get(1).add(list.get(2));
                            out.add(list1);
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < blackPieces.size(); i++) {
                for(int x = 0; x < 8; x++) {
                    for(int y = 0; y < 8; y++) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(x);
                        list.add(y);

                        ArrayList<Integer> temp = Util.create1DCopy(blackPieces.get(i));
                        temp.set(0, null);

                        if(checkValidity(temp, list)) {
                            ArrayList<ArrayList<Integer>> list1 = new ArrayList<ArrayList<Integer>>();
                            list1.add(new ArrayList<Integer>());
                            list1.add(new ArrayList<Integer>());
                            list1.get(0).add(blackPieces.get(i).get(1));
                            list1.get(0).add(blackPieces.get(i).get(2));
                            list1.get(1).add(list.get(1));
                            list1.get(1).add(list.get(2));
                            out.add(list1);
                        }
                    }
                }
            }
        }
        return out;
    }

    public boolean movePieces(ArrayList<Integer> positionStart, ArrayList<Integer> positionEnd) {

        positionEnd.add(0, null);
        positionStart.add(0, null);

        if (checkValidity(positionStart, positionEnd)) {
            if (turn == WHITE) {
                int temp = Util.searchEqualEntry(blackPieces, positionEnd);
                if (temp != Util.NO_ENTRY) {
                    blackPieces.remove(temp);
                }

                temp = Util.searchEqualEntry(whitePieces, positionStart);
                whitePieces.get(temp).set(1, positionEnd.get(1));
                whitePieces.get(temp).set(2, positionEnd.get(2));

            } else {

                int temp = Util.searchEqualEntry(whitePieces, positionEnd);
                if (temp != Util.NO_ENTRY) {
                    whitePieces.remove(temp);
                }

                temp = Util.searchEqualEntry(blackPieces, positionStart);
                blackPieces.get(temp).set(1, positionEnd.get(1));
                blackPieces.get(temp).set(2, positionEnd.get(2));
            }
            turn = !turn;
            return true;
        }

        return false;

    }

    public boolean checkValidity(ArrayList<Integer> positionStart, ArrayList<Integer> positionEnd) {

        if (turn == BLACK && Util.searchEqualEntry(blackPieces, positionEnd) != Util.NO_ENTRY) {
            return false;
        } else if (turn == WHITE && Util.searchEqualEntry(whitePieces, positionEnd) != Util.NO_ENTRY) {
            return false;
        }

        int type;
        int temp = Util.searchEqualEntry(whitePieces, positionStart);
        int temp1 = Util.searchEqualEntry(blackPieces, positionStart);
        if (temp != Util.NO_ENTRY && turn == WHITE) {
            type = whitePieces.get(temp).get(0);
        } else if (temp1 != Util.NO_ENTRY && turn == BLACK) {
            type = blackPieces.get(temp1).get(0);
        } else {
            return false;
        }

        positionStart.set(0, type);

        //in check when piece moved?
        Board copy = new Board(whitePieces, blackPieces, turn);
        if(turn == BLACK) {
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
                if(checkAbleToMove(copy.whitePieces.get(i), copy.blackPieces.get(Util.searchEqualEntry(copy.blackPieces, kingFind)))) {
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
                if(checkAbleToMove(copy.blackPieces.get(i), copy.whitePieces.get(Util.searchEqualEntry(copy.whitePieces, kingFind)))) {
                    return false;
                }
            }
        }

        if(turn == WHITE) {
            if (whitePieces.get(Util.searchEqualEntry(whitePieces, positionStart)).get(0) == PIECE_PAWN && positionEnd.get(1) != positionStart.get(1) && Util.searchEqualEntry(blackPieces, positionEnd) == -1) {
                return false;
            }
        }
        else {
            if (blackPieces.get(Util.searchEqualEntry(blackPieces, positionStart)).get(0) == PIECE_PAWN && positionEnd.get(1) != positionStart.get(1) && Util.searchEqualEntry(blackPieces, positionEnd) == -1) {
                return false;
            }
        }


        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(0);
        if (checkAbleToMove(positionStart, positionEnd)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkAbleToMove(ArrayList<Integer> pieceStart, ArrayList<Integer> pieceEnd) {

        if (pieceStart.get(0) == PIECE_PAWN) {
            if (turn == WHITE) {
                if ((pieceEnd.get(1) == pieceStart.get(1) + 1 || pieceEnd.get(1) == pieceStart.get(1) - 1 || pieceEnd.get(1) == pieceStart.get(1)) && pieceEnd.get(2) == pieceStart.get(2) + 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if ((pieceEnd.get(1) == pieceStart.get(1) + 1 || pieceEnd.get(1) == pieceStart.get(1) - 1 || pieceEnd.get(1) == pieceStart.get(1)) && pieceEnd.get(2) == pieceStart.get(2) - 1) {
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

                int distance = 1;
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

                    distance = 1;
                    while (true) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(pieceStart.get(1) + x * distance);
                        list.add(pieceStart.get(2));

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

                int distance = 1;
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

                    distance = 1;
                    while (true) {

                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(null);
                        list.add(pieceStart.get(1) + x * distance);
                        list.add(pieceStart.get(1));


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
            if(Math.abs(pieceEnd.get(1) - pieceStart.get(1)) <= 1 && Math.abs(pieceEnd.get(2) - pieceStart.get(2)) <= 1) {
                return true;
            }
            else {
                return false;
            }
        }

        return false;
    }

    public int evaluatePosition(boolean color) {
        int value = 0;
        if(color == WHITE) {
            for(int i = 0; i < whitePieces.size(); i++) {
                if(whitePieces.get(i).get(0) == PIECE_PAWN) {
                    value += PAWN_VALUE;
                }
                else if(whitePieces.get(i).get(0) == PIECE_KNIGHT) {
                    value += KNIGHT_VALUE;
                }
                else if(whitePieces.get(i).get(0) == PIECE_BISHOP) {
                    value += BISHOP_VALUE;
                }
                else if(whitePieces.get(i).get(0) == PIECE_ROOK) {
                    value += ROOK_VALUE;
                }
                else if(whitePieces.get(i).get(0) == PIECE_QUEEN) {
                    value += QUEEN_VALUE;
                }
                else if(whitePieces.get(i).get(0) == PIECE_KING) {
                    value += KING_VALUE;
                }
                
            }
        }
        else {
            for(int i = 0; i < blackPieces.size(); i++) {
                if(blackPieces.get(i).get(0) == PIECE_PAWN) {
                    value += PAWN_VALUE;
                }
                else if(blackPieces.get(i).get(0) == PIECE_KNIGHT) {
                    value += KNIGHT_VALUE;
                }
                else if(blackPieces.get(i).get(0) == PIECE_BISHOP) {
                    value += BISHOP_VALUE;
                }
                else if(blackPieces.get(i).get(0) == PIECE_ROOK) {
                    value += ROOK_VALUE;
                }
                else if(blackPieces.get(i).get(0) == PIECE_QUEEN) {
                    value += QUEEN_VALUE;
                }
                else if(blackPieces.get(i).get(0) == PIECE_KING) {
                    value += KING_VALUE;
                }

            }
        }
        return value;
    }

    public void placePieces() {

        whitePieces = new ArrayList<ArrayList<Integer>>();
        blackPieces = new ArrayList<ArrayList<Integer>>();


        //place pawns
        for (int i = 0; i < 8; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(PIECE_PAWN);
            list.add(i);
            list.add(1);
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
