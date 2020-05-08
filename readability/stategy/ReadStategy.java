package readability.stategy;

import java.util.ArrayList;

public abstract class ReadStategy {
    
    Double countWord = 0.0;
    Double countLine = 0.0;
    Double countSentence = 0.0;
    Double countSyllables = 0.0;

    public abstract ArrayList<Double> read(String path);

    public abstract String toString();

    public void error(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public void countAll(String line) {
        //count line
        countLine++;
        
        String lowerCase;
        if (!line.equals("")) {
            String[] wordList = line.split("[., !?]+");
            // count syllable
            for(String i: wordList) {
                lowerCase = i.toLowerCase();
                // check word is he she the or me
                if (lowerCase.equals("he") || lowerCase.equals("she") || lowerCase.equals("the") || lowerCase.equals("me")) countSyllables++;
                // check the last character is 'e' or not
                else if (lowerCase.endsWith("e")) lowerCase = lowerCase.substring(0, i.length()-1);
                for (int num = 0; num < lowerCase.length(); num++) {
                    // check character in front of 'y' is vowel or consonant
                    if (!lowerCase.startsWith("y") && lowerCase.charAt(num) == 'y') {
                        char beforeY = lowerCase.charAt(num-1);
                        if (!isVowel(beforeY)) countSyllables++;
                    }
                    else if (isVowel(lowerCase.charAt(num))) countSyllables++;
                }
            }
        
            String[] wordList2 = line.split("\\s+");
            for (String word: wordList2) {
                // count word
                if (!(word.isBlank() || word.isEmpty())) countWord++;
                // count sentence
                if (word.endsWith(".") || word.endsWith("?") || word.endsWith("!") || word.endsWith(";")) countSentence++;
            }
        }
    }

    private boolean isVowel(char ch) {
        switch (ch) {
            case 'a' : return true;
            case 'e' : return true;
            case 'i' : return true;
            case 'o' : return true;
            case 'u' : return true;
            default : return false;
        }
    }
}