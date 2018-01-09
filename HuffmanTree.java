
// March 6, 2017
//
// The HuffmanTree class is used to encode/decode text using
// an algorithm developed by David Huffman. The algorithm allows the
// most common characters in a text to be more easily available,
// while characters that are not in the text to be disregarded altogether.
// The Huffman algorithm is used to decompress data by changing text into
// binary format. This class can create the Huffman algorithm, and also
// decode the algorithm as well.

import java.util.*;
import java.io.*;

public class HuffmanTree  {
   private Queue<HuffmanNode> saveWords;
   private HuffmanNode overallRoot;
   
   // Pre: Takes in an array of ints, "count" which contains all
   //      possible characters and how many times they occur
   //      in the file.
   //
   // Post: Stores only the characters that occur in the file.
   //       Disregards any characters that do not occur.
   public HuffmanTree(int[] count) {
      saveWords = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) {
            saveWords.add(new HuffmanNode(count[i], i));
         }
      }
      saveWords.add(new HuffmanNode(1, count.length));
      buildTree();
   }
   
   // Pre: Takes in a Scanner, "input" that reads the Huffman tree
   //      (the encrypted text) from a file. The file should contain
   //      both a integer value of a character, indicating a letter in the
   //      original text, and another number composed of 0's and 1's
   //      that indicates the position of the letter in the Huffman Tree.
   //
   //
   // Post: Constructs a Huffman Tree using the given information in the
   //       text file.
   public HuffmanTree(Scanner input) {
      overallRoot = new HuffmanNode();
      HuffmanNode temp = new HuffmanNode();
      while (input.hasNextLine()) {
         int charValue = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         temp = buildTree(code, charValue, overallRoot);
      }
   }
   
   // Pre: Takes in a BitInputStream, "input", that reads the next bit
   //      of information from a text file. Takes in a PrintStream, "output",
   //      which is used to write to a text file. Takes in an int, "eof"
   //      which indicates when the file ends.
   //
   // Post: Decodes the message by finding a letter's integer value that
   //       corresponds to the information passed in by "input". Prints the
   //       letter to an output file using "output".  Stops printing once
   //       "eof" is found in the file.
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int stop = overallRoot.charValue;
      HuffmanNode temp = overallRoot;
      while (stop != eof) {
         if (temp.right == null && temp.left == null) {
            if (temp.charValue != eof) {
               output.write(temp.charValue);
               temp = overallRoot;
            } else {
               stop = temp.charValue;
            }
         } else {
            int bit = input.readBit();
            if (bit == 0) {
               temp = temp.left;
            } else {
               temp = temp.right;
            }
         }
      }
   }
   
   // Pre: Takes in a String, "code" which is the binary code, that is created
   //      by the position of a letter in the Huffman tree. Takes in a int,
   //      "charValue" which is the numerical value of the letter. Takes in a
   //       HuffmanNode, "root", which is a reference to the Huffman tree.
   //       Each "code" is unique to its matching "charValue".
   //
   // Post: Builds a Huffman tree from the information given in a text file.
   //       Adds in "charValue" at the place specified by "code". Returns a
   //       HuffmanNode, which is the reference to the newly constructed
   //       part of the Huffman tree.
   private HuffmanNode buildTree(String code, int charValue, HuffmanNode root) {
      if (code.equals("")) {
         if (root.right == null && root.left == null) {
            return new HuffmanNode(0, charValue);
         }
      } else {
         if (code.startsWith("0")) {
            if (root.left == null) {
               root.left = buildTree(code.substring(1), charValue, new HuffmanNode());
            } else {
               return buildTree(code.substring(1), charValue, root.left);
            }
         } else {
            if (root.right == null) {
               root.right = buildTree(code.substring(1), charValue, new HuffmanNode());
            } else {
               return buildTree(code.substring(1), charValue, root.right);
            }
         }
      }
      return root;
   }
   
   // Post: Builds a Huffman tree using the letters and their frequencies.
   //       The tree is organized by the frequency of the letter. The letters
   //       with a higher frequency are placed higher up in the tree, while
   //       the letters that occur less are lower in the tree.
   private void buildTree() {
      if (saveWords.size() > 1) {
         HuffmanNode min = saveWords.remove();
         HuffmanNode nextMin = saveWords.remove();
         saveWords.add(new HuffmanNode(min.frequency + nextMin.frequency, min, nextMin));
         buildTree();
      }
   }
   
   // Pre: Takes in a PrintStream, "output", which will be used to print
   //      to a specified text file.
   // Post: Constructs a code that indicate the position/path of a particular
   //       letter in the Huffman tree. Writes that letter's integer value and
   //       the positional code to "output".
   public void write(PrintStream output) {
      String code = "";
      write(output, code, saveWords.element());
   }
   
   // Pre: Takes in a PrintStream, "output", which is where the information
   //      will be printed. Takes in a String, "code", which is used to create
   //      the a series of 1's and 0's to indicate a letter's position
   //      in the Huffman tree. Takes in a HuffmanNode "root", which is
   //      a reference to the given position in the tree.
   //
   // Post: Creates a code that indicates the position of a letter in the
   //       Huffman Node. Prints the letter's integer value to "output".
   //       Prints the newly created code, "code", to "output".
   private void write(PrintStream output, String code, HuffmanNode root) {
      if (root.right == null && root.left == null) {
         output.println(root.charValue);
         output.println(code);
      } else {
         write(output, code + "0", root.left);
         write(output, code + "1", root.right);
      }
   }
}
