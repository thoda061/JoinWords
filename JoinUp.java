import java.util.*;

/** COSC326 Etude 7
 * JoinUp.java - Class that takes two words and a dictionary and output the
 * shortest sequence of doubly and singly joined words from the dictionary which
 * are between the two given words.
 *
 * @author Daniel Thomson, ID: 5040702 & Will Shaw, ID: 8291780
 */
public class JoinUp {

    //First word in the sequence
    private static String firstWord;
    //Last word in the sequence
    private static String lastWord;
    //Dictionary of word used to fill the sequence
    private static HashMap<Character, ArrayList<String>> dict = new HashMap<>();

    /**
     * Main method which fill up the dictionary from standard in and sets
     * firstWord and lastWord from command line arguments. Also calls
     * findSequence to generate output.
     *
     * @param args command line arguments usd to set first and last word.
     */
    public static void main(String[] args) {
        firstWord = args[0];
        lastWord = args[1];

        // If the words are the same, then we immediately know they are not
        // singly joined, and the shortest double join is it and itself.
        if (firstWord.equals(lastWord)) {
            System.out.println("1 " + firstWord);
            System.out.println("1 " + firstWord);
            System.exit(0);
        }

        Scanner sc = new Scanner(System.in);
        //Fills dictionary, adding words in to ArrayLists based on first
        //letter and storing all the ArrayLists in a HashMap.
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            if (dict.containsKey(word.charAt(0))) {
                dict.get(word.charAt(0)).add(word);
            } else {
                ArrayList<String> wordGroup = new ArrayList<>();
                wordGroup.add(word);
                dict.put(word.charAt(0), wordGroup);
            }
        }
        findSequence();
        sc.close();
    }

    /**
     * Finds sequences of words that are either doubly linked or singly linked
     * between firstWord and lastWord, then prints them.
     */
    public static void findSequence() {
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
        //Stores words already added to the queue and used to stop stored word
        //being added to the queue again.
        HashSet<String> usedWords;

        //For loop for finding two sequence, once for singly joined and once
        // for doubly joined.
        for (int i = 0; i < 2; i++) {
            //Resets variables for each sequence
            q = new LinkedList<>();
            usedWords = new HashSet<>();
            found = false;
            output = " " + lastWord;
            currDepth = 2;
            q.add(new LinkedWord(firstWord));

            //While queue has items and sequence length is less the dictionary
            // size (to stop potential infinte loops of joined words)
            while (q.peek() != null && currDepth != dict.size()) {
                currWord = q.remove();
                currDepth = currWord.getDepth();
                //Check if currWord is joined with lastWord
                if (joinWords(currWord.getWord(), lastWord, i)) {
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

                    //Gets all unique chars of currWord
                    HashSet<Character> wordChars = new HashSet<>();
                    for (char c : currWord.getWord().toCharArray()) {
                        wordChars.add(c);
                    }
                    //Use chars of currWord to select specfic word groups from
                    //dict
                    for (Character c : wordChars) {
                        if (dict.containsKey(c)) {
                            ArrayList<String> wordGroup = dict.get(c);
                            //Tries to join each word in word group with
                            //currWord, if word not in usedWords
                            for (String word : wordGroup) {
                                if (!usedWords.contains(word)) {
                                    if (joinWords(currWord.getWord(), word, i)) {
                                        //Add word to usedWords
                                        usedWords.add(word);
                                        LinkedWord newLink = new LinkedWord(word);
                                        //Set currWord as parent of new words, so we
                                        // can trace the sequence when outputing
                                        newLink.setParent(currWord);
                                        //Set new words depth to one more than
                                        //currWord
                                        newLink.setDepth(currWord.getDepth() + 1);
                                        q.add(newLink);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //if sequence was not found output 0
            if (!found) {
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
    public static boolean joinWords(String first, String second, int joinType) {
        int length;
        int small = first.length() > second.length() ?
                               second.length() : first.length();
        if(joinType == 0) {
            length = first.length() > second.length() ?
                               second.length() : first.length();
            if(length%2 == 0) {
               length /= 2;
            } else {
               length = (length/2)+1;
            }
        } else {
            length = first.length() > second.length() ?
                                 first.length() : second.length();
            if(length%2 == 0) {
                length /= 2;
            } else {
                length = (length/2)+1;
            }
            if(length > small) {
                return false;
            }
        }

        for(int i = length; i <= small; i++) {
            String suf = first.substring(first.length() - i);
            String pre = second.substring(0, i);
            if(suf.equals(pre)) {
                return true;
            }
        }

        return false;

         
        /*int length = first.length() > second.length() ? 
                                second.length() : first.length();
        int match = length;
        if (length % 2 != 0) {
            match = length + 1;
        }

        int half = length / 2;
        if (length % 2 != 0) {
            half -= 1;
        }

        while (match > half) {
            String suf = first.substring(first.length() - (match - 1));
            String pre = second.substring(0, match - 1);

            if (suf.equals(pre)) {
                break;
            }
            match--;
        }

        if (joinType == 0) {
            if (match > first.length() / 2 && match > second.length() / 2 + second.length() % 2) {
                return true;
            }
            if (match > first.length() / 2 + first.length() % 2 && match > second.length() / 2) {
                return true;
            }
        }

        if (match > first.length() / 2 + first.length() % 2 
            && match > second.length() / 2 + second.length() % 2) {
            return true;
        }

        return false;*/
    }

}
