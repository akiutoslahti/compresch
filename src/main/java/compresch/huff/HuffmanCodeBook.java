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

public class HuffmanCodeBook {

    @SuppressWarnings("unchecked")
    private DynamicArray<Integer>[] codes = new DynamicArray[257];

    /**
     * Populate code table with codes from Huffman tree.
     * @param root root node of fully constructed Huffman tree.
     */
    public void buildCodeBook(HuffmanNode root) {
        dfsTraverse(root, "");
    }

    /**
     * Recursive helper method to do depth first search on Huffman tree.
     * @param node        node to start/continue dfs traversal.
     * @param currentCode string of current path choices. 0 for left child and 1 for right child.
     */
    private void dfsTraverse(HuffmanNode node, String currentCode) {
        if (node.isLeaf()) {
            int symbol = node.getSymbol();
            codes[symbol] = new DynamicArray<>();
            for (int i = 0; i < currentCode.length(); i++) {
                codes[symbol].push(Integer.parseInt(currentCode.charAt(i) + ""));
            }
            return;
        }
        dfsTraverse(node.getLeft(), currentCode + "0");
        dfsTraverse(node.getRight(), currentCode + "1");
    }

    /**
     * Return a list containing bits which make for a codeword of a symbol.
     * @param symbol byte value as unsigned integer.
     * @return List containing bits of codeword.
     */
    public DynamicArray<Integer> getCode(int symbol) {
        if (checkSymbol(symbol)) {
            return codes[symbol];
        }
        return null;
    }

    /**
     * Check whether given symbol is in correct range.
     * @param symbol byte value as unsigned integer.
     * @return true if symbol is in correct range, false if not.
     */
    private boolean checkSymbol(int symbol) {
        if (symbol >= 0 && symbol <= 256) {
            return true;
        }
        return false;
    }
}
