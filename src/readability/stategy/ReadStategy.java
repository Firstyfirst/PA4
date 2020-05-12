package src.readability.stategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class ReadStategy {

    private int words;
    private int lines;
    private int sentences;
    private int syllables;
    private String name;
    
    Scanner scanner;

    public void read(String path) {
        
        words = 0;
        lines = 0;
        sentences = 0;
        syllables = 0;
        
        InputStream in = null;
        if (path.contains("://")) {
            // look like url
            name = "Url path";
            try {
                URL url = new URL(path);
                in = url.openStream();
            } catch (IOException e) {
                // print message
            }
        }
        else {
            // look like file
            name = "File path";
            try {
                in = new FileInputStream(path);
            } catch (IOException e) {
                //print a message
            }
        }
        //if (in == null) // do sth
        scanner = new Scanner(in);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            countAll(line);
        }
    }

    public void error(String message) {
        System.out.println(message);
        System.exit(0);
    }

    public void countAll(String line) {
        //count line
        lines++;
        
        Counter counter = new Counter();
        if (!line.equals("")) {
            String[] wordList = line.split("[.,()_/:!?;]|\s|\"");
            // count syllable
            for(String word: wordList) {
                //System.out.println(word);
                int n = counter.countSyllable(word.toLowerCase());
                //System.out.println("n : " + n);
                syllables += n;
                if (n > 0) {
                    // it is a word
                    words++;
                }
            }
        }
        int i = counter.countSentence(line);
        //System.out.println(counter.state);
        sentences += i;
    }

    public double getWords() {
        return (double) words;
    }

    public double getLines() {
        return (double) lines;
    }

    public double getSentences() {
        return (double) sentences;
    }

    public double getSyllables() {
        return (double) syllables;
    }

    public String getName() {
        return name;
    }
}