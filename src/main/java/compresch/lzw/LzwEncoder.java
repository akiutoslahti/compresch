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

/**
 * Encodes file using LZW.
 */
public class LzwEncoder {

    /**
     * Construct encoder to encode with LZW.
     * @param inputPath      Path to input file to be compressed.
     * @param outputPath     Path to output file to compress to.
     * @param codewordLength bit sequence length to use in writing encoded data.
     * @throws NullPointerException if either one of parameters is null.
     * @throws IOException          if an I/O exception occurs.
     */
    public static void encode(
        String inputPath, String outputPath, int codewordLength) throws IOException {
        Objects.requireNonNull(inputPath);
        Objects.requireNonNull(outputPath);

        InputStream input = new BufferedInputStream(new FileInputStream(inputPath));
        LzwWriter output = new LzwWriter(outputPath, codewordLength);
        writeHeader(output, codewordLength);
        makeEncode(input, output, codewordLength);
        input.close();
        output.close();
    }

    /**
     * Writes a header containing information on used encoding to encoded file.
     * @param output         LzwWriter to utilize for writing header.
     * @param codewordLength bit sequence length used in encoding.
     * @throws IOException if an I/O exception occurs.
     */
    private static void writeHeader(LzwWriter output, int codewordLength) throws IOException {
        String encoding;
        if (codewordLength == 9) {
            encoding = "LZW-0" + String.valueOf(codewordLength);
        } else {
            encoding = "LZW-" + String.valueOf(codewordLength);
        }
        for (int i = 0; i < encoding.length(); i++) {
            output.writeByte((byte) (encoding.charAt(i)));
        }
    }

    /**
     * Helper method to make encoding happen.
     * @param input          InputStream to read from.
     * @param output         LzwWriter to write to.
     * @param codewordLength bit sequence length used in encoding.
     * @throws IOException if an I/O exception occurs.
     */
    private static void makeEncode(
        InputStream input, LzwWriter output, int codewordLength) throws IOException {
        LzwDictionary dict = new LzwDictionary(codewordLength);
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
        output.write(dict.getPseudoEof());
    }

}
