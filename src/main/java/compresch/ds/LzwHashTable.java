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

public class LzwHashTable {

    private LzwEntry[] contents;
    private int size;
    private int capacity;
    private final float loadFactor = 0.75f;

    /**
     * Constructs a new LzwHashTable with default values.
     */
    public LzwHashTable() {
        this.size = 0;
        this.capacity = 16;
        this.contents = new LzwEntry[this.capacity];
    }

    /**
     * Inserts new object to hashtable.
     * @param entry LzwEntry to be inserted to hashtable.
     */
    public void insert(LzwEntry entry) {
        if (1.0f * this.size / this.capacity > this.loadFactor) {
            resize();
        }
        insert(this.contents, entry);
        this.size++;
    }

    private void insert(LzwEntry[] contents, LzwEntry entry) {
        int location = entry.getHash() & (this.capacity - 1);
        entry.setNextEntry(contents[location]);
        contents[location] = entry;
    }

    /**
     * Searches for given key from hashtable.
     * @param key String to be searched from hashtable.
     * @return Value attached to right key or -1 if given key is not found.
     */
    public int search(String key) {
        int hash = key.hashCode() + key.length();
        int location = hash & (this.capacity - 1);
        LzwEntry current = this.contents[location];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNextEntry();
        }
        return -1;
    }

    private void resize() {
        this.capacity *= 2;
        LzwEntry[] newContents = new LzwEntry[this.capacity];
        for (int i = 0; i < this.capacity / 2; i++) {
            LzwEntry current = this.contents[i];
            while (current != null) {
                LzwEntry next = current.getNextEntry();
                insert(newContents, current);
                current = next;
            }
        }
        this.contents = newContents;
    }

}
