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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import compresch.huff.HuffmanNode;

import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Test;

public class HuffmanHeapTest {

    @Test
    public void constructHuffmanHeapTest() {
        HuffmanHeap heap = new HuffmanHeap();
        assertNotNull(heap);
    }

    @Test
    public void onlyPushTest() {
        HuffmanHeap heap = new HuffmanHeap();
        for (int i = 10; i > 0; i--) {
            heap.push(new HuffmanNode(i));
            assertEquals(i, heap.peek().getSymbol());
        }
    }

    @Test
    public void randomPushTest() {
        HuffmanHeap heap = new HuffmanHeap();
        Random rng = new Random();
        int smallest = Integer.MAX_VALUE;
        for (int i = 0; i < 97; i++) {
            int current = rng.nextInt(257);
            if (current < smallest) {
                smallest = current;
            }
            heap.push(new HuffmanNode(current));
            assertEquals(smallest, heap.peek().getSymbol());
        }
    }

    @Test
    public void pushPollTest() {
        HuffmanHeap heap = new HuffmanHeap();
        for (int i = 20; i > 0; i--) {
            heap.push(new HuffmanNode(i));
            if (i % 3 == 0) {
                heap.pop();
                assertEquals(i + 1, heap.peek().getSymbol());
            } else {
                assertEquals(i, heap.peek().getSymbol());
            }
        }
    }

    @Test
    public void randomPushPollTest() {
        HuffmanHeap heap = new HuffmanHeap();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Random rng = new Random();
        for (int i = 0; i < 857; i++) {
            int curr = rng.nextInt(257);
            heap.push(new HuffmanNode(curr));
            pq.add(curr);
            if (i % 3 == 0) {
                pq.poll();
                heap.pop();
            }
            if (!pq.isEmpty() && heap.size() > 0) {
                assertEquals((int) pq.peek(), heap.peek().getSymbol());

            }
        }
    }

}