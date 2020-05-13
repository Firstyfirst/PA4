package src.readability.strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
/**
 * 
 * read text from file or url link
 * 
 */
public class ReadStrategy {
    /** number of word */
    private int wordNumbers;
    /** number of lines */
    private int lineNumbers;
    /** number of sentences */
    private int sentenceNumbers;
    /** number of syllables */
    private int syllableNumbers;
    /** name of output statement, so tell text come from file or url */
    private String name;

    /**
     * read text from file or url and change the Inputstream
     * to match with type of path.
     * @param path the file path or url path
     */
    public void read(String path) {
        wordNumbers = 0;
        lineNumbers = 0;
        sentenceNumbers = 0;
        syllableNumbers = 0;

        InputStream in = null;
        if (path.contains("://")) {
            // look like url
            name = "Url path";
            try {
                URL url = new URL(path);
                in = url.openStream();
            } catch (IOException e) {
                error("URL error!");
            }
        }
        else {
            // look like file
            name = "File path";
            try {
                in = new FileInputStream(path);
            } catch (IOException e) {
                error("File error!");
            }
        }

        Scanner scanner = new Scanner(in);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            countAll(line);
        }
    }

    /**
     * print a message when code stick in error
     * @param message showing message
     */
    public void error(String message) {
        System.out.println(message);
        System.exit(0);
    }

    /**
     * conut number of words, lines, sentences and syllables
     * @param line get line from scanner
     */
    public void countAll(String line) {
        //count line
        lineNumbers++;
        
        Counter counter = new Counter();
        if (!line.equals("")) {
            String[] wordList = line.split("[.,()_/:!?;]|\\s|\"");
            // count syllable
            for(String word: wordList) {
                int n = counter.countSyllable(word.toLowerCase());
                syllableNumbers += n;
                if (n > 0) {
                    // it is a word
                    wordNumbers++;
                }
            }
        }
        int i = counter.countSentence(line);
        sentenceNumbers += i;
    }

    /**
     * get number of words
     * @return total amount of words 
     */
    public double getWords() {
        return (double) wordNumbers;
    }

    /**
     * get number of lines
     * @return total amount of lines
     */
    public double getLines() {
        return (double) lineNumbers;
    }

    /**
     * get number of sentences
     * @return total amount of sentences
     */
    public double getSentences() {
        return (double) sentenceNumbers;
    }

    /**
     * get numbers of syllables
     * @return total amount of syllables
     */
    public double getSyllables() {
        return (double) syllableNumbers;
    }

    /**
     * get name that determined by file or url
     * @return "file name" or "url path"
     */
    public String getName() {
        return name;
    }
}