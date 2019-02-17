import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Extractor.java. Implements feature extraction for collinear points in
 * two dimensional data.
 *
 * @author  Dargo Wang (yzw0060@auburn.edu)
 * @author  Dean Hendrix (dh@auburn.edu)
 * @version 2018-02-27
 *
 */
public class Extractor {
   
   /** raw data: all (x,y) points from source data. */
   private Point[] points;
   
   /** lines identified from raw data. */
   private SortedSet<Line> lines;
  
   /**
    * Builds an extractor based on the points in the file named by filename.
    * @param filename name of file
    * @throws FileNotFoundException throw if file not found 
    */
   public Extractor(String filename) throws FileNotFoundException {
      File file = new File(filename);
      Scanner sc = new Scanner(file);
      int number = sc.nextInt();
      points = new Point[number];
      int count = 0;
      
      while (count < number || sc.hasNext()) {
         int x = sc.nextInt();
         int y = sc.nextInt();
         points[count] = new Point(x, y);
         count++;
      }
      
   }
  
   /**
    * Builds an extractor based on the points in the Collection named by pcoll. 
    *
    * THIS METHOD IS PROVIDED FOR YOU AND MUST NOT BE CHANGED.
    * @param pcoll collection to import
    */
   public Extractor(Collection<Point> pcoll) {
      points = pcoll.toArray(new Point[]{});
   }
  
   /**
    * Returns a sorted set of all line segments of exactly four collinear
    * points. Uses a brute-force combinatorial strategy. Returns an empty set
    * if there are no qualifying line segments.
    * @return return set of lines
    */
   public SortedSet<Line> getLinesBrute() {
      lines = new TreeSet<Line>();
      if (points.length >= 4) {
         for (int a = 0; a < points.length; a++) {
            for (int b = 1; b < points.length; b++) {
               for (int c = 2; c < points.length; c++) {
                  for (int d = 3; d < points.length; d++) {
                     Line line = new Line();
                     line.add(points[a]);
                     line.add(points[b]);
                     line.add(points[c]);
                     line.add(points[d]);
                     if (line.length() == 4) {
                        lines.add(line);
                     }
                  }
               }
            }
         }
      }
      return lines;
   }
  
   /**
    * Returns a sorted set of all line segments of at least four collinear
    * points. The line segments are maximal; that is, no sub-segments are
    * identified separately. A sort-and-scan strategy is used. Returns an empty
    * set if there are no qualifying line segments.
    * @return return set of lines
    */
   public SortedSet<Line> getLinesFast() {
      lines = new TreeSet<Line>();
      Point[] points2 = Arrays.copyOf(points, points.length);
      for (Point p : points) {
         Arrays.sort(points2, p.slopeOrder);
      
         for (int i = 0; i < points2.length; i++) {
            for (int j = points2.length - 1; j > i; j--) {
               if (p.slopeOrder.compare(points2[i], points2[j]) == 0) {
                  if (j - i > 1) {
                     Line line = new Line();
                     line.add(p);
                     for (int a = i; a <= j; a++) {
                        line.add(points2[a]);
                     }
                     lines.add(line);
                     i = j;
                  }
               }
            }
         }
      }
   
      return lines;
   }
   
}
