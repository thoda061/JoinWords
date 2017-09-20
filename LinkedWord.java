import java.util.*;

/** COSC326 Etude 7
 * LinkedWord.java - Class for storing infomation about a word inside a
 * doubly/singly joined word sequence.
 *
 * @author Daniel Thomson, ID: 5040702 & Will Shaw, ID: 8291780
 */
public class LinkedWord {

    //The word we are storing information about
    private String word;
    //The parent of this word in it sequence
    private LinkedWord parent;
    //How deep this word is inside the sequence
    private int depth;

    /**
     * Constructer that take a word and set depth to default value of 2
     *
     * @param word, the word we are storing info about
     */
    public LinkedWord (String word) {
        this.word = word;
        this.depth = 2;
    }

    //Getters and Setters for the variables
    public void setParent (LinkedWord parent) {
        this.parent = parent;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public LinkedWord getParent () {
        return parent;
    }

    public String getWord () {
        return word;
    }

}
        
    
