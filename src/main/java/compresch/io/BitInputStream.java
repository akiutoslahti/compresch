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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Utility class for reading data bit by bit using java.io.InputStream as a basis.
 */
public class BitInputStream {

    private InputStream input;
    private int inputBuffer;
    private int bitsLeft;

    /**
     * Construct bit input stream based on underlying byte input stream.
     * @param inputFilePath file to be read.
     * @throws NullPointerException  when null is provided as parameter.
     * @throws FileNotFoundException if parameter inputFile is not found.
     */
    public BitInputStream(String inputFilePath) throws FileNotFoundException {
        Objects.requireNonNull(inputFilePath);
        this.input = new BufferedInputStream(new FileInputStream(inputFilePath));
        inputBuffer = 0;
        bitsLeft = 0;
    }

    /**
     * Read next bit from stream.
     * @return 0 or 1 as next bit. -1 if end of stream is reached.
     * @throws IOException if an I/O exception occurs.
     */
    public int read() throws IOException {
        if (inputBuffer == -1) {
            return -1;
        }
        if (bitsLeft == 0) {
            inputBuffer = input.read();
            if (inputBuffer == -1) {
                return -1;
            }
            bitsLeft = 8;
        }
        bitsLeft--;
        return (inputBuffer >>> bitsLeft) & 1;
    }

    /**
     * Read next byte from stream.
     * @return byte as integer value in range [0,255]. -1 if end of stream is reached.
     * @throws IOException if an I/O exception occurs.
     */
    public int readByte() throws IOException {
        return input.read();
    }

    /**
     * Close underlying byte stream.
     * @throws IOException if an I/O exception occurs.
     */
    public void close() throws IOException {
        this.inputBuffer = 0;
        this.bitsLeft = 0;
        input.close();
    }

    public void skip(int n) throws IOException {
        this.input.skip(n);
    }

}
