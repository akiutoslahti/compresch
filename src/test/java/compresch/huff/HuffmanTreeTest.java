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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HuffmanTreeTest {

    private File testFile;
    private HuffmanFrequencyTable frequencyTable;

    @Before
    public void setUp() {
        this.testFile = new File("test.txt");
        try {
            PrintWriter output = new PrintWriter(this.testFile);
            output.println("AYBABTU");
            output.close();
            this.frequencyTable = new HuffmanFrequencyTable();
            this.frequencyTable.buildFreqTable(this.testFile);
        } catch (Exception e) {
        }
    }

    @After
    public void tearDown() {
        assertTrue(this.testFile.delete());
    }

    @Test
    public void constructHuffmanTreeTest() {
        HuffmanTree huffmanTree = new HuffmanTree();
        assertNotNull(huffmanTree);
    }

    @Test
    public void getRootNullTest() {
        HuffmanTree huffmanTree = new HuffmanTree();
        assertNull(huffmanTree.getRoot());
    }

    @Test
    public void buildHuffmanTreeFrequencyTableTest() {
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree(this.frequencyTable);
        checkTree(huffmanTree.getRoot());
    }

    @Test
    public void buildHuffmanTreeCodeLengthsTest() {
        int[] codeLengths = initCodeLengths();
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildHuffmanTree(codeLengths);
        checkTree(huffmanTree.getRoot());
    }

    private void checkTree(HuffmanNode root) {
        assertTrue(root instanceof HuffmanNode);
        List<String> expectedCodes = new ArrayList<>(
                Arrays.asList("00", "010", "011", "100", "101", "110", "111"));
        List<String> actualCodes = new ArrayList<>();
        getCodes(root, actualCodes, "");
        assertArrayEquals(expectedCodes.toArray(), actualCodes.toArray());
    }

    private int[] initCodeLengths() {
        int[] codeLengths = new int[257];
        codeLengths[(int)('\n')] = 3;
        codeLengths[(int)('A')] = 3;
        codeLengths[(int)('B')] = 2;
        codeLengths[(int)('T')] = 3;
        codeLengths[(int)('U')] = 3;
        codeLengths[(int)('Y')] = 3;
        codeLengths[256] = 3;
        return codeLengths;
    }

    private void getCodes(HuffmanNode node, List<String> actualCodes, String currentPath) {
        if (node.isLeaf()) {
            actualCodes.add(currentPath);
            return;
        }
        getCodes(node.getLeft(), actualCodes, currentPath + "0");
        getCodes(node.getRight(), actualCodes, currentPath + "1");
    }

}