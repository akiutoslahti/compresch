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

import compresch.ds.LzwEntry;
import compresch.ds.LzwHashTable;

public class LzwDictionary {

    private LzwHashTable dictionary;
    private String[] index;
    private int numOfCodes;

    /**
     * Constructs and initiates new dictionary.
     */
    public LzwDictionary() {
        this.dictionary = new LzwHashTable();
        this.index = new String[4096];
        initDictionary();
    }

    /**
     * Initiates dictionary with base symbols.
     */
    private void initDictionary() {
        for (int i = 0; i < 256; i++) {
            addEntry((char) (i) + "");
        }
    }

    /**
     * Adds new entry to dictionary.
     * @param symbol String entry to be added to dictionary.
     * @return true if adding was successful, false if dictionary is full.
     */
    public boolean addEntry(String symbol) {
        if (this.numOfCodes < 4095) {
            this.dictionary.insert(new LzwEntry(symbol, this.numOfCodes));
            this.index[this.numOfCodes] = symbol;
            numOfCodes++;
            return true;
        }
        return false;
    }

    /**
     * Returns symbol corresponding to parameter codeword.
     * @param codeword Integer codeword to be searched from dictionary.
     * @return String symbol for given codeword from dictionary.
     */
    public String getSymbol(int codeword) throws IllegalArgumentException {
        if (codeword < 0 || codeword > 4095) {
            throw new IllegalArgumentException();
        }
        if (codeword >= this.numOfCodes) {
            return null;
        }
        return this.index[codeword];
    }

    /**
     * Returns codeword corresponding to parameter symbol.
     * @param symbol String symbol to be searched from dictionary.
     * @return codeword for given symbol in range [0, 4095] if found. -1 if not found.
     */
    public int getCodeword(String symbol) {
        return this.dictionary.search(symbol);
    }

}