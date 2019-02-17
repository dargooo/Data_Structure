import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Dargo Wang (yzw0060@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class MarkovModel { 

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;
   private int order;
   private String txt;
   private String[] keyArray;
   private String last;

   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    * @param K kgram
    * @param sourceText file as source of text
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    * @param K kgram
    * @param sourceText String as source of text
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      order = K;
      txt = sourceText;
      for (int i = 0; i < sourceText.length() - K + 1; i++) {
         String kgram = sourceText.substring(i, i + K);
         String next;
         if (i + K == sourceText.length()) {
            next = null;
         }
         else {
            next = sourceText.substring(i + K, i + K + 1);
         }
         if (model.containsKey(kgram)) {
            String newValue = model.get(kgram) + next;
            model.replace(kgram, newValue);
         }
         else {
            model.put(kgram, next);
         }
      }
      keyArray = model.keySet().toArray(new String[0]);
   }


   /** Returns the first kgram found in the source text. 
   * @return return first kgram
   */
   public String getFirstKgram() {
      return txt.substring(0, order);
   }
   /** Returns the last kgram found in the source text. 
   * @return return last kgram
   */
   public String getLastKgram() {
      return txt.substring(txt.length() - order, txt.length());
   }


   /** Returns a kgram chosen at random from the source text.
   * @return return random kgram
    */
   public String getRandomKgram() {
      int size = model.size();
      int index = new Random().nextInt(size);
      return keyArray[index];
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    * @return return set of all kgrams
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    * @param kgram current kgram to accord to 
    * @return return next char
    */
   public char getNextChar(String kgram) {
      String value = model.get(kgram);
      int length = value.length();
      int index = new Random().nextInt(length);
      return value.charAt(index);
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
