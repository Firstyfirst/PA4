package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class IOTask {

    static URL objUrl;

    static Double countWord;
    static Double countLine;
    static Double countSentence;
    static Double countSyllables;

    public static void error(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public static boolean isUrlValid(String url) {
        try {
            // create new object of URl
            objUrl = new URL(url);
            // check URL could or not convert to URI
            objUrl.toURI();
            return true;
        } catch (MalformedURLException mue) {
            return false;
        } catch (URISyntaxException use) {
            return false;
        }
    }

    public static ArrayList<Double> readFile(String filePaths) {

        countWord = 0.0;
        countLine = 0.0;
        countSentence = 0.0;
        countSyllables = 0.0;

        // check string on the textfield is url or file paths
        if (IOTask.isUrlValid(filePaths)) {
            String txt = filePaths.substring(filePaths.length()-4).toLowerCase();
            if (txt.equals(".txt")) {
                try (Scanner scanner = new Scanner(objUrl.openStream())) {
                    while (scanner.hasNextLine()) {
                        countAll(scanner.nextLine());
                    }
                }  catch (IOException ioe) {
                    error("I/O exeption");
                }
            }
            else {
                error("Wrong type url !!");
            }
        }

        else {

            File sourcefile = new File(filePaths);
            // check file paths could work or not
            if (!sourcefile.isFile() || !sourcefile.exists()) {
                error("This file is not file or does not exits.");
            }
            if (!sourcefile.canRead()) {
                error("This file is unreadable.");
            }

            String txt = filePaths.substring(filePaths.length()-4).toLowerCase();
            if (txt.equals(".txt")) {
                try (Scanner scanner = new Scanner(sourcefile);) {
                    while (scanner.hasNextLine()) {
                        countAll(scanner.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    error("File not found!!");
                }
            }
            else {
                error("Wrong file type !!");
            }
            
        }
        //System.out.println("ok");
        ArrayList<Double> strList = new ArrayList<Double>();
        strList.add(countLine);
        strList.add(countSentence);
        strList.add(countSyllables);
        strList.add(countWord);

        return strList;              
    }

    private static void countAll(String line) {
        //count line
        countLine++;
        
        //count word
        String[] puncList = line.split("[^\\w']");
        countWord += puncList.length + 1;
        String[] dashList = line.split("-");
        countWord -= dashList.length + 1;

        //count syllable
        String lowerCase;
        String[] wordList = line.split("\\s+");
        for (String word: wordList) {
            lowerCase = word.toLowerCase();
            
            if ("he she the".contains(lowerCase)) {
                countSyllables++;
            }
            else if (lowerCase.endsWith("e")) {
                lowerCase = lowerCase.substring(0, word.length()-1);
            }

            for (int i = 0; i < lowerCase.length(); i++) {
                if (!lowerCase.startsWith("y") && lowerCase.charAt(i) == 'y') {
                    char beforeY = lowerCase.charAt(i-1);
                    if (!(beforeY == 'a' || beforeY == 'e' || beforeY == 'i' || beforeY == 'o' || beforeY == 'u')) {
                        countSyllables++;
                    }
                }
                else if (lowerCase.charAt(i) == 'a' || lowerCase.charAt(i) == 'e' || lowerCase.charAt(i) == 'i' || lowerCase.charAt(i) == 'o' || lowerCase.charAt(i) == 'u') {
                    countSyllables++;
                }
            }
        }

        //count sentence
        String[] sentenceList = line.split("[!?.:]+");
        countSentence += sentenceList.length;  
    }
}