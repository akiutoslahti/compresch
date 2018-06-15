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

package compresch.ds;

import java.util.Objects;

/**
 * Class used as node in LzwHashTable.
 */
public class LzwEntry {

    private String key;
    private int value;
    private LzwEntry nextEntry;
    private int hash;

    /**
     * Constructs a new LzwEntry to be used in LzwHashTable.
     * @param key   String to be used as key in hash table.
     * @param value codeword corresponding to @param key
     * @throws NullPointerException if @param key is null.
     */
    public LzwEntry(String key, int value) {
        Objects.requireNonNull(key);
        this.key = key;
        this.value = value;
        this.nextEntry = null;
        this.hash = -1;
    }

    String getKey() {
        return this.key;
    }

    public int getValue() {
        return this.value;
    }

    void setNextEntry(LzwEntry entry) {
        this.nextEntry = entry;
    }

    LzwEntry getNextEntry() {
        return this.nextEntry;
    }

    /**
     * Returns the hash of object.
     * @return object hash as integer
     */
    int getHash() {
        if (this.hash == -1) {
            this.hash = this.key.hashCode() + this.key.length();
        }
        return this.hash;
    }

}
