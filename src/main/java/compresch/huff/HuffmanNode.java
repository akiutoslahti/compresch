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
        if (!checkSymbolValue(symbol)) {
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
        if (!checkSymbolValue(symbol)) {
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

    /**
     * Check if node is a leaf node.
     * @return true if node is a leaf and false if node is internal node.
     */
    public boolean isLeaf() {
        if (this.left == null && this.right == null) {
            return true;
        }
        return false;
    }

    /**
     * Get left child node.
     * @return left child node.
     */
    public HuffmanNode getLeft() {
        return this.left;
    }

    /**
     * Get right child node.
     * @return right child node.
     */
    public HuffmanNode getRight() {
        return this.right;
    }

    /**
     * Get frequency of node.
     * @return frequency of node.
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Get symbol of node.
     * @return symbol byte of node as unsigned integer value.
     */
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

    /**
     * Check whether symbol value is in range [0,255] or 256(pseudo-EOF).
     * @param symbol byte as unsigned integer value, range 0-255. 256 reserved for pseudo-EOF.
     * @return true if symbol is in valid range, false otherwise.
     */
    private boolean checkSymbolValue(int symbol) {
        if (symbol >= 0 && symbol <= 256) {
            return true;
        }
        return false;
    }
}
