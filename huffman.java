import java.io.*;
import java.util.*;

public class huffman {
    public static int characterCount = 0; // total number of characters in the file

    // Node class for Huffman tree
    private static class Node implements Comparable<Node> {
        Node left;
        Node right;
        int freq;
        Character c; // null for internal nodes

        // Constructor for leaf nodes
        public Node(Character c, int freq) {
            this.left = null;
            this.right = null;
            this.freq = freq;
            this.c = c;
        }

        // Constructor for internal nodes
        public Node(Node left, Node right, int freq) {
            this.left = left;
            this.right = right;
            this.freq = freq;
            this.c = null;
        }

        // Compare nodes by frequency (for priority queue)
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }

    // Count frequency of each character in a file
    public static TreeMap<Character, Integer> getFreq(String filePath) {
        HashMap<Character, Integer> values = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String fileLine = br.readLine();
            while (fileLine != null) {
                for (int i = 0; i < fileLine.length(); i++) {
                    char c = fileLine.charAt(i);
                    values.put(c, values.getOrDefault(c, 0) + 1);
                    characterCount++;
                }
                if (br.ready()) { // count newline characters
                    values.put('\n', values.getOrDefault('\n', 0) + 1);
                    characterCount++;
                }
                fileLine = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return new TreeMap<>(values); // return sorted map
    }

    // Build Huffman tree from character frequencies
    public static Node HuffmanTreeBuilder(TreeMap<Character, Integer> freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left, right, left.freq + right.freq);
            pq.add(parent);
        }

        return pq.poll(); // root of the Huffman tree
    }

    // Generate Huffman codes recursively
    public static void generateCodes(Node node, String code, TreeMap<Character, String> codes) {
        if (node.c != null) {
            codes.put(node.c, code); // leaf node
            return;
        }
        generateCodes(node.left, code + "0", codes);
        generateCodes(node.right, code + "1", codes);
    }

    // Print table of characters, frequencies, and Huffman codes
    public static void printTable(TreeMap<Character, Integer> values) {
        System.out.print("Ascii Character |   Frequency   | Prefix Code\n");
        System.out.print("----------------|---------------|------------\n");

        TreeMap<Character, String> codes = new TreeMap<>();
        Node root = HuffmanTreeBuilder(values);
        generateCodes(root, "", codes);

        for (Character c : values.keySet()) {
            if (c == '\n') {
                System.out.println("\t\t\t'\\n'\t|\t" + values.get(c) + "\t\t|\t" + codes.get(c));
            } else {
                System.out.println("\t\t\t'" + c + "'\t|\t" + values.get(c) + "\t\t|\t" + codes.get(c));
            }
        }

        // Calculate file size before and after Huffman coding
        System.out.print("\nFile size before Huffman: " + characterCount * 8 + " bits\n");
        int bitSum = 0;
        for (Character c : values.keySet()) {
            bitSum += values.get(c) * codes.get(c).length();
        }
        System.out.print("File size after Huffman:  " + bitSum + " bits");
    }

    public static void main(String[] args) {
        TreeMap<Character, Integer> frequencies = getFreq("ascii.txt");
        printTable(frequencies);
    }
}
