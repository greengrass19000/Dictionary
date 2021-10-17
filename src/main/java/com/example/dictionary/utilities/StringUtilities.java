package com.example.dictionary.utilities;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.MessageType;

public class StringUtilities {

    /** This function checks if the string doesn't have strange character, only space approved.*/
    public static boolean isInvalidString(String word) {
        for(char l : word.toCharArray()) {
            if((l > 'z' || l < 'a') && (l > 'Z' || l < 'A') && l != ' ') {
                DictionaryController.instance.displayMessage("Invalid string", MessageType.ERROR);
                return true;
            }
        }
        return false;
    }

    /** remove unnecessary space characters.*/
    public static String processWord(String word) {
        word = word.trim();
        StringBuilder t = new StringBuilder();
        boolean spaceExists = false;
        for(char l : word.toCharArray()) {
            if (l == ' ') {
                spaceExists = true;
            } else {
                if(spaceExists) {
                    t.append(' ');
                    spaceExists = false;
                }
                t.append(l);
            }
        }
        return t.toString();
    }
}
