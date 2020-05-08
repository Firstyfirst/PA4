package readability.stategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class FileStategy extends ReadStategy {

    public ArrayList<Double> read(String path) {
        // check string on the textfield is url or file paths
        File sourcefile = new File(path);
        // check file paths could work or not
        if (!sourcefile.isFile() || !sourcefile.exists()) {
            error("This file is not file or does not exits.");
        }
        if (!sourcefile.canRead()) {
            error("This file is unreadable.");
        }

        String txt = path.substring(path.length() - 4).toLowerCase();
        if (txt.equals(".txt")) {
            try (Scanner scanner = new Scanner(sourcefile);) {
                while(scanner.hasNextLine()) {
                    countAll(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                error("File not found!!");
            }
        } else error("Wrong file type !!");

        ArrayList<Double> strList = new ArrayList<Double>();
        strList.add(countLine);
        strList.add(countSentence);
        strList.add(countSyllables);
        strList.add(countWord);

        return strList;
    }

    public String toString() {
        return "File name";
    }
}