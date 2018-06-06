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

package compresch.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class EncodingChecker {

    /**
     * Check encoding in file to be decompressed.
     * @param inputPath Path to file to be examined.
     * @return String "HUF" for Huffman coding, "LZW" for Lempel-Ziv-Welch compression.
     * @throws NullPointerException on null input.
     * @throws IOException if input file is not found or cannot be read.
     */
    public static String readEncoding(String inputPath) throws IOException {
        Objects.requireNonNull(inputPath);
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("input file not found");
        }
        InputStream input = new BufferedInputStream(new FileInputStream(new File(inputPath)));
        int[] readBytes = new int[3];
        for (int i = 0; i < readBytes.length; i++) {
            readBytes[i] = input.read();
        }
        input.close();
        StringBuilder builder = new StringBuilder();
        for (int i : readBytes) {
            builder.append((char) (i));
        }
        if (builder.toString().equals("HUF") || builder.toString().equals("LZW")) {
            return builder.toString();
        } else {
            throw new UnsupportedEncodingException("Input file doesn't contain valid header.");
        }
    }

    /**
     * Reads and returns codeword length for LZW compression from the header.
     * @param inputPath Path to file to be examined.
     * @return encoding length of examined file.
     * @throws IOException if an I/O exception occurs.
     */
    public static int readLzwCodewordLength(String inputPath) throws IOException {
        File inputFile = new File(inputPath);
        InputStream input = new BufferedInputStream(new FileInputStream(new File(inputPath)));
        input.skip(4);
        int[] readBytes = new int[2];
        for (int i = 0; i < readBytes.length; i++) {
            readBytes[i] = input.read();
        }
        input.close();
        StringBuilder builder = new StringBuilder();
        for (int i : readBytes) {
            builder.append((char) (i));
        }
        return Integer.parseInt(builder.toString());
    }

}
