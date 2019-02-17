import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
   
/**
 * Provides an implementation of the WordLadderGame interface. The lexicon
 * is stored as a HashSet of Strings.
 *
 * @author Dargo Wang (yzw0060@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2018-04-14
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   private HashSet<String> lexicon;

   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    * @param in input stream
    */
   public Doublets(InputStream in) {
      try {
         lexicon = new HashSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            /////////////////////////////////////////////////////////////
            // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
            /////////////////////////////////////////////////////////////
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
 /**
 * Returns the Hamming distance between two strings.    
 * @param str1 string1 to compare
 * @param str2 string2 to compare
 * @return return the Hamming distance
 */  
   public int getHammingDistance(String str1, String str2) {
      if (str1.length() != str2.length()) {
         return -1;
      }
      int count = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            count++;
         }
      }
      return count;
   }
   
 /**
 * Returns a minimum-length word ladder from start to end.
 * @param start word to start
 * @param end word to end
 * @return return the min ladder list
 */ 
   public List<String> getMinLadder(String start, String end) {
      Deque<Node> queue = new ArrayDeque<Node>();
      LinkedList<String> list = new LinkedList<String>();
      if (getHammingDistance(start, end) == -1) {
         return list;
      }
      /**if (!isWord(start) || !isWord(end)) {
         return list;
      }*/
      if (start.equals(end)) {
         list.add(start);
         return list;
      }
      HashSet<String> visited = new HashSet<String>();
      Node star = new Node(start, null);
      queue.addLast(star);
      visited.add(start);
      while (!queue.isEmpty()) {
         Node n = queue.removeFirst();
         String str = n.string;
         for (String strnb : getNeighbors(str)) {
            if (strnb.equals(end)) {
               list.add(strnb);
               while (n != null) {
                  list.addFirst(n.string);
                  n = n.pre;
               }
               return list;
            }
            if (!visited.contains(strnb)) {
               visited.add(strnb);
               queue.addLast(new Node(strnb, n));
            }
         }
      }
      return list;
   }

   
 /**
 * Returns all the words that have a Hamming distance of one relative to 
 * the given word.
 * @param word word to get neighbors
 * @return return the neighbor list of the word
 */ 
   public List<String> getNeighbors(String word) {
      List<String> nbList = new ArrayList<String>();
      String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
         "l", "m", "n", "o", "p", "q", "r", "s", 
         "t", "u", "v", "w", "x", "y", "z"};
      for (int i = 0; i < word.length(); i++) {
         for (String letter : letters) {
            String newStr = word.substring(0, i) + letter 
               + word.substring(i + 1, word.length());
            if (isWord(newStr) && !newStr.equals(word)) {
               nbList.add(newStr);
            }
         }
      }
      return nbList;
      
   }
   
 /**
 * Returns the total number of words in the current lexicon.
 * @return the word count in the lexicon
 */       
   public int getWordCount() {
      return lexicon.size();
   }
   
 /**
 * Checks to see if the given string is a word.
 * @param str string to check
 * @return return if the string is a word
 */       
   public boolean isWord(String str) {
      return lexicon.contains(str);
   }
   
 /**
 * Checks to see if the given sequence of strings is a valid word ladder.
 * @param sequence list of word ladder to check
 * @return if is a valid word ladder
 */       
   public boolean isWordLadder(List<String> sequence) {
      if (sequence.size() == 0) {
         return false;
      }
      if (sequence.size() == 1) {
         return true;
      }
      String prestr = sequence.get(1);
      for (String s : sequence) {
         if (!isWord(s)) {
            return false;
         }
         if (getHammingDistance(s, prestr) != 1) {
            return false;
         }
         prestr = s;
      }
      return true;
   }

 /**
 * nested class for bfsMemory.
 */    
   public class Node {
      private String string;
      private Node pre;
      boolean visited;
      /**
      * constructor to build a node.
      * @param str string of the node
      * @param n previous node of the node
      */
      public Node(String str, Node n) {
         string = str;
         pre = n;
         visited = false;
      }
   }

}

