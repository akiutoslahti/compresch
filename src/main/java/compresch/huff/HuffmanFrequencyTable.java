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

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class HuffmanFrequencyTable {

    private int[] frequency;

    /**
     * Construct a frequency table for symbols [0, 255].
     * Symbol 256 acts as a pseudo EOF.
     */
    public HuffmanFrequencyTable() {
        this.frequency = new int[257];
        this.frequency[256]++;
    }

    /**
     * Build frequency table from input file by traversing it once and counting occurrences
     * of all bytes.
     * @param inputFile File to base frequency table on.
     * @throws IOException if an I/O exception occurs.
     */
    public void buildFreqTable(File inputFile) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream(inputFile));
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
     *
     * @param symbol byte as unsigned integer value.
     * @return frequency of symbol. -1 if parameter is not valid.
     */
    public int getFrequency(int symbol) {
        if (checkSymbol(symbol)) {
            return this.frequency[symbol];
        }
        return -1;
    }

    /**
     * Checks whether the symbol provided is in range [0,256].
     * @param symbol byte as unsigned int value.
     * @return true if symbol is in range and false if not.
     */
    private boolean checkSymbol(int symbol) {
        if (symbol >= 0 && symbol <= 256) {
            return true;
        }
        return false;
    }

}
