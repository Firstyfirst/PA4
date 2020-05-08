package readability.stategy;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class UrlStategy extends ReadStategy {

    @Override
    public ArrayList<Double> read(String path)   {
		String txt = path.substring(path.length()-4).toLowerCase();
            if (txt.equals(".txt")) {
                try (Scanner scanner = new Scanner(new URL(path).openStream())) {
                    while (scanner.hasNextLine()) {
                        countAll(scanner.nextLine());
                    }
                } catch (IOException e) {
                    error("I/O exeption");
                }
            }
            else {
                error("Wrong type url !!");
            }

        ArrayList<Double> strList = new ArrayList<Double>();
        strList.add(countLine);
        strList.add(countSentence);
        strList.add(countSyllables);
        strList.add(countWord);

        return strList;
    }

    public String toString() {
        return "URL path";
    }
}