import java.io.File;
//import java.io.IOException;

/**
 * TextGenerator.java. Creates an order K Markov model of the supplied source
 * text, and then outputs M characters generated according to the model.
 *
 * @author     Dargo Wang (yzw0060@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class TextGenerator {

   /** Drives execution. 
   * @param args Command line argument
   */
   public static void main(String[] args) {
   
      if (args.length < 3) {
         System.out.println("Usage: java TextGenerator k length input");
         return;
      }
   
      // No error checking! You may want some, but it's not necessary.
      int K = Integer.parseInt(args[0]);
      int M = Integer.parseInt(args[1]);
      if ((K < 0) || (M < 0)) {
         System.out.println("Error: Both K and M must be non-negative.");
         return;
      }
   
      File text;
      try {
         text = new File(args[2]);
         if (!text.canRead()) {
            throw new Exception();
         }
      }
      catch (Exception e) {
         System.out.println("Error: Could not open " + args[2] + ".");
         return;
      }
   
   
      // instantiate a MarkovModel with the supplied parameters and
      // generate sample output text ...
      MarkovModel mm = new MarkovModel(K, text);
      int currentLength = K;
      String kgram = mm.getFirstKgram();
      String output = kgram;
      while (currentLength < M) {
         if (kgram.equals(mm.getLastKgram())) {
            kgram = mm.getRandomKgram();
            output = output + kgram;
            currentLength += K;
         }
         else {
            char next = mm.getNextChar(kgram);
            output = output + next;
            currentLength++;
            kgram = kgram.substring(1, K) + next;
         }
      }
      if (output.length() > M) {
         output = output.substring(0, M);
      }
      System.out.print(output);
   }
}
