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

import java.util.ArrayList;
import java.util.List;

public class HuffmanCodeTable {

    @SuppressWarnings("unchecked")
    private List<Integer>[] codes = new ArrayList[257];

    /**
     * Populate code table with codes from Huffman tree.
     * @param huffmanTree full constructed Huffman tree.
     */
    public void buildCodeTable(HuffmanTree huffmanTree) {
        dfsTraverse(huffmanTree.getRoot(), "");
    }

    /**
     * Recursive helper method to do depth first search on Huffman tree.
     * @param node node to start/continue dfs traversal.
     * @param currentCode string of current path choices. 0 for left child and 1 for right child.
     */
    private void dfsTraverse(HuffmanNode node, String currentCode) {
        if (node.isLeaf()) {
            int symbol = node.getSymbol();
            codes[symbol] = new ArrayList<>();
            for (int i = 0; i < currentCode.length(); i++) {
                codes[symbol].add(Integer.parseInt(currentCode.charAt(i) + ""));
            }
            return;
        }
        dfsTraverse(node.getLeft(), currentCode + "0");
        dfsTraverse(node.getRight(), currentCode + "1");
    }

    /**
     * Return a List containing symbols huffman code.
     * @param symbol byte value as unsigned integer.
     * @return List containing bits of symbols huffman code.
     */
    public List<Integer> getCode(int symbol) {
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

    /**
     * String representation of built code table for debugging purposes.
     * @return Every symbol in code table with coding as a string without EOL.
     */
    public String toString() {
        String res = "";
        for (int i = 0; i < 257; i++) {
            if (codes[i] != null) {
                res += i + ": " + codes[i] + "\n";
            }
        }
        return res.substring(0, res.length() - 1);
    }

}
