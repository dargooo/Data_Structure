import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   DARGO WANG (yzw0060@auburn.edu)
* @author   Dean Hendrix (dh@auburn.edu)
* @version  2018-01-15
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


/**
* Selects the minimum value from the array a. 
* @param a array to search in
* @return return the minimum
*/
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int mini = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] < mini) {
            mini = a[i];
         }
      }
      return mini;
   }

 /**
 * Selects the maximum value from the array a. 
 * @param a array to search in
 * @return return the maximum
 */
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int maxi = a[0];
      for (int i = 0; i < a.length; i++) {
         if (a[i] > maxi) {
            maxi = a[i];
         }
      }
      return maxi;
   }

/**
* Selects the kth minimum value from the array a.
* @param a array to search in
* @param k number of smallest 
* @return return the kth minimum
*/
   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int[] b = Arrays.copyOf(a, a.length);
      int dup = 0;
      Arrays.sort(b);
      for (int i = 0; i < b.length - 1; i++) {
         if (b[i] == b[i + 1]) {
            b[i] = 0;
            dup++;
         }
      }
      int[] c = new int[b.length - dup];
      int n = 0;
      for (int j : b) {
         if (j != 0) {
            c[n] = j;
            n++;
         }
      }
      if (k < 1 || k > c.length) {
         throw new IllegalArgumentException();
      }
      return c[k - 1];
   }
   
/**
* Selects the kth maximum value from the array a.
* @param a array to search in
* @param k number of largest
* @return return the kth maximum
*/
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int[] b = Arrays.copyOf(a, a.length);
      int dup = 0;
      Arrays.sort(b);
      for (int i = 0; i < b.length - 1; i++) {
         if (b[i] == b[i + 1]) {
            b[i] = 0;
            dup++;
         }
      }
      int[] c = new int[b.length - dup];
      int n = 0;
      for (int j : b) {
         if (j != 0) {
            c[n] = j;
            n++;
         }
      }
      if (k < 1 || k > c.length) {
         throw new IllegalArgumentException();
      }  
      return c[c.length - k];
      
   }

   
/**
* Returns an array containing all the values in a in the range.
* @param a array to search in
* @param low lowest end in range
* @param high highest end in range
* @return return the new array
*/  
   public static int[] range(int[] a, int low, int high) { 
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int n = 0;
      int[] b = new int[a.length];
      for (int i : a) {
         if (i >= low && i <= high) {
            b[n] = i;
            n++;
         }
      }
      int[] c = Arrays.copyOfRange(b, 0, n);
      return c;
   }


/**
* Returns the smallest value in a that is greater than 
* or equal to the given key. 
* @param a array to search in
* @param key number to compare
* @return return the ceiling number
*/ 
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int ceiling;
      int[] b = range(a, key, 99999);
      ceiling = min(b);
      return ceiling;
   } 


/**
* Returns the largest value in a that is greater than 
* or equal to the given key. 
* @param a array to search in
* @param key number to compare
* @return return the floor number
*/  
   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int floor;
      int[] b = range(a, -99999, key);
      floor = max(b);
      return floor;
   }
}
