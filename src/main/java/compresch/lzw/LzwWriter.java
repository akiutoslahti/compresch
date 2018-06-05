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

import compresch.io.BitOutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class LzwWriter implements AutoCloseable {

    private BitOutputStream output;

    /**
     * Constructs new LzwWriter.
     * @param outputFilePath BitOutputStream to write data to.
     * @throws NullPointerException  if parameter is null.
     * @throws FileNotFoundException if output file cannot be opened.
     */
    public LzwWriter(String outputFilePath) throws FileNotFoundException {
        Objects.requireNonNull(outputFilePath);
        this.output = new BitOutputStream(outputFilePath);
    }

    /**
     * Writes a 12bit codeword to BitOutputStream.
     * @param codeWord 12bit integer value.
     * @throws IOException              if an I/O exception occurs.
     * @throws IllegalArgumentException if parameter is not in range [0, 4095].
     */
    public void write(int codeWord) throws IOException, IllegalArgumentException {
        if (codeWord < 0 || codeWord > 4095) {
            throw new IllegalArgumentException();
        }
        for (int i = 11; i >= 0; i--) {
            this.output.write((codeWord >> i) & 1);
        }
    }

    /**
     * Writes PseudoEOF symbol to mark end of encoded data.
     * @throws IOException if an I/O exception occurs
     */
    public void writePseudoEof() throws IOException {
        write(4095);
    }

    @Override
    public void close() throws IOException {
        this.output.close();
    }

    public void writeByte(byte b) throws IOException {
        this.output.writeByte(b);
    }
}
