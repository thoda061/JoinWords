import java.util.*;

/**
 * Class that takes two words and a dictionary and output the shortest sequence
 * of doubly and singly joined words from the dictionary which are between the
 * two given words.
 *
 * @author Daniel Thomson, ID: 5040702
 */
public class JoinUp{

    //First word in the sequence
    private static String firstWord;
    //Last word in the sequence
    private static String lastWord;
    //Dictionary of word used to fill the sequence
    private static ArrayList<String> dict = new ArrayList<>();

    /**
     * Main method which fill up the dictionary from standard in and sets
     * firstWord and lastWord from command line arguments. Also calls
     * findSequence to generate output.
     *
     * @param args command line arguments usd to set first and last word.
     */
    public static void main(String[]args) {
        firstWord = args[0];
        lastWord = args[1];

        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            dict.add(sc.nextLine());
        }

        findSequence();
    }

    /**
     * Finds sequences of words that are either doubly linked or singly linked
     * between firstWord and lastWord, then prints them.
     */
    public static void findSequence () {
        //Queue for constructing sequences in a breadth first manner
        LinkedList<LinkedWord> q;
        //Current word we are looking at
        LinkedWord currWord;
        //Length of the current word sequence
        int currDepth;
        //Boolean indicating whether a sequence was found
        boolean found;
        //Output string
        String output;

        //For loop for finding two sequence, once for singly joined and once
        // for doubly joined.
        for(int i = 0; i < 2; i++) {
            //Resets variables for each sequence
            q = new LinkedList<>();
            found = false;
            output = " " + lastWord;
            currDepth = 2;
            q.add(new LinkedWord(firstWord));

            //While queue has items and sequence length is less the dictionary
            // size (to stop potential infinte loops of joined words)
            while(q.peek() != null && currDepth != dict.size()) {
                currWord = q.remove();
                currDepth = currWord.getDepth();
                //Check if currWord is joined with lastWord
                if(joinWords(currWord.getWord(), lastWord, i)) {
                    //If joined, add words in sequence to output, by using the
                    //parent variable of currWord to traverse the sequence
                    //backwards
                    do {
                        output = " " + currWord.getWord() + output;
                        currWord = currWord.getParent();
                    } while (currWord != null);
                    System.out.println(currDepth + output);
                    //Indicate sequence found
                    found = true;
                    //Break while loop
                    currDepth = dict.size();
                } else {
                    //If not joined, find all word in dictionary that join
                    //to currWord and add them to the queue.
                    for(String word: dict) {
                        if(joinWords(currWord.getWord(), word, i)) {
                            LinkedWord newLink = new LinkedWord(word);
                            //Set currWord as parent of new words, so we
                            // can trace the sequence when outputing
                            newLink.setParent(currWord);
                            //Set new words depth to one more than currWord
                            newLink.setDepth(currWord.getDepth() + 1);
                            q.add(newLink);
                        }
                    }
                }
            }
            //if sequence was not found output 0
            if(!found) {
                System.out.println("0");
            }
        }
    }

    /**
     * Check if two words are join, either singly or doubly depending on input
     *
     * @param first left hand side of the join
     * @param second right hand side of the join
     * @param joinType 0 for singly joined, 1 for doubly
     * @return boolean true if join possible between given words, false other
     *                  wise.
     */
    public static boolean joinWords(String first,String second,int joinType) {
        return true;
    }
        
}
