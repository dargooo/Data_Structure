
/**
 * ExampleGameClient.java
 * A sample client for the assignment handout.
 *
 * @author      Dean Hendrix (dh@auburn.edu)
 * @version     2018-03-22
 *
 */
public class ExampleGameClient {

   /** Drives execution. */
   public static void main(String[] args) {
      WordSearchGame game = WordSearchGameFactory.createGame();
      game.loadLexicon("wordfiles/words.txt");
      game.setBoard(new String[]{"TIGER","XXX","ACT","XX"});
      System.out.print("TIGER is on the board at the following positions: ");
      System.out.println(game.isOnBoard("TIGER"));
   
      System.out.print("LENT is on the board at the following positions: ");
      System.out.println(game.isOnBoard("LENT"));
   
      System.out.print("POPE is not on the board: ");
      System.out.println(game.isOnBoard("POPE"));
      
      System.out.print("BENTHAL is on the board at the following positions: ");
      System.out.println(game.isOnBoard("BENTHAL"));
      
      System.out.print("EELPOT is on the board at the following positions: ");
      System.out.println(game.isOnBoard("EELPOT"));
      System.out.println("All words of length 6 or more: ");
      System.out.println(game.getAllValidWords(6));
      System.out.println(game.getBoard());
   }
   
}

/*

RUNTIME OUTPUT:

LENT is on the board at the following positions: [5, 6, 9, 13]
POPE is not on the board: []
All words of length 6 or more:
[ALEPOT, BENTHAL, PELEAN, TOECAP]

 */
