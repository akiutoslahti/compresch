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

public class HuffmanCodeBookTest {

    @Test
    public void constructHuffmanCodeBookTest() {
        HuffmanCodeBook huffmanCodeBook = new HuffmanCodeBook();
        assertNotNull(huffmanCodeBook);
    }

    @Test
    public void getCodeNotValidTest() {
        HuffmanCodeBook huffmanCodeBook = new HuffmanCodeBook();
        assertNull(huffmanCodeBook.getCode(257));
        assertNull(huffmanCodeBook.getCode(-1));
    }

    @Test
    public void buildCodeBookTest() {
        HuffmanCodeBook huffmanCodeBook = new HuffmanCodeBook();
        huffmanCodeBook.buildCodeBook(initHuffmanTree());
        int[] symbols = {10, 65, 66, 84, 85, 89, 256};
        List<String> expectedCodes = new ArrayList<>(
                Arrays.asList("010", "011", "00", "100", "101", "110", "111"));
        for (int i = 0; i < symbols.length; i++) {
            List<Integer> codeword = huffmanCodeBook.getCode(symbols[i]);
            assertEquals(expectedCodes.get(i), codewordToString(codeword));
        }
    }

    private String codewordToString(List<Integer> codeword) {
        String res = "";
        for (int i = 0; i < codeword.size(); i++) {
            res += codeword.get(i);
        }
        return res;
    }

    private HuffmanNode initHuffmanTree() {
        HuffmanNode root = new HuffmanNode(
                new HuffmanNode(
                        new HuffmanNode((int)('B')),
                        new HuffmanNode(
                                new HuffmanNode((int)('\n')),
                                new HuffmanNode((int)('A')))),
                new HuffmanNode(
                        new HuffmanNode(
                                new HuffmanNode((int)('T')),
                                new HuffmanNode((int)('U'))),
                        new HuffmanNode(
                                new HuffmanNode((int)('Y')),
                                new HuffmanNode(256))));
        return root;
    }

}