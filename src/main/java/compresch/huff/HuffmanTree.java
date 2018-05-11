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

import java.util.PriorityQueue;

public class HuffmanTree {

    private PriorityQueue<HuffmanNode> auxHeap;
    private HuffmanNode root;
    private int[] codeLengths;

    /**
     * Construct new empty Huffman tree.
     */
    public HuffmanTree() {
        this.auxHeap = new PriorityQueue<>();
        this.root = null;
        this.codeLengths = new int[257];
    }

    /**
     * Construct a Huffman tree from frequency table.
     * @param freqTbl Input file byte frequencies.
     */
    public void buildHuffmanTree(HuffmanFrequencyTable freqTbl) {
        initAuxHeap(freqTbl);
        assembleHuffmanTree();
        populateCodeLengths();
        HuffmanCanonicalCode canonCode = new HuffmanCanonicalCode();
        canonCode.buildCanonCode(this.codeLengths);
        this.root = canonCode.getCanonRoot();
    }

    /**
     * Construct a Huffman tree from codeword lengths.
     * @param codeLengths array of Huffman codeword lengths.
     */
    public void buildHuffmanTree(int[] codeLengths) {
        HuffmanCanonicalCode canonCode = new HuffmanCanonicalCode();
        canonCode.buildCanonCode(codeLengths);
        this.root = canonCode.getCanonRoot();
    }

    /**
     * Get root node of Huffman tree.
     * @return root node of Huffman tree.
     */
    public HuffmanNode getRoot() {
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
                this.auxHeap.add(new HuffmanNode(i, frequency));
            }
        }
    }

    /**
     * Assemble Huffman tree from least needed leaves up to the root.
     */
    private void assembleHuffmanTree() {
        while (this.auxHeap.size() > 1) {
            HuffmanNode left = auxHeap.poll();
            HuffmanNode right = auxHeap.poll();
            auxHeap.add(new HuffmanNode(left, right));
        }
        this.root = auxHeap.poll();
    }

    private void populateCodeLengths() {
        dfsTraversal(this.root, 0);
    }

    private void dfsTraversal(HuffmanNode node, int depth) {
        if (node.isLeaf()) {
            codeLengths[node.getSymbol()] = depth;
            return;
        }
        dfsTraversal(node.getLeft(), depth + 1);
        dfsTraversal(node.getRight(), depth + 1);
    }

}
