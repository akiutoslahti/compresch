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

package compresch.huff;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class to hold count of distinct symbols in file to be encoded.
 */
public class HuffmanFrequencyTable {

    private int[] frequency;

    /**
     * Construct a frequency table for symbols [0, 255]. Symbol 256 acts as a pseudo EOF.
     */
    HuffmanFrequencyTable() {
        this.frequency = new int[257];
        this.frequency[256]++;
    }

    /**
     * Build frequency table from input file by traversing it once and counting occurrences of all
     * bytes.
     * @param inputFilePath File to base frequency table on.
     * @throws IOException if an I/O exception occurs.
     */
    void buildFreqTable(String inputFilePath) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(inputFilePath));
        while (true) {
            int readBuffer = input.read();
            if (readBuffer == -1) {
                break;
            }
            this.frequency[readBuffer]++;
        }
        input.close();
    }

    /**
     * Returns frequency of a symbol.
     * @param symbol byte as unsigned integer value.
     * @return frequency of symbol. -1 if parameter is not valid.
     */
    int getFrequency(int symbol) {
        if (symbol >= 0 && symbol <= 256) {
            return this.frequency[symbol];
        }
        return -1;
    }

}
