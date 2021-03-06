/*
 * MIT License
 *
 * Copyright (c) 2018 Aki Utoslahti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package compresch.huff;

import compresch.ds.DynamicArray;
import compresch.ds.HuffmanHeap;

/**
 * Utility class used both for encoding and decoding with Huffman coding.
 */
public class HuffmanTree {

    private HuffmanHeap auxHeap;
    private HuffmanNode root;
    private int[] codeLengths;

    /**
     * Construct new empty Huffman tree.
     */
    HuffmanTree() {
        this.auxHeap = new HuffmanHeap();
        this.root = null;
        this.codeLengths = new int[257];
    }

    /**
     * Construct a Huffman tree from frequency table.
     * @param freqTbl Input file byte frequencies.
     */
    void buildHuffmanTree(HuffmanFrequencyTable freqTbl) {
        initAuxHeap(freqTbl);
        assembleHuffmanTree();
        populateCodeLengths();
        buildCanonicalTree();
    }

    /**
     * Construct a Huffman tree from codeword lengths.
     * @param codeLengths array of Huffman codeword lengths.
     */
    void buildHuffmanTree(int[] codeLengths) {
        this.codeLengths = codeLengths;
        buildCanonicalTree();
    }

    HuffmanNode getRoot() {
        return this.root;
    }

    /**
     * Add leaf nodes to auxiliary heap for construction of Huffman tree.
     * @param freqTbl Input file byte frequencies.
     */
    private void initAuxHeap(HuffmanFrequencyTable freqTbl) {
        for (int i = 0; i < 257; i++) {
            int frequency = freqTbl.getFrequency(i);
            if (frequency > 0) {
                this.auxHeap.push(new HuffmanNode(i, frequency));
            }
        }
    }

    /**
     * Assemble Huffman tree from least needed leaves up to the root.
     */
    private void assembleHuffmanTree() {
        while (this.auxHeap.size() > 1) {
            HuffmanNode left = auxHeap.peek();
            auxHeap.pop();
            HuffmanNode right = auxHeap.peek();
            auxHeap.pop();
            auxHeap.push(new HuffmanNode(left, right));
        }
        this.root = auxHeap.peek();
        auxHeap.pop();
    }

    /**
     * Traverses Huffman tree and populates array containing lengths of codewords.
     */
    private void populateCodeLengths() {
        dfsTraversal(this.root, 0);
    }

    /**
     * Recursive helper method to traverse Huffman tree.
     * @param node  HuffmanNode to continue traversal.
     * @param depth Current node distance from zero depth (root node).
     */
    private void dfsTraversal(HuffmanNode node, int depth) {
        if (node.isLeaf()) {
            codeLengths[node.getSymbol()] = depth;
            return;
        }
        dfsTraversal(node.getLeft(), depth + 1);
        dfsTraversal(node.getRight(), depth + 1);
    }

    /**
     * Builds canonical Huffman tree from array of codeword lengths.
     * Building is done from the lowest depth up to the root.
     */
    private void buildCanonicalTree() {
        DynamicArray<HuffmanNode> nodes = new DynamicArray<>();
        for (int i = maxCodeLength(); i >= 0; i--) {
            DynamicArray<HuffmanNode> newNodes = new DynamicArray<>();
            if (i > 0) {
                for (int j = 0; j < codeLengths.length; j++) {
                    if (codeLengths[j] == i) {
                        newNodes.push(new HuffmanNode(j));
                    }
                }
            }
            for (int j = 0; j < nodes.size(); j += 2) {
                newNodes.push(new HuffmanNode(nodes.at(j), nodes.at(j + 1)));
            }
            nodes = newNodes;
        }
        this.root = nodes.at(0);
    }

    /**
     * Finds the maximum length of codeword from array of lengths.
     * @return The length of the longest codeword.
     */
    private int maxCodeLength() {
        int max = 0;
        for (int codeLength : this.codeLengths) {
            if (codeLength > max) {
                max = codeLength;
            }
        }
        return max;
    }
}
