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

package compresch.lzw;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class LzwEncoder {

    /**
     * Construct encoder to encode with LZW.
     * @param inputPath  Path to input file to be compressed.
     * @param outputPath Path to output file to compress to.
     * @throws NullPointerException if either one of parameters is null.
     * @throws IOException if an I/O exception occurs.
     */
    public static void encode(String inputPath, String outputPath) throws IOException {
        Objects.requireNonNull(inputPath);
        Objects.requireNonNull(outputPath);

        InputStream input = new BufferedInputStream(new FileInputStream(inputPath));
        LzwWriter output = new LzwWriter(outputPath);
        writeEncoding(output);
        makeEncode(input, output);
        output.writePseudoEof();
        input.close();
        output.close();
    }

    private static void writeEncoding(LzwWriter output) throws IOException {
        output.writeByte((byte) ('L'));
        output.writeByte((byte) ('Z'));
        output.writeByte((byte) ('W'));
    }

    /**
     * Helper method to make encoding happen.
     * @param input  InputStream to read from.
     * @param output LzwWriter to write to.
     * @throws IOException if an I/O exception occurs.
     */
    private static void makeEncode(InputStream input, LzwWriter output) throws IOException {
        LzwDictionary dict = new LzwDictionary();
        StringBuilder inputBuffer = new StringBuilder();
        while (true) {
            int readBuffer = input.read();
            if (readBuffer == -1) {
                output.write(dict.getCodeword(inputBuffer.toString()));
                break;
            }
            inputBuffer.append((char) (readBuffer));
            if (dict.getCodeword(inputBuffer.toString()) == -1) {
                dict.addEntry(inputBuffer.toString());
                output.write(dict.getCodeword(inputBuffer.substring(0, inputBuffer.length() - 1)));
                inputBuffer.delete(0, inputBuffer.length() - 1);
            }
        }
    }

}
