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

import compresch.io.BitInputStream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class HuffmanDecoder {

    /**
     * Public method to execute decode.
     * @param inputPath  path to input file to be decompressed.
     * @param outputPath path to output file to decompress to.
     * @throws NullPointerException if either one of parameters is null.
     */
    public static void decode(String inputPath, String outputPath) throws IOException {
        Objects.requireNonNull(inputPath);
        Objects.requireNonNull(outputPath);

        BitInputStream input = new BitInputStream(inputPath);
        OutputStream output = new BufferedOutputStream(new FileOutputStream(outputPath));

        input.skip(3);

        HuffmanNode root = restoreHuffmanTree(input);

        writeDecoded(input, output, root);

        input.close();
        output.close();
    }

    /**
     * Read encoded input file and write decoded data to output file.
     * @param input  BitInputStream where to read encoded data.
     * @param output OutputStream where to write decoded data.
     * @param root   root node of Huffman tree for decoding code words.
     * @throws IOException if an I/O exception occurs.
     */
    private static void writeDecoded(BitInputStream input, OutputStream output, HuffmanNode root)
        throws IOException {
        HuffmanNode node = root;
        while (true) {
            if (node.isLeaf()) {
                if (node.getSymbol() == 256) {
                    break;
                }
                output.write(node.getSymbol());
                node = root;
            }
            int bit = input.read();
            if (bit == 0) {
                node = node.getLeft();
            }
            if (bit == 1) {
                node = node.getRight();
            }
        }
    }

    /**
     * Restore Huffman tree from header of encoded file.
     * @param input BitInputStream where to read header.
     * @return root node of restored Huffman tree.
     * @throws IOException if an I/O exception occurs.
     */
    private static HuffmanNode restoreHuffmanTree(BitInputStream input) throws IOException {
        int[] codeLengths = readCodelengths(input);
        HuffmanTree huffTree = new HuffmanTree();
        huffTree.buildHuffmanTree(codeLengths);
        return huffTree.getRoot();
    }

    /**
     * Read codeword lengths for symbols from input file.
     * @param input BitInputStream for reading codeword lengths.
     * @return Array of codeword lengths.
     * @throws IOException if an I/O exception occurs.
     */
    private static int[] readCodelengths(BitInputStream input) throws IOException {
        int[] codeLengths = new int[257];
        for (int i = 0; i < 257; i++) {
            codeLengths[i] = input.readByte();
        }
        return codeLengths;
    }
}
