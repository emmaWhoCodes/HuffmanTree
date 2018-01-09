// March 7, 2017
//
// This class creates a HuffmanNode, which is a single bit of data
// used within the HuffmanTree class. The HuffmanNode is used to
// contain the number of occurences of a particular letter in a
// file.
import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int frequency;
   public int charValue;
   public HuffmanNode left;
   public HuffmanNode right;
   
   // Pre: Takes in an int "charValue" which is the integer value
   //      of a particular letter. Takes in an int "frequency" which
   //      is the number of occurences of the letter.
   //
   // Post: Creates a HuffmanNode that contains the value of a letter
   //       and the number of times it occurs in a particular text.
   public HuffmanNode(int frequency, int charValue) {
      this.frequency = frequency;
      this.charValue = charValue;
   }
   
   // Pre: Takes in an int, "combinedFrequency" which is the sum of two
   //      letters' occurences. Takes in a HuffmanNode "left" and HuffmanNode
   //      "right".
   //
   // Post: Creates a HuffmanNode that contains the sum of both frequencies
   //       in "left" and "right". Makes "left" and "right" the children of
   //       this HuffmanNode. Does not contain a letterv value.
   public HuffmanNode(int combinedFrequency, HuffmanNode left, HuffmanNode right) {
      this.frequency = combinedFrequency;
      this.left = left;
      this.right = right;
   }
   
   // Post: Constructs an HuffmanNode that has a letter frequency of 0. Does
   //        not contain a letter value. Essentially used as a placeholder.
   public HuffmanNode() {
      this(0, null, null);
   }
   
   // Pre: Takes in a HuffmanNode "other" which is another HuffmanNode.
   //
   // Post: Returns an int that compares the two HuffmanNodes' letter
   //       frequency. If the int returned is < 0, this HuffmanNode is
   //       considered to be smaller. If the int returned is > 0, this
   //       HuffmanNode is considered to be larger. If the int returned = 0
   //       then the are considered to be equal.
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency;
   }
}
