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

import java.util.Objects;

/**
 * Node class for HuffmanTree.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private HuffmanNode left;
    private HuffmanNode right;
    private int symbol;
    private int frequency;

    /**
     * Constructor for leaf node containing a symbol and its frequency.
     * @param symbol    byte as unsigned integer value, range 0-255. 256 reserved for pseudo-EOF.
     * @param frequency frequency of symbol in input file.
     * @throws IllegalArgumentException if symbol value is out of bounds.
     */
    public HuffmanNode(int symbol, int frequency) {
        this.left = null;
        this.right = null;
        if (symbol < 0 || symbol > 256) {
            throw new IllegalArgumentException();
        }
        this.symbol = symbol;
        this.frequency = frequency;
    }

    /**
     * Constructor for leaf node containing only symbol. Targeted for building canonical code from
     * initial code lengths.
     * @param symbol byte as unsigned integer value, range 0-255. 256 reserved for pseudo-EOF.
     * @throws IllegalArgumentException if symbol value is out of bounds.
     */
    public HuffmanNode(int symbol) {
        this.left = null;
        this.right = null;
        if (symbol < 0 || symbol > 256) {
            throw new IllegalArgumentException();
        }
        this.symbol = symbol;
        this.frequency = -1;
    }

    /**
     * Constructor for internal node, whose 'symbol' and frequency are sums of those in child nodes.
     * @param left  left child node.
     * @param right right child node.
     * @throws NullPointerException if one of arguments is null.
     */
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        Objects.requireNonNull(left);
        Objects.requireNonNull(right);
        this.left = left;
        this.right = right;
        this.symbol = left.getSymbol() + right.getSymbol();
        this.frequency = left.getFrequency() + right.getFrequency();
    }

    boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    HuffmanNode getLeft() {
        return this.left;
    }

    HuffmanNode getRight() {
        return this.right;
    }

    int getFrequency() {
        return this.frequency;
    }

    public int getSymbol() {
        return this.symbol;
    }

    /**
     * Compare node to parameter node. If frequencies are same compare symbol.
     * @param other Huffman node to compare to.
     * @return -1, 0 or 1 if parameter node is greater, equal or less.
     */
    @Override
    public int compareTo(HuffmanNode other) {
        if (this.frequency == other.frequency) {
            return this.symbol - other.symbol;
        }
        return this.frequency - other.getFrequency();
    }

}
