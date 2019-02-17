/**
 * Provides a factory method for creating word search games. 
 *
 * @author Dargo Wang (yzw0060@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version Mar 27 2018
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    * @return return the Game class
    */
   public static WordSearchGame createGame() {
      Game game = new Game();     
      return game; 
   }

}
