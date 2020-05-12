package src.readability.strategy;
/**
 * count the syllable and sentence
 */
public class Counter {
    /** contain the state when count syllable */
    enum State {
        START,
        VOWEL,
        CONSONENT,
        VOWEL_E, // the first vowel 'e' in a group
        NONWORD;
    }
    /** state for each character, so change every time when change character */
    State state;
    /** tell the state that the prevoius line have sentence or not */
    boolean nosentence = false;
    /** vowel */
    private static String VOWELS = "aeiou";
    
    /**
     * count syllable in a word
     * @param word get word from word list
     * @return number of syllables in one word. Return the integer
     * if final state is vowel or consonant. If end with "e" and there are
     * syllables more than one, minus one before return. 
     * If final state is nonword, return 0
     */
    public int countSyllable(String word) {
        char[] chars = word.toCharArray();
        int vowelgroups = 0;
        // set first state
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

    /**
     * check the character is vowel or not
     * @param ch the character in word
     * @return return true if this character is vowel
     * otherwise, return false 
     */
    private boolean isVowel(char ch) {
        return VOWELS.indexOf(ch) >= 0;
    }

    /**
     * count the sentence in a line
     * @param line the line that seperate with \n 
     * @return return number of sentence in a line. 
     * If the line do not have sentence and next line
     * is blank line, count it as sentence
     */
    public int countSentence(String line) {
        int sentencegroups = 0;
        String[] lines = line.split("\\s+");
        for(String word: lines) {
            // the word end with ".", ";", "!", "?"
            if (word.endsWith(".") || word.endsWith(";") || word.endsWith("!") || word.endsWith("?")) {
                String subword = word.substring(0, word.length() - 1);
                if (isNumeric(subword)) sentencegroups--;
                sentencegroups++;
            }
        } // end of the line

        if (sentencegroups == 0) nosentence = true;
        // if there is not a sentence in line 
        // and next line is blank line, count as a sentence
        if (nosentence && line.isBlank()) {
            nosentence = false; 
            sentencegroups++;
        }
        return sentencegroups;
    }

    /**
     * check the character is number or not
     * @param word get word from word list
     * @return return true if there is a number in word
     * otherwise, return false. 
     */
    public boolean isNumeric(String word) {
        char[] chars = word.toCharArray();
        boolean isnumber = false;
        for(char c: chars) {
            if (Character.isDigit(c)) isnumber = true;
        }
        return isnumber;
    }
}