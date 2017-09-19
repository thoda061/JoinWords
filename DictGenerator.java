import java.util.*;

public class DictGenerator {

    public static void main(String[]args) {
        ArrayList<String> words = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < Integer.parseInt(args[0]); i++) {
            StringBuilder sb = new StringBuilder();
            int length = 1 + rand.nextInt(15);
            for(int j = 0; j < length; j++) {
                char character = (char)(97 + rand.nextInt(26));
                sb.append(character);
            }
            if(words.contains(sb.toString())) {
                i--;
            } else {
                words.add(sb.toString());
            }
        }
        for(String word: words) {
            System.out.println(word);
        }
    }

}
                
