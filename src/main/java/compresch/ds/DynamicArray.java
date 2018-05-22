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

public class DynamicArray {

    private int[] array;
    private int size;
    private int maxSize;

    public DynamicArray() {
        this.array = new int[10];
        this.size = 0;
        this.maxSize = 10;
    }

    public void push(int value) {
        if (this.size == this.maxSize) {
            resize();
        }
        this.array[this.size++] = value;
    }

    public int at(int i) {
        if (i < 0 || i >= this.size) {
            throw new NullPointerException();
        } else {
            return this.array[i];
        }
    }

    public int size() {
        return this.size;
    }

    private void resize() {
        this.maxSize *= 2;
        int[] newArray = new int[this.maxSize];
        System.arraycopy(this.array, 0, newArray, 0, this.size);
        this.array = newArray;
    }

    public String contents() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            builder.append(this.array[i]);
            builder.append(" ");
        }
        return builder.toString().substring(0, builder.length() - 1);
    }

}
