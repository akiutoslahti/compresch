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

import compresch.io.BitOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class HuffmanEncoder {

    private File inputFile;
    private File outputFile;
    private HuffmanCodeBook codeBook;

    /**
     * Construct encoder to encode Huffman coding.
     * @param input  input file to be compressed.
     * @param output output file to compress to.
     * @throws NullPointerException if either one of parameters is null.
     */
    public HuffmanEncoder(File input, File output) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(output);
        this.inputFile = input;
        this.outputFile = output;
        this.codeBook = new HuffmanCodeBook();
    }

    /**
     * Public method to execute encode.
     */
    public void encode() throws IOException {
        getCodeBook();
        InputStream input = new BufferedInputStream(new FileInputStream(this.inputFile));
        BitOutputStream output =
            new BitOutputStream(new BufferedOutputStream(new FileOutputStream(this.outputFile)));

        writeCodeLengths(output);
        writeEncoded(input, output);

        input.close();
        output.close();
    }

    /**
     * Private helper method for encoding. Constructs a code table from given input.
     */
    private void getCodeBook() throws IOException {
        HuffmanFrequencyTable freqTable = new HuffmanFrequencyTable();
        freqTable.buildFreqTable(this.inputFile);
        HuffmanTree huffTree = new HuffmanTree();
        huffTree.buildHuffmanTree(freqTable);
        this.codeBook.buildCodeBook(huffTree.getRoot());
    }

    /**
     * Writes codeword lengths to output.
     * @param output BitOutputStream to write codeword lengths to.
     * @throws IOException if an I/O exception occurs.
     */
    private void writeCodeLengths(BitOutputStream output) throws IOException {
        for (int i = 0; i < 257; i++) {
            List<Integer> bits = codeBook.getCode(i);
            if (bits == null) {
                output.writeByte((byte) 0);
            } else {
                output.writeByte((byte) bits.size());
            }
        }
    }

    /**
     * Private helper method for encoding. Uses pre-constructed code table to
     * encode every symbol from input to output.
     * @param input  InputStream to read symbols to be encoded.
     * @param output BitOutputStream to write encoded symbols to.
     * @throws IOException if an I/O exception occurs.
     */
    private void writeEncoded(InputStream input, BitOutputStream output) throws IOException {
        while (true) {
            int readBuffer = input.read();
            if (readBuffer == -1) {
                break;
            }
            writeSymbol(readBuffer, output);
        }
        writeSymbol(256, output);
    }

    /**
     * Private helper method to actually write encoded symbol to output.
     * @param symbol byte to be encoded as unsigned integer.
     * @param output BitOutputStream where to write coded symbol.
     * @throws IOException if an I/O exception occurs.
     */
    private void writeSymbol(int symbol, BitOutputStream output) throws IOException {
        List<Integer> bits = codeBook.getCode(symbol);
        for (int bit : bits) {
            output.write(bit);
        }
    }
}
