package Util;

import java.util.ArrayList;

public class Util {

    //all entries in part must equal entries in whole
    public boolean searchEntriesEqual(ArrayList<Integer> whole, ArrayList<Integer> part) {

        for(int i = 0; i < whole.size(); i++) {
            if(whole.get(i) == part.get(i) || part.get(i) == null) {

            }
            else {
                return false;
            }
        }

        return true;

    }

    public static ArrayList<ArrayList<Integer>> createCopy(ArrayList<ArrayList<Integer>> in) {
        ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < in.size(); i++) {
            out.add(new ArrayList<Integer>());
            for(int j = 0; j < in.get(i).size(); j++) {
                out.get(i).add(in.get(i).get(j));
            }
        }
        return out;
    }

}
