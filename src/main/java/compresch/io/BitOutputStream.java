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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Utility class for writing data bit by bit using java.io.OutpuStream as a basis.
 */
public class BitOutputStream implements AutoCloseable {

    private OutputStream output;
    private int outputBuffer;
    private int bitsLeft;

    /**
     * Construct bit output stream based on underlying byte output stream.
     * @param output byte output stream
     * @throws NullPointerException when null is provided as param.
     */
    public BitOutputStream(OutputStream output) {
        Objects.requireNonNull(output);
        this.output = output;
        this.outputBuffer = 0;
        this.bitsLeft = 8;
    }
    
    /**
     * Writes single bits as bytes to underlying byte output stream.
     * @param bit 0 or 1 bit as int value
     * @throws IOException if an I/O exception occurs.
     * @throws IllegalArgumentException when trying to write something else than 0 or 1 bit.
     */
    public void write(int bit) throws IOException {
        if (bit != 0 && bit != 1) {
            throw new IllegalArgumentException();
        }
        outputBuffer = (outputBuffer << 1) | bit;
        bitsLeft--;
        if (bitsLeft == 0) {
            output.write(outputBuffer);
            outputBuffer = 0;
            bitsLeft = 8;
        }
    }

    /**
     * Write a byte to output stream.
     * @param num byte in range [0,255]
     * @throws IOException if an I/O exception occurs.
     * @throws IllegalArgumentException if parameter is not a byte value.
     */
    public void writeByte(byte num) throws IOException {
        if (num < 0 || num > 255) {
            throw new IllegalArgumentException();
        }
        output.write(num);
    }

    /**
     * Close underlying byte output stream. If there are bits left to write
     * to reach byte boundary, fill with zeroes.
     * @throws IOException if an I/O exception occurs.
     */
    public void close() throws IOException {
        while (this.bitsLeft != 8) {
            write(0);
        }
        this.output.close();
    }

}
