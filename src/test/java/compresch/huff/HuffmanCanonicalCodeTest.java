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

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HuffmanCanonicalCodeTest {

    @Test
    public void constructHuffmanCanonicalCodeTest() {
        HuffmanCanonicalCode huffmanCanonicalCode = new HuffmanCanonicalCode();
        assertNotNull(huffmanCanonicalCode);
    }

    @Test
    public void getCanonRootNullTest() {
        HuffmanCanonicalCode huffmanCanonicalCode = new HuffmanCanonicalCode();
        assertNull(huffmanCanonicalCode.getCanonRoot());
    }

    @Test
    public void buildCanonCodeTest() {
        int[] codeLengths = new int[257];
        codeLengths[(int)('\n')] = 3;
        codeLengths[(int)('A')] = 3;
        codeLengths[(int)('B')] = 2;
        codeLengths[(int)('T')] = 3;
        codeLengths[(int)('U')] = 3;
        codeLengths[(int)('Y')] = 3;
        codeLengths[256] = 3;
        HuffmanCanonicalCode huffmanCanonicalCode = new HuffmanCanonicalCode();
        huffmanCanonicalCode.buildCanonCode(codeLengths);
        HuffmanNode canonRoot = huffmanCanonicalCode.getCanonRoot();
        assertTrue(canonRoot instanceof HuffmanNode);
        List<String> expectedCodes = new ArrayList<>(
                Arrays.asList("00", "010", "011", "100", "101", "110", "111"));
        List<String> actualCodes = new ArrayList<>();
        checkTree(canonRoot, actualCodes, "");
        assertArrayEquals(expectedCodes.toArray(), actualCodes.toArray());
    }

    private void checkTree(HuffmanNode node, List<String> actualCodes, String currentPath) {
        if (node.isLeaf()) {
            actualCodes.add(currentPath);
            return;
        }
        checkTree(node.getLeft(), actualCodes, currentPath + "0");
        checkTree(node.getRight(), actualCodes, currentPath + "1");
    }
}