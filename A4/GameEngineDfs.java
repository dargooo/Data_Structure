import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Class implements WordSearchGame interface.
 * My game engine.
 *
 * @author      Junyao Yang (jzy0040@auburn.edu)
 * @author      Dean Hendrix (dh@auburn.edu)
 * @version     2018-03-26
 *
 */
public class GameEngineDfs implements WordSearchGame {
   
   //instance variable
   private String[][] board = {{"E", "E", "C", "A"},
      {"A", "L", "E", "P"},
      {"H", "N", "B", "O"}, 
      {"Q", "T", "T", "Y"}};
   private TreeSet<String> lexicon = new TreeSet<String>();
   private boolean[][] visited = new boolean[board.length][board[0].length];
   private int order;
   private int[] neighbors;
   private String wordOnCheck;
   private final int maxNeighbors = 8;
   
   /**
    * This is a constructor taking no parameter.
    *
    */
   public GameEngineDfs() {
   
   }
   
   // methods
   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws FileNotFoundException if file cannot be found.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) 
      throws IllegalArgumentException {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         Scanner scan = new Scanner(new File(fileName));
         String word = "";
         while (scan.hasNext()) {
            word = scan.next();
            lexicon.add(word.toUpperCase());
         }
      }
      catch (FileNotFoundException error) {
         throw new IllegalArgumentException();
      }
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck)
      throws IllegalArgumentException {
      if (lexicon.isEmpty() || wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      return lexicon.contains(wordToCheck);
   }
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) 
      throws IllegalArgumentException {
      if (lexicon.isEmpty() || prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      int lengthPrefix = prefixToCheck.length();
      boolean prefixCheck = false;
      String ceiling  = lexicon.ceiling(prefixToCheck);
      
      if (ceiling == null) {
         return false;
      }
      else {
         return ceiling.startsWith(prefixToCheck);
      }
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of
    *     board position (0,0) and index length-1 stores the contents of board 
    *     position (N-1,N-1). Note that the board must be square and that the 
    *     strings inside may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) throws 
      IllegalArgumentException {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      int length = letterArray.length;
      double sqrt = Math.sqrt(length);
      int sq = (int) sqrt;
      if (Math.pow(sqrt, 2) != Math.pow(sq, 2)) {
         throw new IllegalArgumentException();
      }
      
      else {
         if (length <= 1) {
            board = new String[1][1];
            board[0][0] = letterArray[0].toUpperCase();
         }
         else {
            board = new String[sq][sq];
            for (int i = 0; i < sq; i++) {
               for (int j = 0; j < sq; j++) {
                  board[i][j] = letterArray[i * sq + j].toUpperCase();
               }
            }
            visited = new boolean[board.length][board[0].length];
         }
      }   
   }

   /**
    * Creates a String representation of the board, suitable for printing to
    *    standard out. Note that this method can always be called since
    *    implementing classes should have a default board.
    * @return elementOnBoard string represents the board.
    */
   public String getBoard() {
      int i = 0;
      String elementOnBoard = "";
      for (String[] rowElement : board) {
         elementOnBoard += "\n";
         for (String element : rowElement) {
            elementOnBoard = elementOnBoard + i++ + "." + element + " ";
         }
      }
      
      for (boolean[] rowVisited : visited) {
         elementOnBoard += "\n";
         for (boolean elementVisited : rowVisited) {
            elementOnBoard = elementOnBoard + elementVisited + " ";
         }
      }
      return elementOnBoard;
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength)
      throws IllegalArgumentException, IllegalStateException {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty()) {
         throw new IllegalStateException();
      }
      SortedSet<String> allValidWords = new TreeSet<String>();
      for (String nextWord : lexicon) {
         if (nextWord.length() >= minimumWordLength
            && isOnBoard(nextWord).size() > 0) {
            allValidWords.add(nextWord);  
         }
      }
      
      return allValidWords;
   }
   
   /**
    * Computes the cummulative score for the scorable words in the given set.
    *     To be scorable, a word must have at least the minimum 
    *     number characters, (2) be in the lexicon, and (3) be on the 
    *     board. Each scorable word is awarded one point for the minimum  
    *     number of characters, and one point for
    *     each character beyond the minimum number.
    *
    * @param words The set of words that are to be scored.
    * @param minimumWordLength The minimum number of characters required
    * @return the cummulative score of all scorable words in the set
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength)
      throws IllegalArgumentException, IllegalStateException {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty()) {
         throw new IllegalStateException();
      }
      int score = 0;
      
      for (String nextWord : words) {
         if (nextWord.length() >= minimumWordLength
            && lexicon.contains(nextWord)
            && isOnBoard(nextWord).size() > 0) {
            
            score += nextWord.length() - minimumWordLength + 1;
         }
      }
      return score;
   }
   
   /**
    * Determines if the given word is in on the game board. If so, it returns
    *     the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with the 
    *     path that makes up the word on the game board. If word is not on 
    *     the game board, return an empty list. Positions on the board are 
    *     numbered from zero top to bottom, left to right (i.e., in 
    *     row-major order). Thus, on an NxN board, the upper left position 
    *     is numbered 0 and the lower right position is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck)
      throws IllegalArgumentException, IllegalStateException {
      
      wordOnCheck = wordToCheck.toUpperCase();   
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (lexicon.isEmpty()) {
         throw new IllegalStateException();
      }
      
      if (wordToCheck.length() > board.length * board[0].length) {
         return new ArrayList<Integer>();
      }
      
      wordToCheck = wordToCheck.toUpperCase();
      ArrayList<Integer> path = new ArrayList<Integer>();
      ArrayList<Integer> wordPath = new ArrayList<Integer>();
      
   //       if (board.length <= 1) {
   //          if (board[0][0].equals(wordToCheck)) {
   //             wordPath.add(0);
   //             return wordPath;
   //          }
   //          else {
   //             return new ArrayList<Integer>();
   //          }
   //       }
   //       else {
      Position[] startPositionCandidate = 
            new Position[board.length * board.length];
         
      int count = 0;   
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board.length; j++) {
            //System.out.println("1");
            if (wordToCheck.startsWith(board[i][j])) {
               Position p = new Position(i, j);
               startPositionCandidate[count++] = p;
            }
         }
      }
         
      Position[] startPosition =
            Arrays.copyOf(startPositionCandidate, count);
   
      for (Position startP : startPosition) {
         int intPos = startP.x * board.length + startP.y;
         visited[startP.x][startP.y] = true;      
         path.add(intPos);
         wordPath = nextPos(startP, board[startP.x][startP.y], path);
         visited[startP.x][startP.y] = false;
         path.remove(path.size() - 1);
         if (!wordPath.isEmpty()) {
            return wordPath;
         }
      }
      return wordPath;
      //}
   }
   
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param curPos - current position.
    * @param curString - substring matched with target.
    * @param path - path on the board to generate the target.
    * @return p - path found and returned.
    */
   public ArrayList<Integer> nextPos(Position curPos, 
      String curString, ArrayList<Integer> path) {
      if (curString.equals(wordOnCheck)) {
         ArrayList<Integer> p = new ArrayList<Integer>();
         for (Integer i : path) {
            p.add(i);  
         }
         return p;
      }
      ArrayList<Integer> ret = new ArrayList<Integer>();
      Position[] neigh = curPos.neighbors(curPos);
      
      for (int i = 0; i < neigh.length; i++) {
         int intPos = neigh[i].x * board[0].length + neigh[i].y;
         String newString = curString + board[neigh[i].x][neigh[i].y];
         
         if ((!visited[neigh[i].x][neigh[i].y]) 
            && (wordOnCheck.startsWith(newString))) {
            visited[neigh[i].x][neigh[i].y] = true;
            path.add(intPos);
            ret = nextPos(neigh[i], newString, path);
            visited[neigh[i].x][neigh[i].y] = false;
            path.remove(path.size() - 1);
            if (!ret.isEmpty()) {
               return ret;
            }   
         }
      }
      return ret;
   }
   
   
   /**
    * Defines a position on the board.
    * Defines methods associating with this pisiton.
    */
   public class Position {
      private int x;
      private int y;
      
      /**
       * Is this position valid in the search area?
       * @param xIn - x axis of an instance.
       * @param yIn - y axis of an instance. 
       */
      public Position(int xIn, int yIn) {
         x = xIn;
         y = yIn;
      }
      
      /**
       * Is this position valid in the search area?
       * @param p take a position to check.
       * @return a boolean result.
       */
      public boolean isValid(Position p) {
         return (p.x >= 0) && (p.x < visited.length) 
            && (p.y >= 0) && (p.y < visited[0].length);
      }
      
      /**
       * Has this valid position been visited?
       * @param p - take a position to check whether
       *     it has been visited or not.
       * @return a boolean result indicate the result. 
       */
      public boolean isVisited(Position p) {
         return visited[p.x][p.y];
      }
   
      /**
       * Mark this valid position as having been visited.
       * @param p - take a position to make it visited.
       */
      public void visit(Position p) {
         visited[p.x][p.y] = true;
      }
      
      /**
       * Find valid neighbors of p.
       * p is the current position on the board.
       * @param p - current position.
       * @return  - return the neighbors position.
       */
      public Position[] neighbors(Position p) {
         Position[] nbrs = new Position[maxNeighbors];
         int count = 0;
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!(i == 0 && j == 0)) {
                  Position neighborPosition = new Position(p.x + i, p.y + j);
                  if (isValid(neighborPosition)) {
                     nbrs[count++] = neighborPosition;
                  }
               }
            }
         }
         return Arrays.copyOf(nbrs, count);
      }
      
      public String toString() {
         return "(" + x + y + ")";
      }
   }
}