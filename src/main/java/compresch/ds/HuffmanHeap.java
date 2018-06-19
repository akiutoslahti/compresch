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

import compresch.huff.HuffmanNode;

/**
 * Minimum heap implementation for HuffmanNode data type.
 */
public class HuffmanHeap {

    private HuffmanNode[] heap;
    private int size;
    private int maxSize;

    /**
     * Constructs a new minimum heap with default values.
     */
    public HuffmanHeap() {
        this.heap = new HuffmanNode[16];
        this.size = 0;
        this.maxSize = 15;
    }

    /**
     * Inserts new node to the heap.
     * @param node HuffmanNode to be inserted to heap.
     */
    public void push(HuffmanNode node) {
        this.size++;
        if (this.size == this.maxSize) {
            resize();
        }
        int position = this.size;
        while (position > 1 && this.heap[parent(position)].compareTo(node) > 0) {
            this.heap[position] = this.heap[parent(position)];
            position = parent(position);
        }
        this.heap[position] = node;
    }

    /**
     * Returns top(minimum) element from the heap.
     * @return HuffmanNode at the top of heap.
     */
    public HuffmanNode peek() {
        return this.heap[1];
    }

    /**
     * Removes top(minimum) element from the heap.
     */
    public void pop() {
        this.heap[1] = this.heap[this.size];
        this.size--;
        heapify(1);
    }

    /**
     * Returns the number of elements in heap.
     * @return number of elements in heap.
     */
    public int size() {
        return this.size;
    }

    /**
     * Grows capacity of the heap by current capacity.
     */
    private void resize() {
        this.maxSize *= 2;
        HuffmanNode[] newHeap = new HuffmanNode[this.maxSize];
        System.arraycopy(this.heap, 0, newHeap, 0, this.size);
        this.heap = newHeap;
    }

    private int parent(int index) {
        return index / 2;
    }

    private int left(int index) {
        return index * 2;
    }

    private int right(int index) {
        return index * 2 + 1;
    }

    private void heapify(int pos) {
        int left = left(pos);
        int right = right(pos);
        if (right < this.size) {
            int smallest;
            if (this.heap[left].compareTo(this.heap[right]) < 0) {
                smallest = left;
            } else {
                smallest = right;
            }
            if (this.heap[pos].compareTo(this.heap[smallest]) > 0) {
                swap(pos, smallest);
                heapify(smallest);
            }
        } else if (left == this.size && this.heap[pos].compareTo(this.heap[left]) > 0) {
            swap(pos, left);
        }
    }

    private void swap(int a, int b) {
        HuffmanNode temp = this.heap[a];
        this.heap[a] = this.heap[b];
        this.heap[b] = temp;
    }

}
