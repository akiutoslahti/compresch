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

package compresch;

import compresch.huff.HuffmanDecoder;
import compresch.huff.HuffmanEncoder;

import java.io.File;
import java.io.IOException;

public class Main {

    /**
     * Compresses/decompresses input file to output file depending on given arguments.
     * @param args [-c/-d] [input file] [output file]
     */
    public static void main(String[] args) {

        if (args.length != 3) {
            throw new IllegalArgumentException("Faulty parameters.");
        }

        String action = args[0];
        File inputFile = new File(args[1]);
        File outputFile = new File(args[2]);

        if (action.equals("-c")) {
            try {
                compress(inputFile, outputFile);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                throw new RuntimeException("Command failed.");
            }
        } else if (action.equals("-d")) {
            try {
                decompress(inputFile, outputFile);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                throw new RuntimeException("Command failed.");
            }
        } else {
            throw new IllegalArgumentException("Unknown command.");
        }

    }

    private static void compress(File input, File output) throws IOException {
        HuffmanEncoder encoder = new HuffmanEncoder(input, output);
        encoder.encode();
    }

    private static void decompress(File input, File output) throws IOException {
        HuffmanDecoder decoder = new HuffmanDecoder(input, output);
        decoder.decode();
    }

}
