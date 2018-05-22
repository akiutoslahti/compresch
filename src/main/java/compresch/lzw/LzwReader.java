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

import java.io.IOException;
import java.util.Objects;

public class LzwReader implements AutoCloseable {

    private BitInputStream input;

    /**
     * Constructs new LzwReader.
     * @param input BitInputStream to read data from.
     * @throws NullPointerException if parameter is null.
     */
    public LzwReader(BitInputStream input) {
        Objects.requireNonNull(input);
        this.input = input;
    }

    /**
     * Reads next 12bits from the BitInputStream.
     * @return read codeword as integer.
     * @throws IOException if an I/O exception occurs.
     */
    public int read() throws IOException {
        int codeWord = 0;
        for (int i = 0; i < 12; i++) {
            int buffer = this.input.read();
            if (buffer == -1) {
                throw new IOException();
            }
            codeWord = (codeWord << 1);
            codeWord = codeWord | buffer;
        }
        return codeWord;
    }

    @Override
    public void close() throws IOException {
        this.input.close();
    }

    public void skip(int n) throws IOException {
        this.input.skip(n);
    }
}
