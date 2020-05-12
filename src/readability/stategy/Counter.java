package src.readability.stategy;

public class Counter {
    
    enum State {
        START,
        // vowel state
        VOWEL,
        CONSONENT,
        VOWEL_E, // the first vowel 'e' in a group
        NONWORD;
    }

    State state;

    boolean nosentence = false;

    private static String VOWELS = "aeiou";

    public int countSyllable(String word) {
        char[] chars = word.toCharArray();

        int vowelgroups = 0;
        state = State.START;
        for(char c: chars) {
            switch(state) {
                case START :
                    if (isVowel(c) || c == 'y') {
                        vowelgroups++;
                        state = State.VOWEL;
                    }
                    else if (Character.isLetter(c) || c == '\'') {state = State.CONSONENT;}
                    else state = State.NONWORD;
                    break;
                case CONSONENT :
                    if (c == 'e') {
                        vowelgroups++;
                        state = State.VOWEL_E;
                    }
                    else if (isVowel(c) || c == 'y') {
                        vowelgroups++;
                        state = State.VOWEL;
                    }
                    else if (Character.isLetter(c) || c == '\'' || c == '-') {
                        // still stay in consonent state
                    }
                    else state = State.NONWORD;
                    break;
                case VOWEL_E :
                case VOWEL :
                    if (isVowel(c)) {
                        //still stay in vowel state and do not add vowelgroup
                    }
                    else if (Character.isLetter(c) || c == '\'' || c == '-') {state = State.CONSONENT;}
                    else state = State.NONWORD;
                    break;
                case NONWORD :
                    // always stay in this state (not a word)
            }
        }// end of the word

        // handle special case
        if (state == State.VOWEL_E) {
            // the word end with letter 'e' but 'e' was by itself (not after another vowel)
            // he she we the me
            if (vowelgroups > 1) vowelgroups -= 1;
        }
        else if (state == State.NONWORD) vowelgroups = 0;
        // number of syllables is the number of vowelgroups.
        return vowelgroups;
    }

    private boolean isVowel(char ch) {
        return VOWELS.indexOf(ch) >= 0;
    }

    public int countSentence(String line) {
        int sentencegroups = 0;
        //System.out.println("n : " + line);
        String[] lines = line.split("\\s+");
        for(String word: lines) {
            
            if (word.endsWith(".") || word.endsWith(";") || word.endsWith("!") || word.endsWith("?")) {
                String subword = word.substring(0, word.length() - 1);
                if (isNumeric(subword)) sentencegroups--;
                sentencegroups++;
            }
        }
        
        if (sentencegroups == 0) nosentence = true;
        
        if (nosentence && line.isBlank()) {
            nosentence = false; 
            sentencegroups++;
        }
        return sentencegroups;
    }

    public boolean isNumeric(String word) {
        char[] chars = word.toCharArray();
        boolean isnumber = false;
        for(char c: chars) {
            if (Character.isDigit(c)) {
                isnumber = true;
            }
        }
        return isnumber;
    }
}