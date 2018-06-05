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
import compresch.io.BitOutputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class HuffmanEncoder {

    /**
     * Public method to execute encode.
     * @param inputPath  path to input file to be compressed.
     * @param outputPath path to output file to compress to.
     * @throws NullPointerException if either of arguments is null.
     * @throws IOException if an I/O exception occurs.
     */
    public static void encode(String inputPath, String outputPath) throws IOException {
        Objects.requireNonNull(inputPath);
        Objects.requireNonNull(outputPath);

        HuffmanCodeBook codebook = getCodeBook(inputPath);
        InputStream input = new BufferedInputStream(new FileInputStream(inputPath));
        BitOutputStream output = new BitOutputStream(outputPath);

        writeEncoding(output);
        writeCodeLengths(output, codebook);
        makeEncode(input, output, codebook);

        input.close();
        output.close();
    }

    private static void writeEncoding(BitOutputStream output) throws IOException {
        output.writeByte((byte) ('H'));
        output.writeByte((byte) ('U'));
        output.writeByte((byte) ('F'));
    }

    /**
     * Private helper method for encoding. Constructs a code table from given input.
     */
    private static HuffmanCodeBook getCodeBook(String inputPath) throws IOException {
        HuffmanFrequencyTable freqTable = new HuffmanFrequencyTable();
        freqTable.buildFreqTable(inputPath);
        HuffmanTree huffTree = new HuffmanTree();
        huffTree.buildHuffmanTree(freqTable);
        HuffmanCodeBook codebook = new HuffmanCodeBook();
        codebook.buildCodeBook(huffTree.getRoot());
        return codebook;
    }

    /**
     * Writes codeword lengths to output.
     * @param output BitOutputStream to write codeword lengths to.
     * @throws IOException if an I/O exception occurs.
     */
    private static void writeCodeLengths(
        BitOutputStream output, HuffmanCodeBook codebook) throws IOException {
        for (int i = 0; i < 257; i++) {
            DynamicArray bits = codebook.getCode(i);
            if (bits == null) {
                output.writeByte((byte) 0);
            } else {
                output.writeByte((byte) bits.size());
            }
        }
    }

    /**
     * Helper method to make encoding happen. Uses pre-constructed code table to
     * encode every symbol from input to output.
     * @param input  InputStream to read symbols to be encoded.
     * @param output BitOutputStream to write encoded symbols to.
     * @throws IOException if an I/O exception occurs.
     */
    private static void makeEncode(
        InputStream input, BitOutputStream output, HuffmanCodeBook codebook) throws IOException {
        while (true) {
            int readBuffer = input.read();
            if (readBuffer == -1) {
                break;
            }
            writeSymbol(codebook.getCode(readBuffer), output);
        }
        writeSymbol(codebook.getCode(256), output);
    }

    /**
     * Private helper method to actually write encoded symbol to output.
     * @param bits array of bits representing symbol to be written.
     * @param output BitOutputStream where to write coded symbol.
     * @throws IOException if an I/O exception occurs.
     */
    private static void writeSymbol(
        DynamicArray<Integer> bits, BitOutputStream output) throws IOException {
        for (Integer i : bits) {
            output.write(i);
        }
    }
}
