import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import java.util.TreeSet;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;


public class DepthFirstWordSearchGame implements WordSearchGame {
   private TreeSet<String> lexicon;
   private char[][] board;
   private List<Integer> path;
   private boolean located;

   public void loadLexicon(String fileName) throws IllegalArgumentException {
      lexicon = new TreeSet<String>();
      if (fileName == null)
         throw new IllegalArgumentException();
      try {
         File file = new File(fileName);
         Scanner scanner = new Scanner(file);
         String word;
         while (scanner.hasNext()) {
            word = scanner.next();
            lexicon.add(word.trim().toLowerCase());
         }
      } catch (FileNotFoundException e) {
         throw new IllegalArgumentException();
      }
   }

   public boolean isValidWord(String wordToCheck) throws IllegalArgumentException {
      if (wordToCheck == null || lexicon == null)
         throw new IllegalArgumentException();
      return lexicon.contains(wordToCheck);
   }

   public boolean isValidPrefix(String prefixToCheck) throws IllegalArgumentException {
      if (prefixToCheck == null || lexicon == null)
         throw new IllegalArgumentException();
      String ceilWord = lexicon.ceiling(prefixToCheck);
      if (ceilWord == null)
         return false;
      return ceilWord.startsWith(prefixToCheck);
   }

   public void setBoard(String[] letterArray) throws IllegalArgumentException {
      if (letterArray == null)
         throw new IllegalArgumentException();
      int len = letterArray.length;
      int n = (int) Math.sqrt(len);
      if (n * n != len)
         throw new IllegalArgumentException();
      board = new char[n][n];
   
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            board[i][j] = letterArray[i*n+j].toLowerCase().charAt(0);   //.charAt(i * n + j);
         }
      }
   }

   public String getBoard() {
      int n = this.board.length;
      String output = "";
      for (int i = 0; i < n; i++) {
            //System.out.println(Arrays.toString(board[i]));
         for (int j = 0; j < n; j++) {
            output += (String.valueOf(board[i][j]).toUpperCase() + '\t');
         }
         output += '\n';
      }
      return output;
   }

    /*public List<Integer> isOnBoard(String wordToCheck) throws IllegalArgumentException {
        if (wordToCheck == null || lexicon == null)
            throw new IllegalArgumentException();
		wordToCheck = wordToCheck.toLowerCase();
        int m = wordToCheck.length();
        int n = board.length;
        if (m > n * n)
            return new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                path = new ArrayList<Integer>();
                boolean[][] visited = new boolean[n][n];
				int x = i; int y = j; //int k = 0;
				if (onBoardDfs(wordToCheck, x, y, 0, visited)) {
					path.add(x * n + y);
					//while (k < m) {
					for (int k = 1; k < m; k++) {
						for (int dx = -1; dx <= 1; dx++) {
							for (int dy = -1; dy <= 1; dy++) {
								if (!(dx == 0 && dy == 0)) {
									System.out.println(onBoardDfs(wordToCheck, x + dx, y + dy, k, visited));
									if (onBoardDfs(wordToCheck, x + dx, y + dy, k, visited)) {
										x = x + dx;
										y = y + dy;
										System.out.println(board[i][j] + ", " + x+", "+ y +", "+k);
										path.add(x * n + y);
									}
								}
								x = i; y = j;
							}
						}
					}
					return path;
				}
            }
        }
        return new ArrayList<Integer>();
    } 
	
    public boolean onBoardDfs(String wordToCheck, int i, int j, int k, boolean[][] visited) {
        int n = board.length;
        if (k == wordToCheck.length())
			return true;
        if (i < 0 || i >= n || j < 0 || j >= n)
			return false;
        if (visited[i][j])
			return false;
        if (board[i][j] != wordToCheck.charAt(k)) {
			return false;
		}
        visited[i][j] = true;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (!(dx == 0 && dy == 0)) {
					if (onBoardDfs(wordToCheck, i + dx, j + dy, k + 1, visited))
						return true;
				}
            }
        }
        visited[i][j] = false;
        //path.remove(path.size() - 1);
        return false;
    }*/

	
   public List<Integer> isOnBoard(String wordToCheck) throws IllegalArgumentException {
      if (wordToCheck == null || lexicon == null)
         throw new IllegalArgumentException();
      wordToCheck = wordToCheck.toLowerCase();
      int m = wordToCheck.length();
      int n = board.length;
      if (m > n * n)
         return new ArrayList<Integer>();
   
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            path = new ArrayList<Integer>();
            located = false;
            boolean[][] visited = new boolean[n][n];
         	//onBoardDfs(wordToCheck, i, j, "", visited);
            onBoardDfs(wordToCheck, i, j, 0, visited);
            if (path.size() == m) 
               return path;				
         }
      }
      return new ArrayList<Integer>();
   } 
	
	//private void onBoardDfs(String wordToCheck, int x, int y, String prefixSoFar, boolean[][] visited) {
   private void onBoardDfs(String wordToCheck, int x, int y, int k, boolean[][] visited) {
      int n = board.length;	
      if (x < 0 || x >= n || y < 0 || y >= n || located == true) 
      {
         return;}
      if (visited[x][y])
      {
         return;}
   	
   	//prefixSoFar += board[x][y];
   	//if (! wordToCheck.startsWith(prefixSoFar)) 
   	//	return;
      if (board[x][y] != wordToCheck.charAt(k))
         return;
   	
      path.add(x * n + y);
   	//if (prefixSoFar.equals(wordToCheck))
      if (k + 1 == wordToCheck.length())
         located = true;
   	
      visited[x][y] = true;
   	
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if (!((i == 0) && (j == 0))) {
            	//onBoardDfs(wordToCheck, x + i, y + j, prefixSoFar, visited);
               onBoardDfs(wordToCheck, x + i, y + j, k + 1, visited);
               if (located)
                  return;
            }
         }
      }
   
      visited[x][y] = false;
      if (! located) 
         path.remove(path.size() - 1);
   }

   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1 || lexicon == null)
         throw new IllegalArgumentException();
      int score = 0;
      for (String word : words) {
         int k = word.length();
         if (k >= minimumWordLength) {
            if (isValidWord(word) && (isOnBoard(word).size() == k))
               score += (k - minimumWordLength + 1);
         }
      }
      return score;
   }

   class Item {
      public int x, y;
      public String prefix;
   
      public Item(int row, int col, String prefix) {
         this.x = row;
         this.y = col;
         this.prefix = prefix;
      }
   }

   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      if (minimumWordLength < 1 || lexicon == null)
         throw new IllegalArgumentException();
      SortedSet<String> validWords = new TreeSet<String>();
      int n = board.length;
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            boolean[][] visited = new boolean[n][n];
            findWordsDfs(validWords, visited, new Item(i, j, ""), minimumWordLength);
         }
      }
      return validWords;
   }

   private void findWordsDfs(SortedSet<String> validWords, boolean[][] visited, Item item, int minimumWordLength) {
      int n = board.length;
      int x = item.x;
      int y = item.y;
   	//List<Integer> path = item.path;
      if (x < 0 || x >= n || y < 0 || y >= n)
         return;
      else if (visited[x][y])
         return;
   
      String newWord = (item.prefix + board[x][y]).toLowerCase();
      if (! isValidPrefix(newWord))        { 
         return; }
      if (newWord.length() >= minimumWordLength && isValidWord(newWord)) {
         validWords.add(newWord.toUpperCase());
      }
      visited[x][y] = true;
      findWordsDfs(validWords, visited, new Item(x - 1, y - 1, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x , y - 1, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x + 1, y - 1, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x - 1, y, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x + 1, y, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x - 1, y + 1, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x, y + 1, newWord), minimumWordLength);
      findWordsDfs(validWords, visited, new Item(x + 1, y + 1, newWord), minimumWordLength);
      visited[x][y] = false;
   }
	
}