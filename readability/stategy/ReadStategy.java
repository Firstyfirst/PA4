package readability.stategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadStategy {

    Double countWord = 0.0;
    Double countLine = 0.0;
    Double countSentence = 0.0;
    Double countSyllables = 0.0;

    private static ReadStategy readStategy = null;

    private ReadStategy() {}

    public static ReadStategy getInstance() {
        if (readStategy == null)
            readStategy = new ReadStategy();
        return readStategy;
    }

    public ArrayList<Double> read(String path, String strategy) {
        Scanner scanner = null;
        String txt = path.substring(path.length() - 4).toLowerCase();
        if (txt.equals(".txt")) {
            if (strategy.equals("Url")) {
                try {
                    scanner = new Scanner(new URL(path).openStream());
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (strategy.equals("File")) { 
                try {
                    scanner = new Scanner(new File(path));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } 
        else error("Wrong type !!");
    
        while (scanner.hasNextLine()) {
            countAll(scanner.nextLine());
        }
 
        ArrayList<Double> strList = new ArrayList<Double>();
        strList.add(countLine);
        strList.add(countSentence);
        strList.add(countSyllables);
        strList.add(countWord);

        return strList;
    }


    public String toString(String stategy) {
        if (stategy.equals("File")) return "File name";
        return "URL path"; 
    }

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