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

}
