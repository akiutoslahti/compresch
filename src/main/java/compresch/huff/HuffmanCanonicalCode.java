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

public class HuffmanCanonicalCode {

    private HuffmanNode canonRoot;

    /**
     * Build a canonical Huffman tree from the lengths of initial codewords.
     * @param codeLengths array of Huffman codeword lengths.
     */
    public void buildCanonCode(int[] codeLengths) {
        List<HuffmanNode> nodes = new ArrayList<>();
        for (int i = max(codeLengths); i >= 0; i--) {
            List<HuffmanNode> newNodes = new ArrayList<>();
            if (i > 0) {
                for (int j = 0; j < codeLengths.length; j++) {
                    if (codeLengths[j] == i) {
                        newNodes.add(new HuffmanNode(j));
                    }
                }
            }
            for (int j = 0; j < nodes.size(); j += 2) {
                newNodes.add(new HuffmanNode(nodes.get(j), nodes.get(j + 1)));
            }
            nodes = newNodes;
        }
        this.canonRoot = nodes.get(0);
    }

    /**
     * Returns root of canonical code tree.
     * @return root node of canonical Huffman tree.
     */
    public HuffmanNode getCanonRoot() {
        return this.canonRoot;
    }

    /**
     * Find maximum length from array of codeword lengths.
     * @param codeLengths array of Huffman codeword lengths.
     * @return length of the longest codeword.
     */
    private int max(int[] codeLengths) {
        int max = 0;
        for (int i = 0; i < codeLengths.length; i++) {
            if (codeLengths[i] > max) {
                max = codeLengths[i];
            }
        }
        return max;
    }

}
