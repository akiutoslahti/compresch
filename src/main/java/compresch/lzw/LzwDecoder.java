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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class LzwDecoder {

    /**
     * Public method to decode a file.
     * @param inputPath  path to input file to be decompressed.
     * @param outputPath path to output file to decompress to.
     * @throws NullPointerException if either one of parameters is null.
     * @throws IOException if an I/O exception occurs.
     */
    public static void decode(
        String inputPath, String outputPath, int codewordLength) throws IOException {
        Objects.requireNonNull(inputPath);
        Objects.requireNonNull(outputPath);

        LzwReader input = new LzwReader(inputPath, codewordLength);
        OutputStream output = new BufferedOutputStream(new FileOutputStream(outputPath));
        input.skip(6);
        makeDecode(input, output, codewordLength);
        input.close();
        output.close();
    }

    /**
     * Private helper method to actually read compressed input and decode it to output.
     * @param input  LzwReader to read encoded input from.
     * @param output OutputStream to write decoded data to.
     * @throws IOException if an I/O exception occurs.
     */
    private static void makeDecode(
        LzwReader input, OutputStream output, int codewordLength) throws IOException {
        LzwDictionary dict = new LzwDictionary(codewordLength);
        int prevCode = input.read();
        String outputBuffer = dict.getSymbol(prevCode);
        writeDecoded(outputBuffer, output);
        char leftOverChar = (char) 0;
        while (true) {
            int newCode = input.read();
            if (newCode == dict.getPseudoEof()) {
                break;
            }
            if (dict.getSymbol(newCode) == null) {
                outputBuffer = dict.getSymbol(prevCode);
                outputBuffer += leftOverChar;
            } else {
                outputBuffer = dict.getSymbol(newCode);
            }
            writeDecoded(outputBuffer, output);
            leftOverChar = outputBuffer.charAt(0);
            dict.addEntry(dict.getSymbol(prevCode) + leftOverChar);
            prevCode = newCode;
        }
    }

    /**
     * Private helper method to actually write decoded string to output.
     * @param outputBuffer String to write to output.
     * @param output       OutputStream to write data to.
     * @throws IOException if an I/O exception occurs.
     */
    private static void writeDecoded(String outputBuffer, OutputStream output) throws IOException {
        for (int i = 0; i < outputBuffer.length(); i++) {
            output.write((int) (outputBuffer.charAt(i)));
        }
    }

}
