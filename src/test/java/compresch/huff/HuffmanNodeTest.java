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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class HuffmanNodeTest {

    @Test
    public void constructHuffmanNodeTest1() {
        try {
            new HuffmanNode(257, 11);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
        try {
            new HuffmanNode(-1, 11);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
        HuffmanNode node = new HuffmanNode(128, 10);
        assertNotNull(node);
    }

    @Test
    public void newHuffmanNodeTest2() {
        try {
            new HuffmanNode(257);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
        try {
            new HuffmanNode(-1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            assert true;
        }
        HuffmanNode node = new HuffmanNode(128);
        assertNotNull(node);
    }

    @Test
    public void newHuffmanNodeTest3() {
        try {
            new HuffmanNode(null, new HuffmanNode(128));
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new HuffmanNode(new HuffmanNode(128), null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        try {
            new HuffmanNode(null, null);
            fail("expected NullPointerException");
        } catch (NullPointerException npe) {
            assert true;
        }
        HuffmanNode node1 = new HuffmanNode(new HuffmanNode(127), new HuffmanNode(128));
        assertNotNull(node1);
    }

    @Test
    public void isLeafTest() {
        HuffmanNode node = new HuffmanNode(128, 10);
        assertTrue(node.isLeaf());
        node = new HuffmanNode(new HuffmanNode(
                128,10), new HuffmanNode(127, 10));
        assertFalse(node.isLeaf());

    }

    @Test
    public void getChildrenTest() {
        HuffmanNode node = new HuffmanNode(new HuffmanNode(
                128,10), new HuffmanNode(127, 10));
        assertNotNull(node.getLeft());
        assertEquals(128, node.getLeft().getSymbol());
        assertNotNull(node.getRight());
        assertEquals(127, node.getRight().getSymbol());
    }

    @Test
    public void getFrequencyTest() {
        HuffmanNode node = new HuffmanNode(127, 11);
        assertEquals(11, node.getFrequency());
    }

    @Test
    public void getSymbolTest() {
        HuffmanNode node = new HuffmanNode(127, 11);
        assertEquals(127, node.getSymbol());
    }

    @Test
    public void compareToTest() {
        HuffmanNode node1 = new HuffmanNode(127, 10);
        HuffmanNode node2 = new HuffmanNode(127, 11);
        HuffmanNode node3 = new HuffmanNode(128, 11);
        HuffmanNode node4 = new HuffmanNode(128, 11);
        assertTrue(node1.compareTo(node2) < 0);
        assertTrue(node3.compareTo(node1) > 0);
        assertEquals(0, node3.compareTo(node4));

    }

}