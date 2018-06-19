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

import compresch.io.BitInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for reading LZW encoded file.
 */
public class LzwReader {

    private BitInputStream input;
    private int codewordLength;

    /**
     * Constructs new LzwReader.
     * @param inputFilePath  input file to read data from.
     * @param codeWordLength bit sequence length to use in reading encoded data.
     * @throws NullPointerException  if parameter is null.
     * @throws FileNotFoundException if input file does not exist.
     */
    LzwReader(String inputFilePath, int codeWordLength) throws FileNotFoundException {
        Objects.requireNonNull(inputFilePath);
        this.input = new BitInputStream(inputFilePath);
        this.codewordLength = codeWordLength;
    }

    /**
     * Reads next bit sequence from the BitInputStream.
     * @return read codeword as integer.
     * @throws IOException if an I/O exception occurs.
     */
    public int read() throws IOException {
        int codeWord = 0;
        for (int i = 0; i < this.codewordLength; i++) {
            int buffer = this.input.read();
            if (buffer == -1) {
                throw new IOException();
            }
            codeWord = (codeWord << 1);
            codeWord = codeWord | buffer;
        }
        return codeWord;
    }

    public void close() throws IOException {
        this.input.close();
    }

    /**
     * Skip bytes counting from the current position of stream.
     * @param bytes amount of bytes to skip.
     * @throws IOException if an I/O exception occurs.
     */
    void skip(int bytes) throws IOException {
        this.input.skip(bytes);
    }
}
