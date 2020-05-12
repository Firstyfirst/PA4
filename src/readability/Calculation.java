package src.readability;

import src.readability.strategy.ReadStrategy;
/**
 * calculate the index flesch and convert to flesch level 
 */
public class Calculation {
    /**
     * calculate the flesch index by formula
     * @param rd get the value of factor from ReadStrategy
     * @return result of flesch index
     */
    public Double indexFlesch(ReadStrategy rd) {
        double index = 206.835 - 84.6 * (rd.getSyllables() / rd.getWords())
                - 1.015 * (rd.getWords() / rd.getSentences());
        return index;
    }

    /**
     * cahnge the flesch index to level statement
     * @param index flesch index
     * @return text that tell the level of this flesch index
     */
    public String level(double index) {
        if (index > 100) return "4th grade student (elementary school)";
        else if (index > 90) return "5th grade student";
        else if (index > 80) return "6th grade student";
        else if (index > 70) return "7th grade student";
        else if (index > 65) return "8th grade student";
        else if (index > 60) return "9th grade student";
        else if (index > 50) return "High school student";
        else if (index > 30) return "College student";
        else if (index > 0) return "College graduate";
        else return "Advanced degree graduate";
    }

}