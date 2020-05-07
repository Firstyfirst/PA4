package readability;

public class Calculation {

    double line;

    double sentence;

    double syllabel;

    double word;

    public Calculation(double line, double sentence, double syllabel, double word) {
        this.line = line;
        this.sentence = sentence;
        this.syllabel = syllabel;
        this.word = word;
    }

    public Double IndexFlesch() {
        double index = 206.835 - 84.6 * (this.syllabel / this.word) - 1.015 * (this.word / this.sentence);
        return index;
    }

    public String Level(double index) {
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